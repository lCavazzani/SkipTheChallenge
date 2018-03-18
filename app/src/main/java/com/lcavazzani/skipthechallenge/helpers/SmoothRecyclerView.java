package com.lcavazzani.skipthechallenge.helpers;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

public class SmoothRecyclerView extends RecyclerView {

    Context context;

    public SmoothRecyclerView(Context context) {
        super(context);
        this.context = context;
    }

    public SmoothRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SmoothRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean fling(int velocityX, int velocityY) {

        velocityX *= 0.5;
        // velocityX *= 0.7; for Horizontal recycler view. comment velocityY line not require for Horizontal Mode.

        return super.fling(velocityX, velocityY);
    }


}