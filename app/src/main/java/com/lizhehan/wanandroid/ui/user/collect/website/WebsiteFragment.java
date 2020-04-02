package com.lizhehan.wanandroid.ui.user.collect.website;

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
import com.jeremyliao.liveeventbus.LiveEventBus;
import com.lizhehan.wanandroid.Constants;
import com.lizhehan.wanandroid.R;
import com.lizhehan.wanandroid.adapter.ToolAdapter;
import com.lizhehan.wanandroid.base.BaseFragment;
import com.lizhehan.wanandroid.bean.Tool;
import com.lizhehan.wanandroid.bean.User;
import com.lizhehan.wanandroid.databinding.FragmentListBinding;
import com.lizhehan.wanandroid.ui.web.WebActivity;
import com.lizhehan.wanandroid.widget.EditWebsiteBottomSheetDialogFragment;

import java.util.List;

public class WebsiteFragment extends BaseFragment<WebsitePresenter> implements WebsiteContract.View {

    private FragmentListBinding binding;
    private ToolAdapter toolAdapter;

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
                presenter.getTools();
            }
        });
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    protected void initData() {
        presenter = new WebsitePresenter();
        presenter.attachView(this);
        presenter.getTools();
        toolAdapter = new ToolAdapter();
        toolAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putString(Constants.LINK, toolAdapter.getData().get(position).getLink());
                bundle.putString(Constants.TITLE, toolAdapter.getData().get(position).getName());
                Intent intent = new Intent(getActivity(), WebActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        toolAdapter.setOnItemLongClickListener(new OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                PopupMenu popupMenu = new PopupMenu(requireContext(), view);
                popupMenu.getMenuInflater().inflate(R.menu.popup_menu_collect_website, popupMenu.getMenu());
                popupMenu.setGravity(Gravity.END);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        Tool tool = toolAdapter.getData().get(position);
                        switch (item.getItemId()) {
                            case R.id.cancel_collect:
                                presenter.deleteTool(tool.getId(), position);
                                break;
                            case R.id.edit_website:
                                new EditWebsiteBottomSheetDialogFragment()
                                        .setNameAndLink(tool.getName(), tool.getLink())
                                        .setOnClickListener(new EditWebsiteBottomSheetDialogFragment.OnClickListener() {
                                            @Override
                                            public void onClick(String name, String link) {
                                                presenter.updateTool(tool.getId(), name, link, position);
                                            }
                                        })
                                        .show(getChildFragmentManager(), "EditWebsiteBottomSheetDialogFragment");
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
        binding.recyclerView.setAdapter(toolAdapter);

        LiveEventBus.get(Constants.USER, User.class).observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                binding.swipeRefreshLayout.setRefreshing(true);
                presenter.getTools();
            }
        });
    }

    @Override
    public void getToolsSuccess(List<Tool> tools) {
        binding.swipeRefreshLayout.setRefreshing(false);
        toolAdapter.setNewData(tools);
    }

    @Override
    public void getToolsError(String errorMsg) {
        binding.swipeRefreshLayout.setRefreshing(false);
        Toast.makeText(requireContext(), errorMsg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void updateToolSuccess(Tool tool, int position) {
        toolAdapter.setData(position, tool);
    }

    @Override
    public void updateToolError(String errorMsg) {
        Toast.makeText(requireContext(), errorMsg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void deleteToolSuccess(int position) {
        toolAdapter.remove(position);
    }

    @Override
    public void deleteToolError(String errorMsg) {
        Toast.makeText(requireContext(), errorMsg, Toast.LENGTH_SHORT).show();
    }
}
