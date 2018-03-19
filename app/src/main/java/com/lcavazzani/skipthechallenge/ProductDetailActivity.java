package com.lcavazzani.skipthechallenge;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.lcavazzani.skipthechallenge.adapters.InsideStoreAdapter;
import com.lcavazzani.skipthechallenge.helpers.SmoothRecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by leonardoCavazzani on 3/18/18.
 */

public class ProductDetailActivity extends AppCompatActivity {
    TextView _id;
    String intentId;
    TextView productName, productDetail, productPrice, amount, totalPrice;
    Button btnSubtraction, btnAdd;
    double price;
    int multiple;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_cart);


        productName = findViewById(R.id.productName);
        productDetail = findViewById(R.id.productDetail);
        productPrice = findViewById(R.id.productPrice);
        amount = findViewById(R.id.amount);
        totalPrice = findViewById(R.id.totalPrice);
        btnSubtraction = findViewById(R.id.btnSubtraction);
        btnAdd = findViewById(R.id.btnAdd);

        multiple = Integer.parseInt((String) amount.getText());
        _id= findViewById(R.id._id);
        Intent intent = getIntent();
        intentId = intent.getStringExtra("product_id");
        _id.setText(intentId);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                multiple++;
                amount.setText(String.valueOf(multiple));
                changePrice(multiple, price);
            }
        });
        btnSubtraction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(multiple>0){
                    multiple--;
                    amount.setText(String.valueOf(multiple));
                    changePrice(multiple, price);
                }
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle(Html.fromHtml("<font color=\"white\">" +"How many?" + "</font>"));



        getStores();
    }
    private void getStores() {
        // pd = ProgressDialog.show(getContext(), "", "Carregando...",true);

        String stringUrl = getString(R.string.host) + getString(R.string.product_detail);
        stringUrl = stringUrl.replace(":product_id", intentId);

        RequestBody formBody = new FormBody.Builder()
                .build();

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(stringUrl)
                .build();
        Call call = client.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                // pd.dismiss();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
//                Log.i(TAG, "response: " + response.toString());
                final String jsonData = response.body().string();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject jsonObject = new JSONObject(jsonData);
                            productName.setText(jsonObject.optString("name"));
                            productDetail.setText(jsonObject.optString("description"));
                            productPrice.setText(jsonObject.optString("price"));
                            price = jsonObject.optDouble("price");
                            changePrice(multiple, price);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });
            }
        });
    }


    private void changePrice(int multiple, double price){
        String total = String.valueOf(multiple * price);
        totalPrice.setText("TOTAL: C$"+total);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
