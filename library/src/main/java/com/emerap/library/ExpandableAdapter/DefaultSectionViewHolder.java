package com.emerap.library.ExpandableAdapter;

import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by karbunkul on 13.03.17.
 */

public class DefaultSectionViewHolder extends ExpandableViewHolder {

    public TextView title;
    public TextView count;
    public ImageView icon;

    public DefaultSectionViewHolder(View view) {
        super(view);
        title = (TextView) view.findViewById(R.id.expandable_default_section_title);
        count = (TextView) view.findViewById(R.id.expandable_default_section_count);
        icon = (ImageView) view.findViewById(R.id.expandable_default_section_icon);
    }

    public static DefaultSectionViewHolder newInstance(@NonNull ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.expandeable_default_section, parent, false);
        return new DefaultSectionViewHolder(view);
    }

    public int getIconRes(SectionInterface section, @DrawableRes int collapse, @DrawableRes int expand) {
        return (section.isExpanded()) ? collapse : expand;
    }

    public int getIconRes(SectionInterface section) {
        return getIconRes(section, R.drawable.ic_section_collapse, R.drawable.ic_section_expand);
    }

}
