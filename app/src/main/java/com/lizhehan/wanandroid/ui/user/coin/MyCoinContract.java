package com.lizhehan.wanandroid.ui.user.coin;

import com.lizhehan.wanandroid.base.BaseContract;
import com.lizhehan.wanandroid.bean.Coin;
import com.lizhehan.wanandroid.bean.UserInfo;

import java.util.List;

public interface MyCoinContract {
    interface View extends BaseContract.View {
        void getUserInfoSuccess(UserInfo userInfo);

        void getUserInfoError(String errorMsg);

        void getCoinListSuccess(List<Coin> coinList, boolean isRefresh, boolean isLastPage);

        void getCoinListError(String errorMsg);
    }

    interface Presenter extends BaseContract.Presenter<View> {
        void getUserInfo();

        void getCoinList(int page);

        void refresh();

        void loadMore();
    }
}
