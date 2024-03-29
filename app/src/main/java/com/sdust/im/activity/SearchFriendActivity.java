package com.sdust.im.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import com.sdust.im.BaseActivity;
import com.sdust.im.R;
import com.sdust.im.action.UserAction;
import com.sdust.im.bean.ApplicationData;
import com.sdust.im.bean.TranObject;
import com.sdust.im.bean.User;
import com.sdust.im.util.VerifyUtils;
import com.sdust.im.view.TitleBarView;

import java.util.ArrayList;

public class SearchFriendActivity extends BaseActivity implements OnClickListener {

	private TitleBarView mTitleBarView;
	private EditText mSearchEtName;
	private Button mBtnSearchByName;

	private Spinner mSearchSpLowage;
	private Spinner mSearchSpHighage;
	private RadioGroup mRgpSex;

	private Button mBtnSearchByElse;
	private static boolean mIsReceived;
	private boolean flag = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_searchfriend);
		initViews();
		initEvents();
	}

	@Override
	protected void initViews() {
		mTitleBarView = findViewById(R.id.title_bar);
		mTitleBarView.setCommonTitle(View.GONE, View.VISIBLE, View.GONE);
		mTitleBarView.setTitleText("查找朋友");
		mSearchEtName = findViewById(R.id.search_friend_by_name_edit_name);
		mBtnSearchByName = findViewById(R.id.search_friend_by_name_btn_search);

		mSearchSpLowage = findViewById(R.id.search_friend_by_else_spinner_lowage);
		mSearchSpHighage = findViewById(R.id.search_friend_by_else_spinner_highage);
		mRgpSex = findViewById(R.id.search_friend_by_else_rgp_choose_sex);
		mBtnSearchByElse = findViewById(R.id.search_friend_by_else_btn_search);

	}

	@Override
	protected void initEvents() {
		mIsReceived = false;
		mBtnSearchByName.setOnClickListener(this);
		mBtnSearchByElse.setOnClickListener(this);

	}

	public static void messageArrived(TranObject mReceived){
		ArrayList<User> list = (ArrayList<User>)mReceived.getObject();
		ApplicationData.getInstance().setFriendSearched(list);
		mIsReceived = true;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.search_friend_by_name_btn_search:
			flag = false;
			final String searchName = mSearchEtName.getText().toString();
			if (searchName.equals("")) {
				showCustomToast("请填写账号");
				mSearchEtName.requestFocus();
			} else if (!VerifyUtils.matchAccount(searchName)) {
				showCustomToast("账号格式错误");
				mSearchEtName.requestFocus();
			} else {
				try {
					flag = true;
					UserAction.searchFriend("0" + " " + searchName);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			break;
		case R.id.search_friend_by_else_btn_search:
			flag = false;
			int lage = mSearchSpLowage.getSelectedItemPosition() + 5;
			int uage = mSearchSpHighage.getSelectedItemPosition() + 5;
			if (lage > uage)
				showCustomToast("年龄不合法");
			else {
				int sex = 3;// 默认全部
				int choseId = mRgpSex.getCheckedRadioButtonId();
				switch (choseId) {
				case R.id.search_friend_by_else_rdbtn_female:
					sex = 0;
					break;
				case R.id.search_friend_by_else_rdbtn_male:
					sex = 1;
					break;
				default:
					break;
				}
				try {
					flag = true;
					UserAction.searchFriend("1" + " " + lage + " " + uage + " "
							+ sex);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			break;
		default:
			break;
		}
		if (flag) {
			mIsReceived = false;
			showLoadingDialog("正在查找...");
			while (!mIsReceived) {

			}
			System.out.println("准备跳转查找结果页面");
			Intent intent = new Intent(this, FriendSearchResultActivity.class);
			startActivity(intent);
			finish();
			System.out.println("已跳转");
		}

	}

}
