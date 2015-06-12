package com.jose.cicacau.Telas;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.webkit.WebView;

import com.jose.cicacau.Lista.ListNoticias;
import com.jose.cicacau.R;

import java.util.ArrayList;


public class NoticiaCompleta extends Activity {

    ArrayList<String> titulo = ListNoticias.titulo;
    ArrayList<String> conteudo = new ArrayList<String>();
    ArrayList<String>categoria = ListNoticias.categoria;
    int index = ListNoticias.index;
    String htmlCompleto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noticia_completa);
        htmlCompleto = ListNoticias.htmlCompleto;
        conteudo = noticiasConteudo(htmlCompleto);

        WebView webConteudo = (WebView) findViewById(R.id.webConteudo);

        String text = "<html><body>"
                + "<p align=\"justify\"><b> <font size=\"4\">"
                + titulo.get(index).toString().substring(28)
                + "</font></b></p>"
                +"<p align=\"justify\">"
                +conteudo.get(0).toString()
                + "</p> "
                +"<p align=\"justify\">"
                +categoria.get(index).toString().substring(25)
                + "</p> "
                + "</body></html>";

        webConteudo.loadData(text, "text/html;charset=UTF-8", null);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_noticia_completa, menu);
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

    public static ArrayList<String> noticiasConteudo(String html){
        ArrayList<String> listConteudo = new ArrayList<String>();
        String linha = null;
        int inicio, fim;

        if (html != null) {
            inicio = html.indexOf("<p style=" + '"' + "line-height: 150%;" + '"' + ">", 0);
            fim = html.indexOf("</p>", inicio);
            linha = html.substring(inicio, fim);
            listConteudo.add(linha);

                String temp = tratarSubLinha(listConteudo.get(0));
                listConteudo.set(0, temp);
        }

        return listConteudo;
    }

    private static String tratarSubLinha(String string) {
        // TODO Auto-generated method stub
        String nova = Html.fromHtml(string).toString();
        return nova;
    }
}
