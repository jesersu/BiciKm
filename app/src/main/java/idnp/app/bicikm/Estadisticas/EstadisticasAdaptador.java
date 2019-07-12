package idnp.app.bicikm.Estadisticas;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import idnp.app.bicikm.Objetos.Recorrido;
import idnp.app.bicikm.R;

public class EstadisticasAdaptador extends BaseAdapter {
    List<Recorrido> recorridoList = new ArrayList<>();
    LayoutInflater layoutInflater;
    Context context;

    public EstadisticasAdaptador (List<Recorrido> recorridoList, Context context) {
        this.recorridoList = recorridoList;
        this.context = context;
        layoutInflater=(LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        final View vista = layoutInflater.inflate(R.layout.listview_row, null);
        TextView fecha = (TextView) vista.findViewById(R.id.fecha);
        TextView latitud = (TextView) vista.findViewById(R.id.latitud);
        TextView longitud = (TextView) vista.findViewById(R.id.longitud);

        Recorrido recor =getItem(i);
        //Usuario transnew = getItem(i);
        fecha.setText(recor.getFecha());
        latitud.setText(recor.getLatitud());
        longitud.setText(recor.getLongitud());
        return vista;
    }

    @Override
    public int getCount() {
        return recorridoList.size();
    }

    @Override
    public Recorrido getItem(int position) {
        return recorridoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
}
