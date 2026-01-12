package main.java.library.models;

import java.io.Serializable;

public abstract class LibraryItem implements Serializable {
    private static final long serialVersionUID = 1L;

    protected int id;
    protected String title;

    public abstract String getDisplayName();

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }
}
