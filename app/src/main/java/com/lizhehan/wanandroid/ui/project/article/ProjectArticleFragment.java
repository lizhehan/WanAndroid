package com.lizhehan.wanandroid.ui.project.article;

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
import com.lizhehan.wanandroid.adapter.ProjectArticleAdapter;
import com.lizhehan.wanandroid.base.BaseFragment;
import com.lizhehan.wanandroid.bean.Article;
import com.lizhehan.wanandroid.databinding.FragmentListBinding;
import com.lizhehan.wanandroid.ui.web.WebActivity;

import java.util.List;

public class ProjectArticleFragment extends BaseFragment<ProjectArticlePresenter> implements ProjectArticleContract.View {

    private FragmentListBinding binding;
    private ProjectArticleAdapter projectArticleAdapter;

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
        presenter = new ProjectArticlePresenter();
        presenter.attachView(this);
        presenter.getProjectArticleList(0, getArguments().getInt(Constants.ID));
        projectArticleAdapter = new ProjectArticleAdapter();
        projectArticleAdapter.getLoadMoreModule().setEnableLoadMoreIfNotFullPage(false);
        projectArticleAdapter.getLoadMoreModule().setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                presenter.loadMore();
            }
        });
        projectArticleAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putInt(Constants.ID, projectArticleAdapter.getData().get(position).getId());
                bundle.putString(Constants.LINK, projectArticleAdapter.getData().get(position).getLink());
                bundle.putString(Constants.TITLE, projectArticleAdapter.getData().get(position).getTitle());
                Intent intent = new Intent(getActivity(), WebActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        binding.recyclerView.setAdapter(projectArticleAdapter);
    }

    @Override
    public void getProjectArticleListSuccess(List<Article> articleList, boolean isRefresh, boolean isLastPage) {
        if (isRefresh) {
            binding.swipeRefreshLayout.setRefreshing(false);
            projectArticleAdapter.setNewData(articleList);
        } else {
            projectArticleAdapter.addData(articleList);
            projectArticleAdapter.getLoadMoreModule().loadMoreComplete();
        }
        if (isLastPage) {
            projectArticleAdapter.getLoadMoreModule().loadMoreEnd();
        }
    }

    @Override
    public void getProjectArticleListError(String errorMsg) {
        binding.swipeRefreshLayout.setRefreshing(false);
        projectArticleAdapter.getLoadMoreModule().loadMoreFail();
    }

    public void scrollToTop() {
        binding.recyclerView.scrollToPosition(0);
    }
}
