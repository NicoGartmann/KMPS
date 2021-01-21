// init
    // schema – can be ws or wss
    // host, port – ws server
    conn, _, _, err := ws.DefaultDialer.Dial(ctx, {schema}://{host}:{port})
    if err != nil {
        // handle error
	}
	
    // send message
    err = wsutil.WriteClientMessage(conn, ws.OpText, {message})
    if err != nil {
        // handle error
    }
    
    // receive message    
    msg, _, err := wsutil.ReadServerData(conn)
    if err != nil {
        // handle error
    }
