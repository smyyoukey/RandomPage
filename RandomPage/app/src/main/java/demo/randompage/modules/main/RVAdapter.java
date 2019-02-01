package demo.randompage.modules.main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import demo.randompage.R;
import demo.randompage.database.Item;

/**
 * Created by smy on 18-2-10.
 */

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList mArrayList;


    public RVAdapter(Context context, ArrayList list) {
        this.mContext = context;
//        this.mArrayList = list;

    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView mPhoto;
         TextView mProvide;
         View mPicView;

        public ViewHolder(View itemView) { //set view in this method
            super(itemView);
            mPicView = itemView;
            mPhoto = (ImageView)itemView.findViewById(R.id.imageview_item);
            mProvide = (TextView)itemView.findViewById(R.id.textview_name);
        }
    }


    @Override
    public RVAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item,parent,false);
        final RVAdapter.ViewHolder holder = new RVAdapter.ViewHolder(view);


        return holder;
    }
   private Item mItem;
    @Override
    public void onBindViewHolder(final RVAdapter.ViewHolder holder, final int position) {
        mItem = getItem(position);
        Log.d("item","item's URL : "+mItem.getThumbnailUrl());
        holder.mPhoto = (ImageView)holder.mPicView.findViewById(R.id.imageview_item);
        Picasso.with(holder.mPhoto.getContext())
                .load(mItem.getThumbnailUrl())
                .into(holder.mPhoto);

        holder.mProvide.setText(mItem.getName());

        holder.mPicView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int p = holder.getAdapterPosition();
                mItem = getItem(p);

                // Construct an Intent as normal
                Intent intent = new Intent(mContext, DetailActivity.class);
                intent.putExtra(DetailActivity.EXTRA_PARAM_ID, mItem.getId());
                // BEGIN_INCLUDE(start_activity)
                /**
                 * Now create an {@link android.app.ActivityOptions} instance using the
                 * {@link ActivityOptionsCompat#makeSceneTransitionAnimation(Activity, Pair[])} factory
                 * method.
                 */
                ActivityOptionsCompat activityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(
                        (Activity) mContext,
                        // Now we provide a list of Pair items which contain the view we can transitioning
                        // from, and the name of the view it is transitioning to, in the launched activity
                        new Pair<View, String>(v.findViewById(R.id.imageview_item),
                                DetailActivity.VIEW_NAME_HEADER_IMAGE),
                        new Pair<View, String>(v.findViewById(R.id.textview_name),
                                DetailActivity.VIEW_NAME_HEADER_TITLE)
                );

                // Now we can start the Activity, providing the activity options as a bundle
                ActivityCompat.startActivity(mContext, intent, activityOptions.toBundle());
                // END_INCLUDE(start_activity)
            }
        });
    }

    @Override
    public int getItemCount() {
        return Item.ITEMS.length;
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);

    }
    public Item getItem(int position){
        return Item.ITEMS[position];
    }
}
