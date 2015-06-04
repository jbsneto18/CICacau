package com.example.jose.cicacau.fragment;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jose.cicacau.R;


public class Patentes extends Fragment{
    public View onCreateView (LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
    {

        View fragmentViewPatentes = inflater.inflate(R.layout.fragment_patentes, container, false);

        return fragmentViewPatentes;
    }
}