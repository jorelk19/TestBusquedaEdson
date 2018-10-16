package testedson.searchengine.Entidades.TopArtist;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import testedson.searchengine.Entidades.GeneralResult;

public class Results extends GeneralResult {
    @SerializedName("artistmatches")
    @Expose
    private Artistmatches artistmatches;
    public Artistmatches getArtistmatches() {
        return artistmatches;
    }

    public void setArtistmatches(Artistmatches artistmatches) {
        this.artistmatches = artistmatches;
    }
}
