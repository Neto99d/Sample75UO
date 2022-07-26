package com.neto.sample75uo.ui.options;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.neto.sample75uo.R;

public class ResennaHistoryFragment extends Fragment {

    private ResennaHistoryViewModel mViewModel;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(ResennaHistoryViewModel.class);
        // TODO: Use the ViewModel
        View root = inflater.inflate(R.layout.resenna_history_fragment, container, false);
        final TextView textView = root.findViewById(R.id.textReseña);
        mViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);

            }
        });
        /// Ver estado de conexion con el Servicio
        mViewModel.getStatus().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (!mViewModel.getStatus().getValue()) {
                    Toast toast = Toast.makeText(getActivity(), "Error de Conexión con el servicio", Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        });
        return root;
    }

}