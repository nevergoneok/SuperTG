package com.supertg.supertg.ui.main;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.orhanobut.logger.Logger;
import com.supertg.supertg.R;
import com.supertg.supertg.mvpframe.base.BaseFrameToolbarActivity;
import com.supertg.supertg.util.ActivitySwitcher;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 对应于Activity，负责View的绘制以及与用户交互
 */
public class MainActivity extends BaseFrameToolbarActivity<MainPresenter, MainModel> implements MainContract.View {

    @BindView(R.id.list)
    RecyclerView list;

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
    protected void initData() {
        mPresenter.getMeiZiList(this,list,1,lifecycleSubject);
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
