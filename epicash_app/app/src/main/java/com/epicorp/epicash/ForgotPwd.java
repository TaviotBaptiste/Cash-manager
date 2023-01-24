package com.epicorp.epicash;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.navigation.fragment.NavHostFragment;
import com.epicorp.epicash.databinding.FragmentForgotPwdBinding;
import com.epicorp.epicash.databinding.FragmentProfileBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ForgotPwd#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ForgotPwd extends Fragment {



    private FragmentForgotPwdBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        binding = FragmentForgotPwdBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.EpicashForgotPasswordButtonRetour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(ForgotPwd.this)
                        .navigate(R.id.action_forgotPwd_to_LoginFragmentBinding);
            }
        });
        binding.EpicashForgotpwdButtonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(ForgotPwd.this)
                        .navigate(R.id.action_forgotPwd_to_LoginFragmentBinding);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}