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

public class CartListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    JSONArray mPages;




    public CartListAdapter(Context context, JSONArray pages){
        mContext = context;
        mPages = pages;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.item_cart_list, parent, false);
        CartListAdapter.MyHolder holder=new CartListAdapter.MyHolder(view);

        return holder;
    }
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        CartListAdapter.MyHolder myHolder= (CartListAdapter.MyHolder) holder;

        try {

            JSONObject mpackid = mPages.getJSONObject(position);

            myHolder.nameProduct.setText(mpackid.optString("name"));
            myHolder.descriptionProduct.setText(mpackid.optString("description"));
            myHolder.amount.setText("Amount "+mpackid.optString("amount"));
            myHolder.totalPrice.setText("Total: C$ "+mpackid.optString("totalPrice"));
            myHolder.unityPrice.setText("per unity "+mpackid.optString("unityPrice"));
            myHolder._id.setText(mpackid.optString("id"));
//
//            myHolder.fulllayout.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    TextView _id = (TextView) v.findViewById(R.id._id);
//                    String final_id = _id.getText().toString();
//                    Intent intent = new Intent(mContext, ProductDetailActivity.class);
//                    intent.putExtra("product_id", final_id);
//                    mContext.startActivity(intent);
//                }
//            });



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

        TextView nameProduct, descriptionProduct, amount, unityPrice, totalPrice;
        TextView _id;


        // create constructor to get widget reference
        public MyHolder(View itemView) {
            super(itemView);

            nameProduct =  itemView.findViewById(R.id.nameProduct);
            descriptionProduct =itemView.findViewById(R.id.descriptionStore);
            amount =itemView.findViewById(R.id.amount);
            unityPrice =itemView.findViewById(R.id.unityPrice);
            totalPrice =itemView.findViewById(R.id.totalPrice);
            _id = itemView.findViewById(R.id._id);

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