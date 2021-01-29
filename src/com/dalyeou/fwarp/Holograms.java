package com.dalyeou.fwarp;

import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.plugin.Plugin;

public class Holograms {
	
	public static Plugin plugin = FWarpMain.getPlugin(FWarpMain.class);
	
	static ArmorStand holograms;
	private String text;
	private Location loc;
	
	public ArmorStand getHolograms() {
		return holograms;
	}
	
	public String getText() {
		return text;
	}
	
	public Location getLocation() {
		return loc;
	}
	
	public Holograms(Location loc, String text) {
		this.loc = loc;
		this.text = text;
	}
	
	public void create() {
		holograms = loc.subtract(0, 0, 0).getWorld().spawn(loc, ArmorStand.class);
		holograms.setCustomName(ChatColor.translateAlternateColorCodes('&', text));
		holograms.setCustomNameVisible(true);
		holograms.setGravity(false);
		holograms.setVisible(false);
		holograms.setSmall(true);
		holograms.setRemoveWhenFarAway(false);
		UUID hid = holograms.getUniqueId();
		
		saveHolograms(text, loc, hid);
	}
	
	public void teleport(Location loc) {
		holograms.teleport(loc);
	}
	
	public void changetext(String text) {
		holograms.setCustomName(text);
	}
	
	public static void saveHolograms(String name, Location loc, UUID uuid) {
		ConfigHolder.Configs.PORTAL.getConfig().set(uuid + ".Name ", name);
		ConfigHolder.Configs.PORTAL.getConfig().set(uuid + ".Loc ", loc);
		ConfigHolder.Configs.PORTAL.saveConfig();
	}

	public static void removeHolograms(UUID uuid) {
		ConfigHolder.Configs.PORTAL.getConfig().set(uuid + ".Name ", null);
		ConfigHolder.Configs.PORTAL.getConfig().set(uuid + ".Loc ", null);
		ConfigHolder.Configs.PORTAL.saveConfig();
	}
	
}
