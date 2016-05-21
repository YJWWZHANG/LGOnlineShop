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

/**
 * Created by admin on 2016/3/24.
 */
public class ViewBidFragment extends Fragment {

    Button buttonHome;
    ListView listViewBid;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.fragment_view_bid, container, false);
        buttonHome = (Button) rootView.findViewById(R.id.button_home);
        listViewBid = (ListView) rootView.findViewById(R.id.list_view_bid);

        buttonHome.setOnClickListener(new HomeListener(getActivity()));
        String url = HttpUtil.BASE_URL + "viewBid.jsp";
        try
        {
            JSONArray jsonArray = new JSONArray(HttpUtil.getRequest(url));
            JSONArrayAdapter adapter = new JSONArrayAdapter(getActivity()
                    , jsonArray, "item", true);
            listViewBid.setAdapter(adapter);
        }
        catch (Exception e)
        {
            DialogTool.createMessageDialog(getActivity(), null,
                    "服务器响应异常，请稍后再试！", "确定", null, 0, false).show();
            e.printStackTrace();
        }
        listViewBid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                viewBidDetail(position);
            }
        });

        return rootView;
    }

    private void viewBidDetail(int position){
        View detailView = getActivity().getLayoutInflater().inflate(R.layout.dialog_bid_detail, null);
        TextView itemName = (TextView) detailView
                .findViewById(R.id.text_view_item_name);
        TextView bidPrice = (TextView) detailView
                .findViewById(R.id.text_view_bid_price);
        TextView bidTime = (TextView) detailView
                .findViewById(R.id.text_view_bid_time);
        TextView bidUser = (TextView) detailView
                .findViewById(R.id.text_view_bid_user);
        JSONObject jsonObj = (JSONObject) listViewBid.getAdapter()
                .getItem(position);
        try
        {
            itemName.setText(jsonObj.getString("item"));
            bidPrice.setText(jsonObj.getString("price"));
            bidTime.setText(jsonObj.getString("bidDate"));
            bidUser.setText(jsonObj.getString("user"));
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
        DialogTool.createRandomDialog(getActivity(), null, "确定",
                null, null, null, detailView, 0, false).show();
    }
}
