package com.crenolab.zmq;

import org.zeromq.ZMQ;
import org.zeromq.ZMQ.Context;
import org.zeromq.ZMQ.Socket;

import android.os.Handler;
import android.os.Message;

public class SubRunnable implements Runnable
{
	public static final int ZMQ_SUB = 100;
	
	private String url;
	private int port;
	private String topic;
	
	private Handler handler;
	private Socket socket;
	private Context context;
	
	public SubRunnable( String url, int port, String topic, Handler handler )
	{
		this.url = url;
		this.port = port;
		this.topic = topic;
		this.handler = handler;
		this.context = ZMQ.context( 1 );
		this.socket = this.context.socket( ZMQ.SUB );
		
		socket.connect( "tcp://" + this.url + ":" + this.port );
		socket.subscribe( this.topic.getBytes() );
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		
		System.out.println( "Trying to receive" );
		
		
		while( !Thread.currentThread().isInterrupted() ) {
			byte[] bytes = this.socket.recv();
			String str = new String( bytes );
			
			System.out.println( str );
			
			Message message = new Message();
			message.what = SubRunnable.ZMQ_SUB;
			message.obj = str;
			this.handler.sendMessage( message );
		}
		System.out.println( "interrupted" );
		
	}
	
	
//	
}
