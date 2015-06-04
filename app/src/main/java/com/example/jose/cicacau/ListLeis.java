package com.example.jose.cicacau;

import android.content.Context;
import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class ListLeis extends ActionBarActivity {

    static ArrayList<String> titulo = new ArrayList<String>();
    static ArrayList<String>categoria = new ArrayList<String>();
    static ArrayList<String>conteudo = new ArrayList<String>();
    static ArrayList<String>link = new ArrayList<String>();
    static int index;
    String html;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_leis);
        html = MenuPrincipal.html;
        titulo = leisTitulo(html);
        categoria = leisCategoria(html);
        conteudo = leisConteudo(html);
        link = leisLink(html);

        ListView listview;
        listview =  (ListView) findViewById(R.id.MyListLeis);
        MeuArrayAdapter adapterCodigos = new MeuArrayAdapter(this, titulo, categoria);
        listview.setAdapter(adapterCodigos);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
                index = position;
                leiCompleta(null);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_list_leis, menu);
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
        private final ArrayList<String> categoria;

        public MeuArrayAdapter(Context context, ArrayList<String> titulo, ArrayList<String> categoria) {
            super(context, R.layout.listviewleis, titulo);
            this.context = context;
            this.titulo = titulo;
            this.categoria = categoria;

        }

        @Override
        public View getView(int posicao, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.listviewleis, parent, false);
            TextView textTitulo = (TextView) rowView.findViewById(R.id.titulo);
            textTitulo.setText(titulo.get(posicao).toString().substring(17));
            TextView textCategoria = (TextView) rowView.findViewById(R.id.categoria);
            textCategoria.setText(categoria.get(posicao).toString().substring(19));
            return rowView;
        }
    }

    public static ArrayList<String> leisTitulo(String html){
        ArrayList<String> listTitulo = new ArrayList<String>();
        String linha = null;
        int inicio, fim;

        if (html != null) {
            inicio = html.indexOf("class=underlined>", 0);
            while (inicio != (-1)) {
                fim = html.indexOf("</a>", inicio);
                linha = html.substring(inicio, fim);
                listTitulo.add(linha);
                inicio = html.indexOf("class=underlined>", fim);
            }

            for (int i = 0; i < listTitulo.size(); i++) {
                String temp = tratarSubLinha(listTitulo.get(i));
                listTitulo.set(i, temp);
            }
        }

        return listTitulo;
    }

    public static ArrayList<String> leisCategoria(String html){
        ArrayList<String> listCategoria = new ArrayList<String>();
        String linha = null;
        int inicio, fim;

        if (html != null) {
            inicio = html.indexOf("titulooutros_si", 0);
            while (inicio != (-1)) {
                fim = html.indexOf("</h4>", inicio);
                linha = html.substring(inicio, fim);
                listCategoria.add(linha);
                inicio = html.indexOf("titulooutros_si", fim);
            }

            for (int i = 0; i < listCategoria.size(); i++) {
                String temp = tratarSubLinha(listCategoria.get(i));
                listCategoria.set(i, temp);
            }
        }


        return listCategoria;
    }

    public static ArrayList<String> leisConteudo(String html){
        ArrayList<String> listCategoria = new ArrayList<String>();
        String linha = null;
        int inicio, fim;

        if (html != null) {
            inicio = html.indexOf("Download</a>", 0);
            while (inicio != (-1)) {
                fim = html.indexOf("</p>", inicio);
                linha = html.substring(inicio, fim);
                listCategoria.add(linha);
                inicio = html.indexOf("Download</a>", fim);
            }

            for (int i = 0; i < listCategoria.size(); i++) {
                String temp = tratarSubLinha(listCategoria.get(i));
                listCategoria.set(i, temp);
            }
        }


        return listCategoria;
    }

    public static ArrayList<String> leisLink(String html){
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

    public void leiCompleta(View v){
        Intent intent = new Intent(this, LeiCompleta.class);
        startActivity(intent);
    }
}
