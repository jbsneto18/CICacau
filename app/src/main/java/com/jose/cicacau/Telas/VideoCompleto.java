package com.jose.cicacau.Telas;

import android.os.Bundle;
import android.view.Menu;
import android.webkit.WebView;

import com.jose.cicacau.Lista.ListVideo;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.jose.cicacau.R;

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

        WebView webConteudo = (WebView) findViewById(R.id.webConteudo);

        String text = "<html><body>"
                + "<p align=\"justify\"><b> <font size=\"4\">"
                + titulo.get(index).toString().substring(17)
                + "</font></b></p>"
                +"<p align=\"justify\">"
                +conteudo.get(0).toString().substring(21)
                + "</p> "
                +"<p align=\"justify\"> <b> Autor(es):</b> "
                +"<a href=" +link.get(index).toString().substring(20)+ ">" + autores.get(index).toString().substring(11) + "</a>"
                + "</p> "
                +"<p align=\"justify\"> <b> Data:</b> "
                +data.get(index).toString().substring(25)
                + "</p> "
                + "</body></html>";

        webConteudo.loadData(text, "text/html;charset=UTF-8", null);

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
