package demo.randompage.modules.main;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import java.util.ArrayList;

import demo.randompage.R;

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
