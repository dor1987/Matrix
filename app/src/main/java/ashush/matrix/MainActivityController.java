package ashush.matrix;

import android.content.Context;
import android.util.Log;
import android.widget.Filter;
import android.widget.Filterable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import ashush.matrix.models.Item;
import ashush.matrix.reposetory.ItemRepository;

import static ashush.matrix.utils.Constants.AREA_SORT;
import static ashush.matrix.utils.Constants.EMPTY_TYPE;
import static ashush.matrix.utils.Constants.NAME_SORT;

public class MainActivityController implements ItemRepository.NewDataListener, Filterable {
    private DataChangesListener mDataChangesListener;
    private OpenPopListener mOpenPopListener;

    private ItemRepository itemRepository;
    private ArrayList<Item> itemList;
    private ArrayList<Item> itemListFull;

    public MainActivityController(Context context) {
        itemRepository = new ItemRepository(context);
        itemRepository.registerForNewDataUpdates(this);
        itemRepository.getItems();
    }

    @Override
    public void onNewData(ArrayList<Item> dataArrayList) {
        itemList = dataArrayList;
        itemListFull = new ArrayList<>(itemList);

        if(mDataChangesListener != null)
            mDataChangesListener.onDataChanged(dataArrayList);
    }

    public void SortList(int sortBy, boolean isAscending) {
        ArrayList<Item> items = new ArrayList<Item>();
        items.addAll(itemList);

        switch (sortBy){
            case AREA_SORT:
                if(isAscending){
                    Collections.sort(items, new Comparator<Item>() {

                        @Override
                        public int compare(Item o1, Item o2) {
                            Log.e("O1",o1.getName()+" "+o1.getArea()+"");
                            if(o1.getArea() < o2.getArea()) return 1;
                            if(o1.getArea() > o2.getArea()) return -1;
                            return  0;
                        }
                    });
                }
                else if(!isAscending){
                    Collections.sort(items, new Comparator<Item>() {
                        @Override
                        public int compare(Item o1, Item o2) {
                            if(o1.getArea() > o2.getArea()) return 1;
                            if(o1.getArea() < o2.getArea()) return -1;
                            return  0;                        }
                    });
                }
                break;

            case NAME_SORT:
                if(isAscending){
                    Collections.sort(items, new Comparator<Item>() {

                        @Override
                        public int compare(Item o1, Item o2) {
                            return (int)(o1.getName().compareTo(o2.getName()));
                        }
                    });
                }
                else if(!isAscending){
                    Collections.sort(items, new Comparator<Item>() {

                        @Override
                        public int compare(Item o1, Item o2) {
                            return (int)(o2.getName().compareTo(o1.getName()));
                        }
                    });
                }
                break;
        }

        if(mDataChangesListener != null)
            mDataChangesListener.onDataChanged(items);
    }

    public void itemClicked(Item item) {
        ArrayList<Item> mBorderList = new ArrayList<>();

        for(Item country : itemListFull){
            for(String border : item.getBorders()){
                if(country.getAlpha3Code().compareTo(border)== 0 )
                    mBorderList.add(country);
            }
        }

        if(mBorderList.isEmpty()){
            String[] placeHolder ={""};
            Item emptyItem = new Item("","",0.0,"",placeHolder,"");
            emptyItem.setType(EMPTY_TYPE);
            mBorderList.add(emptyItem);
        }
        if(mOpenPopListener!=null){
            mOpenPopListener.onOpenPopUp(mBorderList);
        }
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    private  Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<Item> filteredList = new ArrayList<>();

            if(charSequence == null || charSequence.length() ==0){
                filteredList.addAll(itemListFull);
            } else{
                String filterPattern = charSequence.toString().toString().toLowerCase().trim();
                for(Item item : itemListFull){
                   if(item.getName().toLowerCase().contains(filterPattern)){
                       filteredList.add(item);
                   }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            itemList.clear();
            itemList.addAll((List)filterResults.values);

            if(mDataChangesListener != null)
                mDataChangesListener.onDataChanged(itemList);        }
    };

    public interface DataChangesListener{
        void onDataChanged(ArrayList<Item> dataArrayList);
    }

    void registerForDataUpdates(DataChangesListener listener){
        mDataChangesListener = listener;
    }

    void unRegisterForDataUpdates(){
        mDataChangesListener = null;
    }


    public interface OpenPopListener{
        void onOpenPopUp(ArrayList<Item> dataArrayList);
    }

    void registerForOpenPopListener(OpenPopListener listener){
        mOpenPopListener = listener;
    }

    void unRegisterForOpenPopListener(){
        mOpenPopListener = null;
    }


}
