package com.emerap.ExpandableAdapter;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MenuItemCompat;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.emerap.library.ExpandableAdapter.DefaultSectionViewHolder;
import com.emerap.library.ExpandableAdapter.EmptyDataViewHolder;
import com.emerap.library.ExpandableAdapter.ExpandableAdapter;
import com.emerap.library.ExpandableAdapter.ExpandableViewHolder;
import com.emerap.library.ExpandableAdapter.ItemInterface;
import com.emerap.library.ExpandableAdapter.ModelSwitch;
import com.emerap.library.ExpandableAdapter.SectionInterface;
import com.emerap.library.ExpandableAdapter.SharedStateConfig;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, AdapterView.OnItemSelectedListener {

    @SuppressWarnings("FieldCanBeLocal")
    private RecyclerView mRecyclerView;
    @SuppressWarnings("FieldCanBeLocal")
    private ExpandableAdapter mAdapter;
    private SwipeRefreshLayout mRefreshLayout;
    private List<Profile> mProfiles;
    private Spinner mSpinner;

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
            public void onBindSection(ExpandableViewHolder holder, SectionInterface section) {
                DefaultSectionViewHolder viewHolder = (DefaultSectionViewHolder) holder;
                viewHolder.title.setText(section.getTitle());
                viewHolder.count.setText(String.format(Locale.getDefault(), "%d", section.getItemsCount()));
                viewHolder.icon.setImageResource(viewHolder.getIconRes(section));
            }

            @Override
            public void onBindItem(ExpandableViewHolder holder, ItemInterface item) {
                ItemViewHolder viewHolder = (ItemViewHolder) holder;

                if (item.getObject() instanceof Profile) {
                    viewHolder.mTextView.setText(item.getTitle() + " - " + ((Profile) item.getObject()).email);
                    return;
                }

                viewHolder.mTextView.setText(item.getTitle());
            }

            @Override
            public void onBindEmptyDataPlaceholder(ExpandableViewHolder holder) {
                super.onBindEmptyDataPlaceholder(holder);
                if (holder instanceof EmptyDataViewHolder) {
                    EmptyDataViewHolder viewHolder = (EmptyDataViewHolder) holder;
                    viewHolder.image.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.mipmap.ic_launcher));
                    viewHolder.message.setText("Empty data placeholder");
                }
            }

            @Override
            public ExpandableViewHolder getSectionViewHolder(ViewGroup parent) {
                return DefaultSectionViewHolder.newInstance(parent);
            }

            @Override
            public ExpandableViewHolder getItemViewHolder(ViewGroup parent) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.expandeable_recycler_item, parent, false);
                return new ItemViewHolder(view);
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
        SharedStateConfig config = new SharedStateConfig(getApplicationContext(), "profile_list");
        mAdapter.setStateConfig(config);
        ModelSwitch modelSwitch = new ModelSwitch(mProfiles);

        modelSwitch.add("gender", "Gender", new GenderModelView());
        modelSwitch.add("company", "Company", new CompanyModelView());
        mAdapter.setModelSwitch(modelSwitch);
    }

    @Override
    public void onRefresh() {
        if (mAdapter.isEmptyData()) {
            setupAdapter();
        }else{
            mAdapter.clearSections();
        }
        mRefreshLayout.setRefreshing(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        MenuItem item = menu.findItem(R.id.menu_model_switch);
        if (item != null) {
            mSpinner = (Spinner) MenuItemCompat.getActionView(item);
            String[] data = {"gender", "company"};
            mSpinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, data));

            List<String> dan = Arrays.asList(data);

//            mSpinner.setSelection(dan.contains());
            mSpinner.setOnItemSelectedListener(this);
        }

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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        mAdapter.switchModel(mSpinner.getAdapter().getItem(position).toString().toLowerCase());
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
