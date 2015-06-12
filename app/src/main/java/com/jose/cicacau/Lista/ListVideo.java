package com.jose.cicacau.Lista;

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

import com.jose.cicacau.R;
import com.jose.cicacau.Telas.MenuPrincipal;
import com.jose.cicacau.Telas.VideoCompleto;

import java.util.ArrayList;


public class ListVideo extends ActionBarActivity {

    public static ArrayList<String> titulo = new ArrayList<String>();
    public static ArrayList<String>data = new ArrayList<String>();
    public static ArrayList<String> conteudo = new ArrayList<String>();
    public static ArrayList<String>autores = new ArrayList<String>();
    public static ArrayList<String>link = new ArrayList<String>();
    public static ArrayList<String>linkVideo = new ArrayList<String>();
    public static int index;
    String html;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_video);
        html = MenuPrincipal.html;
        titulo = videosTitulo(html);
        data = videosData(html);
        conteudo = videosConteudo(html);
        autores = videosAutores(html);
        link = videosLink(html);
        linkVideo = idVideo(html);


        ListView listview;
        listview =  (ListView) findViewById(R.id.MyListVideos);
        MeuArrayAdapter adapterCodigos = new MeuArrayAdapter(this, titulo, data);
        listview.setAdapter(adapterCodigos);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
                index = position;
                videoCompleto(null);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_list_video, menu);
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
            super(context, R.layout.listviewvideos, titulo);
            this.context = context;
            this.titulo = titulo;
            this.data = data;

        }

        @Override
        public View getView(int posicao, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.listviewvideos, parent, false);
            TextView textTitulo = (TextView) rowView.findViewById(R.id.titulo);
            textTitulo.setText(titulo.get(posicao).toString().substring(17));
            TextView textData = (TextView) rowView.findViewById(R.id.data);
            textData.setText(data.get(posicao).toString().substring(19));
            return rowView;
        }
    }

    public static ArrayList<String> videosTitulo(String html){
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

    public static ArrayList<String> videosData(String html){
        ArrayList<String> listData = new ArrayList<String>();
        String linha = null;
        int inicio, fim;

        if (html != null) {
            inicio = html.indexOf("titulooutros_si", 0);
            while (inicio != (-1)) {
                fim = html.indexOf("</h4>", inicio);
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

    public static ArrayList<String> videosConteudo(String html){
        ArrayList<String> listConteudo = new ArrayList<String>();
        String linha = null;
        int inicio, fim;

        if (html != null) {
            inicio = html.indexOf("<div id=" + '"' + "t_resumo21" + '"' + ">", 0);
            while (inicio != (-1)) {
                fim = html.indexOf("</div>", inicio);
                linha = html.substring(inicio, fim);
                listConteudo.add(linha);
                inicio = html.indexOf("<div id=" + '"' + "t_resumo21" + '"' + ">", fim);
            }

        }

        return listConteudo;
    }

    public static ArrayList<String> videosLink(String html){
        ArrayList<String> listAutores = new ArrayList<String>();
        String linha = null;
        int inicio, fim;

        if (html != null) {
            inicio = html.indexOf("Autor(es): <a href=" + '"', 0);
            while (inicio != (-1)) {
                fim = html.indexOf('"' + ">", inicio);
                linha = html.substring(inicio, fim);
                listAutores.add(linha);
                inicio = html.indexOf("Autor(es): <a href=" + '"', fim);
            }
        }

        return listAutores;
    }

    public static ArrayList<String> videosAutores(String html){
        ArrayList<String> listAutores = new ArrayList<String>();
        String linha = null;
        int inicio, fim;

        if (html != null) {
            inicio = html.indexOf("Autor(es): <a href=" + '"', 0);
            while (inicio != (-1)) {
                fim = html.indexOf("</a>", inicio);
                linha = html.substring(inicio, fim);
                listAutores.add(linha);
                inicio = html.indexOf("Autor(es): <a href=" + '"', fim);
            }

            for (int i = 0; i < listAutores.size(); i++) {
                String temp = tratarSubLinha(listAutores.get(i));
                listAutores.set(i, temp);
            }
        }

        return listAutores;
    }

    public static ArrayList<String> idVideo(String html){
        ArrayList<String> listAutores = new ArrayList<String>();
        String linha = null;
        int inicio, fim;
        if (html != null) {
            inicio = html.indexOf("iframe style=", 0);
            while (inicio != (-1)) {
                fim = html.indexOf('"' + ">", inicio);
                linha = html.substring(inicio, fim);
                listAutores.add(linha);
                inicio = html.indexOf("iframe style=", fim);
            }
        }


        return listAutores;
    }


    private static String tratarSubLinha(String string) {
        // TODO Auto-generated method stub
        String nova = Html.fromHtml(string).toString();
        return nova;
    }

    public void videoCompleto(View v){
        Intent intent = new Intent(this, VideoCompleto.class);
        startActivity(intent);
    }
}
