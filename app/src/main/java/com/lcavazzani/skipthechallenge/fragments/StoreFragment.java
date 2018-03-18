package com.lcavazzani.skipthechallenge.fragments;

/**
 * Created by leonardoCavazzani on 3/18/18.
 */
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lcavazzani.skipthechallenge.R;
import com.lcavazzani.skipthechallenge.adapters.StoresListAdapter;

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

public class StoreFragment extends Fragment {
    RecyclerView StoresRV;
    StoresListAdapter storesListAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        GridLayoutManager storeLM = new GridLayoutManager(getActivity(),1, GridLayoutManager.VERTICAL,false);

        StoresRV= (RecyclerView) container.findViewById(R.id.storesList);
        StoresRV.setLayoutManager(storeLM);
        StoresRV.setItemAnimator(new DefaultItemAnimator());

        getStores();

        return inflater.inflate(R.layout.fragment_store_layout, container, false);
    }
    private void getStores() {
        // pd = ProgressDialog.show(getContext(), "", "Carregando...",true);

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