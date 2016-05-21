package app.zqb.legou.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
 * Created by admin on 2016/3/19.
 */
public class ManagerItemFragment extends Fragment {

    public static final int ADD_ITEM = 0x1006;
    private Button buttonHome,buttonAdd;
    private ListView listViewItem;
    private Callbacks mCallbacks;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_manager_item, container, false);
        buttonHome = (Button) rootView.findViewById(R.id.button_home);
        buttonAdd = (Button) rootView.findViewById(R.id.button_add);
        listViewItem = (ListView) rootView.findViewById(R.id.list_view_item);

        buttonHome.setOnClickListener(new HomeListener(getActivity()));
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallbacks.onItemSelected(ADD_ITEM, null);
            }
        });
        String url = HttpUtil.BASE_URL + "viewOwnerItem.jsp";
        try{
            JSONArray jsonArray = new JSONArray(HttpUtil.getRequest(url));
            JSONArrayAdapter adapter = new JSONArrayAdapter(getActivity(), jsonArray, "name", true);
            listViewItem.setAdapter(adapter);
        }catch (Exception e){
            DialogTool.createMessageDialog(getActivity(), null,
                    "服务器响应异常，请稍后再试！", "确定", null, 0, false);
            e.printStackTrace();
        }
        listViewItem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                viewItemInBid(position);
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

    private void viewItemInBid(int position){
        View detailView = getActivity().getLayoutInflater().inflate(R.layout.dialog_detail_in_bid, null);
        TextView itemName = (TextView) detailView
                .findViewById(R.id.text_view_item_name);
        TextView itemKind = (TextView) detailView
                .findViewById(R.id.text_view_item_kind);
        TextView maxPrice = (TextView) detailView
                .findViewById(R.id.text_view_max_price);
        TextView initPrice = (TextView) detailView
                .findViewById(R.id.text_view_init_price);
        TextView endTime = (TextView) detailView
                .findViewById(R.id.text_view_end_time);
        TextView itemRemark = (TextView) detailView
                .findViewById(R.id.text_view_item_remark);
        JSONObject jsonObject = (JSONObject) listViewItem.getAdapter().getItem(position);
        try
        {
            itemName.setText(jsonObject.getString("name"));
            itemKind.setText(jsonObject.getString("kind"));
            maxPrice.setText(jsonObject.getString("maxPrice"));
            itemRemark.setText(jsonObject.getString("desc"));
            initPrice.setText(jsonObject.getString("initPrice"));
            endTime.setText(jsonObject.getString("endTime"));
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
        DialogTool.createRandomDialog(getActivity(),null, "确定",
                null, null, null, detailView, 0, false).show();
    }
}
