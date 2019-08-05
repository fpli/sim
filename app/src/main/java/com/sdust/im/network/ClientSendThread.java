package com.sdust.im.network;

import com.sdust.im.bean.TranObject;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

public class ClientSendThread {

	private Socket mSocket;
	private ObjectOutputStream oos;
	private ExecutorService executorService = Executors.newSingleThreadExecutor();

	public ClientSendThread(Socket socket) {
		this.mSocket = socket;
		try {
			oos = new ObjectOutputStream(mSocket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void sendMessage(final TranObject t) {
		executorService.submit(new SendTranObjectRunnable(t));
	}

	private class SendTranObjectRunnable implements Runnable{

		private TranObject t;

		public SendTranObjectRunnable(TranObject t) {
			super();
			this.t = t;
		}

		@Override
		public void run() {
			try {
				oos.writeObject(t);
				oos.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
}
