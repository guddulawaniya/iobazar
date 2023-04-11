package com.example.statozone;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class Order_detail extends AppCompatActivity {
    private final static String storeDetaileUrl = "https://zone.tbvcsoft.com/app/StoreCharges.php";
    private final static String oderdata = "https://zone.tbvcsoft.com/app/singleOrder.php";
    private final static String orderDetail_url = "https://zone.tbvcsoft.com/app/order_detail.php";
    private TextView ProductName,StoreName,storeAddress,orderup,totalpay_amount,totalproductprice,deliverycharges,storecharge;
    private String prName[];
    private String pricepr[];
    private String Qtypr[];
    private String PrId[];
    private RecyclerView courseRV;
    private LinearLayout SupportLink,BackButton;
    private TextView DownloadButton;


    //store detail

    private  String orderDate[];
    private  String orderTotal[];
    private  String OrderId[];
    private  String deliveryCharge[];
    private  int orderStatus[];
    private  String Order_code[];
    private  String OrderNumber[];
    private  String payment_type[];
    private  String OrderAddress[];
    private  String user;

    //Store detail
    private static TextView OrderNumberCode,paymentType,OrderDate,OrderIdNumber,addressDelivery;
    private static String store_Id[];
    private static String storeName[];
    private static String storedescription[];
    private static String storeImage[];
    private static String storeaddress[];
    private static String store_charge[];

    private CourseAdapter adapter;
    private ArrayList<Order_detail_get> orderListDetail = new ArrayList<Order_detail_get>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        courseRV = (RecyclerView) findViewById(R.id.idRVCourses);
        StoreName = (TextView) findViewById(R.id.StoreName);
        storeAddress = (TextView) findViewById(R.id.storeAddress);
        orderup = (TextView) findViewById(R.id.orderup);
        totalpay_amount = (TextView) findViewById(R.id.totalpay_amount);
        totalproductprice = (TextView) findViewById(R.id.totalproductprice);
        deliverycharges = (TextView) findViewById(R.id.deliverycharges);
        storecharge = (TextView) findViewById(R.id.storecharge);
        DownloadButton = (TextView) findViewById(R.id.DownloadButton);
        SupportLink = (LinearLayout) findViewById(R.id.SupportLink);
        OrderNumberCode = (TextView) findViewById(R.id.OrderNumber);
        paymentType = (TextView) findViewById(R.id.paymentType);
        OrderDate = (TextView) findViewById(R.id.OrderDate);
        OrderIdNumber = (TextView) findViewById(R.id.OrderIdNumber);
        addressDelivery = (TextView) findViewById(R.id.addressDelivery);
        BackButton = (LinearLayout) findViewById(R.id.BackButton);

        DownloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Order_detail.this,"Not Active",Toast.LENGTH_LONG).show();
            }
        });
        BackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               onBackPressed();
            }
        });
        SupportLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Order_detail.this,"Not Active",Toast.LENGTH_LONG).show();
            }
        });
        //get intent value
        Intent intent = getIntent();
        String str = intent.getStringExtra("orderDetail_id");
        String store_id = intent.getStringExtra("storeId");

        user =intent.getStringExtra("orderDetail_id");


        OrderList(str);
        orderdata(str);
        storedetail(store_id);
    }
    private void orderdata(String user_id)
    {
        String UserId = user_id;
        String orderlistvalue = "?order_Id="+user;
        class dbManager extends AsyncTask<String, Void, String>
        {
            protected void onPostExecute(String data)
            {
                try {
                    JSONArray ja = new JSONArray(data);
                    JSONObject jo = null;
                    orderDate = new String[ja.length()];
                    OrderId = new String[ja.length()];
                    deliveryCharge = new String[ja.length()];
                    orderTotal = new String[ja.length()];
                    orderStatus = new int[ja.length()];
                    Order_code  = new String[ja.length()];
                    OrderNumber = new String[ja.length()];
                    payment_type = new String[ja.length()];
                    OrderAddress = new String[ja.length()];
                    for( int i=0; i<ja.length(); i++)
                    {
                        jo = ja.getJSONObject(i);
                        orderDate[i] = jo.getString("created_at");
                        orderTotal[i] = jo.getString("grand_total");
                        OrderId[i]    = jo.getString("id");
                        orderStatus[i]    = jo.getInt("order_status");
                        Order_code[i]    = jo.getString("sub_total");
                        deliveryCharge[i]    = jo.getString("shipping_tax");
                        OrderNumber[i]    = jo.getString("code");
                        payment_type[i]    = jo.getString("payment_type");
                        OrderAddress[i]    = jo.getString("Address");
                        totalpay_amount.setText(orderTotal[i]);
                        totalproductprice.setText(Order_code[i]);
                        deliverycharges.setText(deliveryCharge[i]);
                        OrderNumberCode.setText(OrderNumber[i]);
                        paymentType.setText(payment_type[i]);
                        OrderDate.setText(orderDate[i]);
                        OrderIdNumber.setText("Order Id : "+ OrderNumber[i]);
                        addressDelivery.setText(OrderAddress[i]);
                    }
                }
                catch (Exception ex)
                {
                    Toast.makeText(Order_detail.this,user,Toast.LENGTH_LONG).show();
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
        obj.execute(oderdata+orderlistvalue);
    }
    private void storedetail(String store_id)
    {
        String storeId = store_id;
        String storeIdUrl = "?order_Id="+user;
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
                    storedescription = new String[ja.length()];
                    storeaddress = new String[ja.length()];
                    store_charge = new  String[ja.length()];
                    for (int i = 0; i < ja.length(); i++) {
                        jo = ja.getJSONObject(i);
                        storeName[i] = jo.getString("name");
                        store_Id[i] = jo.getString("id");
                        store_charge[i] = jo.getString("store_charge");
                        storeaddress[i] = jo.getString("address");
                        storedescription[i] = jo.getString("description");
                        storeImage[i] ="https://food.tbvcsoft.com/uploads/shop_profile/" + jo.getString("image");
                        StoreName.setText(storeName[i]);
                        storeAddress.setText(storeaddress[i]);
                        orderup.setText("This order with "+storeName[i]+"was Delivered");
                        storecharge.setText(store_charge[i]);

                        String url=storeImage[i];

                    }
                }
                catch (Exception ex)
                {
                    Toast.makeText(Order_detail.this,"Please check your mobile internet1",Toast.LENGTH_LONG).show();
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
    private void OrderList(String user_id)
    {
        String UserId = user_id;
        String orderlistvalue = "?order_Id="+user;
        class dbManager extends AsyncTask<String, Void, String>
        {
            ProgressDialog pdLoading = new ProgressDialog(Order_detail.this);
            protected void onPostExecute(String data)
            {
                pdLoading.dismiss();
                try {
                    JSONArray ja = new JSONArray(data);
                    JSONObject jo = null;
                    prName =  new String[ja.length()];
                    pricepr =  new String[ja.length()];
                    Qtypr =  new String[ja.length()];
                    PrId  = new String[ja.length()];
                    for( int i=0; i< ja.length(); i++)
                    {
                        jo = ja.getJSONObject(i);
                        prName[i] = jo.getString("name");
                        pricepr[i] = jo.getString("price");
                        Qtypr[i] = jo.getString("quantity");
                        PrId[i] = jo.getString("id");
                        orderListDetail.add(new Order_detail_get(prName[i],pricepr[i],Qtypr[i],PrId[i]));
                    }
                    // initializing our adapter class.
                    adapter = new CourseAdapter(orderListDetail, Order_detail.this);
                    LinearLayoutManager horizontalLayoutManagaer
                            = new LinearLayoutManager(Order_detail.this, LinearLayoutManager.VERTICAL, false);
                    courseRV.setHasFixedSize(true);
                    courseRV.setLayoutManager(horizontalLayoutManagaer);
                    courseRV.setAdapter(adapter);
                }
                catch (Exception ex)
                {
                    Toast.makeText(Order_detail.this,"Please check your mobile internet",Toast.LENGTH_LONG).show();
                }
            }
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                //this method will be running on UI thread
                pdLoading.setMessage("\tLoading...");
                pdLoading.setCancelable(false);
                pdLoading.show();
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
        obj.execute(orderDetail_url+orderlistvalue);
    }

        public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.ViewHolder> {
            private ArrayList<Order_detail_get> orderDetailGets;    // Values to be displayed
            private Context context;

            public CourseAdapter(ArrayList<Order_detail_get> orderDetailGets, Context context) {
                this.orderDetailGets = orderDetailGets;
                this.context = context;
            }
            @NonNull
            @Override
            public CourseAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                // below line is to inflate our layout.
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.orderdetaget, parent, false);
                return new ViewHolder(view);
            }
            @Override
            public void onBindViewHolder(@NonNull CourseAdapter.ViewHolder holder, int position) {
                // setting data to our views of recycler view.
                Order_detail_get modal = orderDetailGets.get(position);
                holder.courseNameTV.setText(modal.getprName());
                holder.ptPrice.setText("₹ "+modal.getpricepr());
                holder.Qty.setText(modal.getQtypr()+"x ");
            }
            @Override
            public int getItemCount() {
                // returning the size of array list.
                return orderDetailGets.size();
            }
            public class ViewHolder extends RecyclerView.ViewHolder {
                // creating variables for our views.
                private TextView courseNameTV,ptPrice,Qty;
                public ViewHolder(@NonNull View itemView) {
                    super(itemView);
                    // initializing our views with their ids.
                    courseNameTV = itemView.findViewById(R.id.crtcnt);
                    ptPrice = itemView.findViewById(R.id.crtcnt2);
                    Qty = itemView.findViewById(R.id.Qty);
                }
            }
        }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }
}



