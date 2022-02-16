//package com.ssafy.config.handler;
//
//import com.google.gson.Gson;
//import com.google.gson.GsonBuilder;
//import com.google.gson.JsonObject;
//import com.ssafy.config.webrtc.UserRegistry;
//import com.ssafy.config.webrtc.UserSession;
//import org.kurento.client.*;
//import org.kurento.jsonrpc.JsonUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.socket.CloseStatus;
//import org.springframework.web.socket.TextMessage;
//import org.springframework.web.socket.WebSocketSession;
//import org.springframework.web.socket.handler.TextWebSocketHandler;
//
//import java.io.IOException;
//
//public class RecHandler extends TextWebSocketHandler {
//
//
//    private final Logger log = LoggerFactory.getLogger(RecHandler.class);
//    private static final Gson gson = new GsonBuilder().create();
//
//    @Autowired
//    private UserRegistry registry;
//
//    @Autowired
//    private KurentoClient kurento;
//
//    @Override
//    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
//        JsonObject jsonMessage = gson.fromJson(message.getPayload(), JsonObject.class);
//
//        log.debug("Incoming message: {}", jsonMessage);
//
//        UserSession user = registry.getBySession(session);
//        if (user != null) {
//            log.debug("Incoming message from user '{}': {}", user.getId(), jsonMessage);
//        } else {
//            log.debug("Incoming message from new user: {}", jsonMessage);
//        }
//
//        switch (jsonMessage.get("id").getAsString()) {
//            case "start":
//                start(session, jsonMessage);
//                break;
//            case "stop":
//                if (user != null) {
//                    user.stop();
//                }
//            case "stopPlay":
//                if (user != null) {
//                    user.release();
//                }
//                break;
//            case "play":
//                play(user, session, jsonMessage);
//                break;
//            case "onIceCandidate": {
//                JsonObject jsonCandidate = jsonMessage.get("candidate").getAsJsonObject();
//
//                if (user != null) {
//                    IceCandidate candidate = new IceCandidate(jsonCandidate.get("candidate").getAsString(),
//                            jsonCandidate.get("sdpMid").getAsString(),
//                            jsonCandidate.get("sdpMLineIndex").getAsInt());
//                    user.addCandidate(candidate, jsonMessage.get("name").getAsString());
//                }
//                break;
//            }
//            default:
//                sendError(session, "Invalid message with id " + jsonMessage.get("id").getAsString());
//                break;
//        }
//    }
//
//    @Override
//    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
//        super.afterConnectionClosed(session, status);
//        registry.removeBySession(session);
//    }
//
//    private void start(final WebSocketSession session, JsonObject jsonMessage) {
//        try {
//
//            // 1. Media logic (webRtcEndpoint in loopback)
//            MediaPipeline pipeline = kurento.createMediaPipeline();
//            WebRtcEndpoint webRtcEndpoint = new WebRtcEndpoint.Builder(pipeline).build();
//            webRtcEndpoint.connect(webRtcEndpoint);
//
//            /**
//             * 필요한 부분
//             */
//
//            MediaProfileSpecType profile = getMediaProfileFromMessage(jsonMessage);
//
//            RecorderEndpoint recorder = new RecorderEndpoint.Builder(pipeline, RECORDER_FILE_PATH)
//                    .withMediaProfile(profile).build();
//
//            recorder.addRecordingListener(new EventListener<RecordingEvent>() {
//
//                @Override
//                public void onEvent(RecordingEvent event) {
//                    JsonObject response = new JsonObject();
//                    response.addProperty("id", "recording");
//                    try {
//                        synchronized (session) {
//                            session.sendMessage(new TextMessage(response.toString()));
//                        }
//                    } catch (IOException e) {
//                        log.error(e.getMessage());
//                    }
//                }
//
//            });
//
//            recorder.addStoppedListener(new EventListener<StoppedEvent>() {
//
//                @Override
//                public void onEvent(StoppedEvent event) {
//                    JsonObject response = new JsonObject();
//                    response.addProperty("id", "stopped");
//                    try {
//                        synchronized (session) {
//                            session.sendMessage(new TextMessage(response.toString()));
//                        }
//                    } catch (IOException e) {
//                        log.error(e.getMessage());
//                    }
//                }
//
//            });
//
//            recorder.addPausedListener(new EventListener<PausedEvent>() {
//
//                @Override
//                public void onEvent(PausedEvent event) {
//                    JsonObject response = new JsonObject();
//                    response.addProperty("id", "paused");
//                    try {
//                        synchronized (session) {
//                            session.sendMessage(new TextMessage(response.toString()));
//                        }
//                    } catch (IOException e) {
//                        log.error(e.getMessage());
//                    }
//                }
//
//            });
//
//            /**
//             * 필요
//             */
//            connectAccordingToProfile(webRtcEndpoint, recorder, profile);
//
//            /**
//             * 필요 없는 부분
//             */
//            // 2. Store user session
//            final UserSession user = registry.getBySession(session);
//            user.setMediaPipeline(pipeline);
//            user.setWebRtcEndpoint(webRtcEndpoint);
//            user.setRecorderEndpoint(recorder);
//            registry.register(user);
//
//            // 3. SDP negotiation
//            String sdpOffer = jsonMessage.get("sdpOffer").getAsString();
//            String sdpAnswer = webRtcEndpoint.processOffer(sdpOffer);
//
//            // 4. Gather ICE candidates
//            webRtcEndpoint.addIceCandidateFoundListener(new EventListener<IceCandidateFoundEvent>() {
//
//                @Override
//                public void onEvent(IceCandidateFoundEvent event) {
//                    JsonObject response = new JsonObject();
//                    response.addProperty("id", "iceCandidate");
//                    response.add("candidate", JsonUtils.toJsonObject(event.getCandidate()));
//                    try {
//                        synchronized (session) {
//                            session.sendMessage(new TextMessage(response.toString()));
//                        }
//                    } catch (IOException e) {
//                        log.error(e.getMessage());
//                    }
//                }
//            });
//
//            JsonObject response = new JsonObject();
//            response.addProperty("id", "startResponse");
//            response.addProperty("sdpAnswer", sdpAnswer);
//
//            synchronized (user) {
//                session.sendMessage(new TextMessage(response.toString()));
//            }
//
//            webRtcEndpoint.gatherCandidates();
//
//            recorder.record();
//        } catch (Throwable t) {
//            log.error("Start error", t);
//            sendError(session, t.getMessage());
//        }
//    }
//
//    private MediaProfileSpecType getMediaProfileFromMessage(JsonObject jsonMessage) {
//
//        MediaProfileSpecType profile;
//        switch (jsonMessage.get("mode").getAsString()) {
//            case "audio-only":
//                profile = MediaProfileSpecType.WEBM_AUDIO_ONLY;
//                break;
//            case "video-only":
//                profile = MediaProfileSpecType.WEBM_VIDEO_ONLY;
//                break;
//            default:
//                profile = MediaProfileSpecType.WEBM;
//        }
//
//        return profile;
//    }
//
//    private void connectAccordingToProfile(WebRtcEndpoint webRtcEndpoint, RecorderEndpoint recorder,
//                                           MediaProfileSpecType profile) {
//        switch (profile) {
//            case WEBM:
//                webRtcEndpoint.connect(recorder, MediaType.AUDIO);
//                webRtcEndpoint.connect(recorder, MediaType.VIDEO);
//                break;
//            case WEBM_AUDIO_ONLY:
//                webRtcEndpoint.connect(recorder, MediaType.AUDIO);
//                break;
//            case WEBM_VIDEO_ONLY:
//                webRtcEndpoint.connect(recorder, MediaType.VIDEO);
//                break;
//            default:
//                throw new UnsupportedOperationException("Unsupported profile for this tutorial: " + profile);
//        }
//    }
//
//    private void play(UserSession user, final WebSocketSession session, JsonObject jsonMessage) {
//        try {
//
//            // 1. Media logic
//            final MediaPipeline pipeline = kurento.createMediaPipeline();
//            WebRtcEndpoint webRtcEndpoint = new WebRtcEndpoint.Builder(pipeline).build();
//            PlayerEndpoint player = new PlayerEndpoint.Builder(pipeline, RECORDER_FILE_PATH).build();
//            player.connect(webRtcEndpoint);
//
//            // Player listeners
//            player.addErrorListener(new EventListener<ErrorEvent>() {
//                @Override
//                public void onEvent(ErrorEvent event) {
//                    log.info("ErrorEvent for session '{}': {}", session.getId(), event.getDescription());
//                    sendPlayEnd(session, pipeline);
//                }
//            });
//            player.addEndOfStreamListener(new EventListener<EndOfStreamEvent>() {
//                @Override
//                public void onEvent(EndOfStreamEvent event) {
//                    log.info("EndOfStreamEvent for session '{}'", session.getId());
//                    sendPlayEnd(session, pipeline);
//                }
//            });
//
//            // 2. Store user session
//            user.setMediaPipeline(pipeline);
//            user.setWebRtcEndpoint(webRtcEndpoint);
//
//            // 3. SDP negotiation
//            String sdpOffer = jsonMessage.get("sdpOffer").getAsString();
//            String sdpAnswer = webRtcEndpoint.processOffer(sdpOffer);
//
//            JsonObject response = new JsonObject();
//            response.addProperty("id", "playResponse");
//            response.addProperty("sdpAnswer", sdpAnswer);
//
//            // 4. Gather ICE candidates
//            webRtcEndpoint.addIceCandidateFoundListener(new EventListener<IceCandidateFoundEvent>() {
//
//                @Override
//                public void onEvent(IceCandidateFoundEvent event) {
//                    JsonObject response = new JsonObject();
//                    response.addProperty("id", "iceCandidate");
//                    response.add("candidate", JsonUtils.toJsonObject(event.getCandidate()));
//                    try {
//                        synchronized (session) {
//                            session.sendMessage(new TextMessage(response.toString()));
//                        }
//                    } catch (IOException e) {
//                        log.error(e.getMessage());
//                    }
//                }
//            });
//
//            // 5. Play recorded stream
//            player.play();
//
//            synchronized (session) {
//                session.sendMessage(new TextMessage(response.toString()));
//            }
//
//            webRtcEndpoint.gatherCandidates();
//        } catch (Throwable t) {
//            log.error("Play error", t);
//            sendError(session, t.getMessage());
//        }
//    }
//
//    public void sendPlayEnd(WebSocketSession session, MediaPipeline pipeline) {
//        try {
//            JsonObject response = new JsonObject();
//            response.addProperty("id", "playEnd");
//            session.sendMessage(new TextMessage(response.toString()));
//        } catch (IOException e) {
//            log.error("Error sending playEndOfStream message", e);
//        }
//        // Release pipeline
//        pipeline.release();
//    }
//
//    private void sendError(WebSocketSession session, String message) {
//        try {
//            JsonObject response = new JsonObject();
//            response.addProperty("id", "error");
//            response.addProperty("message", message);
//            session.sendMessage(new TextMessage(response.toString()));
//        } catch (IOException e) {
//            log.error("Exception sending message", e);
//        }
//    }
//}
