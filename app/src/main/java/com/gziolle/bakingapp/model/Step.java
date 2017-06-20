package com.gziolle.bakingapp.model;

/**
 * BakingApp
 * Created by Guilherme Ziolle on 20/06/2017.
 * gziolle@gmail.com
 */

public class Step {
    private long mId;
    private String mShortDescription;
    private String mDescription;
    private String mVideoUrl;
    private String mThumbnailUrl;

    public Step(long id, String shortDescription, String description, String videoUrl, String thumbnailUrl) {
        this.mId = id;
        this.mShortDescription = shortDescription;
        this.mDescription = description;
        this.mVideoUrl = videoUrl;
        this.mThumbnailUrl = thumbnailUrl;
    }

    public long getId() {
        return mId;
    }

    public String getShortDescription() {
        return mShortDescription;
    }

    public String getDescription() {
        return mDescription;
    }

    public String getVideoUrl() {
        return mVideoUrl;
    }

    public String getThumbnailUrl() {
        return mThumbnailUrl;
    }
}
