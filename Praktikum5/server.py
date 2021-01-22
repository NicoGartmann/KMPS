#!/usr/bin/env python

import asyncio
import websockets
import json

tickets = []


## Ticket erstellen oder Server beenden 
async def server_command():
    command = input("\nn: new Ticket, q:Quit\n")
    if command == "n":
        ticket = { 
            "id":len(tickets),
            "name":"ticket"+str(len(tickets)),
            "assigned": "not assigned",
        }
        tickets.append(ticket)
        print(tickets)
    else:
        quit()

async def message():
    uri = 'ws://locahlhost:3000'
    async with websockets.connect(uri) as websocket:
        async for message in websocket:
            print(json.dumps(json.loads(message)))


def main() :
    print("Server started.\nNo Tickets.")
    asyncio.get_event_loop().run_until_complete(asyncio.wait([
        message(),
        server_command()
    ]))


if __name__ == "__main__":
    main()

