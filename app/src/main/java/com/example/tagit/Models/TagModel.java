package com.example.tagit.Models;

import java.io.Serializable;

public class TagModel implements Serializable {
    String tagName, tagDescription, tagColor;

    public TagModel(String tagName, String tagDescription, String tagColor) {
        this.tagName = tagName;
        this.tagDescription = tagDescription;
        this.tagColor = tagColor;
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
