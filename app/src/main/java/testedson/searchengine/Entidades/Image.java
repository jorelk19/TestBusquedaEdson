package testedson.searchengine.Entidades;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Image {
    @SerializedName("size")
    @Expose
    private String size;
    @SerializedName("#text")
    @Expose
    private String text;
    @SerializedName("mbid")
    @Expose
    private String mbid;

    private String mbidPadre;

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getText() {
        return text;
    }

    public void setText(String valor) {
        this.text = valor;
    }

    public String getMbid() {
        return mbid;
    }

    public void setMbid(String mbid) {
        this.mbid = mbid;
    }


    public String getMbidPadre() {
        return mbidPadre;
    }

    public void setMbidPadre(String mbidPadre) {
        this.mbidPadre = mbidPadre;
    }
}
