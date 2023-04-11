package com.example.statozone;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
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
import java.util.ArrayList;

public class CartView extends AppCompatActivity {
    private final static String generalSetting = Config.Base_url+"zeneral_setting.php";
    private final static String UrlAddCart = "https://zone.tbvcsoft.com/app/addTocart.php";
    private final static String checkout = "https://zone.tbvcsoft.com/app/checkout.php";
    private final static String getCarturl = "https://zone.tbvcsoft.com/app/Getcartdetails.php";
    private final static String storeDetaileUrl = Config.Base_url+"StoreCharges.php";
    private ImageView  home, lefttbn,paymentImage,strImage;
    private ArrayList<ProductCartList> cartProductArrayList = new ArrayList<ProductCartList>();
    private RelativeLayout crtfull,footer;
    private LinearLayout selectAddress,paymentSelect,pytmimg,crtempty,DeliveryTypeCharges;
    private RecyclerView List_crt_pr;
    private TextView paymentupiType,paymentName,RestaurantType;
    private TextView strName, stradrs,plcbtn,strRating,strTime,strMaximum, storecharge,delivery_charge,totalproductprice,totalpay_amount;
    private SharedPreferences myPrefs;
    private ShimmerFrameLayout skeletonPr;
    private MyAdapter adapter1;
    private String user_id,pr_id,store_id,selectAddressplace,userName,email,phone,shppingaddress;
    private String  Cart_id[];
    private String Cart_product_id[];
    private String prName[];
    private int[] Qty;
    private Integer Cart_price[],Cart_pr_item[];
    private int ptTotalAmount,delivery ,restaurantcharg,totalqty;
    private int paymenttype=0 ,total_amount;
    private int minteger=0;
    private int deliveryTypeCharge;
    //Store detail
    private static String store_Id[];
    private static String storeName[];
    private static String storedescription[];
    private static String storeImage[];
    private static String storeRating[];
    private static String storeMax[];
    private static String storeTime[];
    private static String store_charge[];
    String business_category,symbol="₹";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_view);
        home = (ImageView)findViewById(R.id.home);
        lefttbn = (ImageView)findViewById(R.id.lefttbn);
        selectAddress = (LinearLayout)findViewById(R.id.selectAddress);
        paymentSelect  = (LinearLayout)findViewById(R.id.paymentSelect);
        paymentImage = (ImageView)findViewById(R.id.paymentImage);
        paymentupiType = (TextView)findViewById(R.id.paymentupiType);
        paymentName = (TextView) findViewById(R.id.paymentName);
        pytmimg = (LinearLayout) findViewById(R.id.pytmimg);
        crtempty = (LinearLayout)findViewById(R.id.crtempty);
        crtfull = (RelativeLayout)findViewById(R.id.crtfull);
        footer = (RelativeLayout)findViewById(R.id.footer);
        List_crt_pr = (RecyclerView) findViewById(R.id.List_crt_pr);
        strImage = (ImageView)findViewById(R.id.strImage);
        strName = (TextView)findViewById(R.id.strName);
        stradrs = (TextView)findViewById(R.id.stradrs);
        strRating = (TextView) findViewById(R.id.strRating);
        strTime = (TextView)findViewById(R.id.strTime);
        strMaximum = (TextView)findViewById(R.id.strMaximum);
        storecharge = (TextView)findViewById(R.id.storecharge);

        totalproductprice = (TextView)findViewById(R.id.totalproductprice);
        totalpay_amount =(TextView)findViewById(R.id.totalpay_amount);
        plcbtn = (TextView)findViewById(R.id.plcbtn);
        skeletonPr = findViewById(R.id.skeletonPr);
        RestaurantType = (TextView) findViewById(R.id.RestaurantType);
        DeliveryTypeCharges = findViewById(R.id.DeliveryTypeCharges);
        crtempty.setVisibility(View.GONE);
        DeliveryTypeCharges.setVisibility(View.VISIBLE);



        paymentSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CartView.this,PaymentSelect.class);
                intent.putExtra("amountPay", total_amount);
                startActivity(intent);
            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CartView.this,Account.class);
                startActivity(intent);
            }
        });

        lefttbn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        //get store details
        myPrefs = getSharedPreferences("myPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = myPrefs.edit();
        pr_id = myPrefs.getString("product_id", "");
        selectAddressplace = myPrefs.getString("select_place_address", "");
        store_id = myPrefs.getString("crtstrid", "");
        user_id = myPrefs.getString("user_id", "");
        userName=myPrefs.getString("user_Name", "");
        phone=myPrefs.getString("user_Phone", "");
        deliveryTypeCharge= myPrefs.getInt("deliveryType",0);
        email=myPrefs.getString("user_Email", "");
        shppingaddress=myPrefs.getString("shippingaddress", "");
        myPrefs = getSharedPreferences("myPrefs",MODE_PRIVATE);
        paymenttype = myPrefs.getInt("Payment_type",0);

        if(deliveryTypeCharge == 1) {
            delivery =10;
        }


        selectAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              if(selectAddressplace.isEmpty()) {
                  Intent intent = new Intent(CartView.this, ManageAddress.class);
                  startActivity(intent);
              }
              else
              {
                  if(paymenttype == 13) {
                     String paymenttpyeupi = "COD";
                      Checkout(user_id, store_id,paymenttpyeupi);
                  }
                  else
                  {
                    Toast.makeText(CartView.this,"Not Active payment method",Toast.LENGTH_SHORT).show();
                  }
              }
            }
        });

        homesetting();
        storedetail(store_id);
        ProductCart(user_id);
        isInternetOn();
        paymentoption();
        crtfull.setVisibility(View.GONE);
        footer.setVisibility(View.GONE);
    }






    private void paymentoption() {
        if(paymenttype == 1)
        {
            pytmimg.setVisibility(View.VISIBLE);
            paymentName.setText("Paytm UPI");
            Glide.with(this).load(R.drawable.paytm).into(paymentImage);
        }
        else if(paymenttype == 2)
        {
            pytmimg.setVisibility(View.VISIBLE);
            paymentName.setText("PhonePe");
            Glide.with(this).load(R.drawable.phonepay).into(paymentImage);
        }
        else if(paymenttype == 3)
        {
            pytmimg.setVisibility(View.VISIBLE);
            paymentName.setText("Not Active");
            Glide.with(this).load(R.drawable.creditcard).into(paymentImage);
        }
        else if(paymenttype == 4)
        {
            pytmimg.setVisibility(View.VISIBLE);
            paymentName.setText("Not Active");
            Glide.with(this).load(R.drawable.sodexo).into(paymentImage);
        }
        else if(paymenttype == 5)
        {
            pytmimg.setVisibility(View.VISIBLE);
            paymentName.setText("Google Pay");
            Glide.with(this).load(R.drawable.gpay).into(paymentImage);
        }
        else if(paymenttype == 6)
        {
            pytmimg.setVisibility(View.VISIBLE);
            paymentName.setText("Bhim");
            Glide.with(this).load(R.drawable.bhimupi).into(paymentImage);
        }
        else if(paymenttype == 7)
        {
            pytmimg.setVisibility(View.VISIBLE);
            paymentName.setText("Not Active");
            Glide.with(this).load(R.drawable.upi).into(paymentImage);
        }
        else if(paymenttype == 8)
        {
            pytmimg.setVisibility(View.VISIBLE);
            paymentName.setText("Not Active");
            Glide.with(this).load(R.drawable.paytm).into(paymentImage);
        }
        else if(paymenttype == 9)
        {
            pytmimg.setVisibility(View.VISIBLE);
            paymentName.setText("Not Active");
            Glide.with(this).load(R.drawable.mobikwik).into(paymentImage);
        }
        else if(paymenttype == 10)
        {
            pytmimg.setVisibility(View.VISIBLE);
            paymentName.setText("Not Active");
            Glide.with(this).load(R.drawable.freecharge).into(paymentImage);
        }
        else if(paymenttype == 11)
        {
            pytmimg.setVisibility(View.VISIBLE);
            paymentName.setText("Not Active");
            Glide.with(this).load(R.drawable.netbanking).into(paymentImage);
        }
        else if(paymenttype == 12)
        {
            pytmimg.setVisibility(View.VISIBLE);
            paymentName.setText("Not Active");
            Glide.with(this).load(R.drawable.lazy).into(paymentImage);
        }
        else if(paymenttype == 13)
        {
            pytmimg.setVisibility(View.VISIBLE);
            paymentName.setText("Cash On Delivery");
            Glide.with(this).load(R.drawable.cod).into(paymentImage);
        }
        else
        {
            pytmimg.setVisibility(View.GONE);
            paymentName.setText("Select Payment Type");
        }
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
                    prName = new String[ja.length()];
                    Cart_id = new String[ja.length()];
                    Qty = new int[ja.length()];

                    int productPriceTotal=0;
                    for (int i = 0; i < ja.length(); i++) {
                        jo = ja.getJSONObject(i);
                        Cart_id[i] = jo.getString("id");
                        Cart_price[i] = jo.getInt("product_price");
                        Cart_product_id[i] = jo.getString("product_id");
                        prName[i] = jo.getString("name");
                        Cart_pr_item[i] = jo.getInt("quantity");
                        Qty[i] = jo.getInt("Min_qty");

                        productPriceTotal = productPriceTotal+Cart_price[i];
                        ptTotalAmount = productPriceTotal;

                        cartProductArrayList.add(new ProductCartList(Cart_id[i], Cart_price[i], Cart_product_id[i], Cart_pr_item[i],prName[i],Qty[i]));
                    }
                    showbilldetail();
                    adapter1 = new MyAdapter(cartProductArrayList, CartView.this);
                    LinearLayoutManager horizontalLayoutManagaer
                            = new LinearLayoutManager(CartView.this, LinearLayoutManager.VERTICAL, false);
                    List_crt_pr.setHasFixedSize(true);
                    List_crt_pr.setLayoutManager(horizontalLayoutManagaer);
                    List_crt_pr.setAdapter(adapter1);
                    skeletonPr.startShimmer();
                    skeletonPr.setVisibility(View.GONE);
                    crtfull.setVisibility(View.VISIBLE);
                    footer.setVisibility(View.VISIBLE);
                    crtempty.setVisibility(View.GONE);
                }
                catch (Exception ex)
                {
                    skeletonPr.setVisibility(View.GONE);
                    crtfull.setVisibility(View.GONE);
                   crtempty.setVisibility(View.VISIBLE);
                    footer.setVisibility(View.GONE);
                   //   Toast.makeText(CartView.this,data,Toast.LENGTH_LONG).show();
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



    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>  {
        private ArrayList<ProductCartList> cartProductArrayList;    // Values to be displayed
        private Context context;

        public MyAdapter(ArrayList<ProductCartList> cartProductArrayList, Context context) {
            this.cartProductArrayList = cartProductArrayList;
            this.context = context;
        }
        @NonNull
        @Override
        public MyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            // below line is to inflate our layout.
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cartproductlist, parent, false);
            return new MyAdapter.ViewHolder(view);
        }
        @Override
        public void onBindViewHolder(@NonNull MyAdapter.ViewHolder holder, int position) {
            holder.productName.setText(cartProductArrayList.get(position).prName);
            int  productItems = cartProductArrayList.get(position).ProductItem;
            String ProductIds = cartProductArrayList.get(position).Productid;
            int qty = cartProductArrayList.get(position).Qty;

            String ItemQty = ""+Math.round(Float.parseFloat(cartProductArrayList.get(position).ProductItem+""))+"";
            String UnitPricemain = symbol+" "+Math.round(Float.parseFloat(cartProductArrayList.get(position).Productprice+""))+"";
            holder.productQty.setText(HtmlCompat.fromHtml(ItemQty, HtmlCompat.FROM_HTML_MODE_LEGACY));
            holder.productPrice.setText(HtmlCompat.fromHtml(UnitPricemain, HtmlCompat.FROM_HTML_MODE_LEGACY));
            //getprodutprice
            int ProductPrice = cartProductArrayList.get(position).Productprice / productItems;
            //value define
            final int[] productQty = {productItems} ;
            final int[] productPr = {ProductPrice} ;
            final String[] ProductsIds = {ProductIds};
            final int[]  Qty = {qty};
            final int[] finalprice = {0};
            final int[] totalPrice = {0};
            holder.plus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(productQty[0] <100) {
                        productQty[0] = productQty[0] + Qty[0];
                        productPr[0] = productPr[0];
                        totalPrice[0] = productQty[0] * productPr[0];
                        finalprice[0] = ProductPrice * 1;
                        ptTotalAmount = ptTotalAmount + finalprice[0];
                        String ItemQty = " " + Math.round(Float.parseFloat(productQty[0] + "")) + "";
                        String UnitPricemain = symbol+" " + Math.round(Float.parseFloat(totalPrice[0] + "")) + "";
                        holder.productQty.setText(HtmlCompat.fromHtml(ItemQty, HtmlCompat.FROM_HTML_MODE_LEGACY));
                        holder.productPrice.setText(HtmlCompat.fromHtml(UnitPricemain, HtmlCompat.FROM_HTML_MODE_LEGACY));
                        AddtoCart(productQty[0], ProductsIds[0], user_id, totalPrice[0]);
                        showbilldetail();
                    }
                }
            });
            holder.minus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(productQty[0] >0) {
                        productQty[0] = productQty[0] - Qty[0];
                        productPr[0] = productPr[0];
                        totalPrice[0] = productQty[0] * productPr[0];
                        finalprice[0] = ProductPrice * 1;
                        ptTotalAmount = ptTotalAmount - finalprice[0];
                        String ItemQty = "" + Math.round(Float.parseFloat(productQty[0] + "")) + "";
                        String UnitPricemain =  symbol+" " + Math.round(Float.parseFloat(totalPrice[0] + "")) + "";
                        holder.productQty.setText(HtmlCompat.fromHtml(ItemQty, HtmlCompat.FROM_HTML_MODE_LEGACY));
                        holder.productPrice.setText(HtmlCompat.fromHtml(UnitPricemain, HtmlCompat.FROM_HTML_MODE_LEGACY));
                        AddtoCart(productQty[0], ProductsIds[0], user_id, totalPrice[0]);
                        showbilldetail();
                    }
                    if(productQty[0] == 0)
                    {
                        Intent reOpen = new Intent (CartView.this, CartView.class);
                        startActivity(reOpen);
                    }
                }
            });
        }
        @Override
        public int getItemCount() {
            // returning the size of array list.
            return cartProductArrayList.size();
        }
        public class ViewHolder extends RecyclerView.ViewHolder {
            // creating variables for our views.
          private    Button plus,minus;
          private   TextView productName,productQty,productPrice;
            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                plus = (Button) itemView.findViewById(R.id.increase);
                minus = (Button) itemView.findViewById(R.id.decrease);
                productName = (TextView) itemView.findViewById(R.id.crtcnt);
                productQty = (TextView) itemView.findViewById(R.id.integer_number);
                productPrice = (TextView) itemView.findViewById(R.id.crtcnt2);
            }
        }
    }


   //store detail


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
                   storedescription = new String[ja.length()];
                   storeMax = new String[ja.length()];
                   storeTime = new String[ja.length()];
                   store_charge = new  String[ja.length()];
                   for (int i = 0; i < ja.length(); i++) {
                       jo = ja.getJSONObject(i);
                       storeName[i] = jo.getString("name");
                       store_Id[i] = jo.getString("id");
                       storeRating[i] = jo.getString("rating");
                       storeMax[i] = jo.getString("maximum");
                       storeTime[i] = jo.getString("timeaprox");
                       store_charge[i] = jo.getString("store_charge");
                       storedescription[i] = jo.getString("description");
                       storeImage[i] ="https://food.tbvcsoft.com/uploads/shop_profile/" + jo.getString("image");
                       restaurantcharg = Integer.parseInt(store_charge[i]);
                       stradrs.setText(storedescription[i]);
                       strName.setText(storeName[i]);
                       strRating.setText(storeRating[i]);
                       strTime.setText(storeTime[i]);
                       strMaximum.setText(storeMax[i]+" FOR TWO");
                       storecharge.setText(symbol+" "+store_charge[i]);
                       delivery_charge.setText(symbol + " 10");

                       if(deliveryTypeCharge == 1) {
                           delivery_charge.setText(symbol + " 10");
                           DeliveryTypeCharges.setVisibility(View.GONE);
                       }
                       String url=storeImage[i];
                       Glide.with(CartView.this).load(url).into(strImage);
                   }
               }
               catch (Exception ex)
               {
                //   Toast.makeText(CartView.this,user_id,Toast.LENGTH_LONG).show();
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

    private void AddtoCart(Integer ProductQty,String pr_id, String user_id,Integer Total_amount) {

        Integer qty = ProductQty;
        String productId = pr_id;
        String UserId = user_id;
        Integer productprise = Total_amount;
        String AddTOcartUrl = "?users_Id="+UserId+"&product_Id="+productId+"&quantity="+qty+"&ProductPric="+productprise+"&sellerid="+store_id;

        class dbManager extends AsyncTask<String, Void, String>
        {

            protected void onPostExecute(String data)
            {
                try {
                    // Toast.makeText(ProductView.this,data,Toast.LENGTH_LONG).show();
                }
                catch (Exception ex)
                {
                   Toast.makeText(CartView.this,"Something went wrong",Toast.LENGTH_LONG).show();
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


    private void showbilldetail() {

        total_amount = 99+ptTotalAmount+delivery;
        totalproductprice.setText(symbol+" "+ptTotalAmount);
        totalpay_amount.setText(symbol+" "+total_amount);
        placebutton();
    }


    private void placebutton() {
        if(selectAddressplace.isEmpty()) {
            plcbtn.setText("Select Address at next step");
        }
        else
         {
            plcbtn.setText(symbol+" "+total_amount+" Total  Order Place");
        }
    }


    private void Checkout(String user_id,String Store_id,String paymenttpyeupi) {


        String  userid = user_id;
        String Storeid = Store_id;
        String paymenttype = paymenttpyeupi;
        String AddTOcartUrl = "?users_Id="+userid+"&store_id="+Storeid+"&grandtotal="+total_amount+"&restaurentcharge="+restaurantcharg+"&subtotal="+ptTotalAmount+"&shippingtext="+delivery+"&qty="+totalqty+"&username="+userName+"&phone="+phone+"&email="+email+"&address="+shppingaddress+"&paymenttype="+paymenttype;

        class dbManager extends AsyncTask<String, Void, String>
        {

            protected void onPostExecute(String data)
            {
                try {
                    JSONObject jObj = new JSONObject(data);
                    String id = jObj.getString("code");
                    SharedPreferences myPrefs = getSharedPreferences("myPrefs", MODE_PRIVATE);
                    SharedPreferences.Editor prefsEditor;
                    prefsEditor = myPrefs.edit();
                    prefsEditor.putString("order_code", id);
                    prefsEditor.remove("product_id");
                    prefsEditor.remove("crtstrid");
                    prefsEditor.commit();
                   // Toast.makeText(CartView.this,id,Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(CartView.this, Order_placed.class);
                     startActivity(intent);
                }
                catch (Exception ex)
                {
                    Toast.makeText(CartView.this,"no Api found",Toast.LENGTH_LONG).show();
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
        obj.execute(checkout+AddTOcartUrl);
    }


    private void homesetting() {
        class dbManager extends AsyncTask<String, Void, String>
        {
            protected void onPostExecute(String data)
            {
                try {
                    JSONObject jObj = new JSONObject(data);
                    business_category = jObj.getString("business_category");
                    symbol = jObj.getString("symbol");
                    RestaurantType.setText(business_category+" Charges");
                }
                catch (Exception ex)
                {
                  //  Toast.makeText(CartView.this,user_id,Toast.LENGTH_LONG).show();
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


    public final boolean isInternetOn() {

        // get Connectivity Manager object to check connection
        ConnectivityManager connec =
                (ConnectivityManager)getSystemService(getBaseContext().CONNECTIVITY_SERVICE);

        // Check for network connections
        if ( connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED ) {
            return true;
        } else if (
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.DISCONNECTED ||
                        connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.DISCONNECTED  ) {
            Intent intent = new Intent(CartView.this,IntenrNetconnection.class);
            startActivity(intent);
            Toast.makeText(this, " Not Connected ", Toast.LENGTH_LONG).show();
            return false;
        }
        return false;
    }


    @Override
    public void onBackPressed() {
 Intent intent = new Intent(CartView.this,Homepage.class);
        startActivity(intent);

    }
}