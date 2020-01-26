package ashush.matrix.adapters;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import ashush.matrix.MainActivity;
import ashush.matrix.R;
import ashush.matrix.models.Item;
import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivityRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {
    private List<Item> mItemList;
    private OnItemClickListener mOnItemClickListener;
    private Activity activity;

    public MainActivityRecyclerViewAdapter(OnItemClickListener mOnItemClickListener,Activity activity) {
        this.mOnItemClickListener = mOnItemClickListener;
        this.activity = activity;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_item, viewGroup, false);

        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder,final int i) {
        ((ItemViewHolder)viewHolder).mNativeName.setText(mItemList.get(i).getNativeName());
        ((ItemViewHolder)viewHolder).mName.setText(mItemList.get(i).getName());

        //Set images
        RequestOptions defaultOptions = new RequestOptions()
                .error(R.drawable.ic_launcher_background);

        Glide.with(viewHolder.itemView.getContext())
                .setDefaultRequestOptions(defaultOptions)
                .load(mItemList.get(i).getFlagURL())
                .into(((ItemViewHolder)viewHolder).mFlag);

        //Set onClickListener
        ((ItemViewHolder) viewHolder).parentLayout.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                mOnItemClickListener.onItemclick(mItemList.get(i));
            }
        });
    }

    @Override
    public int getItemCount() {
        if(mItemList != null){
            return mItemList.size();
        }
        return 0;
    }

    public void setItems(List<Item> items){
        mItemList = items;
        notifyDataSetChanged();
    }

    protected class ItemViewHolder extends RecyclerView.ViewHolder {
        private TextView mNativeName;
        private TextView mName;
        private CircleImageView mFlag;
        protected RelativeLayout parentLayout;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            mNativeName = itemView.findViewById(R.id.native_name_text);
            mName = itemView.findViewById(R.id.name_text);
            mFlag = itemView.findViewById(R.id.user_avatar);
            parentLayout = itemView.findViewById(R.id.item_parent_layout);
        }
    }
}
