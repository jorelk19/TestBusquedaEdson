package testedson.searchengine.Utilidades;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import testedson.searchengine.Controles.DatosGrilla;
import testedson.searchengine.Entidades.Image;
import testedson.searchengine.Entidades.TopAlbum.Album;
import testedson.searchengine.Entidades.TopAlbum.AlbumResponse;
import testedson.searchengine.Entidades.TopAlbum.AlbumTopResponse;
import testedson.searchengine.Entidades.TopAlbum.TopAlbum;
import testedson.searchengine.Entidades.TopArtist.Artist;
import testedson.searchengine.Entidades.TopArtist.ArtistResponse;
import testedson.searchengine.Entidades.TopTrack.TopTrack;
import testedson.searchengine.Entidades.TopTrack.TopTrackResponse;
import testedson.searchengine.Entidades.TopTrack.Track;
import testedson.searchengine.Entidades.TopTrack.TrackResponse;
import testedson.searchengine.Repositorio.SQLiteHandler;

public class MapperAlbum {

    public static List<DatosGrilla> MapperListaDatosGrillaAlbum(AlbumResponse body, Context context) {
        List<DatosGrilla> lista = new ArrayList<>();
        SQLiteHandler sqlHandler = new SQLiteHandler(context);

        for (Album item:body.getResults().getAlbummatches().getAlbum()
         ) {
            DatosGrilla dato = new DatosGrilla();
            dato.setNombre(item.getName());
            dato.setUrl(item.getUrl());
            dato.setNombreArtista(item.getArtist());
            List<testedson.searchengine.Entidades.Image> listaImagenes =item.getImage();
            if(listaImagenes != null && listaImagenes.size() > 1) {
                dato.setImagen(listaImagenes.get(1).getText());
            }
            lista.add(dato);

            if(Util.ValidarAccesoInternet(context)) {
                sqlHandler.InsertarAlbum(item);
                for (Image imagen : item.getImage()
                        ) {
                    imagen.setMbidPadre(item.getMbid());
                    sqlHandler.InsertarImagen(imagen);
                }
            }
        }


        return lista;
    }

    public static List<DatosGrilla> MapperListaDatosGrillaTopArtist(AlbumTopResponse body, String artista, Context context) {
        List<DatosGrilla> lista = new ArrayList<>();
        SQLiteHandler sqlHandler = new SQLiteHandler(context);

        for (TopAlbum item:body.getTopAlbums().getTopAlbum()
                ) {
            DatosGrilla dato = new DatosGrilla();
            dato.setNombre(item.getName());
            dato.setUrl(item.getUrl());
            dato.setNombreArtista(artista);
            List<testedson.searchengine.Entidades.Image> listaImagenes =item.getImage();
            if(listaImagenes != null && listaImagenes.size() > 1) {
                dato.setImagen(listaImagenes.get(1).getText());
            }
            lista.add(dato);

            if(Util.ValidarAccesoInternet(context)) {
                Album album = new Album();
                album.setName(item.getName());
                album.setMbid(item.getMbid());
                album.setUrl(item.getUrl());
                album.setMbidArtista(item.getArtist().getMbid());

                sqlHandler.InsertarAlbum(album);
                sqlHandler.InsertarArtist(item.getArtist());
                for (Image imagen : item.getImage()
                        ) {
                    imagen.setMbidPadre(item.getMbid());
                    sqlHandler.InsertarImagen(imagen);
                }
            }
        }


        return lista;
    }

    public static List<DatosGrilla> MapperListaDatosGrillaTrack(TrackResponse body, Context context) {
        List<DatosGrilla> lista = new ArrayList<>();
        SQLiteHandler sqlHandler = new SQLiteHandler(context);

        for (Track item:body.getResults().getTrackmatches().getTrack()
                ) {
            DatosGrilla dato = new DatosGrilla();
            dato.setNombre(item.getName());
            dato.setUrl(item.getUrl());
            dato.setNombreArtista(item.getArtist());
            List<testedson.searchengine.Entidades.Image> listaImagenes =item.getImage();
            if(listaImagenes != null && listaImagenes.size() > 1) {
                dato.setImagen(listaImagenes.get(1).getText());
            }
            lista.add(dato);

            if(Util.ValidarAccesoInternet(context)) {
                Track track = new Track();
                track.setName(item.getName());

                Random r = new Random();
                int rnd = r.nextInt(1000000 - 900000) + 900000;
                String mbid = item.getMbid().isEmpty() ? String.valueOf(rnd) :item.getMbid();
                track.setMbid(mbid);
                track.setUrl(item.getUrl());
                track.setArtist(item.getArtist());
                sqlHandler.InsertarTrack(track);

                for (Image imagen : item.getImage()
                        ) {
                    imagen.setMbidPadre(mbid);
                    sqlHandler.InsertarImagen(imagen);
                }
            }
        }


        return lista;

    }


    //FUERA DE USO
    public static List<DatosGrilla> MapperListaDatosGrillaArtist(ArtistResponse body) {
        List<DatosGrilla> lista = new ArrayList<>();

        for (Artist item:body.getResults().getArtistmatches().getArtist()
                ) {
            DatosGrilla dato = new DatosGrilla();
            dato.setNombre(item.getName());
            dato.setUrl(item.getUrl());

            List<testedson.searchengine.Entidades.Image> listaImagenes =item.getImage();
            if(listaImagenes != null && listaImagenes.size() > 1) {
                dato.setImagen(listaImagenes.get(1).getText());
            }
            lista.add(dato);
        }


        return lista;
    }

    //FUERA DE USO
    public static List<DatosGrilla> MapperListaDatosGrillaTopTrack(TopTrackResponse body) {
        List<DatosGrilla> lista = new ArrayList<>();

        for (TopTrack item:body.getToptracks().getTopTrack()
                ) {
            DatosGrilla dato = new DatosGrilla();
            dato.setNombre(item.getName());
            dato.setUrl(item.getUrl());

            List<testedson.searchengine.Entidades.Image> listaImagenes =item.getImage();
            if(listaImagenes != null && listaImagenes.size() > 1) {
                dato.setImagen(listaImagenes.get(1).getText());
            }
            lista.add(dato);
        }


        return lista;
    }
}
