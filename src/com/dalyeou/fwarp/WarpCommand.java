package com.dalyeou.fwarp;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import com.dalyeou.fwarp.ConfigHolder.Configs;

public class WarpCommand implements CommandExecutor, TabCompleter, Listener {


	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List onTabComplete(CommandSender cs, Command c, String l, String[] a) {
		if(c.getName().equals("warp")) {
			if(a.length == 1) {
				return Arrays.asList("set", "del");
			} else if(a.length == 2) {
				if(a[0].equals("set")) {
					return Arrays.asList("Name");
				}
			}
		}
		return null;
	}

	@Override
	public boolean onCommand(CommandSender cs, Command c, String l, String[] a) {
		Player player = (Player) cs;
		if(! (cs instanceof Player)) {
			return false;
		}
		if(l.equalsIgnoreCase("warp")) {
			if(a.length == 1) {
				if(a[0].equalsIgnoreCase("list")) {
					player.sendMessage(FWarpMain.WarpPrefix + ChatColor.WHITE + "Warp List : " + ChatColor.GOLD + FWarpMain.Lists);

				}
				if(a[0].equalsIgnoreCase("Reload")) {
					Configs.CONFIG.reloadConfig();
					Configs.PORTAL.reloadConfig();
					Configs.WARP.reloadConfig();
					FWarpMain.Lists.clear();
					FWarpMain.getLanguage();
					FWarpMain.putWARPData();
					player.sendMessage(FWarpMain.WarpPrefix + "Reload Plugin!");

				}
			}
			if(a.length == 2) {
				if(a[0].equalsIgnoreCase("set")) {
					if(FWarpMain.Lists.contains(a[1])) {
						player.getWorld().playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 0.5f, 1f);

						outMessage(player, a[1], "setWarp");

					} else {
						setWarp(a[1], player, "Create");

					}
				}
				else if(a[0].equalsIgnoreCase("del")) {
					if(FWarpMain.Lists.contains(a[1])) {
						setWarp(a[1], player, "Delete");

					} else {
						player.getWorld().playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 0.5f, 1f);

						outMessage(player, a[1], "delWarp");

					}
				}
			}
		} else {
			player.sendMessage(FWarpMain.WarpPrefix + ChatColor.RED + "Command : /warp <set, remove, list>");

		}
		return true;
	}

	public void setWarp(String name, Player player, String type) {
		World world = player.getWorld();
		Location location = player.getLocation();

		if(type == "Create") {
			world.playSound(player.getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 0.5f, 2f);
			World worldName = Bukkit.getWorld(world.getName());

			FWarpMain.warpWorld.put(name, worldName);
			FWarpMain.warpX.put(name, location.getX());
			FWarpMain.warpY.put(name, location.getY());
			FWarpMain.warpZ.put(name, location.getZ());
			FWarpMain.warpYaw.put(name, location.getYaw());
			FWarpMain.warpPitch.put(name, location.getPitch());

			Configs.WARP.getConfig().set("Warps." + name + ".world", world.getName());
			Configs.WARP.getConfig().set("Warps." + name + ".x", location.getX());
			Configs.WARP.getConfig().set("Warps." + name + ".y", location.getY());
			Configs.WARP.getConfig().set("Warps." + name + ".z", location.getZ());
			Configs.WARP.getConfig().set("Warps." + name + ".yaw", location.getYaw());
			Configs.WARP.getConfig().set("Warps." + name + ".pitch", location.getPitch());
			Configs.WARP.saveConfig();
			
			FWarpMain.Lists.add(name);

			outMessage(player, name, "createWarp");

		}
		if(type == "Delete") {
			world.playSound(player.getLocation(), Sound.ENTITY_ARROW_SHOOT, 0.5f, 3f);

			FWarpMain.warpWorld.put(name, null);
			FWarpMain.warpX.put(name, null);
			FWarpMain.warpY.put(name, null);
			FWarpMain.warpZ.put(name, null);
			FWarpMain.warpYaw.put(name, null);
			FWarpMain.warpPitch.put(name, null);

			Configs.WARP.getConfig().set("Warps." + name, null);
			Configs.WARP.getConfig().set("Warps." + name + ".world", null);
			Configs.WARP.getConfig().set("Warps." + name + ".x", null);
			Configs.WARP.getConfig().set("Warps." + name + ".y", null);
			Configs.WARP.getConfig().set("Warps." + name + ".z", null);
			Configs.WARP.getConfig().set("Warps." + name + ".yaw", null);
			Configs.WARP.getConfig().set("Warps." + name + ".pitch", null);
			Configs.WARP.saveConfig();
			
			FWarpMain.Lists.remove(name);
			
			outMessage(player, name, "removeWarp");

		}
	}

	public void outMessage(Player player, String name, String type) {
		if(type == "setWarp") {
			if(FWarpMain.pluginLanguage.equalsIgnoreCase(FWarpMain.baseLanguage)) {
				player.sendMessage(FWarpMain.WarpPrefix + ChatColor.RED + "Oh, that name already exists, so we can't create it with that name! Please choose a different name!");
			} else {
				player.sendMessage(FWarpMain.WarpPrefix + ChatColor.RED + "그 이름이 이미 있어서 그 이름으로 만들 수 없어요! 다른 이름을 정해주세요!");
			}
		}
		if(type == "delWarp") {
			if(FWarpMain.pluginLanguage.equalsIgnoreCase(FWarpMain.baseLanguage)) {
				player.sendMessage(FWarpMain.WarpPrefix + ChatColor.RED + "Calm down! There's no warp named " + ChatColor.DARK_GREEN + name + "! " + ChatColor.RED + "Check again with the /warp list command!");
			} else {
				player.sendMessage(FWarpMain.WarpPrefix + ChatColor.RED + "잠시만요! " + ChatColor.DARK_GREEN + name + " " + ChatColor.RED + "라는 이름의 워프는 없어요! /warp list 명령어로 다시 확인해봐요!");
			}
		}
		if(type == "createWarp") {
			if(FWarpMain.pluginLanguage.equalsIgnoreCase(FWarpMain.baseLanguage)) {
				player.sendMessage(FWarpMain.WarpPrefix + ChatColor.GREEN + "Ta-da! I created a warp called this " + ChatColor.DARK_GREEN + name + "!");
			} else {
				player.sendMessage(FWarpMain.WarpPrefix + ChatColor.GREEN + "짜잔! 제가 " + ChatColor.DARK_GREEN + name + " " + ChatColor.GREEN + "라는 이름의 워프를 만들었어요!");
			}
		}
		if(type == "removeWarp") {
			if(FWarpMain.pluginLanguage.equalsIgnoreCase(FWarpMain.baseLanguage)) {
				player.sendMessage(FWarpMain.WarpPrefix + ChatColor.GREEN + "You don't need this warp anymore? Ok I see! Now the warp called " + ChatColor.DARK_GREEN + name + ChatColor.GREEN + " has been deleted!");
			} else {
				player.sendMessage(FWarpMain.WarpPrefix + ChatColor.GREEN + "더이상 이 워프가 필요 없으신가요? 알겠어요! 이제 " + ChatColor.DARK_GREEN + name + ChatColor.GREEN + " 라는 이름의 워프는 삭제됐어요!");
			}
		}

	}

}