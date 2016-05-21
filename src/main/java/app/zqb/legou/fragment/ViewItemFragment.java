package app.zqb.legou.fragment;

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
import app.zqb.legou.other.HomeListener;
import app.zqb.legou.other.JSONArrayAdapter;
import app.zqb.legou.util.DialogTool;
import app.zqb.legou.util.HttpUtil;
import app.zqb.legou.util.L;

/**
 * Created by admin on 2016/3/17.
 */
public class ViewItemFragment extends Fragment {

    private Button buttonHome;
    private TextView viewTitle;
    private ListView successList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_view_item, container, false);
        buttonHome = (Button) rootView.findViewById(R.id.button_home);
        viewTitle = (TextView) rootView.findViewById(R.id.text_view_title);
        successList = (ListView) rootView.findViewById(R.id.list_view_success);
        buttonHome.setOnClickListener(new HomeListener(getActivity()));
        String action = getArguments().getString("action");
        String url = HttpUtil.BASE_URL + action;
        if(action.equals("viewFail.jsp")){
            viewTitle.setText(R.string.view_fail);
        }
        try{
            JSONArray jsonArray = new JSONArray(HttpUtil.getRequest(url));
            JSONArrayAdapter adapter = new JSONArrayAdapter(getActivity(), jsonArray, "name", true);
            successList.setAdapter(adapter);
        }catch (Exception e){
            DialogTool.createMessageDialog(getActivity(), null,
                    "服务器响应异常，请稍后再试！", "确定", null, 0, false).show();
            e.printStackTrace();
        }
        successList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                viewItemDetail(position);
            }
        });
        return rootView;
    }

    private void viewItemDetail(int position){
        L.d("viewItemDetail()");
        View detailView = getActivity().getLayoutInflater().inflate(R.layout.dialog_detail, null);
        TextView itemName = (TextView) detailView.findViewById(R.id.item_name);
        TextView itemKind = (TextView) detailView.findViewById(R.id.item_kind);
        TextView maxPrice = (TextView) detailView.findViewById(R.id.max_price);
        TextView itemRemark = (TextView) detailView.findViewById(R.id.item_remark);
        JSONObject jsonObject = (JSONObject) successList.getAdapter().getItem(position);
        try{
            itemName.setText(jsonObject.getString("name"));
            itemKind.setText(jsonObject.getString("kind"));
            maxPrice.setText(jsonObject.getString("maxPrice"));
            itemRemark.setText(jsonObject.getString("remark"));
        }catch (JSONException e){
            e.printStackTrace();
        }
        DialogTool.createRandomDialog(getActivity(), null, "确定",
                "", null, null, detailView, 0, false).show();
    }
}
