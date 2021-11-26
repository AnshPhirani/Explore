package com.example.explore;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

public class Fragment1 extends Fragment implements View.OnClickListener {

    ImageView imageView;
    TextView nameEt, profEt, bioEt, emailEt, webEt;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment1, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        imageView = getActivity().findViewById(R.id.image_f1);
        nameEt = getActivity().findViewById(R.id.tv_name_f1);
        profEt = getActivity().findViewById(R.id.tv_prof_f1);
        bioEt = getActivity().findViewById(R.id.tv_bio_f1);
        emailEt = getActivity().findViewById(R.id.tv_email_f1);
        webEt = getActivity().findViewById(R.id.tv_web_f1);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){

        }
    }

    @Override
    public void onStart() {
        super.onStart();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userId = user.getUid();

        DocumentReference reference;
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        reference = firestore.collection("user").document(userId);
        reference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.getResult().exists()){
                    String nameResult = task.getResult().getString("name");
                    String emailResult = task.getResult().getString("email");
                    String bioResult = task.getResult().getString("bio");
                    String webResult = task.getResult().getString("web");
                    String url = task.getResult().getString("url");
                    String profResult = task.getResult().getString("prof");

                    Picasso.get().load(url).into(imageView);
                    nameEt.setText(nameResult);
                    bioEt.setText(bioResult);
                    emailEt.setText(emailResult);
                    profEt.setText(profResult);
                    webEt.setText(webResult);

                }
                else{
                    Intent intent = new Intent(getActivity(), CreateProfileActivity.class);
                    startActivity(intent);
                }
            }
        });

    }
}
