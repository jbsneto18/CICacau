package com.jose.cicacau.Telas;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.webkit.WebView;

import com.jose.cicacau.Lista.ListPatentes;
import com.jose.cicacau.R;

import java.util.ArrayList;


public class PatenteCompleta extends Activity {

    ArrayList<String> titulo = ListPatentes.titulo;
    ArrayList<String>protocolo = ListPatentes.protocolo;
    ArrayList<String>conteudo = ListPatentes.conteudo;
    ArrayList<String>depositante = ListPatentes.depositante;
    ArrayList<String>inventor = ListPatentes.inventor;
    ArrayList<String>procurador = ListPatentes.procurador;
    ArrayList<String>link = ListPatentes.link;
    int index = ListPatentes.index;
    String prot, dep, pub;
    int inicio, fim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patente_completa);

        WebView webConteudo = (WebView) findViewById(R.id.webConteudo);
        fim = protocolo.get(index).indexOf('/', 0);
        prot = protocolo.get(index).toString().substring(0, fim);
        inicio = fim;
        fim = protocolo.get(index).indexOf('/', inicio+1);
        dep = protocolo.get(index).toString().substring(inicio, fim);
        pub = protocolo.get(index).toString().substring(fim+1);

        String text = "<html><body>"
                + "<p align=\"justify\"><b> <font size=\"4\">"
                + titulo.get(index).toString().substring(17)
                + "</font></b></p>"
                +"<p align=\"justify\">"
                +conteudo.get(index).toString().substring(2)
                + "</p> "
                +"<p align=\"justify\"> <b> Protocolo:</b> "
                +prot.substring(33)
                + "</p> "
                +"<p align=\"justify\"> <b> Data do depósito:</b> "
                +dep.substring(20)
                + "</p> "
                +"<p align=\"justify\"> <b> Data da publicação</b> "
                +pub.substring(20)
                + "</p> "
                +"<p align=\"justify\"> <b> Depositante:</b> "
                +depositante.get(index).toString().substring(13)
                + "</p> "
                +"<p align=\"justify\"> <b> Inventor:</b> "
                +inventor.get(index).toString().substring(10)
                + "</p> "
                +"<p align=\"justify\"> <b> Procurador:</b> "
                +procurador.get(index).toString().substring(12)
                + "</p> "
                + "</body></html>";

        webConteudo.loadData(text, "text/html;charset=UTF-8", null);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_patente_completa, menu);
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

    public void download(View v){
        Uri uri = Uri.parse("http://nbcgib.uesc.br/cicacau/" + link.get(index).toString().substring(28));
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }
}
