package com.vampire.rpg.utils;

import java.lang.reflect.Constructor;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.vampire.rpg.Pluginc;

public class TitleAPI {
	   public static void sendTitle(Player player, String title, String subtitle, int fadeIn, int stay, int fadeOut) {
	        try {
	            if (title != null) {
	                title = ChatColor.translateAlternateColorCodes('&', title);
	                title = title.replaceAll("%player%", player.getDisplayName());

	                Object e = getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0].getField("TIMES").get(null);
	                Object chatTitle = getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", new Class[] { String.class }).invoke(null, new Object[] {
	                        "{\"text\":\"" + title + "\"}" });
	                Constructor<?> subtitleConstructor = getNMSClass("PacketPlayOutTitle").getConstructor(new Class[] {
	                        getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0],
	                        getNMSClass("IChatBaseComponent"),
	                        Integer.TYPE,
	                        Integer.TYPE,
	                        Integer.TYPE });
	                Object titlePacket = subtitleConstructor.newInstance(new Object[] { e, chatTitle, fadeIn, stay, fadeOut });
	                sendPacket(player, titlePacket);

	                e = getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0].getField("TITLE").get(null);
	                chatTitle = getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", new Class[] { String.class }).invoke(null, new Object[] {
	                        "{\"text\":\"" + title + "\"}" });
	                subtitleConstructor = getNMSClass("PacketPlayOutTitle").getConstructor(new Class[] {
	                        getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0],
	                        getNMSClass("IChatBaseComponent") });
	                titlePacket = subtitleConstructor.newInstance(new Object[] { e, chatTitle });
	                sendPacket(player, titlePacket);
	            }
	            if (subtitle != null) {
	                subtitle = ChatColor.translateAlternateColorCodes('&', subtitle);
	                subtitle = subtitle.replaceAll("%player%", player.getDisplayName());

	                Object e = getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0].getField("TIMES").get(null);
	                Object chatSubtitle = getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", new Class[] { String.class }).invoke(null, new Object[] {
	                        "{\"text\":\"" + title + "\"}" });
	                Constructor<?> subtitleConstructor = getNMSClass("PacketPlayOutTitle").getConstructor(new Class[] {
	                        getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0],
	                        getNMSClass("IChatBaseComponent"),
	                        Integer.TYPE,
	                        Integer.TYPE,
	                        Integer.TYPE });
	                Object subtitlePacket = subtitleConstructor.newInstance(new Object[] { e, chatSubtitle, fadeIn, stay, fadeOut });
	                sendPacket(player, subtitlePacket);

	                e = getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0].getField("SUBTITLE").get(null);
	                chatSubtitle = getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", new Class[] { String.class }).invoke(null, new Object[] {
	                        "{\"text\":\"" + subtitle + "\"}" });
	                subtitleConstructor = getNMSClass("PacketPlayOutTitle").getConstructor(new Class[] {
	                        getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0],
	                        getNMSClass("IChatBaseComponent"),
	                        Integer.TYPE,
	                        Integer.TYPE,
	                        Integer.TYPE });
	                subtitlePacket = subtitleConstructor.newInstance(new Object[] { e, chatSubtitle, fadeIn, stay, fadeOut });
	                sendPacket(player, subtitlePacket);
	            }
	        } catch (Exception var11) {
	            var11.printStackTrace();
	        }
	    }
	   public static void sendPacket(Player player, Object packet) {
	        try {
	            Object handle = player.getClass().getMethod("getHandle", new Class[0]).invoke(player, new Object[0]);
	            Object playerConnection = handle.getClass().getField("playerConnection").get(handle);
	            playerConnection.getClass().getMethod("sendPacket", new Class[] { getNMSClass("Packet") }).invoke(playerConnection, new Object[] { packet });
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }

	    public static Class<?> getNMSClass(String name) {
	        String version = Pluginc.getInstance().getServer().getClass().getPackage().getName().split("\\.")[3];
	        try {
	            return Class.forName("net.minecraft.server." + version + "." + name);
	        } catch (ClassNotFoundException e) {
	            e.printStackTrace();
	        }
	        return null;
	    }

}
