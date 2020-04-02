package com.lizhehan.wanandroid.ui.tree;

import com.lizhehan.wanandroid.base.BasePresenter;
import com.lizhehan.wanandroid.bean.Chapter;
import com.lizhehan.wanandroid.bean.WanResponse;
import com.lizhehan.wanandroid.model.RetrofitSubscriber;
import com.lizhehan.wanandroid.model.WanRetrofitService;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class TreePresenter extends BasePresenter<TreeContract.View> implements TreeContract.Presenter {
    @Override
    public void getTree() {
        WanRetrofitService.getInstance()
                .create()
                .getTree()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new RetrofitSubscriber<WanResponse<List<Chapter>>>(view) {
                    @Override
                    public void onSuccess(WanResponse<List<Chapter>> response) {
                        view.getTreeSuccess(response.getData());
                    }

                    @Override
                    public void onError(String errorMsg) {
                        view.getTreeError(errorMsg);
                    }
                });
    }

    @Override
    public void refresh() {
        getTree();
    }
}
