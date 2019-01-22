package com.example.bakingcakes.Models;
public class Step {
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
}
