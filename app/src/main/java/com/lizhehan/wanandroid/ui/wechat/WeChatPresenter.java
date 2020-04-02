package com.lizhehan.wanandroid.ui.wechat;

import com.lizhehan.wanandroid.base.BasePresenter;
import com.lizhehan.wanandroid.bean.Chapter;
import com.lizhehan.wanandroid.bean.WanResponse;
import com.lizhehan.wanandroid.model.RetrofitSubscriber;
import com.lizhehan.wanandroid.model.WanRetrofitService;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class WeChatPresenter extends BasePresenter<WeChatContract.View> implements WeChatContract.Presenter {
    @Override
    public void getWXChapters() {
        WanRetrofitService.getInstance()
                .create()
                .getWXChapters()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new RetrofitSubscriber<WanResponse<List<Chapter>>>(view) {
                    @Override
                    public void onSuccess(WanResponse<List<Chapter>> response) {
                        view.getWXChaptersSuccess(response.getData());
                    }

                    @Override
                    public void onError(String errorMsg) {
                        view.getWXChaptersError(errorMsg);
                    }
                });
    }
}
