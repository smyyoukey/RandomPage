package demo.randompage.modules.main;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import demo.randompage.R;

/**
 * Created by smy on 18-2-10.
 */

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList mArrayList;


    public RVAdapter(Context context, ArrayList list) {
        this.mContext = context;
        this.mArrayList = list;

    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView mPhoto;
        public TextView mProvide;

        public ViewHolder(View itemView) { //set view in this method
            super(itemView);
            mPhoto = (ImageView)itemView.findViewById(R.id.imageview_item);
            mProvide = (TextView)itemView.findViewById(R.id.textview_name);
        }
    }


    @Override
    public RVAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item,parent,false);
        final RVAdapter.ViewHolder holder = new RVAdapter.ViewHolder(view);


        return null;
    }

    @Override
    public void onBindViewHolder(RVAdapter.ViewHolder holder, final int position) {


}

    @Override
    public int getItemCount() {
        return 0;
    }
}
