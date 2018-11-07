package com.example.unaicanales.ejerciciolistview;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


public class AdapterEquipo extends BaseAdapter {

    protected Activity activity;
    //Los items en forma de arraylist del listview
    protected ArrayList<Equipo> items;
    //backup antes de escribir para poder luego volver a poner los originales cuando se deje en blanco el edittext
    protected ArrayList<Equipo> originalItems;


    public AdapterEquipo (Activity activity, ArrayList<Equipo> items) {
        this.activity = activity;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    public void clear() {
        items.clear();
    }

    public void addAll(ArrayList<Equipo> equipo) {
        for (int i = 0; i < equipo.size(); i++) {
            items.add(equipo.get(i));
        }
    }

    @Override
    public Object getItem(int arg0) {
        return items.get(arg0);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (convertView == null) {
            LayoutInflater inf = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inf.inflate(R.layout.itemlistview, null);
        }

        Equipo dir = items.get(position);

        //Damos a los diferentes items del ListView el nombre, descripcion e imagen
        TextView nombre = (TextView) v.findViewById(R.id.nombre);
        nombre.setText(dir.getNombre());

        TextView description = (TextView) v.findViewById(R.id.descripcion);
        description.setText(dir.getDescripcion());

        ImageView imagen = (ImageView) v.findViewById(R.id.img);
        imagen.setImageResource(dir.getImagen());

        return v;
    }

    public Filter getFilter() {
        Filter filter = new Filter() {
            /*

                El ciclo de este filtro funciona de manera que cuando se escribe en el cuadro de texto,
                lo recibimos en el FilterResults como parámetro: el CharSequience constraint
                Después mediante el notifyDatasetChanged(), le daremos noticia que el array que contiene detrás
                el ListView se ha modificado, para que recargue.

             */

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence cadena, FilterResults resultados) {
                //El array principal y original, se le dan los valores de los resultados
                items = (ArrayList<Equipo>) resultados.values;
                //notificamos del cambio
                notifyDataSetChanged();
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                //Aqui recogeremos los resultados que nos devuelva el filtro
                FilterResults resultados = new FilterResults();
                //Array de objetos Equipo ya filtrado
                ArrayList<Equipo> equiposFiltrados = new ArrayList<Equipo>();

                //Guardamos el array ORIGINAL con los items ya cargados
                if (originalItems == null) {
                    originalItems = new ArrayList<Equipo>(items);
                }

                //Si el texto introducido es nulo o de longitud 0, devolveremos el array con los objetos originales
                if (constraint == null || constraint.length() == 0) {
                    resultados.count = originalItems.size();
                    resultados.values = originalItems;
                } else {
                    //Pasamos lo que haya escrito a minuscula y comprobamos con el nombre del item cogido
                    //AQUI PODEMOS FILTRAR POR LO QUE QUERAMOS, NOMBRE, DESCRIPCION, CUALQUIER ATRIBUTO DEL MODELO
                    constraint = constraint.toString().toLowerCase();
                    for(Equipo equipo : items){
                        String data = equipo.getNombre().toLowerCase();
                        if (data.startsWith(constraint.toString())) {
                            //Como coincide, pues lo añadimos al array de filtrados
                            equiposFiltrados.add(equipo);
                        }
                    }
                    // Lo filtrado lo añadimos al array que vamos a devolver
                    resultados.count = equiposFiltrados.size();
                    resultados.values = equiposFiltrados;
                }
                return resultados;
            }
        };
        return filter;
    }
}