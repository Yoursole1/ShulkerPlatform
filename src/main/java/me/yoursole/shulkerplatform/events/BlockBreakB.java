package me.yoursole.shulkerplatform.events;

import me.yoursole.shulkerplatform.blackhole.MovingPlatform;
import me.yoursole.shulkerplatform.blackhole.VelocityRegion;
import me.yoursole.shulkerplatform.data.GameData;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BlockBreakB implements Listener {

    public static Player regionEditor = null;
    public static String currentlyEditing = null;
    public static Location p1 = null;
    public static Location p2 = null;

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e){

        if(regionEditor == null)
            return;
        if(!e.getPlayer().equals(regionEditor))
            return;

        if(p1 == null){
            p1 = e.getBlock().getLocation();
            e.getPlayer().sendMessage(ChatColor.GOLD + "Point one set");
            e.setCancelled(true);
            return;
        }
        if(p2 == null){
            p2 = e.getBlock().getLocation();
            e.getPlayer().sendMessage(ChatColor.GOLD + "Point two set - please run /platform <name> direction (x,y,z)");
            MovingPlatform r = GameData.movingPlatforms.get(currentlyEditing);
            r.setLocations(p1, p2);
            GameData.movingPlatforms.put(currentlyEditing, r);
            regionEditor = null;
            currentlyEditing = null;
            p1 = null;
            p2 = null;
            e.setCancelled(true);
        }


    }
}
