package demo.randompage.modules.main;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import demo.randompage.R;
import demo.randompage.database.Item;

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
        StaggeredGridLayoutManager mlayoutManager = new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mlayoutManager);
        mAdapter = new RVAdapter(this,mPicList);
        mRecyclerView.setAdapter(mAdapter);

    }



}
