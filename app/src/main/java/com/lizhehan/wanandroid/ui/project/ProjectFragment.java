package com.lizhehan.wanandroid.ui.project;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
import com.lizhehan.wanandroid.adapter.ProjectArticleAdapter;
import com.lizhehan.wanandroid.base.BaseFragment;
import com.lizhehan.wanandroid.bean.Article;
import com.lizhehan.wanandroid.bean.Chapter;
import com.lizhehan.wanandroid.databinding.FragmentListBinding;

import java.io.Serializable;
import java.util.List;

public class ProjectFragment extends BaseFragment<ProjectPresenter> implements ProjectContract.View {

    private FragmentListBinding binding;
    private ProjectArticleAdapter projectArticleAdapter;
    private List<Chapter> chapterList;

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
    }

    @Override
    protected void initData() {
        presenter = new ProjectPresenter();
        presenter.attachView(this);
        presenter.getLatestProjectArticleList(0);
        presenter.getProjectChapters();
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
                bundle.putString(Constants.LINK, projectArticleAdapter.getData().get(position).getLink());
                bundle.putString(Constants.TITLE, projectArticleAdapter.getData().get(position).getTitle());
                Navigation.findNavController(requireView()).navigate(R.id.webActivity, bundle);
            }
        });
        binding.recyclerView.setAdapter(projectArticleAdapter);
    }

    @Override
    public void getLatestProjectArticleListSuccess(List<Article> articleList, boolean isRefresh, boolean isLastPage) {
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
    public void getLatestProjectArticleListError(String errorMsg) {
        binding.swipeRefreshLayout.setRefreshing(false);
        projectArticleAdapter.getLoadMoreModule().loadMoreFail();
    }

    @Override
    public void getProjectChaptersSuccess(List<Chapter> chapterList) {
        this.chapterList = chapterList;
    }

    @Override
    public void getProjectChaptersError(String errorMsg) {

    }

    public void scrollToTop() {
        binding.recyclerView.scrollToPosition(0);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_project, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.project_tree:
                if (chapterList != null) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(Constants.CHAPTER, (Serializable) chapterList);
                    Navigation.findNavController(requireView()).navigate(R.id.projectActivity, bundle);
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
