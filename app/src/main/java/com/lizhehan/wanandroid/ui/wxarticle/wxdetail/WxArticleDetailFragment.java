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
    List<WxArticleDetailBean.DatasBean> datasBeanList;
    WxArticleDetailRecyclerViewAdapter adapter;
    private WxArticleDetailPresenter presenter;
    private boolean loading;
    RecyclerView.OnScrollListener scrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);

            LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
            if (!loading && linearLayoutManager.getItemCount() == (linearLayoutManager.findLastVisibleItemPosition() + 10)) {
                loading = true;
                presenter.onLoadMore();
                loading = false;
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
            presenter.getWxPublicListResult(id, 1);
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
        adapter = new WxArticleDetailRecyclerViewAdapter(R.layout.item_home, datasBeanList);
        rv.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
        if (getArguments() != null) {
            id = getArguments().getInt(ConstantUtil.WX_FRAGMENT_ID);
            presenter.getWxPublicListResult(id, 1);
        }
        adapter.notifyDataSetChanged();
    }

    private void setRefresh() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.onRefresh();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        rv.addOnScrollListener(scrollListener);
    }

    @Override
    public void getWxPublicListOk(WxArticleDetailBean beans, boolean hasRefresh) {
        if (id == -1 && adapter == null) {
            return;
        }
        if (hasRefresh) {
            datasBeanList.clear();
            datasBeanList.addAll(beans.getDatas());
            adapter.replaceData(beans.getDatas());
        } else {
            if (beans.getDatas().size() > 0) {
                datasBeanList.addAll(beans.getDatas());
                adapter.addData(beans.getDatas());
            } else {
                loading = true;
            }
        }
        showNormal();
    }

    @Override
    public void getWxPublicErr(String err) {
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
     * @param madapter
     * @param view
     * @param position
     */
    @Override
    public void onItemClick(BaseQuickAdapter madapter, View view, int position) {
        WxArticleDetailBean.DatasBean bean = adapter.getData().get(position);
        Bundle bundle = new Bundle();
        bundle.putInt(ConstantUtil.DETAIL_ID, bean.getId());
        bundle.putString(ConstantUtil.DETAIL_PATH, bean.getLink());
        bundle.putString(ConstantUtil.DETAIL_TITLE, bean.getTitle());
        bundle.putBoolean(ConstantUtil.DETAIL_IS_COLLECT, bean.isCollect());
        bundle.putString(ConstantUtil.DETAIL_AUTHOR, bean.getAuthor());
        IntentUtil.startActivity(activity, WebViewActivity.class, bundle);
    }
}
