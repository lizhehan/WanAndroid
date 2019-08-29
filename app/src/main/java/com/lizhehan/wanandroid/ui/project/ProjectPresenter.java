package com.lizhehan.wanandroid.ui.project;

import com.lizhehan.wanandroid.base.BasePresenter;
import com.lizhehan.wanandroid.data.BaseResponse;
import com.lizhehan.wanandroid.data.bean.ProjectBean;
import com.lizhehan.wanandroid.model.ApiService;
import com.lizhehan.wanandroid.model.ApiStore;
import com.lizhehan.wanandroid.util.ConstantUtil;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * 项目 presenter 层
 */

public class ProjectPresenter extends BasePresenter<ProjectContract.View> implements ProjectContract.Presenter {

    private ProjectContract.View view;

    public ProjectPresenter(ProjectContract.View view) {
        this.view = view;
    }

    @Override
    public void getDemoTitleList() {
        ApiStore.getInstance()
                .create(ApiService.class)
                .getDemoTitleList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponse<List<ProjectBean>>>() {
                    @Override
                    public void onError(Throwable e) {
                        view.getDemoResultErr(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResponse<List<ProjectBean>> projectBaseResponse) {
                        if (projectBaseResponse.getErrorCode() == ConstantUtil.REQUEST_SUCCESS) {
                            view.getDemoResultOK(projectBaseResponse.getData());
                        } else if (projectBaseResponse.getErrorCode() == ConstantUtil.REQUEST_ERROR) {
                            view.getDemoResultErr(projectBaseResponse.getErrorMsg());
                        }
                    }
                });
    }
}