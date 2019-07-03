package com.vampire.rpg.commands.member;

import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.vampire.rpg.PlayerData;
import com.vampire.rpg.commands.AbstractCommand;
import com.vampire.rpg.utils.VamMath;

public class RollCommand extends AbstractCommand {

    public HashMap<String, Long> lastRoll = new HashMap<String, Long>();

    public RollCommand(String... commandNames) {
        super(commandNames);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
    }

    @Override
    public void executePlayer(Player p, PlayerData pd, String[] args) {
        if (pd.party == null) {
            p.sendMessage(ChatColor.RED + "/roll can only be used when you're in a party!");
            return;
        }
        if (lastRoll.containsKey(p.getName()) && System.currentTimeMillis() - lastRoll.get(p.getName()) < 30000) {
            p.sendMessage(ChatColor.RED + "You can only use /roll once every 30 seconds.");
            return;
        }
        try {
            int max = 100;
            if (args.length == 1)
                max = Integer.parseInt(args[0].trim());
            if (max <= 0 || max > 1000000) {
                p.sendMessage(ChatColor.RED + "Die must have between 1 and 1000000 sides.");
                return;
            }
            p.sendMessage(ChatColor.GRAY + "> " + "You roll a " + max + "-sided die...");
            int result = VamMath.randInt(1, max);
            pd.party.sendMessage(ChatColor.GRAY + "> " + ChatColor.GREEN + p.getName() + ChatColor.YELLOW + " rolled a " + ChatColor.GREEN + ChatColor.BOLD + result + ChatColor.YELLOW + " on a " + ChatColor.GREEN + max + ChatColor.YELLOW + "-sided die!");
            lastRoll.put(p.getName(), System.currentTimeMillis());

        } catch (Exception e) {
            p.sendMessage(ChatColor.RED + "Invalid syntax! The command is /roll [max].");
            e.printStackTrace();
        }
    }

    @Override
    public void executeConsole(CommandSender sender, String[] args) {
    }

}
