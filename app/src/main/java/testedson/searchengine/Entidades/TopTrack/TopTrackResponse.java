package testedson.searchengine.Entidades.TopTrack;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TopTrackResponse {
    @SerializedName("toptracks")
    @Expose
    private TopTracks toptracks;

    public TopTracks getToptracks() {
        return toptracks;
    }

    public void setToptracks(TopTracks toptracks) {
        this.toptracks = toptracks;
    }

}
