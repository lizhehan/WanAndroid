package com.lizhehan.wanandroid.ui.home;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lizhehan.wanandroid.R;
import com.lizhehan.wanandroid.base.BaseFragment;
import com.lizhehan.wanandroid.data.bean.ArticleBean;
import com.lizhehan.wanandroid.data.bean.BannerBean;
import com.lizhehan.wanandroid.data.bean.UserBean;
import com.lizhehan.wanandroid.ui.home.adapter.HomeRecyclerViewAdapter;
import com.lizhehan.wanandroid.ui.webview.WebViewActivity;
import com.lizhehan.wanandroid.ui.webview.WebViewContract;
import com.lizhehan.wanandroid.ui.webview.WebViewPresenter;
import com.lizhehan.wanandroid.util.ConstantUtil;
import com.lizhehan.wanandroid.util.GlideImageLoader;
import com.lizhehan.wanandroid.util.IntentUtil;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class HomeFragment extends BaseFragment implements HomeRecyclerViewAdapter.OnItemClickListener,
        HomeRecyclerViewAdapter.OnItemChildClickListener, HomeContract.View, WebViewContract.view {

    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.normal_view)
    SwipeRefreshLayout swipeRefreshLayout;

    private List<ArticleBean.DatasBean> articleList;
    private List<String> linkList;
    private List<String> imageList;
    private List<String> titleList;
    private HomeRecyclerViewAdapter mAdapter;
    private HomePresenter presenter;
    private WebViewPresenter collectPresenter;
    private CardView bannerCardView;
    private Banner banner;
    private LinearLayout bannerView;
    private int clickPosition;
    private boolean loading;
    private RecyclerView.OnScrollListener scrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
            if (!loading && linearLayoutManager.getItemCount() == (linearLayoutManager.findLastVisibleItemPosition() + 10)) {
                presenter.loadMore();
            }
        }
    };

    public static HomeFragment getInstance() {
        return new HomeFragment();
    }

    @Override
    public int getLayoutResID() {
        return R.layout.fragment_home;
    }

    /**
     * 数据 初始化
     */
    @Override
    protected void initData() {
        setRefresh();
        articleList = new ArrayList<>();
        linkList = new ArrayList<>();
        imageList = new ArrayList<>();
        titleList = new ArrayList<>();
        presenter = new HomePresenter(this);
        collectPresenter = new WebViewPresenter(this);
        presenter.getBanner();
        presenter.getHomeData(0);
        mAdapter = new HomeRecyclerViewAdapter(R.layout.item_home, articleList);
        mAdapter.addHeaderView(bannerView);
        mAdapter.setOnItemClickListener(this);
        mAdapter.setOnItemChildClickListener(this);
        rv.setAdapter(mAdapter);
    }

    /**
     * 初始化 ui
     */
    @Override
    protected void initView() {
        super.initView();
        showLoading();
        rv.setLayoutManager(new LinearLayoutManager(context));
        bannerView = (LinearLayout) getLayoutInflater().inflate(R.layout.view_banner, null);
        banner = bannerView.findViewById(R.id.banner);
        bannerCardView = bannerView.findViewById(R.id.banner_card_view);
        bannerView.removeView(bannerCardView);
        bannerView.addView(bannerCardView);
    }

    /**
     * SmartRefreshLayout刷新加载
     */
    private void setRefresh() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.refresh();
            }
        });
        rv.addOnScrollListener(scrollListener);
    }

    /**
     * 点击item 事件  （共享元素跳转）
     *
     * @param adapter
     * @param view
     * @param position
     */
    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        ArticleBean.DatasBean bean = mAdapter.getData().get(position);
        Bundle bundle = new Bundle();
        bundle.putInt(ConstantUtil.DETAIL_ID, bean.getId());
        bundle.putString(ConstantUtil.DETAIL_PATH, bean.getLink());
        bundle.putString(ConstantUtil.DETAIL_TITLE, bean.getTitle());
        bundle.putBoolean(ConstantUtil.DETAIL_IS_COLLECT, bean.isCollect());
        bundle.putString(ConstantUtil.DETAIL_AUTHOR, bean.getAuthor());
        IntentUtil.startActivity(activity, WebViewActivity.class, bundle);
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
//        if (view.getId() == R.id.image_collect) {
//            ArticleBean.DatasBean bean = mAdapter.getData().get(position);
//            if (bean.isCollect()) {
//                collectPresenter.cancelCollectArticle(bean.getId());
//                bean.setCollect(false);
//                mAdapter.setData(position, bean);
//            } else {
//                collectPresenter.collectArticle(bean.getId());
//                bean.setCollect(true);
//                mAdapter.setData(position, bean);
//            }
//        }
    }

    @Override
    public void getHomeOk(ArticleBean articleBean, boolean isRefresh) {
        if (swipeRefreshLayout != null) {
            if (swipeRefreshLayout.isRefreshing()) {
                swipeRefreshLayout.setRefreshing(false);
            }
        }
        showNormal();
        // 是 刷新adapter 则 添加数据到adapter
        if (isRefresh) {
            articleList = articleBean.getDatas();
            mAdapter.replaceData(articleList);
        } else {
            articleList.addAll(articleBean.getDatas());
            mAdapter.addData(articleBean.getDatas());
        }
    }

    @Override
    public void getHomeErr(String info) {
        if (swipeRefreshLayout != null) {
            if (swipeRefreshLayout.isRefreshing()) {
                swipeRefreshLayout.setRefreshing(false);
            }
        }
        showError(info);
    }

    @Override
    public void getBannerOk(List<BannerBean> bannerBean) {
        imageList.clear();
        titleList.clear();
        linkList.clear();
        for (BannerBean benarBean : bannerBean) {
            imageList.add(benarBean.getImagePath());
            titleList.add(benarBean.getTitle());
            linkList.add(benarBean.getUrl());
        }
        if (!activity.isDestroyed()) {
            banner.setImageLoader(new GlideImageLoader())
                    .setBannerStyle(BannerConfig.CIRCLE_INDICATOR)
                    .setImages(imageList)
                    .setBannerAnimation(Transformer.Default)
                    .setBannerTitles(titleList)
                    .isAutoPlay(true)
                    .setDelayTime(5000)
                    .setIndicatorGravity(BannerConfig.RIGHT)
                    .start();
        }
        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                if (!TextUtils.isEmpty(linkList.get(position))) {
                    Bundle bundle = new Bundle();
                    bundle.putString(ConstantUtil.DETAIL_TITLE, titleList.get(position));
                    bundle.putString(ConstantUtil.DETAIL_PATH, linkList.get(position));
                    IntentUtil.startActivity(activity, WebViewActivity.class, bundle);
                }
            }
        });
    }

    @Override
    public void getBannerErr(String info) {
        showError(info);
    }

    @Override
    public void loginOk(UserBean userBean) {

    }

    @Override
    public void loginErr(String err) {

    }

    @Override
    public void collectArticleOK(String info) {

    }

    @Override
    public void collectArticleErr(String info) {

    }

    @Override
    public void cancelCollectArticleOK(String info) {

    }

    @Override
    public void cancelCollectArticleErr(String info) {

    }

    @Override
    public void reload() {
        showLoading();
        presenter.getBanner();
        presenter.refresh();
    }

    public void scrollToTop() {
        rv.scrollToPosition(0);
    }

    @Override
    public void onStart() {
        super.onStart();
        //开始轮播
        banner.startAutoPlay();
    }

    @Override
    public void onStop() {
        super.onStop();
        //结束轮播
        banner.stopAutoPlay();
    }
}
