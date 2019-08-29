package com.lizhehan.wanandroid.ui.wxarticle;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.lizhehan.wanandroid.R;
import com.lizhehan.wanandroid.base.BaseFragment;
import com.lizhehan.wanandroid.data.bean.WxArticleBean;
import com.lizhehan.wanandroid.ui.wxarticle.wxdetail.WxArticleDetailFragment;
import com.lizhehan.wanandroid.ui.wxarticle.wxdetail.adapter.WxArticleDetailFragmentAdapter;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;

public class WxArticleFragment extends BaseFragment implements WxArticleContract.View {

    @BindView(R.id.tab_layout_wxarticle)
    TabLayout wxArticleTabLayout;
    @BindView(R.id.view_pager_wxarticle)
    ViewPager wxArticleViewpager;

    List<WxArticleBean> wxBeanList;
    List<Fragment> fragmentList;
    List<String> titles;
    private WxArticlePresenter presenter;
    private WxArticleDetailFragmentAdapter adapter;

    public static WxArticleFragment getInstance() {
        return new WxArticleFragment();
    }

    @Override
    public int getLayoutResID() {
        return R.layout.fragment_wxarticle;
    }

    @Override
    protected void initView() {
        super.initView();
    }

    @Override
    protected void initData() {
        presenter = new WxArticlePresenter(this);
        fragmentList = new ArrayList<>();
        wxBeanList = new ArrayList<>();
        titles = new LinkedList<>();
        showLoading();
        presenter.getWxTitleList();
    }

    @Override
    public void getWxResultOK(List<WxArticleBean> demoBeans) {
        wxBeanList.clear();
        titles.clear();
        fragmentList.clear();
        wxBeanList.addAll(demoBeans);
        if (wxBeanList.size() > 0) {
            for (WxArticleBean bean : wxBeanList) {
                fragmentList.add(WxArticleDetailFragment.getInstance(bean.getId()));
                titles.add(bean.getName());
            }
            adapter = new WxArticleDetailFragmentAdapter(getChildFragmentManager(), fragmentList, titles);
            wxArticleViewpager.setOffscreenPageLimit(2);
            wxArticleViewpager.setAdapter(adapter);
            wxArticleTabLayout.setupWithViewPager(wxArticleViewpager);
            adapter.notifyDataSetChanged();
        }
        showNormal();
    }

    @Override
    public void getWxResultErr(String info) {
        showError(info);
    }

    @Override
    public void reload() {
        super.reload();
        presenter.getWxTitleList();
    }

    /**
     * 查找 子 fragment 回到顶部
     */
    public void scrollChildToTop() {
        if (adapter != null) {
            WxArticleDetailFragment currentFragment = adapter.getCurrentFragment();
            currentFragment.scrollToTop();
        }
    }
}
