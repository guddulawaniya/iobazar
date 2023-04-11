package com.example.statozone;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class My_order_list extends AppCompatActivity {
    private final static String orderlisturl = "https://zone.tbvcsoft.com/app/OrderList.php";
   private SharedPreferences myPrefs;
   private String userid;
    private ArrayList<OrderViewholder> OrderListArray = new ArrayList<OrderViewholder>();
    private OrderAdapter Adapter;
    private RecyclerView OrderList;
    private ImageView home;
   //detail order
    private  String StoreName[];
    private  String StoreLogo[];
    private  String StoreAddress[];
    private  String orderDate[];
    private  String orderTotal[];
    private  String OrderId[];
    private  String StoreId[];
    private  int orderStatus[];
    private  String Order_code[];
    private String StoreRating[];
    private String order_status_change;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order_list);
        OrderList = (RecyclerView) findViewById(R.id.OrderList);
        home = (ImageView)findViewById(R.id.home);



        //get store details
        myPrefs = getSharedPreferences("myPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = myPrefs.edit();
        userid = myPrefs.getString("user_id", "");

        isInternetOn();
        OrderList(userid);

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(My_order_list.this,Homepage.class);
                startActivity(intent);
            }
        });




        final SwipeRefreshLayout pullToRefresh = findViewById(R.id.swiperefresh);
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
            //    OrderList(userid);
                pullToRefresh.setRefreshing(false);
            }
        });
    }
    private void OrderList(String user_id) {
        String UserId = user_id;
        String orderlistvalue = "?users_Id="+UserId;
        class dbManager extends AsyncTask<String, Void, String>
        {
            ProgressDialog pdLoading = new ProgressDialog(My_order_list.this);
            protected void onPostExecute(String data)
            {
                pdLoading.dismiss();
                try {
                    JSONArray ja = new JSONArray(data);
                    JSONObject jo = null;
                    StoreName = new String[ja.length()];
                    StoreLogo = new String[ja.length()];
                    StoreAddress = new String[ja.length()];
                    orderDate = new String[ja.length()];
                    OrderId = new String[ja.length()];
                    StoreId = new String[ja.length()];
                    orderTotal = new String[ja.length()];
                    orderStatus = new int[ja.length()];
                    Order_code  = new String[ja.length()];
                    StoreRating = new String[ja.length()];
                    for( int i=0; i<ja.length(); i++)
                    {
                        jo = ja.getJSONObject(i);
                        StoreName[i] = jo.getString("storename");
                        StoreLogo[i] = "https://food.tbvcsoft.com/uploads/shop_profile/" + jo.getString("logo");
                        StoreAddress[i] = jo.getString("address");
                        orderDate[i] = jo.getString("created_at");
                        orderTotal[i] = jo.getString("grand_total");
                        OrderId[i]    = jo.getString("id");
                        orderStatus[i]    = jo.getInt("order_status");
                        Order_code[i]    = jo.getString("code");
                        StoreId[i]    = jo.getString("restaurent_id");
                        StoreRating[i] = jo.getString("StoreRating");
                        OrderListArray.add(new  OrderViewholder(StoreName[i],StoreLogo[i],StoreAddress[i],orderDate[i],orderTotal[i],OrderId[i],orderStatus[i],Order_code[i],StoreId[i],StoreRating[i]));
                    }
                    Adapter = new OrderAdapter(OrderListArray,My_order_list.this);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(My_order_list.this,LinearLayoutManager.VERTICAL,false);
                    OrderList.setHasFixedSize(true);
                    OrderList.setLayoutManager(linearLayoutManager);
                    OrderList.setAdapter(Adapter);

                }
                catch (Exception ex)
                {
                      Toast.makeText(My_order_list.this,"Your order tab is Empty",Toast.LENGTH_LONG).show();
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
        obj.execute(orderlisturl+orderlistvalue);
    }



    public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder>
    {
     private ArrayList<OrderViewholder> OrderListArray;
     private Context context;
     public OrderAdapter(ArrayList<OrderViewholder> OrderListArray, Context context)
     {
         this.OrderListArray = OrderListArray;
         this.context = context;
     }

        @NonNull
        @Override
        public OrderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
         View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ordersummry,parent,false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull OrderAdapter.ViewHolder holder, int position) {
             OrderViewholder model = OrderListArray.get(position);

             int rating = Integer.parseInt(model.getStoreRating());

            if(model.getOrderStatus() == 0)
            {
                holder.OrderStatus.setBackgroundResource(R.color.green_200);
                holder.OrderStatus.setTextColor( Color.WHITE );
                order_status_change = "Order Placed";
                holder.ratingHide.setVisibility(View.GONE);
            }
            else if(model.getOrderStatus() == 1)
            {
                holder.OrderStatus.setBackgroundResource(R.color.purple_700);
                holder.OrderStatus.setTextColor( Color.WHITE );
                order_status_change = "Order Preparing";
                holder.ratingHide.setVisibility(View.GONE);
            }
            else if(model.getOrderStatus() == 2)
            {
                holder.OrderStatus.setBackgroundResource(R.color.teal_200);
                holder.OrderStatus.setTextColor( Color.WHITE );
                order_status_change = "On Delivery";
                holder.ratingHide.setVisibility(View.GONE);
            }
            else if(model.getOrderStatus() == 3)
            {
                holder.OrderStatus.setBackgroundResource(R.color.colorPrimaryDark);
                holder.OrderStatus.setTextColor( Color.WHITE );
                order_status_change = "Delivered";
                holder.ratingHide.setVisibility(View.VISIBLE);
            }
            else if(model.getOrderStatus() == 4)
            {
                holder.OrderStatus.setBackgroundResource(R.color.purple_200);
                holder.OrderStatus.setTextColor( Color.WHITE );
                order_status_change = "Assign Boy";
                holder.ratingHide.setVisibility(View.GONE);
            }
            else if(model.getOrderStatus() == 5)
            {
                holder.OrderStatus.setBackgroundResource(R.color.purple_700);
                holder.OrderStatus.setTextColor( Color.WHITE );
                order_status_change = "Cancel Order";
                holder.ratingHide.setVisibility(View.GONE);
            }
            holder.OrderStatus.setText(order_status_change);
            holder.store_name.setText(model.getStoreName());
            holder.store_location.setText(model.getStoreAddress());
            holder.OrderDate.setText(model.getOrderDate());
            holder.totalAmount.setText(model.getOrderTotal());
            holder.order_Id.setText(model.getOrder_code());
            String url=model.getStoreLogo();
            Glide.with(My_order_list.this).load(url).into(holder.pdctImage);
       holder.itemView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               String order_id = model.getOrderId();
               Intent intent = new Intent(My_order_list.this,Order_detail.class);
               intent.putExtra("orderDetail_id", model.getOrder_code());
               intent.putExtra("storeId", model.getStoreId());
               startActivity(intent);
           }
       });
       if(rating > 0)
            {
                holder.Rated.setText("You Rated");
                holder.LStar2.setVisibility(View.GONE);
                holder.LStar3.setVisibility(View.GONE);
                holder.LStar4.setVisibility(View.GONE);
                holder.LStar5.setVisibility(View.GONE);
                holder.rateValue.setText(model.getStoreRating());
                holder.rateValue.setTextColor( Color.WHITE );
                holder.star1.setBackgroundResource(R.drawable.orderratinga);
                holder.LStar1.setBackgroundResource(R.drawable.ratingorder1);
                if(rating ==1)
                {
                    holder.LStar1.setBackgroundResource(R.drawable.ratingorder1);
                }
                else if(rating ==2)
                {
                    holder.LStar1.setBackgroundResource(R.drawable.ratingorder2);
                }
                else if(rating ==3)
                {
                    holder.LStar1.setBackgroundResource(R.drawable.ratingorder3);
                }
                else if(rating ==4)
                {
                    holder.LStar1.setBackgroundResource(R.drawable.ratingorder4);
                }
                else if(rating ==5)
                {
                    holder.LStar1.setBackgroundResource(R.drawable.ratingorder5);
                }
            }
       else
       {
           holder.ratingLayout.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   String OrderCode = model.getOrder_code();
                   String order_id = model.getOrderId();
                   Intent intent = new Intent(My_order_list.this, Ratingmanage.class);
                   intent.putExtra("orderDetail_id", model.getOrder_code());
                   intent.putExtra("orderCode", OrderCode);
                   intent.putExtra("storeId", model.getStoreId());
                   startActivity(intent);
               }
           });
       }
        }
        @Override
        public int getItemCount() {
            return OrderListArray.size();
        }
        public class ViewHolder extends RecyclerView.ViewHolder {
         private ImageView pdctImage;
         private LinearLayout ratingLayout;
         private TextView store_name,store_location,OrderDate,totalAmount,OrderStatus,order_Id;
         private LinearLayout ratingHide,LStar1,LStar2,LStar3,LStar4,LStar5;
         private TextView Rated,rateValue,star1;

            public ViewHolder(View itemView) {
                super(itemView);
                 pdctImage=itemView.findViewById(R.id.prdctImage);
                 store_name=itemView.findViewById(R.id.store_name);
                 store_location=itemView.findViewById(R.id.store_location);
                 OrderDate = itemView.findViewById(R.id.OrderId);
                 totalAmount = itemView.findViewById(R.id.totalAmount);
                 OrderStatus = itemView.findViewById(R.id.orderStatus);
                 order_Id = itemView.findViewById(R.id.orderId);
                 ratingHide = itemView.findViewById(R.id.ratingHide);
                 ratingLayout = itemView.findViewById(R.id.ratingLayout);
                 Rated = itemView.findViewById(R.id.Rated);
                 LStar1 = itemView.findViewById(R.id.LStar1);
                 LStar2 = itemView.findViewById(R.id.LStar2);
                 LStar3 = itemView.findViewById(R.id.LStar3);
                 LStar4 = itemView.findViewById(R.id.LStar4);
                 LStar5 = itemView.findViewById(R.id.LStar5);
                 rateValue = itemView.findViewById(R.id.rateValue);
                 star1 = itemView.findViewById(R.id.star1);
            }
        }
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
            // if connected with internet
          //  Toast.makeText(this, " Connected ", Toast.LENGTH_LONG).show();
            return true;
        } else if (
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.DISCONNECTED ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.DISCONNECTED  ) {
            Intent intent = new Intent(My_order_list.this,IntenrNetconnection.class);
            startActivity(intent);
            Toast.makeText(this, " Not Connected ", Toast.LENGTH_LONG).show();
            return false;
        }
        return false;
    }
}