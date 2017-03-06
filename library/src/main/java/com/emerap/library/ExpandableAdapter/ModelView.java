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
    String getItemFieldValue(T item);
    void sort(List<T> data);
}

public abstract class ModelView<T> implements ModelViewInterface<T>{

    private List<T> mDataList;
    private HashMap<String, List<T>> mGroupMap = new HashMap<>();

    public ModelView(List<T> data) {
        mDataList = data;
        sort(mDataList);
        createGroups();
    }

    @SuppressWarnings("unused")
    public HashMap<String, List<T>> getGroupMap(){
        return mGroupMap;
    }

    public List<SectionInterface> createModelView() {

        List<SectionInterface> sections = new ArrayList<>();

        for (String key: mGroupMap.keySet()) {
            List<T> items = mGroupMap.get(key);
            SectionModel section = new SectionModel(key, null);
            sections.add(section);
            for (T item : items) {
                section.addItem(new ItemModel(getItemFieldValue(item), item));
            }
        }

        return sections;
    }

    private void createGroups() {
        for ( T item : mDataList) {
            String key = getGroupKeyValue(item);
            if (mGroupMap.get(key) == null) mGroupMap.put(key, new ArrayList<T>());
            List<T> list = mGroupMap.get(key);
            list.add(item);
        }
    }

    @SuppressWarnings("WeakerAccess")
    private class SectionModel extends Section<String> {

        public SectionModel(String title, String object) {
            super(title, object);
        }
    }

    @SuppressWarnings("WeakerAccess")
    private class ItemModel extends Item<T> {

        public ItemModel(String title, T object) {
            super(title, object);
        }
    }
}
