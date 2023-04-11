package com.example.statozone;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.text.HtmlCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.facebook.shimmer.ShimmerFrameLayout;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class ProductView extends AppCompatActivity {
    private final static String storeDetaileUrl = "https://food.obla.co.in/app/Singlestore.php";
    private final static String UrlAddCart = "https://zone.tbvcsoft.com/app/addTocart.php";
    private final static String productDetaileUrl ="https://zone.tbvcsoft.com/app/singleproduct.php";
    private final static String getCarturl = "https://zone.tbvcsoft.com/app/Getcartdetails.php";
    private final static String generalSetting = " " ;
    private final static String DeleteCartSession = " ";
    private static final DecimalFormat df = new DecimalFormat("0.00");
    private TextView strName, ProductPriceDiscount, stradrs,strRating,strTime,strMaximum, storename, prdoctName,prdoctprice, textprName, prdoctdescription, addbtn, crtcnt,GST_value,Min_value;
    private ImageView backBtn, strImage, prdImage,Home;
    private SharedPreferences myPrefs;
    private Button plus,minus;
    private RelativeLayout footer,MaterialBox;
    private LinearLayout btncount,prdDtl,linearLayout1;
    private String id,symbol="₹";
    int minteger=0;
    int gst=0;
    private ShimmerFrameLayout skeletonPr;
    float productprise = 0;
    //Store detail
    private String asdf;
    private static String store_Id[];
    private static String storeName[];
    private static String storedescription[];
    private static String storeImage[];
    private static String storeRating[];
    private static String storeMax[];
    private static String storeUserId[];
    private static String storeTime[];
    //loader skeleton

    //product detail
    private RecyclerView lgrd;
    //private ProductImage imageSlider;
    private ArrayList<ProductImageslider> sliderProductArrayList = new ArrayList<ProductImageslider>();
    private View sliderView;
    private String cartide;
    private static String product_id[];
    private static Integer product_price[];
    private static String product_desc[];
    private static String product_image[];
    private static String product_name[];
    private static String product_photoSlide[];
    private static Float[] product_Discount;


    private static int Min_Qty[];
    private static int GST[];
    //get cart detail for api
    private static String Cart_id[];
    private static Integer Cart_price[];
    private static String Cart_product_id[];
    private static Integer Cart_pr_item[];
    //get detail store
    private static String store_id,pr_id,user_id,prNAme,crtStoreId;
    private static float TotalamountC=0;
    private static int total_qty=0;
    private static int Product_price_cnt=0;
    private static int totalqtypr=0;
    private static float TotalCartShowAmmount=0;
    private static float pdp=0;
    private static int crtshamount=0;
    private static int crtshowqty=0;
    //cart detail value
    private static String Storenamecart;
    private  static int newgst;
    private float totalnewprice;
    private TextView Retail_Marging;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_product_view);
        strName = (TextView)findViewById(R.id.strName);
        stradrs = (TextView)findViewById(R.id.stradrs);
        strRating = (TextView)findViewById(R.id.strRating);
        strTime = (TextView)findViewById(R.id.strTime);
        ProductPriceDiscount = (TextView) findViewById(R.id.ProductPriceDiscount);
        Retail_Marging = (TextView) findViewById(R.id.retail_margin);
        GST_value = (TextView) findViewById(R.id.gst);
        Min_value = (TextView) findViewById(R.id.min_qty);
        strMaximum = (TextView)findViewById(R.id.strMaximum);
        prdoctName  = (TextView)findViewById(R.id.prdoctName);
        storename = (TextView)findViewById(R.id.storeName);
        prdoctprice = (TextView)findViewById(R.id.prdoctprice);
        textprName= (TextView)findViewById(R.id.textprName);
        prdoctdescription = (TextView)findViewById(R.id.prdoctdescription);
        strImage = (ImageView)findViewById(R.id.strImage);
        lgrd = (RecyclerView) findViewById(R.id.List_crt_pr);
        backBtn= (ImageView)findViewById(R.id.back);
        btncount= (LinearLayout)findViewById(R.id.btncount);
        addbtn = (TextView)findViewById(R.id.addbtn);
        skeletonPr = findViewById(R.id.skeletonPr);
        plus =(Button)findViewById(R.id.increase);
        minus = (Button)findViewById(R.id.decrease);
        footer = (RelativeLayout)findViewById(R.id.footer);
        Home = (ImageView)findViewById(R.id.home);

        prdDtl = (LinearLayout) findViewById(R.id.prdDtl);
        linearLayout1 = (LinearLayout) findViewById(R.id.linearLayout1);
        MaterialBox =(RelativeLayout)findViewById(R.id.MaterialBox);

        ArrayList<itemqountityModule> qountitylist = new ArrayList<>();

        RecyclerView recyclerviewQuntity = findViewById(R.id.recyclerviewquntity);
        recyclerviewQuntity.setLayoutManager(new LinearLayoutManager(this));
        qunttityAdapter qunttityAdapter = new qunttityAdapter(qountitylist,this);

        recyclerviewQuntity.setAdapter(qunttityAdapter);

        TextView descri = findViewById(R.id.item_descri);
        TextView incre = findViewById(R.id.item_incre);
        TextView totalitem = findViewById(R.id.totalitem);


//        descri.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                int minusvalue = Integer.parseInt(String.valueOf(totalitem));
//                if (minusvalue!=0)
//                {
//                    int newvalue = minusvalue-1;
//                    totalitem.setText(newvalue);
//
//                }
//                else return ;
//            }
//        });




        qountitylist.add(new itemqountityModule("100","15","300","mix"));
        qountitylist.add(new itemqountityModule("100","15","300","Blue"));
        qountitylist.add(new itemqountityModule("100","15","300","Red"));
        qountitylist.add(new itemqountityModule("100","15","300","Green"));
        qountitylist.add(new itemqountityModule("100","15","300","Yellow"));
        qountitylist.add(new itemqountityModule("100","15","300","White"));
        qountitylist.add(new itemqountityModule("100","15","300","Pink"));
        qountitylist.add(new itemqountityModule("100","15","300","SkyBlue"));

        sliderView = findViewById(R.id.imageSlider);
        //get store value
        myPrefs = getSharedPreferences("myPrefs", MODE_PRIVATE);
        user_id = myPrefs.getString("user_id", "");

        //pass value activity
        Intent intent = getIntent();


       // pr_id = "500";


        pr_id= intent.getStringExtra("product_id");

        store_id = intent.getStringExtra("storeUser_id");
        storedetail(user_id);

        //invisile

        btncount.setVisibility(View.GONE);





        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ProductView.this, user_id, Toast.LENGTH_SHORT).show();
                cartide = myPrefs.getString("crtstrid", "");
                if(cartide.isEmpty()) {
                    SharedPreferences myPrefs = getSharedPreferences("myPrefs", MODE_PRIVATE);
                    SharedPreferences.Editor prefsEditor;
                    prefsEditor = myPrefs.edit();
                    prefsEditor.putString("crtstrid",crtStoreId );
                    prefsEditor.putString("product_id",pr_id );
                    prefsEditor.commit();
                    increaseInteger();
                }
                else {
                    int firstcon = Integer. parseInt(cartide);
                    int Secoundcon = Integer. parseInt(store_id);
                    if (firstcon == Secoundcon) {
                        increaseInteger();
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(ProductView.this);
                        builder.setTitle("Please Clear Cart").
                                setMessage("sure, You want to Clear Cart?");
                        builder.setPositiveButton("Yes",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        DeleteCartSession(cartide,user_id);
                                        SharedPreferences myPrefs = getSharedPreferences("myPrefs", MODE_PRIVATE);
                                        SharedPreferences.Editor prefsEditor;
                                        prefsEditor = myPrefs.edit();
                                        prefsEditor.remove("crtstrid");
                                        prefsEditor.putString("crtstrid",crtStoreId );
                                        prefsEditor.commit();
                                        Intent intent = getIntent();
                                        increaseInteger();
                                        Product_price_cnt=0;
                                        total_qty=0;
                                        finish();
                                        startActivity(intent);
                                    }
                                });
                        builder.setNegativeButton("No",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });
                        AlertDialog alert11 = builder.create();
                        alert11.show();
                    }
                }
            }
        });




        footer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProductView.this,CartView.class);
                startActivity(intent);
            }
        });




        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decreaseInteger();
            }
        });



        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                cartide = myPrefs.getString("crtstrid", "");
                if(cartide.isEmpty()) {
                    SharedPreferences myPrefs = getSharedPreferences("myPrefs", MODE_PRIVATE);
                    SharedPreferences.Editor prefsEditor;
                    prefsEditor = myPrefs.edit();
                    prefsEditor.putString("crtstrid",crtStoreId );
                    prefsEditor.putString("product_id",pr_id );
                    prefsEditor.commit();
                    addbtn.setVisibility(View.GONE);
                    btncount.setVisibility(View.VISIBLE);
                    footer.setVisibility(View.VISIBLE);
                    increaseInteger();
                }
                else {
                    int firstcon = Integer. parseInt(cartide);
                    int Secoundcon = Integer. parseInt(store_id);
                    if (firstcon == Secoundcon) {
                        addbtn.setVisibility(View.GONE);
                        btncount.setVisibility(View.VISIBLE);
                        footer.setVisibility(View.VISIBLE);
                        increaseInteger();
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(ProductView.this);
                        builder.setTitle("Replace cart item?").
                                setMessage("Your cart contains dishes from "+Storenamecart+". Do you want to discard the selection and add dishes from "+Storenamecart);
                        builder.setPositiveButton("Yes",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        DeleteCartSession(cartide,user_id);
                                        SharedPreferences myPrefs = getSharedPreferences("myPrefs", MODE_PRIVATE);
                                        SharedPreferences.Editor prefsEditor;
                                        prefsEditor = myPrefs.edit();
                                        prefsEditor.remove("crtstrid");
                                        prefsEditor.putString("crtstrid",crtStoreId );
                                        prefsEditor.commit();
                                        increaseInteger();
                                        Intent intent = getIntent();
                                        finish();
                                        Product_price_cnt=0;
                                        total_qty=0;
                                        startActivity(intent);
                                    }
                                });
                        builder.setNegativeButton("No",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });
                        AlertDialog alert11 = builder.create();
                        alert11.show();
                    }
                }
            }
        });





        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProductView.this,Storeproduct.class);
                intent.putExtra("storeUser_id", store_id);
                startActivity(intent);
            }
        });


       Home .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProductView.this, categories.class);
                intent.putExtra("storeUser_id", store_id);
                startActivity(intent);
            }
        });



















        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });



        footer.setVisibility(View.GONE);
        MaterialBox.setVisibility(View.GONE);
        //get store value
        //AddtoCart(12,pr_id,user_id, TotalamountC);


       productdetail(pr_id);
       ProductCart(user_id);


      isInternetOn();
    }







    private void storedetail(String store_id) {
        String storeId = store_id;
        String storeIdUrl = "?users_Id="+storeId;
        class dbManager extends AsyncTask<String, Void, String>
        {
            protected void onPostExecute(String data)
            {
                try {
                    JSONArray ja = new JSONArray(data);
                    JSONObject jo = null;
                    storeName = new String[ja.length()];
                    storeImage = new String[ja.length()];
                    store_Id = new String[ja.length()];
                    storeRating = new String[ja.length()];
                    storeUserId = new String[ja.length()];
                    storedescription = new String[ja.length()];
                    storeMax = new String[ja.length()];
                    storeTime = new String[ja.length()];
                    for (int i = 0; i < ja.length(); i++) {
                        jo = ja.getJSONObject(i);
                        storeName[i] = jo.getString("name");
                        store_Id[i] = jo.getString("id");
                        storeRating[i] = jo.getString("rating");
                        storeMax[i] = jo.getString("maximum");
                        storeUserId[i] = jo.getString("user_id");
                        storeTime[i] = jo.getString("timeaprox");
                        storedescription[i] = jo.getString("description");
                        storeImage[i] ="https://food.obla.co.in/uploads/shop_profile/" + jo.getString("image");
                        stradrs.setText(storedescription[i]);
                        strName.setText(storeName[i]);
                        strRating.setText(storeRating[i]);
                        strTime.setText(storeTime[i]);
                        storename.setText(storeName[i]+" > ");
                        strMaximum.setText(storeMax[i]+" FOR TWO");
                        String url=storeImage[i];
                        crtStoreId = storeUserId[i];
                        Glide.with(ProductView.this).load(url).into(strImage);
                        Storenamecart = storeName[i];
                    }
                }
                catch (Exception ex)
                {
                  //  Toast.makeText(ProductView.this,asdf,Toast.LENGTH_LONG).show();
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
        obj.execute(storeDetaileUrl+storeIdUrl);
    }


    private void productdetail(String pr_id) {
        String productId = pr_id;
        String productIdurl ="?users_Id="+productId;
        class dbManager extends AsyncTask<String, Void, String>
        {
            protected void onPostExecute(String data)
            {
                try {
                    JSONArray ja = new JSONArray(data);
                    JSONObject jo = null;

                    // DECLARATION OF VARIABLE




                        product_name = new String[ja.length()];
                        product_image = new String[ja.length()];
                        product_id = new String[ja.length()];
                        product_price = new Integer[ja.length()];
                        product_desc = new String[ja.length()];
                        product_photoSlide = new String[ja.length()];
                        product_Discount = new Float [ja.length()];
                        Min_Qty = new int[ja.length()];
                        GST= new int [ja.length()];



                        for (int i = 0; i < ja.length(); i++) {
                            jo = ja.getJSONObject(i);
                            //
                            // Intialization of variable
                            product_name[i] = jo.getString("name");
                            product_id[i] = jo.getString("id");
                            product_price[i] = jo.getInt("price");
                            product_desc[i] = jo.getString("description");
                            product_image[i] ="https://zone.tbvcsoft.com/images/thumbnail/" + jo.getString("image");
                            product_photoSlide[i] = jo.getString("photos");
                            product_Discount[i] = Float.valueOf(jo.getString("discount"));
                            Min_Qty[i] = jo.getInt("qty");
                            GST[i] = jo.getInt("gst");

                        // Price Variable
                        float discountPrice = (product_price[i]*product_Discount[i])/100;
                        float TotalDiscount = product_price[i] - discountPrice;




                        //String UnitPrice = "<del>"+symbol+Math.round(Integer.parseInt(String.valueOf(product_price[i])))+"</del>";
                            Gst(GST[i],product_Discount[i]);
                            price(GST[i]);

                            float newprice =( product_Discount[i]*newgst)/100 ;
                             totalnewprice =( product_Discount[i]+newprice) ;

                         //   int Margin = ((product_price[i]-totalnewprice )/(product_price[i]));
                            float M  = (product_price[i]-totalnewprice) ;

                           float b = Math.round(M );

                            float Margin = b*100/product_price[i] ;

                            pdp=product_Discount[i];


                        // Product Description from HTML tag
                        prdoctdescription.setText(HtmlCompat.fromHtml(product_desc[i], HtmlCompat.FROM_HTML_MODE_LEGACY));
                        prdoctprice.setText(String.valueOf(product_price[i]));
                        ProductPriceDiscount.setText(HtmlCompat.fromHtml(String.valueOf(product_Discount[i]), HtmlCompat.FROM_HTML_MODE_LEGACY));

                        // Set Price for product
                        ProductPriceDiscount.setText(symbol+" "+ totalnewprice);

                        prdoctName.setText(product_name[i]);
                        textprName.setText(product_name[i]);
                        Min_value.setText(String.valueOf(Min_Qty[i]));
                        Retail_Marging.setText(String.valueOf(Margin) +" %");


                        Glide.with(ProductView.this).load(product_image[i]).into((ImageView) sliderView);

                        productprise = TotalDiscount;

                        prNAme = product_name[i];
                       //gst=0;



                        // Creating Array for image Slider
                        String[] result = product_photoSlide[i].split("  ");

                        for (String str : result) {
                            sliderProductArrayList.add(new ProductImageslider("https://food.tbvcsoft.com/uploads/products/"+str));
                        }

                        // Pass the Value of image in ArrayList

                        //imageSlider = new ProductImage(sliderProductArrayList, ProductView.this);

                        // Set image on Slider
                        //  sliderView.setSliderAdapter(imageSlider);


                        //  sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM);
                        //set indicator animation by using IndicatorAnimationType. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
                        //  sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);

                        //  sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);

                        //  sliderView.setIndicatorSelectedColor(Color.WHITE);
                        //  sliderView.setIndicatorUnselectedColor(Color.GRAY);


                        //   sliderView.setScrollTimeInSec(2); //set scroll delay in seconds :
                        //  sliderView.startAutoCycle();

                    }
                    // Creating Loader for Variable loading


                   // footer.setVisibility(View.VISIBLE);
                   // MaterialBox.setVisibility(View.VISIBLE);


                    skeletonPr.startShimmer();
                    skeletonPr.setVisibility(View.GONE);
                    footer.setVisibility(View.VISIBLE);
                    MaterialBox.setVisibility(View.VISIBLE);

                }

                catch (Exception ex)
                {
                    footer.setVisibility(View.GONE);
                  //  Toast.makeText(ProductView.this,pr_id,Toast.LENGTH_LONG).show();
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
        obj.execute(productDetaileUrl+productIdurl);
    }









    private void AddtoCart(Integer number, String pr_id, String user_id, float Total_amount, Integer Min_qty)
    {
        Integer qty = number;
        Integer m_qty=Min_qty;
        String productId = pr_id;
        String UserId = user_id;
        float aproductprise = Total_amount;
        String Pr_name= prNAme;
        String AddTOcartUrl = "?users_Id="+UserId+"&product_Id="+productId+"&quantity="+qty+"&ProductPric="+pdp+"&ProductName="+Pr_name+"&sellerid="+store_id+"&Min_qty="+m_qty+"&ProductPrice="+aproductprise+"&gst="+newgst;
        class dbManager extends AsyncTask<String, Void, String>
        {
            protected void onPostExecute(String data)
            {
                try {
                    // Toast.makeText(ProductView.this,data,Toast.LENGTH_LONG).show();
                }
                catch (Exception ex)
                {
                    Toast.makeText(ProductView.this,"Something went wrong",Toast.LENGTH_LONG).show();
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



    private void ProductCart(String user_id) {
        String UserId = user_id;
        String getTOcartUrl = "?users_Id="+UserId;
        class dbManager extends AsyncTask<String, Void, String>
        {
            protected void onPostExecute(String data)
            {
                try {
                    JSONArray ja = new JSONArray(data);
                    JSONObject jo = null;
                    Cart_id = new String[ja.length()];
                    Cart_price = new Integer[ja.length()];
                    Cart_product_id = new String[ja.length()];
                    Cart_pr_item = new Integer[ja.length()];
                    int productPriceTotal=0,prqty=0,prsingleqty=0,shwcrtamt=0,shwqty=0;
                    for (int i = 0; i < ja.length(); i++) {
                        jo = ja.getJSONObject(i);
                        Cart_id[i] = jo.getString("id");
                        Cart_price[i] = jo.getInt("product_price");
                        Cart_product_id[i] = jo.getString("product_id");
                        Cart_pr_item[i] = jo.getInt("quantity");
                        productPriceTotal = productPriceTotal+Cart_price[i];
                        shwcrtamt = shwcrtamt+Cart_price[i];
                        prqty = prqty+Cart_pr_item[i];
                        shwqty= shwqty+Cart_pr_item[i];
                        int prid=Integer.parseInt(pr_id);
                        int sngprid = Integer.parseInt(Cart_product_id[i]);
                        if(prid == sngprid)
                        {
                            prsingleqty = Cart_pr_item[i];
                        }
                        if(prid == sngprid)
                        {
                            productPriceTotal =productPriceTotal - Cart_price[i];
                            prqty = prqty-Cart_pr_item[i];
                        }
                    }
                    crtshamount= shwcrtamt;
                    crtshowqty = shwqty;
                    Product_price_cnt =productPriceTotal;
                    total_qty = prqty;
                    minteger = prsingleqty;
                    showcardtdetail(minteger);
                }
                catch (Exception ex)
                {
                    footer.setVisibility(View.GONE);
                    Product_price_cnt=0;
                    total_qty=0;
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
        obj.execute(getCarturl+getTOcartUrl);
    }



    public void increaseInteger() {
        if(minteger<100) {
            minteger = minteger + Min_Qty[0];
            TotalamountC = totalnewprice * minteger;
            display(minteger);
        }
    }



    public void decreaseInteger() {
        if(minteger>0)
        {
            minteger = minteger - Min_Qty[0];
            TotalamountC = totalnewprice * minteger;
            display(minteger);
        }
        if(minteger<1)
        {
            addbtn.setVisibility(View.VISIBLE);
            btncount.setVisibility(View.GONE);
        }
    }

    public void Gst(int asgt , float price){
        gst=asgt;

        if (gst==6) {

            GST_value.setText("₹"+price+" + "+"0 %");

        }
        if (gst==7) {

            GST_value.setText("₹"+price+" + "+"5 %");

        }
        if (gst==8) {

            GST_value.setText("₹"+price+" + "+"12 %");


        }
        if (gst==9) {

            GST_value.setText("₹"+price+" + "+"18 %");

        }
        if (gst==10) {

            GST_value.setText("₹"+price+" + "+"28 %");

        }




    }

    public void price( int asgt )  {
        gst=asgt;

        if (gst==6) {
            newgst=0;


        }
        if (gst==7) {
            newgst=5;


        }
        if (gst==8) {
            newgst=12;



        }
        if (gst==9) {
            newgst=18;


        }
        if (gst==10) {
            newgst=28;


        }




    }



    private void display(int number) {
        TextView displayInteger = (TextView) findViewById(R.id.integer_number);
        TextView viewPrice = (TextView) findViewById(R.id.viewPrise);
        crtcnt = (TextView)findViewById(R.id.crtcnt);
        TotalCartShowAmmount = TotalamountC+Product_price_cnt;
        totalqtypr = number + total_qty;
        if(totalqtypr==0)
        {
            footer.setVisibility(View.GONE);
            SharedPreferences myPrefs = getSharedPreferences("myPrefs", MODE_PRIVATE);
            SharedPreferences.Editor prefsEditor;
            prefsEditor = myPrefs.edit();
            prefsEditor.remove("crtstrid");
            prefsEditor.commit();
        }
        displayInteger.setText("" + number);
        crtcnt.setText(totalqtypr+" ITEMS");
        viewPrice.setText(symbol+""+TotalCartShowAmmount);
        // data get aad to cart function
        AddtoCart(number,pr_id,user_id, TotalamountC,Min_Qty[0]);
    }

    private void showcardtdetail(int number) {
        footer.setVisibility(View.VISIBLE);
        minteger = number;
        if(minteger>0)
        {
            addbtn.setVisibility(View.GONE);
            btncount.setVisibility(View.VISIBLE);
        }
        if(minteger<1)
        {
            addbtn.setVisibility(View.VISIBLE);
            btncount.setVisibility(View.GONE);
        }
        TextView displayInteger = (TextView) findViewById(R.id.integer_number);
        TextView viewPrice = (TextView) findViewById(R.id.viewPrise);
        crtcnt = (TextView)findViewById(R.id.crtcnt);
        TotalCartShowAmmount = TotalamountC+Product_price_cnt;
        totalqtypr = number+total_qty;
        displayInteger.setText("" + number);
        crtcnt.setText(totalqtypr+" ITEMS");
        viewPrice.setText(symbol+""+crtshamount);

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
                    Toast.makeText(ProductView.this,"Something went wrong",Toast.LENGTH_LONG).show();
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


    /*
    public class ProductImage extends SliderViewAdapter<ProductImage.ViewHolder>  {
        private ArrayList<ProductImageslider> sliderProductArrayList;    // Values to be displayed
        private Context context;
        public ProductImage(ArrayList<ProductImageslider> sliderProductArrayList, Context context) {
            this.sliderProductArrayList = sliderProductArrayList;
            this.context = context;
        }
        @NonNull

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.productimageslider, parent, false);
            return new ProductImage.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ProductImage.ViewHolder holder, int position) {
            Glide.with(ProductView.this).load(sliderProductArrayList.get(position).getImage()).into(holder.categoryName);
        }


        @Override
        public int getCount() {
            return sliderProductArrayList.size();
        }

        public class ViewHolder extends SliderViewAdapter.ViewHolder {
            // creating variables for our views.
            private ImageView categoryName;
            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                categoryName = (ImageView) itemView.findViewById(R.id.prdctImage);
            }
        }
    }

     */

    public final boolean isInternetOn() {
        // get Connectivity Manager object to check connection
        ConnectivityManager connec = (ConnectivityManager)getSystemService(getBaseContext().CONNECTIVITY_SERVICE);
        // Check for network connections
        if ( connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED ) {
            return true;
        } else if (
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.DISCONNECTED ||
                        connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.DISCONNECTED  ) {
           // Intent intent = new Intent(ProductView.this, com.example.oblapartner.InterNetconnection.class);
           // startActivity(intent);
            Toast.makeText(this, " Not Connected ", Toast.LENGTH_LONG).show();
            return false;
        }
        return false;
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }


}