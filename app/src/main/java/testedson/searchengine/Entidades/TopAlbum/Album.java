package testedson.searchengine.Entidades.TopAlbum;

import java.util.Date;
import java.util.List;

import testedson.searchengine.Entidades.GeneralAttributes;
import testedson.searchengine.Entidades.Tag;
import testedson.searchengine.Entidades.TopTrack.Track;

public class Album extends GeneralAttributes{

    private String artist;
    private long id;
    private Date releasedate;
    private long playacount;
    private List<Track> tracks;
    private List<Tag> toptags;
    private String mbidArtista;

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getReleasedate() {
        return releasedate;
    }

    public void setReleasedate(Date releasedate) {
        this.releasedate = releasedate;
    }

    public long getPlayacount() {
        return playacount;
    }

    public void setPlayacount(long playacount) {
        this.playacount = playacount;
    }

    public List<Track> getTracks() {
        return tracks;
    }

    public void setTracks(List<Track> tracks) {
        this.tracks = tracks;
    }

    public List<Tag> getToptags() {
        return toptags;
    }

    public void setToptags(List<Tag> toptags) {
        this.toptags = toptags;
    }

    public String getMbidArtista() {
        return mbidArtista;
    }

    public void setMbidArtista(String mbidArtista) {
        this.mbidArtista = mbidArtista;
    }
}
