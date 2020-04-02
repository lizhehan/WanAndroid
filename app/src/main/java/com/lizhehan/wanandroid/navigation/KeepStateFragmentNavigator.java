package com.lizhehan.wanandroid.navigation;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavDestination;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigator;
import androidx.navigation.fragment.FragmentNavigator;

@Navigator.Name("keep_state_fragment")
public class KeepStateFragmentNavigator extends FragmentNavigator {
    private Context mContext;
    private FragmentManager mFragmentManager;
    private int mContainerId;

    public KeepStateFragmentNavigator(@NonNull Context context, @NonNull FragmentManager manager, int containerId) {
        super(context, manager, containerId);
        mContext = context;
        mFragmentManager = manager;
        mContainerId = containerId;
    }

    @Nullable
    @Override
    public NavDestination navigate(@NonNull Destination destination, @Nullable Bundle args,
                                   @Nullable NavOptions navOptions, @Nullable Navigator.Extras navigatorExtras) {
        String tag = String.valueOf(destination.getId());
        FragmentTransaction transaction = mFragmentManager.beginTransaction();

        int enterAnim = navOptions != null ? navOptions.getEnterAnim() : -1;
        int exitAnim = navOptions != null ? navOptions.getExitAnim() : -1;
        int popEnterAnim = navOptions != null ? navOptions.getPopEnterAnim() : -1;
        int popExitAnim = navOptions != null ? navOptions.getPopExitAnim() : -1;
        if (enterAnim != -1 || exitAnim != -1 || popEnterAnim != -1 || popExitAnim != -1) {
            enterAnim = enterAnim != -1 ? enterAnim : 0;
            exitAnim = exitAnim != -1 ? exitAnim : 0;
            popEnterAnim = popEnterAnim != -1 ? popEnterAnim : 0;
            popExitAnim = popExitAnim != -1 ? popExitAnim : 0;
            transaction.setCustomAnimations(enterAnim, exitAnim, popEnterAnim, popExitAnim);
        }

        boolean initialNavigate = false;
        Fragment currentFragment = mFragmentManager.getPrimaryNavigationFragment();
        if (currentFragment != null) {
            //transaction.detach(currentFragment);
            transaction.hide(currentFragment);
        } else {
            initialNavigate = true;
        }
        Fragment fragment = mFragmentManager.findFragmentByTag(tag);
        if (fragment == null) {
            String className = destination.getClassName();
            fragment = mFragmentManager.getFragmentFactory().instantiate(mContext.getClassLoader(), className);
            fragment.setArguments(args);
            transaction.add(mContainerId, fragment, tag);
        } else {
            //transaction.attach(fragment);
            transaction.show(fragment);
        }

        transaction.setPrimaryNavigationFragment(fragment);
        transaction.setReorderingAllowed(true);
        transaction.commitNow();
        //return initialNavigate ? destination : null;
        return destination;
    }
}
