package com.emerap.library.ExpandableAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * ModelView
 * Created by karbunkul on 05.03.17.
 */

interface ModelViewInterface<T> {
    void fillFields(T item);

    String getGroupTitle();

    String getGroupId();

    String getItemTitle();

    void setGroupTitle(String groupTitle);

    void setGroupId(String groupId);

    void setItemTitle(String itemTitle);

    void sortItem(List<T> data);

    void sortGroup(List<T> data);
}

public abstract class ModelView<T> implements ModelViewInterface<T> {

    private List<T> mDataList;
    private HashMap<String, GroupItem> mGroupMap;

    private String mGroupTitle;
    private String mGroupId;
    private String mItemTitle;

    public ModelView(List<T> data) {
        mDataList = data;
        sortGroup(mDataList);
        createGroups();
    }

    public ModelView() {
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
                fillFields(item);
                section.addItem(new ItemModel(getItemTitle(), item));
            }
        }

        return sections;
    }

    private void createGroups() {
        mGroupMap = new HashMap<>();
        for (T item : mDataList) {
            fillFields(item);
            String key = getGroupTitle();
            String groupId = getGroupId();
            if (mGroupMap.get(key) == null) mGroupMap.put(key, new GroupItem(groupId));
            List<T> list = mGroupMap.get(key).getItems();
            sortItem(list);
            list.add(item);
        }
    }

    public void setData(List<T> dataList) {
        mDataList = new ArrayList<>();
        mDataList.addAll(dataList);
        sortGroup(mDataList);
        createGroups();
    }

    @SuppressWarnings("WeakerAccess")
    private class SectionModel extends Section<String> {

        public SectionModel(String title, String sectionId, String object) {
            super(title, sectionId, object);
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

    public void setGroupTitle(String groupTitle) {
        mGroupTitle = groupTitle;
    }

    public void setGroupId(String groupId) {
        mGroupId = groupId;
    }

    public void setItemTitle(String itemTitle) {
        mItemTitle = itemTitle;
    }

    @Override
    public String getGroupTitle() {
        return mGroupTitle;
    }

    @Override
    public String getGroupId() {
        return mGroupId;
    }

    @Override
    public String getItemTitle() {
        return mItemTitle;
    }
}
