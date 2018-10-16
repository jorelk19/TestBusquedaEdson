package testedson.searchengine.Interactor;

import android.content.Context;

import java.util.List;

import testedson.searchengine.Entidades.Categoria;

public interface ISearchEngineInteractor {
    void Buscar(String data, Categoria categoriaSeleccionada);
    void CrearBD(Context context);
    List<Categoria> ObtenerCategorias();
}
