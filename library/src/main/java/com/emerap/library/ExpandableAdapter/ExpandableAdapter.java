package com.emerap.library.ExpandableAdapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * ExpandableAdapter.
 * Created by karbunkul on 27.01.2017.
 */

interface ExpandableInterface {

    void onBindSection(ExpandableViewHolder holder, SectionInterface section);

    void onBindItem(ExpandableViewHolder holder, ItemInterface item);

    void onBindEmptyDataPlaceholder(ExpandableViewHolder holder);

    ExpandableViewHolder getSectionViewHolder(ViewGroup parent);

    ExpandableViewHolder getItemViewHolder(ViewGroup parent);

    ExpandableViewHolder getEmptyDataViewHolder(ViewGroup parent);
}
@SuppressWarnings("WeakerAccess")
public abstract class ExpandableAdapter extends RecyclerView.Adapter<ExpandableViewHolder> implements ExpandableInterface {

    protected static final int TYPE_SECTION = 0;
    protected static final int TYPE_ITEM = 1;
    protected static final int TYPE_EMPTY_DATA = 2;


    public static final int TOGGLE_MODE_FREE = 0;
    public static final int TOGGLE_MODE_ACCORDION = 1;

    private List<SectionInterface> mSections = new ArrayList<>();
    private int mToggleMode = TOGGLE_MODE_FREE;
    private StateConfig mStateConfig;
    private ModelSwitchInterface mModelSwitch;

    @Override
    public int getItemViewType(int position) {
        if (mSections.isEmpty()) {
            return TYPE_EMPTY_DATA;
        }
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

    public ExpandableViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_EMPTY_DATA: {
                return getEmptyDataViewHolder(parent);
            }

            case TYPE_SECTION: {
                return getSectionViewHolder(parent);
            }
            case TYPE_ITEM: {
                return getItemViewHolder(parent);
            }
        }
        return null;
    }

    @Override
    public ExpandableViewHolder getEmptyDataViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.expandable_empty_data_placeholder, parent, false);
        return new EmptyDataViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ExpandableViewHolder holder, int position) {

        if (mSections.isEmpty()) {
            onBindEmptyDataPlaceholder(holder);
            return;
        }

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
                        if (mStateConfig != null && mStateConfig.getSavedFoldingState()) {
                            mStateConfig.onSaveState(section);
                        }
                        notifyDataSetChanged();

                    }
                });
                onBindSection(holder, section);
                break;
            } else if (expand && (position < count + section.getItemsCount() + 1)) {
                ItemInterface item = section.getItem(position - count - 1);
                onBindItem(holder, item);
                break;
            }
            count = (expand) ? count + section.getItemsCount() + 1 : count + 1;
        }
    }

    @Override
    public void onBindEmptyDataPlaceholder(ExpandableViewHolder holder) {
        if (holder instanceof EmptyDataViewHolder) {
            EmptyDataViewHolder viewHolder = (EmptyDataViewHolder) holder;
            viewHolder.message.setText("List is empty");
        }
    }

    @Override
    public int getItemCount() {

        if (mSections.isEmpty()) return 1;

        int count = 0;
        for (SectionInterface section : mSections) {
            count++;
            if (section.isExpanded()) count += section.getItemsCount();
        }
        return count;
    }

    /**
     * Add sections.
     *
     * @param sections list sections
     */
    @SuppressWarnings("unused")
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
        notifyDataSetChanged();
    }

    public boolean isEmptyData() {
        return mSections.isEmpty();
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
        if (mStateConfig != null) {
            mStateConfig.onSaveState(mSections);
        }
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

    public void setStateConfig(StateConfig stateConfig) {
        mStateConfig = stateConfig;
        if (mStateConfig.getSavedFoldingState() && mSections.size() > 0) {
            mStateConfig.onLoadFromStorageState();
            mStateConfig.onLoadState(mSections);
        }
    }

    public void setModelSwitch(ModelSwitch modelSwitch) {
        mModelSwitch = modelSwitch;
    }

    public boolean switchModel(String key) {
        if (mModelSwitch != null && mModelSwitch.getModels().size() > 0) {
            if (mModelSwitch.getModels().containsKey(key)) {
                ModelSwitch.Model model = (ModelSwitch.Model) mModelSwitch.getModels().get(key);
                ModelView modelView = model.getModelView();
                modelView.setData(mModelSwitch.getData());
                List<SectionInterface> sections = modelView.createModelView();
                if (sections.size() > 0) {
                    clearSections();
                    if (mStateConfig != null) mStateConfig.setPostfix(key);
                    addSections(sections, true);

                    return true;
                }
                return false;
            }
        }
        return false;
    }

    private void addSections(List<SectionInterface> sections, boolean loadState) {
        addSections(sections);
        if (loadState && mStateConfig != null) {
            mStateConfig.onLoadState(sections);

        }
    }
}
