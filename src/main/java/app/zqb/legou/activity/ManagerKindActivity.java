package app.zqb.legou.activity;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;

import app.zqb.legou.fragment.ManagerKindFragment;
import app.zqb.legou.other.Callbacks;

/**
 * Created by admin on 2016/3/25.
 */
public class ManagerKindActivity extends FragmentActivity implements Callbacks{

    @Override
    protected Fragment getFragment() {
        return new ManagerKindFragment();
    }

    @Override
    public void onItemSelected(Integer id, Bundle bundle) {
        Intent i = new Intent(this , AddKindActivity.class);
        startActivity(i);
    }
}
