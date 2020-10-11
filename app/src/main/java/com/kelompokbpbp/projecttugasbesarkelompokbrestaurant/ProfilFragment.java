package com.kelompokbpbp.projecttugasbesarkelompokbrestaurant;

import android.content.Intent;
import android.location.Address;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;
import com.kelompokbpbp.projecttugasbesarkelompokbrestaurant.activity.login_activity.LoginActivity;
import com.kelompokbpbp.projecttugasbesarkelompokbrestaurant.adapter.AddressAdapter;
import com.kelompokbpbp.projecttugasbesarkelompokbrestaurant.database.AppPreference;
import com.kelompokbpbp.projecttugasbesarkelompokbrestaurant.database.DatabaseClient;
import com.kelompokbpbp.projecttugasbesarkelompokbrestaurant.model.Alamat;
import com.kelompokbpbp.projecttugasbesarkelompokbrestaurant.model.User;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfilFragment extends Fragment {
    private TextView tvName, tvUsername, tvPhoneNumber;
    private RecyclerView rvAddress;
    private AddressAdapter addressAdapter;
    private MaterialButton btnEdit, btnLogout;
    private CircleImageView photoProfile;
    private User dataUser;

    public ProfilFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profil, container, false);
        tvName = view.findViewById(R.id.profile_name);
        tvUsername = view.findViewById(R.id.profile_username);
        tvPhoneNumber = view.findViewById(R.id.profile_phone);
        photoProfile = view.findViewById(R.id.profile_photo);
        btnEdit = view.findViewById(R.id.btn_editProfile);
        btnLogout = view.findViewById(R.id.btn_logout);
        rvAddress = view.findViewById(R.id.rvAddress);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getAllAddress();
        rvAddress.setLayoutManager(new LinearLayoutManager(this.getContext()));
        rvAddress.setAdapter(addressAdapter);

        getUserProfile();
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditProfileFragment fragmentEditProfile = new EditProfileFragment();
                Bundle profileData = new Bundle();
                profileData.putSerializable("user_profile", dataUser);
                fragmentEditProfile.setArguments(profileData);
                getActivity().getSupportFragmentManager().beginTransaction().
                        replace(R.id.fragment_profile,fragmentEditProfile).commit();
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppPreference appPreference = new AppPreference(getContext());
                appPreference.setLoginUsername(null);
                Intent toLogin = new Intent(ProfilFragment.this.getContext(), LoginActivity.class);
                getActivity().finishAndRemoveTask();
                startActivity(toLogin);
            }
        });
    }

    private void getUserProfile() {
        class GetUserProfie extends AsyncTask<Void, Void, User> {

            @Override
            protected User doInBackground(Void... voids) {
                AppPreference appPreference = new AppPreference(ProfilFragment.this.getActivity().getApplicationContext());
                String username = appPreference.getLoginUsername();

                if(username != null){
                    dataUser = DatabaseClient.getInstance(getActivity().getApplicationContext())
                            .getAppDatabase()
                            .userDao()
                            .getUserProfile(username);
                    return dataUser;
                }

                return null;
            }

            @Override
            protected void onPostExecute(User user) {
                super.onPostExecute(user);
                if(user!=null) {
                    tvName.setText(user.getNama());
                    tvUsername.setText(user.getUsername());
                    tvPhoneNumber.setText(user.getNohp());
                    Glide.with(getContext())
                            .load(Uri.parse(user.getPhotoProfile()))
                            .into(photoProfile);
                }
            }
        }

        GetUserProfie getProfile = new GetUserProfie();
        getProfile.execute();
    }

    private void getAllAddress(){
        class GetAddress extends AsyncTask<Void,Void, List<Alamat>>{
            AppPreference appPreference = new AppPreference(ProfilFragment.this.getActivity().getApplicationContext());
            String username = appPreference.getLoginUsername();

            @Override
            protected List<Alamat> doInBackground(Void... voids) {

                List<Alamat> dataList = DatabaseClient.getInstance(ProfilFragment.this.getContext())
                        .getAppDatabase()
                        .addressDAO()
                        .getAllAlamat(username);

                return dataList;
            }

            @Override
            protected void onPostExecute(List<Alamat> alamats) {
                super.onPostExecute(alamats);
                addressAdapter = new AddressAdapter(alamats);
            }
        }

        GetAddress getAddress = new GetAddress();
        getAddress.execute();
    }
}