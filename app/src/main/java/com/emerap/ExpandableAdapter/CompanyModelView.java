package com.emerap.ExpandableAdapter;

import com.emerap.library.ExpandableAdapter.ModelView;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by karbunkul on 05.03.17.
 */

public class CompanyModelView extends ModelView<Profile> {

    @Override
    public void fillFields(Profile item) {
        setGroupId(item.id);
        setGroupTitle(item.company);
        setItemTitle(item.name);
    }

    @Override
    public void sortItem(List<Profile> data) {
        Collections.sort(data, new Comparator<Profile>() {
            @Override
            public int compare(Profile o1, Profile o2) {
                return (o1.name + o1.company).compareTo((o2.name + o2.company));
            }
        });
    }

    @Override
    public void sortGroup(List<Profile> data) {

    }
}
