package com.vampire.rpg.commands.owner;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.vampire.rpg.PlayerData;
import com.vampire.rpg.commands.AbstractCommand;
import com.vampire.rpg.npcs.generics.GenericNPCManager;
import com.vampire.rpg.utils.VamMessages;

public class ReloadGenericsCommand extends AbstractCommand {

    public ReloadGenericsCommand(String... commandNames) {
        super(commandNames);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        GenericNPCManager.reloadGenerics();
        VamMessages.announce(ChatColor.RED + "Generic NPCs reloaded for updates.");
    }

    @Override
    public void executePlayer(Player p, PlayerData pd, String[] args) {

    }

    @Override
    public void executeConsole(CommandSender sender, String[] args) {
    }

}
