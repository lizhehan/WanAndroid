package com.lizhehan.wanandroid.ui.tree.article;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.listener.OnLoadMoreListener;
import com.lizhehan.wanandroid.Constants;
import com.lizhehan.wanandroid.adapter.TreeArticleAdapter;
import com.lizhehan.wanandroid.base.BaseFragment;
import com.lizhehan.wanandroid.bean.Article;
import com.lizhehan.wanandroid.databinding.FragmentListBinding;
import com.lizhehan.wanandroid.ui.web.WebActivity;

import java.util.List;

public class TreeArticleFragment extends BaseFragment<TreeArticlePresenter> implements TreeArticleContract.View {

    private FragmentListBinding binding;
    private TreeArticleAdapter treeArticleAdapter;

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
        presenter = new TreeArticlePresenter();
        presenter.attachView(this);
        presenter.getTreeArticleList(0, getArguments().getInt(Constants.ID));
        treeArticleAdapter = new TreeArticleAdapter();
        treeArticleAdapter.getLoadMoreModule().setEnableLoadMoreIfNotFullPage(false);
        treeArticleAdapter.getLoadMoreModule().setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                presenter.loadMore();
            }
        });
        treeArticleAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putInt(Constants.ID, treeArticleAdapter.getData().get(position).getId());
                bundle.putString(Constants.LINK, treeArticleAdapter.getData().get(position).getLink());
                bundle.putString(Constants.TITLE, treeArticleAdapter.getData().get(position).getTitle());
                Intent intent = new Intent(getActivity(), WebActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        binding.recyclerView.setAdapter(treeArticleAdapter);
    }

    @Override
    public void getTreeArticleListSuccess(List<Article> articleList, boolean isRefresh, boolean isLastPage) {
        if (isRefresh) {
            binding.swipeRefreshLayout.setRefreshing(false);
            treeArticleAdapter.setNewData(articleList);
        } else {
            treeArticleAdapter.addData(articleList);
            treeArticleAdapter.getLoadMoreModule().loadMoreComplete();
        }
        if (isLastPage) {
            treeArticleAdapter.getLoadMoreModule().loadMoreEnd();
        }
    }

    @Override
    public void getTreeArticleListError(String errorMsg) {
        binding.swipeRefreshLayout.setRefreshing(false);
        treeArticleAdapter.getLoadMoreModule().loadMoreFail();
    }
}
