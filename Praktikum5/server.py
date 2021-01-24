import asyncio
import websockets
import json

tickets = []

async def server(websocket, path):
    data = await websocket.recv()

    await websocket.send(json.dumps(tickets))

start_server = websockets.serve(server, "localhost", 3000)

asyncio.get_event_loop().run_until_complete(start_server)
asyncio.get_event_loop().run_forever()