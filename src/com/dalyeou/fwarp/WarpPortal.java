package com.dalyeou.fwarp;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import com.dalyeou.fwarp.ConfigHolder.Configs;

public class WarpPortal implements CommandExecutor, TabCompleter, Listener {

	public static HashMap<Entity, Location> portalloc = new HashMap<>();

	public static Plugin plugin = FWarpMain.getPlugin(FWarpMain.class);
	public static int remove = 0;

	static ArmorStand holograms;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List onTabComplete(CommandSender cs, Command c, String l, String[] a) {
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

	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();

		String pluginLanguage = Configs.CONFIG.getConfig().getString("Language");
		player.sendMessage(FWarpMain.Language + "\n" + pluginLanguage + "\n" + FWarpMain.pluginLanguage);
	}

	@Override
	public boolean onCommand(CommandSender cs, Command c, String l, String[] a) {
		Player player = (Player) cs;
		if(! (cs instanceof Player)) {
			return false;
		}
		if(l.equalsIgnoreCase("portal")) {
			if(a.length >= 2) {
				if(a[0].equalsIgnoreCase("create")) {

					if(FWarpMain.warpWorld.get(a[1]) == null) {

						player.getWorld().playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 0.5f, 1f);
						outMessage(player, a[1], "noWarp");
						return true;

					} else {
						String text = "";
						for(int i = 2; i < a.length; i++) {
							text = text + " " + a[i];

						}
						text = text.replaceFirst(" ", "");

						ItemStack item = new ItemStack(Material.JACK_O_LANTERN);

						Location hologramLocation = player.getLocation().subtract(0, 0, 0);
						portalloc.put(player, hologramLocation);

						holograms = hologramLocation.getWorld().spawn(hologramLocation, ArmorStand.class);
						holograms.setCustomName(ChatColor.translateAlternateColorCodes('&', text));
						holograms.setCustomNameVisible(true);
						holograms.setGravity(false);
						holograms.setVisible(false);
						holograms.setSmall(true);
						holograms.setRemoveWhenFarAway(false);
						holograms.setHelmet(item);
						UUID hologramUUID = holograms.getUniqueId();

						player.getWorld().playSound(player.getLocation(), Sound.ENTITY_CHICKEN_EGG, 0.5f, 1f);

						FWarpMain.portalNames.put(hologramUUID, a[1]);
						Configs.PORTAL.getConfig().set("Portal." + hologramUUID + ".Name", a[1]);
						Configs.PORTAL.getConfig().set("Portal." + hologramUUID + ".Loc ", hologramLocation);
						Configs.PORTAL.saveConfig();

						outMessage(player, a[1], "linkPortal");

					}
				}
			}else if(a.length == 1) {
				if(a[0].equalsIgnoreCase("remove") && remove == 0) {

					remove = 1;

					outMessage(player, null, "delPortal");

				} else if(a[0].equalsIgnoreCase("remove") && remove == 1) {

					remove = 0;

					outMessage(player, null, "canDelPortal");

				}
			}else {
				player.getWorld().playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 0.5f, 1f);
				player.sendMessage(FWarpMain.WarpPrefix + ChatColor.RED + "Command : /portal <create, remove>");
			}
		}
		return true;
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void KillHolo(EntityDamageByEntityEvent event) {
		Player player = (Player) event.getDamager();

		if(event.getEntity().getType() == EntityType.ARMOR_STAND && player.getItemInHand().getType() == Material.BOOK_AND_QUILL) {
			if(remove == 1) {
				event.setCancelled(true);
				ArmorStand a = (ArmorStand) event.getEntity();
				UUID hologramUUID = a.getUniqueId();
				String HologramName = FWarpMain.portalNames.get(hologramUUID);

				if(HologramName != null) {
					a.remove();

					player.getWorld().playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 0.4f, 3f);

					FWarpMain.portalNames.put(hologramUUID, null);
					Configs.PORTAL.getConfig().set("Portal." + hologramUUID , null);
					Configs.PORTAL.getConfig().set("Portal." + hologramUUID + ".Name", null);
					Configs.PORTAL.getConfig().set("Portal." + hologramUUID + ".Loc ", null);
					Configs.PORTAL.saveConfig();

					remove = 0;
					outMessage(player, HologramName, "removePortal");

				} else {
					event.setCancelled(true);
					player.getWorld().playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 0.5f, 1f);
					outMessage(player, null, "donBreak");

				}
			}
		}
	}

	@EventHandler
	public void onPlayerInteractAtEntity(PlayerInteractAtEntityEvent event) {
		Entity entity = event.getRightClicked();

		if(entity.getType() == EntityType.ARMOR_STAND) {
			UUID hologramUUID = entity.getUniqueId();
			String HologramName = FWarpMain.portalNames.get(hologramUUID);

			if(FWarpMain.warpWorld.get(HologramName) != null) {
				event.setCancelled(true);
			}
		}
	}

	@EventHandler
	public void onPlayerToggleSneakEvent(PlayerToggleSneakEvent event) {
		Player player = event.getPlayer();

		if (player.isSneaking()) {
			for(Entity nearby : player.getNearbyEntities(0.175,0,0.175)) {
				if(nearby instanceof Entity) {

					
					if(nearby.getType() == EntityType.ARMOR_STAND && nearby.getCustomName() != null) {
						UUID hologramUUID = nearby.getUniqueId();
						String HologramName = FWarpMain.portalNames.get(hologramUUID);
						String PortalName = nearby.getName();
						PortalName = PortalName.replaceFirst("§6", "");
						
						if(FWarpMain.warpWorld.get(PortalName) != null) {

							playSound(player);
							moveToWarp(player, PortalName);
							playSound(player);
							outMessage(player, PortalName, "moveWarp");
							return;

						} else if(FWarpMain.warpWorld.get(HologramName) != null) {

							playSound(player);
							moveToWarp(player, HologramName);
							playSound(player);
							outMessage(player, HologramName, "moveWarp");
							return;

						} else {
							player.getWorld().playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 0.5f, 1f);
							outMessage(player, null, "noLink");
							return;

						}

					}
				}
			}
		} else {
			//is sneaking code
		}
	}

	public void moveToWarp(Player player, String Name) {
		World world = FWarpMain.warpWorld.get(Name);
		Double x = FWarpMain.warpX.get(Name);
		Double y = FWarpMain.warpY.get(Name);
		Double z = FWarpMain.warpZ.get(Name);
		Float yaw = FWarpMain.warpYaw.get(Name);
		Float pitch = FWarpMain.warpPitch.get(Name);

		player.teleport(new Location(world, x, y, z, yaw, pitch));
	}

	public void outMessage(Player player, String name, String type) {
		if(type == "noWarp") {
			if(FWarpMain.pluginLanguage.equalsIgnoreCase(FWarpMain.baseLanguage)) {
				player.sendMessage(FWarpMain.WarpPrefix + ChatColor.RED + "Calm down! There's no warp named " + ChatColor.DARK_GREEN + name + "! " + ChatColor.RED + "Check again with the /warp list command!");
			} else {
				player.sendMessage(FWarpMain.WarpPrefix + ChatColor.RED + "잠시만요! " + ChatColor.DARK_GREEN + name + " " + ChatColor.RED + "라는 이름의 워프는 없어요! /warp list 명령어로 다시 확인해봐요!");
			}
		}
		if(type == "linkPortal") {
			if(FWarpMain.pluginLanguage.equalsIgnoreCase(FWarpMain.baseLanguage)) {
				player.sendMessage(FWarpMain.WarpPrefix + ChatColor.DARK_GREEN + "Ta-da! I created a portal that connects to " + ChatColor.GREEN + name + "!");
			} else {
				player.sendMessage(FWarpMain.WarpPrefix + ChatColor.DARK_GREEN + "짜잔! 제가 " + ChatColor.GREEN + name + " 라는 이름의 워프와 포탈을 연결시켰어요!");
			}
		}
		if(type == "delPortal") {
			if(FWarpMain.pluginLanguage.equalsIgnoreCase(FWarpMain.baseLanguage)) {
				player.sendMessage(FWarpMain.WarpPrefix + ChatColor.DARK_GREEN + "Oh, did you create the wrong portal? It's okay! We can make it again!");
				player.sendMessage(FWarpMain.WarpPrefix + ChatColor.DARK_GREEN + "Hit the portal you want to delete!");
			} else {
				player.sendMessage(FWarpMain.WarpPrefix + ChatColor.DARK_GREEN + "혹시 포탈을 잘못 만드셧나요? 괜찮아요! 다시 만들면 되니까요!");
				player.sendMessage(FWarpMain.WarpPrefix + ChatColor.DARK_GREEN + "지우려는 포탈을 공격해주세요!");
			}
		}
		if(type == "canDelPortal") {
			if(FWarpMain.pluginLanguage.equalsIgnoreCase(FWarpMain.baseLanguage)) {
				player.sendMessage(FWarpMain.WarpPrefix + ChatColor.DARK_GREEN + "Didn't I make the wrong portal? What a relief!");

			} else {
				player.sendMessage(FWarpMain.WarpPrefix + ChatColor.DARK_GREEN + "제가 포탈을 잘못 만든게 아니였군요? 다행이네요!");
			}
		}
		if(type == "removePortal") {
			if(FWarpMain.pluginLanguage.equalsIgnoreCase(FWarpMain.baseLanguage)) {
				player.sendMessage(FWarpMain.WarpPrefix + ChatColor.DARK_GREEN + "I just deleted a portal that connects to a warp called " + ChatColor.GREEN + name + "!" + ChatColor.DARK_GREEN +" It doesn't exist anymore!");
			} else {
				player.sendMessage(FWarpMain.WarpPrefix + ChatColor.DARK_GREEN + "제가 " + ChatColor.GREEN + name + " " + ChatColor.DARK_GREEN +"라는 워프에 연결된 포탈을 삭제했어요! 이제 더이상 그 포탈은 없어요!");
			}
		}
		if(type == "donBreak") {
			if(FWarpMain.pluginLanguage.equalsIgnoreCase(FWarpMain.baseLanguage)) {
				player.sendMessage(FWarpMain.WarpPrefix + ChatColor.RED + "Wait! Don't break it! This isn't a portal! Let's find something else!");
			} else {
				player.sendMessage(FWarpMain.WarpPrefix + ChatColor.RED + "잠시만요! 부수지 마세요! 이건 포탈이 아니에요! 지울 포탈을 찾아보자구요!");
			}
		}
		if(type == "moveWarp") {
			if(FWarpMain.pluginLanguage.equalsIgnoreCase(FWarpMain.baseLanguage)) {
				player.sendMessage(FWarpMain.WarpPrefix + ChatColor.DARK_GREEN + "Ta-da! I moved you to " + ChatColor.GREEN + name + ChatColor.DARK_GREEN + "!");
			} else {
				player.sendMessage(FWarpMain.WarpPrefix + ChatColor.DARK_GREEN + "짠! 제가 당신을 " + ChatColor.GREEN + name + ChatColor.DARK_GREEN + " 라는 워프장소로 이동시켜드렸어요!");
			}
		}
		if(type == "noLink") {
			if(FWarpMain.pluginLanguage.equalsIgnoreCase(FWarpMain.baseLanguage)) {
				player.sendMessage(FWarpMain.WarpPrefix + ChatColor.RED + "Oh, I'm sorry! I think the portal that was linked to this portal is gone!");
				player.sendMessage(FWarpMain.WarpPrefix + ChatColor.RED + "Please delete this portal or specify the same warp as the name linked to this portal!");
			} else {
				player.sendMessage(FWarpMain.WarpPrefix + ChatColor.RED + "이런, 죄송해요! 제 생각엔 이 포탈에 연결된 워프가 없어진 것 같아요!");
				player.sendMessage(FWarpMain.WarpPrefix + ChatColor.RED + "이 포탈을 삭제하거나 이 포탈에 연결된 이름과 동일한 이름의 워프를 만들어 주세요!");
			}
		}

	}

	public static void playSound(Player player) {
		player.getWorld().playSound(player.getLocation(), Sound.ENTITY_ENDERMEN_TELEPORT, 0.5f, 1f);
		player.getWorld().spawnParticle(Particle.PORTAL, player.getEyeLocation().subtract(0, 0, 0), 100);
		player.getWorld().spawnParticle(Particle.PORTAL, player.getLocation().subtract(0, -0.5, 0), 100);

	}

}
