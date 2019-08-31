package com.lizhehan.wanandroid.ui.wxarticle.wxdetail;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lizhehan.wanandroid.R;
import com.lizhehan.wanandroid.base.BaseFragment;
import com.lizhehan.wanandroid.data.bean.WxArticleDetailBean;
import com.lizhehan.wanandroid.ui.webview.WebViewActivity;
import com.lizhehan.wanandroid.ui.wxarticle.wxdetail.adapter.WxArticleDetailRecyclerViewAdapter;
import com.lizhehan.wanandroid.util.ConstantUtil;
import com.lizhehan.wanandroid.util.IntentUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


/**
 * 微信详情界面
 */

public class WxArticleDetailFragment extends BaseFragment implements WxArticleDetailContract.View, BaseQuickAdapter.OnItemClickListener {

    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.normal_view)
    SwipeRefreshLayout swipeRefreshLayout;
    private List<WxArticleDetailBean.DatasBean> datasBeanList;
    private WxArticleDetailRecyclerViewAdapter mAdapter;
    private WxArticleDetailPresenter presenter;
    private boolean loading;
    private RecyclerView.OnScrollListener scrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
            if (!loading && linearLayoutManager.getItemCount() == (linearLayoutManager.findLastVisibleItemPosition() + 10)) {
                presenter.onLoadMore();
            }
        }
    };
    /**
     * id 编号
     */
    private int id = -1;

    public static WxArticleDetailFragment getInstance(int id) {
        WxArticleDetailFragment fragment = new WxArticleDetailFragment();
        Bundle args = new Bundle();
        args.putInt(ConstantUtil.WX_FRAGMENT_ID, id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutResID() {
        return R.layout.fragment_tree_detail;
    }

    @Override
    public void reload() {
        super.reload();
        if (id != -1) {
            presenter.getWxArticleDetailResult(id, 1);
        }
    }

    @Override
    protected void initView() {
        super.initView();
        setRefresh();
        showLoading();
        rv.setLayoutManager(new LinearLayoutManager(activity));
    }

    @Override
    protected void initData() {
        presenter = new WxArticleDetailPresenter(this);
        datasBeanList = new ArrayList<>();
        mAdapter = new WxArticleDetailRecyclerViewAdapter(R.layout.item_home, datasBeanList);
        rv.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(this);
        if (getArguments() != null) {
            id = getArguments().getInt(ConstantUtil.WX_FRAGMENT_ID);
            presenter.getWxArticleDetailResult(id, 1);
        }
        mAdapter.notifyDataSetChanged();
    }

    private void setRefresh() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.refresh();
                loading = false;
            }
        });

        rv.addOnScrollListener(scrollListener);
    }

    @Override
    public void getWxArticleDetailOk(WxArticleDetailBean beans, boolean hasRefresh) {
        if (swipeRefreshLayout != null) {
            if (swipeRefreshLayout.isRefreshing()) {
                swipeRefreshLayout.setRefreshing(false);
            }
        }
        if (id == -1 && mAdapter == null) {
            return;
        }
        if (hasRefresh) {
            datasBeanList.clear();
            datasBeanList.addAll(beans.getDatas());
            mAdapter.replaceData(beans.getDatas());
        } else {
            if (beans.getDatas().size() > 0) {
                datasBeanList.addAll(beans.getDatas());
                mAdapter.addData(beans.getDatas());
            } else {
                loading = true;
            }
        }
        showNormal();
    }

    @Override
    public void getWxArticleDetailErr(String err) {
        if (swipeRefreshLayout != null) {
            if (swipeRefreshLayout.isRefreshing()) {
                swipeRefreshLayout.setRefreshing(false);
            }
        }
        showError(err);
    }

    /**
     * 滚动到 顶部
     */
    public void scrollToTop() {
        rv.scrollToPosition(0);
    }

    /**
     * item 跳转事件
     *
     * @param adapter
     * @param view
     * @param position
     */
    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        WxArticleDetailBean.DatasBean bean = mAdapter.getData().get(position);
        Bundle bundle = new Bundle();
        bundle.putInt(ConstantUtil.DETAIL_ID, bean.getId());
        bundle.putString(ConstantUtil.DETAIL_PATH, bean.getLink());
        bundle.putString(ConstantUtil.DETAIL_TITLE, bean.getTitle());
        bundle.putBoolean(ConstantUtil.DETAIL_IS_COLLECT, bean.isCollect());
        bundle.putString(ConstantUtil.DETAIL_AUTHOR, bean.getAuthor());
        IntentUtil.startActivity(activity, WebViewActivity.class, bundle);
    }
}
