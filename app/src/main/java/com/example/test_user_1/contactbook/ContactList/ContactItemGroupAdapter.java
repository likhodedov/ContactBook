package com.example.test_user_1.contactbook.ContactList;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.test_user_1.contactbook.ContactBookModel.GroupModel;
import com.example.test_user_1.contactbook.databinding.RecyclerContactGroupItemBinding;

import java.util.List;



public class ContactItemGroupAdapter extends RecyclerView.Adapter<ContactItemGroupAdapter.ViewHolder> {
    private List<GroupModel> groupModelList;

    ContactItemGroupAdapter(List<GroupModel> groupModelList) {
        this.groupModelList = groupModelList;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        RecyclerContactGroupItemBinding binding;

        ViewHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }
    }


    @Override
    public ContactItemGroupAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        RecyclerContactGroupItemBinding binding = RecyclerContactGroupItemBinding.inflate(inflater,parent,false);
        return  new ContactItemGroupAdapter.ViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(ContactItemGroupAdapter.ViewHolder holder, int position) {
        GroupModel groupModel = groupModelList.get(position);
        holder.binding.setContactItemGroup(groupModel);
    }

    @Override
    public int getItemCount() {
        return groupModelList.size();
    }
}
