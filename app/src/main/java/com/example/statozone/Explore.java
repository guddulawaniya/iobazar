package com.example.statozone;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.text.HtmlCompat;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class Explore extends AppCompatActivity {
    private final static String URL = "https://zone.tbvcsoft.com/app/SearchProductList.php";
    private ImageView near, alert, explore, cart, account;
    private TextView t1,t2,t3,t4,t5;
    private EditText searchEditText;
    private TextView character,SearchNotFound;
    private LinearLayout llContainer;
    private EditText etSearch;
    private ListView lvProducts;
    private static String prName[];
    private static String ids[];
    private static String prImage[];
    private static int prPrice[];
    private static String User_id[];
    private static int discount[];
    private LinearLayout back;
    private ArrayList<Productlist> mProductArrayList = new ArrayList<Productlist>();
    private MyAdapter adapter1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore);

        near = (ImageView)findViewById(R.id.near);
        back = (LinearLayout) findViewById(R.id.adrLink);

        alert = (ImageView)findViewById(R.id.bell);
        explore = (ImageView)findViewById(R.id.explore);
        cart = (ImageView)findViewById(R.id.cart);
        account = (ImageView)findViewById(R.id.profile);
        t1= (TextView)findViewById(R.id.t1);
        t2= (TextView)findViewById(R.id.t2);
        t3= (TextView)findViewById(R.id.t3);
        t4= (TextView)findViewById(R.id.t4);
        t5= (TextView)findViewById(R.id.t5);




        searchEditText = (EditText)findViewById(R.id.searchEditText);
        character = (TextView)findViewById(R.id.character);
        SearchNotFound= (TextView)findViewById(R.id.SearchNotFound);
        character.setVisibility(View.INVISIBLE);
        SearchNotFound.setVisibility(View.INVISIBLE);

        near.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Explore.this,Homepage.class);
                startActivity(intent);
            }
        });

        t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Explore.this,Homepage.class);
                startActivity(intent);
            }
        });

        alert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Explore.this,categories.class);
                startActivity(intent);
            }
        });
        t2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Explore.this,categories.class);
                startActivity(intent);
            }
        });
        explore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Explore.this,Explore.class);
                startActivity(intent);
            }
        });

        t3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Explore.this,Explore.class);
                startActivity(intent);
            }
        });


        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Explore.this,CartView.class);
                startActivity(intent);
            }
        });
        t4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Explore.this,CartView.class);
                startActivity(intent);
            }
        });

        account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Explore.this,Account.class);
                startActivity(intent);
            }
        });

        t5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Explore.this,Account.class);
                startActivity(intent);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });



        isInternetOn();

        lvProducts = (ListView)findViewById(R.id.lvProducts);



        // Add Text Change Listener to EditText
        searchEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Call back the Adapter with current character to Filter
                final String Searchkey = s.toString();
                adapter1.getFilter().filter(s.toString());
                lvProducts.setAdapter(adapter1);

                if (Searchkey.length() > 2) {
                    lvProducts.setVisibility(View.VISIBLE);
                    if (mProductArrayList.contains(s.toString())) {
                        adapter1.getFilter().filter(s.toString());
                        lvProducts.setAdapter(adapter1);
                        character.setVisibility(View.VISIBLE);

                       // Toast.makeText(Explore.this, "a", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        SearchNotFound.setVisibility(View.GONE);
                        character.setVisibility(View.GONE);
                       // Toast.makeText(Explore.this, "b", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    SearchNotFound.setVisibility(View.INVISIBLE);
                    lvProducts.setVisibility(View.INVISIBLE);
                    character.setVisibility(View.VISIBLE);
                   // Toast.makeText(Explore.this, "c", Toast.LENGTH_SHORT).show();
                    // Toast.makeText(Explore.this, "enter 3 Charcter", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,int after) {
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        new dbclass().execute(URL);
    }
    class dbclass extends AsyncTask<String ,Void, String>
    {
        protected void onPostExecute(String data)
        {
            try {
                JSONArray ja = new JSONArray(data);
                JSONObject jo = null;
                ids = new String[ja.length()];
                prName = new String[ja.length()];
                prImage = new String[ja.length()];
                prPrice = new int[ja.length()];
                User_id = new String[ja.length()];
                discount = new int[ja.length()];
                for (int i = 0; i < ja.length(); i++) {
                    jo = ja.getJSONObject(i);
                    ids[i] = jo.getString("id");
                    prName[i] = jo.getString("name");
                    prPrice[i] = jo.getInt("price");
                    User_id[i] = jo.getString("id");
                    discount[i] = jo.getInt("discount");
                    prImage[i] = "https://zone.tbvcsoft.com/images/thumbnail/" + jo.getString("image");
                    mProductArrayList.add(new Productlist(prName[i], prPrice[i],prImage[i],ids[i],User_id[i],discount[i]));
                }
                adapter1 = new MyAdapter(Explore.this, mProductArrayList);
            }
            catch (Exception ex)
            {
                Toast.makeText(Explore.this,"Please check your mobile internet",Toast.LENGTH_LONG).show();
            }
        }
        @Override
        protected String doInBackground(String... param) {
            try {
                java.net.URL url =  new URL(param[0]);
                HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                BufferedReader br  = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                return br.readLine();
            }catch (Exception ex)
            {
                return ex.getMessage();
            }
        }
    }
    // Adapter Class
    public class MyAdapter extends BaseAdapter implements Filterable {
        private ArrayList<Productlist> mOriginalValues; // Original Values
        private ArrayList<Productlist> mDisplayedValues;    // Values to be displayed
        LayoutInflater inflater;
        public MyAdapter(Context context, ArrayList<Productlist> mProductArrayList) {
            this.mOriginalValues = mProductArrayList;
            this.mDisplayedValues = mProductArrayList;
            inflater = LayoutInflater.from(context);
        }
        @Override
        public int getCount() {
            return mDisplayedValues.size();
        }
        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        class ViewHolder {
            LinearLayout llContainer;
            TextView tvName,DelPrice,UnitPrice;
            ImageView listImage;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            ViewHolder holder = null;

            if (convertView == null) {
                holder = new ViewHolder();
                convertView = inflater.inflate(R.layout.searchlist, null);
                holder.llContainer = (LinearLayout)convertView.findViewById(R.id.llContainer);
                holder.tvName = (TextView) convertView.findViewById(R.id.prdctName);
                holder.DelPrice = (TextView) convertView.findViewById(R.id.prdctPrice);
                holder.UnitPrice = (TextView) convertView.findViewById(R.id.UnitPrice);
                holder.listImage = (ImageView) convertView.findViewById(R.id.prdctImage);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.tvName.setText(mDisplayedValues.get(position).name);
            int TotalPrice = mDisplayedValues.get(position).price - (mDisplayedValues.get(position).price*mDisplayedValues.get(position).discount)/100;
            String DelPricedes = " ₹"+Math.round(Float.parseFloat(mDisplayedValues.get(position).price+""))+" ";
            String UnitPricemain = "₹"+Math.round(Float.parseFloat(mDisplayedValues.get(position).discount+""))+"";
            holder.DelPrice.setText(HtmlCompat.fromHtml(DelPricedes, HtmlCompat.FROM_HTML_MODE_LEGACY));
            holder.UnitPrice.setText(HtmlCompat.fromHtml(UnitPricemain, HtmlCompat.FROM_HTML_MODE_LEGACY));
            Glide.with(Explore.this).load(mDisplayedValues.get(position).image).into(holder.listImage);
            holder.llContainer.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {

                    SharedPreferences myPrefs = getSharedPreferences("myPrefs", MODE_PRIVATE);
                    SharedPreferences.Editor prefsEditor;
                    prefsEditor = myPrefs.edit();
                    prefsEditor.putString("product_id", mDisplayedValues.get(position).user_id);
                    prefsEditor.putString("storeUser_id", mDisplayedValues.get(position).ID);
                    prefsEditor.commit();
                   // Toast.makeText(Explore.this, mDisplayedValues.get(position).ID, Toast.LENGTH_SHORT).show();
                  Intent intent= new Intent(Explore.this, ProductView.class);
                  intent.putExtra("product_id",String.valueOf(mDisplayedValues.get(position).ID)  );

                     startActivity(intent);
                }
            });

            return convertView;
        }

        @Override
        public Filter getFilter() {
            Filter filter = new Filter() {

                @SuppressWarnings("unchecked")
                @Override
                protected void publishResults(CharSequence constraint,FilterResults results) {

                    mDisplayedValues = (ArrayList<Productlist>) results.values; // has the filtered values
                    notifyDataSetChanged();  // notifies the data with new filtered values
                }

                @Override
                protected FilterResults performFiltering(CharSequence constraint) {
                    FilterResults results = new FilterResults();        // Holds the results of a filtering operation in values
                    ArrayList<Productlist> FilteredArrList = new ArrayList<Productlist>();

                    if (mOriginalValues == null) {
                        mOriginalValues = new ArrayList<Productlist>(mDisplayedValues); // saves the original data in mOriginalValues
                    }

                    /********
                     *
                     *  If constraint(CharSequence that is received) is null returns the mOriginalValues(Original) values
                     *  else does the Filtering and returns FilteredArrList(Filtered)
                     *
                     ********/
                    if (constraint == null || constraint.length() == 0) {

                        // set the Original result to return
                        results.count = mOriginalValues.size();
                        results.values = mOriginalValues;

                    } else {
                        constraint = constraint.toString().toLowerCase();

                        for (int i = 0; i < mOriginalValues.size(); i++) {
                            String data = mOriginalValues.get(i).name;
                            if (data.toLowerCase().contains(constraint.toString())) {
                                FilteredArrList.add(new Productlist(mOriginalValues.get(i).name,mOriginalValues.get(i).price,mOriginalValues.get(i).image,mOriginalValues.get(i).user_id,mOriginalValues.get(i).ID,mOriginalValues.get(i).discount));
                            }
                        }
                        // set the Filtered result to return
                        results.count = FilteredArrList.size();
                        results.values = FilteredArrList;

                    }
                    return results;
                }
            };
            return filter;
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


            ///       Toast.makeText(this, " Connected ", Toast.LENGTH_LONG).show();

            return true;

        } else if (
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.DISCONNECTED ||
                        connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.DISCONNECTED  ) {

            Intent intent = new Intent(Explore.this,IntenrNetconnection.class);
            startActivity(intent);

            Toast.makeText(this, " Not Connected ", Toast.LENGTH_LONG).show();
            return false;
        }
        return false;
    }
}