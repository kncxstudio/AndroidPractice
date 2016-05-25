package com.example.kncx.recyclerviewtest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kncx.recyclerviewtest.adapter.MyAdapter;
import com.example.kncx.recyclerviewtest.myInterface.RecyclerViewOnClickListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private MyAdapter myAdapter;
    private List<String> data = new ArrayList<String>();
    private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        data.add("a");
        data.add("b");
        data.add("c");
        data.add("d");
        data.add("e");

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(myAdapter);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myAdapter.addItem("haha",myAdapter.adapterData.size());
            }
        });
    }

    public void initViews(){
        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        button = (Button)findViewById(R.id.button);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        myAdapter = new MyAdapter(data);
        myAdapter.setItemOnClickListener(new RecyclerViewOnClickListener() {
            @Override
            public void onItemClick(View v) {
                Log.e("===========","123");
                Toast.makeText(getApplicationContext(),((TextView)v.findViewById(R.id.text_view)).getText().toString(),Toast.LENGTH_LONG).show();

            }

        });

    }
}
