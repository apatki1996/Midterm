package com.example.apatki.midterm;

import java.util.Date;

public class Track {

    private String track, artist, album, url;
    private Date date;

    public Track () {

    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTrack() {
        return track;
    }

    public Date getDate() {
        return date;
    }

    public String getArtist() {
        return artist;
    }

    public String getAlbum() {
        return album;
    }


    public Track(String track, Date date, String artist, String album, String url) {
        this.track = track;
        this.date = date;
        this.artist = artist;
        this.album = album;
        this.url = url;
    }

    public void setTrack(String track) {
        this.track = track;
    }


    public void setArtist(String artist) {
        this.artist = artist;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "Track{" +
                "track='" + track + '\'' +
                ", date='" + date + '\'' +
                ", artist='" + artist + '\'' +
                ", url='" + url + '\'' +
                '}';
    }

}
