package com.lcavazzani.skipthechallenge.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lcavazzani.skipthechallenge.ProductDetailActivity;
import com.lcavazzani.skipthechallenge.R;
import com.lcavazzani.skipthechallenge.StoreActivity;

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

        View view = LayoutInflater.from(mContext).inflate(R.layout.item_store_list, parent, false);
        ProductListAdapter.MyHolder holder=new ProductListAdapter.MyHolder(view);

        return holder;
    }
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        ProductListAdapter.MyHolder myHolder= (ProductListAdapter.MyHolder) holder;

        try {

            JSONObject mpackid = mPages.getJSONObject(position);

            myHolder.nameStore.setText(mpackid.optString("name"));
            myHolder.addressStore.setText(mpackid.optString("description"));
            myHolder._id.setText(mpackid.optString("id"));

            myHolder.fulllayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TextView _id = (TextView) v.findViewById(R.id._id);
                    String final_id = _id.getText().toString();
                    Intent intent = new Intent(mContext, ProductDetailActivity.class);
                    intent.putExtra("product_id", final_id);
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