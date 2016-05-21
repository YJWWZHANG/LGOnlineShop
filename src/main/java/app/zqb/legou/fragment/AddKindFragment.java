package app.zqb.legou.fragment;

import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.util.HashMap;
import java.util.Map;

import app.zqb.legou.R;
import app.zqb.legou.other.HomeListener;
import app.zqb.legou.util.DialogTool;
import app.zqb.legou.util.HttpUtil;
import app.zqb.legou.util.L;

/**
 * Created by admin on 2016/3/18.
 */
public class AddKindFragment extends Fragment {

    private EditText kindName, kindDesc;
    private Button buttonAdd, buttonCancel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.fragment_add_kind, container, false);
        kindName = (EditText) rootView.findViewById(R.id.edit_text_kind_name);
        kindDesc = (EditText) rootView.findViewById(R.id.edit_text_kind_desc);
        buttonAdd = (Button) rootView.findViewById(R.id.button_add);
        buttonCancel = (Button) rootView.findViewById(R.id.button_cancel);

        buttonCancel.setOnClickListener(new HomeListener(getActivity()));
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validate()){
                    String name = kindName.getText().toString();
                    String desc = kindDesc.getText().toString();
                    try {
                        String result = addKind(name, desc);
                        L.d("buttonAdd.setOnClickListener()->onClick() " + result.trim());
                        DialogTool.createMessageDialog(getActivity(), null,
                                result, "确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        kindName.setText("");
                                        kindDesc.setText("");
                                    }
                                }, 0, true).show();
                    }catch (Exception e){
                        DialogTool.createMessageDialog(getActivity(), null,
                                "服务器响应异常，请稍后再试！", "确定", null, 0, false).show();
                        e.printStackTrace();
                    }
                }
            }
        });
        return rootView;
    }

    private boolean validate(){
        String name = kindName.getText().toString().trim();
        if(name.equals("")){
            DialogTool.createMessageDialog(getActivity(), null,
                    "种类名称是必填项！", "确定", null, 0, false);
            return false;
        }
        return true;
    }

    private String addKind(String name , String desc) throws Exception{
        Map<String, String> map = new HashMap<String, String>();
        map.put("kindName", name);
        map.put("kindDesc", desc);
        String url = HttpUtil.BASE_URL + "addKind.jsp";
        return HttpUtil.postRequest(url, map);
    }
}
