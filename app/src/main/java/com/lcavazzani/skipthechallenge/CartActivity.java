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

import com.lcavazzani.skipthechallenge.adapters.CartListAdapter;
import com.lcavazzani.skipthechallenge.adapters.InsideStoreAdapter;
import com.lcavazzani.skipthechallenge.helpers.SmoothRecyclerView;
import com.lcavazzani.skipthechallenge.models.User;

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

public class CartActivity extends AppCompatActivity {
    TextView _id;
    String intentId;
    SmoothRecyclerView ProductsRV;
    CartListAdapter cartListAdapter;
    ProgressDialog pd;
    User user = User.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_layout);
        ProductsRV = findViewById(R.id.mCartList);
        GridLayoutManager cartLM = new GridLayoutManager(this, 1, GridLayoutManager.VERTICAL, false);
        ProductsRV.setLayoutManager(cartLM);
        ProductsRV.setItemAnimator(new DefaultItemAnimator());
        _id = findViewById(R.id._id);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle(Html.fromHtml("<font color=\"white\">" + "Cart" + "</font>"));
       // getCart();
        try {
            uptadeCart();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void uptadeCart() throws JSONException, IOException {

        JSONArray rootArray = user.getmCart();

        cartListAdapter = new CartListAdapter(CartActivity.this, rootArray);

        ProductsRV.setFocusable(false);

        ProductsRV.setAdapter(cartListAdapter);
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