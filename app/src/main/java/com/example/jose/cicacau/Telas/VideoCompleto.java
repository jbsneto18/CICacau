package com.example.jose.cicacau.Telas;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import com.example.jose.cicacau.Lista.ListVideo;
import com.example.jose.cicacau.R;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import java.util.ArrayList;


public class VideoCompleto extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener{

    private static final String API_KEY = "AIzaSyDpdKmijrQOLSjvN30l2KMajdpGN5eCHtY";
    private String ID_Video;
    private YouTubePlayerView youtube;
    ArrayList<String> titulo = ListVideo.titulo;
    ArrayList<String> conteudo = ListVideo.conteudo;
    ArrayList<String> autores = ListVideo.autores;
    ArrayList<String>data = ListVideo.data;
    ArrayList<String>link = ListVideo.link;
    ArrayList<String>linkVideo = ListVideo.linkVideo;
    int index = ListVideo.index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_completo);

        TextView textTitulo = (TextView)findViewById(R.id.titulo);
        textTitulo.setText(titulo.get(index).toString().substring(17));
        TextView textConteudo = (TextView)findViewById(R.id.conteudo);
        textConteudo.setText(conteudo.get(0).toString().substring(21));
        TextView textAutores = (TextView)findViewById(R.id.pAutores);
        textAutores.setText(autores.get(index).toString().substring(11));
        TextView textData = (TextView)findViewById(R.id.pData);
        textData.setText(data.get(index).toString().substring(25));
        ID_Video =  linkVideo.get(index).toString().substring(106);
        youtube = (YouTubePlayerView) findViewById(R.id.youtube);
        youtube.initialize(API_KEY, this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_video_completo, menu);
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

    public void carregaAutor(View v){
        Uri uri = Uri.parse(link.get(index).toString().substring(20));
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        if(!b){
            youTubePlayer.cueVideo(ID_Video);
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

    }
}
