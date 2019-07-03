package com.vampire.rpg.commands.owner;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.vampire.rpg.PlayerData;
import com.vampire.rpg.commands.AbstractCommand;
import com.vampire.rpg.holograms.HologramManager;
import com.vampire.rpg.utils.VamMessages;

public class ReloadHologramsCommand extends AbstractCommand {

    public ReloadHologramsCommand(String... commandNames) {
        super(commandNames);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        HologramManager.reload();
        VamMessages.announce(ChatColor.RED + "Holograms reloaded for updates.");
    }

    @Override
    public void executePlayer(Player p, PlayerData pd, String[] args) {

    }

    @Override
    public void executeConsole(CommandSender sender, String[] args) {
    }

}
