package com.dalyeou.fwarp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.permissions.PermissionAttachment;

import com.dalyeou.fwarp.ConfigHolder.Configs;

public class WarpGivePer implements Listener {



	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		String playerName = player.getName();
		UUID playerUUID = player.getUniqueId();

		if(Configs.PER.getConfig().getConfigurationSection("Permissions." + playerUUID) == null || Configs.PER.getConfig().getConfigurationSection("Permissions." + playerUUID + "." + playerName) == null) {
			String usePortalPer = Configs.CONFIG.getConfig().getString("useportal");
			String useScrollPer = Configs.CONFIG.getConfig().getString("usescroll");
			String gotoPer = Configs.CONFIG.getConfig().getString("goto");
			String portalPer = Configs.CONFIG.getConfig().getString("portal");
			String scrollPer = Configs.CONFIG.getConfig().getString("scroll");
			String warpPer = Configs.CONFIG.getConfig().getString("warp");
			String givePer = Configs.CONFIG.getConfig().getString("give");

			Configs.PER.getConfig().set("Permissions." + playerUUID + "." + playerName + ".useportal", usePortalPer);
			Configs.PER.getConfig().set("Permissions." + playerUUID + "." + playerName + ".usescroll", useScrollPer);
			Configs.PER.getConfig().set("Permissions." + playerUUID + "." + playerName + ".goto", gotoPer);
			Configs.PER.getConfig().set("Permissions." + playerUUID + "." + playerName + ".portal", portalPer);
			Configs.PER.getConfig().set("Permissions." + playerUUID + "." + playerName + ".scroll", scrollPer);
			Configs.PER.getConfig().set("Permissions." + playerUUID + "." + playerName + ".warp", warpPer);
			Configs.PER.getConfig().set("Permissions." + playerUUID + "." + playerName + ".give", givePer);
			Configs.PER.saveConfig();
		} else {
			givePermissions(player);
		}
	}

	@EventHandler
	public void onQuit(PlayerQuitEvent event) { 		// 나가면 펫 정보 삭제

	}

	static HashMap<UUID, PermissionAttachment> permission = new HashMap<UUID, PermissionAttachment>();

	public static HashMap<UUID, List<String>> permissionTypes = new HashMap<>();
	public static HashMap<UUID, List<String>> permissionBooleans = new HashMap<>();

	public static final String enable = "enable";
	public static final String disable = "disable";
	public static final String op = "op";

	public static void loadPermissions() {
		for(String PlayerUUID : Configs.PER.getConfig().getConfigurationSection("Permissions.").getKeys(false)){
			for(String PlayerName : Configs.PER.getConfig().getConfigurationSection("Permissions." + PlayerUUID).getKeys(false)){
				for(String PlayerPer : Configs.PER.getConfig().getConfigurationSection("Permissions." + PlayerUUID + "." + PlayerName).getKeys(false)){

					String PermText = "warp.";
					PermText = PermText + PlayerPer;
					PermText = PermText.replaceAll("useportal", "portal.use");
					PermText = PermText.replaceAll("usescroll", "scroll.use");
					UUID uuid = UUID.fromString(PlayerUUID);
					List<String> permissionNameList = permissionTypes.getOrDefault(uuid, new ArrayList<String>());
					permissionNameList.add(PermText);
					permissionTypes.put(uuid, permissionNameList);

					List<String> permissionBooleanList = permissionBooleans.getOrDefault(uuid, new ArrayList<String>());
					permissionBooleanList.add(Configs.PER.getConfig().getString("Permissions." + PlayerUUID + "." + PlayerName + "." + PlayerPer));
					permissionBooleans.put(uuid, permissionBooleanList);
				}
			}
		}
	}
	static HashMap<UUID, PermissionAttachment> perm = new HashMap<UUID, PermissionAttachment>();

	public static void givePlayerPermissions(Player player, String persName, String boo) {
		PermissionAttachment attachment = player.addAttachment(WarpScroll.plugin);
		permission.put(player.getUniqueId(), attachment);
		if(boo.equalsIgnoreCase(enable)) {
			PermissionAttachment pperms = permission.get(player.getUniqueId());
			pperms.setPermission(persName, true);
			
			Configs.PER.getConfig().set("Permissions." + player.getUniqueId() + "." + player.getName() + "." + persName, boo);
			Configs.PER.saveConfig();
			loadPermissions();
		} else if(boo.equalsIgnoreCase(disable)) {
			PermissionAttachment pperms = permission.get(player.getUniqueId());
			pperms.setPermission(persName, false);
			Configs.PER.getConfig().set("Permissions." + player.getUniqueId() + "." + player.getName() + "." + persName, boo);
			Configs.PER.saveConfig();
			loadPermissions();
		} else {
			if(FWarpMain.pluginLanguage.equalsIgnoreCase(FWarpMain.baseLanguage)) {
			player.sendMessage(FWarpMain.WarpPrefix + ChatColor.RED + "Verify the format of the command correctly!");
			} else {
				player.sendMessage(FWarpMain.WarpPrefix + ChatColor.RED + "명령어 형식을 제대로 확인해주세요!");
			}
			return;
		}
	}

	public static void givePermissions(Player player) {
		UUID PlayerUUID = player.getUniqueId();
		PermissionAttachment attachment = player.addAttachment(WarpScroll.plugin);
		permission.put(player.getUniqueId(), attachment);


		int listint = permissionTypes.get(PlayerUUID).size();
		for(int z = 0; z < listint; z++) {
			String persNames = permissionTypes.get(PlayerUUID).get(z);
			String persBooleans = permissionBooleans.get(PlayerUUID).get(z);

			if(persBooleans.equalsIgnoreCase(enable)) {
				PermissionAttachment pperms = permission.get(player.getUniqueId());
				pperms.setPermission(persNames, true);
			} else if(persBooleans.equalsIgnoreCase(disable)) {
				PermissionAttachment pperms = permission.get(player.getUniqueId());
				pperms.setPermission(persNames, false);
			} else if(persBooleans.equalsIgnoreCase(op)) {
			}
		}

	}

}
