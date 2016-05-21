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

import org.json.JSONArray;

import app.zqb.legou.R;
import app.zqb.legou.other.Callbacks;
import app.zqb.legou.other.HomeListener;
import app.zqb.legou.other.KindArrayAdapter;
import app.zqb.legou.util.DialogTool;
import app.zqb.legou.util.HttpUtil;

/**
 * Created by admin on 2016/3/22.
 */
public class ChooseKindFragment extends Fragment {

    public static final int CHOOSE_ITEM = 0x1008;
    Callbacks mCallbacks;
    Button buttonHome;
    ListView listViewKind;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.fragment_choose_kind, container, false);
        buttonHome = (Button) rootView.findViewById(R.id.button_home);
        listViewKind = (ListView) rootView.findViewById(R.id.list_view_kind);
        buttonHome.setOnClickListener(new HomeListener(getActivity()));

        String url = HttpUtil.BASE_URL + "viewKind.jsp";
        try
        {
            JSONArray jsonArray = new JSONArray(
                    HttpUtil.getRequest(url));
            listViewKind.setAdapter(new KindArrayAdapter(getActivity(), jsonArray));
        }
        catch (Exception e)
        {
            DialogTool.createMessageDialog(getActivity(), null,
                    "服务器响应异常，请稍后再试！", "确定", null, 0, false);
            e.printStackTrace();
        }

        listViewKind.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                bundle.putLong("kindId", id);
                mCallbacks.onItemSelected(CHOOSE_ITEM , bundle);
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
                    "ManageKindFragment所在的Activity必须实现Callbacks接口!");
        }
        mCallbacks = (Callbacks) activity;
    }
    // 当该Fragment从它所属的Activity中被删除时回调该方法
    @Override
    public void onDetach()
    {
        super.onDetach();
        mCallbacks = null;
    }
}
