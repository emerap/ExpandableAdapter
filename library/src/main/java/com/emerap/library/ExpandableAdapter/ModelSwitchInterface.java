package com.emerap.library.ExpandableAdapter;

import android.support.annotation.NonNull;

import java.util.HashMap;
import java.util.List;

/**
 * ModelSwitchInterface
 * Created by karbunkul on 12.03.17.
 */

@SuppressWarnings("WeakerAccess")
public interface ModelSwitchInterface<T> {
    void add(@NonNull String key, @NonNull String title, @NonNull ModelView modelView);

    HashMap<String, ModelSwitch<T>.Model> getModels();

    List<T> getData();
}
