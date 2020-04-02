package com.lizhehan.wanandroid.navigation;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

public class KeepStateNavHostFragment extends NavHostFragment {
    @Override
    protected void onCreateNavController(@NonNull NavController navController) {
        super.onCreateNavController(navController);
        navController.getNavigatorProvider().addNavigator(new KeepStateFragmentNavigator(requireContext(), getChildFragmentManager(), getId()));
    }
}
