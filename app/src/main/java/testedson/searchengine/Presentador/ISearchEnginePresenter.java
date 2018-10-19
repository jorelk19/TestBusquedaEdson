package testedson.searchengine.Presentador;

import android.content.Context;

import java.util.List;

import testedson.searchengine.Controles.DatosGrilla;
import testedson.searchengine.Entidades.Categoria;

public interface ISearchEnginePresenter {
    void MostrarMensaje(String mensaje);
    void MostrarResultado(List<DatosGrilla> resultado);
    void Buscar(String data, Categoria categoriaSeleccionada);
    void CrearBD(Context context);
    List<Categoria> ObtenerCategorias();
    void NotificarCierreProgressDialog();
}
