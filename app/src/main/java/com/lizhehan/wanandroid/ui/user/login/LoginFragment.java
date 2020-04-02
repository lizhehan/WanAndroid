package com.lizhehan.wanandroid.ui.user.login;

import android.content.SharedPreferences;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;

import androidx.navigation.Navigation;

import com.jeremyliao.liveeventbus.LiveEventBus;
import com.lizhehan.wanandroid.Constants;
import com.lizhehan.wanandroid.R;
import com.lizhehan.wanandroid.base.BaseFragment;
import com.lizhehan.wanandroid.bean.User;
import com.lizhehan.wanandroid.databinding.FragmentLoginBinding;

import static android.content.Context.MODE_PRIVATE;

public class LoginFragment extends BaseFragment<LoginPresenter> implements LoginContract.View, TextWatcher {

    private FragmentLoginBinding binding;
    private String username;
    private String password;

    @Override
    protected View getViewBindingRoot(LayoutInflater inflater) {
        binding = FragmentLoginBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    protected void initView() {
        binding.usernameEditText.addTextChangedListener(this);
        binding.passwordEditText.addTextChangedListener(this);
        binding.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.login(username, password);
            }
        });
        binding.registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(requireView()).navigate(R.id.action_loginFragment_to_registerFragment);
            }
        });
    }

    @Override
    protected void initData() {
        presenter = new LoginPresenter();
        presenter.attachView(this);
    }

    @Override
    public void loginSuccess(User user) {
        SharedPreferences.Editor editor = requireContext().getSharedPreferences("user", MODE_PRIVATE).edit();
        editor.putString(Constants.USERNAME, user.getUsername());
        editor.putInt(Constants.ID, user.getId());
        editor.apply();
        LiveEventBus.get(Constants.USER).post(user);
        requireActivity().finish();
    }

    @Override
    public void loginError(String errorMsg) {
        binding.usernameTextInputLayout.setError(errorMsg);
        binding.passwordTextInputLayout.setError(errorMsg);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        username = binding.usernameEditText.getText().toString().trim();
        password = binding.passwordEditText.getText().toString().trim();
        if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(password)) {
            binding.loginButton.setEnabled(true);
        } else {
            binding.loginButton.setEnabled(false);
        }
    }
}
