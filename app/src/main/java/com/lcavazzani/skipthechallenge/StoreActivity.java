package com.lcavazzani.skipthechallenge;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.text.Html;
import android.view.MenuItem;
import android.widget.TextView;

import com.lcavazzani.skipthechallenge.adapters.InsideStoreAdapter;
import com.lcavazzani.skipthechallenge.adapters.StoresListAdapter;
import com.lcavazzani.skipthechallenge.helpers.SmoothRecyclerView;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class StoreActivity extends AppCompatActivity {
    TextView _id;
    String intentId;
    SmoothRecyclerView ProductsRV;
    InsideStoreAdapter insideStoreAdapter;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);
        ProductsRV = findViewById(R.id.store_product_list);
        GridLayoutManager storeLM = new GridLayoutManager(this,1, GridLayoutManager.VERTICAL,false);
        ProductsRV.setLayoutManager(storeLM);
        ProductsRV.setItemAnimator(new DefaultItemAnimator());
        _id= findViewById(R.id._id);

        Intent intent = getIntent();
        intentId = intent.getStringExtra("id");
        _id.setText(intentId);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle(Html.fromHtml("<font color=\"white\">" +"Select your dish" + "</font>"));

        getStores();
    }
    private void getStores() {
         pd = ProgressDialog.show(StoreActivity.this, "", "Loading Dishes...",true);

        String stringUrl = getString(R.string.host) + getString(R.string.open_store);
        stringUrl = stringUrl.replace(":store_id", intentId);

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
                            updateStores(jsonData);
                            pd.dismiss();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }
    private void updateStores (String jsonData) throws JSONException, IOException {

        JSONArray rootArray = new JSONArray(jsonData);

        insideStoreAdapter = new InsideStoreAdapter (StoreActivity.this, rootArray);

        ProductsRV.setFocusable(false);

        ProductsRV.setAdapter(insideStoreAdapter);
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
