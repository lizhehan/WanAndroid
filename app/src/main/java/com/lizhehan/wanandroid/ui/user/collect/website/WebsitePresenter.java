package com.lizhehan.wanandroid.ui.user.collect.website;

import com.lizhehan.wanandroid.base.BasePresenter;
import com.lizhehan.wanandroid.bean.Tool;
import com.lizhehan.wanandroid.bean.WanResponse;
import com.lizhehan.wanandroid.model.RetrofitSubscriber;
import com.lizhehan.wanandroid.model.WanRetrofitService;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class WebsitePresenter extends BasePresenter<WebsiteContract.View> implements WebsiteContract.Presenter {
    @Override
    public void getTools() {
        WanRetrofitService.getInstance()
                .create()
                .getTools()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new RetrofitSubscriber<WanResponse<List<Tool>>>(view) {
                    @Override
                    public void onSuccess(WanResponse<List<Tool>> response) {
                        view.getToolsSuccess(response.getData());
                    }

                    @Override
                    public void onError(String errorMsg) {
                        view.getToolsError(errorMsg);
                    }
                });
    }

    @Override
    public void updateTool(int id, String name, String link, int position) {
        WanRetrofitService.getInstance()
                .create()
                .updateTool(id, name, link)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new RetrofitSubscriber<WanResponse<Tool>>(view) {
                    @Override
                    public void onSuccess(WanResponse<Tool> response) {
                        view.updateToolSuccess(response.getData(), position);
                    }

                    @Override
                    public void onError(String errorMsg) {
                        view.updateToolError(errorMsg);
                    }
                });
    }

    @Override
    public void deleteTool(int id, int position) {
        WanRetrofitService.getInstance()
                .create()
                .deleteTool(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new RetrofitSubscriber<WanResponse>(view) {
                    @Override
                    public void onSuccess(WanResponse response) {
                        view.deleteToolSuccess(position);
                    }

                    @Override
                    public void onError(String errorMsg) {
                        view.deleteToolError(errorMsg);
                    }
                });
    }
}
