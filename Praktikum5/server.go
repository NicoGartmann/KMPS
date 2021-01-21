  // init
  listener, err := net.Listen("tcp", op.Port)
  if err != nil {
	  // handle error
  }
  conn, err := listener.Accept()
  if err != nil {
	  // handle error
  }
  upgrader := ws.Upgrader{}
  if _, err = upgrader.Upgrade(conn); err != nil {
	  // handle error
  }
  // receive message
  for { 
	   reader := wsutil.NewReader(conn, ws.StateServerSide)
	   _, err := reader.NextFrame()
	   if err != nil {
		   // handle error
	   }
	   data, err := ioutil.ReadAll(reader)
	   if err != nil {
		   // handle error
	   }
  }   
  // send message
  msg := "new server message"
  if err := wsutil.WriteServerText(conn, {message}); err != nil {
	  // handle error
  }
