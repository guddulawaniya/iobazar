package com.example.statozone;

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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.facebook.shimmer.ShimmerFrameLayout;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class Categoryshop extends AppCompatActivity {
    private final static String generalSetting = Config.Base_url+"zeneral_setting.php";
    private final static String storeurl = Config.Base_url+"CategoryStore.php";
    private ImageView near, alert, explore, cart, account,Logohome;
    private TextView addressh;
    private TextView btn1,btn2,businessType;
    private LinearLayout sechTxt,addressLink;
    private ListView  storelist;
    private  SharedPreferences myPrefs;
    private static String storname[];
    private static String storeimage[];
    private static String storeaddress[];
    private static String storeid[];
    private static String storeUser_id[];
    private static String storeRating[];
    private static String twoorder[];
    private static String aproxtime[];
    private ArrayList<String> holder = new ArrayList<>();
    //skeleton loader
    private ShimmerFrameLayout categorySkeleton;
    //validation
    private TextView noItem;
    //General detail
    String business_category,id,logo;
    // delivery type value
    String deliverytype="1";
    int rowcnt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categoryshop);

        near = (ImageView)findViewById(R.id.near);
        alert = (ImageView)findViewById(R.id.bell);
        explore = (ImageView)findViewById(R.id.explore);
        cart = (ImageView)findViewById(R.id.cart);
        account = (ImageView)findViewById(R.id.profile);
        sechTxt = (LinearLayout)findViewById(R.id.srchlnr);
        addressLink = (LinearLayout) findViewById(R.id.adrLink);
        addressh = (TextView) findViewById(R.id.address);
        storelist = (ListView)findViewById(R.id.storelist);
        btn1 = (TextView) findViewById(R.id.btn1);
        btn2 = (TextView)findViewById(R.id.btn2);
        businessType = (TextView)findViewById(R.id.businessType);
        Logohome =(ImageView) findViewById(R.id.HomeLogo);
        noItem = (TextView) findViewById(R.id.noItem);
        categorySkeleton = findViewById(R.id.StoreListSkeleton);
        myPrefs = getSharedPreferences("myPrefs", MODE_PRIVATE);
        String addressd = myPrefs.getString("address", "");
        addressh.setText(addressd);

        sechTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Categoryshop.this,Explore.class);
                startActivity(intent);
            }
        });

        addressLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Categoryshop.this, "Not Active", Toast.LENGTH_SHORT).show();
            }
        });
        alert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Categoryshop.this,Alert.class);
                startActivity(intent);
            }
        });
        explore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Categoryshop.this,Explore.class);
                startActivity(intent);
            }
        });
        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Categoryshop.this,CartView.class);
                startActivity(intent);
            }
        });
        account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Categoryshop.this,Account.class);
            }
        });
        storelist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String s = storelist.getItemAtPosition(position).toString();

                Intent intent = new Intent(Categoryshop.this,Storeproduct.class);
                intent.putExtra("storeUser_id", storeUser_id[position]);
                intent.putExtra("storeId", storeid[position]);
                startActivity(intent);
            }
        });

        //delivery type button
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn2.setTextColor( Color.BLACK );
                btn1.setTextColor( Color.WHITE );
                btn1.setBackgroundResource(R.drawable.radious_button_dl);
                btn2.setBackgroundResource(R.drawable.radious_button_slf);
                deliverytype = "1";
                storedata(storelist, deliverytype);
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                deliverytype = "2";
                storedata(storelist, deliverytype);
                btn1.setTextColor( Color.BLACK );
                btn2.setTextColor( Color.WHITE );
                btn2.setBackgroundResource(R.drawable.radious_button_dl);
                btn1.setBackgroundResource(R.drawable.radious_button_slf);
            }
        });


        homesetting();
        storedata(storelist,deliverytype);
        isInternetOn();
    }
    private void homesetting() {
        class dbManager extends AsyncTask<String, Void, String>
        {
            protected void onPostExecute(String data)
            {

                try {
                    JSONObject jObj = new JSONObject(data);
                    id = jObj.getString("id");
                    business_category = jObj.getString("business_category");
                    logo = "https://food.tbvcsoft.com/logo/system_logo/"+jObj.getString("logo");
                    Glide.with(Categoryshop.this).load(logo).into(Logohome);

                }
                catch (Exception ex)
                {
                    Toast.makeText(Categoryshop.this,"Please check your mobile internet",Toast.LENGTH_LONG).show();
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
    private void storedata(View view ,String deliverytype) {


        Intent intent = getIntent();
        String category_id = intent.getStringExtra("category_id");
        SharedPreferences myPrefs;

        String deliveryId =deliverytype;

        myPrefs = getSharedPreferences("myPrefs", MODE_PRIVATE);

        String loactionId=myPrefs.getString("location_id", "");
        String storeIdUrl = "?cat_id="+category_id+"&city_id="+loactionId+"&dbid="+deliveryId;
        class dbManager extends AsyncTask<String, Void, String>
        {
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
                        storeaddress[i] = jo.getString("address");
                        storeRating[i] = jo.getString("rating");
                        storeUser_id[i] = jo.getString("user_id");
                        storeid[i] = jo.getString("id");
                        twoorder[i] = jo.getString("maximum");
                        aproxtime[i] = jo.getString("time");
                        storeimage[i] ="https://food.tbvcsoft.com/uploads/shop_profile/" + jo.getString("image");
                        rowcnt = i+1;
                    }
                    categorySkeleton.startShimmer();
                    categorySkeleton.setVisibility(View.GONE);
                    businessType.setText(rowcnt+" "+business_category+" Near You");
                    storeadptr adptr = new storeadptr(getApplicationContext(), storname, twoorder, aproxtime, storeaddress, storeRating, storeimage);
                    storelist.setAdapter(adptr);
                    storelist.setVisibility(View.VISIBLE);
                    noItem.setVisibility(View.GONE);
                }
                catch (Exception ex)
                {
                    categorySkeleton.startShimmer();
                    categorySkeleton.setVisibility(View.GONE);
                    storelist.setVisibility(View.GONE);
                    noItem.setVisibility(View.VISIBLE);
                    businessType.setText("No "+business_category+" Near You");
                   // Toast.makeText(Categoryshop.this,"Please check your mobile internet",Toast.LENGTH_LONG).show();
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
        obj.execute(storeurl+storeIdUrl);
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


            Glide.with(Categoryshop.this).load(url).into(img);
            return row;
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

            Intent intent = new Intent(Categoryshop.this,IntenrNetconnection.class);
            startActivity(intent);

            Toast.makeText(this, " Not Connected ", Toast.LENGTH_LONG).show();
            return false;
        }
        return false;
    }
}