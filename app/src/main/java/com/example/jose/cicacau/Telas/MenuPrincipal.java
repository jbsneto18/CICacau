package com.example.jose.cicacau.Telas;

import com.example.jose.cicacau.Exit;
import com.example.jose.cicacau.Lista.ListAnaliseConjural;
import com.example.jose.cicacau.Lista.ListCursosEventos;
import com.example.jose.cicacau.Lista.ListLeis;
import com.example.jose.cicacau.Lista.ListNoticias;
import com.example.jose.cicacau.Lista.ListPatentes;
import com.example.jose.cicacau.Lista.ListTextosTecnicos;
import com.example.jose.cicacau.Lista.ListVideo;
import com.example.jose.cicacau.R;
import com.example.jose.cicacau.util.SystemUiHider;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;


/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 *
 * @see SystemUiHider
 */

public class MenuPrincipal extends Activity {

    private String url;
    public static String html;
    ProgressBar carrega;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_menu_principal);

        carrega = (ProgressBar) findViewById(R.id.carregando);
        carrega.setVisibility(View.GONE);

    }
    @Override
    public void onBackPressed(){
//        super.onBackPressed();
        DialogFragment exitDialog = new Exit();
        exitDialog.show(getFragmentManager(),"exit_dialog");
    }

    public void carregaAutor(View v){
        carrega.setVisibility(View.VISIBLE);
        Uri uri = Uri.parse("https://www.facebook.com/jose.neto.334");
       Intent intent = new Intent(Intent.ACTION_VIEW, uri);
       startActivity(intent);
        carrega.setVisibility(View.GONE);
    }

    public void carregaSite(View v){
        carrega.setVisibility(View.VISIBLE);
        Uri uri = Uri.parse("http://nbcgib.uesc.br/cicacau/index.php");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
        carrega.setVisibility(View.GONE);
    }

    //Carrega noticias
    public void carregaListaNoticias(View v){
        carrega.setVisibility(View.VISIBLE);
        url = "http://nbcgib.uesc.br/cicacau/cicacau_noticias.php";
        html = carregaHtml(null, url);
        Intent intent = new Intent(this, ListNoticias.class);
        startActivity(intent);
        carrega.setVisibility(View.GONE);

    }

    //carrega cursos e eventos
    public void carregaListaCursosEventos(View v){
        carrega.setVisibility(View.VISIBLE);
        url = "http://nbcgib.uesc.br/cicacau/cicacau_cursos-eventos.php";
        html = carregaHtml(null, url);
        Intent intent = new Intent(this, ListCursosEventos.class);
        startActivity(intent);
        carrega.setVisibility(View.GONE);

    }

    //carrega leis
    public void carregaListaLeis(View v){
        carrega.setVisibility(View.VISIBLE);
        url = "http://nbcgib.uesc.br/cicacau/cicacau_legislacoes.php";
        html = carregaHtml(null, url);
        Intent intent = new Intent(this, ListLeis.class);
        startActivity(intent);
        carrega.setVisibility(View.GONE);
    }

    //carrega Videos
    public void carregaListaVideos(View v){
        carrega.setVisibility(View.VISIBLE);
        url = "http://nbcgib.uesc.br/cicacau/cicacau_videos.php";
        html = carregaHtml(null, url);
        Intent intent = new Intent(this, ListVideo.class);
        startActivity(intent);
        carrega.setVisibility(View.GONE);
    }

    //Carrega analise conjural
    public void carregaListaAnaliseConjural(View v){
        carrega.setVisibility(View.VISIBLE);
        url = "http://nbcgib.uesc.br/cicacau/cicacau_analise.php";
        html = carregaHtml(null, url);
        Intent intent = new Intent(this, ListAnaliseConjural.class);
        startActivity(intent);
        carrega.setVisibility(View.GONE);

    }

    //Carrega patentes
    public void carregaListaPatentes(View v){
        carrega.setVisibility(View.VISIBLE);
        url = "http://nbcgib.uesc.br/cicacau/cicacau_patentes.php";
        html = carregaHtml(null, url);
        Intent intent = new Intent(this, ListPatentes.class);
        startActivity(intent);
        carrega.setVisibility(View.GONE);
    }

    //carrega textos tecnicos
    public void carregaListaTextosTecnicos(View v){
        carrega.setVisibility(View.VISIBLE);
        url = "http://nbcgib.uesc.br/cicacau/cicacau_producao.php?cat=9";
        html = carregaHtml(null, url);
        Intent intent = new Intent(this, ListTextosTecnicos.class);
        startActivity(intent);
        carrega.setVisibility(View.GONE);
    }


    //Carrega Html
    public String carregaHtml (View v, String url){

        InputStream content = null;
        BufferedReader leitor;
        StringBuilder str;
        String line = null;
        String html = null;
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            HttpClient httpclient = new DefaultHttpClient();
            HttpResponse response = httpclient.execute( new HttpGet(url ));
            content = response.getEntity().getContent();
            leitor = new BufferedReader(new InputStreamReader(content));
            str = new StringBuilder();

            while((line = leitor.readLine()) != null) {
                str.append(line);
            }

            content.close();
            html = str.toString();



        } catch (Exception e) {
            Toast.makeText(this, "Verifique a conexão com a internet", Toast.LENGTH_LONG).show();
            Log.e("[GET REQUEST]", "Network exception", e);
        }
        return html;
    }
}
