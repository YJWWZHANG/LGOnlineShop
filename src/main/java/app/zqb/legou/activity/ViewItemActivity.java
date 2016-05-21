package app.zqb.legou.activity;

import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import app.zqb.legou.fragment.ViewItemFragment;

public class ViewItemActivity extends FragmentActivity {

    @Override
    protected Fragment getFragment(){
        ViewItemFragment fragment = new ViewItemFragment();
        Bundle arguments = new Bundle();
        arguments.putString("action", getIntent().getStringExtra("action"));
        fragment.setArguments(arguments);
        return fragment;
    }

}
