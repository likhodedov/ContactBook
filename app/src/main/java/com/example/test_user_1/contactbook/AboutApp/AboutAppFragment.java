package com.example.test_user_1.contactbook.AboutApp;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.test_user_1.contactbook.MainActivity;
import com.example.test_user_1.contactbook.R;
import com.example.test_user_1.contactbook.databinding.AboutFragmentBinding;

public class AboutAppFragment extends Fragment {
    AboutFragmentBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.about_fragment,container,false);

        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
        MainActivity.binding.drawerLayout.closeDrawer(Gravity.START);
        MainActivity.binding.toolbar.searchView.setQuery("", false);;
        MainActivity.binding.toolbar.searchView.setIconified(true);

        View view = binding.getRoot();
        return view;
    }
}
