package com.example.test_user_1.contactbook;

import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.test_user_1.contactbook.ContactBookModel.GroupModel;
import com.example.test_user_1.contactbook.ContactList.ContactListFragment;
import com.example.test_user_1.contactbook.databinding.RecyclerDrawerItemBinding;

import java.util.List;



public class GroupAdapter extends RecyclerView.Adapter<GroupAdapter.ViewHolder> {

    private List<GroupModel> groupModelList;
    FragmentManager fragmentManager;

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        RecyclerDrawerItemBinding binding;

        ViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            binding = DataBindingUtil.bind(view);
        }

        @Override
        public void onClick(View v) {
            if (!binding.getGroupModel().getCount().equals("0")){
                MainActivity.binding.drawerLayout.closeDrawer(Gravity.START);
                Bundle bundle = new Bundle();
                bundle.putString("group",binding.getGroupModel().getGroupName());
                ContactListFragment contactListFragment = new ContactListFragment();
                contactListFragment.setArguments(bundle);
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.addToBackStack("f3");
                fragmentTransaction.replace(R.id.flContent,contactListFragment);
                fragmentTransaction.commit();
            } else
                Toast.makeText(MainActivity.context.getApplicationContext(),R.string.group_count_error,Toast.LENGTH_SHORT).show();
        }
    }

    public GroupAdapter(List<GroupModel> groupModelList,FragmentManager fragmentManager){
        this.groupModelList = groupModelList;
        this.fragmentManager = fragmentManager;
    }

    @Override
    public GroupAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        RecyclerDrawerItemBinding binding = RecyclerDrawerItemBinding.inflate(inflater,parent,false);
        return new GroupAdapter.ViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(GroupAdapter.ViewHolder holder, int position) {
        GroupModel groupModel = groupModelList.get(position);
        holder.binding.setGroupModel(groupModel);
    }

    @Override
    public int getItemCount() {
        return groupModelList.size();
    }
}
