package app.zqb.legou.other;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import app.zqb.legou.R;

/**
 * Created by admin on 2016/3/17.
 */
public class JSONArrayAdapter extends BaseAdapter {

    private Context context;
    private JSONArray jsonArray;
    private String property;
    private boolean hasIcon;

    public JSONArrayAdapter(Context context, JSONArray jsonArray, String property, boolean hasIcon){
        this.context = context;
        this.jsonArray = jsonArray;
        this.property = property;
        this.hasIcon = hasIcon;
    }

    @Override
    public int getCount() {
        return jsonArray.length();
    }

    @Override
    public Object getItem(int position) {
        return jsonArray.optJSONObject(position);
    }

    @Override
    public long getItemId(int position) {
        try {
            return ((JSONObject)getItem(position)).getInt("id");
        }catch (JSONException e){
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        ImageView iv = new ImageView(context);
        iv.setPadding(10, 0, 20, 0);
        iv.setImageResource(R.mipmap.item);
        linearLayout.addView(iv);
        TextView tv = new TextView(context);
        try {
            String itemName = ((JSONObject)getItem(position)).getString(property);
            tv.setText(itemName);
        }catch (JSONException e){
            e.printStackTrace();
        }
        tv.setTextSize(20);
        if(hasIcon){
            linearLayout.addView(tv);
            return linearLayout;
        }
        else {
            return tv;
        }
    }
}
