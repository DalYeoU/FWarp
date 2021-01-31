package com.dalyeou.fwarp;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
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
				player.sendMessage(FWarpMain.WarpPrefix + ChatColor.RED + "Wait a minute! We need to write Warp's name!");
			} else {
				player.sendMessage(FWarpMain.WarpPrefix + ChatColor.RED + "잠시만요! 워프이름을 적어주셔야 해요!");
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
				player.sendMessage(FWarpMain.WarpPrefix + ChatColor.DARK_GREEN + "Attack the portal you want to erase with a book and quill!");
			} else {
				player.sendMessage(FWarpMain.WarpPrefix + ChatColor.DARK_GREEN + "혹시 포탈을 잘못 만드셧나요? 괜찮아요! 다시 만들면 되니까요!");
				player.sendMessage(FWarpMain.WarpPrefix + ChatColor.DARK_GREEN + "지우려는 포탈을 책과 깃펜으로 공격해주세요!");
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
		if(type == "problemerase") {
			if(FWarpMain.pluginLanguage.equalsIgnoreCase(FWarpMain.baseLanguage)) {
				player.sendMessage(FWarpMain.WarpPrefix + ChatColor.DARK_GREEN + "Do you have a problem? Don't worry! I'll erase it at once! Please use flint and steel!");
			} else {
				player.sendMessage(FWarpMain.WarpPrefix + ChatColor.DARK_GREEN + "혹시 문제가 생기셧나요? 걱정하지 마세요! 제가 한방에 지워드릴게요! 라이터를 사용해주세요!");
			}
		}
		if(type == "problemcancle") {
			if(FWarpMain.pluginLanguage.equalsIgnoreCase(FWarpMain.baseLanguage)) {
				player.sendMessage(FWarpMain.WarpPrefix + ChatColor.DARK_GREEN + "You seem to have solved the problem! That's a relief!");
			} else {
				player.sendMessage(FWarpMain.WarpPrefix + ChatColor.DARK_GREEN + "문제를 해결하셧나 보군요! 다행이네요!");
			}
		}
		if(type == "problemPortal") {
			if(FWarpMain.pluginLanguage.equalsIgnoreCase(FWarpMain.baseLanguage)) {
				player.sendMessage(FWarpMain.WarpPrefix + ChatColor.DARK_GREEN + "I erased the problem!");
			} else {
				player.sendMessage(FWarpMain.WarpPrefix + ChatColor.DARK_GREEN + "제가 골칫덩어리를 지워버렸어요!");
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
		if(type == "fakePortal") {
			if(FWarpMain.pluginLanguage.equalsIgnoreCase(FWarpMain.baseLanguage)) {
				player.sendMessage(FWarpMain.WarpPrefix + ChatColor.RED + "Hey! Stop it! Isn't the name tag a waste?");

			} else {
				player.sendMessage(FWarpMain.WarpPrefix + ChatColor.RED + "이봐요! 그만해요! 이름표가 아깝지 않나요?");
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
				player.sendMessage(FWarpMain.WarpPrefix + ChatColor.DARK_GREEN + "The scroll portal was erased because of the time limit!");
			} else {
				player.sendMessage(FWarpMain.WarpPrefix + ChatColor.DARK_GREEN + "스크롤로 만든 포탈이 시간 제한 때문에 지워졌어요!");
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
		if(type == "noPlayer") {
			if(FWarpMain.pluginLanguage.equalsIgnoreCase(FWarpMain.baseLanguage)) {
				player.sendMessage(FWarpMain.WarpPrefix + ChatColor.RED + "Sorry, there is no player named " + ChatColor.DARK_GREEN + name + ChatColor.RED + "!");
			} else {
				player.sendMessage(FWarpMain.WarpPrefix + ChatColor.RED + "죄송해요, " + ChatColor.DARK_GREEN + name + ChatColor.RED + "라는 플레이어가 없어요!");
			}
		}
		if(type == "Language") {
			if(FWarpMain.pluginLanguage.equalsIgnoreCase(FWarpMain.baseLanguage)) {
				player.sendMessage(FWarpMain.WarpPrefix + ChatColor.DARK_GREEN + "Now plugin's Language is " + ChatColor.GREEN + "ENGLISH!");
			} else {
				player.sendMessage(FWarpMain.WarpPrefix + ChatColor.DARK_GREEN + "이제 플러그인의 언어는 " + ChatColor.GREEN + "한국어에요!!");
			}
		}
		
	}
	
	public static void givePermission(Player player, Player target, String permission, String perType) {
			if(FWarpMain.pluginLanguage.equalsIgnoreCase(FWarpMain.baseLanguage)) {
//				KAI modified your warp.goto node to true!
//				I modified dkrwm's warp.gotto node to true!
				player.sendMessage(FWarpMain.WarpPrefix + ChatColor.DARK_GREEN + "I modified " + ChatColor.GREEN + target.getName() + ChatColor.DARK_GREEN + " 's " + ChatColor.GREEN + permission + ChatColor.DARK_GREEN + " node to " + ChatColor.GREEN + perType + ChatColor.DARK_GREEN + "!");
				target.sendMessage(FWarpMain.WarpPrefix + ChatColor.GREEN + player.getName() + ChatColor.DARK_GREEN + "modified your " + ChatColor.GREEN + permission + ChatColor.DARK_GREEN + " node to " + ChatColor.GREEN + perType + ChatColor.DARK_GREEN + "!");
			} else {
//				KAI님이 당신의 warp.goto 노드를 true로 수정했어요!
//				dkrwm님의 warp.goto노드를 true로 수정했어요!
				target.sendMessage(FWarpMain.WarpPrefix + ChatColor.GREEN + player.getName() + ChatColor.DARK_GREEN + "님이 당신의 " + ChatColor.GREEN + permission + ChatColor.DARK_GREEN + " 노드를 " + ChatColor.GREEN + perType + ChatColor.DARK_GREEN + "로 수정했어요!");
				player.sendMessage(FWarpMain.WarpPrefix + ChatColor.GREEN + target.getName() + ChatColor.DARK_GREEN + "님의 " + ChatColor.GREEN + permission + ChatColor.DARK_GREEN + "노드를 " + ChatColor.GREEN + perType + ChatColor.DARK_GREEN + "로 수정했어요!");
			}
	}
	
	public static void noPermission(Player player) {
			if(FWarpMain.pluginLanguage.equalsIgnoreCase(FWarpMain.baseLanguage)) {
				player.sendMessage(FWarpMain.WarpPrefix + ChatColor.RED + "Sorry, You don't have permission!");
				player.getWorld().playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 0.5f, 1f);
			} else {
				player.sendMessage(FWarpMain.WarpPrefix + ChatColor.RED + "죄송해요, 당신은 이 명령어를 사용할 권한이 없어요!");
				player.getWorld().playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 0.5f, 1f);
			}
	}
	
}
