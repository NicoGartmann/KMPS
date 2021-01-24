import asyncio 
import websockets
import json

async def client(): 
    uri = "ws://localhost:3000"
    async with websockets.connect(uri) as websocket:
        print("Client gestartet.")
        username = input("Bitte Client-ID eingeben: ")
        data = json.dumps({"username":username})

        await websocket.send(data)

        tickets = await websocket.recv()
        if len(tickets) > 0:
            print(tickets)
        else:
            print("Keine Tickets")
    
    asyncio.get_event_loop().run_until_complete(client())