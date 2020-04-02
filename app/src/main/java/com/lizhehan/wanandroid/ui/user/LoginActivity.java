package com.lizhehan.wanandroid.ui.user;

import android.view.View;

import androidx.navigation.Navigation;

import com.lizhehan.wanandroid.R;
import com.lizhehan.wanandroid.base.BaseActivity;
import com.lizhehan.wanandroid.databinding.ActivityLoginBinding;

public class LoginActivity extends BaseActivity {

    private ActivityLoginBinding binding;

    @Override
    protected View getViewBindingRoot() {
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    protected void initView() {
        setSupportActionBar(binding.toolBar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        binding.toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void initData() {

    }

    @Override
    public boolean onSupportNavigateUp() {
        return super.onSupportNavigateUp() || Navigation.findNavController(this, R.id.nav_host_fragment).navigateUp();
    }
}
