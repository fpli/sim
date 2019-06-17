package com.sdust.im.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.sdust.im.R;
import com.sdust.im.bean.User;
import com.sdust.im.util.PhotoUtils;

import java.util.Date;
import java.util.List;

public class FriendSearchResultAdapter extends BaseAdapter {

	private List<User> mVector;
	private LayoutInflater mInflater;
	private Context mContext0;

	public FriendSearchResultAdapter(Context context, List<User> vector) {
		this.mVector = vector;
		mInflater = LayoutInflater.from(context);
		mContext0 = context;
	}

	public View getView(int position, View convertView, ViewGroup root) {
		ImageView avatarView;
		TextView nameView;
		TextView ageView;
		TextView sexView;

		User user = mVector.get(position);
		Bitmap photo = PhotoUtils.getBitmap(user.getPhoto());
		String name = user.getUserName();
		int age = new Date().getYear() - user.getBirthday().getYear();
		int gender = user.getGender();

		convertView = mInflater.inflate(R.layout.friend_search_result_item, null);
		avatarView = convertView.findViewById(R.id.cc0_friend_search_result_item_avatar);
		nameView = convertView.findViewById(R.id.cc0_friend_search_result_item_name);
		ageView = convertView.findViewById(R.id.cc0_friend_search_result_item_age);
		sexView = convertView.findViewById(R.id.cc0_friend_search_result_item_gender);

		nameView.setText(name);

		if (photo != null) {
			avatarView.setImageBitmap(photo);
		}
		ageView.setText(age + "岁");
		if (gender == 0) {
			sexView.setText("女");
		} else {
			sexView.setText("男");
		}

		return convertView;
	}

	public int getCount() {
		return mVector.size();
	}

	public Object getItem(int position) {
		return mVector.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

}
