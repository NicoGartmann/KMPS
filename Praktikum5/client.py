#!/usr/bin/env python

# WS client example

import asyncio
import websockets
import json

async def connect():
    uri = "ws://localhost:3000"
    async with websockets.connect(uri) as websocket:
        name = input("Client gestartet.\nBitte Client-ID eingeben: ")

        await websocket.send(name)
        print(f"\nClient: {name}")

        tickets = await websocket.recv()
        if len(tickets)>0:
            for ticket in tickets:
                print(json.dumps(ticket))
        else: 
            print("Keine Tickets")


asyncio.get_event_loop().run_until_complete(connect())
asyncio.get_event_loop().run_forever()