package com.example.ayush.manavta;

import android.content.Context;
import android.support.constraint.solver.widgets.ConstraintAnchor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class UserAdapter extends BaseAdapter {

    Context context;
    List<UserBean> userlist;

    public UserAdapter(Context context, List<UserBean> userlist) {
        this.context = context;
        this.userlist = userlist;
    }

    @Override
    public int getCount() {
        return userlist.size();
    }

    @Override
    public Object getItem(int position) {
        return userlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View userrow=inflater.inflate(R.layout.user_row,null);
        TextView usernametv=userrow.findViewById(R.id.usernametv);
        TextView usercontacttv=userrow.findViewById(R.id.usercontacttv);
        TextView userplacetv=userrow.findViewById(R.id.userplacetxt);

       UserBean user= userlist.get(position);
        usernametv.setText(user.getName());
        usercontacttv.setText(user.getContact());
        userplacetv.setText(user.getPlace());


        return userrow;
    }
}
