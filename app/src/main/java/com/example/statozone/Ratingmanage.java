package com.example.statozone;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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

public class Ratingmanage extends AppCompatActivity {
    private final static String FeedbackUrlStore = "https://food.tbvcsoft.com/app/FeedbackStore.php";
    private final static String FeedbackUrlProduct = "https://food.tbvcsoft.com/app/FeedbackProduct.php";
    private final static String storeDetailUrl = "https://food.tbvcsoft.com/app/StoreCharges.php";
    private final static String orderDetail_url = "https://food.tbvcsoft.com/app/order_detail.php";
    private TextView StoreName,RateResult,text1,text2,text3,text4,text5;
    private ImageView Clear;
    private LinearLayout rating1,rating2,rating3,rating4,rating5;
    private TextView star1,star2,star3,star4,star5,ratingText;
    private RelativeLayout footer;
    private SharedPreferences myPrefs;
    private String storeName[];
    private String store_Id[];
    private int RatingValue;
    private String userid;
    private RecyclerView courseRV;;
    private String prName[];
    private String pricepr[];
    private String Qtypr[];
    private String PrId[];
    String store_id,OrderCode;
    private CourseAdapter adapter;
    private ArrayList<Order_detail_get> orderListDetail = new ArrayList<Order_detail_get>();
    private ArrayList<Customer> items = new ArrayList<Customer>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ratingmanage);

        courseRV = (RecyclerView) findViewById(R.id.idRVCourses);
        StoreName = (TextView)findViewById(R.id.StoreName);
        rating1 = (LinearLayout)findViewById(R.id.rating1);
        rating2 = (LinearLayout)findViewById(R.id.rating2);
        rating3 = (LinearLayout)findViewById(R.id.rating3);
        rating4 = (LinearLayout)findViewById(R.id.rating4);
        rating5 = (LinearLayout)findViewById(R.id.rating5);
        Clear = (ImageView) findViewById(R.id.Clear);

        RateResult = findViewById(R.id.RateResult);
        text1 = (TextView) findViewById(R.id.text1);
        text2 = (TextView) findViewById(R.id.text2);
        text3 = (TextView) findViewById(R.id.text3);
        text4 = (TextView) findViewById(R.id.text4);
        text5 = (TextView) findViewById(R.id.text5);
        star1 = (TextView) findViewById(R.id.star1);
        star2 = (TextView) findViewById(R.id.star2);
        star3 = (TextView) findViewById(R.id.star3);
        star4 = (TextView) findViewById(R.id.star4);
        star5 = (TextView) findViewById(R.id.star5);

        footer = (RelativeLayout) findViewById(R.id.footer);
        ratingText = findViewById(R.id.ratingText);
        myPrefs = getSharedPreferences("myPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = myPrefs.edit();
        userid = myPrefs.getString("user_id", "");

        Intent intent = getIntent();
        String OrderId = intent.getStringExtra("orderDetail_id");
        OrderCode = intent.getStringExtra("orderCode");
        RatingValue = intent.getIntExtra("ratingValue",0);
         store_id = intent.getStringExtra("storeId");

        if(RatingValue == 1)
        {
            rating1.setBackgroundResource(R.drawable.ratingorder1);
            star1.setBackgroundResource(R.drawable.orderratinga);
            text1.setTextColor(Color.WHITE);
            RateResult.setText("Horrible");
        }
        else if (RatingValue == 2)
        {
            rating1.setBackgroundResource(R.drawable.ratingorder2);
            rating2.setBackgroundResource(R.drawable.ratingorder2);
            star1.setBackgroundResource(R.drawable.orderratinga);
            star2.setBackgroundResource(R.drawable.orderratinga);
            text1.setTextColor(Color.WHITE);
            text2.setTextColor(Color.WHITE);
            RateResult.setText("Bad");
        }
        else if (RatingValue == 3)
        {
            rating1.setBackgroundResource(R.drawable.ratingorder3);
            rating2.setBackgroundResource(R.drawable.ratingorder3);
            rating3.setBackgroundResource(R.drawable.ratingorder3);
            star1.setBackgroundResource(R.drawable.orderratinga);
            star2.setBackgroundResource(R.drawable.orderratinga);
            star3.setBackgroundResource(R.drawable.orderratinga);
            text1.setTextColor(Color.WHITE);
            text2.setTextColor(Color.WHITE);
            text3.setTextColor(Color.WHITE);
            RateResult.setText("Average");
        }
        else if (RatingValue == 4)
        {
            rating1.setBackgroundResource(R.drawable.ratingorder4);
            rating2.setBackgroundResource(R.drawable.ratingorder4);
            rating3.setBackgroundResource(R.drawable.ratingorder4);
            rating4.setBackgroundResource(R.drawable.ratingorder4);
            star1.setBackgroundResource(R.drawable.orderratinga);
            star2.setBackgroundResource(R.drawable.orderratinga);
            star3.setBackgroundResource(R.drawable.orderratinga);
            star4.setBackgroundResource(R.drawable.orderratinga);
            text1.setTextColor(Color.WHITE);
            text2.setTextColor(Color.WHITE);
            text3.setTextColor(Color.WHITE);
            text4.setTextColor(Color.WHITE);
            RateResult.setText("Good");
        }
        else if (RatingValue == 5)
        {
            rating1.setBackgroundResource(R.drawable.ratingorder5);
            rating2.setBackgroundResource(R.drawable.ratingorder5);
            rating3.setBackgroundResource(R.drawable.ratingorder5);
            rating4.setBackgroundResource(R.drawable.ratingorder5);
            rating5.setBackgroundResource(R.drawable.ratingorder5);
            star1.setBackgroundResource(R.drawable.orderratinga);
            star2.setBackgroundResource(R.drawable.orderratinga);
            star3.setBackgroundResource(R.drawable.orderratinga);
            star4.setBackgroundResource(R.drawable.orderratinga);
            star5.setBackgroundResource(R.drawable.orderratinga);
            text1.setTextColor(Color.WHITE);
            text2.setTextColor(Color.WHITE);
            text3.setTextColor(Color.WHITE);
            text4.setTextColor(Color.WHITE);
            text5.setTextColor(Color.WHITE);
            RateResult.setText("Excellent");
        }

        rating1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = getIntent();
                intent.putExtra("ratingValue",1);
                finish();
                startActivity(intent);
            }
        });
        rating2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = getIntent();
                intent.putExtra("ratingValue",2);
                finish();
                startActivity(intent);
            }
        });
        rating3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = getIntent();
                intent.putExtra("ratingValue",3);
                finish();
                startActivity(intent);
                Toast.makeText(Ratingmanage.this,"3",Toast.LENGTH_LONG).show();
            }
        });
        rating4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = getIntent();
                intent.putExtra("ratingValue",4);
                finish();
                startActivity(intent);
            }
        });
        rating5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = getIntent();
                intent.putExtra("ratingValue",5);
                finish();
                startActivity(intent);
            }
        });
        Clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Ratingmanage.this,My_order_list.class);
                startActivity(intent);
            }
        });
        storedetail(store_id);
        OrderList(OrderId);
    }
    private void storedetail(String store_id)
    {
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
                    store_Id = new String[ja.length()];
                    for (int i = 0; i < ja.length(); i++) {
                        jo = ja.getJSONObject(i);
                        storeName[i] = jo.getString("name");
                        store_Id[i] = jo.getString("id");
                        StoreName.setText(storeName[i]);
                    }
                }
                catch (Exception ex)
                {
                    Toast.makeText(Ratingmanage.this,"Please check your mobile internet",Toast.LENGTH_LONG).show();
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
        obj.execute(storeDetailUrl+storeIdUrl);
    }
    private void feedbackStore(String store_id, String userid, String orderCode, int ratingValue)
    {
        String storeId =store_id;
        String UserId = userid;
        String OrderId = orderCode;
        int RatingValue = ratingValue;
        String FeedbackValue = "?StoreId="+storeId+"&UserId="+UserId+"&OrderCode="+OrderId+"&Rating="+RatingValue;
        class dbManager extends AsyncTask<String, Void, String>
        {
            protected void onPostExecute(String data)
            {
                try {

                    Intent intent = new Intent(Ratingmanage.this,Feedback.class);
                    startActivity(intent);

                //     Toast.makeText(Ratingmanage.this,data,Toast.LENGTH_LONG).show();
                }
                catch (Exception ex)
                {
                    Toast.makeText(Ratingmanage.this,"Something went wrong",Toast.LENGTH_LONG).show();
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
        obj.execute(FeedbackUrlStore+FeedbackValue);
    }
    private void feedbackProduct(String Productid, String userid, String orderCode, int ratingValue)
    {
        String ProductId =Productid;
        String UserId = userid;
        String OrderId = orderCode;
        int RatingValue = ratingValue;
        String FeedbackValue = "?ItemId="+ProductId+"&UserId="+UserId+"&OrderCode="+OrderId+"&Rating="+RatingValue;
        class dbManager extends AsyncTask<String, Void, String>
        {
            protected void onPostExecute(String data)
            {
                try {
                 //   Toast.makeText(Ratingmanage.this,data,Toast.LENGTH_LONG).show();
                }
                catch (Exception ex)
                {
                    Toast.makeText(Ratingmanage.this,"Something went wrong",Toast.LENGTH_LONG).show();
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
        obj.execute(FeedbackUrlProduct+FeedbackValue);
    }
    private void OrderList(String user_id)
    {
        String UserId = user_id;
        String orderlistvalue = "?order_Id="+UserId;
        class dbManager extends AsyncTask<String, Void, String>
        {
            protected void onPostExecute(String data)
            {
                try {
                    JSONArray ja = new JSONArray(data);
                    JSONObject jo = null;
                    prName =  new String[ja.length()];
                    pricepr =  new String[ja.length()];
                    Qtypr =  new String[ja.length()];
                    PrId = new String[ja.length()];
                    for( int i=0; i< ja.length(); i++)
                    {
                        jo = ja.getJSONObject(i);
                        prName[i] = jo.getString("name");
                        pricepr[i] = jo.getString("price");
                        Qtypr[i] = jo.getString("quantity");
                        PrId[i]= jo.getString("id");
                        orderListDetail.add(new Order_detail_get(prName[i],pricepr[i],Qtypr[i],PrId[i]));
                    }
                    adapter = new CourseAdapter(orderListDetail, Ratingmanage.this);
                    LinearLayoutManager horizontalLayoutManagaer
                            = new LinearLayoutManager(Ratingmanage.this, LinearLayoutManager.VERTICAL, false);
                    courseRV.setHasFixedSize(true);
                    courseRV.setLayoutManager(horizontalLayoutManagaer);
                    courseRV.setAdapter(adapter);
                }
                catch (Exception ex)
                {
                  //  Toast.makeText(Order_detail.this,"Please check your mobile internet",Toast.LENGTH_LONG).show();
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
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ratingproductlist, parent, false);
            return new ViewHolder(view);
        }
        @Override
        public void onBindViewHolder(@NonNull CourseAdapter.ViewHolder holder, int position) {
            // setting data to our views of recycler view.
            int count=0;
            int value=0;
            Order_detail_get modal = orderDetailGets.get(position);

            final String[] ProductRating = {"0"};

            Customer customer = new Customer(ProductRating[0],modal.getPrId());


            items.add(customer);


            for( int i = 0; i <= items.size()-1; i++)
            {
                count++;
                value = count-1;
               // Toast.makeText(Ratingmanage.this,"Value="+ items.get(i), Toast.LENGTH_LONG).show();
                // get element number 0 and 1 and put it in a variable,
                // and the next time get element      1 and 2 and put this in another variable.
            }

            //  feedbackStore(store_id,userid,OrderCode,RatingValue);

            int finalValue = value;

            int index = items.indexOf(ProductRating[0]);

            holder.ProductName.setText(modal.getprName()+finalValue);

            if(RatingValue > 0)
            {
                holder.ratingHide.setVisibility(View.VISIBLE);
            }
            else
            {
                holder.ratingHide.setVisibility(View.GONE);
            }


            holder.rating1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    holder.rating1.setBackgroundResource(R.drawable.ratingorder3);
                    holder.rating2.setBackgroundResource(R.drawable.border_order);
                    holder.rating3.setBackgroundResource(R.drawable.border_order);
                    holder.rating4.setBackgroundResource(R.drawable.border_order);
                    holder.rating5.setBackgroundResource(R.drawable.border_order);
                    holder.star1.setBackgroundResource(R.drawable.orderratinga);
                    holder.star2.setBackgroundResource(R.drawable.order_rating);
                    holder.star3.setBackgroundResource(R.drawable.order_rating);
                    holder.star4.setBackgroundResource(R.drawable.order_rating);
                    holder.star5.setBackgroundResource(R.drawable.order_rating);
                    holder.text1.setTextColor(Color.WHITE);
                    holder.text2.setTextColor(Color.BLACK);
                    holder.text3.setTextColor(Color.BLACK);
                    holder.text4.setTextColor(Color.BLACK);
                    holder.text5.setTextColor(Color.BLACK);
                    ProductRating[0] = "1";
                    Customer customer = new Customer(ProductRating[0],modal.getPrId());
                    items.set(finalValue, customer);
                //   Toast.makeText(Ratingmanage.this,"Value="+ProductRating[0], Toast.LENGTH_LONG).show();
                }
            });
            holder.rating2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    holder.rating1.setBackgroundResource(R.drawable.ratingorder3);
                    holder.rating2.setBackgroundResource(R.drawable.ratingorder3);
                    holder.rating3.setBackgroundResource(R.drawable.border_order);
                    holder.rating4.setBackgroundResource(R.drawable.border_order);
                    holder.rating5.setBackgroundResource(R.drawable.border_order);
                    holder.star1.setBackgroundResource(R.drawable.orderratinga);
                    holder.star2.setBackgroundResource(R.drawable.orderratinga);
                    holder.star3.setBackgroundResource(R.drawable.order_rating);
                    holder.star4.setBackgroundResource(R.drawable.order_rating);
                    holder.star5.setBackgroundResource(R.drawable.order_rating);
                    holder.text1.setTextColor(Color.WHITE);
                    holder.text2.setTextColor(Color.WHITE);
                    holder.text3.setTextColor(Color.BLACK);
                    holder.text4.setTextColor(Color.BLACK);
                    holder.text5.setTextColor(Color.BLACK);
                    ProductRating[0] = "2";
                    Customer customer = new Customer(ProductRating[0],modal.getPrId());
                   items.set(finalValue, customer);
                  //  Toast.makeText(Ratingmanage.this,"Value="+ProductRating[0], Toast.LENGTH_LONG).show();
                }
            });
            holder.rating3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    holder.rating1.setBackgroundResource(R.drawable.ratingorder3);
                    holder.rating2.setBackgroundResource(R.drawable.ratingorder3);
                    holder.rating3.setBackgroundResource(R.drawable.ratingorder3);
                    holder.rating4.setBackgroundResource(R.drawable.border_order);
                    holder.rating5.setBackgroundResource(R.drawable.border_order);
                    holder.star1.setBackgroundResource(R.drawable.orderratinga);
                    holder.star2.setBackgroundResource(R.drawable.orderratinga);
                    holder.star3.setBackgroundResource(R.drawable.orderratinga);
                    holder.star4.setBackgroundResource(R.drawable.order_rating);
                    holder.star5.setBackgroundResource(R.drawable.order_rating);
                    holder.text1.setTextColor(Color.WHITE);
                    holder.text2.setTextColor(Color.WHITE);
                    holder.text3.setTextColor(Color.WHITE);
                    holder.text4.setTextColor(Color.BLACK);
                    holder.text5.setTextColor(Color.BLACK);
                    ProductRating[0] = "3";
                    Customer customer = new Customer(ProductRating[0],modal.getPrId());
                    items.set(finalValue, customer);
                //    Toast.makeText(Ratingmanage.this,"Value="+ProductRating[0], Toast.LENGTH_LONG).show();
                }
            });
            holder.rating4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    holder.rating1.setBackgroundResource(R.drawable.ratingorder3);
                    holder.rating2.setBackgroundResource(R.drawable.ratingorder3);
                    holder.rating3.setBackgroundResource(R.drawable.ratingorder3);
                    holder.rating4.setBackgroundResource(R.drawable.ratingorder3);
                    holder.rating5.setBackgroundResource(R.drawable.border_order);
                    holder.star1.setBackgroundResource(R.drawable.orderratinga);
                    holder.star2.setBackgroundResource(R.drawable.orderratinga);
                    holder.star3.setBackgroundResource(R.drawable.orderratinga);
                    holder.star4.setBackgroundResource(R.drawable.orderratinga);
                    holder.star5.setBackgroundResource(R.drawable.order_rating);
                    holder.text1.setTextColor(Color.WHITE);
                    holder.text2.setTextColor(Color.WHITE);
                    holder.text3.setTextColor(Color.WHITE);
                    holder.text4.setTextColor(Color.WHITE);
                    holder.text5.setTextColor(Color.BLACK);
                    ProductRating[0] = "4";
                    Customer customer = new Customer(ProductRating[0],modal.getPrId());
                    items.set(finalValue, customer);
                //    Toast.makeText(Ratingmanage.this,"Value="+ProductRating[0], Toast.LENGTH_LONG).show();
                }
            });
            holder.rating5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    holder.rating1.setBackgroundResource(R.drawable.ratingorder3);
                    holder.rating2.setBackgroundResource(R.drawable.ratingorder3);
                    holder.rating3.setBackgroundResource(R.drawable.ratingorder3);
                    holder.rating4.setBackgroundResource(R.drawable.ratingorder3);
                    holder.rating5.setBackgroundResource(R.drawable.ratingorder3);
                    holder.star1.setBackgroundResource(R.drawable.orderratinga);
                    holder.star2.setBackgroundResource(R.drawable.orderratinga);
                    holder.star3.setBackgroundResource(R.drawable.orderratinga);
                    holder.star4.setBackgroundResource(R.drawable.orderratinga);
                    holder.star5.setBackgroundResource(R.drawable.orderratinga);
                    holder.text1.setTextColor(Color.WHITE);
                    holder.text2.setTextColor(Color.WHITE);
                    holder.text3.setTextColor(Color.WHITE);
                    holder.text4.setTextColor(Color.WHITE);
                    holder.text5.setTextColor(Color.WHITE);
                    ProductRating[0] = "5";
                    Customer customer = new Customer(ProductRating[0],modal.getPrId());
                    items.set(finalValue, customer);
                  //  Toast.makeText(Ratingmanage.this,"Value="+ProductRating[0], Toast.LENGTH_LONG).show();
                }
            });
            if(RatingValue > 0)
            {
                footer.setBackgroundResource(R.drawable.radious_button_dl);
                ratingText.setTextColor(Color.WHITE);
                RateResult.setText("Horrible");
                footer.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        for( int i = 0; i <= items.size()-1; i++)
                        {
                            Customer customer = items.get(i);

                            int ratingValueDb = Integer.parseInt(customer.ratingValue);

                            if(ratingValueDb > 0 )
                            {
                                feedbackProduct(customer.ProductId,userid,OrderCode, Integer.parseInt(customer.ratingValue));
                            //    Toast.makeText(Ratingmanage.this,"Value="+customer.ratingValue + " itemId=" + customer.ProductId, Toast.LENGTH_LONG).show();
                            }

                            // get element number 0 and 1 and put it in a variable,
                            // and the next time get element      1 and 2 and put this in another variable.
                        }
                          feedbackStore(store_id,userid,OrderCode,RatingValue);
                    }
                });

            }

        }
        @Override
        public int getItemCount() {
            // returning the size of array list.
            return orderDetailGets.size();
        }
        public class ViewHolder extends RecyclerView.ViewHolder {
            // creating variables for our views.
            private TextView text1,text2,text3,text4,text5;
            private TextView ProductName;
            private TextView star1,star2,star3,star4,star5,ratingText;
            private LinearLayout ratingHide;
            private LinearLayout rating1,rating2,rating3,rating4,rating5;
            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                // initializing our views with their ids.
                ProductName = itemView.findViewById(R.id.ProductName);
                ratingHide = itemView.findViewById(R.id.ratingHide);
                rating1 = itemView.findViewById(R.id.rating1);
                rating2 = itemView.findViewById(R.id.rating2);
                rating3 = itemView.findViewById(R.id.rating3);
                rating4 = itemView.findViewById(R.id.rating4);
                rating5 = itemView.findViewById(R.id.rating5);
                star1 = itemView.findViewById(R.id.star1);
                star2 = itemView.findViewById(R.id.star2);
                star3 = itemView.findViewById(R.id.star3);
                star4 = itemView.findViewById(R.id.star4);
                star5 = itemView.findViewById(R.id.star5);
                text1 = itemView.findViewById(R.id.text1);
                text2 = itemView.findViewById(R.id.text2);
                text3 = itemView.findViewById(R.id.text3);
                text4 = itemView.findViewById(R.id.text4);
                text5 = itemView.findViewById(R.id.text5);
            }
        }
    }
    public class Customer {
        String ratingValue;
        String  ProductId;

        public Customer(String s, String prId) {
           this.ratingValue = s;
           this.ProductId=prId;



        }
    }
}
