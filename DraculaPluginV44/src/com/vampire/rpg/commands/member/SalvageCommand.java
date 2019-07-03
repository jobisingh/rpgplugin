package com.vampire.rpg.commands.member;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.vampire.rpg.PlayerData;
import com.vampire.rpg.commands.AbstractCommand;
import com.vampire.rpg.economy.EconomyManager;

public class SalvageCommand extends AbstractCommand {

    public SalvageCommand(String... commandNames) {
        super(commandNames);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
    }

    @Override
    public void executePlayer(final Player p, PlayerData pd, String[] args) {
       // if (p.getWorld().getName().equalsIgnoreCase(SakiRPG.TUTORIAL_WORLD)) {
       //     p.sendMessage(ChatColor.GRAY + "> " + ChatColor.GREEN + "Sorry! You can't use this command in the tutorial!");
      //     p.sendMessage(ChatColor.GRAY + "> " + ChatColor.AQUA + "Please finish the tutorial first. Feel free to ask for help!");
     //       return;
      //  }
        p.sendMessage("The shard system is incomplete!");
        p.sendMessage("In the future, shards will be used to upgrade equipment, so make sure to stock up!");
        EconomyManager.openSalvageMenu(p, pd);
    }

    @Override
    public void executeConsole(CommandSender sender, String[] args) {
    }

}
