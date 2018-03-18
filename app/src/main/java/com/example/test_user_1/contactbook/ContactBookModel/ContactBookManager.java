package com.example.test_user_1.contactbook.ContactBookModel;


import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.test_user_1.contactbook.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ContactBookManager {

    private static ContactBookManager instance;

    public static synchronized ContactBookManager getInstance(){
        if (instance == null) {
            instance = new ContactBookManager();
        }
        return instance;
    }

    private List<ContactModel> contactModels;
    private List<GroupModel> groupModels;
    private Context context;

    public ContactBookManager(){
        this.context = MainActivity.context;
        parseData();
    }

    public List<GroupModel> getGroupModels() {
        return groupModels;
    }

    public List<ContactModel> getContactModels() {
        return contactModels;
    }

    private void parseData(){
        contactModels = new ArrayList<>();
        groupModels = new ArrayList<>();
        String temp = loadJSONFromAsset();
        parseContacts(temp);
        parseGroups(temp);
    }

    /**
     * @param jsonString - json в виде строки
     * Заполняем данными groupContacts
     */
    private void parseContacts(String jsonString){
        JSONObject jsonObj;
        JSONArray jsonArray;
        try {
            jsonObj = new JSONObject(jsonString);
            JSONObject object = jsonObj.getJSONObject("employees");
            jsonArray = object.getJSONArray("employee");
            for (int i=0;i<jsonArray.length();i++){
                JSONObject tempObj = jsonArray.getJSONObject(i);
                List<GroupModel> tempList = new ArrayList<>();
                try {
                    JSONArray tempArr = tempObj.getJSONArray("groups");
                    for (int j=0;j<tempArr.length();j++){
                        GroupModel group = new GroupModel(tempArr.optString(j,""),0);
                        tempList.add(group);
                    }
                }catch (JSONException e){
                    Log.e("JSONParser","Can't parse person group array");
                }
                String firstName = tempObj.optString("firstName","");
                String lastName = tempObj.optString("lastName","");
                if (firstName.equals("")|firstName.equals("null"))
                    firstName = "NoName";
                if (lastName.equals("")|lastName.equals("null"))
                    lastName = "NoSurname";
                String imageUri = tempObj.optString("image_uri","android.resource://com.example.test_user_1.contactbook/drawable/test");
                if (imageUri.equals(""))
                    imageUri = "android.resource://com.example.test_user_1.contactbook/drawable/test";

                ContactModel contactModel = new ContactModel(
                        i,
                        firstName,
                        lastName,
                        tempList,
                        imageUri,
                        tempObj.optString("phoneNumber",""),
                        tempObj.optString("address",""));
                contactModels.add(contactModel);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param jsonString - json в виде строки
     * Заполнение данными groupModels
     */
    public void parseGroups(String jsonString){
        JSONObject jsonObj;
        JSONArray jsonArray;
        try {
            jsonObj = new JSONObject(jsonString);
            jsonArray = jsonObj.getJSONArray("groups_list");
            groupModels.add(new GroupModel("All",contactModels.size()));
            for (int i=0;i<jsonArray.length();i++){
                int count = 0;
                String tempName = jsonArray.getString(i) ;
                for (ContactModel contactModel : contactModels)
                    for (GroupModel groupModel : contactModel.getGroupModelList())
                        if (groupModel.getGroupName().equals(jsonArray.getString(i)))
                            count++;
                GroupModel group = new GroupModel(tempName,count);
                groupModels.add(group);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(context,"Oops, coudn't get groups",Toast.LENGTH_SHORT).show();
        }
    }

    private String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = context.getAssets().open("Contacts.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return "";
        }
        return json;
    }

    /**
     * @param group - наименование группы
     * @return список контактов, которые состоят в этой группе
     */
   public List<ContactModel> getContactModelsByGroup(String group){
        List<ContactModel> tempContactModel = new ArrayList<>();
            for (ContactModel contactModel : contactModels)
                for (GroupModel groupModel : contactModel.getGroupModelList())
                    if (groupModel.getGroupName().equals(group))
                        tempContactModel.add(contactModel);
        return tempContactModel;
    }

    /**
     * @param id - id, указанный в json файле
     * @return контакт, соответствующий этому id
     */
    public ContactModel getContactModelById(int id){
        for (ContactModel contactModel : contactModels)
            if (contactModel.getId() == id)
                return contactModel;
        return null;
    }

    /**
     * @param name - наименование группы
     * @return GroupModel, у которой совпадает наименование группы с name
     */
    public GroupModel getGroupModelByName(String name){
        for (GroupModel groupModel: groupModels)
            if (groupModel.getGroupName().equals(name))
                return groupModel;
        return null;
    }
}
