package com.lcavazzani.skipthechallenge.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lcavazzani.skipthechallenge.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



/**
 * Created by leonardoCavazzani on 2/22/17.
 */

public class ProductListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    JSONArray mPages;




    public ProductListAdapter(Context context, JSONArray pages){
        mContext = context;
        mPages = pages;

    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.activity_list_products, parent, false);
        MyHolder holder=new MyHolder(view);
        return holder;

    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        MyHolder myHolder= (MyHolder) holder;

        try {
            JSONObject mpackid = mPages.getJSONObject(position);

            myHolder.suggestionImageViewScreen.setVisibility(View.INVISIBLE);
            myHolder.suggestionImageViewFree.setVisibility(View.INVISIBLE);
            myHolder.suggestionImageViewPrice.setVisibility(View.INVISIBLE);

            //Do whatever you want here

            if(mpackid.optInt("price_credits")==0){
                myHolder.suggestionImageViewFree.setVisibility(View.VISIBLE);
            }else if(mpackid.optInt("price_credits")==10) {
                myHolder.suggestionImageViewPrice.setVisibility(View.VISIBLE);
            }

//            String murl = mpackid.optString("file_full");
//            myHolder.url.setText(murl);

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
        ImageView icon;
        TextView itemName;
        TextView url;
    }
    class MyHolder extends RecyclerView.ViewHolder{

        RecyclerView suggestionImageView;
        ImageView suggestionImageViewScreen;
        ImageView suggestionImageViewPrice;
        ImageView suggestionImageViewFree;


        // create constructor to get widget reference
        public MyHolder(View itemView) {
            super(itemView);

            suggestionImageView = (RecyclerView) itemView.findViewById(R.id.product_list);
//            suggestionImageViewScreen = (ImageView) itemView.findViewById(R.id.suggestionImageViewScreen);
//            suggestionImageViewPrice = (ImageView) itemView.findViewById(R.id.suggestionImageViewPrice);
//            suggestionImageViewFree = (ImageView) itemView.findViewById(R.id.suggestionImageViewFree);

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