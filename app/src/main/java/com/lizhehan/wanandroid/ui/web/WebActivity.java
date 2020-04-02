package com.lizhehan.wanandroid.ui.web;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.view.menu.MenuPopupHelper;
import androidx.appcompat.widget.PopupMenu;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;
import com.lizhehan.wanandroid.Constants;
import com.lizhehan.wanandroid.R;
import com.lizhehan.wanandroid.base.BaseActivity;
import com.lizhehan.wanandroid.databinding.ActivityWebBinding;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class WebActivity extends BaseActivity<WebPresenter> implements WebContract.View {

    private ActivityWebBinding binding;
    private int id;
    private String title;
    private String link;
    private String tmpImageUrl;
    private int x;
    private int y;

    @Override
    protected View getViewBindingRoot() {
        binding = ActivityWebBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    protected void initView() {
        setSupportActionBar(binding.toolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        binding.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                binding.webView.reload();
            }
        });
    }

    @Override
    protected void initData() {
        presenter = new WebPresenter();
        presenter.attachView(this);

        id = getIntent().getIntExtra(Constants.ID, -1);
        title = getIntent().getStringExtra(Constants.TITLE);
        link = getIntent().getStringExtra(Constants.LINK);
        getSupportActionBar().setTitle(title);
        setWebView(link);
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void setWebView(String url) {
        WebSettings webSettings = binding.webView.getSettings();
        webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDisplayZoomControls(false);
        webSettings.setAllowFileAccess(true);
        webSettings.setAppCacheEnabled(true);
        webSettings.setDatabaseEnabled(true);

        binding.webView.setWebViewClient(new WebViewClient() {

        });

        binding.webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                binding.progressBar.setProgress(newProgress);
                if (newProgress == 100) {
                    binding.progressBar.setVisibility(View.INVISIBLE);
                    binding.swipeRefreshLayout.setRefreshing(false);
                } else {
                    binding.progressBar.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onReceivedTitle(WebView view, String newTitle) {
                super.onReceivedTitle(view, newTitle);
                if (binding.webView.canGoBack() || TextUtils.isEmpty(title)) {
                    binding.toolBar.setTitle(newTitle);
                } else {
                    binding.toolBar.setTitle(title);
                }
            }
        });

        binding.webView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                WebView.HitTestResult hitTestResult = binding.webView.getHitTestResult();
                PopupMenu popupMenu = new PopupMenu(WebActivity.this, v);
                // 如果是图片类型或者是带有图片链接的类型
                if (hitTestResult.getType() == WebView.HitTestResult.IMAGE_TYPE ||
                        hitTestResult.getType() == WebView.HitTestResult.SRC_IMAGE_ANCHOR_TYPE) {
                    popupMenu.getMenuInflater().inflate(R.menu.popup_menu_web_image, popupMenu.getMenu());
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()) {
                                case R.id.save_image:
                                    saveImage(hitTestResult.getExtra());
                                    break;
                                case R.id.share_image:
                                    shareImage(hitTestResult.getExtra());
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
                    popupMenu.getMenuInflater().inflate(R.menu.popup_menu_web_link, popupMenu.getMenu());
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()) {
                                case R.id.copy_link:
                                    copyLink(hitTestResult.getExtra());
                                    break;
                                case R.id.open_in_browser:
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

        binding.webView.loadUrl(url);
    }

    private void saveImage(String imageUrl) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            new MaterialAlertDialogBuilder(this)
                    .setTitle(getString(R.string.dialog_request_permissions_title))
                    .setMessage(getString(R.string.dialog_request_permissions_message) + Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/WanAndroid")
                    .setPositiveButton(getString(R.string.dialog_request_permissions_next), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(WebActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                            tmpImageUrl = imageUrl;
                        }
                    })
                    .show();
        } else {
            Glide.with(this)
                    .asFile()
                    .load(imageUrl)
                    .into(new CustomTarget<File>() {
                        @Override
                        public void onResourceReady(@NonNull File resource, @Nullable Transition<? super File> transition) {
                            BitmapFactory.Options options = new BitmapFactory.Options();
                            options.inJustDecodeBounds = true;
                            BitmapFactory.decodeFile(resource.getPath(), options);
                            String mimeType = options.outMimeType;
                            String fileName = "WanAndroid_" + new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
                            OutputStream os = null;
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                                ContentValues values = new ContentValues();
                                values.put(MediaStore.MediaColumns.DISPLAY_NAME, fileName);
                                values.put(MediaStore.MediaColumns.MIME_TYPE, mimeType);
                                values.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES + "/WanAndroid");
                                ContentResolver resolver = getContentResolver();
                                Uri insertUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                                try {
                                    if (insertUri != null) {
                                        os = resolver.openOutputStream(insertUri);
                                    }
                                    copy(resource, os);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                    Snackbar.make(binding.getRoot(), getString(R.string.failed_to_save_image), Snackbar.LENGTH_SHORT).show();
                                }
                            } else {
                                File targetFolder = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "WanAndroid");
                                if (!targetFolder.exists()) {
                                    if (!targetFolder.mkdirs()) {
                                        Snackbar.make(binding.getRoot(), getString(R.string.failed_to_save_image), Snackbar.LENGTH_SHORT).show();
                                    }
                                }
                                switch (mimeType) {
                                    case "image/png":
                                        fileName += ".png";
                                        break;
                                    case "image/gif":
                                        fileName += ".gif";
                                        break;
                                    case "image/webp":
                                        fileName += ".webp";
                                        break;
                                    default:
                                        fileName += ".jpg";
                                        break;
                                }
                                File targetFile = new File(targetFolder, fileName);
                                try {
                                    os = new FileOutputStream(targetFile);
                                    copy(resource, os);
                                    MediaScannerConnection.scanFile(getApplicationContext(),
                                            new String[]{targetFile.getAbsolutePath()}, new String[]{mimeType}, null);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                    Snackbar.make(binding.getRoot(), getString(R.string.failed_to_save_image), Snackbar.LENGTH_SHORT).show();
                                }
                            }
                        }

                        @Override
                        public void onLoadCleared(@Nullable Drawable placeholder) {

                        }
                    });
        }
    }

    private void copy(File file, OutputStream os) {
        InputStream is = null;
        try {
            is = new FileInputStream(file);
            byte[] buffer = new byte[4096];
            int byteCount;
            while ((byteCount = is.read(buffer)) != -1) {
                os.write(buffer, 0, byteCount);
            }
            Snackbar.make(binding.getRoot(), getString(R.string.saved_image), Snackbar.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Snackbar.make(binding.getRoot(), getString(R.string.failed_to_save_image), Snackbar.LENGTH_SHORT).show();
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
                if (os != null) {
                    os.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void shareImage(String imageUrl) {
        Glide.with(this)
                .asFile()
                .load(imageUrl)
                .into(new CustomTarget<File>() {
                    @Override
                    public void onResourceReady(@NonNull File resource, @Nullable Transition<? super File> transition) {
                        Uri uri = FileProvider.getUriForFile(getApplicationContext(), "com.lizhehan.wanandroid.fileprovider", resource);
                        Intent shareIntent = new Intent(Intent.ACTION_SEND);
                        shareIntent.setType("image/*");
                        shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
                        startActivity(Intent.createChooser(shareIntent, getString(R.string.share_image)));
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {

                    }
                });
    }

    private void copyLink(String link) {
        ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        if (clipboardManager != null) {
            clipboardManager.setPrimaryClip(ClipData.newPlainText("Label", link));
        }
        Snackbar.make(binding.getRoot(), getString(R.string.copied_link), Snackbar.LENGTH_SHORT).show();
    }

    private void openInBrowser(String link) {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(link)));
    }

    @Override
    public void collectArticleSuccess() {
        Snackbar.make(binding.getRoot(), getString(R.string.collected), Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void collectArticleError(String errorMsg) {
        Toast.makeText(this, errorMsg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void cancelCollectArticleSuccess() {

    }

    @Override
    public void cancelCollectArticleError(String errorMsg) {

    }

    @Override
    public void addToolSuccess() {
        Snackbar.make(binding.getRoot(), getString(R.string.collected), Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void addToolError(String errorMsg) {
        Toast.makeText(this, errorMsg, Toast.LENGTH_SHORT).show();
    }

    private void hookPopMenu(View view, PopupMenu popupMenu) {
        try {
            Field mPopup = popupMenu.getClass().getDeclaredField("mPopup");
            mPopup.setAccessible(true);
            MenuPopupHelper menuPopupHelper = (MenuPopupHelper) mPopup.get(popupMenu);
            Method show = null;
            if (menuPopupHelper != null) {
                show = menuPopupHelper.getClass().getMethod("show", int.class, int.class);
            }
            int[] position = new int[2];
            //获取view在屏幕上的坐标
            view.getLocationInWindow(position);
            x = (x - position[0]);
            y = (y - position[1] - view.getHeight());
            if (show != null) {
                show.invoke(menuPopupHelper, x, y);
            }
        } catch (NoSuchFieldException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        } finally {
            //出错时调用普通show方法。未出错时此方法也不会影响正常显示
            popupMenu.show();
        }
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
        getMenuInflater().inflate(R.menu.menu_web, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.collect:
                if (binding.webView.canGoBack() || id == -1) {
                    presenter.addTool(binding.webView.getTitle(), binding.webView.getUrl());
                } else {
                    presenter.collectArticle(id);
                }
                break;
            case R.id.share:
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                if (binding.webView.canGoBack() || TextUtils.isEmpty(title)) {
                    shareIntent.putExtra(Intent.EXTRA_TEXT, binding.webView.getTitle() + "\n" + binding.webView.getUrl());
                } else {
                    shareIntent.putExtra(Intent.EXTRA_TEXT, title + "\n" + link);
                }
                startActivity(Intent.createChooser(shareIntent, getString(R.string.share)));
                break;
            case R.id.open_in_browser:
                openInBrowser(binding.webView.getUrl());
                break;
            case R.id.copy_link:
                copyLink(link);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (binding.webView.canGoBack()) {
            binding.webView.goBack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                saveImage(tmpImageUrl);
            } else {
                Snackbar.make(binding.getRoot(), "You denied the permission", Snackbar.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onDestroy() {
        binding.webView.stopLoading();
        super.onDestroy();
    }
}
