package com.lizhehan.wanandroid.ui.tree;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.lizhehan.wanandroid.Constants;
import com.lizhehan.wanandroid.R;
import com.lizhehan.wanandroid.adapter.TreeAdapter;
import com.lizhehan.wanandroid.base.BaseFragment;
import com.lizhehan.wanandroid.bean.Chapter;
import com.lizhehan.wanandroid.databinding.FragmentTreeBinding;

import java.util.ArrayList;
import java.util.List;

public class TreeFragment extends BaseFragment<TreePresenter> implements TreeContract.View {

    private FragmentTreeBinding binding;
    private TreeAdapter treeAdapter;
    private List<Chapter> chapterList;

    @Override
    protected View getViewBindingRoot(LayoutInflater inflater) {
        binding = FragmentTreeBinding.inflate(inflater);
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
        presenter = new TreePresenter();
        presenter.attachView(this);
        presenter.getTree();
        chapterList = new ArrayList<>();
        treeAdapter = new TreeAdapter(getContext(), chapterList);
        treeAdapter.setOnItemClickListener(new TreeAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                Bundle bundle = new Bundle();
                bundle.putSerializable(Constants.CHAPTER, chapterList.get(position));
                Navigation.findNavController(requireView()).navigate(R.id.treeActivity, bundle);
            }
        });
        treeAdapter.setOnItemChildClickListener(new TreeAdapter.OnItemChildClickListener() {
            @Override
            public void onClick(int position, int childPosition) {
                Bundle bundle = new Bundle();
                bundle.putSerializable(Constants.CHAPTER, chapterList.get(position));
                bundle.putInt(Constants.CHAPTER_CHILD, childPosition);
                Navigation.findNavController(requireView()).navigate(R.id.treeActivity, bundle);
            }
        });
        binding.recyclerView.setAdapter(treeAdapter);
    }

    @Override
    public void getTreeSuccess(List<Chapter> chapterList) {
        binding.swipeRefreshLayout.setRefreshing(false);
        this.chapterList.clear();
        this.chapterList.addAll(chapterList);
        treeAdapter.notifyItemRangeChanged(0, chapterList.size());
    }

    @Override
    public void getTreeError(String errorMsg) {
        binding.swipeRefreshLayout.setRefreshing(false);
    }

    public void scrollToTop() {
        binding.recyclerView.scrollToPosition(0);
    }
}
