package com.lizhehan.wanandroid.ui.tree.treedetail;

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
 * 体系 二级 列表 presenter 层
 */

public class TreeDetailPresenter extends BasePresenter<TreeDetailContract.View>
        implements TreeDetailContract.Presenter {

    private TreeDetailContract.View view;
    private int currentPage;
    private int id = -1;
    private boolean isRefresh = true;

    public TreeDetailPresenter(TreeDetailContract.View view) {
        this.view = view;
    }

    @Override
    public void refresh() {
        isRefresh = true;
        if (id != -1) {
            currentPage = 0;
            getTreeDetailList(currentPage, id);
        }
    }

    @Override
    public void loadMore() {
        isRefresh = false;
        if (id != -1) {
            currentPage++;
            getTreeDetailList(currentPage, id);
        }
    }

    @Override
    public void getTreeDetailList(int page, int id) {
        this.id = id;
        this.currentPage = page;
        ApiStore.getInstance()
                .create(ApiService.class)
                .getSystemDetailList(page, id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponse<ArticleBean>>() {
                    @Override
                    public void onError(Throwable e) {
                        view.getTreeDetailResultErr(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResponse<ArticleBean> treeDetailBeanBaseResponse) {
                        if (treeDetailBeanBaseResponse.getErrorCode() == ConstantUtil.REQUEST_ERROR) {
                            view.getTreeDetailResultErr(treeDetailBeanBaseResponse.getErrorMsg());
                        } else if (treeDetailBeanBaseResponse.getErrorCode() == ConstantUtil.REQUEST_SUCCESS) {
                            view.getTreeDetailResultOK(treeDetailBeanBaseResponse.getData(), isRefresh);
                        }
                    }
                });
    }
}
