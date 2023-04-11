package com.example.statozone;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Locale;

public class EditAddressBook extends AppCompatActivity implements OnMapReadyCallback {
    private static final String SaveAddressUrl= Config.Base_url+"UpdateAddressBook.php";
    private Location currentLocation,mLocation;
    private RelativeLayout currentLocation1;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private static final int REQUEST_CODE = 101;
    private GoogleMap mMap;
    private TextView textShow,textShow1;
    private List<android.location.Address> addresses;
    private String users_id;
    private ImageView clear;
    boolean opened;
    private ImageView leftBtn;
    private RadioGroup rg;
    private RadioButton rdOffice,rdHome,rdHotel,rdOther;
    private LatLng mCenterLatLong;
    private LinearLayout view;
    private Button SaveAddress;
    private int requestCode;
    private String[] permissions;
    private int[] grantResults;
    private EditText Address,Floor,Landmark;
    private String Lat,Long,Locationhere;
    private LinearLayout AddressBox;
    private ScrollView scrollView;
    private SharedPreferences myPrefs;
    private  String lat,lang,floor,nearBy,AddressComplete,TypeAddress;
    private   int  addressId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_address_book);
        textShow = (TextView) findViewById(R.id.textView2);
        textShow1 = (TextView) findViewById(R.id.textView3);
        clear = findViewById(R.id.clear);
        SaveAddress = findViewById(R.id.SaveAddress);
        Address = findViewById(R.id.Address);
        leftBtn = findViewById(R.id.lefttbn);
        rg = (RadioGroup) findViewById(R.id.radioGroup);
        currentLocation1 = findViewById(R.id.currentLocation);
        Floor = findViewById(R.id.Floor);
        Landmark = findViewById(R.id.Landmark);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        AddressBox = findViewById(R.id.AddressBox);
        rdOffice = findViewById(R.id.rdOffice);
        rdHome = findViewById(R.id.rdHome);
        rdHotel = findViewById(R.id.rdHotel);
        rdOther = findViewById(R.id.rdOther);
        view = findViewById(R.id.view);
        scrollView = findViewById(R.id.scrollView);
        view.setVisibility(View.INVISIBLE);
        //get value
        Intent intent = getIntent();

         lat = intent.getStringExtra("lat");
         lang = intent.getStringExtra("lang");
         floor = intent.getStringExtra("floor");
         AddressComplete = intent.getStringExtra("Address");
         nearBy = intent.getStringExtra("landMark");
         TypeAddress = intent.getStringExtra("addressType");
         addressId = intent.getIntExtra("id",0);
        Toast.makeText(this, "id="+addressId, Toast.LENGTH_SHORT).show();
         Floor.setText(floor);
         Landmark.setText(nearBy);
         Address.setText(AddressComplete);
    //    Toast.makeText(this, addressType, Toast.LENGTH_SHORT).show();
        String addressType = TypeAddress;

        if (addressType.equals("Home") )
         {
             rdHome.setChecked(true);
         }
     else if (addressType.equals("Office") )
        {
            rdOffice.setChecked(true);
        }
     else if (addressType.equals("Hotel") )
        {
            rdHotel.setChecked(true);
        }
     else if (addressType.equals("Other") )
        {
            rdOther.setChecked(true);
        }
        leftBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!opened){
                    view.setVisibility(View.VISIBLE);
                    AddressBox.setVisibility(View.VISIBLE);
                    scrollView.setVisibility(View.VISIBLE);
                    TranslateAnimation animate = new TranslateAnimation(
                            0,
                            0,
                            view.getHeight(),
                            0);
                    animate.setDuration(500);
                    animate.setFillAfter(true);
                    view.startAnimation(animate);
                } else {
                    view.setVisibility(View.GONE);
                    AddressBox.setVisibility(View.GONE);
                    scrollView.setVisibility(View.GONE);
                    TranslateAnimation animate = new TranslateAnimation(
                            0,
                            0,
                            0,
                            view.getHeight());
                    animate.setDuration(500);
                    animate.setFillAfter(true);
                    view.startAnimation(animate);
                }
                opened = !opened;
            }
        });
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!opened){
                    view.setVisibility(View.VISIBLE);
                    AddressBox.setVisibility(View.VISIBLE);
                    scrollView.setVisibility(View.VISIBLE);
                    TranslateAnimation animate = new TranslateAnimation(
                            0,
                            0,
                            view.getHeight(),
                            0);
                    animate.setDuration(500);
                    animate.setFillAfter(true);
                    view.startAnimation(animate);
                } else {

                    view.setVisibility(View.GONE);
                    AddressBox.setVisibility(View.GONE);
                    scrollView.setVisibility(View.GONE);
                    TranslateAnimation animate = new TranslateAnimation(
                            0,
                            0,
                            0,
                            view.getHeight());
                    animate.setDuration(500);
                    animate.setFillAfter(true);
                    view.startAnimation(animate);
                }
                opened = !opened;
            }
        });
        fetchLocation();
        currentLocation1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(getIntent());
            }
        });
        SaveAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String AddressGet = Address.getText().toString();
                final String FloorGet = Floor.getText().toString();
                final String LandmarkGet = Landmark.getText().toString();
                final String AddressType = ((RadioButton)findViewById(rg.getCheckedRadioButtonId())).getText().toString();

                SaveAddresses(AddressGet,FloorGet,LandmarkGet,AddressType,Lat,Long,Locationhere);

            }
        });
    }
    private void fetchLocation() {
        if (ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
            return;
        }
        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    currentLocation = location;

                    SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.myMap);
                    assert supportMapFragment != null;
                    supportMapFragment.getMapAsync(EditAddressBook.this);
                }
            }
        });
    }
    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        double Lat1 = Double.parseDouble(lat);
        double Lang1 = Double.parseDouble(lang);

        LatLng latLng = new LatLng(Lat1, Lang1);
        Geocoder geocoder = new Geocoder(EditAddressBook.this, Locale.getDefault());

        try {
            addresses = geocoder.getFromLocation(Lat1, Lang1,1);
            textShow1.setText(""+addresses.get(0).getSubLocality());
            textShow.setText(""+addresses.get(0).getAddressLine(0));

            Lat = String.valueOf(currentLocation.getLatitude());
            Long = String.valueOf(currentLocation.getLongitude());
            Locationhere = String.valueOf(addresses.get(0).getSubLocality());
        } catch (IOException e) {
            e.printStackTrace();
        }
        mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 18));
        mMap.setMyLocationEnabled(true);

        mMap.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(CameraPosition cameraPosition) {
                mCenterLatLong = cameraPosition.target;
                mMap.clear();
                try {
                    Location mLocation = new Location("");
                    mLocation.setLatitude(mCenterLatLong.latitude);
                    mLocation.setLongitude(mCenterLatLong.longitude);
                    Geocoder geocoder = new Geocoder(EditAddressBook.this, Locale.getDefault());
                    try {
                        addresses = geocoder.getFromLocation(mCenterLatLong.latitude, mCenterLatLong.longitude,1);

                        textShow.setText(""+addresses.get(0).getAddressLine(0));
                        textShow1.setText(""+addresses.get(0).getSubLocality());

                        Lat = String.valueOf(mCenterLatLong.latitude);
                        Long = String.valueOf(mCenterLatLong.longitude);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        mMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener(){
            @Override
            public boolean onMyLocationButtonClick()
            {
                Toast.makeText(EditAddressBook.this,"Clicked", Toast.LENGTH_SHORT).show();
                if(mMap != null){ //prevent crashing if the map doesn't exist yet (eg. on starting activity)
                    mMap.clear();
                    // add markers from database to the map
                }
                return false;
            }
        });
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        this.requestCode = requestCode;
        this.permissions = permissions;
        this.grantResults = grantResults;
        switch (requestCode) {
            case REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    fetchLocation();
                }
                break;
        }
    }

    public void SaveAddresses(String addressGet, String floorGet, String landmarkGet, String addressType, String Lat, String Long, String Locationhere)
    {
        myPrefs = getSharedPreferences("myPrefs", MODE_PRIVATE);
        users_id = myPrefs.getString("user_id", "");


        String address = addressGet;
        String floor  = floorGet;
        String landmark = landmarkGet;
        String TypeAddress = addressType;
        String LatLocation = Lat;
        String LongLocation = Long;
        String CurrentLocation = Locationhere;
        String addcrturl = "?address="+address+"&floor="+floor+"&landmark="+landmark+"&TypeAddress="+TypeAddress+"&LatLocation="+LatLocation+"&LongLocation="+LongLocation+"&Users_id="+users_id+"&CurrentLocation="+CurrentLocation+"&id="+addressId;
        class dbclass extends AsyncTask<String ,Void, String>
        {
            protected void onPostExecute(String data)
            {
                try {
                    Intent intent = new Intent(EditAddressBook.this,AddressBook.class);
                    startActivity(intent);
                }
                catch (Exception ex)
                {
                    Toast.makeText(EditAddressBook.this, "Invalid id and password", Toast.LENGTH_LONG).show();
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
        obj.execute(SaveAddressUrl+addcrturl);
    }


}
