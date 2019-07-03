package com.vampire.rpg.utils;


import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_10_R1.CraftServer;
import org.bukkit.craftbukkit.v1_10_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import com.mojang.authlib.GameProfile;
import com.vampire.rpg.Pluginc;

import net.minecraft.server.v1_10_R1.EntityPlayer;
import net.minecraft.server.v1_10_R1.EnumGamemode;
import net.minecraft.server.v1_10_R1.IChatBaseComponent;
import net.minecraft.server.v1_10_R1.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_10_R1.MinecraftServer;
import net.minecraft.server.v1_10_R1.PacketPlayOutChat;
import net.minecraft.server.v1_10_R1.PacketPlayOutPlayerInfo;
import net.minecraft.server.v1_10_R1.PacketPlayOutPlayerInfo.EnumPlayerInfoAction;
import net.minecraft.server.v1_10_R1.PlayerInteractManager;
import net.minecraft.server.v1_10_R1.WorldServer;

public class VamMessages {

    public static void death(String s) {
        for (Player p : Pluginc.getInstance().getServer().getOnlinePlayers()) {
            if (Pluginc.getPD(p) != null/* && SakiCore.plugin.getPD(p).getOption(SakiOption.DEATH_ALERTS)*/) {
                p.sendMessage("");
                p.sendMessage(s);
            }
        }
        Bukkit.getConsoleSender().sendMessage(s);
    }

    public static void announce(String s) {
        for (Player p : Pluginc.getInstance().getServer().getOnlinePlayers())
            p.sendMessage(s);
        Bukkit.getConsoleSender().sendMessage(s);
    }
    public static void announcePlayers(String s){
    	for (Player p : Pluginc.getInstance().getServer().getOnlinePlayers())
            p.sendMessage(s);
    }
    public static void announce(Object o) {
        String s = o.toString();
        for (Player p : Pluginc.getInstance().getServer().getOnlinePlayers())
            p.sendMessage(s);
        Bukkit.getConsoleSender().sendMessage(s);
    }

    private final static int CENTER_PX = 154;

    public static void sendCenteredMessage(Player player, String message) {
        if (message == null || message.equals(""))
            player.sendMessage("");
        player.sendMessage(getCenteredMessage(message));
    }

    public static String getCenteredMessage(String message) {
        message = ChatColor.translateAlternateColorCodes('&', message);

        int messagePxSize = 0;
        boolean previousCode = false;
        boolean isBold = false;

        for (char c : message.toCharArray()) {
            if (c == '\u00a7') {
                previousCode = true;
                continue;
            } else if (previousCode == true) {
                previousCode = false;
                if (c == 'l') {
                    isBold = true;
                    continue;
                } else
                    isBold = false;
            } else {
             //   DefaultFontInfo dFI = DefaultFontInfo.getDefaultFontInfo(c);
            //    messagePxSize += isBold ? dFI.getBoldLength() : dFI.getLength();
               // messagePxSize++;
            }
        }

        int halvedMessageSize = messagePxSize / 2;
        int toCompensate = CENTER_PX - halvedMessageSize;
    //    int spaceLength = DefaultFontInfo.SPACE.getLength() + 1;
        int compensated = 0;
        StringBuilder sb = new StringBuilder();
        while (compensated < toCompensate) {
            sb.append(" ");
        //    compensated += spaceLength;
        }
        return sb.toString() + message;
    }

    public static void sendActionBar(Player p, String msg) {
        IChatBaseComponent cbc = ChatSerializer.a("{\"text\": \"" + msg + "\"}");
        PacketPlayOutChat ppoc = new PacketPlayOutChat(cbc, (byte) 2);
        ((CraftPlayer) p).getHandle().playerConnection.sendPacket(ppoc);
    }

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

    public static void sendTabTitle(Player player, String header, String footer) {
        if (header == null) {
            header = "";
        }
        header = ChatColor.translateAlternateColorCodes('&', header);
        if (footer == null) {
            footer = "";
        }
        footer = ChatColor.translateAlternateColorCodes('&', footer);
        header = header.replaceAll("%player%", player.getDisplayName());
        footer = footer.replaceAll("%player%", player.getDisplayName());
        try {
            Object tabHeader = getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", new Class[] { String.class }).invoke(null, new Object[] {
                    "{\"text\":\"" + header + "\"}" });
            Object tabFooter = getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", new Class[] { String.class }).invoke(null, new Object[] {
                    "{\"text\":\"" + footer + "\"}" });
            Constructor<?> titleConstructor = getNMSClass("PacketPlayOutPlayerListHeaderFooter").getConstructor(new Class[] { getNMSClass("IChatBaseComponent") });
            Object packet = titleConstructor.newInstance(new Object[] { tabHeader });
            Field field = packet.getClass().getDeclaredField("b");
            field.setAccessible(true);
            field.set(packet, tabFooter);
            sendPacket(player, packet);
        } catch (Exception ex) {
            ex.printStackTrace();
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
    public static void handleTab(Player p,String name){
    //	p.get
    	GameProfile gp= new GameProfile(UUID.fromString("2e12870c-08ec-4337-bc2e-8fbf54e79853"), name);
    	MinecraftServer server=  ((CraftServer) Bukkit.getServer()).getServer();
    	WorldServer world = server.getWorldServer(0);
    	PlayerInteractManager manager = new PlayerInteractManager(world);
    	EntityPlayer player = new EntityPlayer(server, world, gp, manager);
    	player.ping=100;
    	player.playerInteractManager.setGameMode(EnumGamemode.CREATIVE);
    	PacketPlayOutPlayerInfo packet = new PacketPlayOutPlayerInfo(EnumPlayerInfoAction.ADD_PLAYER, player);
    	((CraftPlayer)p).getHandle().playerConnection.sendPacket(packet);
    }
    /*public static void setPlayerlistHeader(Player p,String header){
    	CraftPlayer cplayer=(CraftPlayer)p;
    	PlayerConnection connection = cplayer.getHandle().playerConnection;
    //	FancyMessage f=new FancyMessage("HIIII").color(ChatColor.RED).toJSONString();
    	IChatBaseComponent top= ChatSerializer.a("{\"text\": \"" + header + "\"}");
    	PacketPlayOutPlayerListHeaderFooter packet= new PacketPlayOutPlayerListHeaderFooter();
    	try{
    		Field headerfield= packet.getClass().getDeclaredField("a");
    		headerfield.setAccessible(true);
    		headerfield.set(packet, top);
    		headerfield.setAccessible(!headerfield.isAccessible());
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	connection.sendPacket(packet);
    }
    public static void setPlayerlistFooter(Player p,String footer){
    	CraftPlayer cplayer=(CraftPlayer)p;
    	PlayerConnection connection = cplayer.getHandle().playerConnection;
    	IChatBaseComponent top= ChatSerializer.a("{\"text\": \"" + footer + "\"}");
    	PacketPlayOutPlayerListHeaderFooter packet= new PacketPlayOutPlayerListHeaderFooter();
    	try{
    		Field headerfield= packet.getClass().getDeclaredField("b");
    		headerfield.setAccessible(true);
    		headerfield.set(packet, top);
    		headerfield.setAccessible(!headerfield.isAccessible());
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	connection.sendPacket(packet);
    }*/

}
