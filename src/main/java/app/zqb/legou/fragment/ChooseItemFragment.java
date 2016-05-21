package app.zqb.legou.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import app.zqb.legou.R;
import app.zqb.legou.other.Callbacks;
import app.zqb.legou.other.HomeListener;
import app.zqb.legou.other.JSONArrayAdapter;
import app.zqb.legou.util.DialogTool;
import app.zqb.legou.util.HttpUtil;

/**
 * Created by admin on 2016/3/22.
 */
public class ChooseItemFragment extends Fragment {

    public static final int ADD_BID = 0x1009;
    Button buttonHome;
    ListView listViewSuccess;
    TextView textViewTitle;
    Callbacks mCallbacks;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.fragment_view_item, container, false);
        buttonHome = (Button) rootView.findViewById(R.id.button_home);
        listViewSuccess = (ListView) rootView.findViewById(R.id.list_view_success);
        textViewTitle = (TextView) rootView.findViewById(R.id.text_view_title);
        buttonHome.setOnClickListener(new HomeListener(getActivity()));

        long kindId = getArguments().getLong("kindId");
        String url = HttpUtil.BASE_URL + "itemList.jsp?kindId=" + kindId;
        try
        {
            JSONArray jsonArray = new JSONArray(HttpUtil.getRequest(url));
            JSONArrayAdapter adapter = new JSONArrayAdapter(
                    getActivity() , jsonArray , "name" , true);
            listViewSuccess.setAdapter(adapter);
        }
        catch (Exception e1)
        {
            DialogTool.createMessageDialog(getActivity(), null,
                    "服务器响应异常，请稍后再试！", "确定", null, 0, false);
            e1.printStackTrace();
        }

        textViewTitle.setText(R.string.item_list);
        listViewSuccess.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                JSONObject jsonObj = (JSONObject) listViewSuccess
                        .getAdapter().getItem(position);
                Bundle bundle = new Bundle();
                try
                {
                    bundle.putInt("itemId" , jsonObj.getInt("id"));
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
                mCallbacks.onItemSelected(ADD_BID, bundle);
            }
        });

        return rootView;
    }

    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
        if (!(activity instanceof Callbacks))
        {
            throw new IllegalStateException(
                    "ManagerItemFragment所在的Activity必须实现Callbacks接口!");
        }
        mCallbacks = (Callbacks) activity;
    }

    @Override
    public void onDetach()
    {
        super.onDetach();
        mCallbacks = null;
    }

}

