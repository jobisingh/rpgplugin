package com.vampire.rpg;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.comphenix.packetwrapper.WrapperPlayServerEntityMetadata;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import com.comphenix.protocol.wrappers.WrappedWatchableObject;
import com.vampire.rpg.buffs.BuffManager;
import com.vampire.rpg.chat.ChatManager;
import com.vampire.rpg.combat.CombatManager;
import com.vampire.rpg.commands.AddMoney;
import com.vampire.rpg.commands.CleanupWorlds;
import com.vampire.rpg.commands.CommandManager;
import com.vampire.rpg.commands.Craft;
import com.vampire.rpg.commands.GenerateItem;
import com.vampire.rpg.commands.MailboxCommand;
import com.vampire.rpg.commands.PChat;
import com.vampire.rpg.commands.QuestResetCommand;
import com.vampire.rpg.commands.ReloadOres;
import com.vampire.rpg.crafting.CraftedAPI;
import com.vampire.rpg.crafting.CraftedItem;
import com.vampire.rpg.crafting.CraftingManager;
import com.vampire.rpg.drops.DropManager;
import com.vampire.rpg.economy.EconomyManager;
import com.vampire.rpg.economy.ShardManager;
import com.vampire.rpg.event.EnvironmentManager;
import com.vampire.rpg.event.block.BlockBreak;
import com.vampire.rpg.event.inventory.InventoryClick;
import com.vampire.rpg.event.player.PlayerBlock;
import com.vampire.rpg.event.player.PlayerDropItem;
import com.vampire.rpg.event.player.PlayerInteract;
import com.vampire.rpg.event.player.PlayerJoin;
import com.vampire.rpg.event.player.PlayerLeave;
import com.vampire.rpg.event.player.PlayerLogin;
import com.vampire.rpg.haybales.HaybaleManager;
import com.vampire.rpg.holograms.HologramManager;
import com.vampire.rpg.items.ItemManager;
import com.vampire.rpg.lasers.LaserManager;
import com.vampire.rpg.menus.MenuManager;
import com.vampire.rpg.mobs.EntityRegistrar;
import com.vampire.rpg.mobs.MobManager;
import com.vampire.rpg.npcs.NPCManager;
import com.vampire.rpg.npcs.generics.GenericNPCManager;
import com.vampire.rpg.options.Option;
import com.vampire.rpg.particles.ParticleManager;
import com.vampire.rpg.parties.PartyManager;
import com.vampire.rpg.professions.Profession;
import com.vampire.rpg.professions.mining.OreManager;
import com.vampire.rpg.quests.QuestManager;
import com.vampire.rpg.regions.RegionManager;
import com.vampire.rpg.shops.ShopManager;
import com.vampire.rpg.spells.SpellManager;
import com.vampire.rpg.sql.SQLManager;
import com.vampire.rpg.stealth.StealthManager;
import com.vampire.rpg.trades.TradeManager;
import com.vampire.rpg.trinkets.TrinketManager;
import com.vampire.rpg.utils.VamScheduler;
import com.vampire.rpg.utils.VamSerializer;
import com.vampire.rpg.utils.VamTicks;
import com.vampire.rpg.warps.WarpManager;


public class Pluginc extends JavaPlugin{
	public static Pluginc instance;
	public static HashMap<UUID,Inventory> backpacks = new HashMap<UUID,Inventory>();
	public static HashMap<OfflinePlayer,String> testing= new  HashMap<OfflinePlayer,String>();
	public static HashMap<OfflinePlayer,ArrayList<CraftedItem>> knownRecipes = new HashMap<OfflinePlayer,ArrayList<CraftedItem>>();
	public static ArrayList<CraftedItem> allCraftedItems;
	public static ArrayList<PlayerData> pdlist;
	private ProtocolManager protocolManager;
	
	public static String Owner_Name= "jobisingh";
	public static String Host_Name= "FILL_HERE";
	public static String server_name= "PLACEHOLDER";
    private final static HashMap<String, PlayerData> playerdatas = new HashMap<String, PlayerData>();
	public static HashMap<UUID,Timestamp> playing= new HashMap<UUID,Timestamp>();
	//Timestamp asdg = new Timestamp(asd);
	//public static ArrayList<craf> asd1;
	
	// public static Connection connection;
	//private static String host, database, username, password;
	// private static int port;
	public Integer getMoney(Player p){
		int money=0;
		PlayerInventory inv=p.getInventory();
		for(ItemStack is:inv.getContents()){
			if(is.hasItemMeta()&&is.getItemMeta().getDisplayName().equals(ChatColor.GREEN+"Aurum Fragment")){
				money+=is.getAmount();
			}
			if(is.hasItemMeta()&&is.getItemMeta().getDisplayName().equals(ChatColor.GREEN+"Aurum Ingot")){
				money+=is.getAmount()*64;
			}
		}
		return money;
	}
	 private static final int CUSTOM_NAME_INDEX = 5;
	private void processDataWatcher(WrappedDataWatcher watcher, String name) {
		// If it's being updated, change it!
		if (watcher.getObject(CUSTOM_NAME_INDEX) != null) {
			watcher.setObject(CUSTOM_NAME_INDEX, name);
		}
		// Entities that have a per-player name
		//Table<UUID, String, String> entityName = HashBasedTable.create();
	}
	public void onEnable(){
		
		instance=this;
		PluginDescriptionFile pdfile = getDescription();
		/* host ="108.166.163.130";
	     port = 3306;
	     database = "mc36669";
	     username = "mc36669";
	     password = "2d16c43b30";  */  
		Logger logger = getLogger();
		logger.info(pdfile.getName() +ChatColor.DARK_PURPLE+ " Has Been Enabled (V." + pdfile.getVersion() + ")");
		allCraftedItems = new ArrayList<CraftedItem>();
		//asd1 = new ArrayList<craf>();
		pdlist=new ArrayList<PlayerData>();
	//	Table<UUID, String, String> entityName = HashBasedTable.create();
		//takes care of showing bar above dropped item on ground
		 protocolManager = ProtocolLibrary.getProtocolManager();
		 protocolManager.addPacketListener(new PacketAdapter(this,ListenerPriority.NORMAL,PacketType.Play.Server.ENTITY_METADATA){
			 @Override
			 public void onPacketSending(PacketEvent e){
				 PacketContainer packet= e.getPacket();
				 if(e.getPacketType()==PacketType.Play.Server.ENTITY_METADATA){
					 packet.getEntityModifier(Pluginc.getInstance().getServer().getWorld("world"));
				//	 Wrappe
					 WrapperPlayServerEntityMetadata packeta = new WrapperPlayServerEntityMetadata(e.getPacket().deepClone());
	                    try {
	                        if (packeta.getEntity(e.getPlayer().getWorld()).getType() != EntityType.DROPPED_ITEM) return;
	                    } catch (NullPointerException npe) { return; }
	                    PlayerData pd=Pluginc.getPD(e.getPlayer());
	                    //REMOVE LINE BELOW
	                    if(pd == null) return;;
	                    if(!pd.getOption(Option.TAGS_ABOVE_ITEMS))
	                    	return;
	                    List<WrappedWatchableObject> meta = packeta.getMetadata();
	                    Item item=(Item) packeta.getEntity(e.getPlayer().getWorld());
	                    ItemStack stack=item.getItemStack();
	                    if(stack==null)
	                    	return;
	                    if(stack.getType()==Material.AIR)
	                    	return;
	                    if(stack.getItemMeta()==null)
	                    	return;
	                    if(stack.getItemMeta().getDisplayName()==null)
	                    	return;
	                    String name=stack.getItemMeta().getDisplayName();
	                    
	                    if(ChatColor.stripColor(name)==name)
	                    	return;
	                    WrappedWatchableObject oname = meta.get(2);
	                    oname.setValue(name);
	                    meta.set(2, oname);
	                    WrappedWatchableObject oboolean = meta.get(3);
	                    oboolean.setValue(true);
	                    meta.set(3, oboolean);
	                    packeta.setMetadata(meta);
	               //     if(e.isServerPacket()) it is server packet
	                  //  	System.out.println("Server packettt");
	             //       e.setCancelled(true);
	                 //   packeta.sendPacket(e.getPlayer());
	            //        e.setCancelled(true);
	                    try {
	                //    	((CraftPlayer)e.getPlayer()).getHandle().playerConnection.sendPacket((Packet<?>) packeta.getHandle().getHandle());
				//			protocolManager.sendServerPacket(e.getPlayer(), packeta.getHandle());
	                    	//packeta.se
	                    //	 packeta.sendPacket(e.getPlayer());
	                    	e.setPacket(packeta.getHandle());
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
							System.out.println(ChatColor.DARK_AQUA+"ERROR: PACKETS");
						};
				
				 }
			 }
		 });
		 new DropManager(this);
		 loadWorlds();
		// EntityRegistrar.registerEntities();
		// Listeners
		//getServer().getPluginManager().registerEvents(this, this);
		new SQLManager(this);
		new EnvironmentManager(this);
		new CommandManager(this);
		
		
		new ChatManager(this);
		new MenuManager(this);
		new CombatManager(this);
		new ItemManager(this);
		new MobManager(this);
		new SpellManager(this);
		new WarpManager(this);
		new StealthManager(this);
		new EconomyManager(this);
	    new ShardManager(this);
	    new RegionManager(this);
	    new NPCManager(this);
	    new QuestManager(this);
	    new BuffManager(this);
	    new TradeManager(this);
        new PartyManager(this);
        new ShopManager(this);
        new TrinketManager(this);
		new ParticleManager(this);
		new HologramManager(this);
		new HaybaleManager(this); 
		new CraftingManager(this);
		new GenericNPCManager(this);
		new OreManager(this);
	//	new LaserManager(this);
	//	new MountsManager(this);
	//	new HologramManager(this);
	//	new OreManager(this);
	//	new ShopManager(this);
	//	new HaybaleManager(this);
	//	new GenericNPCManager(this);
	//	getServer().createWorld(new WorldCreator("testing"));
		saveDefaultConfig();
		registerConfig();
		registerCommands();
		registerEvents();
		registerCraftedItems();
		//updatePlayers();
		//new CleanupManager(this);
		VamScheduler.schedule(this, new Runnable() {
			
			@Override
			public void run() {
				CraftingManager.reload();
				
			}
		});
		System.out.println(ChatColor.DARK_PURPLE+"Successfully loaded DRACULARPG.");
        VamScheduler.schedule(this, new Runnable() {
            public void run() {
                for (World w : getServer().getWorlds()) {
                    for (Chunk chunk : w.getLoadedChunks()) {
                        NPCManager.handleChunk(chunk);
                    }
                }
            }
        }, VamTicks.seconds(1));
        CraftingManager.reload();
		for(World w:Pluginc.getInstance().getServer().getWorlds()){
			for(Entity e:w.getEntities()){
				if(e.getName().equals(ChatColor.DARK_PURPLE+"Blacksmith") && !NPCManager.npcs.containsKey(e.getUniqueId())){
					e.remove();
					}
				}
			}
		//new RegionManager(this);
		}
	private void loadWorlds() {
        for (World w : getServer().getWorlds()) {
            EnvironmentManager.despawnEntities(w.getEntities().toArray(new Entity[w.getEntities().size()]));
            w.setThunderDuration(0);
            w.setWeatherDuration(0);
            w.setStorm(false);
            w.setThundering(false);           
                w.setTime(0);
        }
    }
	public void postLoad() {
        VamScheduler.schedule(this, new Runnable() {
            public void run() {
                for (World w : getServer().getWorlds()) {
                    for (Chunk chunk : w.getLoadedChunks()) {
                        NPCManager.handleChunk(chunk);
                      //  DungeonManager.handleChunk(chunk);
                        HologramManager.handleChunk(chunk);
                        EnvironmentManager.handleChunk(chunk);
                    }
                }
            }
        }, VamTicks.seconds(1));
	}
	public void onDisable(){
		instance=null;
		PluginDescriptionFile pdfile = getDescription();
		Logger logger = getLogger();
		logger.info(pdfile.getName() +ChatColor.DARK_PURPLE+ " Has Been Disabled (V." + pdfile.getVersion() + ")");
		try {
			if(SQLManager.connection!=null && !SQLManager.connection.isClosed()){
				SQLManager.connection.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		for(Entry<UUID,Inventory> entry:backpacks.entrySet()){
			if(!getConfig().contains("backpacks."+entry.getKey())){
				getConfig().createSection("backpacks."+entry.getKey());
			}
			saveContents(getConfig().getConfigurationSection("backpacks."+entry.getKey()), entry.getValue());
			saveConfig();
		}
		for (PlayerData pd : playerdatas.values()) {
            try {
            	if(pd.getPlayer()!=null && pd.getPlayer().isOnline()){
                pd.getPlayer().closeInventory();
                SQLManager.saveData(pd.getPlayer());
             //   pd.getPlayer().kickPlayer("Server is Restarting");
                pd.unload();
             //   saveData(pd.getPlayer());
               // pd.getPlayer().kickPlayer("restart nub");
            	}
                pd.unload();
            } catch (Exception e) {
                e.printStackTrace();
            }
		}
		LaserManager.kill();
		HologramManager.a();
	//	QuestManager.shutdown();
	//	CraftingManager.shutdown();
	//	ManagerInstances.cleanup();
		 try {
	            EntityRegistrar.unregisterEntities();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
		 try {
	            ParticleManager.cleanup();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
		/*List<String> s= getConfig().getStringList("players");
		for(OfflinePlayer p: knownRecipes.keySet()){
			s.add(p.getName()+": ");
			for(CraftedItem ci: knownRecipes.get(p)){
			s.add(ChatColor.stripColor(ci.getCraftedItemName())+" ");
			}
		}
		getConfig().set("players",s);
		saveConfig();*/
	}
	  public final PlayerData addPD(Player p) {
	        
	         //   if (playerdatas.containsKey(p.getUniqueId().toString()))
	        //        playerdatas.get(p.getUniqueId().toString()).unload();
	            PlayerData pd = new PlayerData(p);
	            playerdatas.put(p.getUniqueId().toString(), pd);
	            SQLManager.loadData(p);
	            return pd;
	    }
	 public static PlayerData getPD(Object o) {
	        if (o == null)
	            return null;
	        String uuid = null;
	        if (o instanceof Player) {
	            if (((Player) o).isOnline()) {
	                uuid = ((Player) o).getUniqueId().toString();
	            }
	        } else if (o instanceof UUID) {
	            uuid = o.toString();
	        } else if (o instanceof String) {
	            uuid = (String) o;
	        }
	        if (uuid == null)
	            return null;
	        return playerdatas.get(uuid);
	    }
	 
	
	public static HashMap<Profession, Integer[]> deserializeProfessions(String s) {
		HashMap<Profession, Integer[]> temp=new HashMap<Profession, Integer[]>();
		String[] t1=s.split(",");
		for(String sa:t1){
			String[] sas=sa.split("-");
			String[] ad=sas[1].split(":");
			temp.put(Profession.valueOf(sas[0]), new Integer[]{Integer.valueOf(ad[0]),Integer.valueOf(ad[1])});
		}
		return temp;
	}
	public void registerCommands(){
		getCommand("addmoney").setExecutor(new AddMoney());
		getCommand("craft").setExecutor(new Craft());
		getCommand("partychat").setExecutor(new PChat());
		getCommand("gene").setExecutor(new GenerateItem());
		getCommand("rlworlds").setExecutor(new CleanupWorlds());
		getCommand("mailbox").setExecutor(new MailboxCommand());
		getCommand("rlores").setExecutor(new ReloadOres());
		getCommand("resetquest").setExecutor(new QuestResetCommand());
	//	getCommand("rpgmenu").setExecutor(new MenuCommand());
	}
	public void registerEvents(){
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(new PlayerDropItem(), this);
		pm.registerEvents(new InventoryClick(),this);
		pm.registerEvents(new PlayerInteract(), this);
		pm.registerEvents(new PlayerLogin(this),this);
		pm.registerEvents(new PlayerJoin(), this);
		pm.registerEvents(new PlayerLeave(), this);
		pm.registerEvents(new PlayerBlock(), this);
		pm.registerEvents(new BlockBreak(), this);
	}
	
	  public final <T> T getInstance(Class<T> clazz) {
	        T inst = ManagerInstances.getInstance(clazz);
	        if (inst == null) {
	            System.out.println("WARNING: " + clazz + " instance is null!");
	        }
	        return inst;
	    }
	public void registerCraftedItems(){
		CraftedAPI.registerCraftedItems(this);
	}
	private void registerConfig(){
		saveDefaultConfig();
	}
	public static ArrayList<CraftedItem> getAllCraftedItems(){
		return allCraftedItems;
	}
	public static Pluginc getInstance(){
		return instance;
	}
	
	public void saveItem(ConfigurationSection section,ItemStack i){
		section.set("info", VamSerializer.serializeItemStack(i));
	}
	public static String desirialise(Inventory inv){
		StringBuilder sb = new StringBuilder();
		if(inv==null){
			sb.append("null");
			return sb.toString();
		}
				
		if(inv.getSize()==0){
			sb.append("null");
			return sb.toString();
		}
	    ItemStack[] arr = inv.getContents();
	    for (int k = 0; k < arr.length; k++) {
	        if (arr[k] != null) {
	            sb.append(k);
	            sb.append("::");
	            sb.append(VamSerializer.serializeItemStack(arr[k]));
	            sb.append("@");
	        }
	    }
	    String s = sb.toString().trim();
	    if (s.endsWith("@"))
	        s = s.substring(0, s.length() - 1);
	    
	    return s;
	    //section.set("info", s);
	
	}
	public void saveContents(ConfigurationSection section,Inventory i){
	StringBuilder sb = new StringBuilder();
    ItemStack[] arr = i.getContents();
    for (int k = 0; k < arr.length; k++) {
        if (arr[k] != null) {
            sb.append(k);
            sb.append("::");
            sb.append(VamSerializer.serializeItemStack(arr[k]));
            sb.append("@");
        }
    }
    String s = sb.toString().trim();
    if (s.endsWith("@"))
        s = s.substring(0, s.length() - 1);
    section.set("info", s);
	}
	
	public static void loadBank(String s,Inventory inv){
		HashMap<Integer, ItemStack> map = new HashMap<Integer, ItemStack>();
		if (s == null)
        return;
		if(s.equals("null"))
			return;
    String[] data = s.split("@");
    if (data.length == 0 || (data.length == 1 && data[0].equals("")))
        return;
    for (String temp : data) {
        try {
            // don't use split in case item serialization contains ::
            String a = temp.substring(0, temp.indexOf("::"));
            String b = temp.substring(temp.indexOf("::") + "::".length());
            int k = Integer.parseInt(a);
            ItemStack item = VamSerializer.deserializeItemStack(b);
           map.put(k, item);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
   // bank.clear();
   for (Entry<Integer, ItemStack> e : map.entrySet()) {
      inv.setItem(e.getKey(), e.getValue());
    }
	}
	public ItemStack loadContents(ConfigurationSection section,Inventory in){
		HashMap<Integer, ItemStack> map = new HashMap<Integer, ItemStack>();
		String s= section.getString("info");
        if (s == null)
            return null;
        String[] data = s.split("@");
        if (data.length == 0 || (data.length == 1 && data[0].equals("")))
            return null;
        for (String temp : data) {
            try {
                // don't use split in case item serialization contains ::
                String a = temp.substring(0, temp.indexOf("::"));
                String b = temp.substring(temp.indexOf("::") + "::".length());
                int k = Integer.parseInt(a);
                ItemStack item = VamSerializer.deserializeItemStack(b);
               map.put(k, item);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
       // bank.clear();
       for (Entry<Integer, ItemStack> e : map.entrySet()) {
          in.setItem(e.getKey(), e.getValue());
        }
		//return VamSerializer.deserializeItemStack(section.getString("info"));
		return null;
	}
	public static HashMap<OfflinePlayer,ArrayList<CraftedItem>> getKnownRecipes(){
		return knownRecipes;
	}

}
