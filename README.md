# GiveMe
A client-side Fabric mod to give you any item you want (incl. Barriers, Command Blocks, etc.) with as much NBT data as you want (within the 256 character limit - you can install another mod to bypass that).

Requires you to be in Creative, but doesn't require OP or cheats to be enabled. Useful on Multiplayer servers with PlotSquared (or any other plot plugin) installed.

## Usage
```
/giveme <item> [count (default: 1)]
```

To attach NBT data to the item, use the following syntax:
```
/giveme <item>{NBT data} [count (default: 1)]
``` 
(for example: `/giveme minecraft:stick{display:{Name:"custom name"}}`)