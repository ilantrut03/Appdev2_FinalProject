package com.example.project;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.Task;
//import com.google.firebase.auth.AuthResult;
//import com.google.firebase.auth.FirebaseAuth;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SignUpFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SignUpFragment extends Fragment {

    FirebaseAuth fAuth = FirebaseAuth.getInstance();;
    FirebaseFirestore fStore;

    // TODO: Rename parameter arguments, choose names that match
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SignUpFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SignUpFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SignUpFragment newInstance(String param1, String param2) {
        SignUpFragment fragment = new SignUpFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fStore = FirebaseFirestore.getInstance();
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);

        EditText firstNameInfo          = view.findViewById(R.id.firstNameField);
        EditText lastNameInfo           = view.findViewById(R.id.lastNameField);
        EditText phoneInfo              = view.findViewById(R.id.phoneField);
        EditText emailInfo              = view.findViewById(R.id.emailField);
        EditText passwordInfo           = view.findViewById(R.id.passwordField);
        EditText confirmPasswordInfo    = view.findViewById(R.id.confirmPasswordField);
        Button register                 = view.findViewById(R.id.registerbutton);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast message = null;

                String firstName        = firstNameInfo.getText().toString();
                String lastName         = lastNameInfo.getText().toString();
                String phone            = phoneInfo.getText().toString();
                String email            = emailInfo.getText().toString();
                String password         = passwordInfo.getText().toString();
                String confirmPassword  = confirmPasswordInfo.getText().toString();

                if (firstName.isEmpty() || lastName.isEmpty() || phone.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                    message = Toast.makeText(getActivity(), "Please fill all the fields!", Toast.LENGTH_LONG);
                    message.show();
                }

                if (!firstName.isEmpty() && !lastName.isEmpty() && !phone.isEmpty() && !email.isEmpty() && !password.isEmpty() && !confirmPassword.isEmpty() && password.length() < 6) {
                    if (message != null) {
                        message.cancel();
                    }
                    message = Toast.makeText(getActivity(), "Your password has to contain at least 6 characters!", Toast.LENGTH_LONG);
                    message.show();
                }

                if (!password.equals(confirmPassword)) {
                    if (message != null) {
                        message.cancel();
                    }
                    message = Toast.makeText(getActivity(), "Your passwords do not match!", Toast.LENGTH_LONG);
                    message.show();
                }

                if (!firstName.isEmpty() &&
                        !lastName.isEmpty() &&
                        !phone.isEmpty() &&
                        !email.isEmpty() &&
                        !password.isEmpty() &&
                        !confirmPassword.isEmpty() &&
                        (password.equals(confirmPassword)) &&
                        password.length() >= 6) {
                    fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                String userID = fAuth.getCurrentUser().getUid();
                                DocumentReference reference  = fStore.collection("Users").document(userID);
                                Map<String, Object> user = new HashMap<>();
                                user.put("First Name", firstName);
                                user.put("Last Name", lastName);
                                user.put("Phone", phone);
                                user.put("Email", email);
                                reference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {

                                    }
                                });
                            }
                        }
                    });

                    if (message != null) { message.cancel(); }

                    message = Toast.makeText(getActivity(), "You have registered successfully!", Toast.LENGTH_LONG);
                    message.show();

                    message = Toast.makeText(getActivity(), "Please sign in to continue!", Toast.LENGTH_LONG);
                    message.show();
                }
            }
        });
        return view;
    }
}