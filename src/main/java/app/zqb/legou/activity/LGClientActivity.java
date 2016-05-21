package app.zqb.legou.activity;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;

import app.zqb.legou.R;
import app.zqb.legou.fragment.AddBidFragment;
import app.zqb.legou.fragment.AddItemFragment;
import app.zqb.legou.fragment.AddKindFragment;
import app.zqb.legou.fragment.ChooseItemFragment;
import app.zqb.legou.fragment.ChooseKindFragment;
import app.zqb.legou.fragment.LGListFragment;
import app.zqb.legou.fragment.ManagerItemFragment;
import app.zqb.legou.fragment.ManagerKindFragment;
import app.zqb.legou.fragment.ViewBidFragment;
import app.zqb.legou.fragment.ViewItemFragment;
import app.zqb.legou.other.Callbacks;

public class LGClientActivity extends BaseActivity implements Callbacks{

    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lgclient);

        if(findViewById(R.id.lg_detail_container) != null){
            mTwoPane = true;
            ((LGListFragment) getFragmentManager().findFragmentById(R.id.lg_list))
                    .setActivateOnItemClick(true);
        }
    }

    @Override
    public void onItemSelected(Integer id, Bundle bundle) {
        if(mTwoPane){
            Fragment fragment = null;
            switch ((int) id){
                case 0:
                    fragment = new ViewItemFragment();
                    Bundle arguments = new Bundle();
                    arguments.putString("action", "viewSucc.jsp");
                    fragment.setArguments(arguments);
                    break;
                case 1:
                    fragment = new ViewItemFragment();
                    Bundle arguments2 = new Bundle();
                    arguments2.putString("action", "viewFail.jsp");
                    fragment.setArguments(arguments2);
                    break;
                case 2:
                    fragment = new ManagerKindFragment();
                    break;
                case 3:
                    fragment = new ManagerItemFragment();
                    break;
                case 4:
                    fragment = new ChooseKindFragment();
                    break;
                case ManagerKindFragment.ADD_KIND:
                    fragment = new AddKindFragment();
                    break;
                case 5:
                    fragment = new ViewBidFragment();
                    break;
                case ManagerItemFragment.ADD_ITEM:
                    fragment = new AddItemFragment();
                    break;
                case ChooseKindFragment.CHOOSE_ITEM:
                    fragment = new ChooseItemFragment();
                    Bundle args = new Bundle();
                    args.putLong("kindId", bundle.getLong("kindId"));
                    fragment.setArguments(args);
                    break;
                case ChooseItemFragment.ADD_BID:
                    fragment = new AddBidFragment();
                    Bundle args2 = new Bundle();
                    args2.putInt("itemId", bundle.getInt("itemId"));
                    fragment.setArguments(args2);
                    break;
            }
            getFragmentManager().beginTransaction().replace(R.id.lg_detail_container, fragment)
                    .addToBackStack(null).commit();
        }else {
            Intent intent = null;
            switch ((int) id){
                case 0:
                    intent = new Intent(this, ViewItemActivity.class);
                    intent.putExtra("action", "viewSucc.jsp");
                    startActivity(intent);
                    break;
                case 1:
                    intent = new Intent(this, ViewItemActivity.class);
                    intent.putExtra("action", "viewFail.jsp");
                    startActivity(intent);
                    break;
                case 2:
                    intent = new Intent(this, ManagerKindActivity.class);
                    startActivity(intent);
                    break;
                case 3:
                    intent = new Intent(this, ManagerItemActivity.class);
                    startActivity(intent);
                    break;
                case 4:
                    intent = new Intent(this, ChooseKindActivity.class);
                    startActivity(intent);
                    break;
                case 5:
                    intent = new Intent(this, ViewBidActivity.class);
                    startActivity(intent);
                    break;
            }
        }
    }
}
