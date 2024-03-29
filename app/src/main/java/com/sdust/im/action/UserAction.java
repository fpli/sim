package com.sdust.im.action;

import com.sdust.im.bean.*;
import com.sdust.im.global.Result;
import com.sdust.im.network.NetService;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class UserAction {

	private static NetService mNetService = NetService.getInstance();

	public static void accountVerify(String account) {
		TranObject t = new TranObject(account, TranObjectType.REGISTER_ACCOUNT);
		mNetService.send(t);
	}

	public static void register(User user) {
		TranObject t = new TranObject(user, TranObjectType.REGISTER);
		mNetService.send(t);
	}

	public static void loginVerify(User user) {
		TranObject t = new TranObject(user, TranObjectType.LOGIN);
		mNetService.send(t);
	}

	public static void searchFriend(String message) {
		TranObject t = new TranObject(message, TranObjectType.SEARCH_FRIEND);
		mNetService.send(t);
	}

	public static void sendFriendRequest(Result result, Integer id) {
		TranObject t = new TranObject();
		t.setReceiveId(id);
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd hh:mm:ss");
		String sendTime = sdf.format(date);
		t.setSendTime(sendTime);
		User user = ApplicationData.getInstance().getUserInfo();
		t.setResult(result);
		t.setSendId(user.getId());
		t.setTranType(TranObjectType.FRIEND_REQUEST);
		t.setSendName(user.getUserName());
		mNetService.send(t);
	}

	public static void sendMessage(ChatEntity message) {
		TranObject t = new TranObject();
		t.setTranType(TranObjectType.MESSAGE);
		t.setReceiveId(message.getReceiverId());
		t.setSendName(ApplicationData.getInstance().getUserInfo().getUserName());
		t.setObject(message);
		mNetService.send(t);
	}

}
