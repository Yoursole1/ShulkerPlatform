package me.yoursole.shulkerplatform.data;

import me.yoursole.shulkerplatform.blackhole.MovingPlatform;
import me.yoursole.shulkerplatform.blackhole.VelocityRegion;

import java.util.ArrayList;
import java.util.HashMap;

public class GameData {

    //name -> region
    public static HashMap<String, VelocityRegion> velocityRegions = new HashMap<>();
    public static HashMap<String, MovingPlatform> movingPlatforms = new HashMap<>();

}
