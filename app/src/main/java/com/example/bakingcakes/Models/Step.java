package com.example.bakingcakes.Models;

import android.os.Parcel;
import android.os.Parcelable;

public class Step implements Parcelable {
    private int stepId;
    private String stepShortDescription;
    private String stepDescription;
    private String stepVideoUrl;
    private String stepThumbnailUrl;

    public Step(int stepId, String stepShortDescription, String stepDescription,
                String stepVideoUrl, String stepThumbnailUrl) {
        this.stepId = stepId;
        this.stepShortDescription = stepShortDescription;
        this.stepDescription = stepDescription;
        this.stepVideoUrl = stepVideoUrl;
        this.stepThumbnailUrl = stepThumbnailUrl;
    }

    private Step(Parcel in) {
        stepId = in.readInt();
        stepShortDescription = in.readString();
        stepDescription = in.readString();
        stepVideoUrl = in.readString();
        stepThumbnailUrl = in.readString();
    }

    public static final Creator<Step> CREATOR = new Creator<Step>() {
        @Override
        public Step createFromParcel(Parcel in) {
            return new Step(in);
        }

        @Override
        public Step[] newArray(int size) {
            return new Step[size];
        }
    };

    public int getStepId() {return stepId;}
    public String getStepShortDescription() {return stepShortDescription;}
    public String getStepDescription() {return stepDescription;}
    public String getStepVideoUrl() {return stepVideoUrl;}
    public String getStepThumbnailUrl() {return stepThumbnailUrl;}

    public void setStepId(int stepId) {
        this.stepId = stepId;
    }
    public void setStepShortDescription(String stepShortDescription) {
        this.stepShortDescription = stepShortDescription;
    }
    public void setStepDescription(String stepDescription) {
        this.stepDescription = stepDescription;
    }
    public void setStepVideoUrl(String stepVideoUrl) {
        this.stepVideoUrl = stepVideoUrl;
    }
    public void setStepThumbnailUrl(String stepThumbnailUrl) {
        this.stepThumbnailUrl = stepThumbnailUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(stepId);
        dest.writeString(stepShortDescription);
        dest.writeString(stepDescription);
        dest.writeString(stepVideoUrl);
        dest.writeString(stepThumbnailUrl);
    }
}
