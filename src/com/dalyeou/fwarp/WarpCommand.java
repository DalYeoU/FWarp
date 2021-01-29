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
		if(c.getName().equals("portal")) {
			if(a.length == 1) {
				return Arrays.asList("create", "remove");
			} else if(a.length == 2) {
				if(a[0].equals("create")) {
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
		if(l.equalsIgnoreCase("Goto")) { 		// 워프 관련 명령어
			if(a.length == 1) {
				if(FWarpMain.Lists.contains(a[0])) {
					player.getWorld().playSound(player.getLocation(), Sound.ENTITY_CHICKEN_EGG, 0.5f, 1f);
					WarpMessages.outMessage(player, a[0], "moveWarp");
					WarpPortal.moveToWarp(player, a[0]);

				} else {
					player.getWorld().playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 0.5f, 1f);
					WarpMessages.outMessage(player, a[0], "noWarp");
					
				}
			} else {
				player.getWorld().playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 0.5f, 1f);
				WarpMessages.outMessage(player, null, "emptyWarp");

			}

			if(l.equalsIgnoreCase("warp")) { 		// 워프 관련 명령어
				if(a.length >= 1) {
					if(a[0].equalsIgnoreCase("list")) {
						player.sendMessage(FWarpMain.WarpPrefix + ChatColor.WHITE + "Warp List : " + ChatColor.GOLD + FWarpMain.Lists);

					}
					else if(a[0].equalsIgnoreCase("Reload")) {
						Configs.CONFIG.reloadConfig();
						Configs.PORTAL.reloadConfig();
						Configs.WARP.reloadConfig();
						FWarpMain.Lists.clear();
						FWarpMain.getLanguage();
						FWarpMain.putWARPData();
						player.sendMessage(FWarpMain.WarpPrefix + "Reload Plugin!");

					}
					else if(a[0].equalsIgnoreCase("set")) {
						if(a.length != 1) {
							if(FWarpMain.Lists.contains(a[1])) {
								player.getWorld().playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 0.5f, 1f);
								WarpMessages.outMessage(player, a[1], "alreadyWarp");

							} else {
								setWarp(a[1], player, "Create");
							}
						} else {
							player.getWorld().playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 0.5f, 1f);
							WarpMessages.outMessage(player, null, "emptyWarp");
						}
					}
					else if(a[0].equalsIgnoreCase("del")) {
						if(a.length != 1) {
							if(FWarpMain.Lists.contains(a[1])) {
								setWarp(a[1], player, "Delete");

							} else {
								player.getWorld().playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 0.5f, 1f);
								WarpMessages.outMessage(player, a[1], "noWarp");

							}
						} else {
							player.getWorld().playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 0.5f, 1f);
							WarpMessages.outMessage(player, null, "emptyWarp");
						}
					} else {
						player.sendMessage(FWarpMain.WarpPrefix + ChatColor.RED + "Command : " + ChatColor.GOLD + "/warp <set, del, list, reload>");

					}
				} 
			}

			if(l.equalsIgnoreCase("portal")) { 		//포탈 관련 명령어
				if(a.length >= 2) {
					if(a[0].equalsIgnoreCase("create")) {

						if(FWarpMain.warpWorld.get(a[1]) == null) {

							player.getWorld().playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 0.5f, 1f);
							WarpMessages.outMessage(player, a[1], "noWarp");
							return true;

						} else {
							String text = "";
							for(int i = 2; i < a.length; i++) {
								text = text + " " + a[i];

							}
							text = text.replaceFirst(" ", "");
							WarpPortal.createPortal(player, text, a[1]);
							WarpMessages.outMessage(player, a[1], "linkPortal");

						}
					}
				}else if(a.length == 1) {
					if(a[0].equalsIgnoreCase("remove") && WarpPortal.remove == 0) {

						WarpPortal.remove = 1;

						WarpMessages.outMessage(player, null, "delPortal");

					} else if(a[0].equalsIgnoreCase("remove") && WarpPortal.remove == 1) {

						WarpPortal.remove = 0;

						WarpMessages.outMessage(player, null, "canDelPortal");

					}
				}else {
					player.getWorld().playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 0.5f, 1f);
					player.sendMessage(FWarpMain.WarpPrefix + ChatColor.RED + "Command : /portal <create, remove>");
				}
			}

			if(l.equalsIgnoreCase("Scroll")) { 		// 스크롤 관련 명령어
				if(a.length >= 2) {
					if(FWarpMain.Lists.contains(a[1])) {
						String text = "";
						for(int i = 2; i < a.length; i++) {
							text = text + "" + a[i];
							String loreText = a[i];
							loreText = loreText.replaceAll("_", " ");
							WarpScroll.loreList.add(loreText);
						}
						text = text.replaceFirst(" ", "");

						WarpScroll.customItem(player, a[0], a[1]);
						WarpScroll.loreList.clear();
						WarpMessages.outMessage(player, a[1], "createScroll");

					} else {
						WarpMessages.outMessage(player, null, "wrongScroll");
					}
				}
			}
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

			WarpMessages.outMessage(player, name, "createWarp");

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

			WarpMessages.outMessage(player, name, "removeWarp");

		}
	}

}