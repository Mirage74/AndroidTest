package com.example.testexpress;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


class countryDescribe {
    int id;
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
    private Button btnGET;
    private TextView textViewResult;
    //private static final String GET_URL = "http://localhost:4000/api";
    private static final String GET_URL = "http://10.0.2.2:4000/api/get";

    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
            //your codes here

        }
    }

    private String cutGetStr(String sIn) {
        String s = "";
        int i = sIn.indexOf("[");
        int j = sIn.indexOf("]");
        s = sIn.substring(i, j);
        return s;
    }

    private void parseString( List<countryDescribe> countryList, String sIn) {
        boolean isFinished = false;
        int cnt = 0, i, j;
        String s, sTemp, cntName, capName, imgName;
        int id, diffLvl;
        countryDescribe cD;
        s = sIn;
        while (!isFinished) {
            i = s.indexOf("{");
            if (i > -1) {
                i+=6;
                j = s.indexOf(",");
                id = Integer.parseInt(s.substring(i, j));

                s = s.substring(j + 1);
                i = s.indexOf("countryName");
                i+=14;
                j = s.indexOf(",");
                cntName = s.substring(i, j - 1);

                s = s.substring(j + 1);
                i = s.indexOf("capitalName");
                i+=14;
                j = s.indexOf(",");
                capName = s.substring(i, j - 1);

                s = s.substring(j + 1);
                i = s.indexOf("diffLvl");
                i+=9;
                j = s.indexOf(",");
                diffLvl = Integer.parseInt(s.substring(i, j));

                s = s.substring(j + 1);
                i = s.indexOf("imageName");
                i+=12;
                j = s.indexOf(".jpg") + 4;
                imgName = s.substring(i, j);


                cD = new countryDescribe(id, cntName, capName, diffLvl, imgName);
                countryList.add(cD);
                System.out.println(countryList.size());
//                if (countryList.size() == 193) {
//                    cnt = 3;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnGET = findViewById(R.id.btnGET);
        textViewResult = findViewById(R.id.textViewResult);


        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
            //your codes here

        }

        btnGET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String lineRes = "";
                JSONObject myObject;
                JSONArray array;
                String arr[];
                System.out.println("onClick");
                URL url = null;
                try {
                    url = new URL(GET_URL);
                } catch (MalformedURLException e) {
                    System.out.println("onClick 2");
                    throw new RuntimeException(e);

                }
                HttpURLConnection con = null;
                try {
                    con = (HttpURLConnection) url.openConnection();
                } catch (IOException e) {
                    System.out.println("onClick 3");
                    throw new RuntimeException(e);
                }
                try {
                    con.setRequestMethod("GET");
                } catch (ProtocolException e) {
                    System.out.println("onClick 3");
                    throw new RuntimeException(e);
                }
                //con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                try {
                    int responseCode = con.getResponseCode();
                    if (responseCode !=  200) {
                        throw new IOException("Invalid response from server: " + responseCode);
                    }

                    BufferedReader rd = new BufferedReader(new InputStreamReader(
                            con.getInputStream()));
                    String line;
                    while ((line = rd.readLine()) != null) {
                        lineRes += line;
                        //Log.i("data", line);
                    }                } catch (IOException e) {
                    System.out.println("onClick 4");

                    throw new RuntimeException(e);
                }


                lineRes = cutGetStr(lineRes);


                System.out.println("lineRes : " + lineRes);

                List<countryDescribe> countryList = new ArrayList<>();
                parseString(countryList, lineRes);

                System.out.println("countryList : " + countryList.get(4));



                //System.out.println("obj : " + obj);
                textViewResult.setText(countryList.get(3).countryName);
            }
        });
    }
}