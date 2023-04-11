package com.example.statozone;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Profile extends AppCompatActivity {
    private SharedPreferences myPrefs;
    private static final String ProfileUpdate="https://food.tbvcsoft.com/app/ProfileUpdate.php";
    private int SELECT_PICTURE = 200;
    public ImageView imgProfile;
    public FloatingActionButton fabProfile;
    public ProgressBar progressBar;
    private EditText Nameedit, Email;
    private String users_id;
    private TextView changepsw;
    private Button btnsubmit;


    EditText edtname, edtemail, edtMobile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        edtname = findViewById(R.id.edtname);
        edtemail = findViewById(R.id.edtemail);
        edtMobile = findViewById(R.id.edtMobile);
        btnsubmit =findViewById(R.id.btnsubmit);



        progressBar = findViewById(R.id.progressBar);
        changepsw = findViewById(R.id.txtchangepassword);

        Nameedit = (EditText) findViewById(R.id.edtname);
        Email = (EditText) findViewById(R.id.edtemail);

        myPrefs = getSharedPreferences("myPrefs", MODE_PRIVATE);
         users_id=myPrefs.getString("user_id", "");
        String userName=myPrefs.getString("user_Name", "");
        String userPhone=myPrefs.getString("user_Phone", "");
        String userEmail=myPrefs.getString("user_Email", "");

        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String Name = Nameedit.getText().toString();
                final String EmailEdit = Email.getText().toString();
                if (Name.length() == 0) {
                    Nameedit.requestFocus();
                    Nameedit.setError("FIELD CANNOT BE EMPTY");
                } else if (EmailEdit.length() == 0) {
                    Email.requestFocus();
                    Email.setError("FIELD CANNOT BE EMPTY");
                }
                else
                {
                   // dltcrt(Name,EmailEdit);
                    Toast.makeText(Profile.this, "Update Succesfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Profile.this,Homepage.class);
                    startActivity(intent);


                }
            }
        });
        changepsw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(Profile.this, "Not Activity", Toast.LENGTH_SHORT).show();
            }
        });
        edtname.setText(userName);
        edtemail.setText(userEmail);
        edtMobile.setText(userPhone);
    }
    public void dltcrt(String name, String EmailEdit)
    {
        String usersName = name;
        String UserEmail  = EmailEdit;

        String ProfileValue = "?UserName="+usersName+"&UsersEmail="+UserEmail+"&userId="+users_id;
        class dbclass extends AsyncTask<String ,Void, String>
        {
            protected void onPostExecute(String data)
            {
                try {

                    SharedPreferences myPrefs = getSharedPreferences("myPrefs", MODE_PRIVATE);
                    SharedPreferences.Editor prefsEditor;
                    prefsEditor = myPrefs.edit();
                    prefsEditor.putString("user_Name",usersName);
                    prefsEditor.putString("user_Email",UserEmail);
                    prefsEditor.commit();

                    Intent intent = getIntent();
                    finish();
                    startActivity(intent);
                  //  Toast.makeText(Profile.this, data, Toast.LENGTH_LONG).show();
                }
                catch (Exception ex)
                {
                    Toast.makeText(Profile.this, "Invalid id and password", Toast.LENGTH_LONG).show();
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
        obj.execute(ProfileUpdate+ProfileValue);
    }
    void imageChooser() {

        // create an instance of the
        // intent of the type image
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);

        // pass the constant to compare it
        // with the returned requestCode
        startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE);
    }

    // this function is triggered when user
    // selects the image from the imageChooser
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            // compare the resultCode with the
            // SELECT_PICTURE constant
            if (requestCode == SELECT_PICTURE) {
                // Get the url of the image from data
                Uri selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    // update the preview image in the layout
                  //  imgProfile.setImageURI(selectedImageUri);
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }
}