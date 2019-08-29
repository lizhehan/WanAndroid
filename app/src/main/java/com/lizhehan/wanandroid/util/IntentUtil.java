package com.lizhehan.wanandroid.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class IntentUtil {

    /**
     * 不带参跳转
     */
    public static void startActivity(Context context, Class<? extends Activity> targetClass) {
        Intent mIntent = new Intent(context, targetClass);
        mIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        context.startActivity(mIntent);
    }

    /**
     * 带参数跳转
     */
    public static void startActivity(Context context, Class<? extends Activity> targetClass, Bundle bundle) {
        Intent mIntent = new Intent(context, targetClass);
        mIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        if (bundle != null) {
            mIntent.putExtras(bundle);
        }
        context.startActivity(mIntent);
    }
}
