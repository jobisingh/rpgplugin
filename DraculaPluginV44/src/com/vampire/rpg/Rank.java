package com.vampire.rpg;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.permissions.Permission;

public enum Rank {
	MEMBER(1, "", "Member", ChatColor.WHITE, ChatColor.GRAY,new String[]{}),
    BETA(2, "Beta", "Beta", ChatColor.RED, ChatColor.WHITE,new String[]{}),
    VIP(3, "VIP", "VIP", ChatColor.GOLD, ChatColor.WHITE,new String[]{}),
    KNIGHT(3, "Knight", "Knight", ChatColor.GOLD, ChatColor.WHITE,new String[]{}),
    DUKE(4, "Duke", "Duke", ChatColor.AQUA, ChatColor.WHITE,new String[]{}),
    KING(5, "King", "King", ChatColor.GOLD, ChatColor.WHITE,new String[]{}),
    QUEEN(5, "Queen", "Queen", ChatColor.GOLD, ChatColor.WHITE,new String[]{}),
    HELPER(6, "Helper", "Helper", ChatColor.AQUA, ChatColor.WHITE,new String[]{}),
    GAMEMASTER(7, "GM", "Gamemaster", ChatColor.BLUE, ChatColor.WHITE,new String[]{}),
    BUILDER(8, "Builder", "Builder", ChatColor.DARK_AQUA, ChatColor.WHITE,new String[]{"worldedit.*","voxelsniper.*","voxelsniper.sniper","voxelsniper.brush.*","voxelsniper.command.*","bukkit.command.gamemode","bukkit.command.effect","minecraft.command.effect","minecraft.command.gamemode","essentials.fly","essentials.hat","multiverse.teleport.self.NAME","mv.bypass.gamemode.*"}),
    MOD(9, "Mod", "Moderator", ChatColor.LIGHT_PURPLE, ChatColor.WHITE,new String[]{}),
    ADMIN(10, "Admin", "Administrator", ChatColor.DARK_RED, ChatColor.WHITE,new String[]{}),
    OWNER(11, "Owner", "Owner", ChatColor.DARK_PURPLE, ChatColor.WHITE,new String[]{"worldedit.*","voxelsniper.*","voxelsniper.sniper","voxelsniper.brush.*","voxelsniper.command.*","bukkit.command.gamemode","bukkit.command.effect","minecraft.command.effect","minecraft.command.gamemode","essentials.fly","essentials.hat","multiverse.teleport.self.NAME","mv.bypass.gamemode.*"});
	public final int power;
	public final String chatPrefix, rankDisplayName;
	public final ChatColor nameColor;
	public final ChatColor chatColor;
	public final String[] perms;
	
	private String chatRankDisplay = null;
	private String fullRankDisplay = null;
	Rank(int i, String chat, String display, ChatColor c1, ChatColor c2, String[] permissions) {
	    this.power = i;
	    this.chatPrefix = chat;
	    this.rankDisplayName = display;
	    this.nameColor = c1;
	    this.chatColor = c2;
	    this.perms=permissions;
	}
	public String gettChatRankDisplay() {
        if (chatRankDisplay != null)
            return chatRankDisplay;
        if (chatPrefix.length() == 0)
            return chatRankDisplay = nameColor + "";
        return chatRankDisplay = nameColor + "" + ChatColor.BOLD + chatPrefix + " " + ChatColor.WHITE;
    }
	public String[] getPermissions(){
		/*for(Rank r:Rank.values()){
			ArrayList<String>  Permissions=new ArrayList<String>();
			if(checkIsAtLeast(r)){
				for(String s:r.getPermissions())
					Permissions.add(s);
			}
			return (String[]) Permissions.toArray();
		}
		return null;*/
		return perms;
	}
	public String getName(){
	return rankDisplayName;
	}
	 public String getChatRankDisplay() {
	        if (chatRankDisplay != null)
	            return chatRankDisplay;
	        if (chatPrefix.length() == 0)
	            return chatRankDisplay = nameColor + "";
	        return chatRankDisplay = nameColor + "" + ChatColor.BOLD + chatPrefix + " " + ChatColor.WHITE;
	    }
	 public String getFullRankDisplay() {
	        if (fullRankDisplay != null)
	            return fullRankDisplay;
	        return fullRankDisplay = nameColor + "" + ChatColor.BOLD + rankDisplayName + " " + ChatColor.WHITE;
	    }
	 public boolean checkIsAtLeast(Rank other) {
	        return this.power >= other.power;
	    }
}