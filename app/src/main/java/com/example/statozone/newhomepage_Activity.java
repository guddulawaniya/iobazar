package com.example.statozone;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;

import java.util.ArrayList;

public class newhomepage_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newhomepage);

        ArrayList<cate_Module> list = new ArrayList<>();


        list.add(new cate_Module(R.drawable.dashboard,"Top categories"));
        list.add(new cate_Module(R.drawable.lightbulb,"All Product"));
        list.add(new cate_Module(R.drawable.crown,"Top Product"));
        list.add(new cate_Module(R.drawable.security,"New Arriaval"));

        categories_adapter adapter = new categories_adapter(list,this);

        RecyclerView caterecyclerview = findViewById(R.id.cateRecyclerview);
        RecyclerView itemGridrecyclerview = findViewById(R.id.itemGridrecyclerview);

//        GridLayoutManager gridLayout = new GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false);
//        itemGridrecyclerview.setLayoutManager(gridLayout);


        caterecyclerview.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        caterecyclerview.setAdapter(adapter);
    }
}