package com.uo75.ernestoDuvalonUO.ui.options;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.uo75.ernestoDuvalonUO.R;

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
                    Toast toast = Toast.makeText(getActivity(), "Error de Conexión al servicio, además verifique su conexión a internet", Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        });
        return root;
    }

}