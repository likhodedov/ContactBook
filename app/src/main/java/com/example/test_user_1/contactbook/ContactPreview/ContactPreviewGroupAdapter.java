package com.example.test_user_1.contactbook.ContactPreview;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.example.test_user_1.contactbook.ContactBookModel.ContactBookManager;
import com.example.test_user_1.contactbook.ContactBookModel.ContactModel;
import com.example.test_user_1.contactbook.ContactBookModel.GroupModel;
import com.example.test_user_1.contactbook.MainActivity;
import com.example.test_user_1.contactbook.databinding.RecyclerGroupWithCheckboxBinding;

import java.util.List;

public class ContactPreviewGroupAdapter extends RecyclerView.Adapter<ContactPreviewGroupAdapter.ViewHolder> {
    private List<GroupModel> allGroupModelList;
    private List<GroupModel> personGroups;

    ContactPreviewGroupAdapter(List<GroupModel> allGroupModelList, List<GroupModel> personGroups){
        this.allGroupModelList = allGroupModelList;
        this.personGroups = personGroups;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        RecyclerGroupWithCheckboxBinding binding;

        ViewHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
            this.setIsRecyclable(false);
        }
    }

    @Override
    public ContactPreviewGroupAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        RecyclerGroupWithCheckboxBinding binding = RecyclerGroupWithCheckboxBinding.inflate(inflater,parent,false);
        return  new ContactPreviewGroupAdapter.ViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(ContactPreviewGroupAdapter.ViewHolder holder, int position) {
        final GroupModel tempGroupModel = allGroupModelList.get(position);
        holder.binding.setGroupModel(tempGroupModel);

        if(position == 0){ //блочим checkbox для All
            holder.binding.checkbox.setChecked(true);
            holder.binding.checkbox.setEnabled(false);
        }

        for (GroupModel groupModel : personGroups)
            if (groupModel.getGroupName().equals(tempGroupModel.getGroupName()))
                holder.binding.checkbox.setChecked(true);

        holder.binding.checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    personGroups.add(new GroupModel(tempGroupModel.getGroupName(),0));
                    ContactBookManager.getInstance().getGroupModelByName(tempGroupModel.getGroupName()).addCount();
                    MainActivity.adapter.notifyDataSetChanged();
                } else {
                    for (int i=0;i<personGroups.size();i++)
                        if (personGroups.get(i).getGroupName().equals(tempGroupModel.getGroupName())){
                            personGroups.remove(i);
                            ContactBookManager.getInstance().getGroupModelByName(tempGroupModel.getGroupName()).removeCount();
                            MainActivity.adapter.notifyDataSetChanged();
                        }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return allGroupModelList.size();
    }
}
