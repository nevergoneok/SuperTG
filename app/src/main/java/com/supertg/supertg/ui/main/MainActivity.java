package com.supertg.supertg.ui.main;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.orhanobut.logger.Logger;
import com.supertg.supertg.R;
import com.supertg.supertg.adapter.MeizhiListAdapter;
import com.supertg.supertg.eventbus.CommonEvent;
import com.supertg.supertg.interfaces.OnMeizhiTouchListener;
import com.supertg.supertg.mvpframe.base.BaseFrameToolbarActivity;
import com.supertg.supertg.util.ActivitySwitcher;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 对应于Activity，负责View的绘制以及与用户交互
 */
public class MainActivity extends BaseFrameToolbarActivity<MainPresenter, MainModel> implements MainContract.View {

    @BindView(R.id.list)
    RecyclerView mRecyclerView;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }


    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onCommonEvent(CommonEvent event) {
        if (event.type == CommonEvent.asyncRecycleMain) {
            switch (event.what) {
                case CommonEvent.REQUESTSUEECCD:
                    setRecycleView(event);
                    break;
                case CommonEvent.REQUESTFAILUE:
                case CommonEvent.LINKFAILUE:
                    break;
                default:
                    break;
            }
        }
    }

    private void setRecycleView(CommonEvent event) {
        //瀑布流布局
        final StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        MeizhiListAdapter meizhiListAdapter = new MeizhiListAdapter(MainActivity.this, (List<String>) event.object);
        mRecyclerView.setAdapter(meizhiListAdapter);
        meizhiListAdapter.setOnMeizhiTouchListener(new OnMeizhiTouchListener() {
            @Override
            public void onTouch(View meizhiView, String meizhi, List<String> mList) {
                ActivitySwitcher.startPictureActivity(MainActivity.this, meizhi, meizhiView);
            }
        });
    }

    @Override
    protected void initData() {
        mPresenter.getMeiZiList(this, 1, lifecycleSubject);
    }

    @Override
    protected void initListener() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_about:
                ActivitySwitcher.goAboutAct(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showLoading() {
        Logger.e("showLoading");
    }

    @Override
    public void hideLoading() {
        Logger.e("hideLoading");
    }
}
