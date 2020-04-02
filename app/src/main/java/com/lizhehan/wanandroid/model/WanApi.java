package com.lizhehan.wanandroid.model;

import com.lizhehan.wanandroid.bean.Article;
import com.lizhehan.wanandroid.bean.Banner;
import com.lizhehan.wanandroid.bean.Chapter;
import com.lizhehan.wanandroid.bean.Coin;
import com.lizhehan.wanandroid.bean.Page;
import com.lizhehan.wanandroid.bean.Tool;
import com.lizhehan.wanandroid.bean.User;
import com.lizhehan.wanandroid.bean.UserInfo;
import com.lizhehan.wanandroid.bean.WanResponse;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface WanApi {
    /**
     * 首页文章列表
     */
    @GET("article/list/{page}/json")
    Observable<WanResponse<Page<Article>>> getHomeArticleList(@Path("page") int page);

    /**
     * 首页 banner
     */
    @GET("banner/json")
    Observable<WanResponse<List<Banner>>> getBanner();

    /**
     * 常用网站
     */
    @GET("friend/json")
    Observable<WanResponse<List<Tool>>> getFriend();

    /**
     * 搜索热词
     */
    @GET("hotkey/json")
    Observable<WanResponse<List<Tool>>> getHotKey();

    /**
     * 置顶文章
     */
    @GET("article/top/json")
    Observable<WanResponse<List<Article>>> getTopArticleList();

    /**
     * 体系数据
     */
    @GET("tree/json")
    Observable<WanResponse<List<Chapter>>> getTree();

    /**
     * 知识体系下的文章
     */
    @GET("article/list/{page}/json")
    Observable<WanResponse<Page<Article>>> getTreeArticleList(@Path("page") int page, @Query("cid") int cid);

    /**
     * 获取公众号列表
     */
    @GET("wxarticle/chapters/json")
    Observable<WanResponse<List<Chapter>>> getWXChapters();

    /**
     * 查看某个公众号历史数据
     */
    @GET("wxarticle/list/{id}/{page}/json")
    Observable<WanResponse<Page<Article>>> getWXArticleList(@Path("page") int page, @Path("id") int id);

    /**
     * 项目分类
     */
    @GET("project/tree/json")
    Observable<WanResponse<List<Chapter>>> getProjectChapters();

    /**
     * 项目列表数据
     */
    @GET("project/list/{page}/json")
    Observable<WanResponse<Page<Article>>> getProjectArticleList(@Path("page") int page, @Query("cid") int cid);

    /**
     * 最新项目
     */
    @GET("article/listproject/{page}/json")
    Observable<WanResponse<Page<Article>>> getLatestProjectArticleList(@Path("page") int page);

    /**
     * 登录
     */
    @POST("user/login")
    @FormUrlEncoded
    Observable<WanResponse<User>> login(@Field("username") String username, @Field("password") String password);

    /**
     * 注册
     */
    @POST("user/register")
    @FormUrlEncoded
    Observable<WanResponse<User>> register(@Field("username") String username, @Field("password") String password, @Field("repassword") String repassword);

    /**
     * 退出
     */
    @GET("user/logout/json")
    Observable<WanResponse> logout();

    /**
     * 收藏文章列表
     */
    @GET("lg/collect/list/{page}/json")
    Observable<WanResponse<Page<Article>>> getCollectArticleList(@Path("page") int page);

    /**
     * 收藏站内文章
     */
    @POST("lg/collect/{id}/json")
    Observable<WanResponse> collectArticle(@Path("id") int id);

    /**
     * 收藏站外文章
     */
    @POST("lg/collect/add/json")
    @FormUrlEncoded
    Observable<WanResponse> collectAdd(@Field("title") String title, @Field("author") String author, @Field("link") String link);

    /**
     * 取消收藏 文章列表
     */
    @POST("lg/uncollect_originId/{id}/json")
    Observable<WanResponse> uncollectArticleWithOriginId(@Path("id") int id);

    /**
     * 取消收藏 我的收藏页面（该页面包含自己录入的内容）
     */
    @POST("lg/uncollect/{id}/json")
    @FormUrlEncoded
    Observable<WanResponse> uncollectArticle(@Path("id") int id, @Field("originId") int originId);

    /**
     * 收藏网站列表
     */
    @GET("lg/collect/usertools/json")
    Observable<WanResponse<List<Tool>>> getTools();

    /**
     * 收藏网址
     */
    @POST("lg/collect/addtool/json")
    @FormUrlEncoded
    Observable<WanResponse> addTool(@Field("name") String name, @Field("link") String link);

    /**
     * 编辑收藏网站
     */
    @POST("lg/collect/updatetool/json")
    @FormUrlEncoded
    Observable<WanResponse<Tool>> updateTool(@Field("id") int id, @Field("name") String name, @Field("link") String link);

    /**
     * 删除收藏网站
     */
    @POST("lg/collect/deletetool/json")
    @FormUrlEncoded
    Observable<WanResponse> deleteTool(@Field("id") int id);

    /**
     * 搜索
     */
    @POST("article/query/{page}/json")
    @FormUrlEncoded
    Observable<WanResponse<Page<Article>>> getQueryArticleList(@Path("page") int page, @Field("k") String k);

    /**
     * 积分排行榜接口
     */
    @GET("coin/rank/{page}/json")
    Observable<WanResponse<Page<UserInfo>>> getRank(@Path("page") int page);

    /**
     * 获取个人积分，需要登录后访问
     */
    @GET("lg/coin/userinfo/json")
    Observable<WanResponse<UserInfo>> getUserInfo();

    /**
     * 获取个人积分获取列表，需要登录后访问
     */
    @GET("lg/coin/list/{page}/json")
    Observable<WanResponse<Page<Coin>>> getCoinList(@Path("page") int page);
}
