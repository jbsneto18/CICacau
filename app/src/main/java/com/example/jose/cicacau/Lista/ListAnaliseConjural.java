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

import com.example.jose.cicacau.Telas.AnaliseConjuralCompleta;
import com.example.jose.cicacau.Telas.MenuPrincipal;
import com.example.jose.cicacau.R;

import java.util.ArrayList;


public class ListAnaliseConjural extends ActionBarActivity {

    public static ArrayList<String> titulo = new ArrayList<String>();
    public static ArrayList<String>data = new ArrayList<String>();
    public static ArrayList<String>link = new ArrayList<String>();
    public static int index;
    String html;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_analise_conjural);
        html = MenuPrincipal.html;
        titulo = analiseTitulo(html);
        data = analiseData(html);
        link = analiseLink(html);

        ListView listview;
        listview =  (ListView) findViewById(R.id.MyListAnaliseConjural);
        MeuArrayAdapter adapterCodigos = new MeuArrayAdapter(this, titulo, data);
        listview.setAdapter(adapterCodigos);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
                index = position;
                analiseCompleta(null);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_list_analise_conjural, menu);
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
        private final ArrayList<String> data;

        public MeuArrayAdapter(Context context, ArrayList<String> titulo, ArrayList<String> data) {
            super(context, R.layout.listviewanaliseconjural, titulo);
            this.context = context;
            this.titulo = titulo;
            this.data = data;

        }

        @Override
        public View getView(int posicao, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.listviewanaliseconjural, parent, false);
            TextView textTitulo = (TextView) rowView.findViewById(R.id.titulo);
            textTitulo.setText(titulo.get(posicao).toString().substring(17));
            TextView textData = (TextView) rowView.findViewById(R.id.data);
            textData.setText(data.get(posicao).toString().substring(19));
            return rowView;
        }
    }


    public static ArrayList<String> analiseTitulo(String html){
        ArrayList<String> listTitulo = new ArrayList<String>();
        String linha = null;
        int inicio, fim;

        if (html != null) {
            inicio = html.indexOf("class=underlined><a>", 0);
            while (inicio != (-1)) {
                fim = html.indexOf("</a>", inicio);
                linha = html.substring(inicio, fim);
                listTitulo.add(linha);
                inicio = html.indexOf("class=underlined><a>", fim);
            }

            for (int i = 0; i < listTitulo.size(); i++) {
                String temp = tratarSubLinha(listTitulo.get(i));
                listTitulo.set(i, temp);
            }
        }

        return listTitulo;
    }

    public static ArrayList<String> analiseData(String html){
        ArrayList<String> listData = new ArrayList<String>();
        String linha = null;
        int inicio, fim;

        if (html != null) {
            inicio = html.indexOf("titulooutros_si", 0);
            while (inicio != (-1)) {
                fim = html.indexOf("<h3", inicio);
                linha = html.substring(inicio, fim);
                listData.add(linha);
                inicio = html.indexOf("titulooutros_si", fim);
            }

            for (int i = 0; i < listData.size(); i++) {
                String temp = tratarSubLinha(listData.get(i));
                listData.set(i, temp);
            }
        }

        return listData;
    }

    public static ArrayList<String> analiseLink(String html){
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

    public void analiseCompleta(View v){
        Intent intent = new Intent(this, AnaliseConjuralCompleta.class);
        startActivity(intent);
    }
}
