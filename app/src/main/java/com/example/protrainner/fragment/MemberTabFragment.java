package com.example.protrainner.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.protrainner.R;
import com.example.protrainner.activity.MainActivity;
import com.example.protrainner.activity.RegisterMemberActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MemberTabFragment extends Fragment {

    EditText inp_email;
    TextInputLayout lyttext;
    EditText inp_password;
    TextView forgetpass;
    Button button;
    float v=0;
    FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState){
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.member_tab_frag, container, false);

        mAuth = FirebaseAuth.getInstance();

        inp_email = root.findViewById(R.id.email);
        inp_password = root.findViewById(R.id.pass);
        lyttext = root.findViewById(R.id.passlyt);
        forgetpass = root.findViewById(R.id.forgetpass);
        button = root.findViewById(R.id.button);


        inp_email.setTranslationX(800);
        lyttext.setTranslationX(800);
        forgetpass.setTranslationX(800);
        button.setTranslationX(800);

        inp_email.setAlpha(v);
        lyttext.setAlpha(v);
        forgetpass.setAlpha(v);
        button.setAlpha(v);

        inp_email.animate().translationX(0).alpha(1).setDuration(1000).setStartDelay(300).start();
        lyttext.animate().translationX(0).alpha(1).setDuration(1000).setStartDelay(500).start();
        forgetpass.animate().translationX(0).alpha(1).setDuration(1000).setStartDelay(600).start();
        button.animate().translationX(0).alpha(1).setDuration(1000).setStartDelay(700).start();

        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser mFirebaseUser = mAuth.getCurrentUser();
                if(mFirebaseUser !=null){
                    Intent i = new Intent(getActivity(), MainActivity.class);
                }
            }
        };

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = inp_email.getText().toString();
                String pass = inp_password.getText().toString();

                if(email.isEmpty()){
                    inp_email.setError("Please enter your email");
                }
                else if(pass.isEmpty()){
                    inp_password.setError("Please enter your password");
                }

                else if(!(email.isEmpty() &&  pass.isEmpty() )){
                    mAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful()){
                                Toast.makeText(getActivity(),"Login error! coba lagi",Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Intent inthome =new Intent(getActivity(),MainActivity.class);
                                startActivity(inthome);
                                Toast.makeText(getActivity(),"Login Succesfull",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

        return root;
    }

    @Override
    public void onStart(){
        super.onStart();
        mAuth.addAuthStateListener(mAuthStateListener);
    }


}
