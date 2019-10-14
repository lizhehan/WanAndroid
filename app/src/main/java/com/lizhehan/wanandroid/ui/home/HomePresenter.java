package com.lizhehan.wanandroid.ui.home;

import com.lizhehan.wanandroid.base.BasePresenter;
import com.lizhehan.wanandroid.data.BaseResponse;
import com.lizhehan.wanandroid.data.bean.ArticleBean;
import com.lizhehan.wanandroid.data.bean.BannerBean;
import com.lizhehan.wanandroid.data.bean.UserBean;
import com.lizhehan.wanandroid.model.ApiService;
import com.lizhehan.wanandroid.model.ApiStore;
import com.lizhehan.wanandroid.util.ConstantUtil;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * 首页 presenter 层
 */

public class HomePresenter extends BasePresenter<HomeContract.View> implements HomeContract.Per {

    private HomeContract.View view;
    private boolean isRefresh = true;
    private int currentPage;

    public HomePresenter(HomeContract.View view) {
        this.view = view;
    }

    @Override
    public void attachView(HomeContract.View view) {
        super.attachView(view);
    }

    @Override
    public void detachView() {
        super.detachView();
    }

    @Override
    public void refresh() {
        isRefresh = true;
        currentPage = 0;
        getBanner();
        getHomeData(currentPage);
    }

    @Override
    public void loadMore() {
        isRefresh = false;
        currentPage++;
        getHomeData(currentPage);
    }

    /**
     * 获取 banner 信息
     */
    @Override
    public void getBanner() {
        ApiStore.getInstance()
                .create(ApiService.class)
                .getBannerList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponse<List<BannerBean>>>() {
                    @Override
                    public void onError(Throwable e) {
                        view.getBannerErr(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResponse<List<BannerBean>> bannerBaseResponse) {
                        if (bannerBaseResponse.getErrorCode() == ConstantUtil.REQUEST_ERROR) {
                            view.getBannerErr(bannerBaseResponse.getErrorMsg());
                        } else if (bannerBaseResponse.getErrorCode() == ConstantUtil.REQUEST_SUCCESS) {
                            view.getBannerOk(bannerBaseResponse.getData());
                        }
                    }
                });
    }

    /**
     * 获取首页 信息
     *
     * @param page
     */
    @Override
    public void getHomeData(int page) {
        ApiStore.getInstance()
                .create(ApiService.class)
                .getArticleList(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponse<ArticleBean>>() {
                    @Override
                    public void onError(Throwable e) {
                        view.getHomeErr(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResponse<ArticleBean> homeArticleBaseResponse) {
                        if (homeArticleBaseResponse.getErrorCode() == ConstantUtil.REQUEST_ERROR) {
                            view.getHomeErr(homeArticleBaseResponse.getErrorMsg());
                        } else if (homeArticleBaseResponse.getErrorCode() == ConstantUtil.REQUEST_SUCCESS) {
                            view.getHomeOk(homeArticleBaseResponse.getData(), isRefresh);
                        }
                    }
                });
    }

    @Override
    public void loginUser(String username, String password) {
        ApiStore.getInstance()
                .createLogin(ApiService.class)
                .login(username, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponse<UserBean>>() {
                    @Override
                    public void onError(Throwable e) {
                        if (e.getMessage() != null) {
                            view.loginErr(e.getMessage());
                        }
                    }

                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResponse<UserBean> userBaseResponse) {
                        if (userBaseResponse.getErrorCode() == ConstantUtil.REQUEST_SUCCESS) {
                            view.loginOk(userBaseResponse.getData());
                        } else if (userBaseResponse.getErrorCode() == ConstantUtil.REQUEST_ERROR) {
                            view.loginErr(userBaseResponse.getErrorMsg());
                        }
                    }
                });
    }
}