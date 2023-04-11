package com.example.statozone;

public class CategoryValue {
    private String name;
    private String img;
    private String ids;

    public CategoryValue(String name, String img, String ids )
    {
        super();
        this.name = name;
        this.img = img;
        this.ids = ids;
    }

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg()
    {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
