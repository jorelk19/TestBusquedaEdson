package testedson.searchengine.Entidades.TopAlbum;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AlbumTopResponse {
    @SerializedName("topalbums")
    @Expose
    private TopAlbums topalbums;

    public TopAlbums getTopAlbums() {
        return topalbums;
    }

    public void setTopAlbums(TopAlbums topalbums) {
        this.topalbums = topalbums;
    }
}
