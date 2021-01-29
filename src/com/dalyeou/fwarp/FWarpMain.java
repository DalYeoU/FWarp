package com.dalyeou.fwarp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;

import com.dalyeou.fwarp.ConfigHolder.Configs;

public class FWarpMain extends JavaPlugin {

	public static String WarpPrefix = ChatColor.DARK_GRAY+"["+ChatColor.BLUE+"Warp Portal"+ChatColor.DARK_GRAY+"] ";

	public static HashMap<String, String> Language = new HashMap<>();

	public static HashMap<UUID, String> portalNames = new HashMap<>();

	public static HashMap<String, World> warpWorld = new HashMap<>();
	public static HashMap<String, Double> warpX = new HashMap<>();
	public static HashMap<String, Double> warpY = new HashMap<>();
	public static HashMap<String, Double> warpZ = new HashMap<>();
	public static HashMap<String, Float> warpYaw = new HashMap<>();
	public static HashMap<String, Float> warpPitch = new HashMap<>();

	public static List<String> Lists = new ArrayList<String>();
	
	public static String pluginLanguage = "";
	public static String baseLanguage = "en";
	/*
	 * Goto 명령어로 해당 워프로 바로 이동하는 명령어 만들기 
	 */
	
	
	@SuppressWarnings("unused")
	private void checkUpdate() {
		   final Updater updater = new Updater(this);
		}

	public void onEnable() {

		// 커맨드 관련 로드
		getCommand("Goto").setExecutor( new WarpCommand());
		
		getCommand("portal").setExecutor( new WarpCommand());
		getCommand("portal").setTabCompleter( new WarpCommand());

		getCommand("warp").setExecutor( new WarpCommand());
		getCommand("warp").setTabCompleter( new WarpCommand());
		
		getCommand("Scroll").setExecutor( new WarpCommand());

		// 클래스 로드
		Bukkit.getPluginManager().registerEvents(new WarpPortal(), this);
		Bukkit.getPluginManager().registerEvents(new WarpCommand(), this);
		Bukkit.getPluginManager().registerEvents(new WarpScroll(), this);

		new ConfigHolder(this);
		putWARPData();
		getLanguage();
		checkUpdate();

		Bukkit.getConsoleSender().sendMessage(WarpPrefix + ChatColor.GREEN + "Plug-in activation");

	}

	public static void getLanguage() {
		String getLanguage = Configs.CONFIG.getConfig().getString("Language");
		Language.put("Language", pluginLanguage);
		pluginLanguage = getLanguage;
	}

	public static void putWARPData() {
		for(String PortalUUID : Configs.PORTAL.getConfig().getConfigurationSection("Portal.").getKeys(false)){
			UUID uuid = UUID.fromString(PortalUUID);
			String PoratalNames = Configs.PORTAL.getConfig().getString("Portal." + PortalUUID + ".Name");
			portalNames.put(uuid, PoratalNames);
		}
		for(String WarpNames : Configs.WARP.getConfig().getConfigurationSection("Warps.").getKeys(false)){
			World WarpsWorlds = Bukkit.getWorld(Configs.WARP.getConfig().getString("Warps." + WarpNames + ".world"));
			Double WarpsXs = Configs.WARP.getConfig().getDouble("Warps." + WarpNames + ".x");
			Double WarpsYs = Configs.WARP.getConfig().getDouble("Warps." + WarpNames + ".y");
			Double WarpsZs = Configs.WARP.getConfig().getDouble("Warps." + WarpNames + ".z");
			Float WarpsYaws = (float) Configs.WARP.getConfig().getDouble("Warps." + WarpNames + ".yaw");
			Float WarpsPitchs = (float) Configs.WARP.getConfig().getDouble("Warps." + WarpNames + ".pitch");

			warpWorld.put(WarpNames, WarpsWorlds);
			warpX.put(WarpNames, WarpsXs);
			warpY.put(WarpNames, WarpsYs);
			warpZ.put(WarpNames, WarpsZs);
			warpYaw.put(WarpNames, WarpsYaws);
			warpPitch.put(WarpNames, WarpsPitchs);
			Lists.add(WarpNames);
		}

	}

	public void onDisable() {

		Bukkit.getConsoleSender().sendMessage(WarpPrefix + ChatColor.GREEN + "워프 플러그인 비활성화");

	}
}