package com.supertg.supertg.ui.pic.view;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.orhanobut.logger.Logger;
import com.supertg.supertg.R;
import com.supertg.supertg.util.barutils.ImmersiveUtil;
import com.supertg.supertg.util.InOutAnimationUtils;
import com.supertg.supertg.util.share.Shares;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by xiongxing on 2017/1/17.
 */

public class PictureActivity extends AppCompatActivity {
    public static final String TRANSIT_PIC = "picture";
    public static final String EXTRA_IMAGE_URL = "picture_url";
    @BindView(R.id.picture)
    ImageView picture;
    String mImageUrl;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    PhotoViewAttacher photoViewAttacher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_back);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                supportFinishAfterTransition();
            }
        });
        parseIntent();
        ViewCompat.setTransitionName(picture, TRANSIT_PIC);
//        disPlayImage();
        Glide.with(this).load(mImageUrl).into(picture);
//        Picasso.with(this).load(mImageUrl).into(picture);
        setupPhotoAttacher();
    }

    /**
     * 单独练习AxAndroid的使用
     */
    private void disPlayImage() {
        Observable.just(mImageUrl)// 输入类型 String  IO 线程，由 subscribeOn() 指定
                .subscribeOn(Schedulers.io())
                .map(new Func1<String, File>() {
                    @Override
                    public File call(String s) {
                        return new File(mImageUrl);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<File>() {//Android 主线程，由 observeOn() 指定
                    @Override
                    public void call(File file) {
                        picture.setImageURI(Uri.fromFile(file));
                    }
                });
    }

    private void setupPhotoAttacher() {
        photoViewAttacher = new PhotoViewAttacher(picture);
        //处理单击事件
        photoViewAttacher.setOnViewTapListener(new PhotoViewAttacher.OnViewTapListener() {
            @Override
            public void onViewTap(View view, float x, float y) {
                if (toolbar.getVisibility() == View.VISIBLE) {
                    fadeOut();
                } else {
                    fadeIn();
                }
            }
        });
    }

    private void parseIntent() {
        mImageUrl = getIntent().getStringExtra(EXTRA_IMAGE_URL);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_pic, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_share) {
            Logger.e("mImageUrl==" + mImageUrl);
            File file = new File(mImageUrl);
            Shares.shareImage(this, Uri.fromFile(file), getString(R.string.share_meizhi_to));
            return true;
        } else if (item.getItemId() == R.id.action_save) {


            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    void fadeIn() {
        InOutAnimationUtils.animateIn(toolbar, R.anim.viewer_toolbar_fade_in);
        ImmersiveUtil.exit(this);
    }

    void fadeOut() {
        InOutAnimationUtils.animateOut(toolbar, R.anim.viewer_toolbar_fade_out);
        ImmersiveUtil.enter(this);
    }

    @Override
    public void onBackPressed() {
        photoViewAttacher.cleanup();
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        photoViewAttacher.cleanup();
    }
}
