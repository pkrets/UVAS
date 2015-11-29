package com.setec2015.appsetec;

/**
 * Created by Krets on 27/11/2015.
 */
public class settingsDataHolder {
    private static int minTemp;
    private static int maxTemp;


    public settingsDataHolder(int minTemp, int maxTemp) {
        super();
        this.minTemp = minTemp;
        this.maxTemp = maxTemp;
    }

    public static int getMinTemp() { return minTemp; }
    public static int getMaxTemp() { return maxTemp; }
}
