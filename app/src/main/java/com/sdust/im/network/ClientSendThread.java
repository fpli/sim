package com.sdust.im.network;

import com.sdust.im.bean.TranObject;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientSendThread {

	private Socket mSocket;
	private ObjectOutputStream oos;

	public ClientSendThread(Socket socket) {
		this.mSocket = socket;
		try {
			oos = new ObjectOutputStream(mSocket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	public void sendMessage(final TranObject t) throws IOException {
		new Thread(){
			@Override
			public void run() {
				try {
					oos.writeObject(t);
					oos.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}.start();

	}
}
