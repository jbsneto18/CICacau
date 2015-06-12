package com.jose.cicacau.Telas;

import com.jose.cicacau.Exit;
import com.jose.cicacau.Fragmentos.CursosEventos;
import com.jose.cicacau.Lista.ListAnaliseConjural;
import com.jose.cicacau.Lista.ListCursosEventos;
import com.jose.cicacau.Lista.ListLeis;
import com.jose.cicacau.Lista.ListNoticias;
import com.jose.cicacau.Lista.ListPatentes;
import com.jose.cicacau.Lista.ListTextosTecnicos;
import com.jose.cicacau.Lista.ListVideo;
import com.jose.cicacau.R;
import com.jose.cicacau.util.SystemUiHider;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
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
import java.util.ArrayList;


/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 *
 * @see SystemUiHider
 */

public class MenuPrincipal extends Activity {

    private String urlCont, urlPag;
    public static String html;
    ProgressBar carrega;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_menu_principal);

        context = getApplicationContext();
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
    }

    public void carregaSite(View v){
        carrega.setVisibility(View.VISIBLE);
        Uri uri = Uri.parse("http://nbcgib.uesc.br/cicacau/index.php");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    //Carrega noticias
    public void carregaListaNoticias(View v){
        urlPag = "http://nbcgib.uesc.br/cicacau/cicacau_noticias.php?pg=";
        urlCont = "http://nbcgib.uesc.br/cicacau/cicacau_noticias.php?pg=";
        getData getDados = new getData(context, carrega, urlPag, urlCont, ListNoticias.class);
        getDados.execute();
    }

    //carrega cursos e eventos
    public void carregaListaCursosEventos(View v){
        urlPag = "http://nbcgib.uesc.br/cicacau/cicacau_cursos-eventos.php?pg=";
        urlCont = "http://nbcgib.uesc.br/cicacau/cicacau_cursos-eventos.php?pg=";
        getData getDados = new getData(context, carrega, urlPag, urlCont, ListCursosEventos.class);
        getDados.execute();
    }


   //carrega leis
    public void carregaListaLeis(View v){
        urlPag = "http://nbcgib.uesc.br/cicacau/cicacau_legislacoes.php?pg=";
        urlCont = "http://nbcgib.uesc.br/cicacau/cicacau_legislacoes.php?pg=";
        getData getDados = new getData(context, carrega, urlPag, urlCont, ListLeis.class);
        getDados.execute();

    }

     //carrega Videos
    public void carregaListaVideos(View v){
        urlPag = "http://nbcgib.uesc.br/cicacau/cicacau_videos.php?pg=";
        urlCont = "http://nbcgib.uesc.br/cicacau/cicacau_videos.php?pg=";
        getData getDados = new getData(context, carrega, urlPag, urlCont, ListVideo.class);
        getDados.execute();

    }

    //Carrega analise conjural
    public void carregaListaAnaliseConjural(View v){
        urlPag = "http://nbcgib.uesc.br/cicacau/cicacau_analise.php?pg=";
        urlCont = "http://nbcgib.uesc.br/cicacau/cicacau_analise.php?pg=";
        getData getDados = new getData(context, carrega, urlPag, urlCont, ListAnaliseConjural.class);
        getDados.execute();

    }


    //Carrega patentes
    public void carregaListaPatentes(View v){

        urlPag = "http://nbcgib.uesc.br/cicacau/cicacau_patentes.php?pg=";
        urlCont = "http://nbcgib.uesc.br/cicacau/cicacau_patentes.php?pg=";
        getData getDados = new getData(context, carrega, urlPag, urlCont,ListPatentes.class);
        getDados.execute();

    }

    //carrega textos tecnicos
    public void carregaListaTextosTecnicos(View v){

        getDataTextos getDados = new getDataTextos(context, carrega);
        getDados.execute();

    }

    //***************************
    //CLasse pega dados
    //***************************

    public class getData extends AsyncTask<Void, Void, String>{
        Context context;
        ProgressBar carrega;
        String urlPag, urlCont;
        Class classe;
        boolean erro = false;

        public getData(Context context, ProgressBar carrega, String urlPag, String urlCont, Class classe){
            this.context = context;
            this.carrega = carrega;
            this.urlPag = urlPag;
            this.urlCont = urlCont;
            this.classe = classe;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            carrega.setVisibility(View.VISIBLE);
            MenuPrincipal.html = null;
        }

        @Override
        protected String doInBackground(Void... params) {
            InputStream content = null;
            BufferedReader leitor;
            StringBuilder str;
            String line = null;
            String html = null;
            String SPag = null;
            int pag=1, inicio, fim;
            String linha = null;

            try {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
                HttpClient httpclient = new DefaultHttpClient();
                HttpResponse response = httpclient.execute(new HttpGet(urlPag));
                content = response.getEntity().getContent();
                leitor = new BufferedReader(new InputStreamReader(content));
                str = new StringBuilder();

                while ((line = leitor.readLine()) != null) {
                    str.append(line);
                }

                content.close();
                html = str.toString();


            } catch (Exception e) {
                //Toast.makeText(context, "Verifique a conexão com a internet", Toast.LENGTH_LONG).show();
                erro = true;
                Log.e("[GET REQUEST]", "Network exception", e);
            }

            if (html != null) {
                inicio = html.indexOf("?pg=", 0);
                while (inicio != (-1)) {
                    fim = html.indexOf(" </a>", inicio);
                    linha = html.substring(inicio, fim);
                    SPag = linha;
                    inicio = html.indexOf("?pg=", fim);
                }

            }
            try {
                if(urlPag.equals("http://nbcgib.uesc.br/cicacau/cicacau_noticias.php?pg=")) {
                    SPag = SPag.substring(8);
                }
                else
                    SPag = SPag.substring(7);
                pag = Integer.parseInt(SPag);
            }catch (Exception e){
                pag = 1;
            }

            for(int iPos=1; iPos<=pag; iPos++) {
                String url = urlCont + iPos;
                try {
                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                    StrictMode.setThreadPolicy(policy);
                    HttpClient httpclient = new DefaultHttpClient();
                    HttpResponse response = httpclient.execute(new HttpGet(url));
                    content = response.getEntity().getContent();
                    leitor = new BufferedReader(new InputStreamReader(content));
                    str = new StringBuilder();

                    while ((line = leitor.readLine()) != null) {
                        str.append(line);
                    }

                    content.close();
                    html = str.toString();


                } catch (Exception e) {
                    //Toast.makeText(context, "Verifique a conexão com a internet", Toast.LENGTH_LONG).show();
                    erro = true;
                    Log.e("[GET REQUEST]", "Network exception", e);
                }
                MenuPrincipal.html += html;
            }

            return html;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(erro == false) {
                Intent intent = new Intent(context, classe);
                startActivity(intent);
            }
            else{
                Toast.makeText(context, "Verifique a conexão com internet", Toast.LENGTH_LONG).show();
            }
            carrega.setVisibility(View.GONE);
        }
    }

    //**********************************
    //CLasse pega dados textos tecnicos
    //**********************************
    public class getDataTextos extends AsyncTask<Void, Void, String>{
        Context context;
        ProgressBar carrega;
        boolean erro = false;

        public getDataTextos(Context context, ProgressBar carrega){
            this.context = context;
            this.carrega = carrega;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            carrega.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(Void... params) {
            InputStream content = null;
            BufferedReader leitor;
            StringBuilder str;
            String line = null;
            String html = null;
            String SPag = null;
            int pag=1, inicio, fim;
            String linha = null;

            /*try {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
                HttpClient httpclient = new DefaultHttpClient();
                HttpResponse response = httpclient.execute(new HttpGet("http://nbcgib.uesc.br/cicacau/cicacau_patentes.php?pg="));
                content = response.getEntity().getContent();
                leitor = new BufferedReader(new InputStreamReader(content));
                str = new StringBuilder();

                while ((line = leitor.readLine()) != null) {
                    str.append(line);
                }

                content.close();
                html = str.toString();


            } catch (Exception e) {
                //Toast.makeText(context, "Verifique a conexão com a internet", Toast.LENGTH_LONG).show();
                erro = true;
                Log.e("[GET REQUEST]", "Network exception", e);
            }

            if (html != null) {
                inicio = html.indexOf("?pg=", 0);
                while (inicio != (-1)) {
                    fim = html.indexOf(" </a>", inicio);
                    linha = html.substring(inicio, fim);
                    SPag = linha;
                    inicio = html.indexOf("?pg=", fim);
                }

            }
            try {
                SPag = SPag.substring(7);
                pag = Integer.parseInt(SPag);
            }catch (Exception e){
                pag = 1;
            }
            */
            for(int iPos=1; iPos<=pag; iPos++) {
                String url = "http://nbcgib.uesc.br/cicacau/cicacau_producao.php?cat=9";
                try {
                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                    StrictMode.setThreadPolicy(policy);
                    HttpClient httpclient = new DefaultHttpClient();
                    HttpResponse response = httpclient.execute(new HttpGet(url));
                    content = response.getEntity().getContent();
                    leitor = new BufferedReader(new InputStreamReader(content));
                    str = new StringBuilder();

                    while ((line = leitor.readLine()) != null) {
                        str.append(line);
                    }

                    content.close();
                    html = str.toString();


                } catch (Exception e) {
                    //Toast.makeText(context, "Verifique a conexão com a internet", Toast.LENGTH_LONG).show();
                    erro = true;
                    Log.e("[GET REQUEST]", "Network exception", e);
                }
                MenuPrincipal.html += html;
            }

            return html;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(erro == false) {
                Intent intent = new Intent(context, ListTextosTecnicos.class);
                startActivity(intent);
            }
            else{
                Toast.makeText(context, "Verifique a conexão com internet", Toast.LENGTH_LONG).show();
            }
            carrega.setVisibility(View.GONE);
        }
    }
}


