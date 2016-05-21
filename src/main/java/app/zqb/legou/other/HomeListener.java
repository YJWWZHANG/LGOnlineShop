package app.zqb.legou.other;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import app.zqb.legou.activity.LGClientActivity;

/**
 * Created by admin on 2016/3/13.
 */
public class HomeListener implements View.OnClickListener{

    private Activity activity;

    public HomeListener(Activity activity){
        this.activity = activity;
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(activity, LGClientActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        activity.startActivity(intent);
    }
}
