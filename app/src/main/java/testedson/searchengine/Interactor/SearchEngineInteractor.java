package testedson.searchengine.Interactor;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import testedson.searchengine.Entidades.Categoria;
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
                    switch(categoria.getIdCategoria()) {
                        case 1:
                            retrofitManager.ObtenerInformacionAlbum(searchEnginePresenter, datoBuscado);
                            break;
                        case 2:
                            retrofitManager.ObtenerInformacionTopAlbumsArtist(searchEnginePresenter, datoBuscado);
                            break;
                        case 3:
                            retrofitManager.ObtenerInformacionTrack(searchEnginePresenter, datoBuscado);
                            break;
                        default:
                            searchEnginePresenter.MostrarMensaje("Debe seleccionar una categoría");
                            break;
                    }
                } catch (Exception ex) {
                    searchEnginePresenter.MostrarMensaje(ex.getMessage());
                }
            }
            else{
                HandlerOffline handler = new HandlerOffline(context);
                switch(categoria.getIdCategoria()) {
                    case 1:
                        handler.ObtenerInformacionAlbum(searchEnginePresenter, datoBuscado);
                        break;
                    case 2:
                        //retrofitManager.ObtenerInformacionArtist(searchEnginePresenter, data);
                        handler.ObtenerInformacionTopAlbumsArtist(searchEnginePresenter, datoBuscado);
                        break;
                    case 3:
                        handler.ObtenerInformacionTrack(searchEnginePresenter, datoBuscado);
                        //retrofitManager.ObtenerInformacionTopTrack(searchEnginePresenter, data);
                        break;
                    default:
                        searchEnginePresenter.MostrarMensaje("Debe seleccionar una categoría");
                        break;
                }
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
