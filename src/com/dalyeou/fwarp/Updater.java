package com.dalyeou.fwarp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
 
import org.bukkit.plugin.java.JavaPlugin;
 
public class Updater {

    private HttpURLConnection connection;
    private String WRITE_STRING;

    private String oldVersion = "0.0";
    private String newVersion = "0.0";

    public Updater(JavaPlugin plugin) {

        oldVersion = plugin.getDescription().getVersion();

        try {
            connection = (HttpURLConnection) new URL("https://raw.githubusercontent.com/DalYeoU/FWarp/master/version.txt?token=ASTS25FJZB7XS5AEXBERRALACM6AG").openConnection();
            connection.connect();
            newVersion = new BufferedReader(new InputStreamReader(connection.getInputStream())).readLine();
            System.out.println(newVersion);
        } catch (IOException e) {
            return;
        }

    }
}