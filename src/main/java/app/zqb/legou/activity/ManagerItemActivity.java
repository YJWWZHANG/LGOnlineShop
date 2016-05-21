package app.zqb.legou.activity;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;

import app.zqb.legou.fragment.AddItemFragment;
import app.zqb.legou.fragment.ManagerItemFragment;
import app.zqb.legou.other.Callbacks;

/**
 * Created by admin on 2016/3/25.
 */
public class ManagerItemActivity extends FragmentActivity implements Callbacks {
    @Override
    public void onItemSelected(Integer id, Bundle bundle) {
        Intent i = new Intent(this , AddItemActivity.class);
        startActivity(i);
    }

    @Override
    protected Fragment getFragment() {
        return new ManagerItemFragment();
    }
}
