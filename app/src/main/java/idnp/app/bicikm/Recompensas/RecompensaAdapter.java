package idnp.app.bicikm.Recompensas;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import idnp.app.bicikm.Objetos.Recompensa;
import idnp.app.bicikm.R;

public class RecompensaAdapter extends BaseAdapter {
    List<Recompensa> recompensaList = new ArrayList<>();
    LayoutInflater layoutInflater;
    Context context;

    public RecompensaAdapter (List<Recompensa> recompensaList, Context context) {
        this.recompensaList = recompensaList;
        this.context = context;
        layoutInflater=(LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        final View vista = layoutInflater.inflate(R.layout.listview_row_recom, null);
        TextView titulo = vista.findViewById(R.id.recomtitulo);
        TextView detalle = vista.findViewById(R.id.recomdetalle);
        TextView empresa = vista.findViewById(R.id.recomempresa);
        TextView costo = vista.findViewById(R.id.recomcosto);
        TextView foto = vista.findViewById(R.id.recomfoto);

        Recompensa recor = getItem(i);
        titulo.setText(recor.getTitulo());
        detalle.setText(recor.getDetalle());
        empresa.setText(recor.getEmpresa());
        costo.setText(recor.getCosto());
        foto.setText(recor.getFoto());

        return vista;
    }

    @Override
    public int getCount() {
        return recompensaList.size();
    }

    @Override
    public Recompensa getItem(int position) {
        return recompensaList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
}
