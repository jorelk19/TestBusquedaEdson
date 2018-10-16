package testedson.searchengine.Entidades.TopAlbum;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import testedson.searchengine.Entidades.GeneralResult;
import testedson.searchengine.Entidades.TopAlbum.Albummatches;

public class Results extends GeneralResult {
    @SerializedName("albummatches")
    @Expose
    private Albummatches albummatches;
    public Albummatches getAlbummatches() {
        return albummatches;
    }

    public void setAlbummatches(Albummatches albummatches) {
        this.albummatches = albummatches;
    }
}

