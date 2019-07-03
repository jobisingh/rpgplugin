package com.vampire.rpg.commands.member;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.vampire.rpg.PlayerData;
import com.vampire.rpg.commands.AbstractCommand;
import com.vampire.rpg.trinkets.TrinketManager;

public class TrinketCommand extends AbstractCommand {

    public TrinketCommand(String... commandNames) {
        super(commandNames);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
    }

    @Override
    public void executePlayer(final Player p, PlayerData pd, String[] args) {
        TrinketManager.showMenu(p, pd);
        pd.usedTrinketCommand = true;
    }

    @Override
    public void executeConsole(CommandSender sender, String[] args) {
    }

}
