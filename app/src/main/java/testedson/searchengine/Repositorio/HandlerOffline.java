package testedson.searchengine.Repositorio;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import retrofit2.Response;
import testedson.searchengine.Controles.DatosGrilla;
import testedson.searchengine.Entidades.Categoria;
import testedson.searchengine.Entidades.Image;
import testedson.searchengine.Entidades.TopAlbum.Album;
import testedson.searchengine.Entidades.TopAlbum.AlbumResponse;
import testedson.searchengine.Entidades.TopAlbum.AlbumTopResponse;
import testedson.searchengine.Entidades.TopAlbum.Albummatches;
import testedson.searchengine.Entidades.TopAlbum.Results;
import testedson.searchengine.Entidades.TopAlbum.TopAlbum;
import testedson.searchengine.Entidades.TopAlbum.TopAlbums;
import testedson.searchengine.Entidades.TopArtist.Artist;
import testedson.searchengine.Entidades.TopTrack.Track;
import testedson.searchengine.Entidades.TopTrack.TrackResponse;
import testedson.searchengine.Entidades.TopTrack.Trackmatches;
import testedson.searchengine.Presentador.ISearchEnginePresenter;
import testedson.searchengine.R;
import testedson.searchengine.Utilidades.MapperAlbum;

public class HandlerOffline {
    private Context context;
    SQLiteHandler sqLiteHandler;
    private Categoria categoria;
    private ISearchEnginePresenter presentador;
    private String datoBuscado;

    private List<DatosGrilla> listaDatosGrilla;
    public HandlerOffline(Context context, Categoria categoriaSeleccionada, ISearchEnginePresenter presenter){
        this.context = context;
        this.presentador = presenter;
        this.categoria = categoriaSeleccionada;
        sqLiteHandler = new SQLiteHandler(context);
        ISearchEnginePresenter presentador = presenter;
    }

    private void ObtenerInformacionAlbum(String data){
        AlbumResponse albumResponse = new AlbumResponse();
        albumResponse.setResults(new Results());
        albumResponse.getResults().setAlbummatches(new Albummatches());
        ;

        List<Album> listaAlbum = sqLiteHandler.ObtenerListaAlbum(data);
        
        for (Album item:listaAlbum
             ) {
            item.setImage(sqLiteHandler.ObtenerListaImagenes(item.getMbid()));
            item.setArtist(data);
        }
        
        albumResponse.getResults().getAlbummatches().setAlbum(listaAlbum);
        
        
        listaDatosGrilla = MapperAlbum.MapperListaDatosGrillaAlbum(albumResponse, context);
        threadMsgCargueGrilla(true, "");
        //presentador.MostrarResultado(listaDatosGrilla);
    }

    private void ObtenerInformacionTopAlbumsArtist(String data) {
        AlbumTopResponse albumTopResponse = new AlbumTopResponse();

        List<Artist> listaArtista = sqLiteHandler.ObtenerListaArtista(data);
        List<Album> listaAlbum = new ArrayList<>();
        TopAlbums topAlbums = new TopAlbums();
        List<TopAlbum> listaTopAlbum = new ArrayList<>();

        for (Artist item:listaArtista
             ) {


            TopAlbum topAlbum = new TopAlbum();
            topAlbum.setArtist(item);
            listaTopAlbum.add(topAlbum);
        }

        int cont = 0;
        for (TopAlbum item:listaTopAlbum
             ) {
            if(listaAlbum.size() <= 0) {
                listaAlbum = sqLiteHandler.ObtenerListaAlbumMbIdArtista(item.getArtist().getMbid());
            }

            Album album = listaAlbum.get(cont);

            if(album.getMbidArtista().equals(item.getArtist().getMbid())){
                item.setMbid(album.getMbid());
                List<Image> imagenes = sqLiteHandler.ObtenerListaImagenes(album.getMbid());
                item.setImage(imagenes);
                item.setName(album.getName());
                item.setUrl(album.getUrl());
            }

            cont++;
            if(cont >= listaAlbum.size())
            {
                break;
            }
        }

        topAlbums.setTopAlbum(listaTopAlbum);
        albumTopResponse.setTopAlbums(topAlbums);

        listaDatosGrilla = MapperAlbum.MapperListaDatosGrillaTopArtist(albumTopResponse, data, context);
        threadMsgCargueGrilla(true, "");
        //presentador.MostrarResultado(listaDatosGrilla);
    }

    private void ObtenerInformacionTrack(String data) {
        TrackResponse trackResponse = new TrackResponse();
        
        trackResponse.setResults(new testedson.searchengine.Entidades.TopTrack.Results());
        trackResponse.getResults().setTrackmatches(new Trackmatches());

        List<Track> listaTrack = sqLiteHandler.ObtenerListaTracks(data);

        for (Track item:listaTrack
             ) {
            List<Image> imagenes = sqLiteHandler.ObtenerListaImagenes(item.getMbid());
            item.setImage(imagenes);
        }

        trackResponse.getResults().getTrackmatches().setTrack(listaTrack);

        listaDatosGrilla = MapperAlbum.MapperListaDatosGrillaTrack(trackResponse, context);
        threadMsgCargueGrilla(true, "");
        //presentador.MostrarResultado(listaDatosGrilla);
    }

    public void ConsultarInfotmacionOffline(String dato) {
        datoBuscado = dato;
        new Thread(new RunnableCargarDatosGrilla()).start();
    }

    private class RunnableCargarDatosGrilla implements Runnable {
        @Override
        public void run() {
            switch(categoria.getIdCategoria()) {
                case 1:
                    ObtenerInformacionAlbum(datoBuscado);
                    break;
                case 2:
                    ObtenerInformacionTopAlbumsArtist(datoBuscado);
                    break;
                case 3:
                    ObtenerInformacionTrack(datoBuscado);
                    break;
                default:
                    threadMsgCargueGrilla(false, "Debe seleccionar una categoría");
                    break;
            }

            threadMsgCargueGrilla(true, "");
        }
    }

    /***********************************************************
     * Metodo para notificar que se termino el proceso del hilo
     *******************************************************/
    private void threadMsgCargueGrilla(boolean estado, String mensaje) {
        presentador.NotificarCierreProgressDialog();
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
                presentador.MostrarResultado(listaDatosGrilla);
                presentador.NotificarCierreProgressDialog();
            } else {
                presentador.MostrarMensaje(mensaje);
                presentador.NotificarCierreProgressDialog();
            }
        }
    };
}

