package com.digipodium.www.objectdetector;

import android.Manifest;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.digipodium.www.objectdetector.databinding.FragmentPermissionBinding;

import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;


public class PermissionFragment extends Fragment implements EasyPermissions.PermissionCallbacks {


    private FragmentPermissionBinding bind;
    private String[] perms= new String[]{Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_permission, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bind = FragmentPermissionBinding.bind(view);
        if(EasyPermissions.hasPermissions(getActivity(),perms)){
           moveOn(true);
        }
        bind.btnPerm.setOnClickListener(view1 -> {
            EasyPermissions.requestPermissions(getActivity(),"request permissions",23,perms);
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode,permissions,grantResults,this);
    }

    private void moveOn(boolean state) {
        NavHostFragment.findNavController(this).navigate(R.id.action_permissionFragment_to_loginFragment);
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        moveOn(true);
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        moveOn(false);
    }
}