package testedson.searchengine.Controles;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import testedson.searchengine.R;

public class GridAdapter extends RecyclerView.Adapter<ViewHolderDatosGrilla> {

    private Context context;
    private List<DatosGrilla> listaDatosGrilla;
    DatosGrilla datosGrilla;

    public GridAdapter(Context context, List<DatosGrilla> objects) {
        this.context = context;
        listaDatosGrilla = objects;
    }

    @NonNull
    @Override
    public ViewHolderDatosGrilla onCreateViewHolder(ViewGroup parent, int i) {
        View viewHolder = LayoutInflater.from(parent.getContext()).inflate(R.layout.grilla_resultado, parent, false);
        return new ViewHolderDatosGrilla(viewHolder);
    }

    @Override
    public void onBindViewHolder(ViewHolderDatosGrilla holder, int pos) {

        datosGrilla =  listaDatosGrilla.get(pos);

        holder.txtNombre.setText(datosGrilla.getNombre());
        String link ="<a href='" + datosGrilla.getUrl() + "'>Mas información en la Web</a> ";
        holder.txtUrl.setText(Html.fromHtml(link));
        holder.txtNombreArtista.setText(listaDatosGrilla.get(pos).getNombreArtista());
        Glide.with(context).asBitmap().load(listaDatosGrilla.get(pos).getImagen()).into(holder.imageView);

        holder.txtUrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = datosGrilla.getUrl();

                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                context.startActivity(intent);
            }
        });




    }

    @Override
    public int getItemCount() {
        return listaDatosGrilla.size();
    }
}


class ViewHolderDatosGrilla extends RecyclerView.ViewHolder {

    TextView txtNombre, txtUrl, txtNombreArtista;
    CircleImageView imageView;

    LinearLayout parent;

    ViewHolderDatosGrilla(View itemView) {
        super(itemView);
        txtNombre = (TextView) itemView.findViewById(R.id.Nombre);
        txtUrl = (TextView) itemView.findViewById(R.id.Url);
        txtNombreArtista = (TextView) itemView.findViewById(R.id.NombreArtista);
        imageView = (CircleImageView) itemView.findViewById(R.id.imagen);
        parent = (LinearLayout) itemView.findViewById(R.id.grillaResultado);
    }
}