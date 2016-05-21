package app.zqb.legou.activity;

import android.app.Fragment;

import app.zqb.legou.fragment.AddKindFragment;

/**
 * Created by admin on 2016/3/25.
 */
public class AddKindActivity extends FragmentActivity {
    @Override
    protected Fragment getFragment() {
        return new AddKindFragment();
    }
}
