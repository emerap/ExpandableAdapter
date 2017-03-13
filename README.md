# ExpandableAdapter
Customizable adapter for RecyclerView


[ ![Download](https://api.bintray.com/packages/emerap/android/expandable-adapter/images/download.svg) ](https://bintray.com/emerap/android/expandable-adapter/_latestVersion)
[![API](https://img.shields.io/badge/API-15%2B-green.svg?style=flat)](https://android-arsenal.com/api?level=15)
[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-ExpandableAdapter-blue.svg?style=flat)](https://android-arsenal.com/details/1/5427)

## Setup

### Gradle
```
compile 'com.github.emerap:expandable-adapter:0.0.2'
```
### Maven
```
<dependency>
  <groupId>com.github.emerap</groupId>
  <artifactId>expandable-adapter</artifactId>
  <version>0.0.1</version>
  <type>pom</type>
</dependency>
```
### Ivy
```
<dependency org='com.github.emerap' name='expandable-adapter' rev='0.0.2'>
  <artifact name='expandable-adapter' ext='pom' ></artifact>
</dependency>
```
## Features
- Interface `StateConfig` for saving and restore items state.
- Get and set any object to `Section` and `Item` from via them interfaces.
- Create Model view from list of object (any types).
- Interface ModelSwitch for switch between ModelView objects.
- Predefined SectionViewHolder with title, icon and count fields.

## Examples

### Customize adapter

- Implement onCreateViewHolder.
- Bind your data to UI.

```
import com.emerap.library.ExpandableAdapter.ExpandableAdapter;
import com.emerap.library.ExpandableAdapter.ExpandableViewHolder;
import com.emerap.library.ExpandableAdapter.ItemInterface;
import com.emerap.library.ExpandableAdapter.SectionInterface;

public class CustomAdapter extends ExpandableAdapter {

    @Override
    public void onBindSection(ExpandableViewHolder holder, SectionInterface section) {

    }

    @Override
    public void onBindItem(ExpandableViewHolder holder, String title, ItemInterface item) {

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
}
```

See MainActivity.java from app module
