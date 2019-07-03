package com.vampire.rpg.commands;

import java.util.Arrays;
import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import com.vampire.rpg.PlayerData;
import com.vampire.rpg.Pluginc;
import com.vampire.rpg.Rank;

public abstract class AbstractCommand extends Command implements CommandExecutor, Listener {

    public static Pluginc plugin;
    protected Rank requiredRank = Rank.MEMBER;
    public HashMap<String,SubCommand> subCommands= new HashMap<String,SubCommand>();
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command == null)
            return false;
        if (sender == null)
            return false;
        if (!command.getName().equalsIgnoreCase(getName()))
            return false;
        //IF match one of the subs
        //run OnCommand of the sub
        //return;
        //else run dis
        if (sender instanceof Player) {
            Player p = (Player) sender;
            PlayerData pd = plugin.getPD(p);
            if (pd == null || !pd.loadedSQL) {
                p.sendMessage(ChatColor.RED + "Please wait a moment while your data is loaded.");
                return true;
            }
            if (!pd.check(requiredRank)) {
                p.sendMessage(ChatColor.RED + "You don't have permission to use that command!");
                return true;
            }
            /*if(args.length>0){
            	if(subCommands.containsKey(args[0])){
            		Method m=subCommands.get(args[0]).method;
            		m.setAccessible(true);
            		java.lang.Class<?>[] types = m.getParameterTypes();
            		Object[] params=new Object[m.getParameterCount()];
            		 for (int i = 0; i < params.length; i++) {
            			 java.lang.Class<?> type = types[i];
                     /*    if (isCommandIssuer(type) && type.isAssignableFrom(issuerObject.getClass())) {
                             params[i] = issuerObject;
                         }  else if (RegisteredCommand.class.isAssignableFrom(type)) {
                             params[i] = cmd;
                         } if (String[].class.isAssignableFrom((type))) {
                             params[i] = args;
                         } else {
                             params[i] = null;
                         }
                     }
            		try {
						subCommands.get(args[0]).method.invoke(subCommands.get(args[0]),params);
					} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
						VamMessages.announce(ChatColor.RED+"Youston' we have a problem");
						e.printStackTrace();
					}

            	}
            }*/
            execute(sender, args);
            executePlayer(p, pd, args);
        } else if (sender instanceof ConsoleCommandSender) {
            execute(sender, args);
            executeConsole(sender, args);
        }
        System.out.println("Executing /" + command.getName() + " for " + sender.getName() + " with args " + Arrays.toString(args));
        return true;
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        return this.onCommand(sender, this, commandLabel, args);
    }

    /*
     * Run by both player and console executions.
     */
    public abstract void execute(CommandSender sender, String[] args);

    /*
     * Special execution for player command.
     */
    public abstract void executePlayer(Player p, PlayerData pd, String[] args);

    /*
     * Special execution for console command.
     */
    public abstract void executeConsole(CommandSender sender, String[] args);

    public AbstractCommand(String... commandNames) {
        super(commandNames[0]);
        if (commandNames.length > 1)
            for (int k = 1; k < commandNames.length; k++)
                getAliases().add(commandNames[k]);
    }
  //  public boolean isCommandIssuer(java.lang.Class<?> type) {
 //       return CommandSender.class.isAssignableFrom(type);
//    }
	/*public void onRegister() {
		final java.lang.Class<? extends AbstractCommand> self = this.getClass();
		CommandPermission perms=self.getAnnotation(CommandPermission.class);
		if(perms!=null)
			this.requiredRank=perms.value();
		CommandAlias alias=self.getAnnotation(CommandAlias.class);
		for(Method m:self.getDeclaredMethods()){
			if(m.isAnnotationPresent(SubCommandAnotation.class) && m.isAnnotationPresent(CommandPlayer.class)){
				Annotation an=m.getAnnotation(SubCommandAnotation.class);
				SubCommandAnotation sub= (SubCommandAnotation)an;
				subCommands.put(sub.value(),new SubCommand(this,sub.value(),m));
			}
		}
		getAliases().add(alias.value());
	}*/
}
