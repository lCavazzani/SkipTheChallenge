package com.lcavazzani.skipthechallenge.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.lcavazzani.skipthechallenge.R;
import com.lcavazzani.skipthechallenge.StoreActivity;
import com.lcavazzani.skipthechallenge.fragments.StoreFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;


/**
 * Created by leonardoCavazzani on 2/22/17.
 */

public class StoresListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    JSONArray mPages;

    public StoresListAdapter(Context context, JSONArray pages){
        mContext = context;
        mPages = pages;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.item_store_list, parent, false);
        MyHolder holder=new MyHolder(view);

        return holder;
    }
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        MyHolder myHolder= (MyHolder) holder;

        try {

            JSONObject mpackid = mPages.getJSONObject(position);

            myHolder.nameStore.setText(mpackid.optString("name"));
            myHolder.addressStore.setText(mpackid.optString("address"));
            myHolder._id.setText(mpackid.optString("id"));

            myHolder.fulllayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TextView _id = (TextView) v.findViewById(R.id._id);
                    String final_id = _id.getText().toString();
                    Intent intent = new Intent(mContext, StoreActivity.class);
                    intent.putExtra("id", final_id);
                    mContext.startActivity(intent);
                }
            });



//            Glide.with(mContext).load(mpackid.optString("file_thumb"))
//                    .asBitmap()
//                    .thumbnail(Glide.with(mContext).load(R.drawable.ajax_loader).asBitmap())
//                    .fitCenter()
//                    .into(myHolder.suggestionImageView);

        } catch (JSONException e) {
            e.printStackTrace();

        }
    }

    @Override
    public long getItemId(int position) {
        return 0;// not using this
    }

    @Override
    public int getItemCount() {
        return mPages.length();
    }


    private static class ViewHolder{

    }
    class MyHolder extends RecyclerView.ViewHolder{

        TextView nameStore;
        TextView addressStore;
        TextView _id;
        LinearLayout fulllayout;


        // create constructor to get widget reference
        public MyHolder(View itemView) {
            super(itemView);

            nameStore = (TextView) itemView.findViewById(R.id.nameStore);
            addressStore = (TextView) itemView.findViewById(R.id.addressStore);
            _id = (TextView) itemView.findViewById(R.id._id);
            fulllayout = itemView.findViewById(R.id.storefulllayout);

        }

    }

    public JSONObject getItem(int position) {
        try {
            return mPages.getJSONObject(position);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

}