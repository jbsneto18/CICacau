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


public class ListPatentes extends ActionBarActivity {

    static ArrayList<String> titulo = new ArrayList<String>();
    static ArrayList<String>protocolo = new ArrayList<String>();
    static ArrayList<String>conteudo = new ArrayList<String>();
    static ArrayList<String>depositante = new ArrayList<String>();
    static ArrayList<String>inventor = new ArrayList<String>();
    static ArrayList<String>procurador = new ArrayList<String>();
    static ArrayList<String>link = new ArrayList<String>();
    static int index;
    String html;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_patentes);
        html = MenuPrincipal.html;
        titulo = patentesTitulo(html);
        protocolo = patentesProtocolo(html);
        conteudo = patentesConteudo(html);
        depositante = patentesDepositante(html);
        inventor = patentesInventor(html);
        procurador = patentesProcurador(html);
        link = patentesLink(html);

        ListView listview;
        listview =  (ListView) findViewById(R.id.MyListPatentes);
        MeuArrayAdapter adapterCodigos = new MeuArrayAdapter(this, titulo, protocolo);
        listview.setAdapter(adapterCodigos);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
                index = position;
                patenteCompleta(null);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_list_patentes, menu);
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
        private final ArrayList<String> protocolo;

        public MeuArrayAdapter(Context context, ArrayList<String> titulo, ArrayList<String> protocolo) {
            super(context, R.layout.listviewpatentes, titulo);
            this.context = context;
            this.titulo = titulo;
            this.protocolo = protocolo;

        }

        @Override
        public View getView(int posicao, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.listviewpatentes, parent, false);
            TextView textTitulo = (TextView) rowView.findViewById(R.id.titulo);
            textTitulo.setText(titulo.get(posicao).toString().substring(17));
            TextView textProtocolo = (TextView) rowView.findViewById(R.id.protocolo);
            textProtocolo.setText(protocolo.get(posicao).toString().substring(22));
            return rowView;
        }
    }

    public static ArrayList<String> patentesTitulo(String html){
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

    public static ArrayList<String> patentesProtocolo(String html){
        ArrayList<String> listProtocolo = new ArrayList<String>();
        String linha = null;
        int inicio, fim;

        if (html != null) {
            inicio = html.indexOf("titulooutros_si", 0);
            while (inicio != (-1)) {
                fim = html.indexOf("</h4>", inicio);
                linha = html.substring(inicio, fim);
                listProtocolo.add(linha);
                inicio = html.indexOf("titulooutros_si", fim);
            }

        }


        return listProtocolo;
    }

    public static ArrayList<String> patentesConteudo(String html){
        ArrayList<String> listConteudo = new ArrayList<String>();
        String linha = null;
        int inicio, fim, aux;

        if (html != null) {
            inicio = html.indexOf("Procurador:", 0);
            while (inicio != (-1)) {
                fim = html.indexOf("</p>", inicio);
                linha = html.substring(inicio, fim);
                aux = linha.indexOf("<br/><br/>", 0);
                linha = linha.substring(aux);
                listConteudo.add(linha);
                inicio = html.indexOf("Procurador:", fim);
            }

            for (int i = 0; i < listConteudo.size(); i++) {
                String temp = tratarSubLinha(listConteudo.get(i));
                listConteudo.set(i, temp);
            }
        }


        return listConteudo;
    }

    public static ArrayList<String> patentesDepositante(String html){
        ArrayList<String> listDepositante = new ArrayList<String>();
        String linha = null;
        int inicio, fim, aux;

        if (html != null) {
            inicio = html.indexOf("Depositante:", 0);
            while (inicio != (-1)) {
                fim = html.indexOf("<br/>", inicio);
                linha = html.substring(inicio, fim);
                listDepositante.add(linha);
                inicio = html.indexOf("Depositante:", fim);
            }

            for (int i = 0; i < listDepositante.size(); i++) {
                String temp = tratarSubLinha(listDepositante.get(i));
                listDepositante.set(i, temp);
            }
        }


        return listDepositante;
    }

    public static ArrayList<String> patentesInventor(String html){
        ArrayList<String> listInventor = new ArrayList<String>();
        String linha = null;
        int inicio, fim, aux;

        if (html != null) {
            inicio = html.indexOf("Inventor:", 0);
            while (inicio != (-1)) {
                fim = html.indexOf("<br/>", inicio);
                linha = html.substring(inicio, fim);
                listInventor.add(linha);
                inicio = html.indexOf("Inventor:", fim);
            }

            for (int i = 0; i < listInventor.size(); i++) {
                String temp = tratarSubLinha(listInventor.get(i));
                listInventor.set(i, temp);
            }
        }


        return listInventor;
    }

    public static ArrayList<String> patentesProcurador(String html){
        ArrayList<String> listProcurador = new ArrayList<String>();
        String linha = null;
        int inicio, fim, aux;

        if (html != null) {
            inicio = html.indexOf("Procurador:", 0);
            while (inicio != (-1)) {
                fim = html.indexOf("<br/>", inicio);
                linha = html.substring(inicio, fim);
                listProcurador.add(linha);
                inicio = html.indexOf("Procurador:", fim);
            }

            for (int i = 0; i < listProcurador.size(); i++) {
                String temp = tratarSubLinha(listProcurador.get(i));
                listProcurador.set(i, temp);
            }
        }


        return listProcurador;
    }

    public static ArrayList<String> patentesLink(String html){
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

    public void patenteCompleta(View v){
        Intent intent = new Intent(this, PatenteCompleta.class);
        startActivity(intent);
    }

}
