package com.dalyeou.fwarp;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class WarpMessages {

	public static void outMessage(Player player, String name, String type) {
		if(type == "alreadyWarp") {
			if(FWarpMain.pluginLanguage.equalsIgnoreCase(FWarpMain.baseLanguage)) {
				player.sendMessage(FWarpMain.WarpPrefix + ChatColor.RED + "Oh, that name already exists, so we can't create it with that name! Please choose a different name!");
			} else {
				player.sendMessage(FWarpMain.WarpPrefix + ChatColor.RED + "그 이름이 이미 있어서 그 이름으로 만들 수 없어요! 다른 이름을 정해주세요!");
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
		if(type == "emptyWarp") {
			if(FWarpMain.pluginLanguage.equalsIgnoreCase(FWarpMain.baseLanguage)) {
				player.sendMessage(FWarpMain.WarpPrefix + ChatColor.RED + "Wait! We need to write down the name of the warp!");
			} else {
				player.sendMessage(FWarpMain.WarpPrefix + ChatColor.RED + "잠시만요! 워프이름을 설정해주셔야 해요!");
			}
		}
		
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
		if(type == "wrongScroll") {
			if(FWarpMain.pluginLanguage.equalsIgnoreCase(FWarpMain.baseLanguage)) {
				player.sendMessage(FWarpMain.WarpPrefix + ChatColor.RED + "Failed to create scroll! I think there's a problem with the warp, can you check again with the /warp list command?");
			} else {
				player.sendMessage(FWarpMain.WarpPrefix + ChatColor.RED + "스크롤을 만드는데 실패했어요! 워프가 잘못된 거 같은데 /warp list 명령어로 다시 한번 확인해 줄래요?");
			}
		}
		if(type == "createScroll") {
			if(FWarpMain.pluginLanguage.equalsIgnoreCase(FWarpMain.baseLanguage)) {
				player.sendMessage(FWarpMain.WarpPrefix + ChatColor.DARK_GREEN + "Ta-da! I made a scroll to the " + ChatColor.GREEN + name + ChatColor.DARK_GREEN + " warp!");
			} else {
				player.sendMessage(FWarpMain.WarpPrefix + ChatColor.DARK_GREEN + "짜잔! 제가 " + ChatColor.GREEN + name + ChatColor.DARK_GREEN + " 워프로 이동하는 스크롤을 만들었어요!");
			}
		}

	}
	
}
