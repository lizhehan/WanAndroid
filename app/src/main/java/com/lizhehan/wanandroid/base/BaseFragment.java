package com.lizhehan.wanandroid.base;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.lizhehan.wanandroid.ui.user.LoginActivity;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public abstract class BaseFragment<P extends BaseContract.Presenter> extends Fragment implements BaseContract.View {

    protected P presenter;
    private CompositeDisposable compositeDisposable;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return getViewBindingRoot(inflater);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        compositeDisposable = new CompositeDisposable();
        initView();
        initData();
    }

    protected abstract View getViewBindingRoot(LayoutInflater inflater);

    protected abstract void initView();

    protected abstract void initData();

    @Override
    public void login() {
        startActivity(new Intent(requireActivity(), LoginActivity.class));
    }

    @Override
    public void addSubscribe(Disposable disposable) {
        if (compositeDisposable != null) {
            compositeDisposable.add(disposable);
        }
    }

    private void clearSubscribe() {
        if (compositeDisposable != null) {
            compositeDisposable.clear();
            compositeDisposable = null;
        }
    }

    @Override
    public void onDestroyView() {
        clearSubscribe();
        if (presenter != null) {
            presenter.detachView();
        }
        super.onDestroyView();
    }
}
