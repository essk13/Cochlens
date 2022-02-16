package com.ssafy.config.handler;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.ssafy.config.webrtc.Room;
import com.ssafy.config.webrtc.RoomManager;
import com.ssafy.config.webrtc.UserRegistry;
import com.ssafy.config.webrtc.UserSession;
import org.kurento.client.*;
import org.kurento.jsonrpc.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
//----------------------추가사항------------------------

//----------------------추가사항------------------------

public class CallHandler extends TextWebSocketHandler {

//    파일 저장 주소
//    private static final String RECORDER_FILE_PATH = "file:///tmp/HelloWorldRecorded.webm";

    StringBuffer TEMP = new StringBuffer("file:///tmp/RecordedLectureId.webm");

    Integer temp = 1;

    Long lectureId = temp.longValue();

    String RECORDER_FILE_PATH =  TEMP.insert(TEMP.length() - 5, Long.toString(lectureId)).toString();

    private static final Logger log = LoggerFactory.getLogger(CallHandler.class);

    private static final Gson gson = new GsonBuilder().create();

    @Autowired
    private RoomManager roomManager;

    @Autowired
    private UserRegistry registry;

    @Autowired
    private KurentoClient kurento;

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        final JsonObject jsonMessage = gson.fromJson(message.getPayload(), JsonObject.class);

//        로그에 받은 json메세지 출력
        log.debug("Incoming message: {}", jsonMessage);

//        사용자 세션값 가져오기
        final UserSession user = registry.getBySession(session);

//        유저가 없을 경우
        if (user != null) {
            log.debug("Incoming message from user '{}': {}", user.getName(), jsonMessage);
//        유저가 있을 경우
        } else {
            log.debug("Incoming message from new user: {}", jsonMessage);
        }


        switch (jsonMessage.get("id").getAsString()) {
//            방에 들어오기
            case "joinRoom":
                joinRoom(jsonMessage, session);
                break;
            case "receiveVideoFrom": {
                final String senderName = jsonMessage.get("sender").getAsString();
                final UserSession sender = registry.getByName(senderName);
                final String sdpOffer = jsonMessage.get("sdpOffer").getAsString();
                user.receiveVideoFrom(sender, sdpOffer);
                break;
            }
            case "leaveRoom":
                leaveRoom(user);
                break;
            case "onIceCandidate":
                JsonObject candidate = jsonMessage.get("candidate").getAsJsonObject();

                if (user != null) {
                    IceCandidate cand = new IceCandidate(candidate.get("candidate").getAsString(),
                            candidate.get("sdpMid").getAsString(), candidate.get("sdpMLineIndex").getAsInt());
                    user.addCandidate(cand, jsonMessage.get("name").getAsString());
                }
                break;
            /**
             * recording
             */
            case "startRecording": {
                log.debug("********************startRecording");
                startRecording(session, jsonMessage);
                break;
            }
            case "stopRecording":
                if (user != null) {
                    log.debug("********************stopRecording");
                    user.stop();
                }
                break;
            case "stopPlayRecording":
                if (user != null) {
                    log.debug("********************stopPlayRecording");
//                    user.release();
                }
                break;
            case "playRecording":
                log.debug("********************playRecording");
                playRecording(user, session, jsonMessage);
            default:
                break;
        }
    }

    private void startRecording(final WebSocketSession session, JsonObject jsonMessage) {
        try {

            // 1. Media logic (webRtcEndpoint in loopback)
            MediaPipeline pipeline = kurento.createMediaPipeline();
            WebRtcEndpoint webRtcEndpoint = new WebRtcEndpoint.Builder(pipeline).build();
            webRtcEndpoint.connect(webRtcEndpoint);

            /**
             * 필요한 부분
             */

            MediaProfileSpecType profile = getMediaProfileFromMessage(jsonMessage);

            RecorderEndpoint recorder = new RecorderEndpoint.Builder(pipeline, RECORDER_FILE_PATH)
                    .withMediaProfile(profile).build();

            recorder.addRecordingListener(new EventListener<RecordingEvent>() {

                @Override
                public void onEvent(RecordingEvent event) {
                    JsonObject response = new JsonObject();
                    response.addProperty("id", "recording");
                    try {
                        synchronized (session) {
                            session.sendMessage(new TextMessage(response.toString()));
                        }
                    } catch (IOException e) {
                        log.error(e.getMessage());
                    }
                }

            });

            recorder.addStoppedListener(new EventListener<StoppedEvent>() {

                @Override
                public void onEvent(StoppedEvent event) {
                    JsonObject response = new JsonObject();
                    response.addProperty("id", "stopped");
                    try {
                        synchronized (session) {
                            session.sendMessage(new TextMessage(response.toString()));
                        }
                    } catch (IOException e) {
                        log.error(e.getMessage());
                    }
                }

            });

            recorder.addPausedListener(new EventListener<PausedEvent>() {

                @Override
                public void onEvent(PausedEvent event) {
                    JsonObject response = new JsonObject();
                    response.addProperty("id", "paused");
                    try {
                        synchronized (session) {
                            session.sendMessage(new TextMessage(response.toString()));
                        }
                    } catch (IOException e) {
                        log.error(e.getMessage());
                    }
                }

            });

            connectAccordingToProfile(webRtcEndpoint, recorder, profile);

            // 2. Store user session
            final UserSession user = registry.getBySession(session);
            user.setMediaPipeline(pipeline);
            user.setWebRtcEndpoint(webRtcEndpoint);
            user.setRecorderEndpoint(recorder);
            registry.register(user);

            // 3. SDP negotiation
            String sdpOffer = jsonMessage.get("sdpOffer").getAsString();
            String sdpAnswer = webRtcEndpoint.processOffer(sdpOffer);

            // 4. Gather ICE candidates
            webRtcEndpoint.addIceCandidateFoundListener(new EventListener<IceCandidateFoundEvent>() {

                @Override
                public void onEvent(IceCandidateFoundEvent event) {
                    JsonObject response = new JsonObject();
                    response.addProperty("id", "iceCandidate");
                    response.addProperty("name", user.getName());
                    response.add("candidate", JsonUtils.toJsonObject(event.getCandidate()));
                    try {
                        synchronized (session) {
                            session.sendMessage(new TextMessage(response.toString()));
                        }
                    } catch (IOException e) {
                        log.error(e.getMessage());
                    }
                }
            });

            JsonObject response = new JsonObject();
            response.addProperty("id", "startResponse");
            response.addProperty("name", user.getName());
            response.addProperty("sdpAnswer", sdpAnswer);

            synchronized (user) {
                session.sendMessage(new TextMessage(response.toString()));
            }

            webRtcEndpoint.gatherCandidates();

            recorder.record();
        } catch (Throwable t) {
            log.error("Start error", t);
            sendError(session, t.getMessage());
        }
    }

    private MediaProfileSpecType getMediaProfileFromMessage(JsonObject jsonMessage) {

        MediaProfileSpecType profile;
        switch (jsonMessage.get("mode").getAsString()) {
            case "audio-only":
                profile = MediaProfileSpecType.WEBM_AUDIO_ONLY;
                break;
            case "video-only":
                profile = MediaProfileSpecType.WEBM_VIDEO_ONLY;
                break;
            default:
                profile = MediaProfileSpecType.WEBM;
        }

        return profile;
    }

    private void playRecording(UserSession user, final WebSocketSession session, JsonObject jsonMessage) {
        try {

            // 1. Media logic
            final MediaPipeline pipeline = kurento.createMediaPipeline();
            WebRtcEndpoint webRtcEndpoint = new WebRtcEndpoint.Builder(pipeline).build();
            PlayerEndpoint player = new PlayerEndpoint.Builder(pipeline, RECORDER_FILE_PATH).build();
            player.connect(webRtcEndpoint);

            // Player listeners
            player.addErrorListener(new EventListener<ErrorEvent>() {
                @Override
                public void onEvent(ErrorEvent event) {
                    log.info("ErrorEvent for session '{}': {}", session.getId(), event.getDescription());
                    sendPlayEnd(session, pipeline);
                }
            });
            player.addEndOfStreamListener(new EventListener<EndOfStreamEvent>() {
                @Override
                public void onEvent(EndOfStreamEvent event) {
                    log.info("EndOfStreamEvent for session '{}'", session.getId());
                    sendPlayEnd(session, pipeline);
                }
            });

            // 2. Store user session
            user.setMediaPipeline(pipeline);
            user.setWebRtcEndpoint(webRtcEndpoint);

            // 3. SDP negotiation
            String sdpOffer = jsonMessage.get("sdpOffer").getAsString();
            String sdpAnswer = webRtcEndpoint.processOffer(sdpOffer);

            JsonObject response = new JsonObject();
            response.addProperty("id", "playResponse");
            response.addProperty("name", user.getName());
            response.addProperty("sdpAnswer", sdpAnswer);

            // 4. Gather ICE candidates
            webRtcEndpoint.addIceCandidateFoundListener(new EventListener<IceCandidateFoundEvent>() {

                @Override
                public void onEvent(IceCandidateFoundEvent event) {
                    JsonObject response = new JsonObject();
                    response.addProperty("id", "iceCandidate");
                    response.addProperty("name", user.getName());
                    response.add("candidate", JsonUtils.toJsonObject(event.getCandidate()));
                    try {
                        synchronized (session) {
                            session.sendMessage(new TextMessage(response.toString()));
                        }
                    } catch (IOException e) {
                        log.error(e.getMessage());
                    }
                }
            });

            // 5. Play recorded stream
            player.play();

            synchronized (session) {
                session.sendMessage(new TextMessage(response.toString()));
            }

            webRtcEndpoint.gatherCandidates();
        } catch (Throwable t) {
            log.error("Play error", t);
            sendError(session, t.getMessage());
        }
    }

    public void sendPlayEnd(WebSocketSession session, MediaPipeline pipeline) {
        try {
            JsonObject response = new JsonObject();
            response.addProperty("id", "playEnd");
            session.sendMessage(new TextMessage(response.toString()));
        } catch (IOException e) {
            log.error("Error sending playEndOfStream message", e);
        }
        // Release pipeline
        pipeline.release();
    }

    private void sendError(WebSocketSession session, String message) {
        try {
            JsonObject response = new JsonObject();
            response.addProperty("id", "error");
            response.addProperty("message", message);
            session.sendMessage(new TextMessage(response.toString()));
        } catch (IOException e) {
            log.error("Exception sending message", e);
        }
    }

    private void connectAccordingToProfile(WebRtcEndpoint webRtcEndpoint, RecorderEndpoint recorder,
                                           MediaProfileSpecType profile) {
        switch (profile) {
            case WEBM:
                webRtcEndpoint.connect(recorder, MediaType.AUDIO);
                webRtcEndpoint.connect(recorder, MediaType.VIDEO);
                break;
            case WEBM_AUDIO_ONLY:
                webRtcEndpoint.connect(recorder, MediaType.AUDIO);
                break;
            case WEBM_VIDEO_ONLY:
                webRtcEndpoint.connect(recorder, MediaType.VIDEO);
                break;
            default:
                throw new UnsupportedOperationException("Unsupported profile for this tutorial: " + profile);
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        UserSession user = registry.removeBySession(session);
        roomManager.getRoom(user.getRoomName()).leave(user);
    }

    private void joinRoom(JsonObject params, WebSocketSession session) throws IOException {
        final String roomName = params.get("room").getAsString();
        final String name = params.get("name").getAsString();
        log.info("PARTICIPANT {}: trying to join room {}", name, roomName);

        Room room = roomManager.getRoom(roomName);
        final UserSession user = room.join(name, session);
        registry.register(user);
    }

    private void leaveRoom(UserSession user) throws IOException {
        final Room room = roomManager.getRoom(user.getRoomName());
        room.leave(user);
        if (room.getParticipants().isEmpty()) {
            roomManager.removeRoom(room);
        }
    }
}
