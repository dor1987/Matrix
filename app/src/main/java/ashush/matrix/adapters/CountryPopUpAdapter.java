package ashush.matrix.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import ashush.matrix.R;
import ashush.matrix.models.Item;

import static ashush.matrix.utils.Constants.COUNTRY_TYPE;
import static ashush.matrix.utils.Constants.EMPTY_TYPE;

public class CountryPopUpAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
private ArrayList<Item> listOfBorders;

    public CountryPopUpAdapter(ArrayList<Item> listOfBorders) {
        this.listOfBorders = listOfBorders;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = null;

          if(listOfBorders.get(0).getType() == EMPTY_TYPE)
               view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.empty_item, viewGroup, false);

          else
               view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_item, viewGroup, false);

        return new CountryPopUpAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        int itemViewType = getItemViewType(i);

        if(itemViewType == COUNTRY_TYPE) {
            ((ViewHolder) viewHolder).naitveNameTextView.setText(listOfBorders.get(i).getNativeName());
            ((ViewHolder) viewHolder).nameTextView.setText(listOfBorders.get(i).getName());
        }
    }

    @Override
    public int getItemCount() {
        if(listOfBorders != null){
            return listOfBorders.size();
        }
        return 0;    }


    @Override
    public int getItemViewType(int position) {
        if(listOfBorders.get(position).getType() == COUNTRY_TYPE){
            return COUNTRY_TYPE;
        }
        else {
            return EMPTY_TYPE;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView naitveNameTextView;
        TextView nameTextView;
        RelativeLayout parentLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            naitveNameTextView = itemView.findViewById(R.id.native_name_text);
            nameTextView = itemView.findViewById(R.id.name_text);
            parentLayout = itemView.findViewById(R.id.item_parent_layout);
        }
    }

    protected class EmptyViewHolder extends RecyclerView.ViewHolder {

        public EmptyViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
