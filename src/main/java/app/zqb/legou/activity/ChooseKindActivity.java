package app.zqb.legou.activity;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;

import app.zqb.legou.fragment.ChooseKindFragment;
import app.zqb.legou.other.Callbacks;

/**
 * Created by admin on 2016/3/25.
 */
public class ChooseKindActivity extends FragmentActivity implements Callbacks {
    @Override
    public void onItemSelected(Integer id, Bundle bundle) {
        Intent intent = new Intent(this , ChooseItemActivity.class);
        intent.putExtra("kindId", bundle.getLong("kindId"));
        startActivity(intent);
    }

    @Override
    protected Fragment getFragment() {
        return new ChooseKindFragment();
    }
}
