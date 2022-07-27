package me.yoursole.shulkerplatform.blackhole.commands;

import me.yoursole.shulkerplatform.blackhole.Dir;
import me.yoursole.shulkerplatform.blackhole.Direction;
import me.yoursole.shulkerplatform.blackhole.VelocityRegion;
import me.yoursole.shulkerplatform.data.GameData;
import me.yoursole.shulkerplatform.events.BlockBreak;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Locale;

public class Region implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player))
            return true;
        processRegion((Player) sender, args);

        return true;
    }

    private static void processRegion(Player sender, String[] args){
        if(args.length == 0){
            sender.sendMessage("Please specify a region name");
            return;
        }

        if(args[0].equalsIgnoreCase("create")){
            if(args.length < 2){
                sender.sendMessage("Please specify a region name to create");
                return;
            }
            String name = args[1];
            VelocityRegion r = new VelocityRegion(null, null, 0 , null);

            GameData.velocityRegions.put(name.toLowerCase(Locale.ROOT), r);
            sender.sendMessage("Region created, please run /region "+name +" setCorners");

            return;
        }

        VelocityRegion r = GameData.velocityRegions.get(args[0].toLowerCase(Locale.ROOT));
        if(r == null){
            sender.sendMessage("Invalid region name");
            return;
        }

        if(args.length < 2){
            sender.sendMessage("Please specify an action for this region");
            return;
        }

        String action = args[1].toLowerCase(Locale.ROOT);
        switch (action){
            case "setcorners":{
                if(BlockBreak.regionEditor != null){
                    sender.sendMessage("Someone is already editing a region");
                    return;
                }
                BlockBreak.regionEditor = sender;
                BlockBreak.currentlyEditing = args[0].toLowerCase(Locale.ROOT);
                sender.sendMessage("You are now editing the region, please select corner 1");
                break;
            }

            case "direction":{
                if(args.length < 3){
                    sender.sendMessage("Please specify a direction for this region");
                    return;
                }

                String dir = args[2].toLowerCase(Locale.ROOT);
                switch (dir){
                    case "n":{
                        r.setDirection(new Direction(Dir.NORTH));
                        GameData.velocityRegions.put(args[0].toLowerCase(Locale.ROOT), r);
                        sender.sendMessage("Direction set!");
                        break;
                    }
                    case "s":{
                        r.setDirection(new Direction(Dir.SOUTH));
                        GameData.velocityRegions.put(args[0].toLowerCase(Locale.ROOT), r);
                        sender.sendMessage("Direction set!");
                        break;
                    }
                    case "e":{
                        r.setDirection(new Direction(Dir.EAST));
                        GameData.velocityRegions.put(args[0].toLowerCase(Locale.ROOT), r);
                        sender.sendMessage("Direction set!");
                        break;
                    }
                    case "w":{
                        r.setDirection(new Direction(Dir.WEST));
                        GameData.velocityRegions.put(args[0].toLowerCase(Locale.ROOT), r);
                        sender.sendMessage("Direction set!");
                        break;
                    }

                }
                break;
            }

            case "force":{
                if(args.length < 3){
                    sender.sendMessage("Please specify a force for this region");
                    return;
                }

                try{
                    float f = Float.parseFloat(args[2]);
                    r.setVel(f);
                    GameData.velocityRegions.put(args[0].toLowerCase(Locale.ROOT), r);
                    sender.sendMessage("Force set!");
                }catch (NumberFormatException e){
                    sender.sendMessage("Invalid force, try again");
                }
                break;
            }
        }

    }
}
