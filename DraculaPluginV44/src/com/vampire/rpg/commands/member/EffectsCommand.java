package com.vampire.rpg.commands.member;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.vampire.rpg.PlayerData;
import com.vampire.rpg.commands.AbstractCommand;
import com.vampire.rpg.particles.ParticleManager;

public class EffectsCommand extends AbstractCommand {

    public EffectsCommand(String... commandNames) {
        super(commandNames);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
    }

    @Override
    public void executePlayer(Player p, PlayerData pd, String[] args) {
        ParticleManager.showMenu(p, pd);
    }

    @Override
    public void executeConsole(CommandSender sender, String[] args) {
    }

}
