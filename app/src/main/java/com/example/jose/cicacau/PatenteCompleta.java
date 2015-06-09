package com.example.jose.cicacau;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

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

        TextView textTitulo = (TextView) findViewById(R.id.titulo);
        textTitulo.setText(titulo.get(index).toString().substring(17));
        TextView textConteudo = (TextView) findViewById(R.id.conteudo);
        textConteudo.setText(conteudo.get(index).toString().substring(2));
        fim = protocolo.get(index).indexOf('/', 0);
        prot = protocolo.get(index).toString().substring(0, fim);
        TextView textProtocolo = (TextView) findViewById(R.id.pProtocolo);
        textProtocolo.setText(prot.substring(33));
        inicio = fim;
        fim = protocolo.get(index).indexOf('/', inicio+1);
        dep = protocolo.get(index).toString().substring(inicio, fim);
        TextView textDatDep = (TextView) findViewById(R.id.pDatDep);
        textDatDep.setText(dep.substring(20));
        pub = protocolo.get(index).toString().substring(fim+1);
        TextView textDatPub = (TextView) findViewById(R.id.pDatPub);
        textDatPub.setText(pub.substring(20));
        TextView textDepositante = (TextView) findViewById(R.id.pDepositante);
        textDepositante.setText(depositante.get(index).toString().substring(13));
        TextView textInventor = (TextView) findViewById(R.id.pInventor);
        textInventor.setText(inventor.get(index).toString().substring(10));
        TextView textProcurador = (TextView) findViewById(R.id.pProcurador);
        textProcurador.setText(procurador.get(index).toString().substring(12));
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
