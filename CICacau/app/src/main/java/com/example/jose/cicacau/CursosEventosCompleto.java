package com.example.jose.cicacau;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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


public class CursosEventosCompleto extends Activity {

    ArrayList<String> titulo = ListCursosEventos.titulo;
    ArrayList<String> conteudo = new ArrayList<String>();
    ArrayList<String> local = new ArrayList<String>();
    ArrayList<String> contato = new ArrayList<String>();
    ArrayList<String> fonte = new ArrayList<String>();
    ArrayList<String> link = new ArrayList<String>();
    ArrayList<String>data = ListCursosEventos.data;
    int index = ListCursosEventos.index;
    String htmlCompleto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cursos_eventos_completo);
        htmlCompleto = ListCursosEventos.htmlCompleto;
        conteudo = cursosConteudo(htmlCompleto);
        local = cursosLocal(htmlCompleto);
        contato = cursosContato(htmlCompleto);
        fonte = cursosFonte(htmlCompleto);
        link = cursosLink(htmlCompleto);

        TextView textTitulo = (TextView)findViewById(R.id.titulo);
        textTitulo.setText(titulo.get(index).toString().substring(32));
        TextView textConteudo = (TextView)findViewById(R.id.conteudo);
        textConteudo.setText(conteudo.get(0).toString().substring(30));
        TextView textData = (TextView)findViewById(R.id.pData);
        textData.setText(data.get(index).toString().substring(15));
        TextView textLocal = (TextView)findViewById(R.id.pLocal);
        textLocal.setText(local.get(0).toString().substring(7));
        TextView textContato = (TextView)findViewById(R.id.pContato);
        textContato.setText(contato.get(0).toString().substring(9));
        TextView textFonte = (TextView)findViewById(R.id.pFonte);
        textFonte.setText(fonte.get(0).toString().substring(2));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_cursos_eventos_completo, menu);
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


    public static ArrayList<String> cursosConteudo(String html){
        ArrayList<String> listConteudo = new ArrayList<String>();
        String linha = null;
        int inicio, fim;

        if (html != null) {
            inicio = html.indexOf("<p style=" + '"' + "line-height: 150%;" + '"' + ">", 0);
            fim = html.indexOf("<br/>", inicio);
            linha = html.substring(inicio, fim);
            listConteudo.add(linha);

        }

        return listConteudo;
    }

    public static ArrayList<String> cursosLocal(String html){
        ArrayList<String> listLocal = new ArrayList<String>();
        String linha = null;
        int inicio, fim;

        if (html != null) {
            inicio = html.indexOf("<b>Local:", 0);
            fim = html.indexOf("<br/>", inicio);
            linha = html.substring(inicio, fim);
            listLocal.add(linha);

            String temp = tratarSubLinha(listLocal.get(0));
            listLocal.set(0, temp);
        }

        return listLocal;
    }

    public static ArrayList<String> cursosContato(String html){
        ArrayList<String> listContato = new ArrayList<String>();
        String linha = null;
        int inicio, fim;

        if (html != null) {
            inicio = html.indexOf("<b>Contato: </b>", 0);
            fim = html.indexOf("</p>", inicio);
            linha = html.substring(inicio, fim);
            listContato.add(linha);

            String temp = tratarSubLinha(listContato.get(0));
            listContato.set(0, temp);
        }

        return listContato;
    }

    public static ArrayList<String> cursosFonte(String html){
        ArrayList<String> listFonte = new ArrayList<String>();
        String linha = null;
        int inicio, fim;

        if (html != null) {
            inicio = html.indexOf("<b>Contato: </b>", 0);
            fim = html.indexOf("</p>", inicio);
            inicio = html.indexOf("href=", fim);
            fim = html.indexOf('"' + ">", inicio);
            inicio = fim;
            fim = html.indexOf("</a>", inicio);
            linha = html.substring(inicio, fim);
            listFonte.add(linha);

            String temp = tratarSubLinha(listFonte.get(0));
            listFonte.set(0, temp);
        }

        return listFonte;
    }

    public static ArrayList<String> cursosLink(String html){
        ArrayList<String> listLink = new ArrayList<String>();
        String linha = null;
        int inicio, fim;

        if (html != null) {
            inicio = html.indexOf("<b>Contato: </b>", 0);
            fim = html.indexOf("</p>", inicio);
            inicio = html.indexOf("href=", fim);
            fim = html.indexOf('"' + ">", inicio);
            linha = html.substring(inicio, fim);
            listLink.add(linha);

            String temp = tratarSubLinha(listLink.get(0));
            listLink.set(0, temp);
        }

        return listLink;
    }

    private static String tratarSubLinha(String string) {
        // TODO Auto-generated method stub
        String nova = Html.fromHtml(string).toString();
        return nova;
    }

    public void carregaFonte(View v){
        Uri uri = Uri.parse(link.get(0).toString().substring(6));
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }
}
