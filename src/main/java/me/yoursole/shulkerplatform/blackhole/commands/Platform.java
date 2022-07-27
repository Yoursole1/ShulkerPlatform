package me.yoursole.shulkerplatform.blackhole.commands;

import me.yoursole.shulkerplatform.blackhole.Dir;
import me.yoursole.shulkerplatform.blackhole.Direction;
import me.yoursole.shulkerplatform.blackhole.MovingPlatform;
import me.yoursole.shulkerplatform.blackhole.VelocityRegion;
import me.yoursole.shulkerplatform.data.GameData;
import me.yoursole.shulkerplatform.events.BlockBreak;
import me.yoursole.shulkerplatform.events.BlockBreakB;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.Locale;

public class Platform implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player))
            return true;
        processPlatform((Player) sender, args);

        return true;
    }

    private void processPlatform(Player sender, String[] args){
        if(args.length == 0){
            sender.sendMessage("Please specify a platform name");
            return;
        }

        if(args[0].equalsIgnoreCase("create")){
            if(args.length < 2){
                sender.sendMessage("Please specify a region name to create");
                return;
            }
            String name = args[1];
            MovingPlatform r = new MovingPlatform();

            GameData.movingPlatforms.put(name.toLowerCase(Locale.ROOT), r);
            sender.sendMessage("Platform created, please run /platform "+name +" setCorners");

            return;
        }

        MovingPlatform r = GameData.movingPlatforms.get(args[0].toLowerCase(Locale.ROOT));
        if(r == null){
            sender.sendMessage("Invalid platform name");
            return;
        }

        if(args.length < 2){
            sender.sendMessage("Please specify an action for this platform");
            return;
        }

        String action = args[1].toLowerCase(Locale.ROOT);

        switch (action){
            case "start":{
                r.start();
                break;
            }
            case "stop":{
                r.stop();
                break;
            }


            case "setcorners":{
                if(BlockBreak.regionEditor != null){
                    sender.sendMessage("Someone is already editing a region");
                    return;
                }
                BlockBreakB.regionEditor = sender;
                BlockBreakB.currentlyEditing = args[0].toLowerCase(Locale.ROOT);
                sender.sendMessage("You are now editing the region, please select corner 1");
                break;
            }

            case "direction":{
                if(args.length < 3){
                    sender.sendMessage("Please specify a direction for this platform");
                    return;
                }

                try{
                    String dir = args[2];
                    dir = dir.replace("(","").replace(")","");
                    String[] directions = dir.split(",");

                    if(directions.length != 3){
                        throw new NumberFormatException();
                    }

                    float x = Float.parseFloat(directions[0]);
                    float y = Float.parseFloat(directions[1]);
                    float z = Float.parseFloat(directions[2]);

                    Vector v = new Vector(x, y, z);
                    v.normalize();
                    r.setDirection(v);

                    GameData.movingPlatforms.put(args[0].toLowerCase(Locale.ROOT), r);
                    sender.sendMessage("Direction set!");
                }catch (NumberFormatException e){
                    sender.sendMessage("Invalid direction, try again");
                }
                break;
            }

            case "speed":{
                if(args.length < 3){
                    sender.sendMessage("Please specify a speed for this platform");
                    return;
                }

                try{
                    float f = Float.parseFloat(args[2]);
                    r.setVel(f);
                    GameData.movingPlatforms.put(args[0].toLowerCase(Locale.ROOT), r);
                    sender.sendMessage("Speed set!");
                }catch (NumberFormatException e){
                    sender.sendMessage("Invalid speed, try again");
                }
                break;
            }
        }




    }
}
