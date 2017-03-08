package com.emerap.library.ExpandableAdapter;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by karbunkul on 08.03.17.
 */

public class SharedStateConfig extends StateConfig {

    private SharedPreferences mSharedPreferences;
    private String mStateId;

    public SharedStateConfig(Context context, String stateId) {
        this(context, stateId, "expandable_adapter");
    }

    public SharedStateConfig(Context context, String stateId, String fileName) {
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
    public String getStateId() {
        return mStateId;
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
