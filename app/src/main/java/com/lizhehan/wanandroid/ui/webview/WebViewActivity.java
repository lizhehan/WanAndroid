package com.lizhehan.wanandroid.ui.webview;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.view.menu.MenuPopupHelper;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.lizhehan.wanandroid.R;
import com.lizhehan.wanandroid.base.BaseActivity;
import com.lizhehan.wanandroid.ui.user.UserActivity;
import com.lizhehan.wanandroid.util.ConstantUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static android.os.Environment.DIRECTORY_PICTURES;

public class WebViewActivity extends BaseActivity implements WebViewContract.view {

    private Toolbar webViewToolbar;
    private WebView mWebView;
    private SwipeRefreshLayout webViewSwipeRefreshLayout;
    private ProgressBar webViewProgressBar;
    private View mOfflineView;

    private String title;
    private String author;
    private String detailLink;
    private int detailId;
    private WebViewPresenter presenter;
    private boolean isCollect;
    private int x;
    private int y;
    private String tmpImgUrl;

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_web_view;
    }

    @Override
    protected void initView() {
        webViewToolbar = findViewById(R.id.web_view_toolbar);
        webViewToolbar.setTitle("");
        setSupportActionBar(webViewToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        mWebView = findViewById(R.id.web_view);
        webViewProgressBar = findViewById(R.id.web_view_progress_bar);
        webViewSwipeRefreshLayout = findViewById(R.id.swipe_refresh_layout_web_view);
//        ViewGroup parent = (ViewGroup) webViewSwipeRefreshLayout.getParent();
//        View.inflate(activity, R.layout.view_offline, parent);
//        mOfflineView = parent.findViewById(R.id.offline_group);
//        Button retry = mOfflineView.findViewById(R.id.btn_retry);
//        retry.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                checkNet();
//            }
//        });
//        mOfflineView.setVisibility(View.GONE);
    }

    @Override
    protected void initData() {
        presenter = new WebViewPresenter(this);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            title = bundle.getString(ConstantUtil.DETAIL_TITLE);
            if (bundle.containsKey(ConstantUtil.DETAIL_AUTHOR)) {
                author = bundle.getString(ConstantUtil.DETAIL_AUTHOR);
            }
            detailLink = bundle.getString(ConstantUtil.DETAIL_PATH);
            detailId = bundle.getInt(ConstantUtil.DETAIL_ID, ConstantUtil.REQUEST_ERROR);
            isCollect = bundle.getBoolean(ConstantUtil.DETAIL_IS_COLLECT);
        }
        setRefresh();
        setWebView(detailLink);
    }

//    @Override
//    protected void checkNet() {
//        if (NetUtil.getNetWorkState(context) == NetUtil.NETWORK_NONE) {
//            webViewSwipeRefreshLayout.setVisibility(View.GONE);
//            mOfflineView.setVisibility(View.VISIBLE);
//        } else {
//            mOfflineView.setVisibility(View.GONE);
//            webViewSwipeRefreshLayout.setVisibility(View.VISIBLE);
//            setWebView(detailLink);
//        }
//    }

    @SuppressLint("SetJavaScriptEnabled")
    private void setWebView(String url) {
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setSupportZoom(true);//是否支持缩放，true 支持，false 不支持
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDisplayZoomControls(false);
        webSettings.setAllowFileAccess(true);
        mWebView.setWebViewClient(new WebViewClient() {
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
//                String newurl = request.getUrl().toString();
//                //处理intent协议
//                if (newurl.startsWith("intent://")) {
//                    Intent intent;
//                    try {
//                        intent = Intent.parseUri(newurl, Intent.URI_INTENT_SCHEME);
//                        intent.addCategory("android.intent.category.BROWSABLE");
//                        intent.setComponent(null);
//                        intent.setSelector(null);
//                        List<ResolveInfo> resolves = context.getPackageManager().queryIntentActivities(intent,0);
//                        if(resolves.size() > 0){
//                            startActivityIfNeeded(intent, -1);
//                        } else {
//                            Toast.makeText(activity, "应用未安装", Toast.LENGTH_SHORT).show();
//                        }
//                        return true;
//                    } catch (URISyntaxException e) {
//                        e.printStackTrace();
//                    }
//                }
//                // 处理自定义scheme协议
//                if (!newurl.startsWith("http") || !newurl.startsWith("https") || !newurl.startsWith("ftp")) {
//                    try {
//                        // 以下固定写法
//                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(newurl));
//                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
//                        startActivity(intent);
//                    } catch (Exception e) {
//                        // 防止没有安装的情况
//                        e.printStackTrace();
//                        Toast.makeText(activity, "应用未安装", Toast.LENGTH_SHORT).show();
//                    }
//                    return true;
//                }
//                return super.shouldOverrideUrlLoading(view, request);
//            }
//            @Override
//            public void onPageStarted(WebView view, String url, Bitmap favicon) {
//                super.onPageStarted(view, url, favicon);
//                webViewProgressBar.setVisibility(View.VISIBLE);
//            }
//
//            @Override
//            public void onPageFinished(WebView view, String url) {
//                super.onPageFinished(view, url);
//                webViewProgressBar.setVisibility(View.INVISIBLE);
//            }
        });

        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (newProgress == 100) {
                    if (webViewSwipeRefreshLayout.isRefreshing()) {
                        webViewSwipeRefreshLayout.setRefreshing(false);
                    }
                    webViewProgressBar.setVisibility(View.INVISIBLE);
                } else {
                    if (webViewProgressBar.getVisibility() == View.INVISIBLE) {
                        webViewProgressBar.setVisibility(View.VISIBLE);
                    }
                    webViewProgressBar.setProgress(newProgress);
                }
            }

            @Override
            public void onReceivedTitle(WebView view, String newTitle) {
                super.onReceivedTitle(view, title);
                if (mWebView.canGoBack()) {
                    webViewToolbar.setTitle(newTitle);
                    webViewToolbar.setSubtitle(null);
                } else {
                    webViewToolbar.setTitle(title);
                    if (author != null) {
                        webViewToolbar.setSubtitle(author);
                    } else {
                        webViewToolbar.setSubtitle(null);
                    }
                }
            }
        });

        /*
         * 长按屏幕事件，return false 时长按时会出现复制功能，return true 时长按时不会出现复制
         */
        mWebView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                final WebView.HitTestResult hitTestResult = mWebView.getHitTestResult();
                PopupMenu popupMenu = new PopupMenu(activity, v);
                // 如果是图片类型或者是带有图片链接的类型
                if (hitTestResult.getType() == WebView.HitTestResult.IMAGE_TYPE ||
                        hitTestResult.getType() == WebView.HitTestResult.SRC_IMAGE_ANCHOR_TYPE) {
                    popupMenu.getMenuInflater().inflate(R.menu.popup_menu_web_view_image, popupMenu.getMenu());
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            String imgUrl = hitTestResult.getExtra();//获取图片
                            switch (item.getItemId()) {
                                case R.id.popup_menu_open_image:
                                    mWebView.loadUrl(imgUrl);
                                    break;
                                case R.id.popup_menu_save_image:
                                    saveImage(imgUrl);
                                    break;
                                case R.id.popup_menu_share_image:
                                    shareImage(imgUrl);
                                    break;
                                default:
                                    break;
                            }
                            return false;
                        }
                    });
                    hookPopMenu(v, popupMenu);
                    return true;
                } else if (hitTestResult.getType() == WebView.HitTestResult.SRC_ANCHOR_TYPE) {
                    popupMenu.getMenuInflater().inflate(R.menu.popup_menu_web_view_link, popupMenu.getMenu());
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()) {
                                case R.id.popup_menu_copy_link:
                                    copyLink(hitTestResult.getExtra());
                                    break;
                                case R.id.popup_menu_open_in_browser:
                                    openInBrowser(hitTestResult.getExtra());
                                    break;
                                default:
                                    break;
                            }
                            return false;
                        }
                    });
                    hookPopMenu(v, popupMenu);
                    return true;
                }
                return false;
            }
        });
        mWebView.loadUrl(url);
    }

    private void hookPopMenu(View view, PopupMenu popupMenu) {
        try {
            Field mPopup = popupMenu.getClass().getDeclaredField("mPopup");
            mPopup.setAccessible(true);
            MenuPopupHelper menuPopupHelper = (MenuPopupHelper) mPopup.get(popupMenu);
            Method show = menuPopupHelper.getClass().getMethod("show", int.class, int.class);
            int[] position = new int[2];
            //获取view在屏幕上的坐标
            view.getLocationInWindow(position);
            x = (x - position[0]);
            y = (y - position[1] - view.getHeight());
            show.invoke(menuPopupHelper, x, y);
        } catch (NoSuchFieldException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        } finally {
            //出错时调用普通show方法。未出错时此方法也不会影响正常显示
            popupMenu.show();
        }
    }

    private void setRefresh() {
        webViewSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mWebView.reload();
            }
        });
    }

//    private void saveImageWithDownloadManager(String imgUrl) {
//        if(ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//            new AlertDialog.Builder(activity)
//                    .setTitle(getString(R.string.dialog_request_permissions_title))
//                    .setMessage(getString(R.string.dialog_request_permissions_message))
//                    .setPositiveButton(getString(R.string.dialog_request_permissions_next), new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
//                            tmpImgUrl = imgUrl;
//                        }
//                    })
//                    .show();
//        } else {
//            if(URLUtil.isValidUrl(imgUrl)) {
//                String fileName = "WanAndroid/WanAndroid_" + new SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault()).format(new Date(System.currentTimeMillis())) + ".jpg";
//                // Initialize a new download request
//                DownloadManager downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
//                DownloadManager.Request request = new DownloadManager.Request(Uri.parse(imgUrl));
//                request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI);
//                request.allowScanningByMediaScanner();
//                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_ONLY_COMPLETION);
//                request.setTitle("点按即可查看图片");
//                //设置Notification的message信息
//                request.setDescription("图片下载已完成");
//                request.setVisibleInDownloadsUi(true);
//                request.setMimeType("image/*");
//                //设置文件存放目录
//                request.setDestinationInExternalPublicDir(DIRECTORY_PICTURES, fileName);
//                downloadManager.enqueue(request);
//            }else {
//                Toast.makeText(activity,"Invalid image url.",Toast.LENGTH_SHORT).show();
//            }
//        }
//    }

    private void saveImage(String imgUrl) {
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            new AlertDialog.Builder(activity)
                    .setTitle(getString(R.string.dialog_request_permissions_title))
                    .setMessage(getString(R.string.dialog_request_permissions_message) + Environment.getExternalStoragePublicDirectory(DIRECTORY_PICTURES) + "/WanAndroid")
                    .setPositiveButton(getString(R.string.dialog_request_permissions_next), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                            tmpImgUrl = imgUrl;
                        }
                    })
                    .show();
        } else {
            Glide.with(context)
                    .asFile()
                    .load(imgUrl)
                    .into(new CustomTarget<File>() {
                        @Override
                        public void onResourceReady(@NonNull File resource, @Nullable Transition<? super File> transition) {
                            String filePath = resource.getPath();
                            BitmapFactory.Options options = new BitmapFactory.Options();
                            options.inJustDecodeBounds = true;
                            BitmapFactory.decodeFile(filePath, options);
                            //outMimeType是以--"image/png”、“image/jpeg”、“image/gif”…这样的方式返回的
                            String mimeType = options.outMimeType;
                            String fileName = "WanAndroid_" + new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
                            switch (mimeType) {
                                case "image/png":
                                    fileName = fileName + ".png";
                                    break;
                                case "image/gif":
                                    fileName = fileName + ".gif";
                                    break;
                                default:
                                    fileName = fileName + ".jpg";
                                    break;
                            }
                            File targetFolder = new File(Environment.getExternalStoragePublicDirectory(DIRECTORY_PICTURES), "WanAndroid");
                            File targetFile = new File(targetFolder, fileName);
                            FileInputStream fileInputStream = null;
                            FileOutputStream fileOutputStream = null;
                            try {
                                if (!targetFolder.exists()) {
                                    targetFolder.mkdirs();
                                }
                                fileInputStream = new FileInputStream(resource);
                                fileOutputStream = new FileOutputStream(targetFile);
                                byte[] buffer = new byte[1024];
                                while (fileInputStream.read(buffer) > 0) {
                                    fileOutputStream.write(buffer);
                                }
//                                sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(targetFile)));
                                MediaScannerConnection.scanFile(context,
                                        new String[]{targetFile.getAbsolutePath()}, new String[]{mimeType}, null);
                                Toast.makeText(activity, getString(R.string.saved_image), Toast.LENGTH_SHORT).show();
                            } catch (Exception e) {
                                e.printStackTrace();
                                Toast.makeText(activity, getString(R.string.failed_to_save_image), Toast.LENGTH_SHORT).show();
                            } finally {
                                try {
                                    if (fileInputStream != null) {
                                        fileInputStream.close();
                                    }
                                    if (fileOutputStream != null) {
                                        fileOutputStream.close();
                                    }
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }

                        @Override
                        public void onLoadCleared(@Nullable Drawable placeholder) {

                        }
                    });
        }
    }

    private void shareImage(String imgUrl) {
        Glide.with(context)
                .asFile()
                .load(imgUrl)
                .into(new CustomTarget<File>() {
                    @Override
                    public void onResourceReady(@NonNull File resource, @Nullable Transition<? super File> transition) {
                        Uri uri = FileProvider.getUriForFile(activity, "com.lizhehan.wanandroid.fileprovider", resource);
                        Intent shareIntent = new Intent(Intent.ACTION_SEND);
                        shareIntent.setType("image/*");
                        shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
                        startActivity(Intent.createChooser(shareIntent, getString(R.string.menu_share_image)));
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {

                    }
                });
    }

    private void openInBrowser(String link) {
        Uri uri = Uri.parse(link);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    private void copyLink(String link) {
        ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData mClipData = ClipData.newPlainText("Label", link);
        cm.setPrimaryClip(mClipData);
        Toast.makeText(context, getString(R.string.copied_link), Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            x = (int) ev.getRawX();
            y = (int) ev.getRawY();
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_web_view, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.menu_star).setIcon(isCollect ? R.drawable.ic_star_white : R.drawable.ic_star_border_white);
        menu.findItem(R.id.menu_star).setTitle(isCollect ? getString(R.string.menu_already_collect) : getString(R.string.menu_collect));
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.menu_share:
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_TEXT, "【WanAndroid】\n" + mWebView.getTitle() + "\n" + mWebView.getUrl());
                startActivity(Intent.createChooser(shareIntent, getString(R.string.menu_share)));
                break;
            case R.id.menu_star:
                SharedPreferences pref = getSharedPreferences("user_data", MODE_PRIVATE);
                if (pref.getBoolean(ConstantUtil.IS_LOGIN, ConstantUtil.FALSE)) {
                    if (!isCollect) {
                        presenter.collectArticle(detailId);
                    } else {
                        presenter.cancelCollectArticle(detailId);
                    }
                } else {
                    Toast.makeText(activity, getString(R.string.please_login), Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(activity, UserActivity.class));
                }
                break;
            case R.id.menu_open_in_browser:
                openInBrowser(mWebView.getUrl());
                break;
            case R.id.menu_copy_link:
                copyLink(mWebView.getUrl());
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (mWebView.canGoBack()) {
            mWebView.goBack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                saveImage(tmpImgUrl);
            } else {
                Toast.makeText(activity, "You denied the permission", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void collectArticleOK(String info) {
        isCollect = true;
        invalidateOptionsMenu();
        Toast.makeText(activity, getString(R.string.collect_success), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void collectArticleErr(String info) {
        if (info.contains(getString(R.string.please_login))) {
            Toast.makeText(activity, info, Toast.LENGTH_SHORT).show();
            startActivity(new Intent(activity, UserActivity.class));
        } else {
            Toast.makeText(activity, getString(R.string.collect_fail) + info, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void cancelCollectArticleOK(String info) {
        isCollect = false;
        invalidateOptionsMenu();
        Snackbar.make(mWebView, getString(R.string.cancel_collect_success), Snackbar.LENGTH_LONG)
                .setAction(getString(R.string.undo), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        presenter.collectArticle(detailId);
                    }
                }).show();
    }

    @Override
    public void cancelCollectArticleErr(String info) {
        if (info.contains(getString(R.string.please_login))) {
            Toast.makeText(activity, info, Toast.LENGTH_SHORT).show();
            startActivity(new Intent(activity, UserActivity.class));
        } else {
            Toast.makeText(activity, getString(R.string.cancel_collect_fail) + info, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void showNormal() {

    }

    @Override
    public void showError(String err) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void showOffline() {

    }

    @Override
    public void showEmpty() {

    }

    @Override
    public void reload() {

    }
}
