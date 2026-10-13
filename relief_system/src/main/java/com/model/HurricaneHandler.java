package com.model;

import java.util.ArrayList;
import java.util.UUID;

public class HurricaneHandler {
    private static HurricaneHandler hurricaneHandler;
    private ArrayList<Hurricane> hurricanes;

    private HurricaneHandler(){
        hurricanes = DataLoader.getHurricanes();
    }
    
    public static HurricaneHandler getInstance(){
        if(hurricaneHandler == null)
            hurricaneHandler = new HurricaneHandler();
        return hurricaneHandler;
    }

    public Hurricane getHurricane(String name){
        for(Hurricane hurricane : hurricanes){
            if(name.equalsIgnoreCase(hurricane.getName()))
                return hurricane;
        }
        return null;
    }

    public void addHurricane(UUID id, String name, int category, int windSpeed, ArrayList<Integer> affectedAreas,
        String conditions, double size, boolean active){
            hurricanes.add(new Hurricane(id, name, category, windSpeed, affectedAreas, conditions, size, active));
        }

}
