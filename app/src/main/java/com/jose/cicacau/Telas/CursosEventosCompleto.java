package com.jose.cicacau.Telas;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.webkit.WebView;

import com.jose.cicacau.Lista.ListCursosEventos;
import com.jose.cicacau.R;

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

        WebView webConteudo = (WebView) findViewById(R.id.webConteudo);
        String text = "<html><body>"
                + "<p align=\"justify\"><b> <font size=\"4\">"
                + titulo.get(index).toString().substring(32)
                + "</font></b></p>"
                +"<p align=\"justify\">"
                +conteudo.get(0).toString().substring(30)
                + "</p> "
                +"<p align=\"justify\"> <b> Data:</b> "
                +data.get(index).toString().substring(15)
                + "</p> "
                +"<p align=\"justify\"> <b> Local:</b> "
                +local.get(0).toString().substring(7)
                + "</p> "
                +"<p align=\"justify\"> <b> Contato</b> "
                +contato.get(0).toString().substring(9)
                + "</p> "
                +"<p align=\"justify\"> <b> Fonte:</b> "
                +"<a href=" +link.get(0).toString().substring(6)+ ">" + fonte.get(0).toString().substring(2) + "</a>"
                + "</p> "
                + "</body></html>";

        webConteudo.loadData(text, "text/html;charset=UTF-8", null);

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

}
