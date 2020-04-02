package com.lizhehan.wanandroid.ui.user.register;

import android.content.SharedPreferences;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;

import androidx.navigation.Navigation;

import com.jeremyliao.liveeventbus.LiveEventBus;
import com.lizhehan.wanandroid.Constants;
import com.lizhehan.wanandroid.base.BaseFragment;
import com.lizhehan.wanandroid.bean.User;
import com.lizhehan.wanandroid.databinding.FragmentRegisterBinding;

import static android.content.Context.MODE_PRIVATE;

public class RegisterFragment extends BaseFragment<RegisterPresenter> implements RegisterContract.View, TextWatcher {

    private FragmentRegisterBinding binding;
    private String username;
    private String password;
    private String repassword;

    @Override
    protected View getViewBindingRoot(LayoutInflater inflater) {
        binding = FragmentRegisterBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    protected void initView() {
        binding.usernameEditText.addTextChangedListener(this);
        binding.passwordEditText.addTextChangedListener(this);
        binding.repasswordEditText.addTextChangedListener(this);
        binding.registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.register(username, password, repassword);
            }
        });
        binding.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(requireView()).navigateUp();
            }
        });
    }

    @Override
    protected void initData() {
        presenter = new RegisterPresenter();
        presenter.attachView(this);
    }

    @Override
    public void registerSuccess(User user) {
        SharedPreferences.Editor editor = requireContext().getSharedPreferences("user", MODE_PRIVATE).edit();
        editor.putString(Constants.USERNAME, user.getUsername());
        editor.putInt(Constants.ID, user.getId());
        editor.apply();
        LiveEventBus.get(Constants.USER).post(user);
        requireActivity().finish();
    }

    @Override
    public void registerError(String errorMsg) {
        binding.usernameTextInputLayout.setError(errorMsg);
        binding.passwordTextInputLayout.setError(errorMsg);
        binding.repasswordTextInputLayout.setError(errorMsg);
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
        repassword = binding.repasswordEditText.getText().toString().trim();
        if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(repassword)) {
            binding.registerButton.setEnabled(true);
        } else {
            binding.registerButton.setEnabled(false);
        }
    }
}
