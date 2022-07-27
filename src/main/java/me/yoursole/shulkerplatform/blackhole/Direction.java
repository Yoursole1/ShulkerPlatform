package me.yoursole.shulkerplatform.blackhole;

import org.bukkit.util.Vector;

public class Direction {
    private Dir d;

    public Direction(Dir d){
        this.d = d;
    }

    public Vector getDirection(){
        switch (this.d){
            case EAST: return new Vector(1,0,0);
            case WEST: return new Vector(-1, 0, 0);
            case NORTH: return new Vector(0, 0, -1);
            case SOUTH: return new Vector(0, 0, 1);
        }
        return null; //should be unreachable
    }
}

