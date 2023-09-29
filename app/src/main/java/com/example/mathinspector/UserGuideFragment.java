package com.example.mathinspector;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class UserGuideFragment extends Fragment {
    private FragmentCallback fragmentCallback;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_guide_fragment, container, false);
        Button closeFragmentButton = view.findViewById(R.id.closeFragmentButton);
        closeFragmentButton.setOnClickListener(v -> {
            closeFragment();
        });
        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof FragmentCallback) {
            fragmentCallback = (FragmentCallback) context;
        } else {
            throw new RuntimeException("Activity must implement FragmentCallback interface !");
        }
    }

    private void closeFragment() {
        if (fragmentCallback != null) {
            fragmentCallback.closeFragment();
        }
    }

    public interface FragmentCallback {
        void closeFragment();
    }
}

