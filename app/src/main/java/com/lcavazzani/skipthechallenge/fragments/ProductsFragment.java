package com.lcavazzani.skipthechallenge.fragments;

/**
 * Created by leonardoCavazzani on 3/18/18.
 */
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lcavazzani.skipthechallenge.R;
import com.lcavazzani.skipthechallenge.adapters.ProductListAdapter;
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

public class ProductsFragment extends Fragment {
    SmoothRecyclerView ProductsRV;
    ProductListAdapter productsListAdapter;
    ProgressDialog pd;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_products_layout, container, false);
        ProductsRV= rootView.findViewById(R.id.productsList);
        GridLayoutManager storeLM = new GridLayoutManager(getActivity(),1, GridLayoutManager.VERTICAL,false);
        ProductsRV.setLayoutManager(storeLM);
        ProductsRV.setItemAnimator(new DefaultItemAnimator());
        getProducts();
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {



    }
    private void getProducts() {
        pd = ProgressDialog.show(getContext(), "", "Loading...",true);

        String stringUrl = getString(R.string.host) + getString(R.string.products_list);

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

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            updateProducts(jsonData);
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
    private void updateProducts (String jsonData) throws JSONException, IOException {

        JSONArray rootArray = new JSONArray(jsonData);

        productsListAdapter = new ProductListAdapter(getContext(), rootArray);

        ProductsRV.setFocusable(false);

        ProductsRV.setAdapter(productsListAdapter);
    }


}