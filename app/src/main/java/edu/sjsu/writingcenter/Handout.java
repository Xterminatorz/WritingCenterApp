package edu.sjsu.writingcenter;

public class Handout {
    String title;
    String url;
    Handout(String title, String url) {
        this.title = title;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }
}
