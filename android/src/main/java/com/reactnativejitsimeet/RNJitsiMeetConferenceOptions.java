package com.reactnativejitsimeet;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import java.net.URL;


/**
 * This class represents the options when joining a Jitsi Meet conference. The user can create an
 * instance by using {@link RNJitsiMeetConferenceOptions.Builder} and setting the desired options
 * there.
 *
 * The resulting {@link RNJitsiMeetConferenceOptions} object is immutable and represents how the
 * conference will be joined.
 */
public class RNJitsiMeetConferenceOptions implements Parcelable {
    /**
     * Server where the conference should take place.
     */
    private URL serverURL;
    /**
     * Room name.
     */
    private String room;
    /**
     * Conference subject.
     */
    private String subject;
    /**
     * JWT token used for authentication.
     */
    private String token;

    // ✅ Wehago-Meet Conference Room Token
    private String roomToken;

    /**
     * Color scheme override, see: https://github.com/jitsi/jitsi-meet/blob/dbedee5e22e5dcf9c92db96ef5bb3c9982fc526d/react/features/base/color-scheme/defaultScheme.js
     */
    private Bundle colorScheme;

    /**
     * Feature flags. See: https://github.com/jitsi/jitsi-meet/blob/master/react/features/base/flags/constants.js
     */
    private Bundle featureFlags;

    /**
     * Set to {@code true} to join the conference with audio / video muted or to start in audio
     * only mode respectively.
     */
    private Boolean audioMuted;
    private Boolean audioOnly;
    private Boolean videoMuted;

    /**
     * USer information, to be used when no token is specified.
     */
    private RNJitsiMeetUserInfo userInfo;

    /**
     * Class used to build the immutable {@link RNJitsiMeetConferenceOptions} object.
     */
    public static class Builder {
        private URL serverURL;
        private String room;
        private String subject;
        private String token;

        private String roomToken;

        private Bundle colorScheme;
        private Bundle featureFlags;

        private Boolean audioMuted;
        private Boolean audioOnly;
        private Boolean videoMuted;

        private RNJitsiMeetUserInfo userInfo;

        public Builder() {
            featureFlags = new Bundle();
        }

        /**\
         * Sets the server URL.
         * @param url - {@link URL} of the server where the conference should take place.
         * @return - The {@link Builder} object itself so the method calls can be chained.
         */
        public Builder setServerURL(URL url) {
            this.serverURL = url;

            return this;
        }

        /**
         * Sets the room where the conference will take place.
         * @param room - Name of the room.
         * @return - The {@link Builder} object itself so the method calls can be chained.
         */
        public Builder setRoom(String room) {
            this.room = room;

            return this;
        }

        /**
         * Sets the conference subject.
         * @param subject - Subject for the conference.
         * @return - The {@link Builder} object itself so the method calls can be chained.
         */
        public Builder setSubject(String subject) {
            this.subject = subject;

            return this;
        }

        /**
         * Sets the JWT token to be used for authentication when joining a conference.
         * @param token - The JWT token to be used for authentication.
         * @return - The {@link Builder} object itself so the method calls can be chained.
         */
        public Builder setToken(String token) {
            this.token = token;

            return this;
        }

        public Builder setRoomToken(String roomToken) {
            this.roomToken = roomToken;

            return this;
        }

        /**
         * Sets the color scheme override so the app is themed. See:
         * https://github.com/jitsi/jitsi-meet/blob/master/react/features/base/color-scheme/defaultScheme.js
         * for the structure.
         * @param colorScheme - A color scheme to be applied to the app.
         * @return - The {@link Builder} object itself so the method calls can be chained.
         */
        public Builder setColorScheme(Bundle colorScheme) {
            this.colorScheme = colorScheme;

            return this;
        }

        /**
         * Indicates the conference will be joined with the microphone muted.
         * @param muted - Muted indication.
         * @return - The {@link Builder} object itself so the method calls can be chained.
         */
        public Builder setAudioMuted(boolean muted) {
            this.audioMuted = muted;

            return this;
        }

        /**
         * Indicates the conference will be joined in audio-only mode. In this mode no video is
         * sent or received.
         * @param audioOnly - Audio-mode indicator.
         * @return - The {@link Builder} object itself so the method calls can be chained.
         */
        public Builder setAudioOnly(boolean audioOnly) {
            this.audioOnly = audioOnly;

            return this;
        }
        /**
         * Indicates the conference will be joined with the camera muted.
         * @param videoMuted - Muted indication.
         * @return - The {@link Builder} object itself so the method calls can be chained.
         */
        public Builder setVideoMuted(boolean videoMuted) {
            this.videoMuted = videoMuted;

            return this;
        }

        /**
         * Sets the welcome page enabled / disabled. The welcome page lists recent meetings and
         * calendar appointments and it's meant to be used by standalone applications. Defaults to
         * false.
         * @param enabled - Whether the welcome page should be enabled or not.
         * @return - The {@link Builder} object itself so the method calls can be chained.
         */
        public Builder setWelcomePageEnabled(boolean enabled) {
            this.featureFlags.putBoolean("welcomepage.enabled", enabled);

            return this;
        }

        public Builder setFeatureFlag(String flag, boolean value) {
            this.featureFlags.putBoolean(flag, value);

            return this;
        }

        public Builder setFeatureFlag(String flag, String value) {
            this.featureFlags.putString(flag, value);

            return this;
        }

        public Builder setFeatureFlag(String flag, int value) {
            this.featureFlags.putInt(flag, value);

            return this;
        }

        public Builder setUserInfo(RNJitsiMeetUserInfo userInfo) {
            this.userInfo = userInfo;

            return this;
        }

        /**
         * Builds the immutable {@link RNJitsiMeetConferenceOptions} object with the configuration
         * that this {@link Builder} instance specified.
         * @return - The built {@link RNJitsiMeetConferenceOptions} object.
         */
        public RNJitsiMeetConferenceOptions build() {
            RNJitsiMeetConferenceOptions options = new RNJitsiMeetConferenceOptions();

            options.serverURL = this.serverURL;
            options.room = this.room;
            options.subject = this.subject;
            options.token = this.token;

            options.roomToken = this.roomToken;

            options.colorScheme = this.colorScheme;
            options.featureFlags = this.featureFlags;
            options.audioMuted = this.audioMuted;
            options.audioOnly = this.audioOnly;
            options.videoMuted = this.videoMuted;
            options.userInfo = this.userInfo;

            return options;
        }
    }

    private RNJitsiMeetConferenceOptions() {
    }

    private RNJitsiMeetConferenceOptions(Parcel in) {
        room = in.readString();
        subject = in.readString();
        token = in.readString();

        roomToken = in.readString();

        colorScheme = in.readBundle();
        featureFlags = in.readBundle();
        userInfo = new RNJitsiMeetUserInfo(in.readBundle());
        byte tmpAudioMuted = in.readByte();
        audioMuted = tmpAudioMuted == 0 ? null : tmpAudioMuted == 1;
        byte tmpAudioOnly = in.readByte();
        audioOnly = tmpAudioOnly == 0 ? null : tmpAudioOnly == 1;
        byte tmpVideoMuted = in.readByte();
        videoMuted = tmpVideoMuted == 0 ? null : tmpVideoMuted == 1;
    }

    Bundle asProps() {
        Bundle props = new Bundle();

        // Android always has the PiP flag set by default.
        if (!featureFlags.containsKey("pip.enabled")) {
            featureFlags.putBoolean("pip.enabled", true);
        }

        props.putBundle("flags", featureFlags);

        if (colorScheme != null) {
            props.putBundle("colorScheme", colorScheme);
        }

        Bundle config = new Bundle();

        if (audioMuted != null) {
            config.putBoolean("startWithAudioMuted", audioMuted);
        }
        if (audioOnly != null) {
            config.putBoolean("startAudioOnly", audioOnly);
        }
        if (videoMuted != null) {
            config.putBoolean("startWithVideoMuted", videoMuted);
        }
        if (subject != null) {
            config.putString("subject", subject);
        }

        Bundle urlProps = new Bundle();

        // The room is fully qualified
        if (room != null && room.contains("://")) {
            urlProps.putString("url", room);
        } else {
            if (serverURL != null) {
                urlProps.putString("serverURL", serverURL.toString());
            }
            if (room != null) {
                urlProps.putString("room", room);
            }
        }

        if (token != null) {
            urlProps.putString("jwt", token);
        }

        if (token == null && userInfo != null) {
            props.putBundle("userInfo", userInfo.asBundle());
        }

        urlProps.putBundle("config", config);
        props.putBundle("url", urlProps);

        return props;
    }

    // Parcelable interface
    //

    public static final Creator<RNJitsiMeetConferenceOptions> CREATOR = new Creator<RNJitsiMeetConferenceOptions>() {
        @Override
        public RNJitsiMeetConferenceOptions createFromParcel(Parcel in) {
            return new RNJitsiMeetConferenceOptions(in);
        }

        @Override
        public RNJitsiMeetConferenceOptions[] newArray(int size) {
            return new RNJitsiMeetConferenceOptions[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(room);
        dest.writeString(subject);
        dest.writeString(token);
        dest.writeString(roomToken);
        dest.writeBundle(colorScheme);
        dest.writeBundle(featureFlags);
        dest.writeBundle(userInfo != null ? userInfo.asBundle() : new Bundle());
        dest.writeByte((byte) (audioMuted == null ? 0 : audioMuted ? 1 : 2));
        dest.writeByte((byte) (audioOnly == null ? 0 : audioOnly ? 1 : 2));
        dest.writeByte((byte) (videoMuted == null ? 0 : videoMuted ? 1 : 2));
    }

    @Override
    public int describeContents() {
        return 0;
    }
}
