package testedson.searchengine.Entidades.TopTrack;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import testedson.searchengine.Entidades.GeneralResult;
import testedson.searchengine.Entidades.TopAlbum.Albummatches;

public class Results extends GeneralResult {
    @SerializedName("trackmatches")
    @Expose
    private Trackmatches trackmatches;

    public Trackmatches getTrackmatches() {
        return trackmatches;
    }

    public void setTrackmatches(Trackmatches trackmatches) {
        this.trackmatches = trackmatches;
    }
}