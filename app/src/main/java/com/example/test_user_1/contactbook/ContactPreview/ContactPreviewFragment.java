package com.example.test_user_1.contactbook.ContactPreview;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.test_user_1.contactbook.ContactBookModel.ContactBookManager;
import com.example.test_user_1.contactbook.ContactBookModel.ContactModel;
import com.example.test_user_1.contactbook.MainActivity;
import com.example.test_user_1.contactbook.R;
import com.example.test_user_1.contactbook.databinding.DetailContactFragmentBinding;

public class ContactPreviewFragment extends Fragment{
    DetailContactFragmentBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.detail_contact_fragment,container,false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();

        int bundleId = 0;
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            bundleId = bundle.getInt("id");
        }
        ContactModel model = ContactBookManager.getInstance().getContactModelById(bundleId);
        binding.setContactModel(model);

        ContactPreviewGroupAdapter adapter = new ContactPreviewGroupAdapter(ContactBookManager.getInstance().getGroupModels(),model.getGroupModelList());
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        binding.contactPreviewGroupRecycler.setLayoutManager(layoutManager);
        binding.contactPreviewGroupRecycler.setAdapter(adapter);

        View view = binding.getRoot();
        return view;
    }
}
