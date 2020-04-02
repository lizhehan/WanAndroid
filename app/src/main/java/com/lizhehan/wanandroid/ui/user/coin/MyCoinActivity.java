package com.lizhehan.wanandroid.ui.user.coin;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.chad.library.adapter.base.listener.OnLoadMoreListener;
import com.jeremyliao.liveeventbus.LiveEventBus;
import com.lizhehan.wanandroid.Constants;
import com.lizhehan.wanandroid.R;
import com.lizhehan.wanandroid.adapter.CoinAdapter;
import com.lizhehan.wanandroid.base.BaseActivity;
import com.lizhehan.wanandroid.bean.Coin;
import com.lizhehan.wanandroid.bean.User;
import com.lizhehan.wanandroid.bean.UserInfo;
import com.lizhehan.wanandroid.databinding.ActivityListBinding;
import com.lizhehan.wanandroid.databinding.ViewMyCoinHeadBinding;
import com.lizhehan.wanandroid.ui.web.WebActivity;

import java.util.List;

public class MyCoinActivity extends BaseActivity<MyCoinPresenter> implements MyCoinContract.View {

    private ActivityListBinding binding;
    private ViewMyCoinHeadBinding coinCountBinding;
    private CoinAdapter coinAdapter;

    @Override
    protected View getViewBindingRoot() {
        binding = ActivityListBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    protected void initView() {
        setSupportActionBar(binding.toolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.my_coin));
        binding.swipeRefreshLayout.setRefreshing(true);
        binding.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.refresh();
            }
        });
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        coinCountBinding = ViewMyCoinHeadBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initData() {
        presenter = new MyCoinPresenter();
        presenter.attachView(this);
        presenter.getUserInfo();
        coinAdapter = new CoinAdapter();
        coinAdapter.getLoadMoreModule().setEnableLoadMoreIfNotFullPage(false);
        coinAdapter.getLoadMoreModule().setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                presenter.loadMore();
            }
        });
        coinAdapter.addHeaderView(coinCountBinding.getRoot());
        binding.recyclerView.setAdapter(coinAdapter);

        LiveEventBus.get(Constants.USER, User.class).observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                binding.swipeRefreshLayout.setRefreshing(true);
                presenter.getUserInfo();
            }
        });
    }

    @Override
    public void getUserInfoSuccess(UserInfo userInfo) {
        coinCountBinding.coinCountTextView.setText(String.valueOf(userInfo.getCoinCount()));
        presenter.getCoinList(1);
    }

    @Override
    public void getUserInfoError(String errorMsg) {
        binding.swipeRefreshLayout.setRefreshing(false);
        Toast.makeText(this, errorMsg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void getCoinListSuccess(List<Coin> coinList, boolean isRefresh, boolean isLastPage) {
        if (isRefresh) {
            binding.swipeRefreshLayout.setRefreshing(false);
            coinAdapter.setNewData(coinList);
        } else {
            coinAdapter.addData(coinList);
            coinAdapter.getLoadMoreModule().loadMoreComplete();
        }
        if (isLastPage) {
            coinAdapter.getLoadMoreModule().loadMoreEnd();
        }
    }

    @Override
    public void getCoinListError(String errorMsg) {
        binding.swipeRefreshLayout.setRefreshing(false);
        coinAdapter.getLoadMoreModule().loadMoreFail();
        Toast.makeText(this, errorMsg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_my_coin, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.help:
                Bundle bundle = new Bundle();
                bundle.putString(Constants.LINK, "https://www.wanandroid.com/blog/show/2653");
                Intent intent = new Intent(this, WebActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
