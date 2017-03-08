package com.emerap.library.ExpandableAdapter;

import java.util.List;

/**
 * SectionInterface.
 * Created by karbunkul on 02.02.17.
 */

@SuppressWarnings("unused")
public interface SectionInterface<O> {
    O getObject();

    void addItem(ItemInterface item);

    String getTitle();

    String getSectionId();

    Boolean isExpanded();

    void setExpanded(Boolean expanded);

    List<ItemInterface> getItems();

    int getItemsCount();

    ItemInterface getItem(int index);
}
