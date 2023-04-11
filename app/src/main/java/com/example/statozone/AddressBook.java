package com.example.statozone;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
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

public class AddressBook extends AppCompatActivity {
    private static final String MapAddressUrl= Config.Base_url+"MapAddressDelete.php";
    private static final String SaveAddressUrl= Config.Base_url+"GetAddressBook.php";
    private LinearLayout Address,Edit,ForWord,AddressBookImage;
    private RecyclerView AddressList;
    private ImageView back,home;
    private TextView AddressLink;
    private int id[];
    private String Type[];
    private String CompleteAddress[];
    private String Floor[];
    private String NearBy[];
    private String Lat[];
    private String Langs[];
    private String SubLocality[];
    private SaveListAddress adapter;
    private SharedPreferences myPrefs;
    private String users_id;
    private ArrayList<AddressBookListGet> SaveAddressList = new ArrayList<AddressBookListGet>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_book);
        Address=findViewById(R.id.Address);
        AddressList = findViewById(R.id.AddressList);
        AddressLink = findViewById(R.id.AddressLink);
        home = (ImageView)findViewById(R.id.home);
        back = (ImageView)findViewById(R.id.lefttbn);

        AddressBookImage = findViewById(R.id.AddressBookImage);

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddressBook.this,Homepage.class);
                startActivity(intent);
            }
        });


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });




        Address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddressBook.this,addresspage.class);
                startActivity(intent);
            }
        });

        AddressLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddressBook.this,addresspage.class);
                startActivity(intent);
            }
        });
        SaveAddressList();
    }

    private void SaveAddressList()
    {
        myPrefs = getSharedPreferences("myPrefs", MODE_PRIVATE);
        users_id = myPrefs.getString("user_id", "");
        String addcrturl = "?Users_id="+users_id;
        class dbManager extends AsyncTask<String, Void, String>
        {
            protected void onPostExecute(String data)
            {
                try {
                    JSONArray ja = new JSONArray(data);
                    JSONObject jo = null;
                    id =  new int[ja.length()];
                    Type =  new String[ja.length()];
                    CompleteAddress =  new String[ja.length()];
                    Floor  = new String[ja.length()];
                    NearBy  = new String[ja.length()];
                    Lat  = new String[ja.length()];
                    Langs  = new String[ja.length()];
                    SubLocality  = new String[ja.length()];
                    for( int i=0; i< ja.length(); i++)
                    {
                        jo = ja.getJSONObject(i);
                        id[i] = jo.getInt("id");
                        Type[i] = jo.getString("Type");
                        CompleteAddress[i] = jo.getString("CompleteAddress");
                        Floor[i] = jo.getString("Floor");
                        NearBy[i] = jo.getString("NearBy");
                        Lat[i] = jo.getString("Lat");
                        Langs[i] = jo.getString("Langs");
                        SubLocality[i] = jo.getString("CurrentLocation");
                        SaveAddressList.add(new AddressBookListGet(id[i],Type[i],CompleteAddress[i],Floor[i],NearBy[i],Lat[i],Langs[i],SubLocality[i]));
                    }
                   adapter = new SaveListAddress(SaveAddressList, AddressBook.this);
                    LinearLayoutManager horizontalLayoutManagaer
                            = new LinearLayoutManager(AddressBook.this, LinearLayoutManager.VERTICAL, false);
                    AddressBookImage.setVisibility(View.GONE);
                    AddressList.setVisibility(View.VISIBLE);
                    AddressList.setHasFixedSize(true);
                    AddressList.setLayoutManager(horizontalLayoutManagaer);
                    AddressList.setAdapter(adapter);
                }
                catch (Exception ex)
                {
                    AddressBookImage.setVisibility(View.VISIBLE);
                    AddressList.setVisibility(View.GONE);
                  //  Toast.makeText(AddressBook.this,"Please check your mobile internet",Toast.LENGTH_LONG).show();
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
        obj.execute(SaveAddressUrl+addcrturl);
    }
    public class SaveListAddress extends RecyclerView.Adapter<SaveListAddress.ViewHolder> {
        private ArrayList<AddressBookListGet> SaveAddressList;    // Values to be displayed
        private Context context;

        public SaveListAddress(ArrayList<AddressBookListGet> SaveAddressList, Context context) {
            this.SaveAddressList = SaveAddressList;
            this.context = context;
        }
        @NonNull
        @Override
        public SaveListAddress.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            // below line is to inflate our layout.
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.addressbooklist, parent, false);
            return new ViewHolder(view);
        }
        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            // setting data to our views of recycler view.
            AddressBookListGet modal = SaveAddressList.get(position);
            holder.home_type.setText(modal.getType());
            holder.completeAddress.setText(modal.getCompleteAddress()+","+modal.getFloor()+" Floor, "+modal.getSubLocality());
           
            holder.EditAddress.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PopupMenu popup = new PopupMenu(AddressBook.this, v);
                    popup.getMenuInflater().inflate(R.menu.pop_up, popup.getMenu());

                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

                        public boolean onMenuItemClick(MenuItem item) {

                            switch (item.getItemId()) {
                                case R.id.album_overflow_rename:
                                   /// Toast.makeText(AddressBook.this, "count cleared1", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(AddressBook.this,EditAddressBook.class);
                                    intent.putExtra("lat", modal.getLat());
                                    intent.putExtra("lang", modal.getLangs());
                                    intent.putExtra("addressType",modal.getType());
                                    intent.putExtra("Address",modal.getCompleteAddress());
                                    intent.putExtra("floor",modal.getFloor());
                                    intent.putExtra("landMark",modal.getNearBy());
                                    intent.putExtra("id",modal.getId());
                                    startActivity(intent);
                                    return true;

                                case R.id.album_overflow_lock:
                                    // TODO update alert menu icon
                                 DeleteAddress(modal.getId());

                                    return true;
                            }
                            return true;

                        }
                    });
                    popup.show();//showing popup menu
                }
            });
        }
        @Override
        public int getItemCount() {
            // returning the size of array list.
            return SaveAddressList.size();
        }
        public class ViewHolder extends RecyclerView.ViewHolder {
            // creating variables for our views.
            private TextView home_type,completeAddress;
            private ImageView EditAddress;
            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                // initializing our views with their ids.
                EditAddress = itemView.findViewById(R.id.EditAddress);
                home_type = itemView.findViewById(R.id.home_type);
                completeAddress = itemView.findViewById(R.id.completeAddress);
            }
        }
    }
    private void DeleteAddress(int id) {



            int AddressId = id;


            String addressUrl = "?id="+AddressId;
            class dbclass extends AsyncTask<String ,Void, String>
            {
                protected void onPostExecute(String data)
                {
                    try {

                        finish();
                        startActivity(getIntent());
                    //    Toast.makeText(AddressBook.this, data, Toast.LENGTH_LONG).show();
                    }
                    catch (Exception ex)
                    {
                        Toast.makeText(AddressBook.this, "Invalid id and password", Toast.LENGTH_LONG).show();
                    }
                }
                @Override
                protected String doInBackground(String... param) {

                    try {
                        URL url =  new URL(param[0]);
                        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                        BufferedReader br  = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                        return br.readLine();
                    }catch (Exception ex)
                    {
                        return ex.getMessage();
                    }

                }
            }
            dbclass obj= new dbclass();
            obj.execute(MapAddressUrl+addressUrl);
        }



}