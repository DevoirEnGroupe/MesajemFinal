package ht.mesajem.mesajem.Activities;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentManager;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;


import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.facebook.ParseFacebookUtils;


import org.json.JSONException;

import java.util.Arrays;
import java.util.Collection;

import ht.mesajem.mesajem.R;


public class LoginActivity extends AppCompatActivity {

    TextView tvnonitiliza;
    TextView tvpaswod;
    EditText etnonitiliza;
    EditText etpaswod;
    Button btKonekte;
    TextView tvenskri;
    ProgressBar progressBar;
    String TAG = "LoginActivity";
    private static final int REQUEST_LOCATION = 1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


      if(ParseUser.getCurrentUser()!=null){
        goMainActivity();
       }

        // Assign variable
        tvenskri= findViewById(R.id.tvenskri);
        btKonekte = findViewById(R.id.btKonekte);
        etpaswod = findViewById(R.id.etpaswod);
        etnonitiliza= findViewById(R.id.etnonitiliza);
        tvpaswod= findViewById(R.id.tvpaswod);
        tvnonitiliza = findViewById(R.id.tvnonitiliza);
        progressBar = findViewById(R.id.progressBar);
        //btSignIn=findViewById(R.id.bt_sign_in);

      /* final Button login_button = findViewById(R.id.login_button);
         login_button.setOnClickListener(v -> {
           final ProgressDialog dialog = new ProgressDialog(this);
           dialog.setTitle("Please, wait a moment.");
           dialog.setMessage("Logging in...");
           dialog.show();
           Collection<String> permissions = Arrays.asList("public_profile", "email");

        ParseFacebookUtils.logInWithReadPermissionsInBackground(this, permissions, (user, err) -> {
              dialog.dismiss();
              if (err != null) {
                  Log.e("FacebookLoginExample", "done: ", err);
                  Toast.makeText(this, err.getMessage(), Toast.LENGTH_LONG).show();
               } else if (user == null) {
                   Toast.makeText(this, "The user cancelled the Facebook login.", Toast.LENGTH_LONG).show();
              } else if (user.isNew()) {
                  Toast.makeText(this, "User signed up and logged in through Facebook.", Toast.LENGTH_LONG).show();
                   Log.d("FacebookLoginExample", "User signed up and logged in through Facebook!");
                   getUserDetailFromFB();
                } else {
                    Toast.makeText(this, "User logged in through Facebook.", Toast.LENGTH_LONG).show();
                   Log.d("FacebookLoginExample", "User logged in through Facebook!");
                   showAlert("Oh, you!", "Welcome back!");
               }
               });
       });*/


////////////////////facebook/////////////////////////
        if(ActivityCompat.checkSelfPermission(LoginActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(LoginActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(LoginActivity.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
            btKonekte.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String username = etnonitiliza.getText().toString();
                    String password = etpaswod.getText().toString();
                    Log.i(TAG, "Click on Login button");

                    loginUser(username,password);

                }

                private void loginUser(String username, String password) {

//                progressDialog.show();



                    Log.i(TAG,"Trying to login user" +username);
                    //Navigate to the main activity if the user has signed properly
                    ParseUser.logInInBackground(username, password, new LogInCallback() {

                        @Override
                        public void done(ParseUser user, ParseException e) {
                            progressBar.setVisibility(View.VISIBLE);
                            if(e!=null){

                                ParseUser.logOut();
                                showAlert("Login Fail", e.getMessage() + " Please try again", true);
                                Log.e(TAG, "Issue with Login",e);
                                return;
                            }

                            goMainActivity();
                            showAlert("Login Successful", "Welcome, " + username + "!", false);
                        }
                    });
                }
            });

        }


        tvenskri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(i);

            }
        });

    }

    private void goMainActivity() {

        Intent i = new Intent(LoginActivity.this, ListTaskActivity.class);
        startActivity(i);

        finish();;
    }


    private void showAlert(String title, String message, boolean error) {
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", (dialog, which) -> {
                    dialog.cancel();
                    // don't forget to change the line below with the names of your Activities
                    if (!error) {
                        return;
                    }
                });
        AlertDialog ok = builder.create();
        ok.show();
    }



//    /////facebook
 /* private void getUserDetailFromFB() {
      GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), (object, response) -> {
           ParseUser user = ParseUser.getCurrentUser();
          try {
              if (object.has("name"))
                 user.setUsername(object.getString("name"));
             if (object.has("email"))
                   user.setEmail(object.getString("email"));
           } catch (JSONException e) {
               e.printStackTrace();
           }
            user.saveInBackground(e -> {
                if (e == null) {
                   showAlert("First Time Login!", "Welcome!");
              } else
                   showAlert("Error", e.getMessage());
            });
        });

        Bundle parameters = new Bundle();
        parameters.putString("fields", "name,email");
        request.setParameters(parameters);
       request.executeAsync();
    }

   private void showAlert(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", (dialog, which) -> {
                  dialog.cancel();
                  Intent intent = new Intent(this, ListTaskActivity.class);
                  intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                  startActivity(intent);
               });
       AlertDialog ok = builder.create();
       ok.show();
   }
   @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
       ParseFacebookUtils.onActivityResult(requestCode, resultCode, data);
  }*/

}