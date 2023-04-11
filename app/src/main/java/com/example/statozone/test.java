package com.example.statozone;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class test extends AppCompatActivity
{
    private  static final String url="https://zone.tbvcsoft.com/app/ProductList.php";
    private final static String UrlAddCart = "https://zone.tbvcsoft.com/app/addTocart.php";
    RecyclerView recview;
    ShimmerFrameLayout shimmerFrameLayout;
    private myadapter adapter;
    private String prNAme;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        //shimmerFrameLayout= findViewById(R.id.Shimmer);
        //shimmerFrameLayout.startShimmer();

        recview=(RecyclerView)findViewById(R.id.recview);
        recview.setLayoutManager(new LinearLayoutManager(this));

        processdata();



    }



    public void processdata()
    {

        StringRequest request=new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                GsonBuilder builder=new GsonBuilder();
                Gson gson=builder.create();
                model data[]=gson.fromJson(response,model[].class);




                myadapter adapter=new myadapter(data);
                LinearLayoutManager horizontalLayoutManagaer
                        = new GridLayoutManager(test.this,2, LinearLayoutManager.VERTICAL, false);
                 recview.setHasFixedSize(true);
                recview.setLayoutManager(horizontalLayoutManagaer);

                recview.setAdapter(adapter);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_LONG).show();
            }
        }
        );


        RequestQueue queue= Volley.newRequestQueue(getApplicationContext());
        queue.add(request);

    }




    public static class myadapter extends RecyclerView.Adapter<myadapter.myviewholder>
    {
        private Context context;



        model data[];

        public myadapter(model[] data) {
            this.data = data;
        }

        public myadapter(Context context) {
            this.context = context;
        }

        @NonNull
        @Override
        public  myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerow,parent,false);
            return new  myviewholder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull myviewholder holder, @SuppressLint("RecyclerView") int position) {
            holder.t1.setText(data[position].getName());
            holder.t2.setText(data[position].getPrice());
            holder.t3.setText(data[position].getPricet());

            holder.t5.setText(data[position].getDescription());
            Glide.with(holder.t1.getContext()).load("https://zone.tbvcsoft.com/images/thumbnail/"+data[position].getImage()).into(holder.img);

            holder.t1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //  AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                    /// ProgressDialog progressDialog = new ProgressDialog(view.getContext());

                    // CharSequence[] dialogItem = {"View Data","Edit Data","Delete Data"};
                    Toast.makeText(view.getContext(), "lk" ,Toast.LENGTH_SHORT).show();




                }
            });

            holder.addtocart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //AddtoCart(12,"231",30, data[position].getId());
                  //  prNAme =String.valueOf(  data[position].getId());

                    Toast.makeText(view.getContext(), "Add Successfully" ,Toast.LENGTH_SHORT).show();


                }
            });
            holder.img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Toast.makeText(view.getContext(), "lfff" ,Toast.LENGTH_SHORT).show();












                }




            });



        }



        @Override
        public int getItemCount() {
            return data.length;
        }

        static class myviewholder extends RecyclerView.ViewHolder
        {
            ImageView img,productimage;
            TextView t1,t2,t3,t4,t5,addtocart;

            public myviewholder(@NonNull View itemView)
            {
                super(itemView);

                img=itemView.findViewById(R.id.productimage);
                t1=itemView.findViewById(R.id.productname);
                t2=itemView.findViewById(R.id.product_price);
                t3=itemView.findViewById(R.id.productfullprice);
                t4 = itemView.findViewById(R.id.bestprice);
                t5 = itemView.findViewById(R.id.producttitle);
                productimage=itemView.findViewById(R.id.productimage);
                addtocart = itemView.findViewById(R.id.textView22);





            }
        }

    }


    private void AddtoCart(Integer number, String pr_id, int user_id, Integer Total_amount)
    {
        Integer qty = number;
        String productId = pr_id;
        int UserId = user_id;
        Integer productprise = Total_amount;
        String Pr_name= prNAme;

        String AddTOcartUrl = "?users_Id="+UserId+"&product_Id="+productId+"&quantity="+qty+"&ProductPric="+productprise+"&ProductName="+Pr_name;
        class dbManager extends AsyncTask<String, Void, String>
        {
            protected void onPostExecute(String data)
            {
                try {Toast.makeText(test.this,data,Toast.LENGTH_LONG).show();
                }
                catch (Exception ex)
                {
                    Toast.makeText(test.this,"Something went wrong",Toast.LENGTH_LONG).show();
                }
            }
            @Override
            protected String doInBackground(String... strings) {
                try {
                    URL url= new URL(strings[0]);
                    HttpURLConnection conn =(HttpURLConnection)url.openConnection();
                    BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    StringBuffer data = new StringBuffer();
                    String line;
                    while((line=  br.readLine())!= null)
                    {
                        data.append(line+"\n");
                    }
                    br.close();
                    return data.toString();
                }
                catch (Exception ex)
                {
                    return ex.getMessage();
                }
            }
        }
        dbManager obj =new dbManager();
        obj.execute(UrlAddCart+AddTOcartUrl);
    }

}

