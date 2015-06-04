package com.example.jose.cicacau.Telas;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import com.example.jose.cicacau.Lista.ListAnaliseConjural;
import com.example.jose.cicacau.R;

import java.util.ArrayList;


public class AnaliseConjuralCompleta extends Activity {

    ArrayList<String> titulo = ListAnaliseConjural.titulo;
    ArrayList<String> data = ListAnaliseConjural.data;
    ArrayList<String> link = ListAnaliseConjural.link;
    int index = ListAnaliseConjural.index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analise_conjural_completa);

        TextView textTitulo = (TextView)findViewById(R.id.titulo);
        textTitulo.setText(titulo.get(index).toString().substring(17));
        TextView textData = (TextView)findViewById(R.id.data);
        textData.setText(data.get(index).toString().substring(19));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_analise_conjural_completa, menu);
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
