package com.jose.cicacau.Fragmentos;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jose.cicacau.R;


public class AnaliseConjural extends Fragment{
    public View onCreateView (LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
    {

        View fragmentViewAnaliseConjural = inflater.inflate(R.layout.fragment_analise_conjuntural, container, false);

        return fragmentViewAnaliseConjural;
    }

}
