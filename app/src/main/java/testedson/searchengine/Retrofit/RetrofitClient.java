package testedson.searchengine.Retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import testedson.searchengine.Utilidades.Util;

public class RetrofitClient {
    private static RetrofitClient instance;
    private Retrofit retrofit;

    private RetrofitClient(){

        retrofit = new Retrofit.Builder().baseUrl(Util.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
    }

    public static synchronized RetrofitClient getInstance(){
        if(instance == null ){
            instance = new RetrofitClient();
        }

        return instance;
    }

    public IAlbumService getAlbumApi(){
        return retrofit.create(IAlbumService.class);
    }

    public IArtistService getArtistApi(){
        return retrofit.create(IArtistService.class);
    }
    public IArtistService getTopArtistAlbumApi(){
        return retrofit.create(IArtistService.class);
    }
    public ITrackService getTrackApi(){
        return retrofit.create(ITrackService.class);
    }

    public ITrackService getTopTrackApi(){
        return retrofit.create(ITrackService.class);
    }
}
