package com.lizhehan.wanandroid.ui.user.collect.article;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.listener.OnItemLongClickListener;
import com.chad.library.adapter.base.listener.OnLoadMoreListener;
import com.jeremyliao.liveeventbus.LiveEventBus;
import com.lizhehan.wanandroid.Constants;
import com.lizhehan.wanandroid.R;
import com.lizhehan.wanandroid.adapter.CollectArticleAdapter;
import com.lizhehan.wanandroid.base.BaseFragment;
import com.lizhehan.wanandroid.bean.Article;
import com.lizhehan.wanandroid.bean.User;
import com.lizhehan.wanandroid.databinding.FragmentListBinding;
import com.lizhehan.wanandroid.ui.web.WebActivity;

import java.util.List;

public class ArticleFragment extends BaseFragment<ArticlePresenter> implements ArticleContract.View {

    private FragmentListBinding binding;
    private CollectArticleAdapter collectArticleAdapter;

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
        presenter = new ArticlePresenter();
        presenter.attachView(this);
        presenter.getCollectArticleList(0);
        collectArticleAdapter = new CollectArticleAdapter();
        collectArticleAdapter.getLoadMoreModule().setEnableLoadMoreIfNotFullPage(false);
        collectArticleAdapter.getLoadMoreModule().setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                presenter.loadMore();
            }
        });
        collectArticleAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putInt(Constants.ID, collectArticleAdapter.getData().get(position).getOriginId());
                bundle.putString(Constants.LINK, collectArticleAdapter.getData().get(position).getLink());
                bundle.putString(Constants.TITLE, collectArticleAdapter.getData().get(position).getTitle());
                Intent intent = new Intent(getActivity(), WebActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        collectArticleAdapter.setOnItemLongClickListener(new OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                PopupMenu popupMenu = new PopupMenu(requireContext(), view);
                popupMenu.getMenuInflater().inflate(R.menu.popup_menu_collect_article, popupMenu.getMenu());
                popupMenu.setGravity(Gravity.END);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.cancel_collect:
                                Article article = collectArticleAdapter.getData().get(position);
                                presenter.cancelCollectArticle(article.getId(), article.getOriginId(), position);
                                break;
                            default:
                                break;
                        }
                        return false;
                    }
                });
                popupMenu.show();
                return false;
            }
        });
        binding.recyclerView.setAdapter(collectArticleAdapter);

        LiveEventBus.get(Constants.USER, User.class).observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                binding.swipeRefreshLayout.setRefreshing(true);
                presenter.getCollectArticleList(0);
            }
        });
    }

    @Override
    public void getCollectArticleListSuccess(List<Article> articleList, boolean isRefresh, boolean isLastPage) {
        if (isRefresh) {
            binding.swipeRefreshLayout.setRefreshing(false);
            collectArticleAdapter.setNewData(articleList);
        } else {
            collectArticleAdapter.addData(articleList);
            collectArticleAdapter.getLoadMoreModule().loadMoreComplete();
        }
        if (isLastPage) {
            collectArticleAdapter.getLoadMoreModule().loadMoreEnd();
        }
    }

    @Override
    public void getCollectArticleListError(String errorMsg) {
        binding.swipeRefreshLayout.setRefreshing(false);
        collectArticleAdapter.getLoadMoreModule().loadMoreFail();
        Toast.makeText(requireContext(), errorMsg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void cancelCollectArticleSuccess(int position) {
        collectArticleAdapter.remove(position);
    }

    @Override
    public void cancelCollectArticleError(String errorMsg) {
        Toast.makeText(requireContext(), errorMsg, Toast.LENGTH_SHORT).show();
    }
}
