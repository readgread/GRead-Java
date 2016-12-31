package com.example.gread.gread;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.Collections;
import java.util.List;

/**
 * Created by babayega on 29/12/16.
 */

public class CommasAdapter extends RecyclerView.Adapter<CommasHolder> {
    LayoutInflater cardInflater;
    List<ImageParser> images = Collections.emptyList();

    public CommasAdapter(Context context, List<ImageParser> myImages)
    {
        cardInflater = LayoutInflater.from(context);
        images=myImages;
    }

    @Override
    public CommasHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = cardInflater.inflate(R.layout.card_layout, parent, false);
        CommasHolder commasHolder = new CommasHolder(v);
        return commasHolder;
    }

    @Override
    public void onBindViewHolder(CommasHolder holder, int position) {
        ImageParser imageParser = images.get(position);
        System.out.println(position);
        Uri imageUri = Uri.parse(imageParser.imageURL);
        holder.commasImage.setImageURI(imageUri);
    }

    @Override
    public int getItemCount() {
        return images.size();
    }
}


class CommasHolder extends RecyclerView.ViewHolder{
    SimpleDraweeView commasImage;

    public CommasHolder(View cardView){
        super(cardView);
        commasImage = (SimpleDraweeView) cardView.findViewById(R.id.cardview_image);
    }
}