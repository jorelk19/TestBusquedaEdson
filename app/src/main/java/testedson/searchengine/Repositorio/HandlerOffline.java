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

import testedson.searchengine.Controles.DatosGrilla;
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
    private ProgressDialog pDialog;
    private List<DatosGrilla> listaDatosGrilla;
    public HandlerOffline(Context context, ProgressDialog dialog){
        this.context = context;
        this.pDialog = dialog;
        sqLiteHandler = new SQLiteHandler(context);

    }

    public void ObtenerInformacionAlbum(ISearchEnginePresenter presentador, String data){
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
        presentador.MostrarResultado(listaDatosGrilla);
        //pDialog.dismiss();
    }

    public void ObtenerInformacionTopAlbumsArtist(ISearchEnginePresenter presentador,String data) {
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
            if(listaAlbum.size() == 0) {
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
            //Arrays.stream(listaAlbum.toArray()).filter(n -> n. < 3 || n > 7).count();
        }

        topAlbums.setTopAlbum(listaTopAlbum);
        albumTopResponse.setTopAlbums(topAlbums);

        listaDatosGrilla = MapperAlbum.MapperListaDatosGrillaTopArtist(albumTopResponse, data, context);
        presentador.MostrarResultado(listaDatosGrilla);
        //pDialog.dismiss();
    }

    public void ObtenerInformacionTrack(ISearchEnginePresenter presentador,String data) {
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
        presentador.MostrarResultado(listaDatosGrilla);
        //threadMsgConsulta(true, listaDatosGrilla, presentador);

    }


    public void threadMsgConsulta(boolean estado, List<DatosGrilla> mensaje, ISearchEnginePresenter presentador) {
        Message msgObj = HandlerConsulta.obtainMessage();
        Bundle b = new Bundle();
        msgObj.setData(b);
        Gson gson = new Gson();
        String json = gson.toJson(mensaje);
        Gson gson1 = new Gson();
        String json1 = gson1.toJson(presentador);

        b.putBoolean("estadoRespuesta", estado);
        b.putString("mensaje", json);
        b.putString("presentador", json1);
        HandlerConsulta.sendMessage(msgObj);
    }

    private final Handler HandlerConsulta = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {

            boolean respuesta = msg.getData().getBoolean("estadoRespuesta");
            int statusCode = msg.getData().getInt("statusCode");
            String mensaje = msg.getData().getString("mensaje");
            String presentadorMsj = msg.getData().getString("prresentador");
            Gson gson = new Gson();
            List<DatosGrilla> datosGrilla = gson.fromJson(mensaje, new TypeToken<List<DatosGrilla>>(){}.getType());
            ISearchEnginePresenter presentador = gson.fromJson(presentadorMsj, ISearchEnginePresenter.class);

            pDialog.dismiss();

        }
    };
}

