package com.dalyeou.fwarp;

import java.util.ArrayList;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
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

public class WarpScroll implements Listener, CommandExecutor {

	public static Plugin plugin = FWarpMain.getPlugin(FWarpMain.class);

	static ArrayList<String> loreList = new ArrayList<String>();

	public boolean onCommand(CommandSender cs, Command c, String l, String[] a) {
		Player player = (Player) cs;
		if(! (cs instanceof Player)) {
			return false;
		}
		if(l.equalsIgnoreCase("Scroll")) {
			if(a.length >= 2) {
				if(FWarpMain.Lists.contains(a[1])) {
					String text = "";
					for(int i = 2; i < a.length; i++) {
						text = text + "" + a[i];
						String loreText = a[i];
						loreText = loreText.replaceAll("_", " ");
						loreList.add(loreText);
					}
					text = text.replaceFirst(" ", "");

					customItem(player, a[0], a[1]);
					loreList.clear();
					outMessage(player, a[1], "createWarp");
					
				} else {
					outMessage(player, null, "wrongWarp");
				}
			}
		}
		return true;
	}

	public static ItemStack customItem(Player player, String displayName, String warpName){
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

	public static boolean isCustomItem(ItemStack item){
		if(item.hasItemMeta()){
			if((item.getItemMeta().hasDisplayName()) || item.getItemMeta().hasLore()){
				if(item.getItemMeta().getLore().contains(ChatColor.GRAY + "Scroll")) {
					return true;
				}else{
					return false;
				}
			}else{
				return false;
			}
		}else{
			return false;
		}
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		Action action = event.getAction();
		Block block = event.getClickedBlock();

		if(action.equals(Action.LEFT_CLICK_BLOCK)) {
			if(isCustomItem(player.getItemInHand()) == true) {
				Location blockLocation1 = block.getLocation();
				String wName = player.getItemInHand().getItemMeta().getLore().get(1);
				event.setCancelled(true);
				scrollPortalParticle(player, wName, blockLocation1.subtract(-0.5, -1, -0.5));
				createScrollPortal(player, wName, blockLocation1);
				player.getItemInHand().setAmount(player.getItemInHand().getAmount()-1);
				return;
			} else {
				return;
			}
		}
	}

	public static void scrollPortalParticle(Player player, String warpName, Location location1) {
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

	public static void createScrollPortal(Player player, String name, Location location) {

		ArmorStand holograms = location.getWorld().spawn(location, ArmorStand.class);
		holograms.setGravity(false);
		holograms.setVisible(false);
		holograms.setSmall(true);
		holograms.setAI(false);
		holograms.setRemoveWhenFarAway(false);
		player.getWorld().playSound(location, Sound.ENTITY_CHICKEN_EGG, 0.5f, 1f);
		outMessage(player, null, "createScrollPortal");

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
				outMessage(player, null, "deadScrollPortal");
			}
		}, 200);

	}
	
	public static void outMessage(Player player,String name, String type) {
		if(type == "createScrollPortal") {
			if(FWarpMain.pluginLanguage.equalsIgnoreCase(FWarpMain.baseLanguage)) {
				player.sendMessage(FWarpMain.WarpPrefix + ChatColor.DARK_GREEN + "I created a portal with scroll! The portal lasts only 10 seconds, so hurry up!");
			} else {
				player.sendMessage(FWarpMain.WarpPrefix + ChatColor.DARK_GREEN + "스크롤로 포탈을 만들었어요! 포탈은 10초밖에 유지되지 않으니 서두르세요!");
			}
		}
		if(type == "deadScrollPortal") {
			if(FWarpMain.pluginLanguage.equalsIgnoreCase(FWarpMain.baseLanguage)) {
				player.sendMessage(FWarpMain.WarpPrefix + ChatColor.DARK_GREEN + "The portal I made with scroll was erased due to the time limit!");
			} else {
				player.sendMessage(FWarpMain.WarpPrefix + ChatColor.DARK_GREEN + "스크롤로 만든 포털이 시간 제한 때문에 지워졌어요!");
			}
		}
		if(type == "wrongWarp") {
			if(FWarpMain.pluginLanguage.equalsIgnoreCase(FWarpMain.baseLanguage)) {
				player.sendMessage(FWarpMain.WarpPrefix + ChatColor.RED + "Failed to create scroll! I think there's a problem with the warp, can you check again with the /warp list command?");
			} else {
				player.sendMessage(FWarpMain.WarpPrefix + ChatColor.RED + "스크롤을 만드는데 실패했어요! 워프가 잘못된 거 같은데 /warp list 명령어로 다시 한번 확인해 줄래요?");
			}
		}
		if(type == "createWarp") {
			if(FWarpMain.pluginLanguage.equalsIgnoreCase(FWarpMain.baseLanguage)) {
				player.sendMessage(FWarpMain.WarpPrefix + ChatColor.DARK_GREEN + "Ta-da! I made a scroll to the " + ChatColor.GREEN + name + ChatColor.DARK_GREEN + " warp!");
			} else {
				player.sendMessage(FWarpMain.WarpPrefix + ChatColor.DARK_GREEN + "짜잔! 제가 " + ChatColor.GREEN + name + ChatColor.DARK_GREEN + " 워프로 이동하는 스크롤을 만들었어요!");
			}
		}
		
	}

}
