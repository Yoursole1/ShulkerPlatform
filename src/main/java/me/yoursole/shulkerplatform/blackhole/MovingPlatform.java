package me.yoursole.shulkerplatform.blackhole;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Shulker;

import org.bukkit.util.Vector;


import java.util.*;

public class MovingPlatform {
    private Location corner1;
    private Location corner2;
    private float vel;
    private Vector d;

    private boolean isRunning = false;


    HashMap<ArmorStand, Shulker> standShulker = new HashMap<>();

    public MovingPlatform(){
        this.corner1 = null;
        this.corner2 = null;
        this.vel = 0;
        this.d = null;
    }

    public void setLocations(Location a, Location b){
        this.corner1 = a;
        this.corner2 = b;
    }

    public void setVel(float vel){
        this.vel = vel;
    }

    public void setDirection(Vector d){
        this.d = d;
    }


    public boolean isCreated(){
        return (this.d != null) && (this.vel != 0) && (this.corner2 !=null) && (this.corner1 !=null);
    }

    public boolean isRunning(){
        return this.isRunning;
    }

    public void start(){
        if(this.isRunning) return;

        for (Iterator<Block> it = this.iterator(); it.hasNext();) {
            Block b = it.next();

            b.setType(Material.AIR);
            Shulker s = (Shulker) Objects.requireNonNull(this.corner1.getWorld()).spawnEntity(b.getLocation(), EntityType.SHULKER);
            s.setAI(false);
            s.setSilent(true);
            s.setGravity(false);
            s.setInvulnerable(true);

            ArmorStand stand = (ArmorStand) this.corner1.getWorld().spawnEntity(b.getLocation().add(new Vector(0,-1.5,0)), EntityType.ARMOR_STAND);
            stand.addPassenger(s);
            stand.setInvisible(true);
            stand.setGravity(false);

            this.standShulker.put(stand, s);
        }

        this.isRunning = true;
    }

    public void update(){
        if(!this.isRunning) return;

        if(isColliding()){
            this.d = this.d.multiply(-1);
        }

        for(Map.Entry<ArmorStand, Shulker> entry : this.standShulker.entrySet()){
            ArmorStand s = entry.getKey();
            Shulker shulker = entry.getValue();

            s.eject();
            Location current = s.getLocation();
            current = current.add(this.d.clone().multiply(this.vel));
            s.teleport(current);
            s.addPassenger(shulker);
        }
    }

    private boolean isColliding(){
        for(Shulker s : this.standShulker.values()){
            Location l = s.getLocation();
            if(!l.getBlock().getType().equals(Material.AIR)){
                return true;
            }
        }
        return false;
    }

    public void stop(){
        if(!this.isRunning) return;

        for(Shulker s : this.standShulker.values()){
            s.setHealth(0);
        }
        for(ArmorStand stand : this.standShulker.keySet()){
            stand.setHealth(0);
        }
        for (Iterator<Block> it = this.iterator(); it.hasNext(); ) {
            Block b = it.next();

            b.setType(Material.SHULKER_BOX);
        }
        this.standShulker = new HashMap<>();

        this.isRunning = false;
    }


    public Location getMinimumPoint() {
        return new Location(this.corner1.getWorld(), Math.min(this.corner1.getX(), this.corner2.getX()), Math.min(this.corner1.getY(), this.corner2.getY()), Math.min(this.corner1.getZ(), this.corner2.getZ()));
    }

    public Location getMaximumPoint() {
        return new Location(this.corner1.getWorld(), Math.max(this.corner1.getX(), this.corner2.getX()) + 0.99999D, Math.max(this.corner1.getY(), this.corner2.getY()) + 0.99999D, Math.max(this.corner1.getZ(), this.corner2.getZ()) + 0.99999D);
    }

    public Iterator<Block> iterator() { //thanks surrendrx for your code
        Set<Block> blocks = new HashSet();
        Location min = this.getMinimumPoint();
        Location max = this.getMaximumPoint();

        for(int x = min.getBlockX(); x <= max.getBlockX(); ++x) {
            for(int y = min.getBlockY(); y <= max.getBlockY(); ++y) {
                for(int z = min.getBlockZ(); z <= max.getBlockZ(); ++z) {
                    blocks.add(Objects.requireNonNull(this.corner1.getWorld()).getBlockAt(new Location(this.corner1.getWorld(), x, y, z)));
                }
            }
        }

        return blocks.iterator();
    }


}
