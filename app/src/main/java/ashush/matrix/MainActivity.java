package ashush.matrix;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;

import ashush.matrix.adapters.MainActivityRecyclerViewAdapter;
import ashush.matrix.adapters.OnItemClickListener;
import ashush.matrix.models.Item;
import ashush.matrix.reposetory.ItemRepository;

public class MainActivity extends AppCompatActivity  implements OnItemClickListener , MainActivityController.DataChangesListener{
    private RecyclerView mRecyclerView;
    private MainActivityRecyclerViewAdapter mAdapter;
    private MainActivityController mainActivityController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*
        ItemRepository temp = new ItemRepository(getApplicationContext());
        temp.getItems();
        */
        mainActivityController = new MainActivityController(getApplicationContext());
        mainActivityController.registerForDataUpdates(this);
        mRecyclerView = findViewById(R.id.recycler_view);
        initRecyclerView();



    }

    private void initRecyclerView() {
        mAdapter = new MainActivityRecyclerViewAdapter(this,this);
        RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

    }


    @Override
    public void onItemclick(Item item) {

    }

    @Override
    public void onDataChanged(ArrayList<Item> dataArrayList) {
        mAdapter.setItems(dataArrayList);
    }
}
