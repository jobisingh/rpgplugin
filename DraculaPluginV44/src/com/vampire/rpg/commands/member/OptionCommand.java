package com.vampire.rpg.commands.member;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.vampire.rpg.PlayerData;
import com.vampire.rpg.commands.AbstractCommand;
import com.vampire.rpg.options.OptionsManager;

public class OptionCommand extends AbstractCommand {

    public OptionCommand(String... commandNames) {
        super(commandNames);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
    }

    @Override
    public void executePlayer(final Player p, PlayerData pd, String[] args) {
        OptionsManager.openMenu(p, pd);
    }

    @Override
    public void executeConsole(CommandSender sender, String[] args) {
    }

}
