package demo.randompage.modules.main;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.transition.Scene;
import android.transition.TransitionInflater;
import android.transition.TransitionManager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.util.Arrays;

import demo.randompage.R;
import demo.randompage.modules.ThirdView.ScrollPickerView;
import demo.randompage.modules.ThirdView.StringScrollPicker;

/**
 * Created by smy on 18-2-27.
 */

public class TransitionActivity extends AppCompatActivity {

    private Scene mScene1;
    private Scene mScene2;
    private Scene mScene3;


    private StringScrollPicker mSelectedView;
    private String[] mScenes = {"0","1","2","3","4"};

    /** A custom TransitionManager */
    private TransitionManager mTransitionManagerForScene3;

    /** Transitions take place in this ViewGroup. We retain this for the dynamic transition on scene 4. */
    private ViewGroup mSceneRoot;

    public static TransitionActivity newInstance() {
        return new TransitionActivity();
    }

    public TransitionActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_basic_transition);
        initView();
    }


    private void initView() {
        initToolbar();
        mSceneRoot = (ViewGroup) findViewById(R.id.scene_root);
        // BEGIN_INCLUDE(instantiation_from_view)
        // A Scene can be instantiated from a live view hierarchy.
        mScene1 = new Scene(mSceneRoot, (ViewGroup) mSceneRoot.findViewById(R.id.container));// why I cant write it in onCreateView()?
        // END_INCLUDE(instantiation_from_view)

        // BEGIN_INCLUDE(instantiation_from_resource)
        // You can also inflate a generate a Scene from a layout resource file.
        mScene2 = Scene.getSceneForLayout(mSceneRoot, R.layout.scene2, this);
        // END_INCLUDE(instantiation_from_resource)

        // Another scene from a layout resource file.
        mScene3 = Scene.getSceneForLayout(mSceneRoot, R.layout.scene3, this);

        // BEGIN_INCLUDE(custom_transition_manager)
        // We create a custom TransitionManager for Scene 3, in which ChangeBounds and Fade
        // take place at the same time.
        mTransitionManagerForScene3 = TransitionInflater.from(this)
                .inflateTransitionManager(R.transition.scene3_transition_manager, mSceneRoot);

        mSelectedView = (StringScrollPicker)findViewById(R.id.scroll_selected);
        mSelectedView.setData(Arrays.asList(mScenes));
        mSelectedView.setTextSize(32,80);
        mSelectedView.setOnSelectedListener(new ScrollPickerView.OnSelectedListener() {
            @Override
            public void onSelected(ScrollPickerView scrollPickerView, int position) {
                switch (position) {
                    case 0:
                        TransitionManager.go(mScene1);
                        break;
                    case 1:
                        TransitionManager.go(mScene2);
                        break;
                    case 2:
                        mTransitionManagerForScene3.transitionTo(mScene3);
                        break;
                    case 3:
                        TransitionManager.beginDelayedTransition(mSceneRoot);
                        // Then, we can just change view properties as usual.
                        View square = mSceneRoot.findViewById(R.id.transition_square);
                        ViewGroup.LayoutParams params = square.getLayoutParams();
                        int newSize = getResources().getDimensionPixelSize(R.dimen.square_size_expanded);
                        params.width = newSize;
                        params.height = newSize;
                        square.setLayoutParams(params);
                        break;
                    case 4:
                        startActivity(new Intent("android.settings.INTERNAL_STORAGE_SETTINGS"));
                }
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void initToolbar() {
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("ScrollPickerView");
        setTitleColor(R.color.black);
    }


    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {

        // END_INCLUDE(custom_transition_manager)

        return super.onCreateView(name, context, attrs);
    }
}
