package com.vampire.rpg.commands;

import java.lang.reflect.Field;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandMap;
import org.bukkit.craftbukkit.libs.jline.internal.Log;
import org.bukkit.craftbukkit.v1_10_R1.CraftServer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import com.vampire.rpg.AbstractManager;
import com.vampire.rpg.Pluginc;
import com.vampire.rpg.Rank;
import com.vampire.rpg.commands.member.BankCommand;
import com.vampire.rpg.commands.member.BlueprintCommand;
import com.vampire.rpg.commands.member.ClassCommand;
import com.vampire.rpg.commands.member.EffectsCommand;
import com.vampire.rpg.commands.member.IgnoreCommand;
import com.vampire.rpg.commands.member.MenuCommand;
import com.vampire.rpg.commands.member.OptionCommand;
import com.vampire.rpg.commands.member.PartyCommand;
import com.vampire.rpg.commands.member.PingCommand;
import com.vampire.rpg.commands.member.QuestCommand;
import com.vampire.rpg.commands.member.ResetSPCommand;
import com.vampire.rpg.commands.member.RollCommand;
import com.vampire.rpg.commands.member.SalvageCommand;
import com.vampire.rpg.commands.member.ShardCommand;
import com.vampire.rpg.commands.member.SpellCommand;
import com.vampire.rpg.commands.member.TradeCommand;
import com.vampire.rpg.commands.member.TrinketCommand;
import com.vampire.rpg.commands.member.WhisperCommand;
import com.vampire.rpg.commands.owner.AnnounceCommand;
import com.vampire.rpg.commands.owner.DeleteDatabaseCommand;
import com.vampire.rpg.commands.owner.GiveBadgeCommand;
import com.vampire.rpg.commands.owner.LevelCommand;
import com.vampire.rpg.commands.owner.ReloadBlacksmithsCommand;
import com.vampire.rpg.commands.owner.ReloadGenericsCommand;
import com.vampire.rpg.commands.owner.ReloadHayCommand;
import com.vampire.rpg.commands.owner.ReloadHologramsCommand;
import com.vampire.rpg.commands.owner.ReloadItemsCommand;
import com.vampire.rpg.commands.owner.ReloadMobsCommand;
import com.vampire.rpg.commands.owner.ReloadQuestsCommand;
import com.vampire.rpg.commands.owner.ReloadRegionsCommand;
import com.vampire.rpg.commands.owner.ReloadShopsCommand;
import com.vampire.rpg.commands.owner.ReloadWarpsCommand;
import com.vampire.rpg.commands.owner.RemoveBadgeCommand;
import com.vampire.rpg.commands.owner.SetRankCommand;
import com.vampire.rpg.commands.owner.ShadowMuteCommand;
import com.vampire.rpg.commands.owner.StartDatabaseCommand;
import com.vampire.rpg.commands.owner.ViewBankCommand;

public class CommandManager extends AbstractManager {

    public static CommandMap cmap = null;

    public CommandManager(Pluginc plugin) {
        super(plugin);
    }

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent e) {
        if (e.getMessage().split(" ")[0].contains(":")) {
            e.setCancelled(true);
            e.getPlayer().sendMessage(ChatColor.RED + "Hidden syntax is disabled.");
        }
    }

    @Override
    public void initialize() {
        try {
            Field f = CraftServer.class.getDeclaredField("commandMap");
            f.setAccessible(true);
            cmap = (CommandMap) f.get(plugin.getServer());
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (cmap == null) {
            Log.error("FATAL ERROR: COULD NOT RETRIEVE COMMAND MAP.");
            plugin.getServer().shutdown();
            return;
        }
        AbstractCommand.plugin = plugin;
        // Member
        register(Rank.MEMBER,new PingCommand("ping","pingme","pingo"));
        register(Rank.MEMBER,new MenuCommand("rpgmenu"));
        register(Rank.MEMBER, new BankCommand("bank"));
        register(Rank.MEMBER, new ClassCommand("class", "classes"));
        register(Rank.MEMBER, new IgnoreCommand("ignore"));
        register(Rank.MEMBER, new OptionCommand("option", "options", "opt", "o"));
        register(Rank.MEMBER, new ResetSPCommand("resetsp"));
        register(Rank.MEMBER, new SalvageCommand("salvage", "sell"));
        register(Rank.MEMBER, new ShardCommand("shard", "shards", "eco", "econ", "economy", "bal", "balance", "gold", "money"));
        register(Rank.MEMBER, new TradeCommand("trade"));
        register(Rank.MEMBER, new WhisperCommand("w", "whisper", "tell", "pm", "message", "msg"));
        register(Rank.MEMBER, new QuestCommand("quest", "q", "quests"));
        register(Rank.MEMBER, new EffectsCommand("e", "effect", "effects", "particle", "particles"));
        register(Rank.MEMBER, new RollCommand("roll", "rtd"));
        register(Rank.MEMBER, new SpellCommand("spell", "spells", "magic", "sp", "spl", "spls"));
        register(Rank.MEMBER, new PartyCommand("party", "p"));
        register(Rank.MEMBER, new TrinketCommand("trinket", "t"));
        //Owner
        
        //Reload commands
        
        register(Rank.OWNER, new ReloadItemsCommand("rlitems","reloaditems"));
        register(Rank.OWNER, new ReloadShopsCommand("rlshops","reloadshops"));
        register(Rank.OWNER, new ReloadHayCommand("rlhay","reloadhay"));
        register(Rank.OWNER, new ReloadGenericsCommand("rlgenerics","reloadgenerics"));
        register(Rank.OWNER, new ReloadHologramsCommand("rlholo","rlholograms","realodholograms"));        
        register(Rank.OWNER, new ReloadMobsCommand("rlmobs","reloadmobs"));
        register(Rank.OWNER, new ReloadQuestsCommand("rlquests","reloadquests"));
        register(Rank.OWNER, new ReloadRegionsCommand("rlregions","reloadregions"));
        register(Rank.OWNER, new StartDatabaseCommand("startdb"));
        register(Rank.OWNER, new RemoveBadgeCommand("removebadge"));
        register(Rank.OWNER, new ViewBankCommand("viewbank"));
        register(Rank.OWNER, new ShadowMuteCommand("shadowmute"));
        register(Rank.OWNER, new GiveBadgeCommand("givebadge"));
        register(Rank.OWNER, new BlueprintCommand("blueprint"));
        register(Rank.OWNER, new AnnounceCommand("announce"));
        register(Rank.OWNER, new SetRankCommand("setrank", "setrankalias"));
        register(Rank.OWNER, new ReloadBlacksmithsCommand("rlcrafting","rlblacksmiths","reloadcrafting"));
        register( Rank.OWNER, new LevelCommand("level","setlevel","setlvl"));
        register(Rank.OWNER, new DeleteDatabaseCommand("deletedatabase","deletedb"));
        register(Rank.OWNER, new ReloadWarpsCommand("rlwarps","reloadwarps"));
   /*     // Member
        register(Rank.MEMBER, new TestCommand("testcommand"));
        register(Rank.MEMBER, new PetCommand("pet", "pets"));
        // Mod
        register(SakiCore.TEST_REALM ? Rank.MEMBER : Rank.MOD, new ChangeWorldCommand("changeworld", "cw"));
        register(SakiCore.TEST_REALM ? Rank.MEMBER : Rank.MOD, new TeleportCommand("teleport", "tp"));
        register(Rank.MOD, new TeleportHereCommand("tphere", "teleporthere"));
        register(Rank.MOD, new BanCommand("ban"));
        register(Rank.MOD, new GetIPCommand("getip"));
        register(Rank.MOD, new PardonCommand("pardon"));
        register(Rank.HELPER, new KickCommand("kick")); // manually disable for Builder rank
        register(Rank.HELPER, new MuteCommand("mute")); // manually disable for Builder rank
        register(SakiCore.TEST_REALM ? Rank.MEMBER : Rank.MOD, new BackCommand("back"));
        register(SakiCore.TEST_REALM ? Rank.MEMBER : Rank.MOD, new FlyCommand("fly"));
        register(SakiCore.TEST_REALM ? Rank.MEMBER : Rank.MOD, new FlySpeedCommand("flyspeed"));

        // Admin
        register(Rank.ADMIN, new BanIPCommand("banip", "ipban"));
        // Owner
        register(Rank.OWNER, new SetRankCommand("setrank", "setrankalias"));
        register(Rank.OWNER, new GiveBadgeCommand("givebadge"));
        register(Rank.OWNER, new RemoveBadgeCommand("removebadge"));
        register(Rank.OWNER, new SetInventoryCommand("setinv", "setinventory"));
        register(Rank.OWNER, new ViewInventoryCommand("viewinventory", "checkinventory", "seeinv", "seeinventory"));
        register(Rank.OWNER, new GiveUnlockCommand("giveunlock"));
        register(Rank.OWNER, new RemoveUnlockCommand("removeunlock"));*/

    }
    
    protected void register(Rank rank, AbstractCommand command) {
        command.requiredRank = rank;
        cmap.register("", command);
        plugin.getServer().getPluginManager().registerEvents(command, plugin);
    }
}