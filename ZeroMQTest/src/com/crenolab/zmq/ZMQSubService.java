package com.crenolab.zmq;

import org.zeromq.ZMQ;
import org.zeromq.ZMQ.Context;
import org.zeromq.ZMQ.Socket;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;

@SuppressLint("HandlerLeak")
public class ZMQSubService extends Service {
	
/*	private Context context;
	private Socket socket;*/
	
	private SubRunnable subRunnable;
	private Thread subThread;
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		
/*		this.context = ZMQ.context( 1 );
		this.socket = this.context.socket( ZMQ.PUB );*/
	}
	


	private Handler handler = new Handler() {
    	public void handleMessage(android.os.Message msg) {
//    		System.out.println( "Message(in service): " + msg.obj );
    		Intent intent = new Intent( "zmqbroadcast" );
    		intent.putExtra( "message", (String)msg.obj );
    		sendBroadcast( intent );
    		switch( msg.what )
    		{
    		case SubRunnable.ZMQ_SUB:
/*    			Intent intent = new Intent( "android.intent.action.MAIN" );
    			intent.putExtra( "message", (String)msg.obj );
    			sendBroadcast( intent );*/
    			
    			break;
    		}
    	};
    };
    
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		Bundle extras = intent.getExtras();
		String url = extras.getString( "url" );
		int port = extras.getInt( "port" );
		String topic = extras.getString( "topic" );
/*		this.socket.bind( "tcp://" + url + ":" + port );
		this.socket.subscribe( topic.getBytes() );*/
		this.subRunnable = new SubRunnable( url, port, topic, this.handler); 
		this.subThread = new Thread( subRunnable );
		this.subThread.start();
		
/*		while( true ) {
			byte[] bytes = this.socket.recv();
			String str = new String( bytes );
			
			System.out.println( str );
			
			Message message = new Message();
			message.what = SubThread.ZMQ_SUB;
			message.obj = str;
			this.handler.sendMessage( message );
		}*/
		
		return super.onStartCommand(intent, flags, startId);
	}
	
	@Override
		public void onDestroy() {
			// TODO Auto-generated method stub
			super.onDestroy();
			this.subThread.interrupt();
			this.subThread = null;
		}
	
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

}
