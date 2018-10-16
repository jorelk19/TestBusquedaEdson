package testedson.searchengine.Retrofit;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import testedson.searchengine.Entidades.TopAlbum.AlbumResponse;

public interface IAlbumService {
    //@GET("/?method=album.search&album=");
    @FormUrlEncoded
    @POST("/2.0/?method=album.search")
    Call<AlbumResponse> getAlbum(
            @Field("album") String album,
            @Field("api_key") String apiKey,
            @Field("format") String format

    );
}
