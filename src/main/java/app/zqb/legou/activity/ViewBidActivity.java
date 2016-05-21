package app.zqb.legou.activity;

import android.app.Fragment;

import app.zqb.legou.fragment.ViewBidFragment;

/**
 * Created by admin on 2016/3/25.
 */
public class ViewBidActivity extends FragmentActivity {
    @Override
    protected Fragment getFragment() {
        return new ViewBidFragment();
    }
}
