package com.dalyeou.fwarp;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import com.dalyeou.fwarp.ConfigHolder.Configs;

public class WarpPortal implements Listener {

	public static HashMap<Entity, Location> portalloc = new HashMap<>();

	public static Plugin plugin = FWarpMain.getPlugin(FWarpMain.class);
	public static int remove = 0;

	static ArmorStand holograms;

	

//	@EventHandler
//	public void onJoin(PlayerJoinEvent event) {
//		Player player = event.getPlayer();
//	}

	
	
	public static void createPortal(Player player, String text, String warpName) {
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

		FWarpMain.portalNames.put(hologramUUID, warpName);
		Configs.PORTAL.getConfig().set("Portal." + hologramUUID + ".Name", warpName);
		Configs.PORTAL.getConfig().set("Portal." + hologramUUID + ".Loc ", hologramLocation);
		Configs.PORTAL.saveConfig();
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void removePortal(EntityDamageByEntityEvent event) {
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
					WarpMessages.outMessage(player, HologramName, "removePortal");

				} else {
					event.setCancelled(true);
					player.getWorld().playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 0.5f, 1f);
					WarpMessages.outMessage(player, null, "donBreak");

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
							WarpMessages.outMessage(player, PortalName, "moveWarp");
							return;

						} else if(FWarpMain.warpWorld.get(HologramName) != null) {

							playSound(player);
							moveToWarp(player, HologramName);
							playSound(player);
							WarpMessages.outMessage(player, HologramName, "moveWarp");
							return;

						} else {
							player.getWorld().playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 0.5f, 1f);
							WarpMessages.outMessage(player, null, "noLink");
							return;

						}

					}
				}
			}
		} else {
			//is sneaking code
		}
	}

	public static void moveToWarp(Player player, String Name) {
		World world = FWarpMain.warpWorld.get(Name);
		Double x = FWarpMain.warpX.get(Name);
		Double y = FWarpMain.warpY.get(Name);
		Double z = FWarpMain.warpZ.get(Name);
		Float yaw = FWarpMain.warpYaw.get(Name);
		Float pitch = FWarpMain.warpPitch.get(Name);

		player.teleport(new Location(world, x, y, z, yaw, pitch));
	}

	public static void playSound(Player player) {
		player.getWorld().playSound(player.getLocation(), Sound.ENTITY_ENDERMEN_TELEPORT, 0.5f, 1f);
		player.getWorld().spawnParticle(Particle.PORTAL, player.getEyeLocation().subtract(0, 0, 0), 100);
		player.getWorld().spawnParticle(Particle.PORTAL, player.getLocation().subtract(0, -0.5, 0), 100);

	}

}
