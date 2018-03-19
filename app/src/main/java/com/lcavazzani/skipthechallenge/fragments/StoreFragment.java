package com.lcavazzani.skipthechallenge.fragments;

/**
 * Created by leonardoCavazzani on 3/18/18.
 */
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lcavazzani.skipthechallenge.R;
import com.lcavazzani.skipthechallenge.adapters.StoresListAdapter;
import com.lcavazzani.skipthechallenge.helpers.SmoothRecyclerView;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class StoreFragment extends Fragment {
    SmoothRecyclerView StoresRV;
    StoresListAdapter storesListAdapter;
    ProgressDialog pd;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_store_layout, container, false);
        StoresRV= rootView.findViewById(R.id.storesList);
        GridLayoutManager storeLM = new GridLayoutManager(getActivity(),1, GridLayoutManager.VERTICAL,false);
        StoresRV.setLayoutManager(storeLM);
        StoresRV.setItemAnimator(new DefaultItemAnimator());
        getStores();
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {



    }
    private void getStores() {
         pd = ProgressDialog.show(getContext(), "", "Loading...",true);

        String stringUrl = getString(R.string.host) + getString(R.string.stores_list);

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

        storesListAdapter = new StoresListAdapter(getContext(), rootArray);

        StoresRV.setFocusable(false);

        StoresRV.setAdapter(storesListAdapter);
    }


}