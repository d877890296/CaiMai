package com.xfkc.caimai;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TabHost;
import android.widget.TextView;

import com.hyf.tdlibrary.utils.ActivityUtil;
import com.hyf.tdlibrary.utils.SharedPrefUtil;
import com.hyf.tdlibrary.utils.ToastUtil;
import com.xfkc.caimai.config.SharedPref;
import com.xfkc.caimai.home.fragment.BigLectureHallFragment;
import com.xfkc.caimai.home.fragment.FeelingFragment;
import com.xfkc.caimai.home.fragment.HomeFragment;
import com.xfkc.caimai.home.fragment.SocialCentreFragment;
import com.xfkc.caimai.rx.activity.RxActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

public class MainActivity extends RxActivity {


    @Bind(R.id.tabcontent)
    FrameLayout tabcontent;
    @Bind(android.R.id.tabhost)
    FragmentTabHost mTabHost;



    @Override
    protected int getLayoutResource() {
        return R.layout.activity_main;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        token = SharedPrefUtil.get(mContext, SharedPref.TOKEN);

        initTabHost();

        if (Build.VERSION.SDK_INT >= 23) {
            List<String> permissions = null;
            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                permissions = new ArrayList<>();
                permissions.add(Manifest.permission.CAMERA);
            }
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (permissions == null) {
                    permissions = new ArrayList<>();
                }
                permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            }
            if (permissions != null) {
                String[] permissionArray = new String[permissions.size()];
                permissions.toArray(permissionArray);
                requestPermissions(permissionArray, 5);
            }
        }



    }


    @Override
    protected void loadData() {

    }

    // 设置状态栏颜色
    @Override
    protected void setStatusBar() {
//        StatusBarUtil.setColorNoTranslucent(this, getResources().getColor(R.color.red));
//        StatusBarUtil.setTransparent(this);
    }


    //设置底部导航栏
    private void initTabHost() {

        mTabHost.setup(this, getSupportFragmentManager(), R.id.tabcontent);
        if (android.os.Build.VERSION.SDK_INT > 10) {
            mTabHost.getTabWidget().setShowDividers(0);
        }

        MenuTab[] tabs = MenuTab.values();
        final int size = tabs.length;
        for (int i = 0; i < size; i++) {
            MenuTab tab = tabs[i];
            TabHost.TabSpec spec = mTabHost.newTabSpec(tab.getResName());
            spec.setIndicator(getTabItemView(tab));
            spec.setContent(new TabHost.TabContentFactory() {
                @Override
                public View createTabContent(String tag) {
                    return new View(MainActivity.this);
                }
            });
            mTabHost.addTab(spec, tab.getClz(), null);

        }
        mTabHost.setCurrentTab(0);
        mTabHost.setOnTabChangedListener(new TDOnTabChangeListener());


    }

    //切换底部导航监听
    class TDOnTabChangeListener implements TabHost.OnTabChangeListener {

        @Override
        public void onTabChanged(String tabId) {
            final int size = mTabHost.getTabWidget().getTabCount();
            for (int i = 0; i < size; i++) {
                View view = mTabHost.getTabWidget().getChildAt(i);
                if (i == mTabHost.getCurrentTab()) {
                    view.setSelected(true);
                } else {
                    view.setSelected(false);
                }
            }
            supportInvalidateOptionsMenu();
        }
    }


    /**
     * 给Tab按钮设置图片和文字
     *
     * @param tab
     * @return
     */
    private View getTabItemView(MenuTab tab) {
        View indicator = LayoutInflater.from(getApplicationContext()).inflate(R.layout.tab_indicator, null);
        TextView titleText = (TextView) indicator.findViewById(R.id.tab_title);
        Drawable drawable = mContext.getResources().getDrawable(tab.getResIcon());
        titleText.setText(tab.getResName());
        titleText.setCompoundDrawablesWithIntrinsicBounds(null, drawable, null, null);
        return indicator;
    }

    public enum MenuTab {

        TAB_ONE(0, "大仓库", R.mipmap.ic_launcher, HomeFragment.class),

        TAB_TWO(1, "大讲堂", R.mipmap.ic_launcher, BigLectureHallFragment.class),

        TAB_THREE(2, "情怀链", R.mipmap.ic_launcher, FeelingFragment.class),

        TAB_FOUR(3, "社员中心", R.mipmap.ic_launcher, SocialCentreFragment.class);

        private int idx;
        private String resName;
        private int resIcon;
        private Class<?> clz;

        private MenuTab(int idx, String resName, int resIcon, Class<?> clz) {
            this.idx = idx;
            this.resName = resName;
            this.resIcon = resIcon;
            this.clz = clz;
        }

        public int getIdx() {
            return idx;
        }

        public void setIdx(int idx) {
            this.idx = idx;
        }

        public String getResName() {
            return resName;
        }

        public void setResName(String resName) {
            this.resName = resName;
        }

        public int getResIcon() {
            return resIcon;
        }

        public void setResIcon(int resIcon) {
            this.resIcon = resIcon;
        }

        public Class<?> getClz() {
            return clz;
        }

        public void setClz(Class<?> clz) {
            this.clz = clz;
        }
    }


    @Override
    public void onBackPressed() {
        if (ActivityUtil.exitTwice()) {
            super.onBackPressed();
        } else {
            ToastUtil.showToast("再按一次退出程序");
        }

    }

}
