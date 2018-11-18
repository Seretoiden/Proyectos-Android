package com.example.unaicanales.ejerciciolistview;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class Bbdd extends SQLiteOpenHelper{

    private static final String DATABASE_NAME = "LaLiga";
    private static final int DATABASE_VERSION = 1;

    //SQL que se ejecuta para generar la base de datos
    private static final String DATABASE_CREATE = "create table Equipos(" +
            "id integer primary key," +
            "nombre text not null," +
            "descripcion text not null," +
            "dorsal int not null," +
            "imagen text not null" +
            ");";

    //SQL que se ejecuta para generar la base de datos
    private static final String INSERT_TEAMS = "INSERT INTO Equipos(id, nombre, descripcion, dorsal, imagen)" +
            "VALUES(1, 'Lionel Messi', 'Lionel Andrés Messi Cuccittini (Rosario, 24 de junio de 1987), más conocido como Leo Messi,\u200B es un futbolista argentino que juega como delantero.', " +
            "10, " + R.mipmap.barcelona_logo + "), (2, 'Luis Suarez', 'Barcelona es una ciudad española, capital de la comunidad autónoma de Cataluña, de la comarca del Barcelonés y de la provincia homónima.', " +
            "9, " + R.mipmap.barcelona_logo + "), (3, 'Sergio Ramos', 'El Real Madrid Club de Fútbol, más conocido simplemente como Real Madrid, es una entidad polideportiva con sede en Madrid, España.', " +
            "4, " + R.mipmap.madrid_logo + "), (4, 'Rafael Varane', 'La Real Sociedad de Fútbol es un club de fútbol español, de la ciudad de San Sebastián, Guipúzcoa, que milita en la Primera División de España.', " +
            "5, " + R.mipmap.madrid_logo + "), (5, 'Asier Ilarramendi', 'Barcelona es una ciudad española, capital de la comunidad autónoma de Cataluña, de la comarca del Barcelonés y de la provincia homónima.', " +
            "4, " + R.mipmap.realsociedad_logo + "), (6, 'Willian DaSilva', 'Barcelona es una ciudad española, capital de la comunidad autónoma de Cataluña, de la comarca del Barcelonés y de la provincia homónima.', " +
            "12, " + R.mipmap.realsociedad_logo + ");";


    public Bbdd(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);
        db.execSQL(INSERT_TEAMS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(SQLiteOpenHelper.class.getName(),
                "Actualizando la version de la BBDD " + oldVersion + " a "
                        + newVersion + ", se borrarán todos los datos.");
        db.execSQL("DROP TABLE IF EXISTS Equipos");
        onCreate(db);
    }

    public ArrayList<Equipo> leerEquiposDesdeBBDD() {
        ArrayList<Equipo> equipos = new ArrayList<Equipo>();

        String[] cols = new String[] {"id", "nombre", "descripcion", "dorsal", "imagen"};
        Cursor mCursor = getReadableDatabase().query(true, "Equipos",cols,null
                , null, null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }

        int posArray = 0;

        for(mCursor.moveToFirst(); posArray < mCursor.getCount(); mCursor.moveToNext()) {
            Equipo nuevo = new Equipo();
            // The Cursor is now set to the right position
            nuevo.setId(Integer.parseInt(mCursor.getString(0)));
            nuevo.setNombre(mCursor.getString(1));
            nuevo.setDescripcion(mCursor.getString(2));
            nuevo.setDorsal(Integer.parseInt(mCursor.getString(3)));
            nuevo.setImagen(Integer.parseInt(mCursor.getString(4)));

            System.out.println("nombre: " + nuevo.getNombre());
            equipos.add(posArray, nuevo);

            posArray++;
        }
        System.out.println("nombre: " + equipos.get(0).getNombre());

        return equipos;
    }

    public Equipo buscarEquipo(int idEquipo){
        String[] cols = new String[] {"id", "nombre", "descripcion", "dorsal", "imagen"};

        Cursor mCursor = getReadableDatabase().query("Equipos", cols,"id=?", new String[]{ String.valueOf(idEquipo) },
                null, null, null);

        if (mCursor != null) {
            mCursor.moveToFirst();
        }

        Equipo equipo = new Equipo();
        // The Cursor is now set to the right position
        equipo.setId(Integer.parseInt(mCursor.getString(0)));
        equipo.setNombre(mCursor.getString(1));
        equipo.setDescripcion(mCursor.getString(2));
        equipo.setDorsal(Integer.parseInt(mCursor.getString(3)));
        equipo.setImagen(Integer.parseInt(mCursor.getString(4)));

        return equipo;
    }

    public void nuevoEquipo(Equipo equipo) {
        int id = 0;
        Cursor cursor = getReadableDatabase().rawQuery("SELECT MAX(id) FROM Equipos;", null);
        if(cursor == null){
            System.out.println("ERROR EN EL CURSOR");
        }else{
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                id = cursor.getInt(0) + 1;
            }
            equipo.setId(id);
            getWritableDatabase().insert("Equipos", null, nuevoEquipoValues(equipo));
        }

    }

    public void modificarEquipo(Equipo equipo){
        System.out.println("MODIFICAMOS A " + equipo.getNombre());
        getWritableDatabase().update("Equipos", nuevoEquipoValues(equipo), "id=?", new String[]{ String.valueOf(equipo.getId()) });
    }

    public void eliminar(int idEquipo){
        System.out.println("ELIMINAMOS A " + idEquipo);
        getWritableDatabase().delete("Equipos","id=?", new String[]{ String.valueOf(idEquipo) });
    }

   /* public void consultarId(){
        getReadableDatabase().query();
    }*/

    public ContentValues nuevoEquipoValues(Equipo equipo){
        ContentValues nuevoRegistro = new ContentValues();
        nuevoRegistro.put("id", equipo.getId());
        nuevoRegistro.put("nombre", equipo.getNombre());
        nuevoRegistro.put("descripcion", equipo.getDescripcion());
        nuevoRegistro.put("dorsal", equipo.getDorsal());
        nuevoRegistro.put("imagen", equipo.getImagen());

        return nuevoRegistro;
    }

}
