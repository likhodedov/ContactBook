package com.example.test_user_1.contactbook.ContactBookModel;

public class GroupModel {
    private String groupName;
    private int count;

    /**
     *
     * @param groupName - наименование группы
     * @param count - кол-во контактов входящих в данную группу
     *              (для групп у контакта выставляется 0)
     */
    public GroupModel(String groupName, int count){
        this.groupName = groupName;
        this.count = count;
    }

    public String getGroupName() {
        return groupName;
    }

    public String getCount() {
        return Integer.toString(count);
    }

    public int getCountInt() {
        return count;
    }

    public void addCount(){
        count++;
    }

    public void removeCount(){
        count--;
    }
}
