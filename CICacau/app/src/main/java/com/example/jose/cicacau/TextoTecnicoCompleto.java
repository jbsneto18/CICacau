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


public class TextoTecnicoCompleto extends Activity {

    ArrayList<String> titulo = ListTextosTecnicos.titulo;
    ArrayList<String>autores = ListTextosTecnicos.autores;
    ArrayList<String> link = ListTextosTecnicos.link;
    int index = ListTextosTecnicos.index;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_texto_tecnico_completo);

        TextView textTitulo = (TextView) findViewById(R.id.titulo);
        textTitulo.setText(titulo.get(index).toString());
        TextView textAutor = (TextView) findViewById(R.id.pAutor);
        textAutor.setText(autores.get(index).toString().substring(19));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_texto_tecnico_completo, menu);
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
