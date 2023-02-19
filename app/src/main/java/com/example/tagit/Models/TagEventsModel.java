package com.example.tagit.Models;

public class TagEventsModel {
    String eventDate, eventTagName;
    String tagName, tagDescription, tagColor;

    public TagEventsModel(String eventDate, String eventTagName, String tagName, String tagDescription, String tagColor) {
        this.eventDate = eventDate;
        this.eventTagName = eventTagName;
        this.tagName = tagName;
        this.tagDescription = tagDescription;
        this.tagColor = tagColor;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public String getEventTagName() {
        return eventTagName;
    }

    public void setEventTagName(String eventTagName) {
        this.eventTagName = eventTagName;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public String getTagDescription() {
        return tagDescription;
    }

    public void setTagDescription(String tagDescription) {
        this.tagDescription = tagDescription;
    }

    public String getTagColor() {
        return tagColor;
    }

    public void setTagColor(String tagColor) {
        this.tagColor = tagColor;
    }
}
