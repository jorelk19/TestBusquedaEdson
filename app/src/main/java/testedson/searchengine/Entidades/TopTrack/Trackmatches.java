package testedson.searchengine.Entidades.TopTrack;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Trackmatches {
    @SerializedName("track")
    @Expose
    private List<Track> track = null;

    public List<Track> getTrack() {
        return track;
    }

    public void setTrack(List<Track> track) {
        this.track = track;
    }

}
