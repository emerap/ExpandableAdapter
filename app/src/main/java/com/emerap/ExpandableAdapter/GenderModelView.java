package com.emerap.ExpandableAdapter;

import android.support.annotation.NonNull;

import com.emerap.library.ExpandableAdapter.ModelView;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by karbunkul on 05.03.17.
 */

@SuppressWarnings("WeakerAccess")
public class GenderModelView extends ModelView<Profile> {

    public GenderModelView(List<Profile> data) {
        super(data);
    }

    @Override
    public String getGroupKeyValue(Profile item) {
        return item.gender;
    }

    @Override
    public @NonNull String getItemFieldValue(Profile item) {
        return item.name;
    }

    @Override
    public void sort(List<Profile> data) {
        Collections.sort(data, new Comparator<Profile>() {
            @Override
            public int compare(Profile o1, Profile o2) {
                return (o1.gender + o1.name).compareTo((o2.gender + o2.name));
            }
        });
    }
}
