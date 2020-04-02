package com.lizhehan.wanandroid.widget;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.lizhehan.wanandroid.databinding.FragmentEditWebsiteBottomSheetDialogBinding;

public class EditWebsiteBottomSheetDialogFragment extends BottomSheetDialogFragment {

    private FragmentEditWebsiteBottomSheetDialogBinding binding;

    private String name;
    private String link;
    private OnClickListener onClickListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentEditWebsiteBottomSheetDialogBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
        binding.completeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickListener != null) {
                    onClickListener.onClick(binding.nameEditText.getText().toString(), binding.linkEditText.getText().toString());
                }
                dismiss();
            }
        });
        binding.cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    private void initData() {
        if (name != null) {
            binding.nameEditText.setText(name);
        }
        if (link != null) {
            binding.linkEditText.setText(link);
        }
    }

    public EditWebsiteBottomSheetDialogFragment setNameAndLink(String name, String link) {
        this.name = name;
        this.link = link;
        return this;
    }

    public EditWebsiteBottomSheetDialogFragment setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
        return this;
    }

    public interface OnClickListener {
        void onClick(String name, String link);
    }
}
