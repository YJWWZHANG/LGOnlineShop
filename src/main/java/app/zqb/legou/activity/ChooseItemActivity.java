package app.zqb.legou.activity;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;

import app.zqb.legou.fragment.ChooseItemFragment;
import app.zqb.legou.other.Callbacks;

/**
 * Created by admin on 2016/3/25.
 */
public class ChooseItemActivity extends FragmentActivity implements Callbacks {
    @Override
    public void onItemSelected(Integer id, Bundle bundle) {
        Intent intent = new Intent(this , AddBidActivity.class);
        intent.putExtra("itemId", bundle.getInt("itemId"));
        startActivity(intent);
    }

    @Override
    protected Fragment getFragment() {
        ChooseItemFragment fragment = new ChooseItemFragment();
        Bundle args = new Bundle();
        args.putLong("kindId", getIntent()
                .getLongExtra("kindId", -1));
        fragment.setArguments(args);
        return fragment;
    }
}
