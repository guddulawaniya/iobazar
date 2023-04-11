package com.example.statozone;

public class Submenudetail {
    String MenuId;
    String MenuName;
    int subListCount;
    public Submenudetail(String MenuId, String MenuName,int subListCount){
        super();
        this.MenuId=MenuId;
        this.MenuName = MenuName;
        this.subListCount = subListCount;

    }

    public String getMenuId() {

        return MenuId;
    }
    public void setMenuId(String MenuId) {
        this.MenuId = MenuId;
    }

    public String getMenuName() {
        return MenuName;
    }

    public void setMenuName(String menuName) {
        this.MenuName = menuName;
    }

    public int getSubListCount() {
        return subListCount;
    }

    public void setSubListCount(int subListCount) {
        this.subListCount = subListCount;
    }
}
