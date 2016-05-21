package app.zqb.legou.activity;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.widget.LinearLayout;

import app.zqb.legou.R;

/**
 * Created by admin on 2016/3/17.
 */
public abstract class FragmentActivity extends BaseActivity{

    private static final int ROOT_CONTAINER_ID = 0x90001;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        LinearLayout layout = new LinearLayout(this);
        setContentView(layout);
        layout.setId(ROOT_CONTAINER_ID);
        getFragmentManager().beginTransaction().replace(ROOT_CONTAINER_ID, getFragment()).commit();
    }

    protected abstract Fragment getFragment();
}
