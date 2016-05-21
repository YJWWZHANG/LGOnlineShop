package app.zqb.legou.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
 * Created by admin on 2016/3/18.
 */
public class ManagerKindFragment extends Fragment {

    public static final int ADD_KIND = 0x1007;

    private Button buttonHome, buttonAdd;
    private ListView listViewKind;
    private Callbacks mCallback;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.fragment_manager_kind, container, false);
        buttonHome = (Button) rootView.findViewById(R.id.button_home);
        buttonAdd = (Button) rootView.findViewById(R.id.button_add);
        listViewKind = (ListView) rootView.findViewById(R.id.list_view_kind);

        buttonHome.setOnClickListener(new HomeListener(getActivity()));
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View source) {
                mCallback.onItemSelected(ADD_KIND, null);
            }
        });
        String url = HttpUtil.BASE_URL + "viewKind.jsp";
        try {
            final JSONArray jsonArray = new JSONArray(HttpUtil.getRequest(url));
            listViewKind.setAdapter(new KindArrayAdapter(getActivity(), jsonArray));
        }catch (Exception e){
            DialogTool.createMessageDialog(getActivity(), null,
                    "服务器响应异常，请稍后再试！", "确定", null, 0, false);
            e.printStackTrace();
        }
        return rootView;
    }

    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
        if(!(activity instanceof Callbacks)){
            throw new IllegalStateException("ManageKindFragment所在的Activity必须实现Callbacks接口!");
        }
        mCallback = (Callbacks) activity;
    }

    @Override
    public void onDetach(){
        super.onDetach();
        mCallback = null;
    }
}
