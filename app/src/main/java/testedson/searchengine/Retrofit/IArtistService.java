package testedson.searchengine.Retrofit;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import testedson.searchengine.Entidades.TopAlbum.AlbumResponse;
import testedson.searchengine.Entidades.TopAlbum.AlbumTopResponse;
import testedson.searchengine.Entidades.TopArtist.ArtistResponse;

public interface IArtistService {
    @FormUrlEncoded
    @POST("/2.0/?method=artist.search")
    Call<ArtistResponse> getArtist(
            @Field("artist") String album,
            @Field("api_key") String apiKey,
            @Field("format") String format

    );

    @FormUrlEncoded
    @POST("/2.0/?method=artist.gettopalbums")
    Call<AlbumTopResponse> getTopAlbumsArtist(
            @Field("artist") String album,
            @Field("api_key") String apiKey,
            @Field("format") String format

    );
}
