package com.digipodium.www.objectdetector;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.digipodium.www.objectdetector.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private  FragmentHomeBinding bind;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bind = FragmentHomeBinding.bind(view);

        bind.cardCamera.setOnClickListener(view1 -> {
            Intent intent = new Intent(getActivity(), DetectorActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        });
        bind.cardGallery.setOnClickListener(view1 -> {
            Intent intent = new Intent(getActivity(), GalleryActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        });
        bind.cardSettings.setOnClickListener(view1 -> {
            NavHostFragment.findNavController(this).navigate(R.id.action_homeFragment_to_settingFragment);
        });
    }
}