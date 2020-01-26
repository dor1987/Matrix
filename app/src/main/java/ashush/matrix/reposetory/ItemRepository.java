package ashush.matrix.reposetory;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import ashush.matrix.models.Item;

import static ashush.matrix.utils.Constants.BASE_URL;

public class ItemRepository {

    private RequestQueue queue;
    private Gson gson;
    private NewDataListener mNewDataListener;
    private ArrayList<Item> dataList;

    public ItemRepository(Context context) {
        queue = Volley.newRequestQueue(context);
        gson = new Gson();
        dataList = new ArrayList<Item>();
    }


    public void getItems(){
        final String url = BASE_URL+"all";
        dataList.clear();
        // prepare the Request
        final JsonArrayRequest getRequest = new JsonArrayRequest(Request.Method.GET,url,null,
                new Response.Listener<JSONArray>()
                {
                    @Override
                    public void onResponse(JSONArray response) {
                        // display response
                        Log.d("Response", response.toString());

                        for(int i = 0; i< response.length();i++){
                            try {
                                JSONObject jresponse = response.getJSONObject(i);
                                Item item = gson.fromJson(jresponse.toString(),Item.class);
                                dataList.add(item);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        if(mNewDataListener != null)
                            mNewDataListener.onNewData(dataList);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error.Response", "error");
                    }
                });

        // add it to the RequestQueue
        queue.add(getRequest);
    }

    public interface NewDataListener{
        void onNewData(ArrayList<Item> dataArrayList);
    }

    public void registerForNewDataUpdates(NewDataListener listener){
        mNewDataListener = listener;
    }

    void unRegisterForNewDataUpdates(){
        mNewDataListener = null;
    }
}
