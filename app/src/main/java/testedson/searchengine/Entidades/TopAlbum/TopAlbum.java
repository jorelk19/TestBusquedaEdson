package testedson.searchengine.Entidades.TopAlbum;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import testedson.searchengine.Entidades.GeneralAttributes;
import testedson.searchengine.Entidades.TopArtist.Artist;

public class TopAlbum extends GeneralAttributes {
    private Artist artist;

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }
}
