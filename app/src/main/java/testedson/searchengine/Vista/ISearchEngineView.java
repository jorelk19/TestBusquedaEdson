package testedson.searchengine.Vista;

import android.view.View;

import java.util.List;

import testedson.searchengine.Controles.DatosGrilla;

public interface ISearchEngineView {
    void MostrarResultado(List<DatosGrilla> resultado);
    void MostrarMensaje(String mensaje);
}
