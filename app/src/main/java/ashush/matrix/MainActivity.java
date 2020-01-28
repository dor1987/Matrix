package ashush.matrix;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import com.getbase.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import ashush.matrix.adapters.CountryPopUpAdapter;
import ashush.matrix.adapters.MainActivityRecyclerViewAdapter;
import ashush.matrix.adapters.OnItemClickListener;
import ashush.matrix.models.Item;

import static ashush.matrix.utils.Constants.AREA_SORT;
import static ashush.matrix.utils.Constants.NAME_SORT;

public class MainActivity extends AppCompatActivity  implements OnItemClickListener , MainActivityController.DataChangesListener,MainActivityController.OpenPopListener{
    private RecyclerView mRecyclerView;
    private MainActivityRecyclerViewAdapter mAdapter;
    private MainActivityController mainActivityController;
    private PopupWindow countryWindow = null;
    private CountryPopUpAdapter popUpAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainActivityController = new MainActivityController(getApplicationContext());

        //block user input until data is avilable
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

        initViews();
        initRecyclerView();
        initFAB();


    }

    private void initViews() {
        mRecyclerView = findViewById(R.id.recycler_view);
        progressBar = findViewById(R.id.progressBar_cyclic);
    }

    @Override
    protected void onStart() {
        super.onStart();
        //register to events from controller
        mainActivityController.registerForDataUpdates(this);
        mainActivityController.registerForOpenPopListener(this);

    }

    private void initFAB() {
        //Init FAB and menu
        FloatingActionButton nameAscendBtn = findViewById(R.id.name_ascend_btn);
        FloatingActionButton nameDescBtn = findViewById(R.id.name_desc_btn);
        FloatingActionButton areaAscendBtn = findViewById(R.id.area_ascend_btn);
        FloatingActionButton areaDescBtn = findViewById(R.id.area_desc_btn);

        //add on click listeners
        nameAscendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivityController.SortList(NAME_SORT,true);
            }
        });

        nameDescBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivityController.SortList(NAME_SORT,false);

            }
        });

        areaAscendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivityController.SortList(AREA_SORT,true);

            }
        });

        areaDescBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivityController.SortList(AREA_SORT,false);

            }
        });
    }

    private void initRecyclerView() {
        mAdapter = new MainActivityRecyclerViewAdapter(this);
        RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

    }


    @Override
    public void onItemclick(Item item) {
        mainActivityController.itemClicked(item);
    }

    @Override
    public void onDataChanged(ArrayList<Item> dataArrayList) {
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE); //unblock user input
        progressBar.setVisibility(View.GONE); //Remove progress  bar when data is ready
        mAdapter.setItems(dataArrayList);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        //Adding listeners to search view
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
            //not using this, only using onTextChange
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                mainActivityController.getFilter().filter(s);
                return false;
            }
        });
        return true;
    }

    @Override
    public void onOpenPopUp(ArrayList<Item> dataArrayList) {
        //call back from the controller to show popup
        createPopUp(dataArrayList);
    }
    private void createPopUp(ArrayList<Item> borders){
        // init popup
        LayoutInflater layoutInflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = layoutInflater.inflate(R.layout.country_pop_up_layout, null);
        recyclerView = layout.findViewById(R.id.coutnry_border_recyclerView_popup);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        popUpAdapter = new CountryPopUpAdapter(borders);
        recyclerView.setAdapter(popUpAdapter);
        countryWindow = new PopupWindow(this);
        initPopUpGraphics(layout,countryWindow);
        countryWindow.setOutsideTouchable(false);

    }

    public void initPopUpGraphics(View layout, PopupWindow window){
        //additional graphics definition for pop up
        window.setContentView(layout);
        window.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        window.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        window.setClippingEnabled(true);
        window.setBackgroundDrawable(new ColorDrawable((Color.TRANSPARENT)));
        window.setFocusable(true);
        window.showAtLocation(layout, Gravity.CENTER, 1, 1);
    }

    @Override
    protected void onStop() {
        //unregister from events
        mainActivityController.unRegisterForDataUpdates();
        mainActivityController.unRegisterForOpenPopListener();
        super.onStop();
    }

    public void closePopUp(View view) {
        countryWindow.dismiss();
    }
}
