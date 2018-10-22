package testedson.searchengine.Interactor;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import retrofit2.Response;
import testedson.searchengine.Entidades.Categoria;
import testedson.searchengine.Entidades.TopAlbum.AlbumResponse;
import testedson.searchengine.Entidades.TopAlbum.AlbumTopResponse;
import testedson.searchengine.Entidades.TopTrack.TrackResponse;
import testedson.searchengine.Presentador.ISearchEnginePresenter;
import testedson.searchengine.Repositorio.HandlerOffline;
import testedson.searchengine.Repositorio.SQLiteHandler;
import testedson.searchengine.Retrofit.RetrofitManager;
import testedson.searchengine.Utilidades.Util;

public class SearchEngineInteractor implements ISearchEngineInteractor {

    private ISearchEnginePresenter searchEnginePresenter;
    private String resultado;
    public Context context;
    private RetrofitManager retrofitManager;

    SQLiteHandler sqlHandler;

    Categoria categoria;
    String datoBuscado;

    public SearchEngineInteractor(ISearchEnginePresenter presenter, Context context) {
        this.searchEnginePresenter = presenter;
        this.context = context;
        this.retrofitManager = new RetrofitManager(context);
    }

    @Override
    public void Buscar(String data, Categoria categoriaSeleccionada) {
        categoria = categoriaSeleccionada;
        datoBuscado = data;
        retrofitManager.setCategoriaSeleccionada(categoria.getIdCategoria());
        if (data.equals("")) {
            searchEnginePresenter.MostrarMensaje("Debe ingresar un dato para poderlo buscar");
        } else {
            if(Util.ValidarAccesoInternet(context)) {
                try {
                    retrofitManager.ConsultarInformacionOnline(searchEnginePresenter, datoBuscado);
                } catch (Exception ex) {
                    searchEnginePresenter.MostrarMensaje(ex.getMessage());
                }
            }
            else{
                HandlerOffline handler = new HandlerOffline(context, categoria, searchEnginePresenter);
                handler.ConsultarInfotmacionOffline(datoBuscado);
            }
        }
    }

    @Override
    public void CrearBD(Context context) {
        sqlHandler = new SQLiteHandler(context);
        sqlHandler.CrearTablas();
    }

    @Override
    public List<Categoria> ObtenerCategorias() {
        return sqlHandler.ObtenerCategorias();
    }

    @Override
    public void NotificarCierreProgressDialog() {
        searchEnginePresenter.NotificarCierreProgressDialog();
    }
}
