package app.zqb.legou.other;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import app.zqb.legou.R;

/**
 * Created by admin on 2016/3/18.
 */
public class KindArrayAdapter extends BaseAdapter{

    private JSONArray kindArray;
    private Context context;

    public KindArrayAdapter(Context context, JSONArray kinkArray){
        this.kindArray = kinkArray;
        this.context = context;
    }

    @Override
    public int getCount() {
        return kindArray.length();
    }

    @Override
    public Object getItem(int position) {
        return kindArray.optJSONObject(position);
    }

    @Override
    public long getItemId(int position) {
        try{
            return ((JSONObject)getItem(position)).getInt("id");
        }catch (JSONException e){
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LinearLayout container = new LinearLayout(context);
        container.setOrientation(LinearLayout.VERTICAL);
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        ImageView iv = new ImageView(context);
        iv.setPadding(10, 0, 20, 0);
        iv.setImageResource(R.mipmap.item);
        linearLayout.addView(iv);

        TextView tv = new TextView(context);
        try {
            String kindName = ((JSONObject)getItem(position)).getString("kindName");
            tv.setText(kindName);
        }catch (JSONException e){
            e.printStackTrace();
        }
        tv.setTextSize(20);
        linearLayout.addView(tv);
        container.addView(linearLayout);

        TextView descView = new TextView(context);
        descView.setPadding(30, 0, 0, 0);
        try {
            String kindDesc = ((JSONObject)getItem(position)).getString("kindDesc");
            descView.setText(kindDesc);
        }catch (JSONException e){
            e.printStackTrace();
        }
        descView.setTextSize(16);

        container.addView(descView);
        return container;
    }
}
