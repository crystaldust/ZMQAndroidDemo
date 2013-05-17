var zmq = require( 'zmq' );

var sock = zmq.socket( 'sub' );

sock.connect( 'tcp://127.0.0.1:2013' );
sock.subscribe( 'HELLO' );

sock.on( 'message', function( data ) {
	console.log( data.toString() );
} );
