package testedson.searchengine.Entidades.TopTrack;

import testedson.searchengine.Entidades.GeneralAttributes;

public class Track extends GeneralAttributes {

    private String artist;
    private String mbidArtista;

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getMbidArtista() {
        return mbidArtista;
    }

    public void setMbidArtista(String mbidArtista) {
        this.mbidArtista = mbidArtista;
    }
}
