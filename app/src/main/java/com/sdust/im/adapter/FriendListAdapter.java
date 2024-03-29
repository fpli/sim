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
import com.sdust.im.bean.ApplicationData;
import com.sdust.im.bean.User;

import java.util.List;

public class FriendListAdapter extends BaseAdapter {

	private List<User> mFriendList;
	private LayoutInflater mInflater;

	public FriendListAdapter(Context context, List<User> vector) {
		this.mFriendList = vector;
		mInflater = LayoutInflater.from(context);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup root) {
		ImageView avatarView;
		TextView nameView;
		ImageView isOnline;
		TextView introView;
		User user = mFriendList.get(position);
		Bitmap photo = (ApplicationData.getInstance().getFriendPhotoMap()).get(user.getId());
		String name = user.getUserName();
		String briefIntro = user.getUserBriefIntro();
		convertView = mInflater.inflate(R.layout.friend_list_item, null);
		avatarView =  convertView.findViewById(R.id.user_photo);
		nameView = convertView.findViewById(R.id.friend_list_name);
		isOnline = convertView.findViewById(R.id.stateicon);
		
		introView = convertView.findViewById(R.id.friend_list_brief);
		
		nameView.setText(name);
		
		if(!user.isOnline()) {
			isOnline.setVisibility(View.GONE);
		}
		if (photo != null) {
			avatarView.setImageBitmap(photo);
		}
		introView.setText(briefIntro);
		return convertView;
	}

	public int getCount() {
		return mFriendList.size();
	}

	public Object getItem(int position) {
		return mFriendList.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

}
