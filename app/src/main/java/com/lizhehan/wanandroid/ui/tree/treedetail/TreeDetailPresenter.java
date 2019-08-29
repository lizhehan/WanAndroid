package com.lizhehan.wanandroid.ui.tree.treedetail;

import com.lizhehan.wanandroid.base.BasePresenter;
import com.lizhehan.wanandroid.data.BaseResponse;
import com.lizhehan.wanandroid.data.bean.TreeDetailBean;
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
    public void autoRefresh() {
        isRefresh = true;
        if (id != -1) {
            currentPage = 0;
            getSystemDetailList(currentPage, id);
        }
    }

    @Override
    public void loadMore() {
        isRefresh = false;
        if (id != -1) {
            currentPage++;
            getSystemDetailList(currentPage, id);
        }
    }

    @Override
    public void getSystemDetailList(int page, int id) {
        this.id = id;
        this.currentPage = page;
        ApiStore.getInstance()
                .create(ApiService.class)
                .getSystemDetailList(page, id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponse<TreeDetailBean>>() {
                    @Override
                    public void onError(Throwable e) {
                        view.getSystemDetailListResultErr(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResponse<TreeDetailBean> treeDetailBeanBaseResponse) {
                        if (treeDetailBeanBaseResponse.getErrorCode() == ConstantUtil.REQUEST_ERROR) {
                            view.getSystemDetailListResultErr(treeDetailBeanBaseResponse.getErrorMsg());
                        } else if (treeDetailBeanBaseResponse.getErrorCode() == ConstantUtil.REQUEST_SUCCESS) {
                            view.getSystemDetailListResultOK(treeDetailBeanBaseResponse.getData(), isRefresh);
                        }
                    }
                });
    }
}
