package com.example.statozone;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import java.util.ArrayList;

public class Homepage extends AppCompatActivity {

    private TextView addressh;
    private final static String storeDetaileUrl = "https://food.tbvcsoft.com/app/Singlestore.php";
    private final static String category = "https://zone.tbvcsoft.com/app/category.php";
    private final static String DeleteCartSession = "https://food.tbvcsoft.com/app/DeleteCart.php";
    private final static String storeurl = "https://zone.tbvcsoft.com/app/category.php";
    private final static String generalSetting = "https://food.tbvcsoft.com/app/zeneral_setting.php";


    private  static final String url="https://zone.tbvcsoft.com/app/productnew.php";
    private final static String UrlAddCart = "https://zone.tbvcsoft.com/app/addTocart.php";
    RecyclerView recview;

    private static String  ct_id,subct_id;


    ShimmerFrameLayout shimmerFrameLayout;
    private  myadapter adapter;
    private String prNAme;
    private TextView t1,t2,t3,t4,t5;
    private CategoryList adapter1;
    private RecyclerView lgrd;
    private ImageView near, alert, explore, cart, account,image,ClearCart;
    private LinearLayout sechTxt, addressLink;
    private ListView storelist;
    private RelativeLayout ViewCart;
    private TextView btn1,btn2,businessType,ViewCartName,ViewCartLink;
    private ShimmerFrameLayout mShimmerViewContainer,categorySkeleton;
    private SharedPreferences myPrefs;
    private static String img[];
    private static String name[];
    private static String ids[];
    private static String storname[];
    private static String storeimage[];
    private static String storeaddress[];
    private static String storeid[];
    private static String storeUser_id[];
    private static String storeRating[];
    private static String twoorder[];
    private static String aproxtime[];
    //show cart detail add funcction detail
    private  String storeName[];
    private String user_id;


    String deliverytype = "1";
    private ArrayList<CategoryValue> categoryProductArrayList = new ArrayList<CategoryValue>();

    //loader
    String location_id,StoreId;
    int rowcnt;
    String id,logo,business_category;
    private RelativeLayout header,footer,Scrl,srchbox,storelistsc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        lgrd = (RecyclerView) findViewById(R.id.List_crt_pr);
        near = (ImageView)findViewById(R.id.near);
        alert = (ImageView)findViewById(R.id.bell);
        explore = (ImageView)findViewById(R.id.explore);
        cart = (ImageView)findViewById(R.id.cart);
        account = (ImageView)findViewById(R.id.profile);

        sechTxt = (LinearLayout)findViewById(R.id.srchlnr);
        storelist = (ListView)findViewById(R.id.storelist);
        Scrl = (RelativeLayout)findViewById(R.id.Scrl);
        srchbox = (RelativeLayout) findViewById(R.id.srchbox);
        t1= (TextView)findViewById(R.id.t1);
        t2= (TextView)findViewById(R.id.t2);
        t3= (TextView)findViewById(R.id.t3);
        t4= (TextView)findViewById(R.id.t4);
        t5= (TextView)findViewById(R.id.t5);



        image = (ImageView) findViewById(R.id.image);

        header = (RelativeLayout)findViewById(R.id.header);
        footer = (RelativeLayout)findViewById(R.id.footer);
        categorySkeleton = findViewById(R.id.categorySkeleton);
        mShimmerViewContainer = findViewById(R.id.StoreListSkeleton);
        ViewCartName = findViewById(R.id.ViewCartName);
        ViewCart = findViewById(R.id.ViewCart);
        ViewCartLink = findViewById(R.id.ViewCartLink);
        ClearCart = findViewById(R.id.ClearCart);
        //get store value
        myPrefs = getSharedPreferences("myPrefs", MODE_PRIVATE);
        String addressd = myPrefs.getString("address", "");
        StoreId = myPrefs.getString("crtstrid", "");
        location_id =myPrefs.getString("location_id", "");
        user_id = myPrefs.getString("user_id", "");







        alert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Homepage.this,categories.class);
                startActivity(intent);
            }
        });



        t2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Homepage.this,categories.class);
                startActivity(intent);
            }
        });




        explore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Homepage.this,Explore.class);
                startActivity(intent);
            }
        });

        t3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Homepage.this,Explore.class);
                startActivity(intent);
            }
        });
        sechTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Homepage.this,Explore.class);
                startActivity(intent);
            }
        });
        ClearCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(Homepage.this);
                //builder.setTitle("Replace cart item?").
                builder.setMessage("Discard all the items in your cart?");
                builder.setPositiveButton("DISCARD",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                DeleteCartSession(StoreId,user_id);
                                SharedPreferences myPrefs = getSharedPreferences("myPrefs", MODE_PRIVATE);
                                SharedPreferences.Editor prefsEditor;
                                prefsEditor = myPrefs.edit();
                                prefsEditor.remove("crtstrid");
                                prefsEditor.commit();
                                Intent intent = getIntent();
                                finish();
                                startActivity(intent);
                            }
                        });
                builder.setNegativeButton("CANCEL",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                androidx.appcompat.app.AlertDialog alert11 = builder.create();
                alert11.show();
                //   Toast.makeText(Homepage.this, "Clear Cart",Toast.LENGTH_LONG).show();
            }
        });


        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Homepage.this,CartView.class);
                startActivity(intent);
            }
        });

        t4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Homepage.this,CartView.class);
                startActivity(intent);
            }
        });


        account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Homepage.this,Account.class);
                startActivity(intent);
            }
        });

        t5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Homepage.this,Account.class);
                startActivity(intent);
            }
        });



        recview=(RecyclerView)findViewById(R.id.recview);
        recview.setLayoutManager(new LinearLayoutManager(this));

        processdata();



        // delivery and selfPickup


        fetchdata(lgrd);
        homesetting();
        storedata(storelist,deliverytype);
        isInternetOn();


        ViewCart.setVisibility(View.GONE);
    }
    private void fetchdata(View view ) {
        class dbManager extends AsyncTask<String, Void, String>
        {
            ProgressDialog pdLoading = new ProgressDialog(Homepage.this);
            protected void onPostExecute(String data)
            {
                try {

                    JSONArray ja= new JSONArray(data);
                    JSONObject jo = null;
                    name = new String[ja.length()];
                    img = new String[ja.length()];
                    ids = new String[ja.length()];
                    for  (int i = 0; i < ja.length(); i++) {
                        jo = ja.getJSONObject(i);
                        name[i] = jo.getString("name");
                        ids[i] = jo.getString("id");
                        img[i] ="https://zone.tbvcsoft.com/images/thumbnail/" + jo.getString("image");
                        categoryProductArrayList.add(new CategoryValue(name[i], img[i], ids[i]));
                    }
                    categorySkeleton.startShimmer();
                    categorySkeleton.setVisibility(View.GONE);
                    lgrd.setVisibility(View.VISIBLE);
                    adapter1 = new CategoryList(categoryProductArrayList, Homepage.this);
                    LinearLayoutManager horizontalLayoutManagaer = new LinearLayoutManager(Homepage.this, LinearLayoutManager.HORIZONTAL, false);
                    lgrd.setHasFixedSize(true);
                    lgrd.setLayoutManager(horizontalLayoutManagaer);
                    lgrd.setAdapter(adapter1);
                }
                catch (Exception ex)
                {
                    //  Toast.makeText(Homepage.this,"Please check your mobile internet",Toast.LENGTH_LONG).show();
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
        obj.execute(category);
    }



    private void homesetting() {
        class dbManager extends AsyncTask<String, Void, String>
        {
            ProgressDialog pdLoading = new ProgressDialog(Homepage.this);
            protected void onPostExecute(String data)
            {

                try {
                    JSONObject jObj = new JSONObject(data);
                    id = jObj.getString("id");
                    business_category = jObj.getString("business_category");
                    logo = "https://food.tbvcsoft.com/logo/system_logo/"+jObj.getString("logo");
                    //  Glide.with(Homepage.this).load(logo).into(image);

                }
                catch (Exception ex)
                {
                    //  Toast.makeText(Homepage.this,"Please check your mobile internet",Toast.LENGTH_LONG).show();
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
        obj.execute(generalSetting);
    }
    public class CategoryList extends RecyclerView.Adapter<CategoryList.ViewHolder>  {
        private ArrayList<CategoryValue> categoryListArrayList;    // Values to be displayed
        private Context context;

        public CategoryList(ArrayList<CategoryValue> categoryListArrayList, Context context) {
            this.categoryListArrayList = categoryListArrayList;
            this.context = context;
        }
        @NonNull
        @Override
        public CategoryList.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.categoryimage, parent, false);
            return new CategoryList.ViewHolder(view);
        }
        @Override
        public void onBindViewHolder(@NonNull CategoryList.ViewHolder holder, int position) {
            holder.categoryName.setText(categoryListArrayList.get(position).getName());
            String ids = categoryListArrayList.get(position).getIds();
            Glide.with(Homepage.this).load(categoryListArrayList.get(position).getImg()).into(holder.image);
            holder.image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Homepage.this,sub_categories.class);
                    intent.putExtra("product_id", ids);
                    startActivity(intent);
                }
            });
        }
        @Override
        public int getItemCount() {
            // returning the size of array list.
            return categoryListArrayList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            // creating variables for our views.

            private TextView categoryName;
            private ImageView image;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                categoryName = (TextView) itemView.findViewById(R.id.categoryName);
                image = (ImageView) itemView.findViewById(R.id.image1);
            }

        }
    }


    private void storedata(View view, String deliverytype) {
        String deliveryId =deliverytype;
        String addcrturl;
        class dbManager extends AsyncTask<String, Void, String>
        {
            ProgressDialog pdLoading = new ProgressDialog(Homepage.this);
            protected void onPostExecute(String data)
            {
                try {
                    JSONArray ja = new JSONArray(data);
                    JSONObject jo = null;
                    storname = new String[ja.length()];
                    storeimage = new String[ja.length()];
                    storeaddress = new String[ja.length()];
                    storeRating = new String[ja.length()];
                    storeUser_id = new String[ja.length()];
                    storeid = new String[ja.length()];
                    twoorder = new String[ja.length()];
                    aproxtime = new String[ja.length()];
                    for (int i = 0; i < ja.length(); i++) {
                        jo = ja.getJSONObject(i);
                        storname[i] = jo.getString("name");
                        storeaddress[i] = "ss";
                        storeRating[i] = "ss";
                        storeUser_id[i] = "ss";
                        storeid[i] = jo.getString("id");
                        twoorder[i] = "ss";
                        aproxtime[i] =  "ss";
                        storeimage[i] ="https://zone.tbvcsoft.com/images/thumbnail/" + jo.getString("image");
                        rowcnt = i+1;
                    }

                    mShimmerViewContainer.startShimmer();
                    mShimmerViewContainer.setVisibility(View.GONE);
                    storelist.setVisibility(View.VISIBLE);

                    storeadptr adptr = new storeadptr(getApplicationContext(), storname, twoorder, aproxtime, storeaddress, storeRating, storeimage);
                    storelist.setAdapter(adptr);

                }
                catch (Exception ex)
                {
                    //  Toast.makeText(Homepage.this,"Please check your mobile internet",Toast.LENGTH_LONG).show();
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
        obj.execute(storeurl);
    }
    class storeadptr extends ArrayAdapter<String>
    {
        Context context;
        String strname[];
        String twoorder[];
        String aproxtime[];
        String strAddre[];
        String rating[];
        String rimg[];


        storeadptr(Context c, String strname[],String twoorder[],String aproxtime[], String strAddre[], String rating[],  String rimg[])
        {
            super(c,R.layout.storedesign,R.id.strName,strname);
            context=c;
            this.strname=strname;
            this.twoorder=twoorder;
            this.aproxtime=aproxtime;
            this.rimg=rimg;
            this.strAddre=strAddre;
            this.rating=rating;
        }
        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
        {
            LayoutInflater inflater=(LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row=inflater.inflate(R.layout.storedesign,parent,false);
            ImageView img=row.findViewById(R.id.strImage);
            TextView strName=row.findViewById(R.id.strName);
            TextView storeAddres=row.findViewById(R.id.stradrs);
            TextView storeRating=row.findViewById(R.id.strRating);
            TextView storeTime=row.findViewById(R.id.strTime);
            TextView storemax=row.findViewById(R.id.strMaximum);

            strName.setText(strname[position]);
            storeTime.setText(aproxtime[position]);
            storemax.setText(twoorder[position]+" FOR TWO");
            storeAddres.setText(strAddre[position]);
            storeRating.setText(rating[position]);
            String url=rimg[position];


            Glide.with(Homepage.this).load(url).into(img);
            return row;
        }
    }




    private void processdata( ) {



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
                            = new GridLayoutManager( Homepage.this,2, LinearLayoutManager.VERTICAL, false);

                    recview.setHasFixedSize(true);
                    recview.setLayoutManager(horizontalLayoutManagaer);

                    recview.setAdapter(adapter);
                    // Toast.makeText(productBYid.this, pr_id, Toast.LENGTH_SHORT).show();



                }

                catch (Exception ex)
                {

                    Toast.makeText(Homepage.this,response,Toast.LENGTH_LONG).show();
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
        obj.execute(url);
    }



    public class myadapter extends RecyclerView.Adapter<myadapter.myviewholder>
    {
        private Context context;



        model data[];
        private myviewholder holder;
        private int position;

        public myadapter(model[] data) {
            this.data = data;
        }

        public myadapter(Context context) {
            this.context = context;
        }

        @NonNull
        @Override
        public  myadapter.myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.newsinglerow,parent,false);
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
                    Intent intent= new Intent(Homepage.this, ProductView.class);
                    intent.putExtra("product_id", String.valueOf(data[position].getId()));

                    startActivity(intent);



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

                    //   Toast.makeText(context, String.valueOf(data[position].getId()), Toast.LENGTH_SHORT).show();

                    Intent intent= new Intent(Homepage.this, ProductView.class);
                    intent.putExtra("product_id", String.valueOf(data[position].getId()));

                    startActivity(intent);





                }




            });

            holder.buy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    //   Toast.makeText(context, String.valueOf(data[position].getId()), Toast.LENGTH_SHORT).show();

                    Intent intent= new Intent(Homepage.this, ProductView.class);
                    intent.putExtra("product_id", String.valueOf(data[position].getId()));

                    startActivity(intent);





                }




            });

        }





        @Override
        public int getItemCount() {
            return data.length;
        }

        public class myviewholder extends RecyclerView.ViewHolder
        {
            ImageView img,productimage;
            TextView t1,t2,t3,t4,t5,addtocart,buy;

            public myviewholder(@NonNull View itemView)
            {
                super(itemView);

                img=itemView.findViewById(R.id.productimage);

                t1=itemView.findViewById(R.id.productname);
                t2=itemView.findViewById(R.id.product_price);
                t3=itemView.findViewById(R.id.productfullprice);
                buy = itemView.findViewById(R.id.textView20);
                t4 = itemView.findViewById(R.id.bestprice);
                t5 = itemView.findViewById(R.id.producttitle);
                productimage=itemView.findViewById(R.id.productimage);
                addtocart = itemView.findViewById(R.id.textView22);





            }
        }

    }


















    private void DeleteCartSession(String sellerId,String userId) {
        String seller_id = sellerId;
        String User_id = userId;
        String productIdurl = "?users_Id="+User_id+"&seller_id="+seller_id;
        class dbManager extends AsyncTask<String, Void, String>
        {
            protected void onPostExecute(String data)
            {
                try {
                    //  Toast.makeText(ProductView.this,data,Toast.LENGTH_LONG).show();
                }
                catch (Exception ex)
                {
                    Toast.makeText(Homepage.this,"Something went wrong",Toast.LENGTH_LONG).show();
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
        obj.execute(DeleteCartSession+productIdurl);
    }

    public final boolean isInternetOn() {

        // get Connectivity Manager object to check connection
        ConnectivityManager connec =
                (ConnectivityManager)getSystemService(getBaseContext().CONNECTIVITY_SERVICE);

        // Check for network connections
        if ( connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo (1).getState() == android.net.NetworkInfo.State.CONNECTED ) {
            return true;

        } else if (
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.DISCONNECTED ||
                        connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.DISCONNECTED  ) {

            Intent intent = new Intent(Homepage.this,IntenrNetconnection.class);
            startActivity(intent);

            Toast.makeText(this, " Not Connected ", Toast.LENGTH_LONG).show();
            return false;
        }
        return false;
    }
    //skeleton loader function




    @Override
    public void onBackPressed() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Exit Application?");
        alertDialogBuilder
                .setMessage("Click yes to exit!")
                .setCancelable(false)
                .setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                moveTaskToBack(true);
                                android.os.Process.killProcess(android.os.Process.myPid());
                                System.exit(1);
                            }
                        })

                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        dialog.cancel();
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}