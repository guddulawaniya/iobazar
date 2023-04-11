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

public class sub_categories extends AppCompatActivity
{
    private  static final String url="https://zone.tbvcsoft.com/app/subCategories.php";
    private final static String UrlAddCart = "https://zone.tbvcsoft.com/app/addTocart.php";
    RecyclerView recview;
    private ShimmerFrameLayout shimmerFrameLayout;
    private static String  ct_id;
    private ImageView near, alert, explore, cart, account,back;
    private TextView t1,t2,t3,t4,t5;

    private myadapter adapter;
    private String prNAme;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_categories);

        //shimmerFrameLayout= findViewById(R.id.Shimmer);
        //shimmerFrameLayout.startShimmer();
        near = (ImageView)findViewById(R.id.near);
        alert = (ImageView)findViewById(R.id.bell);
        back = (ImageView)findViewById(R.id.back);
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

        ct_id= intent.getStringExtra("product_id");


        near.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(sub_categories.this,Homepage.class);
                startActivity(intent);
            }
        });

        t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(sub_categories.this,Homepage.class);
                startActivity(intent);
            }
        });

        alert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(sub_categories.this,sub_categories.class);
                startActivity(intent);
            }
        });
        t2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(sub_categories.this,sub_categories.class);
                startActivity(intent);
            }
        });
        explore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(sub_categories.this,Explore.class);
                startActivity(intent);
            }
        });

        t3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(sub_categories.this,Explore.class);
                startActivity(intent);
            }
        });


        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(sub_categories.this,CartView.class);
                startActivity(intent);
            }
        });
        t4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(sub_categories.this,CartView.class);
                startActivity(intent);
            }
        });

        account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(sub_categories.this,Account.class);
                startActivity(intent);
            }
        });

        t5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(sub_categories.this,Account.class);
                startActivity(intent);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        processdata();



    }





    private void processdata( ) {
       String productId = ct_id;
        String productIdurl ="?categories_id="+productId;
        class dbManager extends AsyncTask<String, Void, String>
        {
            protected void onPostExecute(String response)
            {
                try {
                    GsonBuilder builder=new GsonBuilder();
                    Gson gson=builder.create();
                    model data[]=gson.fromJson(response,model[].class);




                    myadapter adapter=new myadapter(data);
                    LinearLayoutManager horizontalLayoutManagaer
                            = new GridLayoutManager(sub_categories.this,2, LinearLayoutManager.VERTICAL, false);
                    shimmerFrameLayout.startShimmer();
                    shimmerFrameLayout.setVisibility(View.GONE);
                    recview.setHasFixedSize(true);
                    recview.setLayoutManager(horizontalLayoutManagaer);

                    recview.setAdapter(adapter);
                   // Toast.makeText(sub_categories.this, pr_id, Toast.LENGTH_SHORT).show();



                }

                catch (Exception ex)
                {

                    Toast.makeText(sub_categories.this,response,Toast.LENGTH_LONG).show();
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
        obj.execute(url+productIdurl);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();


    }


    public class myadapter extends RecyclerView.Adapter<myadapter.myviewholder>
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
        public  myadapter.myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.subcategoriesrow,parent,false);
            return new  myadapter.myviewholder(view);
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
                    Toast.makeText(view.getContext(), "abdddddvc" ,Toast.LENGTH_SHORT).show();



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


                    Intent intent= new Intent(sub_categories.this, productBYid.class);
                    intent.putExtra("cat_id",ct_id);
                    intent.putExtra("subCat_id", String.valueOf(data[position].getId()));

                    //
                    startActivity(intent);





                    /*




                    androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(view.getContext());
                    ProgressDialog progressDialog = new ProgressDialog(view.getContext());

                    CharSequence[] dialogItem = {"View Data","Edit Data","Delete Data"};

                    builder.setItems(dialogItem, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int i) {

                            switch (i){

                                case 0:

                                   Intent intent= new Intent(test.this, ProductView.class);
                                    intent.putExtra("product_id", String.valueOf(data[position].getId()));
                                   //
                                   startActivity(intent);




                                    break;

                                case 1:
                                    // startActivity(new Intent(getApplicationContext(),Edit_Activity.class)
                                    //     .putExtra("position",position));

                                    break;

                                case 2:

                                    // deleteData(employeeArrayList.get(position).getId());

                                    break;


                            }



                        }
                    });


                    builder.create().show();


                    // Toast.makeText(view.getContext(), "abdddddvc" ,Toast.LENGTH_SHORT).show();




                     */

                }




            });



        }



        @Override
        public int getItemCount() {
            return data.length;
        }

        class myviewholder extends RecyclerView.ViewHolder
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
                try {Toast.makeText(sub_categories.this,data,Toast.LENGTH_LONG).show();
                }
                catch (Exception ex)
                {
                    Toast.makeText(sub_categories.this,"Something went wrong",Toast.LENGTH_LONG).show();
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

