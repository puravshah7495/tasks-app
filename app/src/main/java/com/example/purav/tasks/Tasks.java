package com.example.purav.tasks;

/**
 * Created by Purav on 12/29/2014.
 */
public class Tasks {
    private String note;
    private String title;
    private long id;

    public Tasks(String note, String title, long id) {
        this.title = title;
        this.note = note;
        this.id = id;
    }

    public Tasks() {}

    @Override
    public String toString() {
        return title;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
