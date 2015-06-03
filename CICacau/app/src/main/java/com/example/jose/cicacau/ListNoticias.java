package com.example.jose.cicacau;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


public class ListNoticias extends ActionBarActivity {

    private ImageView imgNoticia;
    private Bitmap myBitmap;
    static ArrayList<String>noticias = new ArrayList<String>();
    static ArrayList<String>titulo = new ArrayList<String>();
    static ArrayList<String>categoria = new ArrayList<String>();
    static int index;
    String html;
    static String htmlCompleto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_noticias);
        html = MenuPrincipal.html;
        titulo = noticiasTitulo(html);
        categoria = noticiasCategoria(html);
        noticias = carregaNoticias(html);

        ListView listview;
        listview =  (ListView) findViewById(R.id.MyListNoticias);
        MeuArrayAdapter adapterCodigos = new MeuArrayAdapter(this, titulo, categoria);
        listview.setAdapter(adapterCodigos);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
                index = position;
                htmlCompleto = carregaHtml(null);
                noticiaCompleta(null);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_list_noticias, menu);
        return true;
    }

    /*@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_filtar) {
            //carregaFiltro(null);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/

    public class MeuArrayAdapter extends ArrayAdapter<String> {
        private final Context context;
        private final ArrayList<String> titulo;
        private final ArrayList<String> categoria;

        public MeuArrayAdapter(Context context, ArrayList<String> titulo, ArrayList<String> categoria) {
            super(context, R.layout.listviewnoticias, titulo);
            this.context = context;
            this.titulo = titulo;
            this.categoria = categoria;

        }

        @Override
        public View getView(int posicao, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.listviewnoticias, parent, false);
            TextView textTitulo = (TextView) rowView.findViewById(R.id.titulo);
            textTitulo.setText(titulo.get(posicao).toString().substring(28));
            TextView textCategoria = (TextView) rowView.findViewById(R.id.categoria);
            textCategoria.setText(categoria.get(posicao).toString().substring(25));
            //imgNoticia = (ImageView) rowView.findViewById(R.id.imagemNoticia);
            //myBitmap = getBitmapFromUrl("http://nbcgib.uesc.br/cicacau/imagens/noticia/186.jpg");
            //imgNoticia.setImageBitmap(myBitmap);
            return rowView;
        }
    }



    public static ArrayList<String> noticiasTitulo(String html){
        ArrayList<String> listTitulo = new ArrayList<String>();
        String linha = null;
        int inicio, fim;

        if (html != null) {
            inicio = html.indexOf("cicacau_noticia.php?id=", 0);
            while (inicio != (-1)) {
                fim = html.indexOf("</a>", inicio);
                linha = html.substring(inicio, fim);
                listTitulo.add(linha);
                inicio = html.indexOf("cicacau_noticia.php?id=", fim);
            }

            for (int i = 0; i < listTitulo.size(); i++) {
                String temp = tratarSubLinha(listTitulo.get(i));
                listTitulo.set(i, temp);
            }
        }

        return listTitulo;
    }

    public static ArrayList<String> noticiasCategoria(String html){
        ArrayList<String> listCategoria = new ArrayList<String>();
        String linha = null;
        int inicio, fim;

        if (html != null) {
            inicio = html.indexOf("div class=titulooutros><h4><a href=", 0);
            while (inicio != (-1)) {
                fim = html.indexOf("</h4>", inicio);
                linha = html.substring(inicio, fim);
                listCategoria.add(linha);
                inicio = html.indexOf("div class=titulooutros><h4><a href=", fim);
            }

            for (int i = 0; i < listCategoria.size(); i++) {
                String temp = tratarSubLinha(listCategoria.get(i));
                listCategoria.set(i, temp);
            }
        }


        return listCategoria;
    }

    public static ArrayList<String> carregaNoticias(String html){
        ArrayList<String> listNoticias = new ArrayList<String>();
        String linha = null;
        int inicio, fim;

        if (html != null) {
            inicio = html.indexOf("cicacau_noticia.php?id=", 0);
            while (inicio != (-1)) {
                fim = html.indexOf('"' + ">", inicio);
                linha = html.substring(inicio, fim);
                listNoticias.add(linha);
                inicio = html.indexOf("cicacau_noticia.php?id=", fim);
            }

            for (int i = 0; i < listNoticias.size(); i++) {
                String temp = tratarSubLinha(listNoticias.get(i));
                listNoticias.set(i, temp);
            }
        }

        return listNoticias;
    }

    private static String tratarSubLinha(String string) {
        // TODO Auto-generated method stub
        String nova = Html.fromHtml(string).toString();
        return nova;
    }

    public void noticiaCompleta(View v){
        Intent intent = new Intent(this, NoticiaCompleta.class);
        startActivity(intent);
    }

    /*public void carregaFiltro(View v){
        Intent intent = new Intent(this, filtroNoticia.class);
        startActivity(intent);
    }*/

    /*public Bitmap getBitmapFromUrl(String src){
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        }catch (Exception e){
            return null;
        }
    }*/

    public String carregaHtml (View v){

        InputStream content = null;
        BufferedReader leitor;
        StringBuilder str;
        String line = null;
        String html = null;
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            HttpClient httpclient = new DefaultHttpClient();
            HttpResponse response = httpclient.execute( new HttpGet("http://nbcgib.uesc.br/cicacau/" + noticias.get(index).toString()));
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
