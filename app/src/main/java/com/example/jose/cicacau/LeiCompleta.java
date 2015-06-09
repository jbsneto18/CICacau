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
import android.widget.Toast;

import java.util.ArrayList;


public class LeiCompleta extends Activity {

    ArrayList<String> titulo = ListLeis.titulo;
    ArrayList<String> conteudo = ListLeis.conteudo;
    ArrayList<String> link = ListLeis.link;
    ArrayList<String>categoria = ListLeis.categoria;
    int index = ListLeis.index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lei_completa);

        TextView textTitulo = (TextView)findViewById(R.id.titulo);
        textTitulo.setText(titulo.get(index).toString().substring(17));
        TextView textConteudo = (TextView)findViewById(R.id.conteudo);
        textConteudo.setText(conteudo.get(index).toString().substring(10));
        TextView textCategoria = (TextView)findViewById(R.id.categoria);
        textCategoria.setText(categoria.get(index).toString().substring(19));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_lei_completa, menu);
        return true;
    }

   /* @Override
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
