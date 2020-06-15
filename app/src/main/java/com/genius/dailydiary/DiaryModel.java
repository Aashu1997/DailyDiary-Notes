package com.genius.dailydiary;

public class DiaryModel {

    String content,timestap,title;
    String contentId;

    public DiaryModel() {
    }

    public DiaryModel(String content, String timestap, String title) {
        this.content = content;
        this.timestap = timestap;
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTimestap() {
        return timestap;
    }

    public void setTimestap(String timestap) {
        this.timestap = timestap;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContentId() {
        return contentId;
    }

    public void setContentId(String contentId) {
        this.contentId = contentId;
    }
}
