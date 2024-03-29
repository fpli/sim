package com.sdust.im.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import com.sdust.im.BaseActivity;
import com.sdust.im.R;
import com.sdust.im.action.UserAction;
import com.sdust.im.adapter.ChatMessageAdapter;
import com.sdust.im.bean.ApplicationData;
import com.sdust.im.bean.ChatEntity;
import com.sdust.im.database.ImDB;
import com.sdust.im.view.TitleBarView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ChatActivity extends BaseActivity {

	private TitleBarView        mTitleBarView;
	private int                 friendId;
	private String              friendName;
	private Button              sendButton;
	private ImageButton         emotionButton;
	private EditText            inputEdit;
	private ListView            chatMeessageListView;
	private ChatMessageAdapter  chatMessageAdapter;
	private List<ChatEntity>    chatList;

	private Handler             chatMessageHandler;

	private SimpleDateFormat sdf = new SimpleDateFormat("MM-dd hh:mm:ss");

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_chat);
		Intent intent = getIntent();
		friendName = intent.getStringExtra("friendName");
		friendId   = intent.getIntExtra("friendId", 0);
		initViews();
		initEvents();
	}

	@Override
	protected void initViews() {
		mTitleBarView = findViewById(R.id.title_bar);
		mTitleBarView.setCommonTitle(View.GONE, View.VISIBLE, View.GONE);
		mTitleBarView.setTitleText("与" + friendName + "对话");
		chatMeessageListView =  findViewById(R.id.chat_Listview);
		sendButton = findViewById(R.id.chat_btn_send);
		emotionButton = findViewById(R.id.chat_btn_emote);
		inputEdit = findViewById(R.id.chat_edit_input);
	}

	@Override
	protected void initEvents() {
		chatMessageHandler = new Handler() {
			public void handleMessage(Message msg) {
				switch (msg.what) {
					case 1:
						chatMessageAdapter.notifyDataSetChanged();
						chatMeessageListView.setSelection(chatList.size());
						break;
					default:
						break;
				}
			}
		};
		ApplicationData.getInstance().setChatHandler(chatMessageHandler);
		chatList = ApplicationData.getInstance().getChatMessagesMap().get(friendId);
		if (chatList == null){
			chatList = ImDB.getInstance(ChatActivity.this).getChatMessage(friendId);
			ApplicationData.getInstance().getChatMessagesMap().put(friendId, chatList);
		}
		chatMessageAdapter = new ChatMessageAdapter(ChatActivity.this, chatList);
		chatMeessageListView.setAdapter(chatMessageAdapter);
		sendButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				String content = inputEdit.getText().toString();
				inputEdit.setText("");
				ChatEntity chatMessage = new ChatEntity();
				chatMessage.setContent(content);
				chatMessage.setSenderId(ApplicationData.getInstance().getUserInfo().getId());
				chatMessage.setReceiverId(friendId);
				chatMessage.setMessageType(ChatEntity.SEND);
				Date date = new Date();
				String sendTime = sdf.format(date);
				chatMessage.setSendTime(sendTime);
				chatList.add(chatMessage);
				chatMessageAdapter.notifyDataSetChanged();
				chatMeessageListView.setSelection(chatList.size());
				UserAction.sendMessage(chatMessage);
				ImDB.getInstance(ChatActivity.this).saveChatMessage(chatMessage);
			}
		});
	}

}
