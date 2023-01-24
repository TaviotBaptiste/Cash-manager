package com.epicorp.epicash;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.navigation.fragment.NavHostFragment;
import com.epicorp.epicash.databinding.FragmentBillBinding;

public class BillFragment extends Fragment {

    private FragmentBillBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentBillBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.EpicashBillButtonAjoutCarte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(BillFragment.this)
                        .navigate(R.id.action_billFragment_to_cardFragment);
            }
        });

        binding.EpicashBillButtonRetour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(BillFragment.this)
                        .navigate(R.id.action_billFragment_to_homeFragment);
            }
        });
        binding.EpicashBillButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(BillFragment.this)
                        .navigate(R.id.action_billFragment_to_endFragment);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}