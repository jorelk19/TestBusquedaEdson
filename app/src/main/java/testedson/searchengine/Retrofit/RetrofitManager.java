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
    private String datoBuscado;

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

    public void ObtenerInformacionAlbum(){

        Call<AlbumResponse> resultado = RetrofitClient.getInstance().getAlbumApi().getAlbum(datoBuscado, Util.ApiKey, Util.Formato);
        resultado.enqueue(new Callback<AlbumResponse>() {
            @Override
            public void onResponse(Call<AlbumResponse> call, Response<AlbumResponse> response) {
                listaDatosGrilla = MapperAlbum.MapperListaDatosGrillaAlbum(response.body(), context);
                threadMsgCargueGrilla(true, "");
            }

            @Override
            public void onFailure(Call<AlbumResponse> call, Throwable t) {
                presenter.MostrarMensaje(t.getMessage());
                threadMsgCargueGrilla(false, t.getMessage());
            }
        });
    }

    public void ObtenerInformacionTopAlbumsArtist() {
        Call<AlbumTopResponse> resultado = RetrofitClient.getInstance().getTopArtistAlbumApi().getTopAlbumsArtist(datoBuscado, Util.ApiKey, Util.Formato);
        resultado.enqueue(new Callback<AlbumTopResponse>() {
            @Override
            public void onResponse(Call<AlbumTopResponse> call, Response<AlbumTopResponse> response) {
                listaDatosGrilla = MapperAlbum.MapperListaDatosGrillaTopArtist(response.body(), artist, context);
                threadMsgCargueGrilla(true, "");
            }

            @Override
            public void onFailure(Call<AlbumTopResponse> call, Throwable t) {
                threadMsgCargueGrilla(false, t.getMessage());
            }
        });
    }

    public void ObtenerInformacionTrack() {
        Call<TrackResponse> resultado = RetrofitClient.getInstance().getTrackApi().getTrack(datoBuscado, Util.ApiKey, Util.Formato);
        resultado.enqueue(new Callback<TrackResponse>() {
            @Override
            public void onResponse(Call<TrackResponse> call, Response<TrackResponse> response) {
                listaDatosGrilla = MapperAlbum.MapperListaDatosGrillaTrack(response.body(), context);
                threadMsgCargueGrilla(true, "");
            }

            @Override
            public void onFailure(Call<TrackResponse> call, Throwable t) {
                threadMsgCargueGrilla(false, t.getMessage());
            }
        });
    }

    public void ConsultarInformacionOnline(ISearchEnginePresenter searchEnginePresenter, String dato) {
        this.presenter = searchEnginePresenter;
        this.datoBuscado = dato;
        new Thread(new RunnableCargarDatosGrilla()).start();
    }

    private class RunnableCargarDatosGrilla implements Runnable {
        @Override
        public void run() {
            switch(idCategoriaSeleccionda) {
                case 1:
                    ObtenerInformacionAlbum();
                    break;
                case 2:
                    ObtenerInformacionTopAlbumsArtist();
                    break;
                case 3:
                    ObtenerInformacionTrack();
                    break;
                default:
                    threadMsgCargueGrilla(false, "Debe seleccionar una categoría");
                    break;
            }
        }
    }

    /***********************************************************
     * Metodo para notificar que se termino el proceso del hilo
     *******************************************************/
    private void threadMsgCargueGrilla(boolean estado, String mensaje) {
        presenter.NotificarCierreProgressDialog();
        Message msg = handlerCargarGrilla.obtainMessage();
        Bundle bundleE = new Bundle();
        msg.setData(bundleE);
        bundleE.putBoolean("estadoCargueGrilla", estado);
        bundleE.putString("mensaje", mensaje);
        handlerCargarGrilla.sendMessage(msg);
    }

    private final Handler handlerCargarGrilla = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            boolean respuesta = msg.getData().getBoolean("estadoCargueGrilla");
            String mensaje = msg.getData().getString("mensaje");
            if (respuesta) {
                presenter.MostrarResultado(listaDatosGrilla);
                presenter.NotificarCierreProgressDialog();
            } else {
                presenter.MostrarMensaje(mensaje);
                presenter.NotificarCierreProgressDialog();
            }
        }
    };
}
