package com.project.rushabh.epicure.model;

import java.io.Serializable;

/**
 * Created by brkckr on 28.10.2017.
 */

public class Item implements Serializable
{
    public String firebaseId;
    public int id;
    //public String categoryId;
    public String subCategoryId;
    public String name;
    public double unitPrice;
    public String url;

    public Item(String firebaseId, int id, /*String categoryId,*/ String subCategoryId, String name, double unitPrice, String url)
    {
        this.firebaseId = firebaseId;
        this.id = id;
        //this.categoryId = categoryId;
        this.subCategoryId = subCategoryId;
        this.name = name;
        this.unitPrice = unitPrice;
        this.url = url;
    }
}
