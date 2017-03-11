package com.emerap.library.ExpandableAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * StateConfig.
 * Created by karbunkul on 08.03.17.
 */

interface StateConfigInterface {

    void onLoadState(List<SectionInterface> sections);

    void onSaveState(List<SectionInterface> sections);

    void onSaveState(SectionInterface section);

    HashMap<String, Boolean> onLoadFromStorageState();

    void onSaveToStorageState(HashMap<String, Boolean> states);

    boolean getSavedFoldingState();

    String getStateId();
}

@SuppressWarnings("WeakerAccess")
public abstract class StateConfig implements StateConfigInterface {

    protected HashMap<String, Boolean> mStates = new HashMap<>();
    private String mPostfix = "";

    @Override
    public void onLoadState(List<SectionInterface> sections) {
        mStates = onLoadFromStorageState();
        for (SectionInterface section : sections) {
            if (mStates.containsKey(section.getSectionId())) section.setExpanded(true);
        }
    }

    @Override
    public void onSaveState(List<SectionInterface> sections) {
        changeStates(sections);
        onSaveToStorageState(mStates);
    }

    @Override
    public void onSaveState(final SectionInterface section) {
        changeStates(new ArrayList<SectionInterface>() {{
            add(section);
        }});
        onSaveToStorageState(mStates);
    }

    private void changeStates(List<SectionInterface> sections) {
        for (SectionInterface section : sections) {
            mStates.put(section.getSectionId(), section.isExpanded());
        }
    }

    public void setPostfix(String postfix) {
        mPostfix = postfix;
    }
}
