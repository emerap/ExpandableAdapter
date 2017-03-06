package com.emerap.library.ExpandableAdapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * ExpandableAdapter.
 * Created by karbunkul on 27.01.2017.
 */

interface ExpandableInterface {

    void onBindSection(ExpandableViewHolder holder, SectionInterface section);

    void onBindItem(ExpandableViewHolder holder, String title, ItemInterface item);

    void onLoadListState(List<SectionInterface> sections);

    void onSaveListState(List<SectionInterface> sections);

}

public abstract class ExpandableAdapter extends RecyclerView.Adapter<ExpandableViewHolder> implements ExpandableInterface {

    protected static final int TYPE_SECTION = 0;
    protected static final int TYPE_ITEM = 1;

    @SuppressWarnings("WeakerAccess")
    public static final int TOGGLE_MODE_FREE = 0;
    @SuppressWarnings("WeakerAccess")
    public static final int TOGGLE_MODE_ACCORDION = 1;

    private List<SectionInterface> mSections = new ArrayList<>();
    private int mToggleMode = TOGGLE_MODE_FREE;
    private boolean mSaveFoldingState = true;

    @Override
    public int getItemViewType(int position) {
        int count = 0;
        for (SectionInterface section : mSections) {
            Boolean expand = section.isExpanded();

            if (position == count) {
                return TYPE_SECTION;
            } else if (expand && (position < count + section.getItemsCount() + 1)) {
                return TYPE_ITEM;
            }
            count = (expand) ? count + section.getItemsCount() + 1 : count + 1;
        }
        return 0;
    }

    @Override
    public void onBindViewHolder(ExpandableViewHolder holder, int position) {
        int count = 0;
        for (final SectionInterface section : mSections) {
            Boolean expand = section.isExpanded();

            if (position == count) {
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Boolean expand = !section.isExpanded();
                        if (mToggleMode == TOGGLE_MODE_ACCORDION) {
                            foldSections(false);
                            if (section.isExpanded()) expand = false;
                        }
                        section.setExpanded(expand);
                        notifyDataSetChanged();

                    }
                });
                onBindSection(holder, section);
                break;
            } else if (expand && (position < count + section.getItemsCount() + 1)) {
                ItemInterface item = section.getItem(position - count - 1);
                onBindItem(holder, item.getTitle(), item);
                break;
            }
            count = (expand) ? count + section.getItemsCount() + 1 : count + 1;
        }
    }

    @Override
    public int getItemCount() {
        int count = 0;
        for (SectionInterface section : mSections) {
            count++;
            if (section.isExpanded()) count += section.getItemsCount();
        }
        return count;
    }

    /**
     * Add sections.
     * @param sections list sections
     */
    public void addSections(SectionInterface... sections) {
        for (SectionInterface section : sections) {
            addSection(section);
        }
    }

    public void addSections(List<SectionInterface> sections) {
        mSections.addAll(sections);
        notifyDataSetChanged();
    }

    /**
     * Add new section to collection.
     *
     * @param section section
     */
    @SuppressWarnings("WeakerAccess")
    public void addSection(SectionInterface section) {
        mSections.add(section);
        notifyDataSetChanged();
    }

    /**
     * Clear all sections.
     */
    public void clearSections() {
        mSections.clear();
    }

    /**
     * Toggle sections
     */
    public void toggle() {
        int expandedCount = 0;

        for (SectionInterface section : mSections) {
            if (section.isExpanded()) expandedCount++;
        }
        Boolean fold;
        fold = ((mSections.size() - expandedCount) < expandedCount);
        foldSections(!fold);
        notifyDataSetChanged();
        onSaveListState(mSections);
    }

    private void foldSections(Boolean fold) {
        for (SectionInterface section : mSections) {
            section.setExpanded(fold);
        }
    }

    @SuppressWarnings("unused")
    public ExpandableAdapter setToggleMode(int toggleMode) {
        mToggleMode = toggleMode;
        return this;
    }

    @SuppressWarnings("unused")
    public void setSaveFoldingState(boolean saveFoldingState) {
        mSaveFoldingState = saveFoldingState;
    }

    public void init() {
        if (mSaveFoldingState) onLoadListState(mSections);
    }
}
