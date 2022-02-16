package com.ssafy.config.webrtc;

import java.io.Closeable;
import java.io.IOException;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import lombok.RequiredArgsConstructor;
import org.kurento.client.*;
import org.kurento.jsonrpc.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import com.google.gson.JsonObject;

@RequiredArgsConstructor
public class UserSession implements Closeable {
    private static final String RECORDER_FILE_PATH = "file:///tmp/HelloWorldRecorded.webm";

    private static final Logger log = LoggerFactory.getLogger(UserSession.class);

    private String name = "";
    private WebSocketSession session = null;

    private MediaPipeline pipeline;

    private String roomName;
    private WebRtcEndpoint outgoingMedia;
    private final ConcurrentMap<String, WebRtcEndpoint> incomingMedia = new ConcurrentHashMap<>();

//    private String id;
//    private MediaPipeline mediaPipeline;
//    private WebRtcEndpoint webRtcEndpoint;
    private RecorderEndpoint recorderEndpoint;
    private Date stopTimestamp;

    public UserSession(String name, String roomName, WebSocketSession session,
                       MediaPipeline pipeline) {

        this.pipeline = pipeline;
        this.name = name;
        this.session = session;
        this.roomName = roomName;
        this.outgoingMedia = new WebRtcEndpoint.Builder(pipeline).build();

        this.outgoingMedia.addIceCandidateFoundListener(new EventListener<IceCandidateFoundEvent>() {

            @Override
            public void onEvent(IceCandidateFoundEvent event) {
                JsonObject response = new JsonObject();
                response.addProperty("id", "iceCandidate");
                response.addProperty("name", name);
                response.add("candidate", JsonUtils.toJsonObject(event.getCandidate()));
                try {
                    synchronized (session) {
                        session.sendMessage(new TextMessage(response.toString()));
                    }
                } catch (IOException e) {
                    log.debug(e.getMessage());
                }
            }
        });
    }

//    public String getId() {
//        return id;
//    }
//
//    public void setId(String id) {
//        this.id = id;
//    }

    public WebRtcEndpoint getWebRtcEndpoint() {
        return outgoingMedia;
    }

    public void setWebRtcEndpoint(WebRtcEndpoint webRtcEndpoint) {
        this.outgoingMedia = webRtcEndpoint;
    }

    public MediaPipeline getMediaPipeline() {
        return pipeline;
    }

    public void setMediaPipeline(MediaPipeline mediaPipeline) {
        this.pipeline = mediaPipeline;
    }

    public void setRecorderEndpoint(RecorderEndpoint recorderEndpoint) {
        this.recorderEndpoint = recorderEndpoint;
    }

    public WebRtcEndpoint getOutgoingWebRtcPeer() {
        return outgoingMedia;
    }

    public String getName() {
        return name;
    }

    public WebSocketSession getSession() {
        return session;
    }

    /**
     * The room to which the user is currently attending.
     *
     * @return The room
     */
    public String getRoomName() {
        return this.roomName;
    }

    public void receiveVideoFrom(UserSession sender, String sdpOffer) throws IOException {
        log.info("USER {}: connecting with {} in room {}", this.name, sender.getName(), this.roomName);

        log.trace("USER {}: SdpOffer for {} is {}", this.name, sender.getName(), sdpOffer);

        final String ipSdpAnswer = this.getEndpointForUser(sender).processOffer(sdpOffer);
        final JsonObject scParams = new JsonObject();
        scParams.addProperty("id", "receiveVideoAnswer");
        scParams.addProperty("name", sender.getName());
        scParams.addProperty("sdpAnswer", ipSdpAnswer);

        log.trace("USER {}: SdpAnswer for {} is {}", this.name, sender.getName(), ipSdpAnswer);
        this.sendMessage(scParams);
        log.debug("gather candidates");
        this.getEndpointForUser(sender).gatherCandidates();
    }

    private WebRtcEndpoint getEndpointForUser(final UserSession sender) {
        if (sender.getName().equals(name)) {
            log.debug("PARTICIPANT {}: configuring loopback", this.name);
            return outgoingMedia;
        }

        log.debug("PARTICIPANT {}: receiving video from {}", this.name, sender.getName());

        WebRtcEndpoint incoming = incomingMedia.get(sender.getName());
        if (incoming == null) {
            log.debug("PARTICIPANT {}: creating new endpoint for {}", this.name, sender.getName());
            incoming = new WebRtcEndpoint.Builder(pipeline).build();

            incoming.addIceCandidateFoundListener(new EventListener<IceCandidateFoundEvent>() {

                @Override
                public void onEvent(IceCandidateFoundEvent event) {
                    JsonObject response = new JsonObject();
                    response.addProperty("id", "iceCandidate");
                    response.addProperty("name", sender.getName());
                    response.add("candidate", JsonUtils.toJsonObject(event.getCandidate()));
                    try {
                        synchronized (session) {
                            session.sendMessage(new TextMessage(response.toString()));
                        }
                    } catch (IOException e) {
                        log.debug(e.getMessage());
                    }
                }
            });

            incomingMedia.put(sender.getName(), incoming);
        }

        log.debug("PARTICIPANT {}: obtained endpoint for {}", this.name, sender.getName());
        sender.getOutgoingWebRtcPeer().connect(incoming);

        return incoming;
    }

    public void cancelVideoFrom(final UserSession sender) {
        this.cancelVideoFrom(sender.getName());
    }

    public void cancelVideoFrom(final String senderName) {
        log.debug("PARTICIPANT {}: canceling video reception from {}", this.name, senderName);
        final WebRtcEndpoint incoming = incomingMedia.remove(senderName);

        log.debug("PARTICIPANT {}: removing endpoint for {}", this.name, senderName);
        incoming.release(new Continuation<Void>() {
            @Override
            public void onSuccess(Void result) throws Exception {
                log.trace("PARTICIPANT {}: Released successfully incoming EP for {}",
                        UserSession.this.name, senderName);
            }

            @Override
            public void onError(Throwable cause) throws Exception {
                log.warn("PARTICIPANT {}: Could not release incoming EP for {}", UserSession.this.name,
                        senderName);
            }
        });
    }

    /**
     * startRecording
     * @param jsonMessage
     */

    public void startRecording(UserSession sender, String sdpOffer, JsonObject jsonMessage) {
        try {
//            MediaPipeline pipeline = kurento.createMediaPipeline();
//            webRtcEndpoint = this.getEndpointForUser(sender);
            outgoingMedia = new WebRtcEndpoint.Builder(pipeline).build();
            outgoingMedia.connect(outgoingMedia);

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

            connectAccordingToProfile(outgoingMedia, recorder, profile);

            // 3. SDP negotiation
            String sdpAnswer = outgoingMedia.processOffer(sdpOffer);

            // 4. Gather ICE candidates
            outgoingMedia.addIceCandidateFoundListener(new EventListener<IceCandidateFoundEvent>() {

                @Override
                public void onEvent(IceCandidateFoundEvent event) {
                    JsonObject response = new JsonObject();
                    response.addProperty("id", "iceCandidate");
                    response.addProperty("name", name);
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
            response.addProperty("name", sender.getName());
            response.addProperty("sdpAnswer", sdpAnswer);

            this.sendMessage(response);

            outgoingMedia.gatherCandidates();

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

    /**
     * play recording
     * @param sender
     * @param session
     * @param jsonMessage
     */
    public void playRecording(UserSession sender, final WebSocketSession session, JsonObject jsonMessage) {
        try {
//            String sdpOffer = jsonMessage.get("sdpOffer").getAsString();
//            String sdpAnswer = this.getEndpointForUser(sender).processAnswer(sdpOffer);

//            final MediaPipeline pipeline = kurento.createMediaPipeline();
//            WebRtcEndpoint webRtcEndpoint = this.getEndpointForUser(sender);
            outgoingMedia = new WebRtcEndpoint.Builder(pipeline).build();
            PlayerEndpoint player = new PlayerEndpoint.Builder(pipeline, RECORDER_FILE_PATH).build();
            player.connect(outgoingMedia);

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

            // 3. SDP negotiation
            String sdpOffer = jsonMessage.get("sdpOffer").getAsString();
            String sdpAnswer = outgoingMedia.processOffer(sdpOffer);

            JsonObject response = new JsonObject();
            response.addProperty("id", "playResponse");
            response.addProperty("name", sender.getName());
            response.addProperty("sdpAnswer", sdpAnswer);

            // 4. Gather ICE candidates
            outgoingMedia.addIceCandidateFoundListener(new EventListener<IceCandidateFoundEvent>() {

                @Override
                public void onEvent(IceCandidateFoundEvent event) {
                    JsonObject response = new JsonObject();
                    response.addProperty("id", "iceCandidate");
                    response.addProperty("name", name);
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

            this.sendMessage(response);

            outgoingMedia.gatherCandidates();
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

    public void stop() {
        if (recorderEndpoint != null) {
            final CountDownLatch stoppedCountDown = new CountDownLatch(1);
            ListenerSubscription subscriptionId = recorderEndpoint
                    .addStoppedListener(new EventListener<StoppedEvent>() {

                        @Override
                        public void onEvent(StoppedEvent event) {
                            stoppedCountDown.countDown();
                        }
                    });
            recorderEndpoint.stop();
            try {
                if (!stoppedCountDown.await(5, TimeUnit.SECONDS)) {
                    log.error("Error waiting for recorder to stop");
                }
            } catch (InterruptedException e) {
                log.error("Exception while waiting for state change", e);
            }
            recorderEndpoint.removeStoppedListener(subscriptionId);
//            this.webRtcEndpoint = null;
        }
    }

//    public void release() {
//        this.mediaPipeline.release();
//        this.webRtcEndpoint = null;
//        this.mediaPipeline = null;
//        if (this.stopTimestamp == null) {
//            this.stopTimestamp = new Date();
//        }
//    }

    @Override
    public void close() throws IOException {
        log.debug("PARTICIPANT {}: Releasing resources", this.name);
        for (final String remoteParticipantName : incomingMedia.keySet()) {

            log.trace("PARTICIPANT {}: Released incoming EP for {}", this.name, remoteParticipantName);

            final WebRtcEndpoint ep = this.incomingMedia.get(remoteParticipantName);

            ep.release(new Continuation<Void>() {

                @Override
                public void onSuccess(Void result) throws Exception {
                    log.trace("PARTICIPANT {}: Released successfully incoming EP for {}",
                            UserSession.this.name, remoteParticipantName);
                }

                @Override
                public void onError(Throwable cause) throws Exception {
                    log.warn("PARTICIPANT {}: Could not release incoming EP for {}", UserSession.this.name,
                            remoteParticipantName);
                }
            });
        }

        outgoingMedia.release(new Continuation<Void>() {

            @Override
            public void onSuccess(Void result) throws Exception {
                log.trace("PARTICIPANT {}: Released outgoing EP", UserSession.this.name);
            }

            @Override
            public void onError(Throwable cause) throws Exception {
                log.warn("USER {}: Could not release outgoing EP", UserSession.this.name);
            }
        });
    }

    public void sendMessage(JsonObject message) throws IOException {
        log.debug("USER {}: Sending message {}", name, message);
        synchronized (session) {
            session.sendMessage(new TextMessage(message.toString()));
        }
    }

    public void addCandidate(IceCandidate candidate, String name) {
//        switch (type) {
//            case "origin": {
                if (this.name.compareTo(name) == 0) {
                    outgoingMedia.addIceCandidate(candidate);
                } else {
                    WebRtcEndpoint webRtc = incomingMedia.get(name);
                    if (webRtc != null) {
                        webRtc.addIceCandidate(candidate);
                    }
                }
//                break;
//            }
//            case "record": {
//                if (this.name.compareTo(name) == 0) {
//                    webRtcEndpoint.addIceCandidate(candidate);
//                } else {
//                    WebRtcEndpoint webRtc = webRtcEndpoint;
//                    if (webRtc != null) {
//                        webRtc.addIceCandidate(candidate);
//                    }
//                }
//            }
//        }
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {

        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof UserSession)) {
            return false;
        }
        UserSession other = (UserSession) obj;
        boolean eq = name.equals(other.name);
        eq &= roomName.equals(other.roomName);
        return eq;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        int result = 1;
        result = 31 * result + name.hashCode();
        result = 31 * result + roomName.hashCode();
        return result;
    }
}
