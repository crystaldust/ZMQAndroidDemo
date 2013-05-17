package com.crenolab.zmq;

import org.zeromq.ZMQ;
import org.zeromq.ZMQ.Context;
import org.zeromq.ZMQ.Socket;

public class MessageSubscriber 
{
	private Context context;
	private Socket socket;
	
	private String url;
	private int port;
	private String fullUrl;
	private String topic;
	
	private boolean closed = false;
	
	public MessageSubscriber( String url, int port, String topic )
	{
		this.url = url;
		this.port = port;
		this.topic = topic;
		this.fullUrl = "tcp://" + this.url + ":" + this.port;
		
		this.context = ZMQ.context( 1 );
		this.socket = this.context.socket( ZMQ.SUB );
		this.socket.connect( this.fullUrl );
		this.socket.subscribe( this.topic.getBytes() );
//		byte[] stringValue = socket.recv( 0 );
//		this.socket.subscribe( "".getBytes() );
		
/*		while( true ) {
			if( closed )
				break;
			byte[] stringValue = socket.recv( 0 );
			String str = new String( stringValue );
			System.out.println( "Received: " + str );
			
		}*/
		System.out.println( "Closed" );
	}
	
	public void startListening()
	{
		while( true ) {
			if( closed )
				break;
			byte[] stringValue = socket.recv( 0 );
			String str = new String( stringValue );
			System.out.println( "Received: " + str );
			
		}
		
	}
	
	public void stopListening() {
		
	}
	
	public void close()
	{
		this.socket.close();
		this.context.term();
	}
	
}
