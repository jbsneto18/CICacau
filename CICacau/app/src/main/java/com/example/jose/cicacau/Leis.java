package com.example.jose.cicacau;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class Leis extends Fragment{
    public View onCreateView (LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
    {

        View fragmentViewLegislacao = inflater.inflate(R.layout.fragment_leis, container, false);

        return fragmentViewLegislacao;
    }

}