package testedson.searchengine.Utilidades;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import static android.support.v4.content.ContextCompat.getSystemService;

public class Util {
    public static final String ApiKey = "1b8038b4ab21d8167e5afad83e6c6743";
    public static final String Formato = "json";
    public static final String BASE_URL = "http://ws.audioscrobbler.com";
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "SQLiteTestAndroid";
    public static final String TABLA_TRACK = "Track";
    public static final String TABLA_ALBUM = "Album";
    public static final String TABLA_ARTIST = "Artist";
    public static final String TABLA_CATEGORIA = "Categoria" ;
    public static final String TABLA_IMAGEN = "Imagen";

    /*******************************
     * Validar si existe algun tipo de conexion a internet
     ***************************************/
    public static boolean ValidarAccesoInternet(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        } else {
            return false;
        }
    }
}
