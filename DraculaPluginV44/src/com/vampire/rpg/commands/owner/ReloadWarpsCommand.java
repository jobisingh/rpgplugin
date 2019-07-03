package com.vampire.rpg.commands.owner;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.vampire.rpg.PlayerData;
import com.vampire.rpg.commands.AbstractCommand;
import com.vampire.rpg.utils.VamMessages;
import com.vampire.rpg.warps.WarpManager;

public class ReloadWarpsCommand extends AbstractCommand {

    public ReloadWarpsCommand(String... commandNames) {
        super(commandNames);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        WarpManager.loadWarps();
        sender.sendMessage("Warps reloaded.");
        VamMessages.announce(ChatColor.RED + "Warps reloaded for updates.");
    }

    @Override
    public void executePlayer(Player p, PlayerData pd, String[] args) {

    }

    @Override
    public void executeConsole(CommandSender sender, String[] args) {
    }

}
