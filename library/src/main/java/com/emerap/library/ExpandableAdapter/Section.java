package com.emerap.library.ExpandableAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Section.
 * Created by karbunkul on 27.01.2017.
 */

@SuppressWarnings("WeakerAccess")
abstract public class Section<O> implements SectionInterface<O> {

    private String mTitle;
    private O mObject;
    private Boolean mExpanded = false;
    private List<ItemInterface> mItems = new ArrayList<>();

    public Section(String title, O object) {
        mTitle = title;
        mObject =  object;
    }

    public void addItem(ItemInterface item){
        mItems.add(item);
    }

    public String getTitle() {
        return mTitle;
    }

    public O getObject() {
        return mObject;
    }

    public Boolean isExpanded() {
        return mExpanded;
    }

    public void setExpanded(Boolean expanded) {
        mExpanded = expanded;
    }

    public int getItemsCount() {
        return mItems.size();
    }

    public ItemInterface getItem(int index) {
        return mItems.get(index);
    }


}
