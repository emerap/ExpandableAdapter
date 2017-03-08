package com.emerap.library.ExpandableAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * ModelView
 * Created by karbunkul on 05.03.17.
 */

interface ModelViewInterface<T> {
    String getGroupKeyValue(T item);
    String getFieldGroupIdValue(T item);
    String getItemFieldValue(T item);

    void sortItem(List<T> data);
    void sortGroup(List<T> data);
}

public abstract class ModelView<T> implements ModelViewInterface<T> {

    private List<T> mDataList;
    final private HashMap<String, GroupItem> mGroupMap = new HashMap<>();

    public ModelView(List<T> data) {
        mDataList = data;
        sortGroup(mDataList);
        createGroups();
    }

    @SuppressWarnings("unused")
    public HashMap<String, GroupItem> getGroupMap() {
        return mGroupMap;
    }

    public List<SectionInterface> createModelView() {

        List<SectionInterface> sections = new ArrayList<>();

        for (String key : mGroupMap.keySet()) {
            GroupItem groupItem = mGroupMap.get(key);
            SectionModel section = new SectionModel(key, groupItem.getSectionId(), null);
            sections.add(section);
            for (T item : groupItem.getItems()) {
                section.addItem(new ItemModel(getItemFieldValue(item), item));
            }
        }

        return sections;
    }

    private void createGroups() {
        for (T item : mDataList) {
            String key = getGroupKeyValue(item);
            String groupId = getFieldGroupIdValue(item);
            if (mGroupMap.get(key) == null) mGroupMap.put(key, new GroupItem(groupId));
            List<T> list = mGroupMap.get(key).getItems();
            sortItem(list);
            list.add(item);
        }
    }

    @SuppressWarnings("WeakerAccess")
    private class SectionModel extends Section<String> {

        public SectionModel(String title, String sectionId, String object) {
            super(title,sectionId, object);
        }
    }

    @SuppressWarnings("WeakerAccess")
    private class ItemModel extends Item<T> {

        public ItemModel(String title, T object) {
            super(title, object);
        }
    }

    private class GroupItem {

        private String mSectionId;
        final private List<T> mItems = new ArrayList<>();

        GroupItem(String sectionId) {
            mSectionId = sectionId;
        }

        List<T> getItems() {
            return mItems;
        }

        public String getSectionId() {
            return mSectionId;
        }
    }
}
