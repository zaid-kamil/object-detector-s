package com.digipodium.www.objectdetector;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.digipodium.www.objectdetector.databinding.FragmentSignupBinding;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class SignupFragment extends Fragment {

    private FirebaseAuth auth;
    private FragmentSignupBinding bind;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        auth = FirebaseAuth.getInstance();
        return inflater.inflate(R.layout.fragment_signup, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bind = FragmentSignupBinding.bind(view);
        bind.fabRegister.setOnClickListener(view1 -> {
            String email = bind.editUEmail.getText().toString();
            String username = bind.editusername.getText().toString();
            String password = bind.editUPass.getText().toString();
            String cpassword = bind.editPassConfirm.getText().toString();
            bind.pRegitser.setVisibility(View.VISIBLE);
            if (email.length() >= 10) {
                if (!username.trim().isEmpty()) {
                    if (password.length() >= 8) {
                        if (cpassword.equals(password)) {
                            auth.createUserWithEmailAndPassword(email, password)
                                    .addOnSuccessListener(authResult -> {
                                        FirebaseUser user = authResult.getUser();
                                        UserProfileChangeRequest req = new UserProfileChangeRequest.Builder().setDisplayName(username).build();
                                        user.updateProfile(req);
                                        updateUI(user);
                                    })
                                    .addOnFailureListener(e -> {
                                        Snackbar.make(bind.getRoot(), e.getMessage(), BaseTransientBottomBar.LENGTH_LONG).show();
                                        updateUI(null);

                                    });
                        } else {
                            bind.editusername.setError("username must be provided");
                            bind.editusername.requestFocus();
                            updateUI(null);
                        }
                    } else {
                        bind.editUEmail.setError("Email is invalid");
                        bind.editUEmail.requestFocus();
                        updateUI(null);
                    }
                } else {
                    bind.editUPass.setError("Password is invalid");
                    bind.editUPass.requestFocus();
                    updateUI(null);
                }
            } else {
                bind.editPassConfirm.setError("Password do not match");
                bind.editPassConfirm.requestFocus();
                updateUI(null);
            }
        });


    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {
            NavHostFragment.findNavController(this).navigate(R.id.action_signupFragment_to_homeFragment);
        }
        bind.pRegitser.setVisibility(View.GONE);
    }
}