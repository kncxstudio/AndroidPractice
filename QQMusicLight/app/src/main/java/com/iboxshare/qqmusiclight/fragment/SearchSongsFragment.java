package com.iboxshare.qqmusiclight.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.iboxshare.qqmusiclight.R;

/**
 * Created by KN on 16/7/6.
 */
public class SearchSongsFragment extends Fragment implements View.OnClickListener{
    View view;
    EditText searchSongsET;
    Button searchSongsBtn;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_search_songs,container,false);
        searchSongsET = (EditText) view.findViewById(R.id.searchSongs_searchET);
        searchSongsBtn = (Button) view.findViewById(R.id.searchSongs_searchBtn);
        return view;
    }


    @Override
    public void onClick(View v) {

    }
}
