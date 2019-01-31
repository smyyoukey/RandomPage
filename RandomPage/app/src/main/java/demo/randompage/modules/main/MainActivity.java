package demo.randompage.modules.main;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import demo.randompage.R;
import demo.randompage.database.Item;
import demo.randompage.modules.FloatView.FloatManager;
import demo.randompage.modules.FloatView.FloatWindow;

public class MainActivity extends AppCompatActivity {


    private RecyclerView mRecyclerView;
    private ArrayList mPicList;
    private RVAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        mPicList =new ArrayList();
//        List<Item> list = Arrays.asList(Item.ITEMS);
//        mPicList.addAll(list);
    initView();

    }

    private void initView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        StaggeredGridLayoutManager mLayoutManager = new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addItemDecoration(new RecycleViewItemDecoration(2,4,true));
        mRecyclerView.setHasFixedSize(true);
        mAdapter = new RVAdapter(this,mPicList);
        mRecyclerView.setAdapter(mAdapter);

    }

    @Override
    protected void onResume() {
        super.onResume();

//        if(mFloatV!=null) {
//            mFloatManager.showFloatWindow(this, mFloatV, 0, 0);
//        }
    }
}
