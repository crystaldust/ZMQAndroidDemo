package com.crenolab;
//import org.zeromq.ZMQ;
//
//import com.crenolab.zmq.MessagePublisher;
//import com.crenolab.zmq.MessageSubscriber;


import com.crenolab.zmq.MessagePublisher;
import com.crenolab.zmq.MessageSubscriber;
import com.crenolab.zmq.ZMQSubService;


import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
//import android.view.View;
//import android.widget.Button;
//import android.widget.TextView;
import android.widget.TextView;

@SuppressLint("HandlerLeak")
public class MainActivity extends Activity {
//	private Button createPublisherButton;
//	private Button createSubscriberButton;
	
	private Button startSubServiceButton;
	private Button stopSubServiceButton;
	
	private EditText urlInput;
	private EditText portInput;
	
	private TextView resultTextView;
//	
	
	private Intent subServiceIntent;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        this.startSubServiceButton = (Button)findViewById( R.id.startSubService );
        this.startSubServiceButton.setOnClickListener( onStartSubServiceButtonClick );
        
        this.stopSubServiceButton = (Button)findViewById( R.id.stopSubService );
        this.stopSubServiceButton.setOnClickListener( onStopSubServiceButtonClick );
        
        
        this.resultTextView = (TextView)findViewById( R.id.resultTextView );
        this.urlInput = (EditText)findViewById( R.id.urlInput );
        this.portInput = (EditText)findViewById( R.id.portInput );
    }
    
	
    
    Button.OnClickListener onStartListeningButtonClick = new Button.OnClickListener() {
    	@Override
    	public void onClick(View v) {
//    		subscriber.startListening();
    		/*new Thread( new SubThread( handler ) ).start();*/
    	}
    };

    
    Button.OnClickListener onStartSubServiceButtonClick = new Button.OnClickListener() {
    	@Override
    	public void onClick(View v) {
//    		subscriber.startListening();
    		subServiceIntent = new Intent( MainActivity.this, ZMQSubService.class );
    		
    		subServiceIntent.putExtra( "url", urlInput.getText().toString() );
    		subServiceIntent.putExtra( "port", Integer.parseInt( portInput.getText().toString() ) );
    		subServiceIntent.putExtra( "topic", "HELLO" );

    		startService( subServiceIntent );
    	}
    };
    
    
    Button.OnClickListener onStopSubServiceButtonClick = new Button.OnClickListener() {
    	@Override
    	public void onClick(View v) {
    		stopService( subServiceIntent );
    	}
    };
    
    
    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
    	@Override
    	public void onReceive(Context context, Intent intent) {
    		// TODO Auto-generated method stub
    		Bundle bundle = intent.getExtras();
    		String message = bundle.getString( "message" );
    		System.out.println( "Message received in main activity: " + message );
    		resultTextView.setText( message );
    	}
    };
    
    protected void onResume() {
    	super.onResume();
    	
    	IntentFilter intentFilter = new IntentFilter();
    	
    	intentFilter.addAction( "zmqbroadcast" );
    	
    	this.registerReceiver( broadcastReceiver, intentFilter );
    };
    
    public void onPause() {
        super.onPause();

        this.unregisterReceiver( this.broadcastReceiver );
    };
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
