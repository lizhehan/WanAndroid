package com.lizhehan.wanandroid.ui.search;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.listener.OnLoadMoreListener;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.snackbar.Snackbar;
import com.lizhehan.wanandroid.Constants;
import com.lizhehan.wanandroid.R;
import com.lizhehan.wanandroid.adapter.SearchAdapter;
import com.lizhehan.wanandroid.base.BaseActivity;
import com.lizhehan.wanandroid.bean.Article;
import com.lizhehan.wanandroid.bean.Tool;
import com.lizhehan.wanandroid.databinding.ActivitySearchBinding;
import com.lizhehan.wanandroid.ui.web.WebActivity;

import java.util.List;

public class SearchActivity extends BaseActivity<SearchPresenter> implements SearchContract.View {

    private ActivitySearchBinding binding;
    private SearchAdapter searchAdapter;
    private SearchView searchView;
    private List<Tool> toolList;

    @Override
    protected View getViewBindingRoot() {
        binding = ActivitySearchBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    protected void initView() {
        setSupportActionBar(binding.toolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        binding.toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.refresh();
            }
        });
        binding.chipGroup.setOnCheckedChangeListener(new ChipGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(ChipGroup group, int checkedId) {
                if (checkedId != -1) {
                    group.clearCheck();
                    searchView.setQuery(toolList.get(checkedId).getName(), true);
                }
            }
        });
    }

    @Override
    protected void initData() {
        presenter = new SearchPresenter();
        presenter.attachView(this);
        presenter.getHotKey();
        searchAdapter = new SearchAdapter();
        searchAdapter.getLoadMoreModule().setEnableLoadMoreIfNotFullPage(false);
        searchAdapter.getLoadMoreModule().setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                presenter.loadMore();
            }
        });
        searchAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putInt(Constants.ID, searchAdapter.getData().get(position).getId());
                bundle.putString(Constants.LINK, searchAdapter.getData().get(position).getLink());
                bundle.putString(Constants.TITLE, searchAdapter.getData().get(position).getTitle());
                Intent intent = new Intent(SearchActivity.this, WebActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        binding.recyclerView.setAdapter(searchAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        MenuItem searchItem = menu.findItem(R.id.app_bar_search);
        searchView = (SearchView) searchItem.getActionView();
        searchView.setMaxWidth(2000);
        searchView.setIconified(true);
        searchView.setIconifiedByDefault(true);
        searchView.onActionViewExpanded();
        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                binding.hotKeyLayout.setVisibility(View.GONE);
                binding.swipeRefreshLayout.setVisibility(View.VISIBLE);
                binding.swipeRefreshLayout.setRefreshing(true);
                presenter.getQueryArticleList(0, query, true);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void getQueryArticleListSuccess(List<Article> articleList, boolean isRefresh, boolean isLastPage) {
        if (isRefresh) {
            binding.swipeRefreshLayout.setRefreshing(false);
            if (articleList.size() == 0) {
                Snackbar.make(binding.getRoot(), getString(R.string.no_search_results), Snackbar.LENGTH_SHORT).show();
            }
            searchAdapter.setNewData(articleList);
        } else {
            searchAdapter.addData(articleList);
            searchAdapter.getLoadMoreModule().loadMoreComplete();
        }
        if (isLastPage) {
            searchAdapter.getLoadMoreModule().loadMoreEnd();
        }
    }

    @Override
    public void getQueryArticleListError(String errorMsg) {
        Toast.makeText(this, errorMsg, Toast.LENGTH_SHORT).show();
        binding.swipeRefreshLayout.setRefreshing(false);
        searchAdapter.getLoadMoreModule().loadMoreFail();
    }

    @Override
    public void getHotKeySuccess(List<Tool> toolList) {
        this.toolList = toolList;
        for (int i = 0; i < toolList.size(); i++) {
            Chip chip = new Chip(this);
            chip.setId(i);
            chip.setText(toolList.get(i).getName());
            chip.setCheckable(true);
            binding.chipGroup.addView(chip);
        }
    }

    @Override
    public void getHotKeyError(String errorMsg) {
        Toast.makeText(this, errorMsg, Toast.LENGTH_SHORT).show();
    }
}
