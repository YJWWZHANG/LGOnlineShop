package app.zqb.legou.activity;

import android.app.Fragment;
import android.os.Bundle;

import app.zqb.legou.fragment.AddBidFragment;

/**
 * Created by admin on 2016/3/25.
 */
public class AddBidActivity extends FragmentActivity {
    @Override
    protected Fragment getFragment() {
        AddBidFragment fragment = new AddBidFragment();
        Bundle args = new Bundle();
        args.putInt("itemId", getIntent()
                .getIntExtra("itemId", -1));
        fragment.setArguments(args);
        return fragment;
    }
}
