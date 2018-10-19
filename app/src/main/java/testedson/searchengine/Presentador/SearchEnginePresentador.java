package testedson.searchengine.Presentador;

import android.content.Context;

import java.util.List;
import java.util.concurrent.CancellationException;

import testedson.searchengine.Controles.DatosGrilla;
import testedson.searchengine.Entidades.Categoria;
import testedson.searchengine.Interactor.ISearchEngineInteractor;
import testedson.searchengine.Interactor.SearchEngineInteractor;
import testedson.searchengine.Vista.ISearchEngineView;

public class SearchEnginePresentador implements ISearchEnginePresenter {

        private ISearchEngineView searchEngineView;
        private ISearchEngineInteractor searchEngineInteractor;

        public SearchEnginePresentador(ISearchEngineView view, Context context){
            this.searchEngineView = view;
            searchEngineInteractor = new SearchEngineInteractor(this, context);
        }

    @Override
    public void MostrarMensaje(String mensaje) {
        if(searchEngineView != null ) {
            searchEngineView.MostrarMensaje(mensaje);
        }
    }

    @Override
    public void MostrarResultado(List<DatosGrilla> resultado) {
        if(searchEngineView != null ) {
            searchEngineView.MostrarResultado(resultado);
        }
    }

    @Override
    public void Buscar(String data, Categoria categoriaSeleccionada) {
        if(searchEngineView != null ) {
            searchEngineInteractor.Buscar(data, categoriaSeleccionada);
        }
    }

    @Override
    public void CrearBD(Context context) {
        searchEngineInteractor.CrearBD(context);
    }

    @Override
    public List<Categoria> ObtenerCategorias() {
        return searchEngineInteractor.ObtenerCategorias();
    }

    @Override
    public void NotificarCierreProgressDialog() {
        searchEngineView.NotificarCierreProgressDialog();
    }
}
