var zmq = require( 'zmq' );
var sock = zmq.socket( 'pub' );
var config = require( './config' );


console.log( config );

sock.bind( 'tcp://' + config.url + ':' + config.port, function( err ) {
	if( err ) {
		console.log( err );
	}
        else {
	    console.log( 'Listening on port 2013' );
	    setInterval( function() {
		var message = 'hello, world ' + parseInt( Math.random() * 100 );
		sock.send( 'HELLO ' + message );
		// console.log( 'sending message' );
		console.log( message );
	    }, 1000 );
        }
} );
