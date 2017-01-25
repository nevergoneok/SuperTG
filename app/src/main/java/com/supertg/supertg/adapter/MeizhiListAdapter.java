package com.supertg.supertg.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.squareup.picasso.Picasso;
import com.supertg.supertg.R;
import com.supertg.supertg.imageloader.ImageLoader;
import com.supertg.supertg.interfaces.OnMeizhiTouchListener;
import com.supertg.supertg.widget.RatioImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xiongxing on 2017/1/16.
 */

public class MeizhiListAdapter extends RecyclerView.Adapter<MeizhiListAdapter.ViewHolder> {

    private List<String> mUrlList;
    private Context mContext;
    private OnMeizhiTouchListener mOnMeizhiTouchListener;

    public MeizhiListAdapter(Context context, List<String> urlList) {
        mUrlList = urlList;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_gride_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        try {
            Bitmap bitmap = Picasso.with(mContext).load(mUrlList.get(position)).get();
//         int sdCardImageWidth = BitmapUtils.getSDCardImageWidth(imageItem.sourcePath);
//        int sdCardImageHeight = BitmapUtils.getSDCardImageHeight(imageItem.sourcePath);
            holder.image.setOriginalSize(bitmap.getWidth(), bitmap.getHeight());
        } catch (Exception e) {
            e.printStackTrace();
        }
        ImageLoader.getInstance().displayImage(mContext,mUrlList.get(position),holder.image);
        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnMeizhiTouchListener.onTouch(holder.image, mUrlList.get(position), mUrlList);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mUrlList.size();
    }

    public void setOnMeizhiTouchListener(OnMeizhiTouchListener onMeizhiTouchListener) {
        this.mOnMeizhiTouchListener = onMeizhiTouchListener;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.image)
        RatioImageView image;
        @BindView(R.id.ll_image)
        LinearLayout llImage;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
