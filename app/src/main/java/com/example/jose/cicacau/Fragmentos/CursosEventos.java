package com.example.jose.cicacau.Fragmentos;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jose.cicacau.R;

public class CursosEventos extends Fragment{
    public View onCreateView (LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
    {

        View fragmentViewCursosEventos = inflater.inflate(R.layout.fragment_cursos_eventos, container, false);

        return fragmentViewCursosEventos;
    }

}
