# GiveMe
A client-side Fabric mod to give you any item you want (incl. Barriers, Command Blocks, etc.) with as much NBT data as you want (even over the 256 character limit!).

Requires you to be in Creative, but doesn't require OP or cheats to be enabled. Useful on Multiplayer servers with PlotSquared (or any other plot plugin) installed.

## Usage
```
/giveme <item> [count (default: 1)]
```

If the command goes over the 256 character limit, don't worry because the command will still work anyway! That means if you have a long NBT data string, you can still use it!
To attach NBT data to the item, use the following syntax:
```
/giveme <item>{NBT data} [count (default: 1)]
``` 

Example:
```
/giveme stick{display:{Name:'{"text":"e"}'}}
```

256 character limit example:
```
/giveme wayfinder_armor_trim_smithing_template{Enchantments:[{id:"minecraft:blast_protection",lvl:1s},{id:"minecraft:feather_falling",lvl:1s},{id:"minecraft:fire_protection",lvl:1s},{id:"minecraft:projectile_protection",lvl:1s},{id:"minecraft:protection",lvl:1s},{id:"minecraft:thorns",lvl:1s},{id:"minecraft:aqua_affinity",lvl:1s}]} 1
```
The example above is **335 characters long**, but will still run when put into the chat.