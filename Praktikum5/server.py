#!/usr/bin/env python

import asyncio
import websockets
import json

tickets = []

def server_command():
    command = input("n: neues Ticket, q:Quit\n")
    if command == "n":
        ticket = { 
            "id":len(tickets),
            "name":"ticket"+str(len(tickets)),
            "zugewiesen": "nicht zugewiesen",
        }
        tickets.append(ticket)
        print(tickets)
    else:
        print("Quit")

async def message(websocket, path):
    msg = await websocket.recv()
    print(f"< {msg}")

    ## greeting = f"{msg}"

    ## await websocket.send(greeting)

    if msg.startswith("client"):
        print(f"Neuer Client: {msg}")
    else:
        print("zuweisung")

start_server = websockets.serve(message, "localhost", 3000)
print("Server gestartet.\nKeine Tickets.")

asyncio.get_event_loop().run_until_complete(start_server)
asyncio.get_event_loop().run_forever()