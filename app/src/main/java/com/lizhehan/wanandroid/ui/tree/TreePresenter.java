package com.lizhehan.wanandroid.ui.tree;

import com.lizhehan.wanandroid.base.BasePresenter;
import com.lizhehan.wanandroid.data.BaseResponse;
import com.lizhehan.wanandroid.data.bean.TreeBean;
import com.lizhehan.wanandroid.model.ApiService;
import com.lizhehan.wanandroid.model.ApiStore;
import com.lizhehan.wanandroid.util.ConstantUtil;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * 体系 presenter 层
 */

public class TreePresenter extends BasePresenter<TreeContract.View> implements TreeContract.Presenter {

    private TreeContract.View view;

    public TreePresenter(TreeContract.View view) {
        this.view = view;
    }

    @Override
    public void autoRefresh() {
        getSystemList();
    }

    @Override
    public void getSystemList() {
        ApiStore.getInstance()
                .create(ApiService.class)
                .getSystemList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponse<List<TreeBean>>>() {
                    @Override
                    public void onError(Throwable e) {
                        view.getSystemListErr(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResponse<List<TreeBean>> treeBaseResponse) {
                        if (treeBaseResponse.getErrorCode() == ConstantUtil.REQUEST_SUCCESS) {
                            view.getSystemListOk(treeBaseResponse.getData());
                        } else if (treeBaseResponse.getErrorCode() == ConstantUtil.REQUEST_ERROR) {
                            view.getSystemListErr(treeBaseResponse.getErrorMsg());
                        }
                    }
                });
    }
}