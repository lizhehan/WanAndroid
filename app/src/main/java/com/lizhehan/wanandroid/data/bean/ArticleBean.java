package com.lizhehan.wanandroid.data.bean;

import android.text.Html;
import android.text.TextUtils;

import java.util.List;

/**
 * 首页 实体类
 */

public class ArticleBean {
    /**
     * curPage : 1
     * datas : [{"apkLink":"","audit":1,"author":"soulqw","chapterId":358,"chapterName":"项目基础功能","collect":false,"courseId":13,"desc":"1.一行代码完成某个View,或者多个View的高亮展示\r\n2.高亮区域支持自定义大小、操作灵活\r\n3.顺应变化,基于Android X","envelopePic":"https://www.wanandroid.com/blogimgs/555764b3-2bc9-481b-b08d-9039e275c4e7.png","fresh":false,"id":9452,"link":"https://www.wanandroid.com/blog/show/2679","niceDate":"2019-09-29","niceShareDate":"2019-09-29","origin":"","prefix":"","projectLink":"https://github.com/soulqw/Curtain","publishTime":1569765101000,"selfVisible":0,"shareDate":1569765101000,"shareUser":"","superChapterId":294,"superChapterName":"开源项目主Tab","tags":[{"name":"项目","url":"/project/list/1?cid=358"}],"title":"Curtain-一个更简单更方便的View 高亮库","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","audit":1,"author":"","chapterId":296,"chapterName":"阅读","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":9438,"link":"https://mp.weixin.qq.com/s?__biz=MzI3OTcyNjQ5MQ==&amp;mid=2247483781&amp;idx=1&amp;sn=c423dfc1737b54de893133f7df5afcdc&amp;chksm=eb421171dc3598678e8a5da7d629c4ecc172d7c06602e30be41385dadc6b5f907bd773c2e663&amp;token=579360501&amp;lang=zh_CN#rd","niceDate":"2019-09-29","niceShareDate":"2019-09-29","origin":"","prefix":"","projectLink":"","publishTime":1569764960000,"selfVisible":0,"shareDate":1569717139000,"shareUser":"ZYLAB","superChapterId":202,"superChapterName":"延伸技术","tags":[],"title":"软件开发过程中的思维方式 -- 如何分析问题","type":0,"userId":10577,"visible":1,"zan":0},{"apkLink":"","audit":1,"author":"","chapterId":78,"chapterName":"性能优化","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":9439,"link":"https://blog.csdn.net/csdn_aiyang/article/details/74989318","niceDate":"2019-09-29","niceShareDate":"2019-09-29","origin":"","prefix":"","projectLink":"","publishTime":1569764913000,"selfVisible":0,"shareDate":1569717399000,"shareUser":"zhanlv","superChapterId":74,"superChapterName":"热门专题","tags":[],"title":"Android 性能优化之内存检测、卡顿优化、耗电优化、APK瘦身","type":0,"userId":22666,"visible":1,"zan":0},{"apkLink":"","audit":1,"author":"","chapterId":424,"chapterName":"协程","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":9447,"link":"https://mp.weixin.qq.com/s/0qMWYrHDSRATw3Qj8vrTGQ","niceDate":"2019-09-29","niceShareDate":"2019-09-29","origin":"","prefix":"","projectLink":"","publishTime":1569764879000,"selfVisible":0,"shareDate":1569737781000,"shareUser":"MNY459","superChapterId":232,"superChapterName":"Kotlin","tags":[],"title":"到底什么是「非阻塞式」挂起？协程真的比线程更轻量级吗？","type":0,"userId":2199,"visible":1,"zan":0},{"apkLink":"","audit":1,"author":"","chapterId":182,"chapterName":"JNI编程","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":9449,"link":"https://juejin.im/post/5d8a44096fb9a04ded310586","niceDate":"2019-09-29","niceShareDate":"2019-09-29","origin":"","prefix":"","projectLink":"","publishTime":1569764860000,"selfVisible":0,"shareDate":1569746265000,"shareUser":"叫我小明同学","superChapterId":149,"superChapterName":"JNI","tags":[],"title":"NDK 开发之使用 OpenCV 实现人脸识别","type":0,"userId":26222,"visible":1,"zan":0},{"apkLink":"","audit":1,"author":"玉刚说","chapterId":410,"chapterName":"玉刚说","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":9419,"link":"https://mp.weixin.qq.com/s/wazr-s0jl83mbUGeAviTbA","niceDate":"2019-09-28","niceShareDate":"2019-09-28","origin":"","prefix":"","projectLink":"","publishTime":1569600000000,"selfVisible":0,"shareDate":1569677794000,"shareUser":"","superChapterId":408,"superChapterName":"公众号","tags":[{"name":"公众号","url":"/wxarticle/list/410/1"}],"title":"第一站仿小红书图片裁剪控件，深度解析大厂炫酷控件","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","audit":1,"author":"code小生","chapterId":414,"chapterName":"code小生","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":9428,"link":"https://mp.weixin.qq.com/s/u7s2n9ksyWSniurVqv5Uyw","niceDate":"2019-09-28","niceShareDate":"2019-09-28","origin":"","prefix":"","projectLink":"","publishTime":1569600000000,"selfVisible":0,"shareDate":1569679169000,"shareUser":"","superChapterId":408,"superChapterName":"公众号","tags":[{"name":"公众号","url":"/wxarticle/list/414/1"}],"title":"为什么还要在Activity中写业务代码？","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","audit":1,"author":"程序亦非猿","chapterId":428,"chapterName":"程序亦非猿","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":9437,"link":"https://mp.weixin.qq.com/s/Q7GfRwx66OF3DmHdhx7PJw","niceDate":"2019-09-28","niceShareDate":"2019-09-28","origin":"","prefix":"","projectLink":"","publishTime":1569600000000,"selfVisible":0,"shareDate":1569679988000,"shareUser":"","superChapterId":408,"superChapterName":"公众号","tags":[{"name":"公众号","url":"/wxarticle/list/428/1"}],"title":"接口幂等性这么重要，它是什么？怎么实现？","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","audit":1,"author":"","chapterId":168,"chapterName":"Drawable","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":9359,"link":"https://juejin.im/post/5d822fbf518825171501549f","niceDate":"2019-09-27","niceShareDate":"2019-09-26","origin":"","prefix":"","projectLink":"","publishTime":1569514868000,"selfVisible":0,"shareDate":1569479062000,"shareUser":"徐公码字","superChapterId":168,"superChapterName":"基础知识","tags":[],"title":"Android 点九图机制讲解及在聊天气泡中的应用","type":0,"userId":5339,"visible":1,"zan":0},{"apkLink":"","audit":1,"author":"","chapterId":494,"chapterName":"广场","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":9362,"link":"https://juejin.im/post/5d8c593ee51d45783544b9ac","niceDate":"2019-09-27","niceShareDate":"2019-09-26","origin":"","prefix":"","projectLink":"","publishTime":1569514835000,"selfVisible":0,"shareDate":1569484206000,"shareUser":"祝起光","superChapterId":494,"superChapterName":"广场Tab","tags":[],"title":"HTTPS工作原理以及Android中如何防止抓包","type":0,"userId":27937,"visible":1,"zan":0},{"apkLink":"","audit":1,"author":"","chapterId":484,"chapterName":"okhttp","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":9360,"link":"https://blog.csdn.net/gdutxiaoxu/article/details/100545600","niceDate":"2019-09-27","niceShareDate":"2019-09-26","origin":"","prefix":"","projectLink":"","publishTime":1569514830000,"selfVisible":0,"shareDate":1569479126000,"shareUser":"徐公码字","superChapterId":461,"superChapterName":"常见开源库源码解析","tags":[],"title":"一步步带你读懂 Okhttp 源码","type":0,"userId":5339,"visible":1,"zan":0},{"apkLink":"","audit":1,"author":"","chapterId":459,"chapterName":"Activity","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":9368,"link":"https://blog.csdn.net/qq_41979349/article/details/100032327","niceDate":"2019-09-27","niceShareDate":"2019-09-26","origin":"","prefix":"","projectLink":"","publishTime":1569514781000,"selfVisible":0,"shareDate":1569505837000,"shareUser":"jsyjst","superChapterId":171,"superChapterName":"framework","tags":[],"title":"Android之9.0Activity启动流程(二)","type":0,"userId":16445,"visible":1,"zan":0},{"apkLink":"","audit":1,"author":"","chapterId":459,"chapterName":"Activity","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":9367,"link":"https://blog.csdn.net/qq_41979349/article/details/98636121","niceDate":"2019-09-27","niceShareDate":"2019-09-26","origin":"","prefix":"","projectLink":"","publishTime":1569514771000,"selfVisible":0,"shareDate":1569505793000,"shareUser":"jsyjst","superChapterId":171,"superChapterName":"framework","tags":[],"title":"Android之9.0Activity启动流程(一)","type":0,"userId":16445,"visible":1,"zan":0},{"apkLink":"","audit":1,"author":"鸿洋","chapterId":408,"chapterName":"鸿洋","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":9390,"link":"https://mp.weixin.qq.com/s/gs2bojFLBB4IAWMyN9lfnw","niceDate":"2019-09-27","niceShareDate":"2019-09-27","origin":"","prefix":"","projectLink":"","publishTime":1569513600000,"selfVisible":0,"shareDate":1569585904000,"shareUser":"","superChapterId":408,"superChapterName":"公众号","tags":[{"name":"公众号","url":"/wxarticle/list/408/1"}],"title":"简书，掘金，CSDN，公众号，我要&ldquo;打&rdquo;四个！","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","audit":1,"author":"郭霖","chapterId":409,"chapterName":"郭霖","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":9398,"link":"https://mp.weixin.qq.com/s/HpMu_QI_N-J18fNJG96yzA","niceDate":"2019-09-27","niceShareDate":"2019-09-27","origin":"","prefix":"","projectLink":"","publishTime":1569513600000,"selfVisible":0,"shareDate":1569586358000,"shareUser":"","superChapterId":408,"superChapterName":"公众号","tags":[{"name":"公众号","url":"/wxarticle/list/409/1"}],"title":"关于线程池的这 8 个问题你都能答上来吗？","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","audit":1,"author":"程序亦非猿","chapterId":428,"chapterName":"程序亦非猿","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":9436,"link":"https://mp.weixin.qq.com/s/yvG2KdNewPmOL_YVE4VX8A","niceDate":"2019-09-27","niceShareDate":"2019-09-28","origin":"","prefix":"","projectLink":"","publishTime":1569513600000,"selfVisible":0,"shareDate":1569679970000,"shareUser":"","superChapterId":408,"superChapterName":"公众号","tags":[{"name":"公众号","url":"/wxarticle/list/428/1"}],"title":"消灭 Java 代码的&ldquo;坏味道&rdquo;","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","audit":1,"author":"郭霖","chapterId":409,"chapterName":"郭霖","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":9397,"link":"https://mp.weixin.qq.com/s/heBoKE2UqMgp4_zm_fciuA","niceDate":"2019-09-26","niceShareDate":"2019-09-27","origin":"","prefix":"","projectLink":"","publishTime":1569427200000,"selfVisible":0,"shareDate":1569586338000,"shareUser":"","superChapterId":408,"superChapterName":"公众号","tags":[{"name":"公众号","url":"/wxarticle/list/409/1"}],"title":"性能优化，还得看AspectJ","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","audit":1,"author":" 无异","chapterId":497,"chapterName":"instant run","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":9345,"link":"https://juejin.im/post/5d8ad352f265da5b6b62f01c","niceDate":"2019-09-25","niceShareDate":"2019-09-25","origin":"","prefix":"","projectLink":"","publishTime":1569423976000,"selfVisible":0,"shareDate":1569423976000,"shareUser":"","superChapterId":461,"superChapterName":"常见开源库源码解析","tags":[],"title":"更深入理解instant-run&mdash;&mdash;源码篇","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","audit":1,"author":"鸿洋","chapterId":408,"chapterName":"鸿洋","collect":true,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":9389,"link":"https://mp.weixin.qq.com/s/chcF5vRnFTvSlPti9mfa6A","niceDate":"2019-09-25","niceShareDate":"2019-09-27","origin":"","prefix":"","projectLink":"","publishTime":1569340800000,"selfVisible":0,"shareDate":1569585837000,"shareUser":"","superChapterId":408,"superChapterName":"公众号","tags":[{"name":"公众号","url":"/wxarticle/list/408/1"}],"title":"关于MVC/MVP/MVVM的一些错误认识","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","audit":1,"author":"郭霖","chapterId":409,"chapterName":"郭霖","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":9396,"link":"https://mp.weixin.qq.com/s/6LkQrICuA1_sOedRzXXrmA","niceDate":"2019-09-25","niceShareDate":"2019-09-27","origin":"","prefix":"","projectLink":"","publishTime":1569340800000,"selfVisible":0,"shareDate":1569586322000,"shareUser":"","superChapterId":408,"superChapterName":"公众号","tags":[{"name":"公众号","url":"/wxarticle/list/409/1"}],"title":"你该知道的okhttp3拦截器的那些事","type":0,"userId":-1,"visible":1,"zan":0}]
     * offset : 0
     * over : false
     * pageCount : 361
     * size : 20
     * total : 7215
     */

    private int curPage;
    private int offset;
    private boolean over;
    private int pageCount;
    private int size;
    private int total;
    private List<DatasBean> datas;

    public int getCurPage() {
        return curPage;
    }

    public void setCurPage(int curPage) {
        this.curPage = curPage;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public boolean isOver() {
        return over;
    }

    public void setOver(boolean over) {
        this.over = over;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<DatasBean> getDatas() {
        return datas;
    }

    public void setDatas(List<DatasBean> datas) {
        this.datas = datas;
    }

    public static class DatasBean {
        /**
         * apkLink :
         * audit : 1
         * author : soulqw
         * chapterId : 358
         * chapterName : 项目基础功能
         * collect : false
         * courseId : 13
         * desc : 1.一行代码完成某个View,或者多个View的高亮展示
         2.高亮区域支持自定义大小、操作灵活
         3.顺应变化,基于Android X
         * envelopePic : https://www.wanandroid.com/blogimgs/555764b3-2bc9-481b-b08d-9039e275c4e7.png
         * fresh : false
         * id : 9452
         * link : https://www.wanandroid.com/blog/show/2679
         * niceDate : 2019-09-29
         * niceShareDate : 2019-09-29
         * origin :
         * prefix :
         * projectLink : https://github.com/soulqw/Curtain
         * publishTime : 1569765101000
         * selfVisible : 0
         * shareDate : 1569765101000
         * shareUser :
         * superChapterId : 294
         * superChapterName : 开源项目主Tab
         * tags : [{"name":"项目","url":"/project/list/1?cid=358"}]
         * title : Curtain-一个更简单更方便的View 高亮库
         * type : 0
         * userId : -1
         * visible : 1
         * zan : 0
         */

        private String apkLink;
        private int audit;
        private String author;
        private int chapterId;
        private String chapterName;
        private boolean collect;
        private int courseId;
        private String desc;
        private String envelopePic;
        private boolean fresh;
        private int id;
        private String link;
        private String niceDate;
        private String niceShareDate;
        private String origin;
        private String prefix;
        private String projectLink;
        private long publishTime;
        private int selfVisible;
        private long shareDate;
        private String shareUser;
        private int superChapterId;
        private String superChapterName;
        private String title;
        private int type;
        private int userId;
        private int visible;
        private int zan;
        private List<ArticleBean.DatasBean.TagsBean> tags;

        public String getApkLink() {
            return apkLink;
        }

        public void setApkLink(String apkLink) {
            this.apkLink = apkLink;
        }

        public int getAudit() {
            return audit;
        }

        public void setAudit(int audit) {
            this.audit = audit;
        }

        public String getAuthor() {
            if (!TextUtils.isEmpty(author)) {
                return author;
            } else {
                return shareUser;
            }
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public int getChapterId() {
            return chapterId;
        }

        public void setChapterId(int chapterId) {
            this.chapterId = chapterId;
        }

        public String getChapterName() {
            return chapterName;
        }

        public void setChapterName(String chapterName) {
            this.chapterName = chapterName;
        }

        public boolean isCollect() {
            return collect;
        }

        public void setCollect(boolean collect) {
            this.collect = collect;
        }

        public int getCourseId() {
            return courseId;
        }

        public void setCourseId(int courseId) {
            this.courseId = courseId;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getEnvelopePic() {
            return envelopePic;
        }

        public void setEnvelopePic(String envelopePic) {
            this.envelopePic = envelopePic;
        }

        public boolean isFresh() {
            return fresh;
        }

        public void setFresh(boolean fresh) {
            this.fresh = fresh;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public String getNiceDate() {
            return niceDate;
        }

        public void setNiceDate(String niceDate) {
            this.niceDate = niceDate;
        }

        public String getNiceShareDate() {
            return niceShareDate;
        }

        public void setNiceShareDate(String niceShareDate) {
            this.niceShareDate = niceShareDate;
        }

        public String getOrigin() {
            return origin;
        }

        public void setOrigin(String origin) {
            this.origin = origin;
        }

        public String getPrefix() {
            return prefix;
        }

        public void setPrefix(String prefix) {
            this.prefix = prefix;
        }

        public String getProjectLink() {
            return projectLink;
        }

        public void setProjectLink(String projectLink) {
            this.projectLink = projectLink;
        }

        public long getPublishTime() {
            return publishTime;
        }

        public void setPublishTime(long publishTime) {
            this.publishTime = publishTime;
        }

        public int getSelfVisible() {
            return selfVisible;
        }

        public void setSelfVisible(int selfVisible) {
            this.selfVisible = selfVisible;
        }

        public long getShareDate() {
            return shareDate;
        }

        public void setShareDate(long shareDate) {
            this.shareDate = shareDate;
        }

        public String getShareUser() {
            return shareUser;
        }

        public void setShareUser(String shareUser) {
            this.shareUser = shareUser;
        }

        public int getSuperChapterId() {
            return superChapterId;
        }

        public void setSuperChapterId(int superChapterId) {
            this.superChapterId = superChapterId;
        }

        public String getSuperChapterName() {
            return superChapterName;
        }

        public void setSuperChapterName(String superChapterName) {
            this.superChapterName = superChapterName;
        }

        public String getTitle() {
            return Html.fromHtml(title).toString();
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public int getVisible() {
            return visible;
        }

        public void setVisible(int visible) {
            this.visible = visible;
        }

        public int getZan() {
            return zan;
        }

        public void setZan(int zan) {
            this.zan = zan;
        }

        public List<ArticleBean.DatasBean.TagsBean> getTags() {
            return tags;
        }

        public void setTags(List<ArticleBean.DatasBean.TagsBean> tags) {
            this.tags = tags;
        }

        public static class TagsBean {
            /**
             * name : 项目
             * url : /project/list/1?cid=358
             */

            private String name;
            private String url;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }
        }
    }
}
