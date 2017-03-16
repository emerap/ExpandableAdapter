package com.emerap.library.ExpandableAdapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * EmptyDataViewHolder
 * Created by karbunkul on 17.03.17.
 */

@SuppressWarnings("WeakerAccess")
public class EmptyDataViewHolder extends ExpandableViewHolder {

    public TextView message;
    public ImageView image;

    public EmptyDataViewHolder(View view) {
        super(view);
        message = (TextView) view.findViewById(R.id.message);
        image = (ImageView) view.findViewById(R.id.image);
    }
}
