package com.lizhehan.wanandroid;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.lizhehan.wanandroid.base.BaseActivity;
import com.lizhehan.wanandroid.databinding.ActivityMainBinding;
import com.lizhehan.wanandroid.navigation.KeepStateFragmentNavigator;
import com.lizhehan.wanandroid.ui.home.HomeFragment;
import com.lizhehan.wanandroid.ui.project.ProjectFragment;
import com.lizhehan.wanandroid.ui.tree.TreeFragment;
import com.lizhehan.wanandroid.ui.wechat.WeChatFragment;

public class MainActivity extends BaseActivity {

    private ActivityMainBinding binding;
    private long exitTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected View getViewBindingRoot() {
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    protected void initView() {
        setSupportActionBar(binding.toolBar);

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.homeFragment, R.id.treeFragment, R.id.weChatFragment, R.id.projectFragment, R.id.userFragment)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        KeepStateFragmentNavigator keepStateFragmentNavigator =
                new KeepStateFragmentNavigator(this, navHostFragment.getChildFragmentManager(), R.id.nav_host_fragment);
        navController.getNavigatorProvider().addNavigator(keepStateFragmentNavigator);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.bottomNavigationView, navController);

        binding.bottomNavigationView.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {
                Fragment fragment = navHostFragment.getChildFragmentManager().getPrimaryNavigationFragment();
                if (fragment instanceof HomeFragment) {
                    ((HomeFragment) fragment).scrollToTop();
                } else if (fragment instanceof TreeFragment) {
                    ((TreeFragment) fragment).scrollToTop();
                } else if (fragment instanceof WeChatFragment) {
                    ((WeChatFragment) fragment).scrollChildToTop();
                } else if (fragment instanceof ProjectFragment) {
                    ((ProjectFragment) fragment).scrollToTop();
                }
            }
        });
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onBackPressed() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(this, getString(R.string.hint_exit), Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            super.onBackPressed();
            finish();
        }
    }
}
