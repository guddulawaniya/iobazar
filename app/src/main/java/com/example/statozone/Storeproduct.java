package com.example.statozone;

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
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.text.HtmlCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.bumptech.glide.Glide;
import com.facebook.shimmer.ShimmerFrameLayout;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Storeproduct extends AppCompatActivity {
    private final static String UrlAddCart = Config.Base_url+"AddtoCart.php";
    private final static String productUrl = Config.Base_url+"Productlist.php";
    private final static String MenuList = Config.Base_url+"MenuLIst.php";
    private final static String MenuListUrl = Config.Base_url+"categorymenulist.php";
    private final static String RecommendedUrl = Config.Base_url+"recommended.php";
    private final static String storeDetaileUrl = Config.Base_url+"Singlestore.php";
    private final static String getCartUrl = Config.Base_url+"GetCartDetailbyPrId.php";
    private final static String DeleteCartSession = Config.Base_url+"DeleteCart.php";
    private final static String generalSetting = Config.Base_url+"zeneral_setting.php";
    private static  String MenuId[],MenuName[];
    private static int subListCount[];
    private ArrayList<Submenudetail> subMenuListArray = new ArrayList<Submenudetail>();
    private SubMenuAdapter subMenuAdapter;
    private ImageView backBtn, strImage;
    private Button Menu;
    private int CountMenuList = 0;
    private RequestQueue requestQueue;
    private ArrayList<Recommended> RecommendedList = new ArrayList<Recommended>();
    private   List studentDataList = new ArrayList<>();
    ArrayList<String> categoryList = new ArrayList<String>();
    private RecyclerView recommendedIdList,lgrd;
    private String  store_id;
    private TextView strName, stradrs,strRating,strTime,strMaximum,scrTxt;
    private SharedPreferences myPrefs;
    private static String productIds[];
    private static String productImg[];
    private static String prCount[];
    private static int productPrice[];
    private static String productName[];
    private static int productDiscount[];
    //general setting
    private String id,symbol;
    //store detail name
    private static String store_Id[];
    private static String storeName[];
    private static String storedescription[];
    private static String storeImage[];
    private static String storeRating[];
    private static String storeMax[];
    private static String storeTime[];
    private static int ProductQty[];
    private TextView storeNameLink;
    //recommended detail
    private String cartide;
    int minteger ,TotalQty=0;
    private String pr_id;
    private RelativeLayout footer;
    // private RecommendedAdapter adapter;
    private StoreProductAdapter adapterPr;

    private List storeProductListArrayListArray = new ArrayList<>();
    private static String RecoProName[];
    private static String RecoProId[];
    private static int RecoProPrice[];
    private static String RecoProImage[];
    private static int RecoProDiscount[];
    private static int RePrQty[];
    //category
    private static String img[];
    private static String name[];
    private static String ids[];
    private static String storname[];
    private static String storeimage[];
    private static String storeaddress[];
    private static String storeid[];
    private static String storeUser_id[];
    private static String twoorder[];
    private static String aproxtime[];
    //loader
    private ShimmerFrameLayout shimmerFrameLayout,categorySkeleton,skeletonStore;
    private RelativeLayout fullScr;
    //get cart detail for api
    int totalqty = 0,Product_price_cnt=0;
    private static String Cart_id[];
    private static Integer Cart_price[];
    private static String Cart_product_id[];
    private static Integer Cart_pr_item[];
    // Cart detail
    private String UserId;
    private ScrollView ScrollView01;
    private TextView HeaderStrName;
    private String HeaderStoreNm;
    private MenuListAdapter menuListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storeproduct);
        backBtn =(ImageView) findViewById(R.id.back);
        strName = (TextView)findViewById(R.id.strName);
        storeNameLink = (TextView)findViewById(R.id.storeName);
        stradrs = (TextView)findViewById(R.id.stradrs);
        strRating = (TextView)findViewById(R.id.strRating);
        strTime = (TextView)findViewById(R.id.strTime);
        strMaximum = (TextView)findViewById(R.id.strMaximum);
        scrTxt = (TextView)findViewById(R.id.scrTxt);
        strImage = (ImageView)findViewById(R.id.strImage);
        ScrollView01 = findViewById(R.id.ScrollView01);
        // recommendedIdList = (RecyclerView)findViewById(R.id.recommendedIdList);
        footer = (RelativeLayout)findViewById(R.id.footer);
        shimmerFrameLayout = findViewById(R.id.shimmer_view_container);
        categorySkeleton = findViewById(R.id.categorySkeleton);
        lgrd = (RecyclerView) findViewById(R.id.List_crt_pr);
        skeletonStore = findViewById(R.id.skeletonStore);
        HeaderStrName = findViewById(R.id.HeaderStrName);
        Menu = findViewById(R.id.clickBtn);
        // get intent value
        Intent intent = getIntent();
        store_id = intent.getStringExtra("storeUser_id");
        myPrefs = getSharedPreferences("myPrefs", MODE_PRIVATE);
        UserId = myPrefs.getString("user_id", "");
        footer.setVisibility(View.GONE);
        //click button
        footer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Storeproduct.this,CartView.class);
                startActivity(intent);
            }
        });
        scrTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Storeproduct.this,SearchStoreProduct.class);
                intent.putExtra("SearchStoreId",store_id);
                startActivity(intent);
            }
        });
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Storeproduct.this,Homepage.class);
                startActivity(intent);
            }
        });
        /*
        Menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Initializing the popup menu and giving the reference as current context
                PopupMenu popupMenu = new PopupMenu(Storeproduct.this, Menu);
                for (int count= 0; count < CountMenuList; count++)
                {
                    int second = count+1;
                    int third = count+2;
                    if (categoryList.get(second) == String.valueOf(0)) {
                       // popupMenu.getMenu().add(categoryList.get(count) + categoryList.get(second));
                    }
                    else
                    {
                        popupMenu.getMenu().add(categoryList.get(count) + categoryList.get(second));
                        CategoryByproduct(store_id,UserId,categoryList.get(third));

                    }
                    count  = count+2;
                }
                // Inflating popup menu from popup_menu.xml file
                //popupMenu.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        // Toast message on menu item clicked
                        Toast.makeText(Storeproduct.this, "You Clicked " + menuItem.getGroupId(), Toast.LENGTH_SHORT).show();
                        return true;
                    }
                });
                // Showing the popup menu
                popupMenu.show();
            }
        });*/
        // get store id

        // All function
        homesetting();
        storedetail(store_id);

        SubMenuList();
        ProductCart(UserId);
        //  Recommended(store_id);
        ScrollView01.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                int scrollX = ScrollView01.getScrollY();
                if(scrollX > 250)
                {
                    HeaderStrName.setText(HeaderStoreNm);
                }
                else
                {
                    HeaderStrName.setText("");
                }
            }
        });
        isInternetOn();

        Menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopUp(view);
            }
        });

    }
    //store details
    private void homesetting() {
        class dbManager extends AsyncTask<String, Void, String>
        {
            protected void onPostExecute(String data)
            {
                try {
                    JSONObject jObj = new JSONObject(data);
                    id = jObj.getString("id");
                    symbol = jObj.getString("symbol");
                }
                catch (Exception ex)
                {
                    //  Toast.makeText(Storeproduct.this,"Please check your mobile internet",Toast.LENGTH_LONG).show();
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
                    for (int i = 0; i < ja.length(); i++) {
                        jo = ja.getJSONObject(i);
                        storeName[i] = jo.getString("name");
                        store_Id[i] = jo.getString("id");
                        storeRating[i] = jo.getString("rating");
                        storeMax[i] = jo.getString("maximum");
                        storeTime[i] = jo.getString("timeaprox");
                        storedescription[i] = jo.getString("description");
                        storeImage[i] ="https://food.tbvcsoft.com/uploads/shop_profile/" + jo.getString("image");
                        stradrs.setText(storedescription[i]);
                        strName.setText(storeName[i]);
                        storeNameLink.setText(storeName[i]);
                        strRating.setText(storeRating[i]);
                        strTime.setText(storeTime[i]);
                        strMaximum.setText(storeMax[i]+" FOR TWO");
                        String url=storeImage[i];
                        Glide.with(Storeproduct.this).load(url).into(strImage);
                        skeletonStore.startShimmer();
                        skeletonStore.setVisibility(View.GONE);
                        HeaderStoreNm = storeName[i];
                    }
                }
                catch (Exception ex)
                {
                    Toast.makeText(Storeproduct.this,"something went wrong",Toast.LENGTH_LONG).show();
                }
            }
            @Override
            protected String doInBackground(String... strings) {
                try
                {
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
    private void ProductCart(String UserId) {
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
                    int productPriceTotal=0,prqty=0,prsingleqty=0,shwcrtamt=0,shwqty=0,prId = 0;
                    for (int i = 0; i < ja.length(); i++) {
                        jo = ja.getJSONObject(i);
                        Cart_id[i] = jo.getString("id");
                        Cart_price[i] = jo.getInt("product_price");
                        Cart_product_id[i] = jo.getString("product_id");
                        Cart_pr_item[i] = jo.getInt("quantity");
                        prId = Integer.parseInt(Cart_product_id[i]);
                        shwcrtamt = shwcrtamt+Cart_price[i];
                        prqty = prqty+Cart_pr_item[i];
                        shwqty= shwqty+Cart_pr_item[i];
                        productPriceTotal = productPriceTotal+Cart_price[i];
                    }
                    Product_price_cnt =productPriceTotal;
                    totalqty = prqty;
                    minteger = totalqty;
                    // footer.setVisibility(View.VISIBLE);
                    showcardtdetail(totalqty,Product_price_cnt);
                }
                catch (Exception ex)
                {
                    //   footer.setVisibility(View.GONE);
                    // Toast.makeText(Storeproduct.this,"Please check your mobile internet",Toast.LENGTH_LONG).show();
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
        obj.execute(getCartUrl+getTOcartUrl);
    }
    private void SubMenuList() {
        String AddToUrl = "?users_Id="+store_id;
        class dbManager extends AsyncTask<String, Void, String>
        {

            protected void onPostExecute(String data)
            {
                try {
                    JSONArray ja = new JSONArray(data);
                    JSONObject jo = null;
                    MenuId =  new String[ja.length()];
                    MenuName = new String[ja.length()];
                    subListCount = new int[ja.length()];

                    for( int i=0; i< ja.length(); i++)
                    {
                        jo = ja.getJSONObject(i);
                        MenuId[i] = jo.getString("id");
                        MenuName[i] = jo.getString("name");
                        subListCount[i] = jo.getInt("count");
                        subMenuListArray.add(new Submenudetail(MenuId[i],MenuName[i],subListCount[i]));
                    }
                    shimmerFrameLayout.startShimmer();
                    shimmerFrameLayout.setVisibility(View.GONE);
                    subMenuAdapter = new SubMenuAdapter(subMenuListArray, Storeproduct.this);
                    LinearLayoutManager horizontalLayoutManagaer
                            = new LinearLayoutManager(Storeproduct.this, LinearLayoutManager.VERTICAL, false);
                    lgrd.setHasFixedSize(true);
                    lgrd.setLayoutManager(horizontalLayoutManagaer);
                    lgrd.setAdapter(subMenuAdapter);
                }
                catch (Exception ex)
                {
                    shimmerFrameLayout.setVisibility(View.GONE);
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
        obj.execute(MenuListUrl+AddToUrl);
    }
    public class SubMenuAdapter extends RecyclerView.Adapter<SubMenuAdapter.ViewHolder>
    {
        private ArrayList<Submenudetail> submenudetailArrayList;

        private Context context;

        public SubMenuAdapter(ArrayList<Submenudetail> submenudetailArrayList,Context context )
        {
            this.submenudetailArrayList = submenudetailArrayList;
            this.context = context;
        }
        @NonNull
        @Override
        public SubMenuAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.menulist,parent, false);
            return new SubMenuAdapter.ViewHolder(view);
        }
        @Override
        public void onBindViewHolder(@NonNull SubMenuAdapter.ViewHolder holder, int position) {
            Submenudetail model = subMenuListArray.get(position);
            holder.bind(model);
        }
        @Override
        public int getItemCount() {
            return subMenuListArray.size();
        }
        public class ViewHolder extends RecyclerView.ViewHolder {
            private  TextView PrName,sectionCount;
            private RecyclerView itemRecyclerView;
            private LinearLayout ListShow;
            private int Count=0;
            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                PrName =  itemView.findViewById(R.id.section_item_text_view);
                sectionCount =  itemView.findViewById(R.id.sectionCount);
                itemRecyclerView = itemView.findViewById(R.id.lgrd);
                ListShow = findViewById(R.id.ListShow);
            }
            public void bind(Submenudetail subMenuAdapter) {
                if(subMenuAdapter.getSubListCount() > 0)
                {
                    sectionCount.setText("("+subMenuAdapter.getSubListCount()+")");
                    PrName.setText(subMenuAdapter.getMenuName());
                    productdetail(store_id,UserId,subMenuAdapter.getMenuId(),itemRecyclerView);
                }
            }
        }
    }
    /*
    private void Recommended(String store_id) {
        String storeId = store_id;
        String RecommendUrl = "?seller_id="+storeId+"&CostumerId="+UserId;
        class dbManager extends AsyncTask<String, Void, String>
        {
            protected void onPostExecute(String data)
            {
               try {
                     JSONArray ja = new JSONArray(data);
                    JSONObject jo = null;
                   RecoProName =  new String[ja.length()];
                   RecoProId = new String[ja.length()];
                   RecoProPrice = new int[ja.length()];
                   RecoProImage = new  String[ja.length()];
                   RePrQty = new int[ja.length()];
                   RecoProDiscount = new int[ja.length()];
                    for( int i=0; i< ja.length(); i++)
                    {
                        jo = ja.getJSONObject(i);
                        RecoProName[i] = jo.getString("name");
                        RecoProId[i] = jo.getString("id");
                        RecoProPrice[i] = jo.getInt("price");
                        RecoProImage[i] = "https://food.tbvcsoft.com/uploads/products/"+jo.getString("image");
                        RePrQty[i] = jo.getInt("PrQty");
                        RecoProDiscount[i] = jo.getInt("discount");
                        RecommendedList.add(new Recommended(RecoProName[i],RecoProId[i],RecoProPrice[i],RecoProImage[i],RePrQty[i],RecoProDiscount[i]));
                    }
                   categorySkeleton.startShimmer();
                   categorySkeleton.setVisibility(View.GONE);
                   recommendedIdList.setVisibility(View.VISIBLE);
                    adapter = new RecommendedAdapter(RecommendedList, Storeproduct.this);
                    LinearLayoutManager horizontalLayoutManagaer
                            = new LinearLayoutManager(Storeproduct.this, LinearLayoutManager.HORIZONTAL, false);
                   recommendedIdList.setHasFixedSize(true);
                   recommendedIdList.setLayoutManager(horizontalLayoutManagaer);
                   recommendedIdList.setAdapter(adapter);
                }
                catch (Exception ex)
                {
                    categorySkeleton.setVisibility(View.GONE);
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
        obj.execute(RecommendedUrl+RecommendUrl);
    }
 public class RecommendedAdapter extends RecyclerView.Adapter<RecommendedAdapter.ViewHolder>
  {
    private ArrayList<Recommended> RecommendedList;
    private Context context;
    public RecommendedAdapter(ArrayList<Recommended> RecommendedList,Context context )
    {
        this.RecommendedList = RecommendedList;
        this.context = context;
    }
    @NonNull
    @Override
    public RecommendedAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recommended,parent, false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull RecommendedAdapter.ViewHolder holder, int position) {
        Recommended model = RecommendedList.get(position);
        holder.PrName.setText(model.getRecomPrName());

        int discount = (model.getRecomPrPrice()*model.getRePrDiscount())/100;
        int discountPrice = model.getRecomPrPrice() - discount;

        String ProductName = model.getRecomPrName();
        String price = "<del>"+symbol+Math.round(model.getRecomPrPrice())+"</del>";
        String Unitprice = symbol+Math.round(discountPrice)+"";
        holder.PrUnitPrice.setText(HtmlCompat.fromHtml(Unitprice, HtmlCompat.FROM_HTML_MODE_LEGACY));
        holder.PrPrice.setText(HtmlCompat.fromHtml(price, HtmlCompat.FROM_HTML_MODE_LEGACY));
        //product detail
        pr_id = model.getRecomPrId();
        String ProductId = model.getRecomPrId();
        Glide.with(Storeproduct.this).load(model.getRecomPrImage()).into(holder.Image);
        final int[] PrQty = {model.getRePrQty()};
        if(model.getRePrQty() == 0)
        {
            holder.prId.setVisibility(View.GONE);
        }
        else
        {
            holder.prId.setVisibility(View.VISIBLE);
            holder.prId.setText("" + model.getRePrQty());
        }
        holder.plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(PrQty[0]<10) {
                    PrQty[0] = PrQty[0] + 1;
                    minteger = minteger + 1;
                    Product_price_cnt = Product_price_cnt + discountPrice;
                    int totalPrice = discountPrice * PrQty[0];
                    holder.prId.setText("" + PrQty[0]);
                    holder.prId.setVisibility(View.VISIBLE);
                    display(minteger, ProductId, Product_price_cnt);
                    cartide = myPrefs.getString("crtstrid", "");
                    if (cartide.isEmpty()) {
                        SharedPreferences myPrefs = getSharedPreferences("myPrefs", MODE_PRIVATE);
                        SharedPreferences.Editor prefsEditor;
                        prefsEditor = myPrefs.edit();
                        prefsEditor.putString("crtstrid", store_id);
                        prefsEditor.commit();
                    } else {
                        int firstcon = Integer.parseInt(cartide);
                        int Secoundcon = Integer.parseInt(store_id);
                        if (firstcon == Secoundcon) {
                            AddtoCart(PrQty[0], ProductId, UserId, totalPrice, ProductName);
                        } else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(Storeproduct.this);
                            builder.setTitle("Please Clear Cart").
                                    setMessage("sure, You want to Clear Cart?");
                            builder.setPositiveButton("Yes",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            DeleteCartSession(cartide, UserId);
                                            SharedPreferences myPrefs = getSharedPreferences("myPrefs", MODE_PRIVATE);
                                            SharedPreferences.Editor prefsEditor;
                                            prefsEditor = myPrefs.edit();
                                            prefsEditor.remove("crtstrid");
                                            prefsEditor.putString("crtstrid", store_id);
                                            prefsEditor.commit();
                                            Intent intent = getIntent();
                                            AddtoCart(PrQty[0], ProductId, UserId, totalPrice, ProductName);
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
                if(PrQty[0]<0) {
                    holder.prId.setVisibility(View.GONE);
                }
            }
        });
        holder.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  decreaseInteger(ProductId,productPrice);
                if(PrQty[0]>0) {
                    PrQty[0] = PrQty[0] - 1;
                    minteger = minteger - 1;
                    Product_price_cnt = Product_price_cnt - discountPrice;
                    int totalPrice = discountPrice * PrQty[0];
                    holder.prId.setText("" + PrQty[0]);
                    AddtoCart(PrQty[0], ProductId, UserId, Product_price_cnt,ProductName);
                    display(minteger,ProductId,totalPrice);
                }
                if(PrQty[0]<1) {
                    holder.prId.setVisibility(View.GONE);
                }
                if(minteger == 0) {
                    SharedPreferences myPrefs = getSharedPreferences("myPrefs", MODE_PRIVATE);
                    SharedPreferences.Editor prefsEditor;
                    prefsEditor = myPrefs.edit();
                    prefsEditor.remove("crtstrid");
                    prefsEditor.commit();
                }
            }
        });
        holder.Image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences myPrefs = getSharedPreferences("myPrefs", MODE_PRIVATE);
                SharedPreferences.Editor prefsEditor;
                prefsEditor = myPrefs.edit();
                prefsEditor.putString("product_id", model.getRecomPrId());
                prefsEditor.commit();
                Intent intent = new Intent(Storeproduct.this,ProductView.class);
                intent.putExtra("product_id", model.getRecomPrId());
                intent.putExtra("storeUser_id", store_id);
                startActivity(intent);
            }
        });
    }
    @Override
    public int getItemCount() {
        return RecommendedList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
       private  TextView PrName,PrPrice,PrUnitPrice,prId;
       private ImageView Image;
       private Button plus, minus;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            PrName = (TextView) itemView.findViewById(R.id.prdctName);
            PrPrice = (TextView) itemView.findViewById(R.id.prdctPrice);
            PrUnitPrice = (TextView) itemView.findViewById(R.id.prdctPriceunit);
            Image = (ImageView) itemView.findViewById(R.id.prdctImage);
            plus =(Button)itemView.findViewById(R.id.increase);
            minus = (Button)itemView.findViewById(R.id.decrease);
            prId = (TextView) itemView.findViewById(R.id.prId);
            prId.setVisibility(View.GONE);
        }
    }
}*/
    private void productdetail(String store_id, String UserId, String menuId, RecyclerView itemRecyclerView) {
        String storeuser_id = store_id;
        String Csid = UserId;
        RecyclerView sublist = itemRecyclerView;
        String SubId = menuId;
        String addcrturl = "?users_Id="+storeuser_id+"&CostumerId="+Csid+"&SubId="+SubId;
        class dbManager extends AsyncTask<String, Void, String>
        {
            protected void onPostExecute(String data)
            {
                try {
                    JSONArray ja = new JSONArray(data);
                    JSONObject jo = null;
                    productName = new String[ja.length()];
                    productImg = new String[ja.length()];
                    productIds = new String[ja.length()];
                    productPrice = new int[ja.length()];
                    ProductQty = new int[ja.length()];
                    productDiscount = new int[ja.length()];
                    storeProductListArrayListArray.clear();
                    for (int i = 0; i < ja.length(); i++)
                    {
                        jo = ja.getJSONObject(i);
                        productName[i] = jo.getString("name");
                        productIds[i] = jo.getString("id");
                        productPrice[i] = jo.getInt("price");
                        ProductQty[i] = jo.getInt("PrQty");
                        productDiscount[i]= jo.getInt("discount");
                        productImg[i] ="https://food.tbvcsoft.com/uploads/products/" + jo.getString("image");
                        StoreProductList ListData=  new StoreProductList(productName[i],productIds[i],productPrice[i],ProductQty[i],productImg[i],productDiscount[i]);
                        storeProductListArrayListArray.add(ListData);

                    }

                    adapterPr = new StoreProductAdapter(storeProductListArrayListArray, Storeproduct.this);
                    LinearLayoutManager horizontalLayoutManagaer
                            = new LinearLayoutManager(Storeproduct.this, LinearLayoutManager.VERTICAL, false);
                    sublist.setHasFixedSize(true);
                    sublist.setLayoutManager(horizontalLayoutManagaer);
                    sublist.setAdapter(adapterPr);

                }
                catch (Exception ex)
                {


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
        obj.execute(productUrl+addcrturl);
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
                    Toast.makeText(Storeproduct.this,"Something went wrong",Toast.LENGTH_LONG).show();
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
    //click button work
    private void showcardtdetail(int number,int Product_price_cnt) {
        footer.setVisibility(View.VISIBLE);
        int TotalPrice =Product_price_cnt;
        minteger = number;
        if(minteger>0)
        {
            footer.setVisibility(View.VISIBLE);
        }
        if(minteger<1)
        {
            footer.setVisibility(View.GONE);
        }
        TextView viewPrice = (TextView)findViewById(R.id.viewPrise);
        TextView  crtcnt = (TextView)findViewById(R.id.crtcnt);
        crtcnt.setText(minteger+" ITEMS");
        viewPrice.setText(""+symbol+TotalPrice);
    }
    private void display(int number,String productadid,int totalPrice) {
        int show_amount =  Product_price_cnt;
        TotalQty =  number;
        TextView displayInteger = (TextView) findViewById(R.id.integer_number);
        TextView viewPrice = (TextView) findViewById(R.id.viewPrise);
        TextView crtcnt = (TextView)findViewById(R.id.crtcnt);
        crtcnt.setText(TotalQty+" ITEMS");
        viewPrice.setText(""+symbol+show_amount);
        if(TotalQty > 0)
        {
            footer.setVisibility(View.VISIBLE);
        }
        else
        {
            footer.setVisibility(View.GONE);
        }
    }
    private void AddtoCart(Integer ProductQty,String pr_id, String user_id,Integer Total_amount, String ProductName) {
        Integer qty = ProductQty;
        String productId = pr_id;
        String UserId = user_id;
        Integer productprise = Total_amount;
        String Pr_name = ProductName;
        String AddTOcartUrl = "?users_Id="+UserId+"&product_Id="+productId+"&quantity="+qty+"&ProductPric="+productprise+"&ProductName="+Pr_name+"&sellerid="+store_id;
        class dbManager extends AsyncTask<String, Void, String>
        {
            protected void onPostExecute(String data)
            {
                try {
                    // Toast.makeText(ProductView.this,data,Toast.LENGTH_LONG).show();
                }
                catch (Exception ex)
                {
                    Toast.makeText(Storeproduct.this,"Something went wrong",Toast.LENGTH_LONG).show();
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
    public class StoreProductAdapter extends RecyclerView.Adapter<StoreProductAdapter.ViewHolder>  {
        private List storeProductListArrayListArray;    // Values to be displayed
        private Context context;
        public StoreProductAdapter(List storeProductListArrayListArray, Context context) {
            this.storeProductListArrayListArray = storeProductListArrayListArray;
            this.context = context;
        }
        @NonNull
        @Override
        public StoreProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.productslist, parent, false);
            return new StoreProductAdapter.ViewHolder(view);
        }
        @Override
        public void onBindViewHolder(@NonNull StoreProductAdapter.ViewHolder holder, int position) {
            StoreProductList model = (StoreProductList) storeProductListArrayListArray.get(position);
            int discountPrice = (model.getPrDiscount()*model.getPrPrice())/100;
            int totalDiscount = model.getPrPrice() - discountPrice;
            if(model.getPrQty() == 0)
            {
                holder.prdctQty.setVisibility(View.GONE);
            }
            else {
                holder.prdctQty.setText(""+model.getPrQty());
            }
            String disprice = "<del>"+symbol+Math.round(model.getPrPrice())+"</del>";
            String unitpr = symbol+Math.round(totalDiscount)+"";
            holder.UnitPrice.setText(HtmlCompat.fromHtml(unitpr, HtmlCompat.FROM_HTML_MODE_LEGACY));
            holder.pdctPrice.setText(HtmlCompat.fromHtml(disprice, HtmlCompat.FROM_HTML_MODE_LEGACY));
            holder.pdctName.setText(model.getPrName());
            Glide.with(Storeproduct.this).load(model.getPrImg()).into(holder.pdctImage);
            final int[] totalPrice = {totalDiscount};
            final int[] PrQty = {model.getPrQty()};
            if(model.getPrQty() ==0)
            {
                holder.addbtn.setVisibility(View.VISIBLE);
                holder.btncount.setVisibility(View.GONE);
            }
            else
            {
                holder.addbtn.setVisibility(View.GONE);
                holder.btncount.setVisibility(View.VISIBLE);
                holder.integer_number.setText(""+model.getPrQty());
            }
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Storeproduct.this,ProductView.class);
                    intent.putExtra("product_id", model.getPrIds());
                    intent.putExtra("storeUser_id", store_id);
                    startActivity(intent);
                }
            });
            holder.pdctImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Storeproduct.this,ProductView.class);
                    intent.putExtra("product_id", model.getPrIds());
                    intent.putExtra("storeUser_id", store_id);
                    startActivity(intent);
                }
            });
            holder.addbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //  AddtoCart(PrQty[0], model.getPrIds(), UserId, totalPrice[0], model.getPrName());
                    cartide = myPrefs.getString("crtstrid", "");
                    if (cartide.isEmpty()) {
                        SharedPreferences myPrefs = getSharedPreferences("myPrefs", MODE_PRIVATE);
                        SharedPreferences.Editor prefsEditor;
                        prefsEditor = myPrefs.edit();
                        prefsEditor.putString("crtstrid", store_id);
                        prefsEditor.commit();
                    } else {
                        int firstcon = Integer.parseInt(cartide);
                        int Secoundcon = Integer.parseInt(store_id);
                        if (firstcon == Secoundcon) {
                            PrQty[0] = PrQty[0] +1;
                            minteger = minteger + 1;
                            Product_price_cnt = Product_price_cnt + totalDiscount;
                            totalPrice[0] =  totalPrice[0]*PrQty[0];
                            holder.addbtn.setVisibility(View.GONE);
                            holder.btncount.setVisibility(View.VISIBLE);
                            holder.integer_number.setText(""+PrQty[0]);
                            holder.prdctQty.setText(""+PrQty[0]);
                            holder.prdctQty.setVisibility(View.VISIBLE);
                            display(minteger, model.getPrIds(), Product_price_cnt);
                            AddtoCart(PrQty[0], model.getPrIds(), UserId, totalPrice[0], model.getPrName());
                        } else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(Storeproduct.this);
                            builder.setTitle("Please Clear Cart").
                                    setMessage("sure, You want to Clear Cart?");
                            builder.setPositiveButton("Yes",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            DeleteCartSession(cartide, UserId);
                                            SharedPreferences myPrefs = getSharedPreferences("myPrefs", MODE_PRIVATE);
                                            SharedPreferences.Editor prefsEditor;
                                            prefsEditor = myPrefs.edit();
                                            prefsEditor.remove("crtstrid");
                                            prefsEditor.putString("crtstrid", store_id);
                                            prefsEditor.commit();
                                            Intent intent = getIntent();
                                            PrQty[0] = PrQty[0] +1;
                                            minteger = minteger + 1;
                                            Product_price_cnt = Product_price_cnt + totalDiscount;
                                            totalPrice[0] =  totalPrice[0]*PrQty[0];
                                            holder.addbtn.setVisibility(View.GONE);
                                            holder.btncount.setVisibility(View.VISIBLE);
                                            holder.integer_number.setText(""+PrQty[0]);
                                            holder.prdctQty.setText(""+PrQty[0]);
                                            holder.prdctQty.setVisibility(View.VISIBLE);
                                            display(minteger, model.getPrIds(), Product_price_cnt);
                                            AddtoCart(PrQty[0], model.getPrIds(), UserId, totalPrice[0], model.getPrName());
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
            holder.decrease.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(PrQty[0]>0)
                    {
                        PrQty[0] = PrQty[0] -1;
                        minteger = minteger - 1;
                    }
                    else
                    {
                        holder.addbtn.setVisibility(View.VISIBLE);
                        holder.btncount.setVisibility(View.GONE);
                        holder.prdctQty.setVisibility(View.GONE);
                    }
                    Product_price_cnt = Product_price_cnt - totalDiscount;
                    totalPrice[0] =  totalPrice[0]*PrQty[0];
                    holder.addbtn.setVisibility(View.GONE);
                    holder.btncount.setVisibility(View.VISIBLE);
                    holder.integer_number.setText(""+PrQty[0]);
                    holder.prdctQty.setText(""+PrQty[0]);
                    holder.prdctQty.setVisibility(View.VISIBLE);
                    display(minteger, model.getPrIds(), Product_price_cnt);
                    AddtoCart(PrQty[0], model.getPrIds(), UserId, totalPrice[0], model.getPrName());
                    if(PrQty[0]==0)
                    {
                        holder.addbtn.setVisibility(View.VISIBLE);
                        holder.btncount.setVisibility(View.GONE);
                        holder.prdctQty.setVisibility(View.GONE);
                    }
                    if(minteger == 0) {
                        SharedPreferences myPrefs = getSharedPreferences("myPrefs", MODE_PRIVATE);
                        SharedPreferences.Editor prefsEditor;
                        prefsEditor = myPrefs.edit();
                        prefsEditor.remove("crtstrid");
                        prefsEditor.commit();
                    }
                }
            });
            holder.increase.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(PrQty[0] <10) {
                        PrQty[0] = PrQty[0] + 1;
                        minteger = minteger + 1;
                    }
                    Product_price_cnt = Product_price_cnt + totalDiscount;
                    totalPrice[0] =  totalPrice[0]*PrQty[0];
                    holder.addbtn.setVisibility(View.GONE);
                    holder.btncount.setVisibility(View.VISIBLE);
                    holder.integer_number.setText(""+PrQty[0]);
                    holder.prdctQty.setText(""+PrQty[0]);
                    holder.prdctQty.setVisibility(View.VISIBLE);
                    display(minteger, model.getPrIds(), Product_price_cnt);
                    AddtoCart(PrQty[0], model.getPrIds(), UserId, totalPrice[0], model.getPrName());
                }
            });
        }
        @Override
        public int getItemCount() {
            // returning the size of array list.
            return storeProductListArrayListArray.size();
        }
        public class ViewHolder extends RecyclerView.ViewHolder {
            // creating variables for our views.
            private ImageView pdctImage;
            private Button decrease,increase;
            private LinearLayout btncount;
            private TextView pdctName,pdctPrice,UnitPrice,prdctQty,addbtn,integer_number;
            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                pdctImage= itemView.findViewById(R.id.prdctImage);
                pdctName= itemView.findViewById(R.id.prdctName);
                pdctPrice= itemView.findViewById(R.id.prdctPrice);
                UnitPrice = itemView.findViewById(R.id.prdctPriceunit);
                prdctQty =  itemView.findViewById(R.id.prdctQty);
                addbtn = itemView.findViewById(R.id.addbtn);
                btncount = itemView.findViewById(R.id.btncount);
                integer_number = itemView.findViewById(R.id.integer_number);
                decrease = itemView.findViewById(R.id.decrease);
                increase = itemView.findViewById(R.id.increase);
            }
        }
    }

    public void showPopUp(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater layoutInflater = getLayoutInflater();

        //this is custom dialog
        //custom_popup_dialog contains textview only
        View customView = layoutInflater.inflate(R.layout.popup, null);
        // reference the textview of custom_popup_dialog
        RecyclerView MenuName = customView.findViewById(R.id.MenuList);

        SubMenuListShow(MenuName);

        builder.setView(customView);
        builder.create();
        builder.show();

    }
    private void SubMenuListShow(RecyclerView MenuList) {
        String AddToUrl = "?users_Id="+store_id;
        class dbManager extends AsyncTask<String, Void, String>
        {

            protected void onPostExecute(String data)
            {
                try {
                    JSONArray ja = new JSONArray(data);
                    JSONObject jo = null;
                    MenuId =  new String[ja.length()];
                    MenuName = new String[ja.length()];
                    subListCount = new int[ja.length()];

                    for( int i=0; i< ja.length(); i++)
                    {
                        jo = ja.getJSONObject(i);
                        MenuId[i] = jo.getString("id");
                        MenuName[i] = jo.getString("name");
                        subListCount[i] = jo.getInt("count");
                        subMenuListArray.add(new Submenudetail(MenuId[i],MenuName[i],subListCount[i]));
                    }
                    menuListAdapter = new MenuListAdapter(subMenuListArray, Storeproduct.this);
                    LinearLayoutManager horizontalLayoutManagaer
                            = new LinearLayoutManager(Storeproduct.this, LinearLayoutManager.VERTICAL, false);
                    MenuList.setHasFixedSize(true);
                    MenuList.setLayoutManager(horizontalLayoutManagaer);
                    MenuList.setAdapter(menuListAdapter);
                }
                catch (Exception ex)
                {
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
        obj.execute(MenuListUrl+AddToUrl);
    }
    public class MenuListAdapter extends RecyclerView.Adapter<MenuListAdapter.ViewHolder>
    {
        private ArrayList<Submenudetail> submenudetailArrayList;
        private Context context;
        public MenuListAdapter(ArrayList<Submenudetail> submenudetailArrayList,Context context )
        {
            this.submenudetailArrayList = submenudetailArrayList;
            this.context = context;
        }
        @NonNull
        @Override
        public MenuListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.menudetailshow,parent, false);
            return new ViewHolder(view);
        }
        @Override
        public void onBindViewHolder(@NonNull MenuListAdapter.ViewHolder holder, int position) {
            Submenudetail model = subMenuListArray.get(position);
            holder.PrName.setText(""+model.getMenuName());
            holder.MenuCount.setText(""+model.getSubListCount());
        }
        @Override
        public int getItemCount() {
            return subMenuListArray.size();
        }
        public class ViewHolder extends RecyclerView.ViewHolder {
            private  TextView PrName,MenuCount;
            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                PrName =  itemView.findViewById(R.id.MenuName);
                MenuCount = itemView.findViewById(R.id.MenuCount);
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
            return true;
        } else if (
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.DISCONNECTED ||
                        connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.DISCONNECTED  ) {
            Intent intent = new Intent(Storeproduct.this,IntenrNetconnection.class);
            startActivity(intent);
            Toast.makeText(this, " Not Connected ", Toast.LENGTH_LONG).show();
            return false;
        }
        return false;
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Storeproduct.this,Homepage.class);
        startActivity(intent);
    }
}