package me.yoursole.shulkerplatform.blackhole;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class VelocityRegion {

    private Location corner1;
    private Location corner2;
    private float vel;
    private Direction d;

    public VelocityRegion(Location corner1, Location corner2, float vel, Direction d){
        this.corner1 = corner1;
        this.corner2 = corner2;
        this.vel = vel;
        this.d = d;
    }

    public boolean isInside(Location other){
        return between(this.corner1.getBlockX(), this.corner2.getBlockX(), other.getBlockX()) &&
                between(this.corner1.getBlockY(), this.corner2.getBlockY(), other.getBlockY()) &&
                between(this.corner1.getBlockZ(), this.corner2.getBlockZ(), other.getBlockZ()) &&
                Objects.equals(other.getWorld(), this.corner1.getWorld());
    }

    public void setLocations(Location a, Location b){
        this.corner1 = a;
        this.corner2 = b;
    }

    public void setVel(float vel){
        this.vel = vel;
    }

    public void setDirection(Direction d){
        this.d = d;
    }


    public void addVelocity(Player p){
        if(!isInside(p.getLocation())) return;
        p.setVelocity(p.getVelocity().add(this.d.getDirection().multiply(this.vel)));
        p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 3, 245));
    }

    public void addJumpVelocity(Player p){
        if(!isInside(p.getLocation())) return;
        p.setVelocity(p.getVelocity().add(this.d.getDirection().multiply(this.vel/5f)));
    }

    public void addVelocity(Collection<Player> players){
        for(Player p : players){
            if(!isInside(p.getLocation())) continue;
            p.setVelocity(p.getVelocity().add(this.d.getDirection().multiply(this.vel)));
        }
    }

    public boolean isCreated(){
        return (this.d != null) && (this.vel != 0) && (this.corner2 !=null) && (this.corner1 !=null);
    }


    private boolean between(int a, int b, int toCheck){
        if(a > b){
            return toCheck < a && toCheck > b;
        }else{
            return toCheck > a && toCheck < b;
        }
    }


}
