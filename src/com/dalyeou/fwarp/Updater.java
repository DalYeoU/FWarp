package com.dalyeou.fwarp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class Updater {

	private HttpURLConnection connection;
	@SuppressWarnings("unused")
	private String WRITE_STRING;

	@SuppressWarnings("unused")
	private String oldVersion = "0.0";
	private String newVersion = "0.0";

	public Updater(JavaPlugin plugin) {

		oldVersion = plugin.getDescription().getVersion();

		try {
			connection = (HttpURLConnection) new URL("https://raw.githubusercontent.com/DalYeoU/FWarp/master/version.txt").openConnection();
			connection.connect();
			newVersion = new BufferedReader(new InputStreamReader(connection.getInputStream())).readLine();
			if(newVersion.equals("1.0")) {
				Bukkit.getConsoleSender().sendMessage(FWarpMain.WarpPrefix + ChatColor.DARK_GREEN + "Thank you for using my Plugin!");
			} else {
				Bukkit.getConsoleSender().sendMessage(FWarpMain.WarpPrefix + ChatColor.DARK_GREEN + "A new version has been updated.");
			}
		} catch (IOException e) {
			return;
		}

	}

}