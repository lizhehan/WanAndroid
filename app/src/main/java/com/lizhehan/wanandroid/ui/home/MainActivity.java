package com.lizhehan.wanandroid.ui.home;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.lizhehan.wanandroid.R;
import com.lizhehan.wanandroid.base.BaseActivity;
import com.lizhehan.wanandroid.data.bean.UserBean;
import com.lizhehan.wanandroid.ui.about.AboutActivity;
import com.lizhehan.wanandroid.ui.collect.CollectActivity;
import com.lizhehan.wanandroid.ui.project.ProjectFragment;
import com.lizhehan.wanandroid.ui.search.SearchActivity;
import com.lizhehan.wanandroid.ui.tree.TreeFragment;
import com.lizhehan.wanandroid.ui.user.UserActivity;
import com.lizhehan.wanandroid.ui.user.UserContract;
import com.lizhehan.wanandroid.ui.user.UserPresenter;
import com.lizhehan.wanandroid.ui.wxarticle.WxArticleFragment;
import com.lizhehan.wanandroid.util.ConstantUtil;
import com.lizhehan.wanandroid.util.network.NetUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MainActivity extends BaseActivity implements UserContract.View {

    private static final String FRAGMENT_TAG = "fragment_tag";
    private static final int FRAGMENT_SIZE = 4;
    private static final String CURRENT_INDEX = "current_index";
    private static final int MESSAGE_SHOW_START_PAGE = 1;
    private static boolean isShowPageStart = true;
    @BindView(R.id.app_bar_layout_main)
    AppBarLayout appBarLayout;
    @BindView(R.id.toolbar_main)
    Toolbar toolbar;
    @BindView(R.id.bottom_navigation)
    BottomNavigationView navigation;
    @BindView(R.id.relative_main)
    RelativeLayout relative_main;
    @BindView(R.id.img_page_start)
    ImageView img_page_start;
    private UserContract.Presenter presenter;
    private List<Fragment> fragmentList;
    private int lastIndex;
    private long mExitTime;
    private SharedPreferences pref;
    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (msg.what == MESSAGE_SHOW_START_PAGE) {
                AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
                alphaAnimation.setDuration(300);
                alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        relative_main.setVisibility(View.GONE);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                relative_main.startAnimation(alphaAnimation);
            }
            return false;
        }
    });

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.bottom_navigation_home:
                    isScrollToTop(0);
                    setFragmentPosition(0);
                    return true;
                case R.id.bottom_navigation_tree:
                    isScrollToTop(1);
                    setFragmentPosition(1);
                    return true;
                case R.id.bottom_navigation_wxarticle:
                    isScrollToTop(2);
                    setFragmentPosition(2);
                    return true;
                case R.id.bottom_navigation_project:
                    isScrollToTop(3);
                    setFragmentPosition(3);
                    return true;
                default:
                    break;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentList = new ArrayList<>();
        if (savedInstanceState != null) {
            //获取保存的fragment 没有的话返回null
            for (int i = 0; i < FRAGMENT_SIZE; i++) {
                Fragment fragment = getSupportFragmentManager().getFragment(savedInstanceState, FRAGMENT_TAG + i);
                if (fragment != null) {
                    fragmentList.add(i, fragment);
                } else {
                    switch (i) {
                        case 0:
                            fragmentList.add(i, HomeFragment.getInstance());
                            break;
                        case 1:
                            fragmentList.add(i, TreeFragment.getInstance());
                            break;
                        case 2:
                            fragmentList.add(i, WxArticleFragment.getInstance());
                            break;
                        case 3:
                            fragmentList.add(i, ProjectFragment.getInstance());
                            break;
                        default:
                            break;
                    }
                }
            }
            lastIndex = savedInstanceState.getInt(CURRENT_INDEX, 0);
            setFragmentPosition(lastIndex);
        } else {
            initFragment();
            setFragmentPosition(0);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        for (int i = 0; i < FRAGMENT_SIZE; i++) {
            //确保fragment是否已经加入到fragment manager中 并且fragment不为空时保存
            if (fragmentList.get(i).isAdded() && fragmentList.get(i) != null) {
                //保存已加载的Fragment
                getSupportFragmentManager().putFragment(outState, FRAGMENT_TAG + i, fragmentList.get(i));
            }
        }
        //传入当前选中的fragment值，在销毁重启后再定向到该fragment
        outState.putInt(CURRENT_INDEX, lastIndex);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        if (isShowPageStart) {
            relative_main.setVisibility(View.VISIBLE);
            Glide.with(context).load(R.mipmap.ic_launcher_round).into(img_page_start);
            mHandler.sendEmptyMessageDelayed(MESSAGE_SHOW_START_PAGE, 1000);
            isShowPageStart = false;
        }
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    @Override
    protected void initData() {
    }

    @Override
    protected void login() {
        presenter = new UserPresenter(this);
        pref = getSharedPreferences("user_data", MODE_PRIVATE);
        if (!pref.getString(ConstantUtil.USERNAME, ConstantUtil.DEFAULT).equals(ConstantUtil.DEFAULT)) {
            String userName = pref.getString(ConstantUtil.USERNAME, ConstantUtil.DEFAULT);
            String password = pref.getString(ConstantUtil.PASSWORD, ConstantUtil.DEFAULT);
            presenter.login(userName, password);
        }
    }

    @Override
    public void onNetChange(int status) {
        super.onNetChange(status);
        if (status == NetUtil.NETWORK_MOBILE || status == NetUtil.NETWORK_WIFI) {
            if (!pref.getString(ConstantUtil.USERNAME, ConstantUtil.DEFAULT).equals(ConstantUtil.DEFAULT) &&
                    !pref.getBoolean(ConstantUtil.IS_LOGIN, ConstantUtil.FALSE)) {
                login();
            }
        }
    }

    @Override
    protected void initToolbar() {
        setSupportActionBar(toolbar);
        toolbar.setTitle(R.string.app_name);
    }

    private void initFragment() {
        fragmentList.add(0, HomeFragment.getInstance());
        fragmentList.add(1, TreeFragment.getInstance());
        fragmentList.add(2, WxArticleFragment.getInstance());
        fragmentList.add(3, ProjectFragment.getInstance());
    }

    private void setFragmentPosition(int position) {
        if (position == 0 || position == 1) {
            appBarLayout.setElevation(4);
        } else if (position == 2 || position == 3) {
            appBarLayout.setElevation(0);
        }
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        Fragment currentFragment = fragmentList.get(position);
        Fragment lastFragment = fragmentList.get(lastIndex);
        lastIndex = position;
        fragmentTransaction.hide(lastFragment);
        if (!currentFragment.isAdded()) {
            getSupportFragmentManager().beginTransaction().remove(currentFragment).commit();
            fragmentTransaction.add(R.id.frame_layout, currentFragment);
        }
        fragmentTransaction.show(currentFragment);
        fragmentTransaction.commitAllowingStateLoss();
    }

    /**
     * 滚动置顶
     */
    private void isScrollToTop(int position) {
        if (lastIndex == position) {
            scrollToTop();
        }
    }

    private void scrollToTop() {
        switch (lastIndex) {
            case 0:
                HomeFragment homeFragment = (HomeFragment) fragmentList.get(0);
                homeFragment.scrollToTop();
                break;
            case 1:
                TreeFragment treeFragment = (TreeFragment) fragmentList.get(1);
                treeFragment.scrollToTop();
                break;
            case 2:
                WxArticleFragment wxArticleFragment = (WxArticleFragment) fragmentList.get(2);
                wxArticleFragment.scrollChildToTop();
                break;
            case 3:
                ProjectFragment projectFragment = (ProjectFragment) fragmentList.get(3);
                projectFragment.scrollChildToTop();
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.main_menu_search:
                Toast.makeText(context, "开发中...", Toast.LENGTH_SHORT).show();
//                startActivity(new Intent(activity, SearchActivity.class));
                break;
            case R.id.main_menu_user:
                BottomSheetDialog mBottomSheetDialog = new BottomSheetDialog(activity);
                View dialogView = activity.getLayoutInflater().inflate(R.layout.dialog_bottom_sheet, null);
                RelativeLayout relative_account = dialogView.findViewById(R.id.view_account);
                RelativeLayout relative_history = dialogView.findViewById(R.id.view_history);
                RelativeLayout relative_read_later = dialogView.findViewById(R.id.view_read_later);
                RelativeLayout relative_collect = dialogView.findViewById(R.id.view_collect);
                RelativeLayout relative_about = dialogView.findViewById(R.id.view_about);
                ImageView img_account = dialogView.findViewById(R.id.img_account);
                TextView tv_username = dialogView.findViewById(R.id.tv_username);
                TextView tv_status = dialogView.findViewById(R.id.tv_status);
                if (pref.getBoolean(ConstantUtil.IS_LOGIN, false)) {
                    Glide.with(context).load(R.mipmap.ic_launcher_round).into(img_account);
                    tv_username.setText(pref.getString(ConstantUtil.USERNAME, ConstantUtil.DEFAULT));
                    tv_status.setVisibility(View.VISIBLE);
                }
                relative_account.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (pref.getBoolean(ConstantUtil.IS_LOGIN, ConstantUtil.FALSE)) {
                            new AlertDialog.Builder(activity)
                                    .setTitle("要移除帐号 " + pref.getString(ConstantUtil.USERNAME, ConstantUtil.DEFAULT) + " 吗？")
                                    .setMessage("移除该帐号后将无法查看收藏夹以及收藏文章。")
                                    .setPositiveButton("移除帐号", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            presenter.logout();
                                            mBottomSheetDialog.dismiss();
                                        }
                                    })
                                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                        }
                                    })
                                    .show();
                        } else {
                            startActivity(new Intent(activity, UserActivity.class));
                            mBottomSheetDialog.dismiss();
                        }
                    }
                });
                relative_collect.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (pref.getBoolean(ConstantUtil.IS_LOGIN, ConstantUtil.FALSE)) {
                            startActivity(new Intent(activity, CollectActivity.class));
                            mBottomSheetDialog.dismiss();
                        } else {
                            new AlertDialog.Builder(activity)
                                    .setMessage("需要登录才能查看收藏夹")
                                    .setPositiveButton("去登录", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            startActivity(new Intent(activity, UserActivity.class));
                                            mBottomSheetDialog.dismiss();
                                        }
                                    })
                                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                        }
                                    })
                                    .show();
                        }
                    }
                });
                relative_history.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(context, "开发中...", Toast.LENGTH_SHORT).show();
                        mBottomSheetDialog.dismiss();
                    }
                });
                relative_read_later.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(context, "开发中...", Toast.LENGTH_SHORT).show();
                        mBottomSheetDialog.dismiss();
                    }
                });
                relative_about.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(activity, AboutActivity.class));
                        mBottomSheetDialog.dismiss();
                    }
                });
                mBottomSheetDialog.setContentView(dialogView);
                mBottomSheetDialog.show();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if ((System.currentTimeMillis() - mExitTime) > 2000) {
            Toast.makeText(activity, getString(R.string.tips_exit), Toast.LENGTH_SHORT).show();
            mExitTime = System.currentTimeMillis();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void loginOk(UserBean userBean) {
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean(ConstantUtil.IS_LOGIN, ConstantUtil.TRUE);
        editor.apply();
    }

    @Override
    public void loginErr(String info) {
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean(ConstantUtil.IS_LOGIN, ConstantUtil.FALSE);
        editor.apply();
        Toast.makeText(activity, info, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void registerOk(UserBean userBean) {

    }

    @Override
    public void registerErr(String info) {

    }

    @Override
    public void logoutOk(String info) {
        SharedPreferences.Editor userEditor = pref.edit();
        userEditor.clear();
        userEditor.apply();
        SharedPreferences.Editor cookieEditor = getSharedPreferences("cookie_data", MODE_PRIVATE).edit();
        cookieEditor.clear();
        cookieEditor.apply();
        Toast.makeText(activity, "已移除帐号", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void logoutErr(String info) {

    }

    @Override
    public void showNormal() {

    }

    @Override
    public void showError(String err) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void showOffline() {

    }

    @Override
    public void showEmpty() {

    }

    @Override
    public void reload() {

    }
}
