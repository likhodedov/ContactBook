package com.example.test_user_1.contactbook.ContactList;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.test_user_1.contactbook.ContactBookModel.ContactBookManager;
import com.example.test_user_1.contactbook.ContactBookModel.ContactModel;
import com.example.test_user_1.contactbook.MainActivity;
import com.example.test_user_1.contactbook.R;
import com.example.test_user_1.contactbook.databinding.ActivityMainBinding;
import com.example.test_user_1.contactbook.databinding.ContactListFragmentBinding;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


public class ContactListFragment extends Fragment implements SearchView.OnQueryTextListener{

    private List<ContactModel> currentContactModel;
    ContactListAdapter adapter;

    private static final Comparator<ContactModel> ALPHABETICAL_COMPARATOR = new Comparator<ContactModel>() {
        @Override
        public int compare(ContactModel o1, ContactModel o2) {
            return o1.getFirstName().compareTo(o2.getFirstName());
        }
    };

    ContactListFragmentBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.contact_list_fragment,container,false);

        MainActivity.binding.toolbar.searchView.setQuery("", false);;
        MainActivity.binding.toolbar.searchView.setIconified(true);
        MainActivity.binding.toolbar.searchView.setOnQueryTextListener(this);
        ((AppCompatActivity)getActivity()).getSupportActionBar().show();

        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        binding.contactListRecycler.setLayoutManager(layoutManager);


        String bundleText = "All";
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            bundleText = bundle.getString("group");
        }
        if (bundleText.equals("All"))
            currentContactModel = ContactBookManager.getInstance().getContactModels();
        else
            currentContactModel = ContactBookManager.getInstance().getContactModelsByGroup(bundleText);

        binding.groupTitle.setText(bundleText);
        adapter = new ContactListAdapter(getContext(),ALPHABETICAL_COMPARATOR,getActivity().getSupportFragmentManager());
        adapter.add(currentContactModel);
        binding.contactListRecycler.setAdapter(adapter);

        View view = binding.getRoot();
        return view;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        MainActivity.binding.toolbar.searchView.clearFocus();
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        final List<ContactModel> filteredModelList = filter(currentContactModel,newText);
        adapter.replaceAll(filteredModelList);
        binding.contactListRecycler.scrollToPosition(0);
        return true;
    }

    private static List<ContactModel> filter(List<ContactModel> models, String query) {

        final String lowerCaseQuery = query.toLowerCase();
        final List<ContactModel> filteredModelList = new ArrayList<>();

        for (ContactModel model : models) {
            final String text = model.getFirstName().toLowerCase();
            if (text.contains(lowerCaseQuery)) {
                filteredModelList.add(model);
            }
        }
        return filteredModelList;
    }
}
