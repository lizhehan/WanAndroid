package com.lizhehan.wanandroid.ui.wechat.article;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.listener.OnLoadMoreListener;
import com.lizhehan.wanandroid.Constants;
import com.lizhehan.wanandroid.R;
import com.lizhehan.wanandroid.adapter.WXArticleAdapter;
import com.lizhehan.wanandroid.base.BaseFragment;
import com.lizhehan.wanandroid.bean.Article;
import com.lizhehan.wanandroid.databinding.FragmentListBinding;

import java.util.List;

public class WXArticleFragment extends BaseFragment<WXArticlePresenter> implements WXArticleContract.View {

    private FragmentListBinding binding;
    private WXArticleAdapter wxArticleAdapter;

    @Override
    protected View getViewBindingRoot(LayoutInflater inflater) {
        binding = FragmentListBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    protected void initView() {
        binding.swipeRefreshLayout.setRefreshing(true);
        binding.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.refresh();
            }
        });
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    protected void initData() {
        presenter = new WXArticlePresenter();
        presenter.attachView(this);
        presenter.getWXArticleList(0, getArguments().getInt(Constants.ID));
        wxArticleAdapter = new WXArticleAdapter();
        wxArticleAdapter.getLoadMoreModule().setEnableLoadMoreIfNotFullPage(false);
        wxArticleAdapter.getLoadMoreModule().setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                presenter.loadMore();
            }
        });
        wxArticleAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putString(Constants.LINK, wxArticleAdapter.getData().get(position).getLink());
                bundle.putString(Constants.TITLE, wxArticleAdapter.getData().get(position).getTitle());
                Navigation.findNavController(requireView()).navigate(R.id.webActivity, bundle);
            }
        });
        binding.recyclerView.setAdapter(wxArticleAdapter);
    }

    @Override
    public void getWXArticleListSuccess(List<Article> articleList, boolean isRefresh, boolean isLastPage) {
        if (isRefresh) {
            binding.swipeRefreshLayout.setRefreshing(false);
            wxArticleAdapter.setNewData(articleList);
        } else {
            wxArticleAdapter.addData(articleList);
            wxArticleAdapter.getLoadMoreModule().loadMoreComplete();
        }
        if (isLastPage) {
            wxArticleAdapter.getLoadMoreModule().loadMoreEnd();
        }
    }

    @Override
    public void getWXArticleListError(String errorMsg) {
        binding.swipeRefreshLayout.setRefreshing(false);
        wxArticleAdapter.getLoadMoreModule().loadMoreFail();
    }

    public void scrollToTop() {
        binding.recyclerView.scrollToPosition(0);
    }
}
