package com.emerap.library.ExpandableAdapter;

/**
 * Item.
 * Created by karbunkul on 27.01.2017.
 */

@SuppressWarnings("WeakerAccess")
abstract public class Item<O> implements ItemInterface<O> {

    private String mTitle;
    private O mObject;

    public Item(String title, O object) {
        mTitle = title;
        mObject = object;
    }

    public String getTitle() {
        return mTitle;
    }

    public O getObject() {
        return mObject;
    }
}
