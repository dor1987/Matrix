package ashush.matrix;

import android.content.Context;

import java.util.ArrayList;

import ashush.matrix.models.Item;
import ashush.matrix.reposetory.ItemRepository;

public class MainActivityController implements ItemRepository.NewDataListener {
    private DataChangesListener mDataChangesListener;
    private ItemRepository itemRepository;

    public MainActivityController(Context context) {
        itemRepository = new ItemRepository(context);

        itemRepository.registerForNewDataUpdates(this);
        itemRepository.getItems();
    }

    @Override
    public void onNewData(ArrayList<Item> dataArrayList) {
        if(mDataChangesListener != null)
            mDataChangesListener.onDataChanged(dataArrayList);
    }


    public interface DataChangesListener{
        void onDataChanged(ArrayList<Item> dataArrayList);
    }

    void registerForDataUpdates(DataChangesListener listener){
        mDataChangesListener = listener;
    }

    void unRegisterForDataUpdates(){
        mDataChangesListener = null;
    }
}
