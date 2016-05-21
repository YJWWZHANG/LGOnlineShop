package app.zqb.legou.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import app.zqb.legou.R;
import app.zqb.legou.other.Callbacks;

/**
 * Created by admin on 2016/3/17.
 */
public class LGListFragment extends Fragment {

    private ListView lgList;
    private Callbacks mCallbacks;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.fragment_lg_list, container, false);
        lgList = (ListView) rootView.findViewById(R.id.lg_list);
        lgList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mCallbacks.onItemSelected(position, null);
            }
        });
        return rootView;
    }

    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
        if(!(activity instanceof Callbacks)){
            throw new IllegalStateException("LGListFragment所在的Activity必须实现Callbacks接口!");
        }
        mCallbacks = (Callbacks) activity;
    }

    @Override
    public void onDetach(){
        super.onDetach();
        mCallbacks = null;
    }

    public void setActivateOnItemClick(boolean activateOnItemClick){
        lgList.setChoiceMode(activateOnItemClick? ListView.CHOICE_MODE_SINGLE : ListView.CHOICE_MODE_NONE);
    }
}
