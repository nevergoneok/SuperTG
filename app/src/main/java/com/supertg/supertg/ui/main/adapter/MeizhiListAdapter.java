package com.supertg.supertg.ui.main.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.orhanobut.logger.Logger;
import com.supertg.supertg.R;
import com.supertg.supertg.ui.main.interfaces.OnMeizhiTouchListener;
import com.supertg.supertg.util.BitmapUtils;
import com.supertg.supertg.util.getPicAlbum.ImageItem;
import com.supertg.supertg.widget.RatioImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xiongxing on 2017/1/16.
 */

public class MeizhiListAdapter extends RecyclerView.Adapter<MeizhiListAdapter.ViewHolder> {

    private final List<ImageItem> mList;
    private final Context mContext;
    private OnMeizhiTouchListener mOnMeizhiTouchListener;

    public MeizhiListAdapter(Context context, List<ImageItem> meizhiList) {
        mList = meizhiList;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_gride_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final ImageItem imageItem = mList.get(position);
        int sdCardImageWidth = BitmapUtils.getSDCardImageWidth(imageItem.sourcePath);
        int sdCardImageHeight = BitmapUtils.getSDCardImageHeight(imageItem.sourcePath);
        holder.image.setOriginalSize(sdCardImageWidth, sdCardImageHeight);
        Glide.with(mContext).load(imageItem.sourcePath).into(holder.image);
        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnMeizhiTouchListener.onTouch(holder.image, imageItem, mList);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
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
