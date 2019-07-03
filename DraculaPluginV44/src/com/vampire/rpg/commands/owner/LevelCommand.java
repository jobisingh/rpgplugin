package com.vampire.rpg.commands.owner;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.vampire.rpg.PlayerData;
import com.vampire.rpg.commands.AbstractCommand;

public class LevelCommand extends AbstractCommand {

    public LevelCommand(String... commandNames) {
        super(commandNames);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
    }

    @Override
    public void executePlayer(Player p, PlayerData pd, String[] args) {
        try {
            int val = Integer.parseInt(args[0]);
            if (val < 1) {
                val = 1;
            }
            if (val > 1000000) {
                pd.sendMessage("dont set ur level to higher than 1 million :)");
                val = 1000000;
            }
            pd.level = val;
            pd.baseMaxHP = pd.getBaseMaxHP();
            p.sendMessage("updated level to " + pd.level);
        } catch (Exception e) {
            p.sendMessage("/level <lvl> <player>");
        }
    }

    @Override
    public void executeConsole(CommandSender sender, String[] args) {
    }

}
