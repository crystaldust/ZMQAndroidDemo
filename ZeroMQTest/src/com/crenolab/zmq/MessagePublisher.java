package com.crenolab.zmq;

import org.json.JSONObject;
import org.zeromq.ZMQ;
import org.zeromq.ZMQ.Context;
import org.zeromq.ZMQ.Socket;

import android.os.Handler;
import android.os.Message;

import com.google.gson.Gson;

public class MessagePublisher /*implements Runnable */
{
	private Context context;
	private Socket socket;

	private String url;
	private int port;

	private String fullUrl;

	private Gson gson;

	public MessagePublisher( String url, int port )
	{
		this.url = url;
		this.port = port;
		this.fullUrl = "tcp://" + this.url + ":" + this.port;
		
		this.context = ZMQ.context( 1 );
		this.socket = this.context.socket( ZMQ.PUB );
		
		this.socket.bind( this.fullUrl );
		this.gson = new Gson();
	}

	public void publish( JSONObject jsonObject )
	{
		String jsonString = this.gson.toJson( jsonObject );
		this.socket.send( jsonString.getBytes(), ZMQ.NOBLOCK );
	}

	public void publish( String jsonString )
	{
		System.out.println( "sending: " + jsonString );
		this.socket.send( jsonString.getBytes(), ZMQ.NOBLOCK );
	}

	public String getUrl()
	{
		return this.url;
	}

	public int getPort()
	{
		return this.port;
	}

	public void setUrl( String url )
	{
		this.url = url;
	}
	public void setPort( int port )
	{
		this.port = port;
	}

	public void close()
	{
		this.socket.close();
		this.context.term();
	}
	
	/*
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (!Thread.currentThread().isInterrupted()) {    
            
            Message message = new Message();   
            message.what = 100;
            message.obj = "Hello, world!";
              
//            MessagePublisher.this.myHandler.sendMessage(message);   
            try {   
                 Thread.sleep(100);    
            } catch (InterruptedException e) {   
                 Thread.currentThread().interrupt();   
            }
       }
	}*/
}
