package com.emerap.ExpandableAdapter;

import com.emerap.library.ExpandableAdapter.ModelView;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by karbunkul on 05.03.17.
 */

public class CompanyModelView extends ModelView<Profile> {

    public CompanyModelView(List<Profile> data) {
        super(data);
    }

    @Override
    public String getGroupKeyValue(Profile item) {
        return item.company;
    }

    @Override
    public String getItemFieldValue(Profile item) {
        return item.name;
    }

    @Override
    public void sort(List<Profile> data) {
        Collections.sort(data, new Comparator<Profile>() {
            @Override
            public int compare(Profile o1, Profile o2) {
                return (o1.name + o1.company).compareTo((o2.name + o2.company));
            }
        });
    }
}
