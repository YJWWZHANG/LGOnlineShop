package app.zqb.legou.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import app.zqb.legou.R;
import app.zqb.legou.other.HomeListener;
import app.zqb.legou.other.JSONArrayAdapter;
import app.zqb.legou.util.DialogTool;
import app.zqb.legou.util.HttpUtil;

/**
 * Created by admin on 2016/3/19.
 */
public class AddItemFragment extends Fragment {

    EditText editTextItemName, editTextItemDesc,editTextItemRemark,editTextInitPrice;
    Spinner spinnerItemKind , spinnerAvailTime;
    Button buttonAdd, buttonCancel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.fragment_add_item, container, false);
        editTextItemName = (EditText) rootView.findViewById(R.id.edit_text_item_name);
        editTextItemDesc = (EditText) rootView.findViewById(R.id.edit_text_item_desc);
        editTextItemRemark = (EditText) rootView.findViewById(R.id.edit_text_item_remark);
        editTextInitPrice = (EditText) rootView.findViewById(R.id.edit_text_init_price);
        spinnerItemKind = (Spinner) rootView.findViewById(R.id.spinner_item_kind);
        spinnerAvailTime = (Spinner) rootView.findViewById(R.id.spinner_avail_time);
        String url = HttpUtil.BASE_URL + "viewKind.jsp";
        JSONArray jsonArray = null;
        try
        {
            jsonArray = new JSONArray(HttpUtil.getRequest(url));  //①
        }
        catch (Exception e1)
        {
            e1.printStackTrace();
        }
        JSONArrayAdapter adapter = new JSONArrayAdapter(
                getActivity() , jsonArray , "kindName" , false);
        spinnerItemKind.setAdapter(adapter);
        buttonAdd = (Button) rootView.findViewById(R.id.button_add);
        buttonCancel = (Button) rootView.findViewById(R.id.button_cancel);
        buttonCancel.setOnClickListener(new HomeListener(getActivity()));
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validate()){
                    String name = editTextItemName.getText().toString();
                    String desc = editTextItemDesc.getText().toString();
                    String remark = editTextItemRemark.getText().toString();
                    String price = editTextInitPrice.getText().toString();
                    JSONObject kind = (JSONObject) spinnerItemKind.getSelectedItem();
                    int avail = spinnerAvailTime.getSelectedItemPosition();
                    switch(avail)
                    {
                        case 5 :
                            avail = 7;
                            break;
                        case 6 :
                            avail = 30;
                            break;
                        default :
                            avail += 1;
                            break;
                    }
                    try
                    {
                        String result = addItem(name, desc
                                , remark , price , kind.getInt("id") , avail);
                        DialogTool.createMessageDialog(getActivity(), null, result,
                                "确定", null, 0, false).show();
                    }
                    catch (Exception e)
                    {
                        DialogTool.createMessageDialog(getActivity(), null,
                                "服务器响应异常，请稍后再试！", "确定", null, 0, false).show();
                        e.printStackTrace();
                    }
                }
            }
        });
        return rootView;
    }

    private boolean validate()
    {
        String name = editTextItemName.getText().toString().trim();
        if (name.equals(""))
        {
            DialogTool.createMessageDialog(getActivity(), null, "物品名称是必填项！",
                    "确定", null, 0, false).show();
            return false;
        }
        String price = editTextInitPrice.getText().toString().trim();
        if (price.equals(""))
        {
            DialogTool.createMessageDialog(getActivity(), null, "起拍价格是必填项！",
                    "确定", null, 0, false).show();
            return false;
        }
        try
        {
            Double.parseDouble(price);
        }
        catch(NumberFormatException e)
        {
            DialogTool.createMessageDialog(getActivity(), null, "起拍价格必须是数值！",
                    "确定", null, 0, false).show();
            return false;
        }
        return true;
    }

    private String addItem(String name, String desc
            , String remark , String initPrice , int kindId , int availTime)
            throws Exception
    {
        Map<String , String> map = new HashMap<String, String>();
        map.put("itemName" , name);
        map.put("itemDesc" , desc);
        map.put("itemRemark" , remark);
        map.put("initPrice" , initPrice);
        map.put("kindId" , kindId + "");
        map.put("availTime" , availTime + "");
        String url = HttpUtil.BASE_URL + "addItem.jsp";
        return HttpUtil.postRequest(url , map);
    }
}
