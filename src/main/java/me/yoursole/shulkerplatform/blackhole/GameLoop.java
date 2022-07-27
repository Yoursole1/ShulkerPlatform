package me.yoursole.shulkerplatform.blackhole;

import me.yoursole.shulkerplatform.data.GameData;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Objects;

public class GameLoop {
    public static int taskID;

    public GameLoop(){
        loop();
    }

    private void loop(){
        taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(Objects.requireNonNull(Bukkit.getPluginManager().getPlugin("ShulkerPlatform")), new Runnable() {
            @Override
            public void run() {
                for(VelocityRegion r : GameData.velocityRegions.values()){
                    if(!r.isCreated()) continue;
                    for(Player p : Bukkit.getOnlinePlayers()){
                        if(!p.isOnGround()){
                            //r.addJumpVelocity(p);
                            continue;
                        }
                        r.addVelocity(p);
                    }
                }


                for(MovingPlatform m : GameData.movingPlatforms.values()){
                    if(!m.isCreated()) continue;
                    if(!m.isRunning()) continue;

                    m.update();
                }
            }


        }, 0L, 1L);
    }
}
