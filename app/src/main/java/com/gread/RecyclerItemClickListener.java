package com.gread;

/**
 * Created by ISHAN_GUPTA on 3/18/2017.
 * Handles touch event on images.
 */

import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import  android.view.MotionEvent;
import android.view.View;
import android.content.Context;
import android.widget.AdapterView;

public class RecyclerItemClickListener implements RecyclerView.OnItemTouchListener {

    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        public void onItemClick (View view, int position);
    }

    GestureDetector mGestureDetector;

    public RecyclerItemClickListener(Context context, OnItemClickListener listener){
        mListener = listener;
        mGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener(){
            public boolean onSingleTapUp (MotionEvent e){
                return true;
            }
        });
    }
    @Override
    public boolean onInterceptTouchEvent(RecyclerView view, MotionEvent e) {
        View childView = view.findChildViewUnder(e.getX(), e.getY());
        if(childView != null && mListener != null && mGestureDetector.onTouchEvent(e)){
            mListener.onItemClick(childView, view.getChildAdapterPosition(childView));
        }
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }
}
