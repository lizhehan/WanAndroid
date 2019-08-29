package com.lizhehan.wanandroid.ui.user;

import android.content.SharedPreferences;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.lizhehan.wanandroid.R;
import com.lizhehan.wanandroid.base.BaseActivity;
import com.lizhehan.wanandroid.data.bean.UserBean;
import com.lizhehan.wanandroid.util.ConstantUtil;
import com.lizhehan.wanandroid.util.network.NetUtil;

import butterknife.BindView;
import butterknife.OnClick;

public class UserActivity extends BaseActivity implements UserContract.View {

    @BindView(R.id.form_login)
    View mLoginFormView;
    @BindView(R.id.tv_login_user_name)
    EditText mLoginUserNameView;
    @BindView(R.id.tv_login_password)
    EditText mLoginPasswordView;
    @BindView(R.id.input_login_user_name)
    TextInputLayout login_input_user_name;
    @BindView(R.id.input_login_password)
    TextInputLayout login_input_password;
    @BindView(R.id.btn_login)
    Button login_button;
    @BindView(R.id.btn_forgot_register)
    Button to_register_button;

    @BindView(R.id.form_register)
    View mRegisterFormView;
    @BindView(R.id.tv_register_user_name)
    EditText mRegisterUserNameView;
    @BindView(R.id.tv_register_password)
    EditText mRegisterPasswordView;
    @BindView(R.id.tv_register_repassword)
    EditText mRegisterRePasswordView;
    @BindView(R.id.input_register_user_name)
    TextInputLayout register_input_user_name;
    @BindView(R.id.input_register_password)
    TextInputLayout register_input_password;
    @BindView(R.id.input_register_repassword)
    TextInputLayout register_input_repassword;
    @BindView(R.id.btn_register)
    Button register_button;
    @BindView(R.id.btn_forgot_login)
    Button to_login_button;

    private UserContract.Presenter presenter;
    private String loginUserName;
    private String loginPassword;
    private String registerUserName;
    private String registerPassword;
    private String registerRePassword;

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_user;
    }

    @Override
    protected void initView() {
        presenter = new UserPresenter(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initToolbar() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(getString(R.string.sign_in));
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_white);
        }
    }

    @OnClick({R.id.btn_login, R.id.btn_forgot_register, R.id.btn_register, R.id.btn_forgot_login})
    void click(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                attemptLogin();
                break;
            case R.id.btn_forgot_register:
                if (getSupportActionBar() != null) {
                    getSupportActionBar().setTitle(getString(R.string.register));
                }
                Animation animation_out_to_left = AnimationUtils.loadAnimation(activity, R.anim.out_to_left);
                Animation animation_in_from_right = AnimationUtils.loadAnimation(activity, R.anim.in_from_right);
                mLoginFormView.startAnimation(animation_out_to_left);
                mLoginFormView.setVisibility(View.GONE);
                mRegisterFormView.startAnimation(animation_in_from_right);
                mRegisterFormView.setVisibility(View.VISIBLE);
                break;
            case R.id.btn_register:
                attemptRegister();
                break;
            case R.id.btn_forgot_login:
                if (getSupportActionBar() != null) {
                    getSupportActionBar().setTitle(getString(R.string.title_activity_login));
                }
                Animation animation_out_to_right = AnimationUtils.loadAnimation(activity, R.anim.out_to_right);
                Animation animation_in_from_left = AnimationUtils.loadAnimation(activity, R.anim.in_from_left);
                mRegisterFormView.startAnimation(animation_out_to_right);
                mRegisterFormView.setVisibility(View.GONE);
                mLoginFormView.startAnimation(animation_in_from_left);
                mLoginFormView.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }
    }

    private void attemptLogin() {
        // Reset errors.
        login_input_user_name.setError(null);
        login_input_password.setError(null);

        loginUserName = mLoginUserNameView.getText().toString().trim();
        loginPassword = mLoginPasswordView.getText().toString().trim();

        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(loginUserName)) {
            login_input_user_name.setError(getString(R.string.error_no_name));
            focusView = mLoginUserNameView;
            cancel = true;
        } else if (!isUserNameOrPasswordValid(loginUserName)) {
            login_input_user_name.setError(getString(R.string.error_invalid_name));
            focusView = mLoginUserNameView;
            cancel = true;
        } else if (!TextUtils.isEmpty(loginPassword) && !isUserNameOrPasswordValid(loginPassword)) {
            login_input_password.setError(getString(R.string.error_invalid_password));
            focusView = mLoginPasswordView;
            cancel = true;
        } else if (isUserNameOrPasswordValid(loginUserName) && TextUtils.isEmpty(loginPassword)) {
            login_input_password.setError(getString(R.string.error_no_password));
            focusView = mLoginPasswordView;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            hideInput(login_button);
            if (NetUtil.getNetWorkState(context) == NetUtil.NETWORK_NONE) {
                login_input_password.setError(getString(R.string.tips_connect_to_the_internet));
            } else {
                presenter.login(loginUserName, loginPassword);
            }
        }
    }

    private void attemptRegister() {
        // Reset errors.
        register_input_user_name.setError(null);
        register_input_password.setError(null);
        register_input_repassword.setError(null);

        registerUserName = mRegisterUserNameView.getText().toString().trim();
        registerPassword = mRegisterPasswordView.getText().toString().trim();
        registerRePassword = mRegisterRePasswordView.getText().toString().trim();

        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(registerUserName)) {
            register_input_user_name.setError(getString(R.string.error_no_name));
            focusView = mRegisterUserNameView;
            cancel = true;
        } else if (!isUserNameOrPasswordValid(registerUserName)) {
            register_input_user_name.setError(getString(R.string.error_invalid_name));
            focusView = mRegisterUserNameView;
            cancel = true;
        } else if (!TextUtils.isEmpty(registerPassword) && !isUserNameOrPasswordValid(registerPassword)) {
            register_input_password.setError(getString(R.string.error_invalid_password));
            focusView = mRegisterPasswordView;
            cancel = true;
        } else if (isUserNameOrPasswordValid(registerUserName) && TextUtils.isEmpty(registerPassword)) {
            register_input_password.setError(getString(R.string.error_no_password));
            focusView = mRegisterPasswordView;
            cancel = true;
        } else if (isUserNameOrPasswordValid(registerUserName) && isUserNameOrPasswordValid(registerPassword) && TextUtils.isEmpty(registerRePassword)) {
            register_input_repassword.setError(getString(R.string.error_no_password));
            focusView = mRegisterRePasswordView;
            cancel = true;
        } else if (isUserNameOrPasswordValid(registerUserName) && isUserNameOrPasswordValid(registerPassword)
                && !isUserNameOrPasswordValid(registerRePassword)) {
            register_input_repassword.setError(getString(R.string.error_invalid_password));
            focusView = mRegisterRePasswordView;
            cancel = true;
        } else if (isUserNameOrPasswordValid(registerUserName) && isUserNameOrPasswordValid(registerPassword)
                && isUserNameOrPasswordValid(registerRePassword) && !registerPassword.equals(registerRePassword)) {
            register_input_repassword.setError(getString(R.string.error_no_same_password));
            focusView = mRegisterRePasswordView;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            hideInput(register_button);
            if (NetUtil.getNetWorkState(context) == NetUtil.NETWORK_NONE) {
                register_input_repassword.setError(getString(R.string.tips_connect_to_the_internet));
            } else {
                presenter.register(registerUserName, registerPassword, registerRePassword);
            }
        }
    }

    private boolean isUserNameOrPasswordValid(String str) {
        return str.length() >= 6 && str.length() <= 20;
    }

    public void hideInput(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) this.getSystemService(INPUT_METHOD_SERVICE);
        if (inputMethodManager != null) {
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    public void loginOk(UserBean userBean) {
        SharedPreferences.Editor editor = getSharedPreferences("user_data", MODE_PRIVATE).edit();
        editor.putString(ConstantUtil.USERNAME, loginUserName);
        editor.putString(ConstantUtil.PASSWORD, loginPassword);
        editor.putBoolean(ConstantUtil.IS_LOGIN, true);
        editor.apply();
        finish();
    }

    @Override
    public void loginErr(String info) {
        login_input_user_name.setError(info);
        login_input_password.setError(info);
    }

    @Override
    public void registerOk(UserBean userBean) {
        Toast.makeText(activity, getString(R.string.register_ok), Toast.LENGTH_SHORT).show();
        loginUserName = registerUserName;
        loginPassword = registerPassword;
        presenter.login(loginUserName, loginPassword);
    }

    @Override
    public void registerErr(String info) {
        register_input_user_name.setError(info);
    }

    @Override
    public void logoutOk(String info) {

    }

    @Override
    public void logoutErr(String info) {

    }

    @Override
    public void showNormal() {

    }

    @Override
    public void showError(String err) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void showOffline() {

    }

    @Override
    public void showEmpty() {

    }

    @Override
    public void reload() {

    }
}
