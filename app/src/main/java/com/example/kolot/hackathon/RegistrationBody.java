package com.example.kolot.hackathon;

/**
 * Created by kolot on 29.11.2017.
 */

public class RegistrationBody {

    private String username;
    private String randomkey;
    private String vkid;
    private String facebookid;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRandomkey() {
        return randomkey;
    }

    public void setRandomkey(String randomkey) {
        this.randomkey = randomkey;
    }

    public String getVkid() {
        return vkid;
    }

    public void setVkid(String vkid) {
        this.vkid = vkid;
    }

    public String getFacebookid() {
        return facebookid;
    }

    public void setFacebookid(String facebookid) {
        this.facebookid = facebookid;
    }

    public String getInstlogin() {
        return instlogin;
    }

    public void setInstlogin(String instlogin) {
        this.instlogin = instlogin;
    }

    private String instlogin;
}
