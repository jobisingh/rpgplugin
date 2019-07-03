package com.vampire.rpg.commands;

import java.lang.reflect.Method;
import java.util.Arrays;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import com.vampire.rpg.PlayerData;
import com.vampire.rpg.Pluginc;
import com.vampire.rpg.Rank;

public  class SubCommand{
	public static Pluginc plugin;
	public AbstractCommand parentCommand;
	public Rank perms=Rank.MEMBER;
	public String name;
	public Method method;
	
	public SubCommand(AbstractCommand parent,String name,Method method){
		this.parentCommand=parent;
		this.name=name;
		this.method=method;
		this.perms=parentCommand.requiredRank;
	}
/*	public void onCommand(AbstractCommand parent,CommandSender sender, String label, String[] args) {
		this.parentCommand=parent;
        if (sender == null)
            return;
        if (sender instanceof Player) {
            Player p = (Player) sender;
            PlayerData pd = plugin.getPD(p);
            if (pd == null || !pd.loadedSQL) {
                p.sendMessage(ChatColor.RED + "Please wait a moment while your data is loaded.");
                return;
            }
            if (!pd.check(this.perms)) {
                p.sendMessage(ChatColor.RED + "You don't have permission to use that command!");
                return;
            }
            execute(sender, args);
            executePlayer(p, pd, args);
        } else if (sender instanceof ConsoleCommandSender) {
            execute(sender, args);
            executeConsole(sender, args);
        }
        System.out.println("Executing  /" + parent.getName() + "SUBCOMMAND-"+this.name+" for " + sender.getName() + " with args " + Arrays.toString(args));
        return;
    }
    /*
     * Run by both player and console executions.
     */
  /*  public abstract void execute(CommandSender sender, String[] args);

    /*
     * Special execution for player command.
     */
 /*   public abstract void executePlayer(Player p, PlayerData pd, String[] args);

    /*
     * Special execution for console command.
     */
 //   public abstract void executeConsole(CommandSender sender, String[] args);
}
