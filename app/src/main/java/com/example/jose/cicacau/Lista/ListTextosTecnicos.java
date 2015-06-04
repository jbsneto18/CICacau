package com.example.jose.cicacau.Lista;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.jose.cicacau.Telas.MenuPrincipal;
import com.example.jose.cicacau.R;
import com.example.jose.cicacau.Telas.TextoTecnicoCompleto;

import java.util.ArrayList;


public class ListTextosTecnicos extends ActionBarActivity {

    public static ArrayList<String> titulo = new ArrayList<String>();
    public static ArrayList<String>autores = new ArrayList<String>();
    public static ArrayList<String>link = new ArrayList<String>();
    public static int index;
    String html;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_textos_tecnicos);
        html = MenuPrincipal.html;
        titulo = textosTitulo(html);
        autores = textosAutores(html);
        link = textosLink(html);

        //Toast.makeText(this, link.get(0).toString(), Toast.LENGTH_LONG).show();
        ListView listview;
        listview =  (ListView) findViewById(R.id.MyListTextosTecnicos);
        MeuArrayAdapter adapterCodigos = new MeuArrayAdapter(this, titulo, autores);
        listview.setAdapter(adapterCodigos);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
                index = position;
                textoCompleto(null);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_list_textos_tecnicos, menu);
        return true;
    }

    /*@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/

    public class MeuArrayAdapter extends ArrayAdapter<String> {
        private final Context context;
        private final ArrayList<String> titulo;
        private final ArrayList<String> autores;

        public MeuArrayAdapter(Context context, ArrayList<String> titulo, ArrayList<String> autores) {
            super(context, R.layout.listviewtextostecnicos, titulo);
            this.context = context;
            this.titulo = titulo;
            this.autores = autores;

        }

        @Override
        public View getView(int posicao, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.listviewtextostecnicos, parent, false);
            TextView textTitulo = (TextView) rowView.findViewById(R.id.titulo);
            textTitulo.setText(titulo.get(posicao).toString());
            TextView textAutores = (TextView) rowView.findViewById(R.id.autores);
            textAutores.setText(autores.get(posicao).toString().substring(19));
            return rowView;
        }
    }

    public static ArrayList<String> textosTitulo(String html){
        ArrayList<String> listTitulo = new ArrayList<String>();
        String linha = null;
        int inicio, fim;

        if (html != null) {
            inicio = html.indexOf("titulooutros_si", 0);
            fim = html.indexOf("</h4>", inicio);
            inicio = html.indexOf("<a>", fim);

            while (inicio != (-1)) {
                fim = html.indexOf("</a>", inicio);
                linha = html.substring(inicio, fim);
                listTitulo.add(linha);
                //inicio = html.indexOf("titulooutros_si", fim);
                //fim = html.indexOf("</h4>", inicio);
                inicio = html.indexOf("<a>", fim);
            }

            for (int i = 0; i < listTitulo.size(); i++) {
                String temp = tratarSubLinha(listTitulo.get(i));
                listTitulo.set(i, temp);
            }
        }

        return listTitulo;
    }

    public static ArrayList<String> textosAutores(String html){
        ArrayList<String> listAutores = new ArrayList<String>();
        String linha = null;
        int inicio, fim;

        if (html != null) {
            inicio = html.indexOf("titulooutros_si", 0);
            while (inicio != (-1)) {
                fim = html.indexOf("</h4>", inicio);
                linha = html.substring(inicio, fim);
                listAutores.add(linha);
                inicio = html.indexOf("titulooutros_si", fim);
            }

            for (int i = 0; i < listAutores.size(); i++) {
                String temp = tratarSubLinha(listAutores.get(i));
                listAutores.set(i, temp);
            }
        }


        return listAutores;
    }

    public static ArrayList<String> textosLink(String html){
        ArrayList<String> listLink = new ArrayList<String>();
        String linha = null;
        int inicio, fim;

        if (html != null) {
            inicio = html.indexOf(".pdf", 0);
            while (inicio != (-1)) {
                fim = html.indexOf('"' + ">Download", inicio);
                linha = html.substring(inicio, fim);
                listLink.add(linha);
                inicio = html.indexOf(".pdf", fim);
            }

        }


        return listLink;
    }

    private static String tratarSubLinha(String string) {
        // TODO Auto-generated method stub
        String nova = Html.fromHtml(string).toString();
        return nova;
    }

    public void textoCompleto(View v){
        Intent intent = new Intent(this, TextoTecnicoCompleto.class);
        startActivity(intent);
    }
}
