package com.example.test_user_1.contactbook.ContactBookModel;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.net.Uri;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.file.FileDecoder;
import com.bumptech.glide.request.RequestOptions;

import java.io.File;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ContactModel {

    private int id;
    private String firstName;
    private String lastName;
    private List<GroupModel> groupModelList;
    private String imageUri;
    private String phoneNumber;
    private String address;

    /**
     *
     * @param id - порядковый номер
     * @param firstName - имя
     * @param lastName - фамилия
     * @param groupModelList - список групп
     * @param imageUri - ссылка на изображение в ассетах
     * @param phoneNumber - телефонный номер
     * @param address - адрес
     */
    public ContactModel (int id, String firstName, String lastName, List<GroupModel> groupModelList,
                         String imageUri, String phoneNumber,String address){
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.groupModelList = groupModelList;
        this.imageUri = imageUri;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    public List<GroupModel> getGroupModelList() {
        return groupModelList;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getImageUri() {
        return imageUri;
    }

    public int getId() {
        return id;
    }

    public String getAddress() {
        return address;
    }

    @BindingAdapter("imageUri")
    public static void setImageUri(ImageView imageView, String uri) {
        Context context = imageView.getContext();
        Glide.with(context)
                .applyDefaultRequestOptions(RequestOptions.circleCropTransform())
                .load(uri)
                .into(imageView);
    }

    @BindingAdapter("backImageUri")
    public static void setImageUriBackground(ImageView imageView, String uri) {
        Context context = imageView.getContext();
        Glide.with(context)
                .load(uri)
                .apply(RequestOptions.fitCenterTransform())
                .into(imageView);
    }

}
