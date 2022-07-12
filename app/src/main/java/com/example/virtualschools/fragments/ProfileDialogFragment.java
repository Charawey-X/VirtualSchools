package com.example.virtualschools.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.virtualschools.R;
import com.example.virtualschools.ui.ProfileActivity;

public class ProfileDialogFragment extends AppCompatDialogFragment {

    private EditText editUsername;
    private ProfileDialogFragmentListener listener;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.edit_profile_dialog, null);
        builder.setView(view)
                .setTitle("EDIT PROFILE")
                .setNegativeButton("CANCEL", (dialog, which) -> {

                })
                .setPositiveButton("SAVE", (dialog, which) -> {
                    String username = editUsername.getText().toString();
                    listener.applyTexts(username);
                    startActivity(new Intent(getContext(), ProfileActivity.class));
                });
        editUsername = view.findViewById(R.id.update_name);
        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener =(ProfileDialogFragmentListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString());
        }
    }

    public interface ProfileDialogFragmentListener{
        void applyTexts(String username);
    }
}
