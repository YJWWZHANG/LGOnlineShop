package app.zqb.legou.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import app.zqb.legou.R;
import app.zqb.legou.other.HomeListener;
import app.zqb.legou.util.DialogTool;
import app.zqb.legou.util.HttpUtil;

/**
 * Created by admin on 2016/3/23.
 */
public class AddBidFragment extends Fragment {

    TextView textViewItemName, textViewItemDesc,textViewItemRemark,textViewItemKind
            ,textViewInitPrice , textViewMaxPrice ,textViewEndTime;
    EditText editTextBidPrice;
    Button buttonAdd, buttonCancel;
    JSONObject jsonObj;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.fragment_add_bid, container, false);
        textViewItemName = (TextView) rootView.findViewById(R.id.text_view_item_name);
        textViewItemDesc = (TextView) rootView.findViewById(R.id.text_view_item_desc);
        textViewItemRemark = (TextView) rootView.findViewById(R.id.text_view_item_remark);
        textViewItemKind = (TextView) rootView.findViewById(R.id.text_view_item_kind);
        textViewInitPrice = (TextView) rootView.findViewById(R.id.text_view_init_price);
        textViewMaxPrice = (TextView) rootView.findViewById(R.id.text_view_max_price);
        textViewEndTime = (TextView) rootView.findViewById(R.id.text_view_end_time);
        editTextBidPrice = (EditText) rootView.findViewById(R.id.edit_text_bid_price);
        buttonAdd = (Button) rootView.findViewById(R.id.button_add);
        buttonCancel = (Button) rootView.findViewById(R.id.button_cancel);

        buttonCancel.setOnClickListener(new HomeListener(getActivity()));
        String url = HttpUtil.BASE_URL + "getItem.jsp?itemId="
                + getArguments().getInt("itemId");
        try
        {
            jsonObj = new JSONObject(HttpUtil.getRequest(url));
            textViewItemName.setText(jsonObj.getString("name"));
            textViewItemDesc.setText(jsonObj.getString("desc"));
            textViewItemRemark.setText(jsonObj.getString("remark"));
            textViewItemKind.setText(jsonObj.getString("kind"));
            textViewInitPrice.setText(jsonObj.getString("initPrice"));
            textViewMaxPrice.setText(jsonObj.getString("maxPrice"));
            textViewEndTime.setText(jsonObj.getString("endTime"));
        }
        catch (Exception e1)
        {
            DialogTool.createMessageDialog(getActivity(), null,
                    "服务器响应出现异常！", "确定", null, 0, false).show();
            e1.printStackTrace();
        }
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    double curPrice = Double.parseDouble(
                            editTextBidPrice.getText().toString());
                    if( curPrice <  jsonObj.getDouble("maxPrice"))
                    {
                        DialogTool.createMessageDialog(getActivity(), null,
                                "您输入的竞价必须高于当前竞价", "确定", null, 0, false).show();
                    }
                    else{
                        String result = addBid(jsonObj.getString("id")
                                , curPrice + "");
                        DialogTool.createMessageDialog(getActivity(), null,
                                result, "确定", null, 0, false).show();
                    }
                }catch (NumberFormatException ne){
                    DialogTool.createMessageDialog(getActivity(), null,
                            "您输入的竞价必须是数值", "确定", null, 0, false).show();
                }catch (Exception e){
                    e.printStackTrace();
                    DialogTool.createMessageDialog(getActivity(), null,
                            "服务器响应出现异常，请重试！", "确定", null, 0, false).show();
                }
            }
        });

        return rootView;
    }

    private String addBid(String itemId , String bidPrice)
            throws Exception
    {
        Map<String , String> map = new HashMap<String, String>();
        map.put("itemId" , itemId);
        map.put("bidPrice" , bidPrice);
        String url = HttpUtil.BASE_URL + "addBid.jsp";
        return HttpUtil.postRequest(url , map);
    }
}
