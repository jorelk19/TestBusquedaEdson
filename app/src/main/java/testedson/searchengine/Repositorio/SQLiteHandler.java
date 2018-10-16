package testedson.searchengine.Repositorio;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import testedson.searchengine.Entidades.Categoria;
import testedson.searchengine.Entidades.Image;
import testedson.searchengine.Entidades.TopAlbum.Album;
import testedson.searchengine.Entidades.TopArtist.Artist;
import testedson.searchengine.Entidades.TopTrack.Track;
import testedson.searchengine.Utilidades.Util;

import static testedson.searchengine.Utilidades.Util.DATABASE_VERSION;

public class SQLiteHandler extends SQLiteOpenHelper {

    public SQLiteHandler(Context context) {
        super(context, Util.DATABASE_NAME, null, Util.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }

    /*********************************
     * ** INICIALIZACION DE TABLAS *****
     *********************************/

    public void CrearTablas() {
        SQLiteDatabase db = getWritableDatabase();

        db.execSQL("CREATE TABLE IF NOT EXISTS " + Util.TABLA_ALBUM
                + " (IdAlbum INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "NombreALbum TEXT, " +
                "NombreArtistaAlbum TEXT, " +
                "mbidArtista TEXT, " +
                "mbidAlbum TEXT," +
                "UrlAlbum TEXT)");

        db.execSQL("CREATE TABLE IF NOT EXISTS " + Util.TABLA_ARTIST
                + " (IdArtista INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "NombreArtista TEXT, " +
                "mbidArtista TEXT," +
                "UrlArtista TEXT)");

        db.execSQL("CREATE TABLE IF NOT EXISTS " + Util.TABLA_TRACK
                + " (IdTrack INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "NombreTrack TEXT, " +
                "NombreArtistaTrack TEXT, " +
                "mbidArtista TEXT," +
                "mbidTrack TEXT," +
                "UrlTrack TEXT)");

        db.execSQL("CREATE TABLE IF NOT EXISTS " + Util.TABLA_IMAGEN
                + " (IdImagen INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "Size TEXT, " +
                "valor TEXT, " +
                "mbidImagen TEXT," +
                "mbidPadre TEXT)");


        db.execSQL("CREATE TABLE IF NOT EXISTS " + Util.TABLA_CATEGORIA
                + " (IdCategoria INTEGER PRIMARY KEY, " +
                "NombreCategoria TEXT)");

       if(ValidarRegistrosCategoria("1")){
           InsertarRegistroEnTabla("INSERT INTO " + Util.TABLA_CATEGORIA + "(IdCategoria, NombreCategoria) VALUES (1, 'TOP ALBUM')");
       }
        if(ValidarRegistrosCategoria("2")){
            InsertarRegistroEnTabla("INSERT INTO " + Util.TABLA_CATEGORIA + "(IdCategoria, NombreCategoria) VALUES (2, 'TOP ARTIST')");
        }
        if(ValidarRegistrosCategoria("3")){
            InsertarRegistroEnTabla("INSERT INTO " + Util.TABLA_CATEGORIA + "(IdCategoria, NombreCategoria) VALUES (3, 'TOP TRACK')");
        }
    }

    private boolean ValidarRegistrosCategoria(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query ="SELECT COUNT(*) FROM " + Util.TABLA_CATEGORIA + " WHERE IdCategoria = " + id;
        Cursor cursor = db.rawQuery(query, null );
        if (!cursor.moveToFirst()) {
            return false;
        }
        else{
            return true;
        }
    }

    private boolean ValidarRegistrosTrack(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query ="SELECT COUNT(*) FROM " + Util.TABLA_TRACK + " WHERE mbidTrack = '" + id + "'";
        Cursor cursor = db.rawQuery(query, null );
        if (!cursor.moveToFirst()) {
            return false;
        }
        else{
            return true;
        }
    }

    private boolean ValidarRegistrosAlbum(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query ="SELECT COUNT(*) FROM " + Util.TABLA_ALBUM + " WHERE mbidAlbum = '" + id + "'";
        Cursor cursor = db.rawQuery(query, null );
        if (!cursor.moveToFirst()) {
            return false;
        }
        else{
            return true;
        }
    }

    private boolean ValidarRegistrosArtista(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query ="SELECT COUNT(*) FROM " + Util.TABLA_ARTIST + " WHERE mbidArtista = '" + id + "'";
        Cursor cursor = db.rawQuery(query, null );
        if (!cursor.moveToFirst()) {
            return false;
        }
        else{
            return true;
        }
    }

    private boolean ValidarRegistrosArtistaNombre(String nombre) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query ="SELECT COUNT(*) FROM " + Util.TABLA_ARTIST + " WHERE NombreArtista = '" + nombre + "'";
        Cursor cursor = db.rawQuery(query, null );
        if (!cursor.moveToFirst()) {
            return false;
        }
        else{
            return true;
        }
    }

    private boolean ValidarRegistrosImagen(String texto) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query ="SELECT COUNT(*) FROM " + Util.TABLA_IMAGEN + " WHERE valor = '" + texto + "'";
        Cursor cursor = db.rawQuery(query, null );
        if (!cursor.moveToFirst()) {
            return false;
        }
        else{
            return true;
        }
    }

    public void InsertarAlbum(Album album) {
        if(ValidarRegistrosArtista(album.getMbid())) {
            InsertarRegistroEnTabla("INSERT INTO " + Util.TABLA_ALBUM + "(NombreALbum, NombreArtistaAlbum, UrlAlbum, mbidAlbum, mbidArtista)" +
                    " VALUES ('" + album.getName() + "','" + album.getArtist() + "','" + album.getUrl() + "','" + album.getMbid() + "','" + album.getMbidArtista() + "')");
        }
    }

    public void InsertarArtist(Artist artist) {
        if(ValidarRegistrosArtista(artist.getMbid())) {
            InsertarRegistroEnTabla("INSERT INTO " + Util.TABLA_ARTIST + "(NombreArtista, mbidArtista, UrlArtista)" +
                    " VALUES ('" + artist.getName() + "','" + artist.getMbid() + "','" + artist.getUrl() + "')");
        }
    }

    public void InsertarArtistTexto(String artist) {
        if(ValidarRegistrosArtistaNombre(artist)) {
            InsertarRegistroEnTabla("INSERT INTO " + Util.TABLA_ARTIST + "(NombreArtista, mbidArtista, UrlArtista)" +
                    " VALUES ('" + artist + "','','','')");
        }
    }

    public void InsertarTrack(Track track) {
        if(ValidarRegistrosTrack(track.getMbid())) {
            InsertarRegistroEnTabla("INSERT INTO " + Util.TABLA_TRACK + "(NombreTrack, NombreArtistaTrack, mbidArtista, mbidTrack, UrlTrack)" +
                    " VALUES ('" + track.getName() + "','" + track.getArtist() + "','','" + track.getMbid() + "','" + track.getUrl() + "')");
        }
    }

    public void InsertarImagen(Image imagen) {
        if(ValidarRegistrosImagen(imagen.getText())) {
            InsertarRegistroEnTabla("INSERT INTO " + Util.TABLA_IMAGEN + "(Size, valor, mbidImagen, mbidPadre) " +
                    "VALUES ('" + imagen.getSize() + "','" + imagen.getText() + "','" + imagen.getMbid() + "','" + imagen.getMbidPadre() + "')");
        }
    }

    /*************************************************************************************
     * Obtener lista Artistas*****************************************************
     *****************************************************************************************/
    public List<Track> ObtenerListaTracks(String nombreTrack) {
        List<Track> tracks = new ArrayList<>();

        try {
            SQLiteDatabase db = getReadableDatabase();

            String query = "SELECT NombreTrack" +
                    ",NombreArtistaTrack" +
                    ",mbidArtista" +
                    ",mbidTrack" +
                    ",UrlTrack FROM " + Util.TABLA_TRACK +
                    " WHERE LOWER(NombreTrack) LIKE '%" + nombreTrack.toLowerCase() + "%'";

            Cursor cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                do {

                    Track track = new Track();
                    track.setName(String.valueOf(cursor.getString(0)));
                    track.setArtist(String.valueOf(cursor.getString(1)));
                    track.setMbidArtista(String.valueOf(cursor.getString(2)));
                    track.setMbid(String.valueOf(cursor.getString(3)));
                    track.setUrl(String.valueOf(cursor.getString(4)));
                    tracks.add(track);

                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        catch (Exception ex){
            String menaje = ex.getMessage();
        }

        return tracks;
    }
    /*************************************************************************************
     * Obtener lista Artistas*****************************************************
     *****************************************************************************************/
    public List<Artist> ObtenerListaArtista(String nombreArtista) {
        List<Artist> artistas = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String query ="SELECT NombreArtista" +
                ",mbidArtista" +
                ",UrlArtista " +
                " FROM " + Util.TABLA_ARTIST +
                " WHERE LOWER(NombreArtista) LIKE '%" + nombreArtista.toLowerCase() + "%'";

        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {

                Artist artista = new Artist();
                artista.setName(String.valueOf(cursor.getString(0)));
                artista.setMbid(String.valueOf(cursor.getString(1)));
                artista.setUrl(String.valueOf(cursor.getString(2)));
                artistas.add(artista);

            } while (cursor.moveToNext());
        }
        cursor.close();

        return artistas;
    }
    /*************************************************************************************
     * Obtener lista albumes*****************************************************
     *****************************************************************************************/
    public List<Album> ObtenerListaAlbum(String nombreAlbum) {
        List<Album> albumes = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String query ="SELECT NombreALbum" +
                ",NombreArtistaAlbum" +
                ",UrlAlbum" +
                ",mbidArtista" +
                ",mbidAlbum FROM " + Util.TABLA_ALBUM +
                " WHERE LOWER(NombreALbum) LIKE '%" + nombreAlbum.toLowerCase() + "%'";

        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                Album album = new Album();
                album.setName(String.valueOf(cursor.getString(0)));
                album.setArtist(String.valueOf(cursor.getString(1)));
                album.setUrl(String.valueOf(cursor.getString(2)));
                album.setMbidArtista(String.valueOf(cursor.getString(3)));
                album.setMbid(String.valueOf(cursor.getString(4)));
                albumes.add(album);

            } while (cursor.moveToNext());
        }
        cursor.close();

        return albumes;
    }

    public List<Album> ObtenerListaAlbumNombreArtista(String nombreArtista) {
        List<Album> albumes = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String query ="SELECT NombreALbum" +
                ",NombreArtistaAlbum" +
                ",UrlAlbum" +
                ",mbidArtista" +
                ",mbidAlbum FROM " + Util.TABLA_ALBUM +
                " WHERE NombreArtistaAlbum = '" + nombreArtista + "'";

        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                Album album = new Album();
                album.setName(String.valueOf(cursor.getString(0)));
                album.setArtist(String.valueOf(cursor.getString(1)));
                album.setUrl(String.valueOf(cursor.getString(2)));
                album.setMbidArtista(String.valueOf(cursor.getString(3)));
                album.setMbid(String.valueOf(cursor.getString(4)));
                albumes.add(album);

            } while (cursor.moveToNext());
        }
        cursor.close();

        return albumes;
    }

    public List<Album> ObtenerListaAlbumMbIdArtista(String mbidArtista) {
        List<Album> albumes = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String query ="SELECT NombreALbum" +
                ",NombreArtistaAlbum" +
                ",UrlAlbum" +
                ",mbidArtista" +
                ",mbidAlbum FROM " + Util.TABLA_ALBUM +
                " WHERE mbidArtista = '" + mbidArtista + "'";

        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                Album album = new Album();
                album.setName(String.valueOf(cursor.getString(0)));
                album.setArtist(String.valueOf(cursor.getString(1)));
                album.setUrl(String.valueOf(cursor.getString(2)));
                album.setMbidArtista(String.valueOf(cursor.getString(3)));
                album.setMbid(String.valueOf(cursor.getString(4)));
                albumes.add(album);

            } while (cursor.moveToNext());
        }
        cursor.close();

        return albumes;
    }
    /***********************************************************************************
            * Obtener lista albumes*****************************************************
            *****************************************************************************************/
    public List<Image> ObtenerListaImagenes(String mbidPadre) {
        List<Image> listaImagenes = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String query ="SELECT Size" +
                ",valor" +
                ",mbidImagen" +
                ",mbidPadre FROM " + Util.TABLA_IMAGEN +
                " WHERE mbidPadre = '" + mbidPadre + "'";

        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                Image imagen = new Image();
                imagen.setSize(String.valueOf(cursor.getString(0)));
                imagen.setText(String.valueOf(cursor.getString(1)));
                imagen.setMbid(String.valueOf(cursor.getString(2)));
                imagen.setMbidPadre(String.valueOf(cursor.getString(3)));
                listaImagenes.add(imagen);

            } while (cursor.moveToNext());
        }
        cursor.close();

        return listaImagenes;
    }

    /***************************************
     * Crear tabla
     ******************************************************************/
    public void CrearTablas(String query) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            db.execSQL(query);
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /*****************************************
     * Insertar registro uno a uno
     ***********************************************/
    public void InsertarRegistroEnTabla(String query) {
        SQLiteDatabase db = getWritableDatabase();
        try {
            db.execSQL(query);
            db.close();
        } catch (Exception e) {
            db.close();
            e.printStackTrace();
        }
    }

    /***********************
     * Borrar tabla si no existe
     *****************************************/
    public void BorrarTabla(String nombreTabla) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + nombreTabla);
        db.close();
    }

    /****************************
     * Valida si tabla existe
     ***********************************/
    public boolean isTableExists(String tableName) {

        SQLiteDatabase db = this.getWritableDatabase();
        if (tableName == null || db == null || !db.isOpen()) {
            return false;
        }
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM sqlite_master WHERE type = ? AND name = ?", new String[]{"table", tableName});
        if (!cursor.moveToFirst()) {
            return false;
        }
        int count = cursor.getInt(0);
        cursor.close();
        db.close();

        return count > 0;
    }

    public List<Categoria> ObtenerCategorias() {
        List<Categoria> categorias = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String query ="SELECT [IdCategoria], [NombreCategoria] FROM " + Util.TABLA_CATEGORIA;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                int id = Integer.valueOf(cursor.getString(0));
                String descripcion = String.valueOf(cursor.getString(1));
                Categoria categoria = new Categoria(id, descripcion);
                categorias.add(categoria);

            } while (cursor.moveToNext());
        }
        cursor.close();

        return categorias;
    }


}

