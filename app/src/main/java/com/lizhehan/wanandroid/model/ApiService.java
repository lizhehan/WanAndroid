package com.lizhehan.wanandroid.model;

import com.lizhehan.wanandroid.data.BaseResponse;
import com.lizhehan.wanandroid.data.bean.BannerBean;
import com.lizhehan.wanandroid.data.bean.CollectBean;
import com.lizhehan.wanandroid.data.bean.HomeArticleBean;
import com.lizhehan.wanandroid.data.bean.HotBean;
import com.lizhehan.wanandroid.data.bean.HotKeyBean;
import com.lizhehan.wanandroid.data.bean.ProjectBean;
import com.lizhehan.wanandroid.data.bean.ProjectDetailBean;
import com.lizhehan.wanandroid.data.bean.TreeBean;
import com.lizhehan.wanandroid.data.bean.TreeDetailBean;
import com.lizhehan.wanandroid.data.bean.UserBean;
import com.lizhehan.wanandroid.data.bean.WxArticleBean;
import com.lizhehan.wanandroid.data.bean.WxArticleDetailBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * retrofit api 接口类
 */

public interface ApiService {
    /**
     * 主页
     */
    @GET("article/list/{page}/json")
    Observable<BaseResponse<HomeArticleBean>> getArticleList(@Path("page") int num);

    /**
     * 登录
     */
    @POST("user/login")
    @FormUrlEncoded
    Observable<BaseResponse<UserBean>> login(@Field("username") String username, @Field("password") String password);

    /**
     * 注册
     */
    @POST("user/register")
    @FormUrlEncoded
    Observable<BaseResponse<UserBean>> register(@Field("username") String username, @Field("password") String password, @Field("repassword") String repassword);

    /**
     * 退出
     */
    @GET("user/logout/json")
    Observable<BaseResponse> logout();

    /**
     * banner
     */
    @GET("banner/json")
    Observable<BaseResponse<List<BannerBean>>> getBannerList();

    /**
     * 收藏文章
     *
     * @param id
     */
    @POST("lg/collect/{id}/json")
    Observable<BaseResponse> collectArticle(@Path("id") int id);

    /**
     * 取消收藏文章
     *
     * @param id id
     */
    @POST("lg/uncollect_originId/{id}/json")
    Observable<BaseResponse> cancelCollectArticle(@Path("id") int id);

    /**
     * 体系数据
     */
    @GET("tree/json")
    Observable<BaseResponse<List<TreeBean>>> getSystemList();

    /**
     * 单个知识体系列表
     */
    @GET("article/list/{page}/json")
    Observable<BaseResponse<TreeDetailBean>> getSystemDetailList(@Path("page") int page, @Query("cid") int id);

    /**
     * 获取项目 列表
     */
    @GET("project/tree/json")
    Observable<BaseResponse<List<ProjectBean>>> getDemoTitleList();

    /**
     * 获取 项目详细信息列表数据
     */
    @GET("project/list/{page}/json")
    Observable<BaseResponse<ProjectDetailBean>> getDemoDetailList(@Path("page") int page, @Query("cid") int id);

    /**
     * 获取 我的收藏列表
     */
    @GET("lg/collect/list/{page}/json")
    Observable<BaseResponse<CollectBean>> getCollectionList(@Path("page") int page);

    /**
     * 获取 热门词
     */
    @GET("/friend/json")
    Observable<BaseResponse<List<HotBean>>> getHotList();

    /**
     * 获取 搜索热词
     *
     * @return
     */
    @GET("/hotkey/json")
    Observable<BaseResponse<List<HotKeyBean>>> getHitKeyBean();


    /**
     * 查询搜索结果
     *
     * @param page
     * @param key
     * @return
     */
    @POST("/article/query/{page}/json")
    Observable<BaseResponse<HomeArticleBean>> getSearechResult(@Path("page") int page, @Query("k") String key);

    /**
     * 获取 微信公众号列表
     *
     * @return
     */
    @GET("/wxarticle/chapters/json")
    Observable<BaseResponse<List<WxArticleBean>>> getWXList();

    /**
     * 获取 微信公众号详细信息列表数据
     */
    @GET("wxarticle/list/{id}/{page}/json")
    Observable<BaseResponse<WxArticleDetailBean>> getWXDetailList(@Path("page") int page, @Path("id") int id);
}
