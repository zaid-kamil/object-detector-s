package com.digipodium.www.objectdetector;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.digipodium.www.objectdetector.databinding.FragmentSettingBinding;
import com.google.firebase.auth.FirebaseAuth;


public class SettingFragment extends Fragment {


    private FragmentSettingBinding bind;
    private int nightMode;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_setting, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bind = FragmentSettingBinding.bind(view);
        nightMode = AppCompatDelegate.getDefaultNightMode();
        bind.btnSignOut.setOnClickListener(view1 -> {
            FirebaseAuth.getInstance().signOut();
            NavHostFragment.findNavController(this).navigate(R.id.action_settingFragment_to_loginFragment);
        });


        bind.stts.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                Toast.makeText(getContext(), "functionality not available on this version", Toast.LENGTH_SHORT).show();
                new Handler().postDelayed(() -> compoundButton.setChecked(!b), 1000);
            }
        });
        bind.sPermission.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                Toast.makeText(getContext(), "data cleared", Toast.LENGTH_SHORT).show();
                new Handler().postDelayed(() -> compoundButton.setChecked(!b), 2000);
            }
        });
    }
}