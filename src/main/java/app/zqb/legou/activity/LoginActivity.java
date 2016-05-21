package app.zqb.legou.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import app.zqb.legou.R;
import app.zqb.legou.other.HomeListener;
import app.zqb.legou.util.DialogTool;
import app.zqb.legou.util.HttpUtil;
import app.zqb.legou.util.L;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends BaseActivity{

    EditText editTextUser, editTextPassword;
    Button buttonLogin, buttonCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextUser = (EditText) findViewById(R.id.edit_text_user);
        editTextPassword = (EditText) findViewById(R.id.edit_text_password);
        buttonLogin = (Button) findViewById(R.id.button_login);
        buttonCancel = (Button) findViewById(R.id.button_cancel);

        buttonCancel.setOnClickListener(new HomeListener(this));
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validate()){
                    if(loginPro()){
                        Intent intent = new Intent(LoginActivity.this, LGClientActivity.class);
                        startActivity(intent);
                        finish();
                    }else {
                        DialogTool.createMessageDialog(LoginActivity.this, null, "用户名或密码错误",
                                "确定", null, 0, false).show();
                    }
                }
            }
        });
    }

    private boolean loginPro(){
        String userName = editTextUser.getText().toString();
        String userPassword = editTextPassword.getText().toString();
        L.d("loginPro() " + userName + " " + userPassword);
        JSONObject jsonObject;
        try{
            jsonObject = query(userName, userPassword);
            if (jsonObject.getInt("userId") > 0){
                return true;
            }
        }catch (Exception e){
            DialogTool.createMessageDialog(this, null, "服务响应异常",
                    "确定", null, 0, false).show();
            e.printStackTrace();
        }
        return false;
    }

    private boolean validate(){
        String userName = editTextUser.getText().toString().trim();
        if(userName.equals("")){
            DialogTool.createMessageDialog(this, null, "请输入用户名", "确定", null ,0, false).show();
            return false;
        }
        String userPassword = editTextPassword.getText().toString().trim();
        if(userPassword.equals("")){
            DialogTool.createMessageDialog(this, null, "请输入密码", "确定", null ,0, false).show();
            return false;
        }
        return true;
    }

    private JSONObject query(String userName, String userPassword) throws Exception{
        Map<String, String> map = new HashMap<String, String>();
        map.put("user", userName);
        map.put("pass", userPassword);
        String url = HttpUtil.BASE_URL + "login.jsp";
        L.d("query() " + url);
        return new JSONObject(HttpUtil.postRequest(url, map));
    }
}


