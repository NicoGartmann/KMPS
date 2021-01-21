#!/usr/bin/env python

# WS client example

import asyncio
import websockets

async def connect():
    uri = "ws://localhost:3000"
    async with websockets.connect(uri) as websocket:
        name = input("Client gestartet.\nBitte Client-ID eingeben: ")

        await websocket.send(name)
        print(f"Client: {name}")

        tickets = await websocket.recv()
        print(f" {tickets}")


asyncio.get_event_loop().run_until_complete(connect())
asyncio.get_event_loop().run_forever()