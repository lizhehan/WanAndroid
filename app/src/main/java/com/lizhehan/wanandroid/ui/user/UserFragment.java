package com.lizhehan.wanandroid.ui.user;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.lifecycle.Observer;
import androidx.navigation.Navigation;
import androidx.preference.PreferenceFragmentCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.jeremyliao.liveeventbus.LiveEventBus;
import com.lizhehan.wanandroid.Constants;
import com.lizhehan.wanandroid.R;
import com.lizhehan.wanandroid.base.BaseFragment;
import com.lizhehan.wanandroid.bean.User;
import com.lizhehan.wanandroid.bean.UserInfo;
import com.lizhehan.wanandroid.databinding.FragmentUserBinding;

import static android.content.Context.MODE_PRIVATE;

public class UserFragment extends BaseFragment<UserPresenter> implements UserContract.View {

    private FragmentUserBinding binding;

    @Override
    protected View getViewBindingRoot(LayoutInflater inflater) {
        binding = FragmentUserBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    protected void initView() {
        getChildFragmentManager()
                .beginTransaction()
                .replace(R.id.user_container, new UserPreferenceFragment())
                .commit();

        binding.swipeRefreshLayout.setRefreshing(true);
        binding.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.getUserInfo();
            }
        });
        binding.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(requireView()).navigate(R.id.loginActivity);
            }
        });
        binding.logoutImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MaterialAlertDialogBuilder(requireContext())
                        .setTitle(getString(R.string.hint_logout))
                        .setPositiveButton(getString(R.string.logout), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                presenter.logout();
                            }
                        })
                        .setNegativeButton(getString(R.string.cancel), null)
                        .show();
            }
        });
    }

    @Override
    protected void initData() {
        presenter = new UserPresenter();
        presenter.attachView(this);
        presenter.getUserInfo();

        showLayout(requireContext().getSharedPreferences("user", MODE_PRIVATE).getInt(Constants.ID, -1) != -1);

        LiveEventBus.get(Constants.USER, User.class).observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                binding.swipeRefreshLayout.setRefreshing(true);
                showLayout(true);
                presenter.getUserInfo();
            }
        });
    }

    @Override
    public void getUserInfoSuccess(UserInfo userInfo) {
        binding.swipeRefreshLayout.setRefreshing(false);
        showLayout(true);
        binding.usernameTextView.setText(requireContext().getSharedPreferences("user", MODE_PRIVATE).getString(Constants.USERNAME, ""));
        binding.idTextView.setText(getString(R.string.id) + userInfo.getUserId());
        binding.levelTextView.setText(getString(R.string.level) + userInfo.getLevel());
        binding.rankTextView.setText(getString(R.string.rank) + userInfo.getRank());
    }

    @Override
    public void getUserInfoError(String errorMsg) {
        binding.swipeRefreshLayout.setRefreshing(false);
        showLayout(false);
    }

    @Override
    public void logoutSuccess() {
        SharedPreferences.Editor userEditor = requireContext().getSharedPreferences("user", MODE_PRIVATE).edit();
        userEditor.clear();
        userEditor.apply();
        SharedPreferences.Editor cookieEditor = requireContext().getSharedPreferences("cookie", MODE_PRIVATE).edit();
        cookieEditor.clear();
        cookieEditor.apply();
        showLayout(false);
    }

    @Override
    public void logoutError(String errorMsg) {
        Toast.makeText(requireContext(), errorMsg, Toast.LENGTH_SHORT).show();
    }

    private void showLayout(boolean isLogin) {
        binding.noLoginLayout.setVisibility(isLogin ? View.GONE : View.VISIBLE);
        binding.hasLoggedLayout.setVisibility(isLogin ? View.VISIBLE : View.GONE);
    }

    public static class UserPreferenceFragment extends PreferenceFragmentCompat {
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.preferences_user, rootKey);
        }
    }
}
