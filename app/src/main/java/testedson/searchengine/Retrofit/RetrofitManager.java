package testedson.searchengine.Retrofit;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

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
    Response<AlbumTopResponse> serviceTopAlbumResponse;
    Response<AlbumResponse> serviceAlbumResponse;
    Response<TrackResponse> serviceTrackResponse;

    List<DatosGrilla> listaDatosGrilla;
    int idCategoriaSeleccionda;
    Context context;
    private ProgressDialog pDialog;

    public RetrofitManager(Context context){
        this.context = context;
    }

    public void setCategoriaSeleccionada(int idCategoria){
        this.idCategoriaSeleccionda = idCategoria;
    }

    public void ObtenerInformacionAlbum(final ISearchEnginePresenter presentador, String album){
        this.presenter = presentador;
        Call<AlbumResponse> resultado = RetrofitClient.getInstance().getAlbumApi().getAlbum(album, Util.ApiKey, Util.Formato);
        resultado.enqueue(new Callback<AlbumResponse>() {
            @Override
            public void onResponse(Call<AlbumResponse> call, Response<AlbumResponse> response) {
                serviceAlbumResponse = response;
                new Thread(new RunnableCargarDatosGrilla()).start();
            }

            @Override
            public void onFailure(Call<AlbumResponse> call, Throwable t) {
                presenter.MostrarMensaje(t.getMessage());
                presentador.NotificarCierreProgressDialog();
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
                serviceTopAlbumResponse = response;
                new Thread(new RunnableCargarDatosGrilla()).start();
            }

            @Override
            public void onFailure(Call<AlbumTopResponse> call, Throwable t) {
                presenter.MostrarMensaje(t.getMessage());
                presenter.NotificarCierreProgressDialog();
            }
        });
    }

    public void ObtenerInformacionTrack(ISearchEnginePresenter presentador, String track) {
        this.presenter = presentador;
        Call<TrackResponse> resultado = RetrofitClient.getInstance().getTrackApi().getTrack(track, Util.ApiKey, Util.Formato);
        resultado.enqueue(new Callback<TrackResponse>() {
            @Override
            public void onResponse(Call<TrackResponse> call, Response<TrackResponse> response) {
                serviceTrackResponse = response;
                new Thread(new RunnableCargarDatosGrilla()).start();

                presenter.MostrarResultado(listaDatosGrilla);
                presenter.NotificarCierreProgressDialog();
            }

            @Override
            public void onFailure(Call<TrackResponse> call, Throwable t) {
                presenter.MostrarMensaje(t.getMessage());
                presenter.NotificarCierreProgressDialog();
            }
        });
    }

    private class RunnableCargarDatosGrilla implements Runnable {
        @Override
        public void run() {
            switch(idCategoriaSeleccionda) {
                case 1:
                    listaDatosGrilla = MapperAlbum.MapperListaDatosGrillaAlbum(serviceAlbumResponse.body(), context);
                    break;
                case 2:
                    listaDatosGrilla = MapperAlbum.MapperListaDatosGrillaTopArtist(serviceTopAlbumResponse.body(), artist, context);
                    break;
                case 3:
                    listaDatosGrilla = MapperAlbum.MapperListaDatosGrillaTrack(serviceTrackResponse.body(), context);
                    break;
                default:

                    break;
            }

            threadMsgCargueGrilla(true);
        }
    }

    /***********************************************************
     * Metodo para notificar que se termino el proceso del hilo
     *******************************************************/
    private void threadMsgCargueGrilla(boolean estado) {
        presenter.NotificarCierreProgressDialog();
        Message msg = handlerCargarGrilla.obtainMessage();
        Bundle bundleE = new Bundle();
        msg.setData(bundleE);
        bundleE.putBoolean("estadoCargueGrilla", estado);
        handlerCargarGrilla.sendMessage(msg);
    }

    private final Handler handlerCargarGrilla = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            boolean respuesta = msg.getData().getBoolean("estadoCargueGrilla");
            if (respuesta) {
                presenter.MostrarResultado(listaDatosGrilla);
                presenter.NotificarCierreProgressDialog();
            } else {
                presenter.NotificarCierreProgressDialog();
            }
        }
    };

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


    //FUERA DE USO
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
}
