package testedson.searchengine.Entidades.TopTrack;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import testedson.searchengine.Entidades.GeneralAttributes;

public class TopTracks extends GeneralAttributes {
    @SerializedName("track")
    @Expose
    private List<TopTrack> topTrack = null;

    public List<TopTrack> getTopTrack() {
        return topTrack;
    }

    public void setTopTrack(List<TopTrack> topTrack) {
        this.topTrack = topTrack;
    }

}
