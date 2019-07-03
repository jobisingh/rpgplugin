package com.vampire.rpg.commands.owner;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.vampire.rpg.PlayerData;
import com.vampire.rpg.commands.AbstractCommand;
import com.vampire.rpg.regions.RegionManager;
import com.vampire.rpg.utils.VamMessages;

public class ReloadRegionsCommand extends AbstractCommand {

    public ReloadRegionsCommand(String... commandNames) {
        super(commandNames);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        RegionManager.reload();
        VamMessages.announce(ChatColor.RED + "Regions reloaded for updates.");
    }

    @Override
    public void executePlayer(Player p, PlayerData pd, String[] args) {

    }

    @Override
    public void executeConsole(CommandSender sender, String[] args) {
    }

}
