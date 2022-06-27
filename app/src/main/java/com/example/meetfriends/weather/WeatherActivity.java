
package com.example.meetfriends.weather;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.meetfriends.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;

public class WeatherActivity extends AppCompatActivity {

    Button getBut;
    EditText city;
    TextView results;

    private final String url = "https://api.openweathermap.org/data/2.5/weather"; //burayı yap
    private final String appid = "????????????????"; //burayı da openweather api üzerinden yap.
    DecimalFormat decimalFormat = new DecimalFormat("#.##"); //mesela 3.32 gibi format...


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        getBut = findViewById(R.id.getBut);
        city = findViewById(R.id.cityName);
        results = findViewById(R.id.results);

        getBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String tempUrl = "";
                String cityName = city.getText().toString().trim();

                if(city.equals("")){ //şehir boşsa
                    results.setText("Burası boş olamaz.");
                }else{

                    tempUrl = url + "?q=" + cityName + "&appid=" + appid;

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, tempUrl, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            String output = "";
                            try {
                                JSONObject jsonResponse = new JSONObject(response);
                                JSONArray jsonArray = jsonResponse.getJSONArray("weather");
                                JSONObject jsonObjectWeather = jsonArray.getJSONObject(0);
                                String description = jsonObjectWeather.getString("description");
                                JSONObject jsonObjectMain = jsonResponse.getJSONObject("main");
                                double temp = jsonObjectMain.getDouble("temp") - 273.15; //Celcius için(Kelvin)
                                double feelsLike = jsonObjectMain.getDouble("feels_like") - 273.15;
                                float pressure = jsonObjectMain.getInt("pressure");
                                int humidity = jsonObjectMain.getInt("humidity");
                                JSONObject jsonObjectWind = jsonResponse.getJSONObject("wind");
                                String wind = jsonObjectWind.getString("speed");
                                JSONObject jsonObjectClouds = jsonResponse.getJSONObject("clouds");
                                String clouds = jsonObjectClouds.getString("all");
                                JSONObject jsonObjectSys = jsonResponse.getJSONObject("sys");
                                String countryName = jsonObjectSys.getString("country");
                                String cityName = jsonResponse.getString("name");

                                output += " Durum Sorgulaması: " + cityName + " (" + countryName + ")"
                                        + "\n Sıcaklık: " + decimalFormat.format(temp) + " °C"
                                        + "\n Hissedilen Sıcaklık: " + decimalFormat.format(feelsLike) + " °C"
                                        + "\n Nem oranı: " + humidity + "%"
                                        + "\n Durum: " + description
                                        + "\n Rüzgar hızı: " + wind + "m/s"
                                        + "\n Bulut oranı: " + clouds + "%"
                                        + "\n Basınç: " + pressure + " hPa";
                                results.setText(output);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getApplicationContext(),error.toString().trim(),Toast.LENGTH_SHORT).show();
                        }
                    });
                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    requestQueue.add(stringRequest);

                }


            }
        });


//oncreate sonu
    }

//sınıf sonu
}