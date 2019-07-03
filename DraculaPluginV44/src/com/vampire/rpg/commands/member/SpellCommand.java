package com.vampire.rpg.commands.member;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.vampire.rpg.PlayerData;
import com.vampire.rpg.classes.ClassManager;
import com.vampire.rpg.commands.AbstractCommand;

public class SpellCommand extends AbstractCommand {

    public SpellCommand(String... commandNames) {
        super(commandNames);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
    }

    @Override
    public void executePlayer(final Player p, PlayerData pd, String[] args) {
        ClassManager.showClassSpellMenu(p, pd);
    }

    @Override
    public void executeConsole(CommandSender sender, String[] args) {
    }

}
