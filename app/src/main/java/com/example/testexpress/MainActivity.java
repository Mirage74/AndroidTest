package com.example.testexpress;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;




import com.google.android.material.snackbar.Snackbar;

import androidx.core.view.WindowCompat;
//import androidx.navigation.NavController;
//
//
//import androidx.navigation.Navigation;
//import androidx.navigation.ui.AppBarConfiguration;
//import androidx.navigation.ui.NavigationUI;
//
//import com.test.menu.databinding.ActivityMainBinding;

import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


class countryDescribe implements Serializable {
    int id;

    public countryDescribe() {
    }

    String countryName;

    public void setId(int id) {
        this.id = id;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public void setCapitalName(String capitalName) {
        this.capitalName = capitalName;
    }

    public void setDiffLvl(int diffLvl) {
        this.diffLvl = diffLvl;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public countryDescribe(int id, String countryName, String capitalName, int diffLvl, String imageName) {
        this.id = id;
        this.countryName = countryName;
        this.capitalName = capitalName;
        this.diffLvl = diffLvl;
        this.imageName = imageName;
    }

    @Override
    public String toString() {
        return "countryDescribe{" +
                "id=" + id +
                ", countryName='" + countryName + '\'' +
                ", capitalName='" + capitalName + '\'' +
                ", diffLvl=" + diffLvl +
                ", imageName='" + imageName + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public String getCountryName() {
        return countryName;
    }

    public String getCapitalName() {
        return capitalName;
    }

    public int getDiffLvl() {
        return diffLvl;
    }

    public String getImageName() {
        return imageName;
    }

    String capitalName;
    int diffLvl;
    String imageName;
}

public class MainActivity extends AppCompatActivity {


    EditText name, pass;
    Button buttonLogin, buttonOtherActivity;
    String name1, pass1, email1, link, reply, code;
    ImageView eye;
    boolean state = false;

    //private static final String GET_URL = "http://192.168.1.11:4000/api/get";
    private static final String GET_URL = "http://10.0.2.2:4000/api/get";
    private static final String POST_LOGIN_USER = "http://10.0.2.2:4000/api/login";
    //private static final String POST_LOGIN_USER = "http://192.168.1.11:4000/api/login";


//    private AppBarConfiguration appBarConfiguration;
//    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


//        binding = ActivityMainBinding.inflate(getLayoutInflater());
//        setContentView(binding.getRoot());
//
//        setSupportActionBar(binding.toolbar);
//
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
//        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
//
//        binding.fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAnchorView(R.id.fab)
//                        .setAction("Action", null).show();
//            }
//        });





        name = findViewById(R.id.user1);
        pass = findViewById(R.id.pass1);
        buttonLogin = findViewById(R.id.login);
        buttonOtherActivity = findViewById(R.id.button_other_activity);

        eye = findViewById(R.id.toggle_view1);
        name1 = "";
        pass1 = "";

        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
            //your codes here

        }
        String lineRes = getCountryList();
        ArrayList<countryDescribe> countryList = new ArrayList<>();
        parseString(countryList, lineRes);
        System.out.println("countryList.get(3) : " + countryList.get(3));

        View.OnClickListener onClickListener = v -> {
            if (v.getId() == R.id.login) {
                login();
            } else if (v.getId() == R.id.button_other_activity) {
                Intent intent ;
                intent = new Intent("android.intent.action.express");
                //intent.putParcelableArrayListExtra("al", (ArrayList)countryList);
                //System.out.println("countryList main : " + countryList);
                intent.putExtra("al", countryList);

                startActivity(intent);
            }

        };
        buttonLogin.setOnClickListener(onClickListener);
        buttonOtherActivity.setOnClickListener(onClickListener);
    }



    private String cutGetStr(String sIn) {
        String s = "";
        int i = sIn.indexOf("[");
        int j = sIn.indexOf("]");
        s = sIn.substring(i, j);
        return s;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

//    @Override
//    public boolean onSupportNavigateUp() {
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
//        return NavigationUI.navigateUp(navController, appBarConfiguration)
//                || super.onSupportNavigateUp();
//    }

    private String getUserNameFromBackendResponse(String sIn) {
        String s = "";
        if (sIn.indexOf("CODE LOGIN_USER_02") == 1) {
            return (sIn);
        } else {

            int i = sIn.indexOf(":");
            int j = sIn.indexOf("}");
            s = sIn.substring(i + 2, j - 1);
            s = s.substring(0, 1).toUpperCase() + s.substring(1);
            return s;
        }
    }

    private void parseString(List<countryDescribe> countryList, String sIn) {
        boolean isFinished = false;
        int cnt = 0, i, j;
        String s, sTemp, cntName, capName, imgName;
        int id, diffLvl;
        countryDescribe cD;
        s = sIn;
        while (!isFinished) {
            i = s.indexOf("{");
            if (i > -1) {
                i += 6;
                j = s.indexOf(",");
                id = Integer.parseInt(s.substring(i, j));

                s = s.substring(j + 1);
                i = s.indexOf("countryName");
                i += 14;
                j = s.indexOf(",");
                cntName = s.substring(i, j - 1);

                s = s.substring(j + 1);
                i = s.indexOf("capitalName");
                i += 14;
                j = s.indexOf(",");
                capName = s.substring(i, j - 1);

                s = s.substring(j + 1);
                i = s.indexOf("diffLvl");
                i += 9;
                j = s.indexOf(",");
                diffLvl = Integer.parseInt(s.substring(i, j));

                s = s.substring(j + 1);
                i = s.indexOf("imageName");
                i += 12;
                j = s.indexOf(".jpg") + 4;
                imgName = s.substring(i, j);


                cD = new countryDescribe(id, cntName, capName, diffLvl, imgName);
                countryList.add(cD);
//                }
                i = s.indexOf("{");
                if (i > 10) {
                    s = s.substring(i);
                }

                if (i < 0 || s.length() < 10) {
                    isFinished = true;
                }

            } else {
                isFinished = true;
            }


        }
    }

    private String getCountryList() {
        String lineRes = "";
        URL url;
        try {
            url = new URL(GET_URL);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);

        }
        HttpURLConnection con;
        try {
            con = (HttpURLConnection) url.openConnection();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            con.setRequestMethod("GET");
        } catch (ProtocolException e) {
            throw new RuntimeException(e);
        }
        try {
            int responseCode = con.getResponseCode();
            if (responseCode != 200) {
                throw new IOException("Invalid response from server: " + responseCode);
            }

            BufferedReader rd = new BufferedReader(new InputStreamReader(
                    con.getInputStream()));
            String line;
            while ((line = rd.readLine()) != null) {
                lineRes += line;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        lineRes = cutGetStr(lineRes);
        return lineRes;
    }

    private String postLoginUser(String login, String password) {
        StringBuilder response = new StringBuilder();
        String jsonInputString = "{\"login\" : \"" + login + "\"" + ", \"password\" : \"" + password + "\"}";
        //System.out.println("jsonInputString : " + jsonInputString);
        URL url;
        try {
            url = new URL(POST_LOGIN_USER);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);

        }
        HttpURLConnection con;
        try {
            con = (HttpURLConnection) url.openConnection();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Accept", "application/json");
            con.setDoOutput(true);
        } catch (ProtocolException e) {
            throw new RuntimeException(e);
        }

        try (OutputStream os = con.getOutputStream()) {
            byte[] input = jsonInputString.getBytes("utf-8");
            os.write(input, 0, input.length);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try (BufferedReader br = new BufferedReader(

                new InputStreamReader(con.getInputStream(), "utf-8"))) {
            String responseLine = null;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            System.out.println(response.toString());
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return response.toString();
    }




    // Method to start registration activity.
    public void register(View view) {
        Intent register = new Intent(this, Register.class);
        startActivity(register);
    }

    // Method for login.
    //public void login(View v) {
    public void login() {
        // Get the inputs from the user.
        name1 = name.getText().toString();
        pass1 = pass.getText().toString();
        //email1 = email.getText().toString();

        if (name1.isEmpty() || pass1.isEmpty()) {
            Toast.makeText(MainActivity.this, "Fields cannot be blank", Toast.LENGTH_SHORT).show();  // Check whether the fields are not blank
        } else {
            // Create various messages to display in the app.
            Toast failed_login = Toast.makeText(MainActivity.this, "Login failed", Toast.LENGTH_SHORT);
            Toast success_login = Toast.makeText(MainActivity.this, "Login success", Toast.LENGTH_SHORT);
            Toast logged_toast = Toast.makeText(MainActivity.this, "Logged in", Toast.LENGTH_SHORT);
            String getLoginResponse = postLoginUser(name1, pass1);
            String user = getUserNameFromBackendResponse(getLoginResponse);
            if (user == getLoginResponse) {
                System.out.println("user Login failed : " + user);
                failed_login.show();
            } else {
                System.out.println("user : " + user);
                success_login.show();
            }

        }
    }

    // For toggling visibility of password.
    public void toggle(View v) {
        if (!state) {
            pass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            pass.setSelection(pass.getText().length());
            eye.setImageResource(R.drawable.eye);
        } else {
            pass.setTransformationMethod(PasswordTransformationMethod.getInstance());
            pass.setSelection(pass.getText().length());
            eye.setImageResource(R.drawable.eye_off);
        }
        state = !state;
    }


}