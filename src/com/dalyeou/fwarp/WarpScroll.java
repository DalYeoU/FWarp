package com.dalyeou.fwarp;

import java.util.ArrayList;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

public class WarpScroll implements Listener {

	public static Plugin plugin = FWarpMain.getPlugin(FWarpMain.class);

	static ArrayList<String> loreList = new ArrayList<String>();

	public static ItemStack customItem(Player player, String displayName, String warpName) { 		// 명령어로 스크롤 뽑아내기
		ItemStack item = new ItemStack(Material.PAPER);
		ArrayList<String> lore = new ArrayList<String>();
		ItemMeta meta = item.getItemMeta();

		meta.setDisplayName(ChatColor.translateAlternateColorCodes('&',displayName));

		lore.add(ChatColor.GRAY + "Scroll");
		lore.add(ChatColor.GOLD + warpName);
		for(int i = 0; i < loreList.size(); i++) {
			lore.add(ChatColor.translateAlternateColorCodes('&',loreList.get(i)));

		}
		meta.setLore(lore);
		meta.addEnchant(Enchantment.LURE, 1, true);
		meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		item.setItemMeta(meta);

		player.getInventory().addItem(item);

		return item;
	}

	public static boolean isCustomItem(ItemStack item) { 		// 스크롤인지 확인
		if(item.hasItemMeta()){//1
			if(item.getType() == Material.PAPER) {
				if((item.getItemMeta().hasDisplayName()) && item.getItemMeta().hasLore() && item.getEnchantments() != null) {
					if(item.getItemMeta().getLore().contains(ChatColor.GRAY + "Scroll")) {
						return true;
					} else {
						return false;
					}
				} else {
					return false;
				}
			}else{
				return false;
			}
		}//1
		return false;
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) { 		// 바닥 좌클릭으로 스크롤 사용
		Player player = event.getPlayer();
		Action action = event.getAction();
		Block block = event.getClickedBlock();

		if(action.equals(Action.LEFT_CLICK_BLOCK)) {
			if(isCustomItem(player.getItemInHand()) == true ) {
				String wName = player.getItemInHand().getItemMeta().getLore().get(1);
				String PortalName = wName.replaceFirst("§6", "");

				if(player.hasPermission(new WarpPermission().ScrollUse) == true) {
					if(FWarpMain.Lists.contains(PortalName)) {
						Location blockLocation1 = block.getLocation();
						event.setCancelled(true);
						scrollPortalParticle(player, wName, blockLocation1.subtract(-0.5, -1, -0.5));
						createScrollPortal(player, wName, blockLocation1);
						player.getItemInHand().setAmount(player.getItemInHand().getAmount()-1);
						return;
					} else {
						player.getWorld().playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 0.5f, 1f);
						WarpMessages.outMessage(player, wName, "noLink");

					}
				} else if(player.hasPermission(new WarpPermission().ScrollUse) == false) {
					event.setCancelled(true);
					player.getWorld().playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 0.5f, 1f);
					WarpMessages.noPermission(player);
					return;

				}
			} else {
				return;
			}
		}
	}

	public static void scrollPortalParticle(Player player, String warpName, Location location1) { 		// 스크롤 포탈에 파티클 소환
		int b = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
			@Override
			public void run() {
				player.getWorld().spawnParticle(Particle.PORTAL, location1, 100);
			}
		}, 0, 10);

		Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
			@Override
			public void run() {
				Bukkit.getScheduler().cancelTask(b);
			}
		}, 200);
	}

	public static void createScrollPortal(Player player, String name, Location location) { 		// 스크롤 포탈 소환

		ArmorStand holograms = location.getWorld().spawn(location, ArmorStand.class);
		holograms.setGravity(false);
		holograms.setVisible(false);
		holograms.setSmall(true);
		holograms.setAI(false);
		holograms.setRemoveWhenFarAway(false);
		player.getWorld().playSound(location, Sound.ENTITY_CHICKEN_EGG, 0.5f, 1f);
		String PortalName = name.replaceFirst("§6", "");
		FWarpMain.portalNames.put(holograms.getUniqueId(), PortalName);
		WarpMessages.outMessage(player, null, "createScrollPortal");

		Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
			@Override
			public void run() {
				holograms.setCustomName(ChatColor.translateAlternateColorCodes('&', name));
				holograms.setCustomNameVisible(true);
			}
		}, 1);

		Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
			@Override
			public void run() {
				holograms.remove();
				player.getWorld().playSound(location, Sound.ENTITY_ITEM_BREAK, 0.5f, 1f);
				FWarpMain.portalNames.remove(holograms.getUniqueId());
				WarpMessages.outMessage(player, null, "deadScrollPortal");
			}
		}, 200);

	}

}
