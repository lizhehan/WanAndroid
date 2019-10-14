package com.lizhehan.wanandroid.ui.project.projectdetail;

import com.lizhehan.wanandroid.base.BasePresenter;
import com.lizhehan.wanandroid.data.BaseResponse;
import com.lizhehan.wanandroid.data.bean.ArticleBean;
import com.lizhehan.wanandroid.model.ApiService;
import com.lizhehan.wanandroid.model.ApiStore;
import com.lizhehan.wanandroid.util.ConstantUtil;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * 项目
 */

public class ProjectDetailPresenter extends BasePresenter<ProjectDetailContract.View> implements
        ProjectDetailContract.Presenter {

    ProjectDetailContract.View view;

    private int id = -1;
    private int page;
    private boolean isRefresh = true;

    public ProjectDetailPresenter(ProjectDetailContract.View view) {
        this.view = view;
    }

    /**
     * 获取项目 详细信息列表
     *
     * @param page
     * @param id
     */
    @Override
    public void getProjectDetail(int page, int id) {
        this.id = id;
        this.page = page;
        ApiStore.getInstance()
                .create(ApiService.class)
                .getDemoDetailList(page, id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponse<ArticleBean>>() {
                    @Override
                    public void onError(Throwable e) {
                        view.getProjectDetailErr(e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                    }

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResponse<ArticleBean> projectDetailBaseResponse) {
                        if (projectDetailBaseResponse.getErrorCode() == ConstantUtil.REQUEST_ERROR) {
                            view.getProjectDetailErr(projectDetailBaseResponse.getErrorMsg());
                        } else if (projectDetailBaseResponse.getErrorCode() == ConstantUtil.REQUEST_SUCCESS) {
                            view.getProjectDetailOK(projectDetailBaseResponse.getData(), isRefresh);
                        }
                    }
                });
    }

    @Override
    public void refresh() {
        isRefresh = true;
        page = 1;
        if (id != -1) {
            getProjectDetail(page, id);
        }
    }

    @Override
    public void loadMore() {
        isRefresh = false;
        page++;
        if (id != -1) {
            getProjectDetail(page, id);
        }
    }
}