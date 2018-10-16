package testedson.searchengine.Retrofit;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import testedson.searchengine.Entidades.TopTrack.TopTrackResponse;
import testedson.searchengine.Entidades.TopTrack.TrackResponse;

public interface ITrackService {
    @FormUrlEncoded
    @POST("/2.0/?method=track.search")
    Call<TrackResponse> getTrack(
            @Field("track") String album,
            @Field("api_key") String apiKey,
            @Field("format") String format

    );

    @FormUrlEncoded
    @POST("/2.0/?method=artist.gettoptracks")
    Call<TopTrackResponse> getTopTrack(
            @Field("artist") String album,
            @Field("api_key") String apiKey,
            @Field("format") String format

    );

}
