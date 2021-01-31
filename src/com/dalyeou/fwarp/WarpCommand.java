package com.dalyeou.fwarp;

import java.util.Arrays;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
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
		if(c.getName().equals("Goto")) {
			if(a.length == 1) {
				return FWarpMain.Lists;
			}
		}
		if(c.getName().equals("Warp")) {
			if(a.length == 1) {
				return Arrays.asList("Set", "Del", "List", "Reload");
			} else if(a.length == 2) {
				if(a[0].equals("Del")) {
					return FWarpMain.Lists;
				}
				if(a[0].equals("Sel")) {
					return Arrays.asList("WarpName");
				}
			}
		}
		if(c.getName().equals("Portal")) {
			if(a.length == 1) {
				return Arrays.asList("Create", "Remove");
			} else if(a.length == 2) {
				if(a[0].equals("Create")) {
					return FWarpMain.Lists;
				}
			} else if(a.length == 3) {
				return Arrays.asList("PortalName");
			}
		}
		if(c.getName().equals("SetPer")) {
			if(a.length == 1) {
			} else if(a.length == 2) {
				return Arrays.asList("warp.portal", "warp.portal.use", "warp.warp", "warp.scroll", "warp.scroll.use", "warp.goto", "warp.give");
			} else if(a.length == 3) {
				return Arrays.asList("enable", "disable");
			}
		}
		if(c.getName().equals("Scroll")) {
			if(a.length == 1) {
				return FWarpMain.Lists;
			} else if(a.length == 2) {
				return Arrays.asList("ItemName");
			} else if(a.length >= 3) {
				return Arrays.asList("ItemLore");
			}
		}
		if(c.getName().equals("Language")) {
			if(a.length == 1) {
				return Arrays.asList("Korean", "English");
			}
		}
		return null;
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender cs, Command c, String l, String[] a) {
		Player player = (Player) cs;
		if(! (cs instanceof Player)) {
			return false;
		}
		if(l.equalsIgnoreCase("Language")) {
			if(player.hasPermission(new WarpPermission().Language) == true) {
				if(a.length == 1) {
					if(a[0].equalsIgnoreCase("Korean")) {
						Configs.CONFIG.getConfig().set("Language", "kr");
						Configs.CONFIG.saveConfig();
						FWarpMain.getLanguage();
						WarpMessages.outMessage(player, null, "Language");
					}
					else if(a[0].equalsIgnoreCase("English")) {
						Configs.CONFIG.getConfig().set("Language", "en");
						Configs.CONFIG.saveConfig();
						FWarpMain.getLanguage();
						WarpMessages.outMessage(player, null, "Language");
					}
				}
			}
		}
		if(l.equalsIgnoreCase("SetPer")) {
			if(player.hasPermission(new WarpPermission().Give) == true) {
				if(a.length == 3) {
					if(a[2].equalsIgnoreCase("enable") || a[2].equalsIgnoreCase("disable")) {
						OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(a[0]);
						Player target = Bukkit.getPlayer(a[0]);
						if(offlinePlayer.isOnline()) {
							
							String PermText = "";
							PermText = PermText + a[1];
							PermText = PermText.replaceAll("warp.", "");
							PermText = PermText.replaceAll("portal.use", "useportal");
							PermText = PermText.replaceAll("scroll.use", "usescroll");
							
							WarpGivePer.givePlayerPermissions(target, PermText, a[2]);
							WarpMessages.givePermission(player, target, a[1], a[2]);
						} else {
							WarpMessages.outMessage(player, a[0], "noPlayer");
						}
					} else {
						player.getWorld().playSound(player.getLocation(), Sound.ENTITY_VILLAGER_HURT, 0.5f, 1f);
						player.sendMessage(FWarpMain.WarpPrefix + ChatColor.RED + "<enable or disable>");
					}
				} else {
					player.getWorld().playSound(player.getLocation(), Sound.ENTITY_VILLAGER_HURT, 0.5f, 1f);
					player.sendMessage(FWarpMain.WarpPrefix + ChatColor.RED + "Command : " + ChatColor.GOLD + "/SetPer <PlayerName PermissionNode true,false>");

				}
			} else if(player.hasPermission(new WarpPermission().Give) == false) {
				player.getWorld().playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 0.5f, 1f);
				WarpMessages.noPermission(player);
				return false;

			}
		}

		if(l.equalsIgnoreCase("Goto")) { 		// 마스터 워프 명령어
			if(player.hasPermission(new WarpPermission().Goto) == true) {
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
			} else if(player.hasPermission(new WarpPermission().Goto) == false) {
				player.getWorld().playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 0.5f, 1f);
				WarpMessages.noPermission(player);
				return false;

			}
		}

		if(l.equalsIgnoreCase("warp")) { 		// 워프 관련 명령어
			if(player.hasPermission(new WarpPermission().Warp) == true) {
				if(a.length >= 1) {
					if(a[0].equalsIgnoreCase("list")) {
						player.getWorld().playSound(player.getLocation(), Sound.ENTITY_CHICKEN_EGG, 0.5f, 1f);
						player.sendMessage(FWarpMain.WarpPrefix + ChatColor.WHITE + "Warp List : " + ChatColor.GOLD + FWarpMain.Lists);

					}
					else if(a[0].equalsIgnoreCase("Reload")) {
						Configs.CONFIG.reloadConfig();
						Configs.PORTAL.reloadConfig();
						Configs.WARP.reloadConfig();
						Configs.PER.reloadConfig();
						FWarpMain.Lists.clear();
						FWarpMain.getLanguage();
						FWarpMain.putWARPData();
						WarpGivePer.loadPermissions();
						player.sendMessage(FWarpMain.WarpPrefix + "Reload Plugin!");
						player.getWorld().playSound(player.getLocation(), Sound.BLOCK_ANVIL_PLACE, 0.35f, 1f);

					}
					else if(a[0].equalsIgnoreCase("set")) {
						if(a.length != 1) {
							if(FWarpMain.Lists.contains(a[1])) {
								player.getWorld().playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 0.5f, 1f);
								WarpMessages.outMessage(player, a[1], "alreadyWarp");

							} else {
								player.getWorld().playSound(player.getLocation(), Sound.ENTITY_CHICKEN_EGG, 0.5f, 1f);
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
						player.getWorld().playSound(player.getLocation(), Sound.ENTITY_VILLAGER_HURT, 0.5f, 1f);
						player.sendMessage(FWarpMain.WarpPrefix + ChatColor.RED + "Command : " + ChatColor.GOLD + "/warp <set, del, list, reload>");

					}
				} else {
					player.getWorld().playSound(player.getLocation(), Sound.ENTITY_VILLAGER_HURT, 0.5f, 1f);
					player.sendMessage(FWarpMain.WarpPrefix + ChatColor.RED + "Command : " + ChatColor.GOLD + "/warp <set, del, list, reload>");

				}
			} else if(player.hasPermission(new WarpPermission().Warp) == false) {
				player.getWorld().playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 0.5f, 1f);
				WarpMessages.noPermission(player);
				return false;

			}
		}

		if(l.equalsIgnoreCase("portal")) { 		//포탈 관련 명령어
			if(player.hasPermission(new WarpPermission().Portal) == true) {
				if(a.length >= 1) {
					if(a[0].equalsIgnoreCase("erase") && WarpPortal.master == false) {

						WarpPortal.master = true;

						player.getWorld().playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 0.5f, 1f);
						WarpMessages.outMessage(player, null, "problemerase");

					} else if(a[0].equalsIgnoreCase("erase") && WarpPortal.master == true) {

						WarpPortal.master = false;

						player.getWorld().playSound(player.getLocation(), Sound.ENTITY_VILLAGER_TRADING, 0.5f, 1f);
						WarpMessages.outMessage(player, null, "problemcancle");

					} else if(a[0].equalsIgnoreCase("remove") && WarpPortal.remove == 0) {

						WarpPortal.remove = 1;

						player.getWorld().playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 0.5f, 1f);
						WarpMessages.outMessage(player, null, "delPortal");

					} else if(a[0].equalsIgnoreCase("remove") && WarpPortal.remove == 1) {

						WarpPortal.remove = 0;

						player.getWorld().playSound(player.getLocation(), Sound.ENTITY_VILLAGER_TRADING, 0.5f, 1f);
						WarpMessages.outMessage(player, null, "canDelPortal");

					}
					else if(a[0].equalsIgnoreCase("create")) {
						if(a.length >= 2) {

							if(FWarpMain.warpWorld.get(a[1]) == null) {

								player.getWorld().playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 0.5f, 1f);
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
						} else {
							player.getWorld().playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 0.5f, 1f);
							WarpMessages.outMessage(player, null, "emptyWarp");
						}

					} else {
						player.getWorld().playSound(player.getLocation(), Sound.ENTITY_VILLAGER_HURT, 0.5f, 1f);
						player.sendMessage(FWarpMain.WarpPrefix + ChatColor.RED + "Command : " + ChatColor.GOLD + "/portal <create, remove>");
					}
				} else {
					player.getWorld().playSound(player.getLocation(), Sound.ENTITY_VILLAGER_HURT, 0.5f, 1f);
					player.sendMessage(FWarpMain.WarpPrefix + ChatColor.RED + "Command : " + ChatColor.GOLD + "/portal <create, remove>");
				} 
			}else if(player.hasPermission(new WarpPermission().Portal) == false) {
				player.getWorld().playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 0.5f, 1f);
				WarpMessages.noPermission(player);
				return false;

			}
		}

		if(l.equalsIgnoreCase("Scroll")) { 		// 스크롤 관련 명령어
			if(player.hasPermission(new WarpPermission().Scroll) == true) {
				if(a.length >= 2) {
					if(FWarpMain.Lists.contains(a[0])) {
						String text = "";
						for(int i = 2; i < a.length; i++) {
							text = text + "" + a[i];
							String loreText = a[i];
							loreText = loreText.replaceAll("_", " ");
							WarpScroll.loreList.add(loreText);
						}
						text = text.replaceFirst(" ", "");

						WarpScroll.customItem(player, a[1], a[0]);
						WarpScroll.loreList.clear();
						player.getWorld().playSound(player.getLocation(), Sound.ENTITY_CHICKEN_EGG, 0.5f, 1f);
						WarpMessages.outMessage(player, a[0], "createScroll");

					} else {
						player.getWorld().playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 0.5f, 1f);
						WarpMessages.outMessage(player, null, "wrongScroll");
					}
				} else {
					player.getWorld().playSound(player.getLocation(), Sound.ENTITY_VILLAGER_HURT, 0.5f, 1f);
					player.sendMessage(FWarpMain.WarpPrefix + ChatColor.RED + "Command : " + ChatColor.GOLD + "/Scroll <PortalName, ItemName, ItemLore>");
				} 
			} else if(player.hasPermission(new WarpPermission().Scroll) == false) {
				player.getWorld().playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 0.5f, 1f);
				WarpMessages.noPermission(player);
				return false;

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