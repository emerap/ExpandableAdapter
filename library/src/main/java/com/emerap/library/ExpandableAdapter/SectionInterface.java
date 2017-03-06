package com.emerap.library.ExpandableAdapter;

/**
 * SectionInterface.
 * Created by karbunkul on 02.02.17.
 */

@SuppressWarnings("unused")
public interface SectionInterface<O> {
    O getObject();

    void addItem(ItemInterface item);

    String getTitle();

    Boolean isExpanded();

    void setExpanded(Boolean expanded);

    int getItemsCount();

    ItemInterface getItem(int index);
}
