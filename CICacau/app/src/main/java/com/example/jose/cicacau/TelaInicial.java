package com.example.jose.cicacau;

import com.example.jose.cicacau.util.SystemUiHider;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;


/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 *
 * @see SystemUiHider
 */
public class TelaInicial extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_tela_inicial);

        final int MILISEGUNDOS = 3000;
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                menuPrincipal(null);
            }
        }, MILISEGUNDOS);

    }

    protected void onResume(){
        super.onResume();
        final int MILISEGUNDOS = 3000;
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                menuPrincipal(null);
            }
        }, MILISEGUNDOS);
    }


    public void menuPrincipal(View v){
        Intent i = new Intent(this, MenuPrincipal.class);
        startActivity(i);
    }

}
