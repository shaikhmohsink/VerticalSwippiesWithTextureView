package com.mohsin.model;

import com.google.android.libraries.mediaframework.exoplayerextensions.Video;

/**
 * Simple class to bundle together the title, content video, and ad tag associated with a video.
 */
public class VideoListItem {

    /**
     * The title of the video.
     */
    public final String title;

    /**
     * The actual content video (contains its URL, media type - either DASH or mp4,
     * and an optional media type).
     */
    public final Video video;

    /**
     * The URL of the VAST document which represents the ad.
     */
    public final String adUrl;

    /**
     * @param title The title of the video.
     * @param video The actual content video (contains its URL, media type - either DASH or mp4,
     *                  and an optional media type).
     * @param adUrl The URL of the VAST document which represents the ad.
     */
    public VideoListItem(String title, Video video, String adUrl) {
        this.title = title;
        this.video = video;
        this.adUrl = adUrl;
    }
}
