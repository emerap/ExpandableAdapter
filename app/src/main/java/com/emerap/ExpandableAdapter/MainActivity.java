package com.emerap.ExpandableAdapter;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.emerap.library.ExpandableAdapter.ExpandableAdapter;
import com.emerap.library.ExpandableAdapter.ExpandableViewHolder;
import com.emerap.library.ExpandableAdapter.ItemInterface;
import com.emerap.library.ExpandableAdapter.SectionInterface;
import com.emerap.library.ExpandableAdapter.SharedStateConfig;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    @SuppressWarnings("FieldCanBeLocal")
    private RecyclerView mRecyclerView;
    @SuppressWarnings("FieldCanBeLocal")
    private ExpandableAdapter mAdapter;
    private SwipeRefreshLayout mRefreshLayout;
    private List<Profile> mProfiles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String json = "[{\"_id\":\"58bafc6a160db172f596c829\",\"balance\":\"$1,313.70\",\"name\":\"Park Boone\",\"gender\":\"male\",\"company\":\"GEEKOLOGY\",\"email\":\"parkboone@geekology.com\"},{\"_id\":\"58bafc6a1eb01128b575d5e5\",\"balance\":\"$3,181.17\",\"name\":\"Madge Haney\",\"gender\":\"female\",\"company\":\"EPLOSION\",\"email\":\"madgehaney@eplosion.com\"},{\"_id\":\"58bafc6a15b3a9154cb60933\",\"balance\":\"$3,321.72\",\"name\":\"Katharine Wolf\",\"gender\":\"female\",\"company\":\"QIMONK\",\"email\":\"katharinewolf@qimonk.com\"},{\"_id\":\"58bafc6a29448aa79002eb32\",\"balance\":\"$2,021.28\",\"name\":\"Viola Farley\",\"gender\":\"female\",\"company\":\"PROGENEX\",\"email\":\"violafarley@progenex.com\"},{\"_id\":\"58bafc6adeb4cbec4ed82143\",\"balance\":\"$3,995.33\",\"name\":\"Lizzie Davis\",\"gender\":\"female\",\"company\":\"NAMEBOX\",\"email\":\"lizziedavis@namebox.com\"},{\"_id\":\"58bafc6a52c19ece07db42f1\",\"balance\":\"$1,446.35\",\"name\":\"Audra Cervantes\",\"gender\":\"female\",\"company\":\"OCTOCORE\",\"email\":\"audracervantes@octocore.com\"}]";
        Log.d("JSON", json);

        Gson gson = new Gson();
        Type collectionType = new TypeToken<List<Profile>>() {
        }.getType();
        mProfiles = gson.fromJson(json, collectionType);

        mAdapter = new ExpandableAdapter() {

            @Override
            public ExpandableViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                switch (viewType) {
                    case TYPE_SECTION: {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.expandeable_recycler_section, parent, false);
                        return new SectionViewHolder(view);
                    }
                    case TYPE_ITEM: {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.expandeable_recycler_item, parent, false);
                        return new ItemViewHolder(view);
                    }
                }
                return null;
            }

            @Override
            public void onBindSection(ExpandableViewHolder holder, SectionInterface section) {
                SectionViewHolder viewHolder = (SectionViewHolder) holder;
                viewHolder.mTextView.setText(section.getTitle());
                viewHolder.mStatus.setText(String.format(Locale.getDefault(), "%d", section.getItemsCount()));

                int iconId = (section.isExpanded()) ? R.drawable.ic_collapse : R.drawable.ic_expand;
                viewHolder.mIcon.setImageResource(iconId);
            }

            @Override
            public void onBindItem(ExpandableViewHolder holder, String title, ItemInterface item) {
                ItemViewHolder viewHolder = (ItemViewHolder) holder;

                if (item.getObject() instanceof Profile) {
                    viewHolder.mTextView.setText(item.getTitle() + " - " + ((Profile) item.getObject()).email);
                    return;
                }

                viewHolder.mTextView.setText(item.getTitle());
            }

            class SectionViewHolder extends ExpandableViewHolder {

                TextView mTextView;
                TextView mStatus;
                ImageView mIcon;

                SectionViewHolder(View itemView) {
                    super(itemView);
                    mTextView = (TextView) itemView.findViewById(R.id.item_caption);
                    mStatus = (TextView) itemView.findViewById(R.id.item_count);
                    mIcon = (ImageView) itemView.findViewById(R.id.item_icon);
                }
            }

            class ItemViewHolder extends ExpandableViewHolder {

                TextView mTextView;

                ItemViewHolder(View itemView) {
                    super(itemView);
                    mTextView = (TextView) itemView.findViewById(R.id.item_caption);
                }
            }
        };

        setupAdapter();

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
        mRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.refresh);
        mRefreshLayout.setOnRefreshListener(this);
    }

    private void setupAdapter() {
        GenderModelView modelView = new GenderModelView(mProfiles);
        SharedStateConfig config = new SharedStateConfig(getApplicationContext(), "profile_list");
        mAdapter.addSections(modelView.createModelView());
        mAdapter.setStateConfig(config);

    }

    @Override
    public void onRefresh() {
        mAdapter.clearSections();
        setupAdapter();
        mRefreshLayout.setRefreshing(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_toggle: {
                mAdapter.toggle();
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
