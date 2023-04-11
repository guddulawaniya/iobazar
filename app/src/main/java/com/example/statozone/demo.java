package com.example.statozone;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.text.HtmlCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class demo extends AppCompatActivity
{
    private  static final String url="https://zone.tbvcsoft.com/app/productsByCategories.php";
    private final static String UrlAddCart = "https://zone.tbvcsoft.com/app/addTocart.php";
    RecyclerView recview;
    private ImageView near, alert, explore, cart, account;
    private TextView t1,t2,t3,t4,t5;
    private static String  ct_id,subct_id;
    private ShimmerFrameLayout shimmerFrameLayout;

    private String prNAme;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_byid);

        //shimmerFrameLayout= findViewById(R.id.Shimmer);
        //shimmerFrameLayout.startShimmer();

        near = (ImageView)findViewById(R.id.near);
        alert = (ImageView)findViewById(R.id.bell);
        explore = (ImageView)findViewById(R.id.explore);
        cart = (ImageView)findViewById(R.id.cart);
        account = (ImageView)findViewById(R.id.profile);
        t1= (TextView)findViewById(R.id.t1);
        t2= (TextView)findViewById(R.id.t2);
        t3= (TextView)findViewById(R.id.t3);
        t4= (TextView)findViewById(R.id.t4);
        t5= (TextView)findViewById(R.id.t5);
        shimmerFrameLayout = findViewById(R.id.shimmer_view_container);

        recview=(RecyclerView)findViewById(R.id.recview);

        recview.setLayoutManager(new LinearLayoutManager(this));
        Intent intent = getIntent();

       // subct_id= intent.getStringExtra("subCat_id");
      //  ct_id= intent.getStringExtra("cat_id");








    }








}



