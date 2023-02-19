package com.example.tagit.Models;

public class EventsModel {

    String eventDate, eventTagName;

    public EventsModel(String eventDate, String eventTagName) {
        this.eventDate = eventDate;
        this.eventTagName = eventTagName;
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
}
