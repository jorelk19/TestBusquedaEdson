package testedson.searchengine.Vista;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Calendar;
import java.util.List;

import testedson.searchengine.Controles.DatosGrilla;
import testedson.searchengine.Controles.GridAdapter;
import testedson.searchengine.Entidades.Categoria;
import testedson.searchengine.Interactor.SearchEngineInteractor;
import testedson.searchengine.Presentador.ISearchEnginePresenter;
import testedson.searchengine.Presentador.SearchEnginePresentador;
import testedson.searchengine.R;

public class SearchEngineView extends AppCompatActivity implements ISearchEngineView {

    private EditText etTextoBuscado;
    private Button btnBuscar;
    private TextView tvResultado;
    private ISearchEnginePresenter presenter;
    private Snackbar snackBar;
    private View mainView;
    private GridView gvResultado;
    private RecyclerView rvDatosGrilla;
    private Spinner spCategoria;
    private List<Categoria> listaCategorias;
    private ArrayAdapter<Categoria> adapterCatergoria;
    private Categoria categoriasSeleccionada;
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainView = findViewById(R.id.VistaPrincipal);
        etTextoBuscado = (EditText)findViewById(R.id.etTextoBuscado);
        btnBuscar = (Button)findViewById(R.id.btnBuscar);
        spCategoria = (Spinner)findViewById(R.id.spCategoria);

        presenter = new SearchEnginePresentador(this, this);

    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.CrearBD(this);
        listaCategorias = presenter.ObtenerCategorias();
        listaCategorias.add(0, new Categoria(0, "Seleccione una categoría"));
        spCategoria.setOnItemSelectedListener(new setOnClickListener_Categoria());
        adapterCatergoria = new ArrayAdapter<>(this, R.layout.custom_spinner, listaCategorias);
        spCategoria.setAdapter(adapterCatergoria);
        spCategoria.setSelection(0);
    }

    public void BuscarTexto(View view){
        ShowProgressDialog();
        presenter.Buscar(etTextoBuscado.getText().toString(), categoriasSeleccionada);
    }

    private void ShowProgressDialog() {
        pDialog = new ProgressDialog(this, AlertDialog.THEME_DEVICE_DEFAULT_DARK);
        pDialog.setMessage("Procesando información...");
        pDialog.setCancelable(false);
        pDialog.show();
    }

    @Override
    public void MostrarResultado(List<DatosGrilla> resultado) {
        rvDatosGrilla = (RecyclerView) findViewById(R.id.rvGrillaResultado);
        GridAdapter grillaAdapter = new GridAdapter(this, resultado);
        rvDatosGrilla.setAdapter(grillaAdapter);
        rvDatosGrilla.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void MostrarMensaje(String mensaje) {
        Snackbar.make(mainView,mensaje, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void NotificarCierreProgressDialog() {
        pDialog.dismiss();
    }

    private class setOnClickListener_Categoria implements android.widget.AdapterView.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            categoriasSeleccionada = (Categoria) parent.getItemAtPosition(position);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }




}
