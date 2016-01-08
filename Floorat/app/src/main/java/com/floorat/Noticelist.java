package com.floorat;

public class Noticelist {

    public String heading;
    public String url;
    public Noticelist(String heading, String url) {
        super();
        this.heading = heading;
        this.url = url;
    }
    public String getHeading() {
        return heading;
    }
    public void setHeading(String heading) {
        this.heading = heading;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
}
