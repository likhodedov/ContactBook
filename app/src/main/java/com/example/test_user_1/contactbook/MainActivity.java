package com.example.test_user_1.contactbook;

import android.content.Context;
import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.util.SortedList;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.util.SortedListAdapterCallback;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;

import com.example.test_user_1.contactbook.AboutApp.AboutAppFragment;
import com.example.test_user_1.contactbook.ContactBookModel.ContactBookManager;
import com.example.test_user_1.contactbook.ContactBookModel.GroupModel;
import com.example.test_user_1.contactbook.ContactList.ContactListFragment;
import com.example.test_user_1.contactbook.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static ActivityMainBinding binding;
    ActionBarDrawerToggle actionBarDrawerToggle;
    public static Context context;
    public static GroupAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        setupDrawerToolbar();
        context = getApplicationContext();

        final LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        adapter = new GroupAdapter(ContactBookManager.getInstance().getGroupModels(),getSupportFragmentManager());
        binding.drawerWrap.groupNameRecycler.setLayoutManager(layoutManager);
        binding.drawerWrap.groupNameRecycler.setAdapter(adapter);

        if (ContactBookManager.getInstance().getGroupModels().size()>0){
            Bundle bundle = new Bundle();
            bundle.putString("group",ContactBookManager.getInstance().getGroupModels().get(0).getGroupName());

            ContactListFragment contactListFragment = new ContactListFragment();
            contactListFragment.setArguments(bundle);
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

            fragmentTransaction.replace(R.id.flContent,contactListFragment);
            fragmentTransaction.commit();
        }

        binding.drawerWrap.exitApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(MainActivity.this)
                        .setMessage(R.string.exit_message)
                        .setCancelable(false)
                        .setPositiveButton(R.string.exit_message_apply, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                MainActivity.this.finish();
                            }
                        })
                        .setNegativeButton(R.string.exit_message_cancel, null)
                        .show();
            }
        });

        binding.drawerWrap.about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AboutAppFragment aboutAppFragment = new AboutAppFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(
                        R.anim.slide_right,
                        R.anim.slide_left,
                        R.anim.slide_left_reverse,
                        R.anim.slide_right_reverse);
                fragmentTransaction.replace(R.id.flContent,aboutAppFragment);
                fragmentTransaction.addToBackStack("f5");
                fragmentTransaction.commit();
            }
        });
    }

    private void setupDrawerToolbar(){
        setSupportActionBar(binding.toolbar.toolbarMain);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this,binding.drawerLayout,binding.toolbar.toolbarMain,R.string.app_name,R.string.app_name);
        binding.drawerLayout.addDrawerListener(actionBarDrawerToggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        actionBarDrawerToggle.syncState();
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() >= 1) {
            if (binding.drawerLayout.isDrawerOpen(GravityCompat.START))
                binding.drawerLayout.closeDrawer(Gravity.LEFT);
            getSupportFragmentManager().popBackStack();
        } else {
            moveTaskToBack(true);
        }
    }
}
