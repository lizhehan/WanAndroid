package com.lizhehan.wanandroid.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.listener.OnLoadMoreListener;
import com.lizhehan.wanandroid.Constants;
import com.lizhehan.wanandroid.R;
import com.lizhehan.wanandroid.adapter.HomeAdapter;
import com.lizhehan.wanandroid.adapter.ImageBannerAdapter;
import com.lizhehan.wanandroid.base.BaseFragment;
import com.lizhehan.wanandroid.bean.Article;
import com.lizhehan.wanandroid.bean.Banner;
import com.lizhehan.wanandroid.databinding.BannerBinding;
import com.lizhehan.wanandroid.databinding.FragmentListBinding;
import com.lizhehan.wanandroid.widget.ScaleInTransformer;
import com.youth.banner.indicator.CircleIndicator;
import com.youth.banner.listener.OnBannerListener;

import java.util.List;

public class HomeFragment extends BaseFragment<HomePresenter> implements HomeContract.View {

    private FragmentListBinding binding;
    private BannerBinding bannerBinding;
    private HomeAdapter homeAdapter;

    @Override
    protected View getViewBindingRoot(LayoutInflater inflater) {
        binding = FragmentListBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    protected void initView() {
        setHasOptionsMenu(true);
        binding.swipeRefreshLayout.setRefreshing(true);
        binding.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.refresh();
            }
        });
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        bannerBinding = BannerBinding.inflate(getLayoutInflater());
        bannerBinding.banner.setAdapter(new ImageBannerAdapter(null))
                .setBannerHeight(200)
                .setIndicator(new CircleIndicator(getContext()))
                .setOnBannerListener(new OnBannerListener() {
                    @Override
                    public void OnBannerClick(Object data, int position) {
                        Bundle bundle = new Bundle();
                        bundle.putString(Constants.LINK, ((Banner) data).getUrl());
                        bundle.putString(Constants.TITLE, ((Banner) data).getTitle());
                        Navigation.findNavController(requireView()).navigate(R.id.webActivity, bundle);
                    }

                    @Override
                    public void onBannerChanged(int position) {

                    }
                });

        // ViewPager2 一屏多页效果
        bannerBinding.banner.getViewPager2().setOffscreenPageLimit(1);
        RecyclerView recyclerView = (RecyclerView) bannerBinding.banner.getViewPager2().getChildAt(0);
        recyclerView.setPadding(40, 0, 40, 0);
        recyclerView.setClipToPadding(false);
        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new ScaleInTransformer());
        compositePageTransformer.addTransformer(new MarginPageTransformer(40));
        bannerBinding.banner.setPageTransformer(compositePageTransformer);
    }

    @Override
    protected void initData() {
        presenter = new HomePresenter();
        presenter.attachView(this);
        presenter.getHomeArticleList(0);
        presenter.getBanner();
        homeAdapter = new HomeAdapter();
        homeAdapter.addHeaderView(bannerBinding.banner);
        homeAdapter.getLoadMoreModule().setEnableLoadMoreIfNotFullPage(false);
        homeAdapter.getLoadMoreModule().setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                presenter.loadMore();
            }
        });
        homeAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putInt(Constants.ID, homeAdapter.getData().get(position).getId());
                bundle.putString(Constants.LINK, homeAdapter.getData().get(position).getLink());
                bundle.putString(Constants.TITLE, homeAdapter.getData().get(position).getTitle());
                Navigation.findNavController(requireView()).navigate(R.id.webActivity, bundle);
            }
        });
        binding.recyclerView.setAdapter(homeAdapter);
    }

    @Override
    public void getBannerSuccess(List<Banner> bannerList) {
        bannerBinding.banner.setDatas(bannerList);
    }

    @Override
    public void getBannerError(String errorMsg) {

    }

    @Override
    public void getHomeArticleListSuccess(List<Article> articleList, boolean isRefresh, boolean isLastPage) {
        if (isRefresh) {
            binding.swipeRefreshLayout.setRefreshing(false);
            homeAdapter.setNewData(articleList);
        } else {
            homeAdapter.addData(articleList);
            homeAdapter.getLoadMoreModule().loadMoreComplete();
        }
        if (isLastPage) {
            homeAdapter.getLoadMoreModule().loadMoreEnd();
        }
    }

    @Override
    public void getHomeArticleListError(String errorMsg) {
        Toast.makeText(getContext(), errorMsg, Toast.LENGTH_SHORT).show();
        binding.swipeRefreshLayout.setRefreshing(false);
        homeAdapter.getLoadMoreModule().loadMoreFail();
    }

    public void scrollToTop() {
        binding.recyclerView.scrollToPosition(0);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_home, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.app_bar_search:
                Navigation.findNavController(requireView()).navigate(R.id.searchActivity);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        bannerBinding.banner.start();
    }

    @Override
    public void onStop() {
        super.onStop();
        bannerBinding.banner.stop();
    }
}
