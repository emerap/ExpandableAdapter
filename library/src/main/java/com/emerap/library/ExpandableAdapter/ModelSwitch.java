package com.emerap.library.ExpandableAdapter;

import android.support.annotation.NonNull;

import java.util.HashMap;
import java.util.List;

/**
 * ModelSwitch
 * Created by karbunkul on 12.03.17.
 */

public class ModelSwitch<T> implements ModelSwitchInterface<T> {

    private List<T> mData;
    private HashMap<String, Model> mModels = new HashMap<>();



    public ModelSwitch(List<T> data) {
        mData = data;
    }

    public void add(@NonNull String key, @NonNull String title, @NonNull ModelView modelView) {
        Model model = new Model(key, title, modelView);
        mModels.put(model.getKey(), model);
    }

    public HashMap<String, ModelSwitch<T>.Model> getModels() {
        return mModels;
    }


    public List<T> getData() {
        return mData;
    }

    public class Model {

        private String mKey;
        private String mTitle;
        private ModelView mModelView;


        public Model(@NonNull String key, @NonNull String title, @NonNull ModelView modelView) {
            mKey = key;
            mTitle = title;
            mModelView = modelView;
        }

        public String getKey() {
            return mKey;
        }

        public String getTitle() {
            return mTitle;
        }

        public ModelView getModelView() {
            return mModelView;
        }

    }


}
