package testedson.searchengine.Retrofit;

import android.app.ProgressDialog;
import android.content.Context;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import testedson.searchengine.Controles.DatosGrilla;
import testedson.searchengine.Entidades.TopAlbum.AlbumResponse;
import testedson.searchengine.Entidades.TopAlbum.AlbumTopResponse;
import testedson.searchengine.Entidades.TopArtist.Artist;
import testedson.searchengine.Entidades.TopArtist.ArtistResponse;
import testedson.searchengine.Entidades.TopTrack.TopTrackResponse;
import testedson.searchengine.Entidades.TopTrack.TrackResponse;
import testedson.searchengine.Presentador.ISearchEnginePresenter;
import testedson.searchengine.Utilidades.MapperAlbum;
import testedson.searchengine.Utilidades.Util;

public class RetrofitManager {

    ISearchEnginePresenter presenter;
    String artist;
    Context context;
    private ProgressDialog pDialog;

    public RetrofitManager(Context context){
        this.context = context;
    }

    public void ObtenerInformacionAlbum(ISearchEnginePresenter presentador, String album ){
        this.presenter = presentador;
        Call<AlbumResponse> resultado = RetrofitClient.getInstance().getAlbumApi().getAlbum(album, Util.ApiKey, Util.Formato);
        resultado.enqueue(new Callback<AlbumResponse>() {
            @Override
            public void onResponse(Call<AlbumResponse> call, Response<AlbumResponse> response) {
                List<DatosGrilla> listaDatosGrilla = MapperAlbum.MapperListaDatosGrillaAlbum(response.body(), context);
                 presenter.MostrarResultado(listaDatosGrilla);
                pDialog.dismiss();
            }

            @Override
            public void onFailure(Call<AlbumResponse> call, Throwable t) {
                presenter.MostrarMensaje(t.getMessage());
                pDialog.dismiss();
            }
        });
    }

    public void ObtenerInformacionTopAlbumsArtist(ISearchEnginePresenter presentador, String artista) {
        this.presenter = presentador;
        this.artist = artista;
        Call<AlbumTopResponse> resultado = RetrofitClient.getInstance().getTopArtistAlbumApi().getTopAlbumsArtist(artist, Util.ApiKey, Util.Formato);
        resultado.enqueue(new Callback<AlbumTopResponse>() {
            @Override
            public void onResponse(Call<AlbumTopResponse> call, Response<AlbumTopResponse> response) {
                List<DatosGrilla> listaDatosGrilla = MapperAlbum.MapperListaDatosGrillaTopArtist(response.body(), artist, context);
                presenter.MostrarResultado(listaDatosGrilla);
                pDialog.dismiss();
            }

            @Override
            public void onFailure(Call<AlbumTopResponse> call, Throwable t) {
                presenter.MostrarMensaje(t.getMessage());
                pDialog.dismiss();
            }
        });
    }

    public void ObtenerInformacionTrack(ISearchEnginePresenter presentador, String track) {
        this.presenter = presentador;
        Call<TrackResponse> resultado = RetrofitClient.getInstance().getTrackApi().getTrack(track, Util.ApiKey, Util.Formato);
        resultado.enqueue(new Callback<TrackResponse>() {
            @Override
            public void onResponse(Call<TrackResponse> call, Response<TrackResponse> response) {
                List<DatosGrilla> listaDatosGrilla = MapperAlbum.MapperListaDatosGrillaTrack(response.body(), context);
                presenter.MostrarResultado(listaDatosGrilla);
                pDialog.dismiss();
            }

            @Override
            public void onFailure(Call<TrackResponse> call, Throwable t) {
                presenter.MostrarMensaje(t.getMessage());
                pDialog.dismiss();
            }
        });
    }

    //FUERA DE USO
    public void ObtenerInformacionArtist(ISearchEnginePresenter presentador, String artist) {
        this.presenter = presentador;
        //albumService.getAlbum(album, Util.ApiKey, Util.Formato);
        Call<ArtistResponse> resultado = RetrofitClient.getInstance().getArtistApi().getArtist(artist, Util.ApiKey, Util.Formato);
        resultado.enqueue(new Callback<ArtistResponse>() {
            @Override
            public void onResponse(Call<ArtistResponse> call, Response<ArtistResponse> response) {
                List<DatosGrilla> listaDatosGrilla = MapperAlbum.MapperListaDatosGrillaArtist(response.body());
                presenter.MostrarResultado(listaDatosGrilla);
                pDialog.dismiss();
            }

            @Override
            public void onFailure(Call<ArtistResponse> call, Throwable t) {
                presenter.MostrarMensaje(t.getMessage());
                pDialog.dismiss();
            }
        });
    }



    public void ObtenerInformacionTopTrack(ISearchEnginePresenter presentador, String artist) {
        this.presenter = presentador;
        //albumService.getAlbum(album, Util.ApiKey, Util.Formato);
        Call<TopTrackResponse> resultado = RetrofitClient.getInstance().getTopTrackApi().getTopTrack(artist, Util.ApiKey, Util.Formato);
        resultado.enqueue(new Callback<TopTrackResponse>() {
            @Override
            public void onResponse(Call<TopTrackResponse> call, Response<TopTrackResponse> response) {
                List<DatosGrilla> listaDatosGrilla = MapperAlbum.MapperListaDatosGrillaTopTrack(response.body());
                presenter.MostrarResultado(listaDatosGrilla);
                pDialog.dismiss();
            }

            @Override
            public void onFailure(Call<TopTrackResponse> call, Throwable t) {
                presenter.MostrarMensaje(t.getMessage());
                pDialog.dismiss();
            }
        });
    }

    public ProgressDialog getpDialog() {
        return pDialog;
    }

    public void setpDialog(ProgressDialog pDialog) {
        this.pDialog = pDialog;
    }
}
