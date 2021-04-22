package com.digipodium.www.objectdetector;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.digipodium.www.objectdetector.databinding.FragmentLoginBinding;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class LoginFragment extends Fragment {

    private FirebaseAuth auth;
    private FragmentLoginBinding bind;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        auth = FirebaseAuth.getInstance();
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bind = FragmentLoginBinding.bind(view);
        bind.fabLogin.setOnClickListener(V -> {
            String email = bind.editEmail.getText().toString();
            String password = bind.editPass.getText().toString();

            if (email.length() >= 10) {
                if (password.length() >= 8) {
                    bind.pLogin.setVisibility(View.VISIBLE);
                    auth.signInWithEmailAndPassword(email, password).addOnSuccessListener(authResult -> {
                        updateUI(authResult.getUser());
                    }).addOnFailureListener(e -> {
                        Snackbar.make(bind.getRoot(), "error:" + e.getMessage(), BaseTransientBottomBar.LENGTH_LONG).show();
                        updateUI(null);
                    });
                } else {
                    bind.editEmail.setError("Username invalid");
                    bind.editEmail.requestFocus();
                    updateUI(null);
                }
            } else {
                bind.editPass.setError("Password invalid");
                bind.editPass.requestFocus();
                updateUI(null);
            }

        });

        bind.textCreateAccount.setOnClickListener(view1 -> {
            NavHostFragment.findNavController(this).navigate(R.id.action_loginFragment_to_signupFragment);
        });
    }


    @Override
    public void onStart() {
        super.onStart();
        updateUI(auth.getCurrentUser());
    }

    private void updateUI(FirebaseUser user) {
        bind.pLogin.setVisibility(View.GONE);
        if (user != null) {
            NavHostFragment.findNavController(this).navigate(R.id.action_loginFragment_to_homeFragment);
        }
    }
}