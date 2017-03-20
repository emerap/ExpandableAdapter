package com.emerap.library.ExpandableAdapter;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * SharedStateConfig
 * Created by karbunkul on 08.03.17.
 */

public class SharedStateConfig extends StateConfig {

    private SharedPreferences mSharedPreferences;


    public SharedStateConfig(Context context, String stateId) {
        this(context, stateId, "expandable_adapter");
    }

    @SuppressWarnings("WeakerAccess")
    public SharedStateConfig(Context context, String stateId, String fileName) {
        super(stateId);
        mSharedPreferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        mStateId = stateId;
    }

    @Override
    public HashMap<String, Boolean> onLoadFromStorageState() {
        HashMap<String, Boolean> states = new HashMap<>();
        for (String id : getStringArray(",")) {
            states.put(id, true);
        }
        return states;
    }

    @Override
    public void onSaveToStorageState(HashMap<String, Boolean> states) {
        List<String> items = new ArrayList<>();
        for (String sectionId : states.keySet()) {
            final boolean state = states.get(sectionId);
            if (state) items.add(sectionId);
        }
        String[] spItems = new String[items.size()];
        setStringArray(items.toArray(spItems), ",");
    }

    @Override
    public boolean getSavedFoldingState() {
        return true;
    }

    @Override
    public String getCurrentModelKey() {
        return mSharedPreferences.getString(mStateId + "_current_model_key", "");
    }

    @Override
    public void setCurrentModelKey(String key) {
        mSharedPreferences.edit().putString(mStateId + "_current_model_key", key).apply();
    }

    private void setStringArray(String[] value, String delimiter) {
        StringBuilder sb = new StringBuilder();
        for (String aValue : value) {
            sb.append(aValue).append(delimiter);
        }
        mSharedPreferences.edit().putString(getStateId(), sb.toString()).apply();
    }

    private String[] getStringArray(String delimiter) {
        String spItems = mSharedPreferences.getString(getStateId(), "");
        return (!"".equals(spItems)) ? spItems.split(delimiter) : new String[0];
    }

}
