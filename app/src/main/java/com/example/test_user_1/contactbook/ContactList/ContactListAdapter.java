package com.example.test_user_1.contactbook.ContactList;


import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.util.SortedList;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.test_user_1.contactbook.ContactBookModel.ContactModel;
import com.example.test_user_1.contactbook.ContactPreview.ContactPreviewFragment;
import com.example.test_user_1.contactbook.MainActivity;
import com.example.test_user_1.contactbook.R;
import com.example.test_user_1.contactbook.databinding.RecyclerContactItemBinding;

import java.util.Comparator;
import java.util.List;


public class ContactListAdapter extends RecyclerView.Adapter<ContactListAdapter.ViewHolder> {

    FragmentManager fragmentManager;

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        RecyclerContactItemBinding binding;

        public ViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            binding = DataBindingUtil.bind(view);
        }

        @Override
        public void onClick(View view) {
            Bundle bundle = new Bundle();
            bundle.putInt("id",binding.getContactModel().getId());
            ContactPreviewFragment contactPreviewFragment = new ContactPreviewFragment();
            contactPreviewFragment.setArguments(bundle);
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.setCustomAnimations(
                    R.anim.slide_right,
                    R.anim.slide_left,
                    R.anim.slide_left_reverse,
                    R.anim.slide_right_reverse);
            fragmentTransaction.replace(R.id.flContent,contactPreviewFragment);
            fragmentTransaction.addToBackStack("f1");
            fragmentTransaction.commit();
        }
    }

    private final SortedList<ContactModel> sortedList= new SortedList<>(ContactModel.class, new SortedList.Callback<ContactModel>() {

        @Override
        public void onInserted(int position, int count) {
            notifyItemRangeChanged(position,count);
        }

        @Override
        public void onRemoved(int position, int count) {
            notifyItemRangeRemoved(position, count);
        }

        @Override
        public void onMoved(int fromPosition, int toPosition) {
            notifyItemMoved(fromPosition, toPosition);
        }

        @Override
        public int compare(ContactModel a, ContactModel b) {
            return comparator.compare(a,b);
        }

        @Override
        public void onChanged(int position, int count) {
            notifyItemRangeChanged(position, count);
        }

        @Override
        public boolean areContentsTheSame(ContactModel oldItem, ContactModel newItem) {
            return oldItem.equals(newItem);
        }

        @Override
        public boolean areItemsTheSame(ContactModel item1, ContactModel item2) {
            return item1 == item2;
        }
    });


    private final Comparator<ContactModel> comparator;
    private final LayoutInflater Inflater;



    public ContactListAdapter(Context context, Comparator<ContactModel> comparator,FragmentManager fragmentManager ) {
        Inflater = LayoutInflater.from(context);
        this.comparator = comparator;
        this.fragmentManager = fragmentManager;
    }

    @Override
    public ContactListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        RecyclerContactItemBinding binding = RecyclerContactItemBinding.inflate(inflater,parent,false);
        return new ContactListAdapter.ViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(ContactListAdapter.ViewHolder holder, int position) {
        ContactModel contactModel = sortedList.get(position);

        final LinearLayoutManager layoutManager = new LinearLayoutManager(MainActivity.context.getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        holder.binding.contactGroupRecycler.setLayoutManager(layoutManager);
        ContactItemGroupAdapter contactItemGroupAdapter = new ContactItemGroupAdapter(contactModel.getGroupModelList());
        holder.binding.contactGroupRecycler.setAdapter(contactItemGroupAdapter);

        holder.binding.setContactModel(contactModel);
    }

    @Override
    public int getItemCount() {
        return sortedList.size();
    }

    public void add(ContactModel model) {
        sortedList.add(model);
    }

    public void remove(ContactModel model) {
        sortedList.remove(model);
    }

    public void add(List<ContactModel> models) {
        sortedList.addAll(models);
    }

    public void remove(List<ContactModel> models) {
        sortedList.beginBatchedUpdates();
        for (ContactModel model : models) {
            sortedList.remove(model);
        }
        sortedList.endBatchedUpdates();
    }

    public void replaceAll(List<ContactModel> models) {
        sortedList.beginBatchedUpdates();
        for (int i = sortedList.size() - 1; i >= 0; i--) {
            final ContactModel model = sortedList.get(i);
            if (!models.contains(model)) {
                sortedList.remove(model);
            }
        }
        sortedList.addAll(models);
        sortedList.endBatchedUpdates();
    }


}
