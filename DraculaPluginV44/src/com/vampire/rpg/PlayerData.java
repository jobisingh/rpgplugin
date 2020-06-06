package com.vampire.rpg;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.EntityEffect;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.craftbukkit.libs.jline.internal.Log;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import org.bukkit.util.Vector;

import com.vampire.rpg.badges.Badge;
import com.vampire.rpg.buffs.PlayerBuffHandler;
import com.vampire.rpg.classes.ClassType;
import com.vampire.rpg.combat.DamageType;
import com.vampire.rpg.combat.HealType;
import com.vampire.rpg.crafting.CraftedItem;
import com.vampire.rpg.items.EquipType;
import com.vampire.rpg.items.ItemBalance;
import com.vampire.rpg.items.ItemManager;
import com.vampire.rpg.items.stats.StatAccumulator;
import com.vampire.rpg.mailboxes.MailboxItem;
import com.vampire.rpg.menus.MenuGeneralRunnable;
import com.vampire.rpg.menus.MenuManager;
import com.vampire.rpg.mobs.MobAI;
import com.vampire.rpg.mobs.MobData;
import com.vampire.rpg.mobs.MobManager;
import com.vampire.rpg.mounts.Mount;
import com.vampire.rpg.options.Option;
import com.vampire.rpg.options.OptionsList;
import com.vampire.rpg.options.OptionsManager;
import com.vampire.rpg.particles.EffectName;
import com.vampire.rpg.particles.ParticleManager;
import com.vampire.rpg.parties.Party;
import com.vampire.rpg.parties.PartyManager;
import com.vampire.rpg.pets.PetAI;
import com.vampire.rpg.pets.PetType;
import com.vampire.rpg.professions.Profession;
import com.vampire.rpg.quests.MobTrackerInfo;
import com.vampire.rpg.quests.Quest;
import com.vampire.rpg.quests.QuestManager;
import com.vampire.rpg.regions.Region;
import com.vampire.rpg.regions.RegionManager;
import com.vampire.rpg.regions.areas.TriggerArea;
import com.vampire.rpg.spells.Spell;
import com.vampire.rpg.spells.SpellBookPriest;
import com.vampire.rpg.spells.SpellManager;
import com.vampire.rpg.spells.SpellManager.CastState;
import com.vampire.rpg.spells.Spellbook;
import com.vampire.rpg.spells.SpellbookArcher;
import com.vampire.rpg.spells.SpellbookAssassin;
import com.vampire.rpg.spells.SpellbookBarbarian;
import com.vampire.rpg.spells.SpellbookCrusader;
import com.vampire.rpg.spells.SpellbookPaladin;
import com.vampire.rpg.spells.SpellbookReaper;
import com.vampire.rpg.spells.SpellbookWizard;
import com.vampire.rpg.spells.VillagerSpellbook;
import com.vampire.rpg.spells.assassin.DoubleStab;
import com.vampire.rpg.spells.assassin.ShadowStab;
import com.vampire.rpg.spells.assassin.SinisterStrike;
import com.vampire.rpg.spells.barbarian.Berserk;
import com.vampire.rpg.spells.barbarian.Wrath;
import com.vampire.rpg.spells.crusader.SwordSpirit;
import com.vampire.rpg.spells.paladin.ChantOfNecessarius;
import com.vampire.rpg.spells.paladin.HolyGuardian;
import com.vampire.rpg.spells.paladin.WalkingSanctuary;
import com.vampire.rpg.spells.priest.GodsHand;
import com.vampire.rpg.spells.reaper.DarkBargain;
import com.vampire.rpg.stealth.StealthManager;
import com.vampire.rpg.trinkets.Trinket;
import com.vampire.rpg.trinkets.TrinketStat;
import com.vampire.rpg.utils.VamMath;
import com.vampire.rpg.utils.VamMessages;
import com.vampire.rpg.utils.VamParticles;
import com.vampire.rpg.utils.VamScheduler;
import com.vampire.rpg.utils.VamScheduler.Halter;
import com.vampire.rpg.utils.VamSerializer;
import com.vampire.rpg.utils.VamTicks;
import com.vampire.rpg.utils.fanciful.FancyMessage;
import com.vampire.rpg.warps.WarpLocation;
import com.vampire.rpg.warps.WarpManager;

import de.slikey.effectlib.util.ParticleEffect;
import de.slikey.effectlib.util.ParticleEffect.BlockData;

public class PlayerData {
	private UUID uuid;
	private String name;
	public int level;
	public long exp;
	public Inventory bank;
	public int mana =10;
	public Rank rank;
	public static Pluginc plugin= Pluginc.getInstance();
    public static final int MAX_MANA = 10;

	public ClassType classType;
	public ArrayList<CraftedItem> knownRecipes;
	public Boolean loadedSQL = false;
	 boolean finishedLoadEquips=false;
	 public boolean unloaded=false;
	public Timestamp firstplayed;
	 // Various binary states associated with a player. These are like unlocks, but less well enumerated (often written in configs).
    private HashSet<String> states = new HashSet<String>();

	 public static Scoreboard board = null;
	 
	 private BossBar bossBar;
	 
	 public boolean dead;
	//combat
	  public int damageLow;
	  public int damageHigh;
	  
	  public int hp, baseMaxHP;
	  public int maxHP, defense;
	  public double maxHPMultiplier, defenseMultiplier;
	  
	  public int rarityFinder = 0;
	  public int manaRegenRate = 0;

	  public float speed; //default mc speed is 0.2
	  public double critChance;
	  public double critDamage = 1.50;
	  
	  public EffectName activeEffect = null;
	  
	  private PlayerBuffHandler buffHandler = new PlayerBuffHandler();
	   
	  public double spellDamage = 1.0, attackDamage = 1.0;
	  public double lifesteal = 0;
	  public double hpRegen = 1.0;
	  public double attackSpeed = 1.0;
	  private ArrayList<ItemStack> armor = new ArrayList<ItemStack>();
	  				//quest id     //path id //questPart id
	  public HashMap<String, Integer> questProgress = new HashMap<String, Integer>();
	  /*
	     * Party stuff
	     */

	    public Party party = null;
	    public Party invitedParty = null;

	  //Regions
	  public long lastRegionCheck;
	  public Region region;
	  public int safespotCounter = 0;
	  
	  public TriggerArea lastArea;
	  public long lastAreaCheck;
	  public long lastAreaTriggered;
	  //counters
	  public int mobKills, playerKills, deaths, bossKills;
	  
	  private boolean stealthed = false;
	  private long lastHurt;
	  
	  private HashMap<String, Integer> armorSetCounter = new HashMap<String, Integer>();
	  private String lastMessagedSetName = "";
	    private int lastMessagedSetCount = 0;
	    
	    private boolean hasSetBonusHP = false;
	    private boolean hasSetBonusDamage = false;
	    private ItemStack lastSetItem = null;

	   public int sp = 1;
	   public HashMap<Spell, Integer> spellLevels = new HashMap<Spell, Integer>();
	   public Spell spell_RLL = null;
	   public Spell spell_RLR = null;
	   public Spell spell_RRL = null;
	   public Spell spell_RRR = null;
	   
	   public Trinket trinket;
	    private Trinket lastTrinket;
	    public long nextTrinketCast;
	    public HashMap<Trinket, Long> trinketExp;

	   public static final long MENU_CLICK_RATE_LIMIT = 200;
	    public long lastMenuClick = 0; //prevent double clicking
	    
	  private long lastDamaged = 0;
	    public long lastDamagedGlide = 0;
	    public long lastDamagedGlideMessage = 0;
	    private long lastEnvironmentalDamaged = 0;
	    private long lastDamagedNonEnvironmental = 0;
	    private PlayerData lastDamagerPlayerData = null;
	    private String lastDamagerPlayer = "";
	    private long lastDamagerPlayerTime = 0;
	    private String lastDamagerMob = "";
	    private long lastDamagerMobTime = 0;
	    private DamageType lastDamageType = DamageType.NORMAL;
	    
	    public long lastKnockback = 0;
	    public long lastArrowShot = 0;
	    public long lastPotionThrown = 0;
	    public long lastWandShot = 0;
	    
	    public Inventory currentModifiableInventory;
	    public MenuGeneralRunnable<?> currentMenuClickRunnable;
	    
	    // <mob ID -> <mob ID (checked with contains) -> count>> 
	    private HashMap<String, HashMap<String, Integer>> mobCounter = new HashMap<String, HashMap<String, Integer>>();
	 //   public Date
	    public OptionsList optionsList;
	    
	    // Badges owned by this player
	    public HashSet<Badge> badges = new HashSet<Badge>();
	    
	    // Ignored people (not persistent)
	    public ArrayList<String> ignored = new ArrayList<String>();
	    
	    //Mailbox stuff
	    public ArrayList<MailboxItem> Mailbox=new ArrayList<MailboxItem>();
	    public Inventory mailboxInv;
	    
	    //Mounts
	    public ArrayList<Mount> mounts= new ArrayList<Mount>();
	    public boolean riding = false;
	    
	    //Professions
	    public HashMap<Profession,Integer[]> professions= new HashMap<Profession,Integer[]>();
	    
	    //Banners
	    public ItemStack banner;
	    public ItemStack replaceBanner;
	public PlayerData(Player p){
		this.uuid=p.getUniqueId();
		this.name= p.getName();
		Pluginc.pdlist.add(this);
		halters = new ArrayList<Halter>();
	}
	public  UUID getUUID(){
		return uuid;
	}
	public void PostLoad(){
		 dead = false;
		 baseMaxHP = getBaseMaxHP();
		updateEquipment();
		hp = baseMaxHP + maxHP;
		//hpDisplayAndRegenTask();
		 manaRegenTask();
         hpDisplayAndRegenTask();
         specialEffectsTask();
         givePerms();
         updateHealthManaDisplay();
		 RegionManager.checkRegion(getPlayer());
		 updateHealthManaDisplay();
		 updateEquipment();
		VamMessages.sendTabTitle(getPlayer(), ChatColor.RED+"Fallen Gate Dev Server", ChatColor.BLUE+"Play now on "+ChatColor.GRAY+ "(put ip here)");		
		// VamMessages.handleTab(getPlayer(), "TapL");
		//PacketPlayOutPlayerInfo packet= new PacketPlayOutPlayerInfo(EnumPlayerInfoAction.);
		/* PlayerList list = new PlayerList(getPlayer(),0);
	//	 list.addValue(0, ChatColor.GREEN+ "Friends", UUID.fromString("ca7be434-85fd-4caf-acf5-56b515b4a168"));
		 list.initTable();
		list.removePlayer(getPlayer());
		 list.addValue(1, ChatColor.GREEN+ "Friends", UUID.fromString("ca7be434-85fd-4caf-acf5-56b515b4a168"));
		 list.addValue(20, ChatColor.GREEN+"Party",UUID.fromString("069a79f4-44e9-4726-a5be-fca90e38aaf5"));*/
	//	 list.updateSlot(0,"Top left");
	//	 list.updateSlot(19,"Bottom left");
	//	 list.updateSlot(60,"Top right");
		// list.updateSlot(79,"Bottom right");
		// list.add
		 // for(int i=0;i<76;i++){
		// VamMessages.handleTab(getPlayer(),"");
		// }
		 // VamMessages.setPlayerlistHeader(getPlayer(), ChatColor.RED+"TEST 1");
	//	 VamMessages.setPlayerlistFooter(getPlayer(), ChatColor.BLUE+"im a gud coder");

		//hp = baseMaxHP + maxHP;
	//	hp = loadedHP;
	}
	private void givePerms() {
		for(String s:this.rank.getPermissions()){
			try{
				getPlayer().addAttachment(Pluginc.getInstance(), s, true);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
	}
	public Boolean HasLoadedSQL(){
		return loadedSQL;
	}
	public String getName() {
        return name;
    }
	public boolean check(Rank other) {
        return rank.checkIsAtLeast(other);
    }
	public ChatColor getChatNameColor() {
        if (rank == null)
            return ChatColor.GRAY;
        return rank.nameColor;
    }

    public String getChatRankPrefix() {
        if (rank == null)
            return "";
        return rank.getChatRankDisplay();
    }

    public String getChatRankPrefixNoColor() {
        if (rank == null)
            return "";
        return rank.chatPrefix;
    }
    public ChatColor getChatColor() {
        if (rank == null)
            return ChatColor.GRAY;
        return rank.chatColor;
    }
	 public boolean isIgnoring(PlayerData other) {
	        String name = other.getPlayer().getName();
	        return ignored.contains(name.toLowerCase());
	    }

	    public boolean isIgnoring(Player other) {
	        String name = other.getName();
	        return ignored.contains(name.toLowerCase());
	    }
	public void updateEquipment(){
		ItemStack item;
		Player p= getPlayer();
		if(p==null || !p.isValid())
			return;
		StatAccumulator.clearStats(this);
		 armor.clear();
		 item= p.getEquipment().getItemInMainHand();
		 if (item != null && EquipType.isWeapon(item)) {
	            addStats(item, true);	          
		 }
		 armor.add(p.getEquipment().getHelmet());
	     armor.add(p.getEquipment().getChestplate());
	     armor.add(p.getEquipment().getLeggings());
	     armor.add(p.getEquipment().getBoots());
	     for (ItemStack i : armor) {
	            addStats(i, false);
	            addSet(i);
	        }
	     checkEquipmentSet();
	     checkSpellStatEffects();
	     if (trinket == null) {
	            p.getInventory().setItemInOffHand(null);
	            lastTrinket = null;
	        } else {
	            if (lastTrinket == null || lastTrinket != trinket) {
	                p.getInventory().setItemInOffHand(trinket.getEquipItem(this));
	                lastTrinket = trinket;
	            }
	            int level = Trinket.getTrinketLevel(getTrinketExp(trinket));
	            for (TrinketStat ts : trinket.stats) {
	                ts.apply(p, this, level);
	            }
	            trinket.updateTrinket(this, p.getInventory().getItemInOffHand());
	        }
	  //   if(p.getInventory().getItemInMainHand()!= null)
	  //  	 addStats(p.getInventory().getItemInMainHand(), true);
	     StatAccumulator.finalizeStats(this);
	     finishedLoadEquips = true;
	}
	private void addSet(ItemStack item) {
        if (item == null)
            return;
        ItemMeta im = item.getItemMeta();
        if (im == null)
            return;
        if (!im.hasDisplayName())
            return;
        if (im.getDisplayName().startsWith("*"))
            return;
        String name = im.getDisplayName();
        for (String s : ItemBalance.SET_PREFIXES) {
            if (name.contains(s)) {
                if (armorSetCounter.containsKey(s))
                    armorSetCounter.put(s, armorSetCounter.get(s) + 1);
                else
                    armorSetCounter.put(s, 1);
                break;
            }
        }
    }
	
	private void checkEquipmentSet() {
        Player p = getPlayer();
        hasSetBonusDamage = false;
        hasSetBonusHP = false;
        if (!armorSetCounter.isEmpty()) {
            String currName = null;
            int currCount = 0;
            for (Entry<String, Integer> e : armorSetCounter.entrySet()) {
                if (e.getValue() >= 2 && e.getValue() > currCount) { //only message if there are at least two pieces
                    currName = e.getKey();
                    currCount = e.getValue();
                }
            }
            if (currName != null && (!currName.equals(lastMessagedSetName) || currCount != lastMessagedSetCount)) {
                lastMessagedSetName = currName;
                lastMessagedSetCount = currCount;
                if (getOption(Option.SET_NOTIFICATION)) {
                    p.sendMessage("");
                    p.sendMessage(ChatColor.GRAY + ">> " + ChatColor.YELLOW + "You are currently using " + ChatColor.GREEN + currCount + " pieces of " + ChatColor.LIGHT_PURPLE + currName + ChatColor.YELLOW + " equipment.");
                    if (currCount < 4)
                        p.sendMessage(ChatColor.GRAY + ">> " + ChatColor.YELLOW + "Use " + ChatColor.AQUA + "4" + ChatColor.YELLOW + " pieces of the " + ChatColor.LIGHT_PURPLE + currName + ChatColor.YELLOW + " set for " + ChatColor.GOLD + "+10%" + ChatColor.YELLOW + " HP.");
                    else
                        p.sendMessage(ChatColor.GRAY + ">> " + ChatColor.GOLD + "You currently have " + ChatColor.YELLOW + "+10%" + ChatColor.GOLD + " HP from your " + ChatColor.LIGHT_PURPLE + currName + ChatColor.GOLD + " set.");
                    if (currCount < 5)
                        p.sendMessage(ChatColor.GRAY + ">> " + ChatColor.YELLOW + "Use " + ChatColor.AQUA + "5" + ChatColor.YELLOW + " pieces of the " + ChatColor.LIGHT_PURPLE + currName + ChatColor.YELLOW + " set for " + ChatColor.GOLD + "+15%" + ChatColor.YELLOW + " Damage.");
                    else
                        p.sendMessage(ChatColor.GRAY + ">> " + ChatColor.GOLD + "You currently have " + ChatColor.YELLOW + "+15%" + ChatColor.GOLD + " Damage from your " + ChatColor.LIGHT_PURPLE + currName + ChatColor.GOLD + " set.");
                }
            }
            if (currCount >= 4) {
                hasSetBonusHP = true;
            }
            if (currCount >= 5) {
                hasSetBonusDamage = true;
            }
        }
    }

	
	public boolean inCombat() {
        return System.currentTimeMillis() - lastDamagedNonEnvironmental < 10000;
    }
	public boolean damage(int damageAmount, Entity damager, DamageType damageType) {
		//VamMessages.announce("TESTING DAMAGE");
        return damage(damageAmount, damager, damageType, false);
    }

    public boolean damage(int damageAmount, Entity damager, DamageType damageType, boolean crit) {
        if (!loadedSQL)
            return false;
        if (dead)
            return false;
        // environmental attack speed limiter
        if (System.currentTimeMillis() - lastEnvironmentalDamaged < 600 && (damageType == DamageType.ENVIRONMENTAL_LAVA || damageType == DamageType.ENVIRONMENTAL_DROWNING || damageType == DamageType.ENVIRONMENTAL_FALL))
            return false;
        Player p = getPlayer();
        // check if player can be attacked
        if (p == null || !p.isValid())
            return false;
        if (!this.isPVE())
            return false;
        if (damager != null) {
            if (damager instanceof Player) { // attacked by player
                PlayerData damagerPD = Pluginc.getPD((Player) damager);
                if (!((Player) damager).isOnline() || damagerPD == null)
                    return false;
                if (!damagerPD.isPVP() || !this.isPVP() || PartyManager.sameParty((Player) damager, getPlayer()))
                    return false;
                if (damagerPD.riding)
                    return false;
                if (System.currentTimeMillis() - lastDamaged < damagerPD.getAttackSpeed() && damageType == DamageType.NORMAL)
                    return false;
            } else if (System.currentTimeMillis() - lastDamaged < 600 && damageType == DamageType.NORMAL) { // normal attacked by mob
                return false;
            }
        }
        switch (lastDamageType = damageType) {
            case NORMAL:
            case NORMAL_SPELL:
                damageAmount -= VamMath.randInt(0, defense);
                lastDamagedNonEnvironmental = System.currentTimeMillis();
                break;
            case ENVIRONMENTAL_FALL:
                if (damageAmount >= hp)
                    damageAmount = hp - 1;
                break;
            case ENVIRONMENTAL_LAVA:
            case ENVIRONMENTAL_DROWNING:
            case ENVIRONMENTAL_INSTANT:
                //          case TRUE:
                break;
        }
        if (damageType == DamageType.ENVIRONMENTAL_LAVA || damageType == DamageType.ENVIRONMENTAL_DROWNING || damageType == DamageType.ENVIRONMENTAL_FALL)
            lastEnvironmentalDamaged = System.currentTimeMillis();
        else if (damageType == DamageType.NORMAL)
            lastDamaged = System.currentTimeMillis();
        if (classType == ClassType.PALADIN) {
            if (hasBuff(HolyGuardian.BUFF_ID))
                damageAmount *= getBuffValue(HolyGuardian.BUFF_ID);
            if (hasBuff(ChantOfNecessarius.BUFF_ID))
                damageAmount = 0;
        }
        if(hasBuff(GodsHand.BUFF_ID)){
        	damageAmount*=0.25;
        }
        if (hasBuff(Trinket.GUARDIAN_BUFF_ID)) {
            damageAmount *= getBuffValue(Trinket.GUARDIAN_BUFF_ID);
        }
        if (damager != null && damager != p && damager instanceof Player) {
            damageAmount *= 0.8; // PvP Damage Nerf
        }
        if (damageAmount < 1)
            damageAmount = 1;
        if (damageType == DamageType.NORMAL || damageType == DamageType.NORMAL_SPELL) {
            lastDamagedGlide = System.currentTimeMillis();
            if (damager != null && !(damager instanceof Player)) {
                safespotCounter = 0;
                MobAI.ignore.remove(getUUID());
                           //     VamMessages.announce("Resetting " + getPlayer().getName() + " counter after damaged.");
            }
            if (damageAmount > hp * 0.05) {
                p.leaveVehicle();
            }
        }
        if (p.isGliding())
            p.setGliding(false);
        hp -= damageAmount;
        if (hp < 0)
            hp = 0;
        if (damager != null && damager != p) {
            String nameToDisplayToPlayer = "an unknown source";
            if (damager instanceof Player) {
                PlayerData pd2 = Pluginc.getPD((Player) damager);
                if (pd2 != null) {
                    Player p2 = pd2.getPlayer();
                    if (p2 != null && p2.isValid()) {
                        p2.playSound(p2.getLocation(), Sound.ENTITY_PLAYER_HURT, 0.65f, 0.75f);
                        OptionsManager.msgDamage(p2, pd2, ChatColor.GRAY + ">> " + ChatColor.AQUA + ChatColor.BOLD + "-" + damageAmount + " HP" + ChatColor.WHITE + " to " + ChatColor.RED + ChatColor.BOLD + p.getName() + (crit ? ChatColor.GRAY.toString() + ChatColor.ITALIC + " *Critical Hit*" : ""));
                        lastDamagerPlayerData = pd2;
                        lastDamagerPlayerTime = System.currentTimeMillis();
                        lastDamagerPlayer = (nameToDisplayToPlayer = ChatColor.GRAY + "[" + pd2.level + "] " + ChatColor.YELLOW + damager.getName());
                        if (pd2.lifesteal > 0) {
                            pd2.heal((int) Math.ceil(damageAmount * pd2.lifesteal));
                        }
                        if(pd2.classType==ClassType.BARBARIAN && damageType==DamageType.NORMAL){
                        	//int lv=0;
                        //	if()
                        }
                        if (pd2.classType == ClassType.PALADIN) {
                           /* if (damageType == DamageType.NORMAL && pd2.hasBuff(LightningCharge.BUFF_ID) && Math.random() < 0.3) {
                                VamParticles.sendLightning(p, p.getLocation());
                                Spell.damageNearby((int) (pd2.getDamage(true) * pd2.getBuffValue(LightningCharge.BUFF_ID)), p2, p.getLocation(), 3.0, new ArrayList<Entity>());
                                Spell.notify(pd2.getPlayer(), "Lightning strikes your enemy.");
                            }
                            if (damageType == DamageType.NORMAL && pd2.hasBuff(FlameCharge.BUFF_ID) && Math.random() < 0.3) {
                                RParticles.showWithOffset(ParticleEffect.FLAME, p.getEyeLocation(), 1.5, 15);
                                giveBurn(5, (int) pd2.getBuffValue(FlameCharge.BUFF_ID));
                                Spell.notify(pd2.getPlayer(), "You burn your enemy.");
                            }*/
                        }
                    }
                }
            } else if (MobManager.spawnedMobs.containsKey(damager.getUniqueId())) {
                MobData md = MobManager.spawnedMobs.get(damager.getUniqueId());
                if (md.ai != null)
                    md.ai.lastAttackTickCounter = 0;
                lastDamagerMob = (nameToDisplayToPlayer = md.fullName);
                lastDamagerMobTime = System.currentTimeMillis();
            }
            OptionsManager.msgDamage(p, this, ChatColor.GRAY + ">> " + ChatColor.RED + ChatColor.BOLD + "-" + damageAmount + " HP"

                   + ChatColor.YELLOW + " [" + hp + " HP]" + ChatColor.WHITE + " from " + ChatColor.GOLD + ChatColor.BOLD + nameToDisplayToPlayer

                    + (crit ? ChatColor.GRAY.toString() + ChatColor.ITALIC + " *Critical Hit*" : ""));
        }
        VamParticles.showWithData(ParticleEffect.BLOCK_CRACK, p.getLocation().add(0, 1.5, 0), new BlockData(Material.REDSTONE_BLOCK, (byte) 0), 10);
        p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_HURT, 0.85f, 0.85f);

        if (System.currentTimeMillis() - this.lastHurt > 1000) {
            p.playEffect(EntityEffect.HURT);
            this.lastHurt = System.currentTimeMillis();
        }
        if (hp < 1) {
            if (p != null)
                die();
        } else {
            updateHealthManaDisplay();
            if (board != null)
                board.getObjective("hpdisplay").getScore(name).setScore(hp);
        }
        if (isStealthed())
            removeStealth();
        return true;      
    }
    
    private int stealthCounter = 0;
    
    public void giveStealth(int seconds) {
        stealthed = true;
        Player p = getPlayer();
        if (p != null && p.isValid())
            StealthManager.giveStealth(p, seconds);
        final int counter = stealthCounter;
        VamScheduler.schedule(plugin, new Runnable() {
            public void run() {
                // if stealth counter hasn't changed, then stealth has not already been removed
                // if stealth has been removed, it wont be removed again (this way, past stealth timers won't affect new stealths)
                if (stealthCounter == counter)
                    removeStealth();
            }
        }, VamTicks.seconds(seconds));
        VamScheduler.schedule(plugin, new Runnable() {
            public void run() {
                if (stealthCounter == counter && isStealthed()) {
                    if (p != null && p.isValid()) {
                        VamParticles.showWithOffset(ParticleEffect.SMOKE_NORMAL, p.getLocation().add(0, 1, 0), 1, 8);
                    }
                    VamScheduler.schedule(plugin, this, VamTicks.seconds(0.5));
                }
            }
        }, VamTicks.seconds(0.5));
    }
    
    public void removeStealth() {
        if (!isStealthed())
            return;
        stealthCounter++;
        stealthed = false;
        Player p = getPlayer();
        if (p != null && p.isValid()) {
            StealthManager.removeStealth(p);
            for (PotionEffect pe : p.getActivePotionEffects())
                p.removePotionEffect(pe.getType());
        }
    }

    
    
    public int getSpellLevel(Spell s) {
        if (spellLevels.containsKey(s))
            return spellLevels.get(s);
        return 0;
    }

    private HashMap<String, Long> lastNotifiedFinishedMobCounter = new HashMap<String, Long>();
    
    public void incrementMobCounter(String mob) {
        if (mobCounter.containsKey(mob)) {
            mobCounter.get(mob).replaceAll((k, v) -> v + 1);
            for (Entry<String, Integer> e : mobCounter.get(mob).entrySet()) {
                MobTrackerInfo mti = QuestManager.trackerInfo.get(e.getKey());
                if (mti != null) {
                    int val = this.getMobCount(mti.identifier, mti.mobsToTrack);
                    boolean finished = false;
                    if (val >= mti.requiredCount) {
                        val = mti.requiredCount;
                        finished = true;
                    }
                    if (finished) {
                        if (System.currentTimeMillis() - lastNotifiedFinishedMobCounter.getOrDefault(e.getKey(), 0l) > 15000) {
                            sendMessage(ChatColor.GRAY + "> " + ChatColor.AQUA + mti.trackerFinishedNotification);
                            lastNotifiedFinishedMobCounter.put(e.getKey(), System.currentTimeMillis());
                        }
                    } else {
                        sendMessage(ChatColor.GRAY + "> You have killed " + ChatColor.YELLOW + val + ChatColor.GRAY + "/" + ChatColor.GOLD + mti.requiredCount + " " + ChatColor.GRAY + mti.trackerMobName + " for " + mti.questName + ".");
                    }
                }
            }
        }
    }
    
    public void heal(int healAmount) {
        heal(healAmount, HealType.NORMAL);
    }

    public void heal(int healAmount, HealType type) {
        if (hasBuff(SinisterStrike.DEBUFF_ID)) {
            sendMessage(ChatColor.RED + "You are crippled and have 75% reduced healing.");
            healAmount *= 0.25;
        }
        int bonus = 0;
       /* if (type == HealType.POTION && classType == ClassType.ALCHEMIST) {
            int lv;
          /*  if ((lv = getSpellLevel(SpellbookAlchemist.POTION_MASTERY)) > 0) {
                switch (lv) {
                    case 1:
                        bonus = (int) Math.ceil(healAmount * 0.1);
                        break;
                    case 2:
                        bonus = (int) Math.ceil(healAmount * 0.2);
                        break;
                    case 3:
                        bonus = (int) Math.ceil(healAmount * 0.3);
                        break;
                    case 4:
                        bonus = (int) Math.ceil(healAmount * 0.4);
                        break;
                    case 5:
                        bonus = (int) Math.ceil(healAmount * 0.5);
                        break;
                }
                if (bonus < 1)
                    bonus = 1;
            }
        }*/
        if (classType == ClassType.PRIEST) {
            int lv;
            if ((lv = getSpellLevel(SpellBookPriest.HEAL_ENHANCE)) > 0) {
                switch (lv) {
                    case 1:
                        bonus = (int) Math.ceil(healAmount * 0.10);
                        break;
                    case 2:
                        bonus = (int) Math.ceil(healAmount * 0.12);
                        break;
                    case 3:
                        bonus = (int) Math.ceil(healAmount * 0.14);
                        break;
                    case 4:
                        bonus = (int) Math.ceil(healAmount * 0.16);
                        break;
                    case 5:
                        bonus = (int) Math.ceil(healAmount * 0.18);
                        break;
                    case 6:
                        bonus = (int) Math.ceil(healAmount * 0.20);
                        break;
                }
                if (bonus < 1)
                    bonus = 1;
            }
        }
        if(classType==classType.REAPER){
        	int lv;
        	if ((lv = getSpellLevel(SpellbookReaper.HEAL_ENHANCE)) > 0) {
                switch (lv) {
                    case 1:
                        bonus = (int) Math.ceil(healAmount * 0.10);
                        break;
                    case 2:
                        bonus = (int) Math.ceil(healAmount * 0.12);
                        break;
                    case 3:
                        bonus = (int) Math.ceil(healAmount * 0.14);
                        break;
                    case 4:
                        bonus = (int) Math.ceil(healAmount * 0.16);
                        break;
                    case 5:
                        bonus = (int) Math.ceil(healAmount * 0.18);
                        break;
                    case 6:
                        bonus = (int) Math.ceil(healAmount * 0.20);
                        break;
                }
                if (bonus < 1)
                    bonus = 1;
            }
        }
        this.hp += healAmount;
        this.hp += bonus;
        if (this.hp > this.getCurrentMaxHP())
            this.hp = this.getCurrentMaxHP();
        if (getOption(Option.HEAL_MESSAGES)) {
            if (bonus > 0) {
                getPlayer().sendMessage(ChatColor.GRAY + ">> " + ChatColor.GREEN + ChatColor.BOLD + "+" + healAmount + " (+" + bonus + ") HP" + ChatColor.YELLOW + " [" + this.hp + " HP]");
            } else {
                getPlayer().sendMessage(ChatColor.GRAY + ">> " + ChatColor.GREEN + ChatColor.BOLD + "+" + healAmount + " HP" + ChatColor.YELLOW + " [" + this.hp + " HP]");
            }
        }
        updateHealthManaDisplay();
        Vector v = getPlayer().getLocation().getDirection().normalize().setY(0);
        VamParticles.show(ParticleEffect.HEART, getPlayer().getLocation().add(v).add(0, 0.5 * getPlayer().getEyeHeight(), 0), 1);
    }
	
    
    public boolean attackMob(MobData other) {
        return attackMob(other, 0.5, -1, false);
    }

    public boolean attackMob(MobData other, int rpgDamage) {
        return attackMob(other, 0.5, rpgDamage, false);
    }

    public boolean attackMob(MobData other, int rpgDamage, boolean projectile) {
        return attackMob(other, 0.5, rpgDamage, projectile);
    }

    public boolean attackMob(MobData other, double knockback, int rpgDamage, boolean projectile) {
        if (riding)
            return false;
        Player p = getPlayer();
        if (p == null || other == null || System.currentTimeMillis() < other.invuln)
            return false;
        int damage;
        if (rpgDamage > 0)
            damage = rpgDamage;
        else
            damage = getDamage(false);
        boolean crit = false;
        double critChanceTemp = critChance;
        if (hasBuff(Trinket.HAWKEYE_BUFF_ID)) {
            critChanceTemp += 0.20;
        }
        if (Math.random() < critChanceTemp) {
            crit = true;
            damage *= critDamage;
            other.playCrit();
        }
        boolean success = false;
        if (classType == ClassType.ASSASSIN) {
            if (hasBuff(ShadowStab.BUFF_ID)) {
                if (isStealthed()) {
                    damage *= getBuffValue(ShadowStab.BUFF_ID);
                    Spell.notify(p, "You deliver a powerful stab from the shadows.");
                }
                removeBuff(ShadowStab.BUFF_ID);
            }
            if (hasBuff(SinisterStrike.BUFF_ID_DAMAGE)) {
                damage *= getBuffValue(SinisterStrike.BUFF_ID_DAMAGE);
                Spell.notify(p, "You cripple your enemy.");
                removeBuff(SinisterStrike.BUFF_ID);
                removeBuff(SinisterStrike.BUFF_ID_DAMAGE);
            }
            if (hasBuff(DoubleStab.BUFF_ID) && rpgDamage <= 0) {
                damage *= getBuffValue(DoubleStab.BUFF_ID);
                success = other.damage(damage, p, DamageType.NORMAL, crit);
                other.damage(damage, p, DamageType.NORMAL_SPELL, crit);
                Spell.notify(p, "You stab twice at your enemy.");
                removeBuff(DoubleStab.BUFF_ID);
            } else {
                success = other.damage(damage, p, rpgDamage > 0 ? DamageType.NORMAL_SPELL : DamageType.NORMAL, crit);
            }
        } else {
            success = other.damage(damage, p, rpgDamage > 0 ? DamageType.NORMAL_SPELL : DamageType.NORMAL, crit);
        }
        if (success && !projectile)
            other.knockback(p, knockback);
        if (success && isStealthed())
            removeStealth();
        return success;
    }

    
    public void die() {
        if (dead)
            return;
        if (!loadedSQL) {
            hp = 1;
            return;
        }
        Player p = getPlayer();
        if (p == null)
            return;
        deaths++;
        if (isStealthed())
            removeStealth();
       clearBuffs();
        switch (region.dangerLevel) {
            case 1:
            default:
                break;
            case 2:
                this.exp *= 0.85;
                break;
            case 3:
                this.exp *= 0.80;
                break;
            case 4:
                this.exp *= 0.75;
                ArrayList<Integer> rand = new ArrayList<Integer>();
                for (int k = 0; k < 36; k++) {
                    ItemStack item = p.getInventory().getItem(k);
                    if (!plugin.getInstance(ItemManager.class).isSoulbound(item)) {
                        if (item != null && item.getType() != Material.AIR)
                            rand.add(k);
                    }
                }
                int half = (int) Math.ceil(rand.size() / 2.0);
                while (rand.size() > half) {
                    rand.remove((int) (Math.random() * rand.size()));
                }
                for (int k : rand) {
                    ItemStack item = p.getInventory().getItem(k);
                    if (!plugin.getInstance(ItemManager.class).isSoulbound(item)) {
                        p.getInventory().setItem(k, null);
                        p.getWorld().dropItemNaturally(p.getLocation(), item);
                    }

                }
                ItemStack item = p.getInventory().getHelmet();
                if (!plugin.getInstance(ItemManager.class).isSoulbound(item)) {
                    if (item != null && Math.random() < 0.5) {
                        p.getInventory().setHelmet(null);
                        p.getWorld().dropItemNaturally(p.getLocation(), item);
                    }
                }
                item = p.getInventory().getChestplate();
                if (!plugin.getInstance(ItemManager.class).isSoulbound(item)) {
                    if (item != null && Math.random() < 0.5) {
                        p.getInventory().setChestplate(null);
                        p.getWorld().dropItemNaturally(p.getLocation(), item);
                    }
                }
                item = p.getInventory().getLeggings();
                if (!plugin.getInstance(ItemManager.class).isSoulbound(item)) {
                    if (item != null && Math.random() < 0.5) {
                        p.getInventory().setLeggings(null);
                        p.getWorld().dropItemNaturally(p.getLocation(), item);
                    }
                }
                item = p.getInventory().getBoots();
                if (!plugin.getInstance(ItemManager.class).isSoulbound(item)) {
                    if (item != null && Math.random() < 0.5) {
                        p.getInventory().setBoots(null);
                        p.getWorld().dropItemNaturally(p.getLocation(), item);
                    }
                }
                break;
        }
        //save();
      //  VamMessages.sendTitle(p, ChatColor.RED + "" + ChatColor.BOLD + "You just died!", ChatColor.RED + DEATH_MESSAGES[(int) (Math.random() * DEATH_MESSAGES.length)], 10, 60, 30);
        p.closeInventory();
        long curr = System.currentTimeMillis();
        // if not damaged by player within 15 sec or mob within 30 sec
        if (curr - lastDamagerPlayerTime > 15000 && curr - lastDamagerMobTime > 30000) {
            switch (lastDamageType) {
                case ENVIRONMENTAL_LAVA:
                    VamMessages.death(ChatColor.GRAY + "[" + level + "] " + ChatColor.RED + p.getName() + " burned to death!");
                    break;
                case ENVIRONMENTAL_DROWNING:
                    VamMessages.death(ChatColor.GRAY + "[" + level + "] " + ChatColor.RED + p.getName() + " drowned!");
                    break;
                case ENVIRONMENTAL_FALL:
                    VamMessages.death(ChatColor.GRAY + "[" + level + "] " + ChatColor.RED + p.getName() + " fell from a high place!");
                    break;
                default:
                    VamMessages.death(ChatColor.GRAY + "[" + level + "] " + ChatColor.RED + p.getName() + " died!");
                    break;
            }
        } else if (curr - lastDamagerPlayerTime <= 15000) {
            if (lastDamagerPlayerData != null && lastDamagerPlayerData.isValid()) {
                lastDamagerPlayerData.playerKills++;
            }
            VamMessages.death(ChatColor.GRAY + "[" + level + "] " + ChatColor.YELLOW + p.getName() + ChatColor.RED + " was killed by " + ChatColor.YELLOW + lastDamagerPlayer + ChatColor.RED + "!");
        } else {
            VamMessages.death(ChatColor.GRAY + "[" + level + "] " + ChatColor.YELLOW + p.getName() + ChatColor.RED + " was killed by " + ChatColor.YELLOW + lastDamagerMob + ChatColor.RED + "!");
        }
        dead = true;
        p.teleport(getRespawnLocation(p.getLocation()));
        for (PotionEffect pe : p.getActivePotionEffects()) {
            p.removePotionEffect(pe.getType());
        }
        VamScheduler.schedule(Pluginc.getInstance(), new Runnable() {
            public void run() {
                dead = false;
                hp = baseMaxHP + maxHP;
                mana = PlayerData.MAX_MANA;
                lastDamaged = 0;
                lastDamagerPlayerTime = 0;
                lastDamagerMobTime = 0;
                lastDamageType = DamageType.NORMAL;
                updateHealthManaDisplay();
                setInvulnerable(5);
            }
        }, 5);
    }
    
    public void setInvulnerable(int seconds) {
        lastDamaged = System.currentTimeMillis() + seconds * 1000;
    }

    public Location getRespawnLocation(Location playerLoc) {
        try {
          /*  Dungeon d = null;
            if ((d = getCurrentDungeon()) != null) {
                return d.dungeonMaster.getTPLoc();
            } else {*/
                Location destination = null;
                double shortest = -1;
                for(Map.Entry<String,Location> entry : WarpManager.warps.entrySet()) {
	                	Location warpLoc = entry.getValue();
	                    Region destRegion = RegionManager.getRegion(warpLoc);
	                    if (destRegion == null || destRegion.recLevel > this.level) {
	                        continue;
	                    }
	                    double distance = VamMath.flatDistance(playerLoc, warpLoc);
	                    if (shortest == -1 || distance < shortest) {
	                        shortest = distance;
	                        destination = warpLoc;
	                    }

                	}
                if (destination != null)
                    return destination;
                return WarpLocation.DEFAULT.getMutableLocation();
          //  }
        } catch (Exception e) {
            e.printStackTrace();
            sendMessage("Error code 107: Respawnloc. Please report this to Undvik or TheBloodyVampire!");
            return WarpLocation.DEFAULT.getMutableLocation();
        }
    }
    
    public boolean isPVP() {
        if (!isPVE() || (region != null && region.dangerLevel <= 2))
            return false;
        return true;
    }

    public boolean isPVE() {
        Player p = getPlayer();
        if ((p.getGameMode() != GameMode.SURVIVAL && p.getGameMode() != GameMode.ADVENTURE) || dead || (region != null && region.dangerLevel <= 1))
            return false;
        return true;
    }
  /*  public Location getRespawnLocation(Location playerLoc) {
        try {
            Dungeon d = null;
            if ((d = getCurrentDungeon()) != null) {
                return d.dungeonMaster.getTPLoc();
            } else {
                Location destination = null;
                double shortest = -1;
                for (WarpLocation wl : WarpLocation.values()) {
                    Location warpLoc = wl.getLocation();
                    Region destRegion = RegionManager.getRegion(warpLoc);
                    if (destRegion == null || destRegion.recLevel > this.level) {
                        continue;
                    }
                    double distance = RMath.flatDistance(playerLoc, warpLoc);
                    if (shortest == -1 || distance < shortest) {
                        shortest = distance;
                        destination = warpLoc;
                    }
                }
                if (destination != null)
                    return destination;
                return WarpLocation.OLD_MARU_ISLAND.getMutableLocation();
            }
        } catch (Exception e) {
            e.printStackTrace();
            sendMessage("Error code 107: Respawnloc. Please report this to Misaka or Edasaki!");
            return WarpLocation.OLD_MARU_ISLAND.getMutableLocation();
        }
    }*/
    public void damageSelfTrue(int damageAmount) {
        if (classType == ClassType.PALADIN) {
            if (hasBuff(HolyGuardian.BUFF_ID))
                damageAmount *= getBuffValue(HolyGuardian.BUFF_ID);
            if (hasBuff(ChantOfNecessarius.BUFF_ID))
                damageAmount = 0;
        }
        if (damageAmount < 1)
            damageAmount = 1;
        hp -= damageAmount;
        if (hp < 0)
            hp = 0;
        Player p = getPlayer();
        if (p != null) {
            OptionsManager.msgDamage(p, this, ChatColor.GRAY + ">> " + ChatColor.RED + ChatColor.BOLD + "-" + damageAmount + " HP" + ChatColor.YELLOW + " [" + hp + " HP]" + ChatColor.WHITE + " from yourself");
            p.damage(0.0);
            if (hp < 1) {
                if (p != null)
                    die();
            } else {
                updateHealthManaDisplay();
                if (board != null)
                    board.getObjective("hpdisplay").getScore(name).setScore(hp);
            }
        }
    }
    
 // Pets owned by the player and how much EXP they have for the pet
    public HashMap<PetType, Long> ownedPets = new HashMap<PetType, Long>();
    public LinkedHashMap<PetType, PetAI> activePets = new LinkedHashMap<PetType, PetAI>();
    
    private void startLoadedPets() {
        System.out.println("startLoadedPets() " + this);
        for (Entry<PetType, PetAI> e : activePets.entrySet()) {
            e.getValue().start();
        }
    }

    public void spawnNewPet(PetType pt) {
        System.out.println("spawning " + pt);
        if (!activePets.containsKey(pt)) {
            PetAI ai = new PetAI(pt, getPlayer());
            activePets.put(pt, ai);
            ai.start();
        }
    }
    private long lastEquipLevelMessage = 0;

	public void addStats(ItemStack item, boolean inHand) {
        if (item == null)
            return;
        ItemMeta im = item.getItemMeta();
        if (im == null)
            return;
        List<String> lore = im.getLore();
        if (lore == null)
            return;
        StatAccumulator sa = new StatAccumulator();
        for (String s : lore) {
            s = ChatColor.stripColor(s).trim();
            sa.parseAndAccumulate(s);
        }
        if (this.level < sa.level) {
        	if (getOption(Option.EQUIP_LEVEL) && System.currentTimeMillis() - lastEquipLevelMessage > 30000) {
                getPlayer().sendMessage("");
                getPlayer().sendMessage(ChatColor.GRAY + "> " + ChatColor.RED + "You are wearing equipment that is too high leveled!");
                getPlayer().sendMessage(ChatColor.GRAY + "> " + ChatColor.RED + "You will not receive any stats from that equipment.");
                getPlayer().sendMessage(ChatColor.GRAY + "> " + ChatColor.RED + "You can turn off this message in /options.");
                lastEquipLevelMessage = System.currentTimeMillis();
        	}
            return;
        }
        sa.apply(this);
    }
	public Inventory getBank(){
		return bank;
	}
	  public void clearBuffs() {
	        buffHandler.endTime.clear();
	        buffHandler.values.clear();
	    }
	  public boolean hasBuff(String s) {
	        s = s.toLowerCase();
	        return buffHandler.endTime.containsKey(s) && System.currentTimeMillis() < buffHandler.endTime.get(s) && buffHandler.values.containsKey(s);
	    }
	  public void removeBuff(String s) {
	        s = s.toLowerCase();
	        buffHandler.endTime.remove(s);
	        buffHandler.values.remove(s);
	    }

	    public void giveBuff(String s, double value, long durationMilliseconds) {
	        s = s.toLowerCase();
	        buffHandler.endTime.put(s, System.currentTimeMillis() + durationMilliseconds);
	        buffHandler.values.put(s, value);
	    }

	    public void giveBuff(String s, double value, long durationMilliseconds, final String message) {
	        giveBuff(s, value, durationMilliseconds);
	        VamScheduler.schedule(plugin, new Runnable() {
	            public void run() {
	                sendMessage(ChatColor.GRAY + ">> " + ChatColor.GREEN + message);
	            }
	        }, VamTicks.seconds(durationMilliseconds / 1000.0));
	    }

	    public double getBuffValue(String s) {
	        s = s.toLowerCase();
	        if (hasBuff(s))
	            return buffHandler.values.get(s);
	        else
	            return 0;
	    }
	 public boolean isValid() {
	        return getPlayer() != null && getPlayer().isOnline();
	    }
	 public long getAttackSpeed() {
	        long val = 1000;
	        double mult = attackSpeed;
	        int lv;
	        if (classType == ClassType.ARCHER && (lv = getSpellLevel(SpellbookArcher.RAPID_FIRE)) > 0) {
	            switch (lv) {
	                case 1:
	                    mult += 0.1;
	                    break;
	                case 2:
	                    mult += 0.2;
	                    break;
	                case 3:
	                    mult += 0.3;
	                    break;
	            }
	        }
	       /* if (classType == ClassType.ALCHEMIST && (lv = getSpellLevel(SpellbookAlchemist.SPEEDY_BREWER)) > 0) {
	            switch (lv) {
	                case 1:
	                    mult += 0.1;
	                    break;
	                case 2:
	                    mult += 0.2;
	                    break;
	                case 3:
	                    mult += 0.3;
	                    break;
	            }
	        }*/
	        if(classType==ClassType.BARBARIAN){
	        	if(hasBuff(Berserk.BUFF_ID)){
	        		mult+=getBuffValue(Berserk.BUFF_ID);
	        	}
	        }
	        mult = 1 - mult;
	        if (mult < 0.25)
	            mult = 0.25;
	        val *= mult;
	        return val;
	    }
	 public String serializeSPAllocation() {
	        StringBuilder sb = new StringBuilder();
	        for (Entry<Spell, Integer> e : this.spellLevels.entrySet()) {
	            sb.append(e.getKey().toString().replace(" ", "__"));
	            sb.append(' ');
	            sb.append(e.getValue());
	            sb.append(' ');
	        }
	        return sb.toString().trim();
	    }

	    public void deserializeSPAllocation(String s) {
	        String[] data = s.split(" ");
	        this.spellLevels.clear();
	        this.sp = level;
	        boolean modified = false;
	        for (int k = 0; k < data.length; k += 2) {
	            try {
	                String replaced = data[k].replace("__", " ");
	                if (replaced.trim().length() == 0)
	                    continue;
	                Spell spell = getSpellForName(replaced);
	                if (spell != null) {
	                    int level = Integer.parseInt(data[k + 1]);
	                    if (level > spell.maxLevel) {
	                        modified = true;
	                        level = spell.maxLevel;
	                    }
	                    this.sp -= level;
	                    this.spellLevels.put(spell, level);
	                } else {
	                    modified = true;
	                }
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	        }
	        if (modified) {
	            VamScheduler.schedule(plugin, () -> {
	                sendMessage(ChatColor.DARK_RED + " WARNING: " + ChatColor.RED + "Some of your spells may have changed!");
	                sendMessage(ChatColor.RED + " Some spells you had points in were changed in a recent patch.");
	                sendMessage(ChatColor.RED + " You may have spare spell points - please check in " + ChatColor.YELLOW + "/spell" + ChatColor.RED + ".");
	            }, VamTicks.seconds(2));
	        }
	        if (this.sp < 0)
	            this.sp = 0;
	    }
	    public String serializeQuestProgress() {
	        StringBuilder sb = new StringBuilder();
	        for (Entry<String, Integer> e : this.questProgress.entrySet()) {
	            //            sb.append(e.getKey().identifier);
	            sb.append(e.getKey());
	            sb.append("@@");
	            sb.append(e.getValue());
	            sb.append("###");
	        }
	        String s = sb.toString().trim();
	        if (s.endsWith("###"))
	            s = s.substring(0, s.length() - "###".length());
	        return sb.toString().trim();
	    }

	    public void deserializeQuestProgress(String s) {
	        String[] data = s.split("###");
	        for (String entry : data) {
	            if ((entry = entry.trim()).length() == 0)
	                continue;
	            String[] data2 = entry.split("@@");
	            if (data2.length != 2) {
	                VamMessages.announce("ERROR: Loading player " + name + "'s quest progress.");
	                VamMessages.announce("Entry: " + entry);
	            } else {
	                String identifier = data2[0];
	                int part = Integer.parseInt(data2[1]);
	                questProgress.put(identifier, part);
	            }
	        }
	    }
	/* public void deserializeQuestProgress2(String s) {
		 //questDemo @@ 0 && 9
		 //data[0] = questDemo @@ 0 && 9
		 // data2[0]= questDemo 
		 //data2[1]= 0 && 9
		 //data3[0]= 0
		 //data3[1]= 9
		 
		 //QUEST @@ PATH && ID ### QUEST @@ PATH && ID
		 if(s.length()==0 || s.equals(""))
			 return;
	        String[] data = s.split("###");
	        for (String entry : data) {
	            if ((entry = entry.trim()).length() == 0)
	                continue;
	            String[] data2 = entry.split("@@");
	        //    for(String entry2:data2){
	            	String[] data3=data2[1].split("&&");
	            	if (data3.length != 2) {
		                VamMessages.announce("ERROR: Loading player " + name + "'s quest progress.");
		                VamMessages.announce("Entry: " + entry);
		            }else{
		            	String identifier=data2[0];
		            	int path=Integer.parseInt(data3[0]);
		            	int part=Integer.parseInt(data3[1]);
		            	HashMap<Integer,Integer> temp=new HashMap<Integer,Integer>();
		            	temp.put(path, part);
		            	questProgress.put(identifier, temp);
		            }
	         //   } 
	 //               String identifier = data2[0];
	 //               int part = Integer.parseInt(data2[1]);
	//                questProgress.put(identifier, part);
	        }
	    }*/
	    /*
	     * Quests
	     */

	    public int getQuestProgress(Quest q) {
	        if (questProgress.containsKey(q.identifier)) {
	            return questProgress.get(q.identifier);
	        }
	        return -1;
	    }

	    public void advanceQuest(Quest q) {
	        if (questProgress.containsKey(q.identifier)) {
	            questProgress.put(q.identifier, questProgress.get(q.identifier) + 1);
	        } else {
	            questProgress.put(q.identifier, 0);
	        }
	    }

	    public boolean completedQuest(Quest q) {
	        if (questProgress.containsKey(q.identifier)) {
	            int curr = questProgress.get(q.identifier);
	            if (curr >= q.parts.size() - 1)
	                return true;
	        }
	        return false;
	    }

	    public void addNewMobCounter(String mobID, String associatedTrackerID) {
	        if (!mobCounter.containsKey(mobID)) {
	            HashMap<String, Integer> newMap = new HashMap<String, Integer>();
	            newMap.put(associatedTrackerID, 0);
	            mobCounter.put(mobID, newMap);
	        } else {
	            mobCounter.get(mobID).put(associatedTrackerID, 0);
	        }
	    }
	    public String getFullRankClean() {
	        if (rank == null)
	            return "";
	        return rank.rankDisplayName;
	    }
	    public void removeMobCounter(String associatedID, String... mobs) {
	        for (String s : mobs) {
	            if (mobCounter.containsKey(s)) {
	                Map<String, Integer> map = mobCounter.get(s);
	                map.remove(associatedID);
	                if (map.size() == 0)
	                    mobCounter.remove(s);
	            }
	        }
	    }
	    
	    public int getMobCount(String associatedID, String... mobs) {
	        int count = 0;
	        for (String s : mobs) {
	            if (mobCounter.containsKey(s)) {
	                count += mobCounter.get(s).getOrDefault(associatedID, 0);
	            }
	        }
	        return count;
	    }
	    
	 public void updateHealthManaDisplay() {
	        if (!loadedSQL)
	            return;
	        Player p = getPlayer();
	        if (p != null) {
	            if (hp > baseMaxHP + maxHP && finishedLoadEquips)
	                hp = baseMaxHP + maxHP;
	            if (hp < 0)
	                hp = 0;
	            if (mana > MAX_MANA)
	               mana = MAX_MANA;
	            if (dead)
	                return;
	            double percent = ((double) hp) / (baseMaxHP + maxHP);
	            StringBuilder sb = new StringBuilder();
	            BarColor color = BarColor.GREEN;
	            if (percent > 0.50) {
	                sb.append(ChatColor.GREEN);
	                //				sb.append(ChatColor.BOLD);
	                sb.append(hp);
	                sb.append(" ");
	                sb.append(ChatColor.AQUA);
	                sb.append(ChatColor.BOLD);
	                sb.append("HP");
	            } else if (percent > 0.20) {
	                color = BarColor.YELLOW;
	                sb.append(ChatColor.YELLOW);
	                //				sb.append(ChatColor.BOLD);
	                sb.append(hp);
	                sb.append(" ");
	                sb.append(ChatColor.AQUA);
	                sb.append(ChatColor.BOLD);
	                sb.append("HP");
	            } else {
	                color = BarColor.RED;
	                sb.append(ChatColor.DARK_RED);
	                sb.append(ChatColor.BOLD);
	                sb.append("DANGER - ");
	                sb.append(ChatColor.RED);
	                sb.append(hp);
	                sb.append(" ");
	                sb.append(ChatColor.AQUA);
	                sb.append(ChatColor.BOLD);
	                sb.append("HP");
	                sb.append(ChatColor.DARK_RED);
	                sb.append(ChatColor.BOLD);
	                sb.append(" - DANGER");
	            }
	            sb.append(ChatColor.RESET);
	       /*     if (poisonTicks > 0) {
	                color = BarColor.WHITE;
	                sb.append(ChatColor.YELLOW);
	                sb.append(" | ");
	                sb.append(ChatColor.DARK_PURPLE);
	                sb.append(ChatColor.BOLD);
	                sb.append("Poison ");
	                sb.append(RFormatter.tierToRoman(poisonTier));
	                sb.append(" (" + poisonTicks + "s)");
	            }
	            if (burnTicks > 0) {
	                color = BarColor.WHITE;
	                sb.append(ChatColor.YELLOW);
	                sb.append(" | ");
	                sb.append(ChatColor.RED);
	                sb.append(ChatColor.BOLD);
	                sb.append("Burn ");
	                sb.append(RFormatter.tierToRoman(burnTier));
	                sb.append(" (" + burnTicks + "s)");
	            }
	            if (getOption(SakiOption.PERM_COORDS)) {
	                sb.append(ChatColor.YELLOW);
	                sb.append(" | ");
	                sb.append(ChatColor.WHITE);
	                Location loc = p.getLocation();
	                sb.append('[');
	                sb.append(LocationCommand.roundToHalf(loc.getX()));
	                sb.append(", ");
	                sb.append(LocationCommand.roundToHalf(loc.getY()));
	                sb.append(", ");
	                sb.append(LocationCommand.roundToHalf(loc.getZ()));
	                sb.append(']');
	            }*/
	            if (getOption(Option.TRINKET_TIMER)) {
	                double diff = ((double) nextTrinketCast - System.currentTimeMillis()) / 1000.0;
	                sb.append(ChatColor.YELLOW);
	                sb.append(" | ");
	                sb.append(ChatColor.WHITE);
	                if (diff > 0) {
	                    sb.append(ChatColor.RED);
	                    sb.append("Trinket in ");
	                    sb.append(String.format("%.1fs", diff));
	                } else {
	                    sb.append(ChatColor.GREEN);
	                    sb.append("Trinket Ready");
	                }
	            }
	            
	            //            VamMessages.sendActionBar(p, sb.toString());
	            if (percent > 1.0)
	                percent = 1.0;
	            if (percent < 0.01)
	                percent = 0.01;
	            if (bossBar == null) {
	                bossBar = Bukkit.createBossBar(sb.toString(), BarColor.GREEN, BarStyle.SOLID);
	                bossBar.addPlayer(p);
	            }
	            bossBar.setTitle(sb.toString().trim());
	            bossBar.setProgress(percent);
	            bossBar.setColor(color);

	            if (board != null) {
	                Score score = board.getObjective("hpdisplay").getScore(name);
	                if (score != null)
	                    score.setScore(hp);
	            }
                PartyManager.updatePlayerForAll(name, hp, rank, party);

	            // Don't conflict with soaring stamina display
	            if (!p.isGliding() && SpellManager.casters.containsKey(p.getUniqueId())) {
	                CastState cs = SpellManager.casters.get(p.getUniqueId()).state;
	                if (cs != CastState.NONE) {
	                    StringBuilder sb2 = new StringBuilder();
	                    sb2.append(ChatColor.GOLD);
	                    sb2.append(ChatColor.BOLD);
	                    sb2.append(" ");
	                    sb2.append(cs.toString());
	                    VamMessages.sendActionBar(p, sb2.toString());
	                }
	            }  
	            p.setFoodLevel(mana * 2);
	            p.setLevel(level);
	            p.setExp(((float) exp) / getExpForNextLevel());
	            p.setMaxHealth(20.0);
	            p.setHealthScaled(false);
	            int i = (int) (hp / (double) (baseMaxHP + maxHP) * p.getMaxHealth());
	            if (i < 1)
	                i = 1;
	            if (i > p.getMaxHealth())
	                i = (int) (p.getMaxHealth());
	            if (i != (int) p.getHealth()) {
	                try {
	                    p.setHealth(i);
	                } catch (Exception e) {
	                    e.printStackTrace();
	                }
	            }
	        }
	    } 
	
	 private int lastLevel = -1;
	    private long cachedExp = -1;

	    public long getExpForNextLevel() {
	        long ret = 1;
	        if (lastLevel == level && cachedExp > 0) {
	            return cachedExp;
	        }
	        ret = PlayerStatics.getEXPForNextLevel(level);
	        if (ret < 0) {
	            VamMessages.announce("Error code 105: EXP. Please report this to Misaka or Edasaki.");
	            ret = Long.MAX_VALUE;
	        }
	        lastLevel = level;
	        cachedExp = ret;
	        return ret;
	    }

	 
	  public int getCurrentMaxHP() {
	        return getBaseMaxHP() + maxHP;
	    }
	  
	  public int getBaseMaxHP() {
	        return 90 + level * 50;
	    }
	  
	 public ArrayList<Player> getNearbyAllies(int radius){
		 ArrayList<Player> cached =new  ArrayList<Player> ();
		 ArrayList<Entity> en= VamMath.getNearbyEntities(getPlayer().getLocation(), radius);
		 for(Entity e:en){
			 if(!(e instanceof Player))
				 continue;
			 Player p=(Player)e;
			// PlayerData pd= Pluginc.getPD(p);
			 if(PartyManager.sameParty(p, getPlayer()))
				 cached.add(p);
			// ADD GUILD CHECK
		 }
		 return cached;
	 }
	 
	  public int getDamage(boolean isSpell) {
	        if (riding || dead || !isValid() || getPlayer().isGliding())
	            return 0;
	        int val = VamMath.randInt(damageLow, damageHigh);
	          if(hasBuff(Wrath.BUFF_ID)){
	        	  val*=getBuffValue(Wrath.BUFF_ID);
	          }
	        if (hasBuff(SwordSpirit.BUFF_ID)) {
	            val *= getBuffValue(SwordSpirit.BUFF_ID);
	        }
	        if (hasBuff(DarkBargain.BUFF_ID)) {
	            val *= getBuffValue(DarkBargain.BUFF_ID);
	        }
	        if(hasBuff(Berserk.BUFF_ID2)){
	        	val*=getBuffValue(Berserk.BUFF_ID2);
	        }
	        /*
	        if (hasBuff(MysteryDrink.DAMAGE_BUFF_ID)) {
	            val *= getBuffValue(MysteryDrink.DAMAGE_BUFF_ID);
	        }
	        if (hasBuff(MysteryDrink.DAMAGE_DEBUFF_ID)) {
	            val *= getBuffValue(MysteryDrink.DAMAGE_DEBUFF_ID);
	        }*/
	        if (hasBuff(WalkingSanctuary.BUFF_ID)) {
	            val *= getBuffValue(WalkingSanctuary.BUFF_ID);
	        }
	        if (getSpellLevel(SpellbookCrusader.SWORD_MASTERY) > 0) {
	            switch (getSpellLevel(SpellbookCrusader.SWORD_MASTERY)) {
	                default:
	                case 1:
	                    val *= 1.02;
	                    break;
	                case 2:
	                    val *= 1.04;
	                    break;
	                case 3:
	                    val *= 1.06;
	                    break;
	                case 4:
	                    val *= 1.08;
	                    break;
	                case 5:
	                    val *= 1.10;
	                    break;
	            }
	        }
	        if(getSpellLevel(SpellbookBarbarian.AXE_MASTERY)>0){
	        	switch (getSpellLevel(SpellbookArcher.BOW_MASTERY)) {
                default:
                case 1:
                    val *= 1.02;
                    break;
                case 2:
                    val *= 1.04;
                    break;
                case 3:
                    val *= 1.06;
                    break;
                case 4:
                    val *= 1.08;
                    break;
                case 5:
                    val *= 1.10;
                    break;
            }
	        }
	        if (getSpellLevel(SpellbookArcher.BOW_MASTERY) > 0) {
	            switch (getSpellLevel(SpellbookArcher.BOW_MASTERY)) {
	                default:
	                case 1:
	                    val *= 1.02;
	                    break;
	                case 2:
	                    val *= 1.04;
	                    break;
	                case 3:
	                    val *= 1.06;
	                    break;
	                case 4:
	                    val *= 1.08;
	                    break;
	                case 5:
	                    val *= 1.10;
	                    break;
	            }
	        }
	        if (getSpellLevel(SpellbookAssassin.DAGGER_MASTERY) > 0) {
	            switch (getSpellLevel(SpellbookAssassin.DAGGER_MASTERY)) {
	                default:
	                case 1:
	                    val *= 1.02;
	                    break;
	                case 2:
	                    val *= 1.04;
	                    break;
	                case 3:
	                    val *= 1.06;
	                    break;
	                case 4:
	                    val *= 1.08;
	                    break;
	                case 5:
	                    val *= 1.10;
	                    break;
	            }
	        }
	        if (getSpellLevel(SpellbookPaladin.MACE_MASTERY) > 0) {
	            switch (getSpellLevel(SpellbookPaladin.MACE_MASTERY)) {
	                default:
	                case 1:
	                    val *= 1.02;
	                    break;
	                case 2:
	                    val *= 1.04;
	                    break;
	                case 3:
	                    val *= 1.06;
	                    break;
	                case 4:
	                    val *= 1.08;
	                    break;
	                case 5:
	                    val *= 1.10;
	                    break;
	            }
	        }
	        if (isSpell) {
	            val *= spellDamage;
	        } else {
	            val *= attackDamage;
	        }
	        if (hasSetBonusDamage)
	            val *= 1.15; // 5-set bonus damage
	        return val;
	    }
	  
	  
	  public boolean attackPlayer(PlayerData other) {
	        return attackPlayer(other, 0.5, -1, false);
	    }

	    public boolean attackPlayer(PlayerData other, int rpgDamage) {
	        return attackPlayer(other, 0.5, rpgDamage, false);
	    }

	    public boolean attackPlayer(PlayerData other, int rpgDamage, boolean projectile) {
	        return attackPlayer(other, 0.5, rpgDamage, projectile);
	    }

	    public boolean attackPlayer(PlayerData other, double knockback, int rpgDamage, boolean projectile) {
	        Player p = getPlayer();
	        if (riding)
	            return false;
	        if (p == null)
	            return false;
	        if (other == this)
	            return false;
	        int damage;
	        if (rpgDamage > 0)
	            damage = rpgDamage;
	        else
	            damage = getDamage(false);
	        boolean crit = false;
	        double critChanceTemp = critChance;
	        if (hasBuff(Trinket.HAWKEYE_BUFF_ID)) {
	            critChanceTemp += 0.20;
	        }
	        if (Math.random() < critChanceTemp) {
	            crit = true;
	            damage *= critDamage;
	            other.playCrit();
	        }
	        boolean success = false;
	        if (classType == ClassType.ASSASSIN) {
	            if (hasBuff(ShadowStab.BUFF_ID)) {
	                if (isStealthed()) {
	                    damage *= getBuffValue(ShadowStab.BUFF_ID);
	                    Spell.notify(p, "You deliver a powerful stab from the shadows.");
	                }
	                removeBuff(ShadowStab.BUFF_ID);
	            }
	            if (hasBuff(SinisterStrike.BUFF_ID)) {
	                damage *= getBuffValue(SinisterStrike.BUFF_ID_DAMAGE);
	                int duration = (int) getBuffValue(SinisterStrike.BUFF_ID);
	                other.giveBuff(SinisterStrike.DEBUFF_ID, 0, duration * 1000);
	                other.sendMessage(ChatColor.RED + "You have been crippled for " + ChatColor.YELLOW + duration + "s" + ChatColor.RED + " and have reduced healing.");
	                Spell.notify(p, "You cripple your enemy.");
	                removeBuff(SinisterStrike.BUFF_ID);
	                removeBuff(SinisterStrike.BUFF_ID_DAMAGE);
	            }
	            if (hasBuff(DoubleStab.BUFF_ID) && rpgDamage <= 0) {
	                damage *= getBuffValue(DoubleStab.BUFF_ID);
	                success = other.damage(damage, p, DamageType.NORMAL, crit);
	                other.damage(damage, p, DamageType.NORMAL_SPELL, crit);
	                Spell.notify(p, "You stab twice at your enemy.");
	                removeBuff(DoubleStab.BUFF_ID);
	            } else {
	                success = other.damage(damage, p, rpgDamage > 0 ? DamageType.NORMAL_SPELL : DamageType.NORMAL, crit);
	            }
	        } else {
	            success = other.damage(damage, p, rpgDamage > 0 ? DamageType.NORMAL_SPELL : DamageType.NORMAL, crit);
	        }
	        if (success && !projectile && knockback > 0)
	            other.knockback(p, knockback);
	        if (success && isStealthed())
	           removeStealth();
	        return success;
	    }
	  
	    public void playCrit() {
	        Player p = getPlayer();
	        if (p == null || !p.isValid())
	            return;
	        VamParticles.showWithOffset(ParticleEffect.CRIT, p.getEyeLocation(), 1, 10);
	    }
	    
	    public void gainExp(long amount) {
	        gainExp(amount, false);
	    }

	    public void gainExp(long amount, boolean penalty) {
	        exp += amount;
	        Player p = getPlayer();
	        if (p == null || !p.isValid())
	            return;
	        int bonus = 0;
	        if (classType == ClassType.WIZARD) {
	            int lv;
	            if ((lv = getSpellLevel(SpellbookWizard.WISDOM)) > 0) {
	                switch (lv) {
	                    case 1:
	                        bonus = (int) Math.ceil(amount * 0.05);
	                        break;
	                    case 2:
	                        bonus = (int) Math.ceil(amount * 0.07);
	                        break;
	                    case 3:
	                        bonus = (int) Math.ceil(amount * 0.09);
	                        break;
	                    case 4:
	                        bonus = (int) Math.ceil(amount * 0.11);
	                        break;
	                    case 5:
	                        bonus = (int) Math.ceil(amount * 0.13);
	                        break;
	                    case 6:
	                        bonus = (int) Math.ceil(amount * 0.15);
	                        break;
	                }
	                if (bonus < 1)
	                    bonus = 1;
	            }
	        }
	        if (party != null) {
	            bonus += (int) Math.ceil(amount * party.getExpMultiplier());
	        }
	        long tExp = amount;
	        if (bonus > 0) {
	            tExp += bonus;
	            exp += bonus;
	           if (getOption(Option.EXP_MESSAGES))
	                p.sendMessage(ChatColor.GRAY + ">> +" + amount + " (+" + bonus + ") EXP [" + exp + "/" + getExpForNextLevel() + "]" + (penalty ? " (Level Gap Penalty)" : ""));
	        } else {
	            if (getOption(Option.EXP_MESSAGES))
	                p.sendMessage(ChatColor.GRAY + ">> +" + amount + " EXP [" + exp + "/" + getExpForNextLevel() + "]" + (penalty ? " (Level Gap Penalty)" : ""));
	        }
	        if (trinket != null) {
	            int pastLevel = Trinket.getTrinketLevel(getTrinketExp(trinket));
	            if (trinketExp.containsKey(trinket)) {
	                tExp += trinketExp.get(trinket);
	            }
	            trinketExp.put(trinket, tExp);
	            int newLevel = Trinket.getTrinketLevel(tExp);
	            if (newLevel > pastLevel)
	                p.sendMessage(ChatColor.GRAY + ">> " + ChatColor.GOLD + "Your Trinket leveled up! It is now level " + ChatColor.YELLOW + newLevel + ChatColor.GOLD + ".");
	            updateEquipment();
	        }
	        if (exp >= getExpForNextLevel()) {
	            long extra = exp - getExpForNextLevel();
	            exp = 0;
	            level++;
	            this.sp++;
	            exp = extra < getExpForNextLevel() ? extra : getExpForNextLevel() - 1;
	            baseMaxHP = getBaseMaxHP();
	            if (level >= 50) {
	                String msg = ChatColor.GRAY + "> " + ChatColor.YELLOW + name + ChatColor.GRAY + " just leveled up to level " + ChatColor.YELLOW + level + "!";
	                for (Player p2 : Pluginc.getInstance().getServer().getOnlinePlayers()) {
	                    PlayerData pd2 = Pluginc.getPD(p2);
	                   if (pd2 != null && pd2.getOption(Option.LEVEL_ANNOUNCEMENTS)) {
	                        pd2.sendMessage(msg);
	                    }
	                }
	            }
	            p.sendMessage(ChatColor.GOLD + "**************************************");
	            p.sendMessage(ChatColor.GOLD + " You leveled up! You are now level " + ChatColor.YELLOW + level + ChatColor.GOLD + ".");
	            p.sendMessage(ChatColor.GOLD + " Your base Max HP increased to " + baseMaxHP + ".");
	            p.sendMessage(ChatColor.GOLD + "**************************************");
	            VamParticles.spawnRandomFirework(p.getLocation());
	            updateHealthManaDisplay();
	        }
	    }
	    
	    public void unload() {
	        unloaded = true;
	      //  if (uuid != null) {
	      /////      PunishmentManager.muted_byUUID.remove(uuid);
	      //      PunishmentManager.ips_byUUID.remove(uuid);
	     //   }
	     ///   for (PetAI p : activePets.values()) {
	     //       p.halt();
	      //  }
	      //  activePets.clear();
	        if (bossBar != null)
	            bossBar.removeAll();
	        bossBar = null;
	        if(uuid!=null){
	        ParticleManager.dispose(uuid);
	        MenuManager.clear(uuid);
	        }
	        name = null;
	        uuid = null;
	        for (Halter h : halters)
	           h.halt = true;
	    }
	    // Special Effects
	    public void specialEffectsTask() {
	        final PlayerData me = this;
	        VamScheduler.schedule(plugin, new Runnable() {
	            public void run() {
	                if (isValid()) {
	                    ParticleManager.tick(getPlayer(), me);
	                    VamScheduler.schedule(plugin, this, VamTicks.seconds(0.5));
	                } else {
	                    ParticleManager.dispose(uuid);
	                }
	            }
	        });
	    }
	  /*  public void giveStealth(int seconds) {
	        stealthed = true;
	        Player p = getPlayer();
	        if (p != null && p.isValid())
	            StealthManager.giveStealth(p, seconds);
	        final int counter = stealthCounter;
	        VamScheduler.schedule(plugin, new Runnable() {
	            public void run() {
	                // if stealth counter hasn't changed, then stealth has not already been removed
	                // if stealth has been removed, it wont be removed again (this way, past stealth timers won't affect new stealths)
	                if (stealthCounter == counter)
	                    removeStealth();
	            }
	        }, RTicks.seconds(seconds));
	        VamScheduler.schedule(plugin, new Runnable() {
	            public void run() {
	                if (stealthCounter == counter && isStealthed()) {
	                    if (p != null && p.isValid()) {
	                        RParticles.showWithOffset(ParticleEffect.SMOKE_NORMAL, p.getLocation().add(0, 1, 0), 1, 8);
	                    }
	                    RScheduler.schedule(plugin, this, RTicks.seconds(0.5));
	                }
	            }
	        }, RTicks.seconds(0.5));
	    }*/
	    public void manaRegenTask() {
	        VamScheduler.schedule(plugin, new Runnable() {
	            public void run() {
	                mana++;
	                if (mana > MAX_MANA)
	                    mana = MAX_MANA;
	                updateHealthManaDisplay();
	                VamScheduler.schedule(plugin, this, getManaRegenRateTicks());
	            }
	        }, getManaRegenRateTicks());
	    }
	    public int getManaRegenRateTicks() {
	        int rate = 40; // 2 seconds by default
	        double multiplier = 1;
	        if (classType == ClassType.WIZARD) {
	            switch (getSpellLevel(SpellbookWizard.MANA_TIDE)) {
	                default:
	                case 1:
	                    multiplier += 0.15;
	                    break;
	                case 2:
	                    multiplier += 0.30;
	                    break;
	                case 3:
	                    multiplier += 0.45;
	                    break;
	                case 4:
	                    multiplier += 0.60;
	                    break;
	                case 5:
	                    multiplier += 0.75;
	                    break;
	            }
	        } else if (classType == ClassType.ASSASSIN) {
	            if (isStealthed() && getSpellLevel(SpellbookAssassin.DARK_HARMONY) > 0)
	                multiplier += 0.5;
	        }
	        rate /= multiplier;
	        if (rate < 1)
	            rate = 1;
	        return rate;
	    }
	    public void giveSlow(int durationSeconds, int tier) {
	        int highestTier = tier;
	        int remaining = 0;
	        Player p = getPlayer();
	        if (p == null)
	            return;
	        for (PotionEffect pe : p.getActivePotionEffects()) {
	            if (pe.getType().equals(PotionEffectType.SLOW)) {
	                remaining = pe.getDuration();
	                int temp = pe.getAmplifier();
	                if (temp > highestTier)
	                    highestTier = temp;
	            }
	        }

	        p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, VamTicks.seconds(durationSeconds) + (remaining / 2), highestTier), true);
	    }

	    /*
	     * Temp States
	     */

	    public void addState(Object o) {
	        states.add(o.toString());
	    }

	    public boolean hasState(Object o) {
	        return states.contains(o.toString());
	    }

	    public void removeState(Object o) {
	        states.remove(o.toString());
	    }  
	  public Projectile shootArrow() {
	        //attempted fix for arrows crashing
	        Player p = getPlayer();
	        return p.launchProjectile(Arrow.class, p.getLocation().getDirection().normalize().multiply(1.5));
	    }
	  
	  public void sendMessage(Object o) {
	        if (o != null)
	            sendMessage(o.toString());
	    }

	    public void sendMessage(String s) {
	        if (isValid())
	            getPlayer().sendMessage(s);
	    }
	 
	    public void addBadgesSuffix(FancyMessage fm) {
	        if (badges.size() == 0)
	            return;
	        fm.then(" ");
	        for (Badge b : badges) {
	            fm.then(b.getDisplay());
	            fm.tooltip(b.getTooltip());
	        }
	    }
	    private String serializeBadges() {
	        StringBuilder sb = new StringBuilder();
	        for (Badge b : badges) {
	            sb.append(b.toString());
	            sb.append(' ');
	        }
	        return sb.toString().trim();
	    }
	    private void deserializeBadges(String s) {
	        String[] data = s.split(" ");
	        for (String b : data) {
	            b = b.trim();
	            if (b.length() == 0)
	                continue;
	            try {
	                badges.add(Badge.valueOf(b));
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	        }
	    }
	    
	    public void setRank(String s) {
	        try {
	            setRank(Rank.valueOf(s.toUpperCase()));
	        } catch (Exception e) {
	            Log.error("Could not find rank corresponding to '" + s + "'");
	            sendMessage(ChatColor.RED + "Could not find rank corresponding to '" + s + "'");
	        }
	    }

	    public void setRank(Rank r) {
	        rank = r;
	    }

	    public String serializeMailbox(){
	    	StringBuilder sb= new StringBuilder();
	    	//StringBuilder s= new StringBuilder();
			if(Mailbox==null){
				sb.append("null");
				return sb.toString();
			}
			if(Mailbox.size()==0){
				sb.append("null");
				return sb.toString();
			}
	    	for(MailboxItem i: Mailbox){
	    		sb.append(i.toString());
	    		sb.append("@");
	    	}
	    	 String s = sb.toString().trim();
	 	    if (s.endsWith("@"))
	 	        s = s.substring(0, s.length() - 1);
	 	    
	 	    return s;
	    }
	    public void deserializeMailbox(String s){
	   // 	ArrayList<MailboxItem> temp= new ArrayList<MailboxItem>();
	    	if(s==null)
			return;
	    	if(s.equals(""))
	    		return;
	    	if(s.equals("null"))
	    		return;
	    	String[] data = s.split("@");
	    	if (data.length == 0 || (data.length == 1 && data[0].equals(""))){
	    		return;
	    	}
	    	for(String sa:data){
	    		  String a = sa.substring(0, sa.indexOf("::"));
	              String b = sa.substring(sa.indexOf("::") + "::".length());
	    		ItemStack mailbox=VamSerializer.deserializeItemStack(a);
	    		LocalDateTime d= LocalDateTime.parse(b);
	    		MailboxItem m= new MailboxItem(mailbox, d, this,false);
	    	}
	    	
	    }
	    public void hpDisplayAndRegenTask() {
	        // HP Display
	        if (board == null) {
	            board = plugin.getServer().getScoreboardManager().getNewScoreboard();
	            Objective objective = board.registerNewObjective("hpdisplay", "dummy");
	            objective.setDisplaySlot(DisplaySlot.BELOW_NAME);
	            objective.setDisplayName(ChatColor.DARK_RED + "\u2764");
	        }
	        final Halter h = halter();
	        VamScheduler.scheduleRepeating(plugin, new Runnable() {
	            int counter = 0;

	            public void run() {
	                if (isValid()) {
	                    if (dead)
	                        return;
	                    counter++;
	                    if (counter % 2 == 0) {
	                        int regenAmount = (int) (Math.ceil((baseMaxHP + maxHP) * getHPRegenRate()));
	                        if (getSpellLevel(SpellbookReaper.RAPID_RECOVERY) > 0) {
	                            int missing = baseMaxHP + maxHP - hp;
	                            switch (getSpellLevel(SpellbookReaper.RAPID_RECOVERY)) {
	                                case 1:
	                                    regenAmount += (int) (Math.ceil(missing * 0.0020));
	                                    break;
	                                case 2:
	                                    regenAmount += (int) (Math.ceil(missing * 0.0025));
	                                    break;
	                                case 3:
	                                    regenAmount += (int) (Math.ceil(missing * 0.0030));
	                                    break;
	                                case 4:
	                                    regenAmount += (int) (Math.ceil(missing * 0.0035));
	                                    break;
	                                case 5:
	                                    regenAmount += (int) (Math.ceil(missing * 0.0040));
	                                    break;
	                            }
	                        }
	                     /*   if (hasBuff(MysteryDrink.REGEN_BUFF_ID))
	                            regenAmount += (int) (Math.ceil((baseMaxHP + maxHP) * getBuffValue(MysteryDrink.REGEN_BUFF_ID)));
	                        if (hasBuff(SinisterStrike.DEBUFF_ID))
	                            regenAmount *= 0.25;*/
	                        if (regenAmount < 1)
	                            regenAmount = 1;
	                        if (region != null && region.dangerLevel == 1)
	                            regenAmount *= 2;
	                        regenAmount *= hpRegen;
	                        hp += regenAmount;
	                    }
	                    if (hp > baseMaxHP + maxHP && finishedLoadEquips)
	                        hp = baseMaxHP + maxHP;
	                    updateHealthManaDisplay();
	                } else {
	                    h.halt = true;
	                }
	            }
	        }, VamTicks.seconds(0.5), h);

	        Team team = board.getTeam(rank.rankDisplayName);
	        if (team == null) {
	            team = board.registerNewTeam(rank.rankDisplayName);
	            team.setPrefix(rank.nameColor + "");
	            team.setAllowFriendlyFire(true);
	            team.setOption(Team.Option.COLLISION_RULE, Team.OptionStatus.NEVER);
	        }
	        team.addEntry(name);
	        getPlayer().setScoreboard(board);
	    }
	  
	  public double getHPRegenRate() {
	        return 0.005; //0.5% by default
	    }
	  public void recoverMana(int amount) {
	        mana += amount;
	        if (mana > MAX_MANA)
	            mana = MAX_MANA;
	        sendMessage(ChatColor.GRAY + ">> " + ChatColor.DARK_AQUA + ChatColor.BOLD + "+" + amount + " Mana" + ChatColor.AQUA + " [" + this.mana + " Mana]");
	    }
	   protected List<Halter> halters;
	  public boolean castedFirework;
	public boolean usedTrinketCommand;

	    protected Halter halter() {
	        Halter h = new Halter();
	        halters.add(h);
	        return h;
	    }
	    public void resetSpells() {
	        spellLevels.clear();
	        spell_RLL = null;
	        spell_RLR = null;
	        spell_RRL = null;
	        spell_RRR = null;
	        sp = level;
	    }
	 /*   public void openMailbox(){
	    	if(Mailbox!=null){
	    		Inventory inv= Bukkit.createInventory(getPlayer(), 54, "Mailbox");
	    		for(MailboxItem i:Mailbox){
	    			ItemStack is= i.Reward;
	    		}
	    	}
	    	//Inventory inv= Bukkit.createInventory(getPlayer(), 54, "Mailbox");
	    }*/
	//    public void addMailboxItem(M)
	  public void knockback(Entity attacker, double knockbackMultiplier) {
	        return;
	        //        final Player p = getPlayer();
	        //        if (p == null)
	        //            return;
	        //        if (attacker instanceof Player && !this.isPVP())
	        //            return;
	        //        else if (!this.isPVE())
	        //            return;
	        //        if (hasBuff(PowerStance.BUFF_ID) || hasBuff(ChantOfNecessarius.BUFF_ID))
	        //            return;
	        //        if (System.currentTimeMillis() - lastKnockback > 600) {
	        //            lastKnockback = System.currentTimeMillis();
	        //            Vector newVelocity = p.getLocation().toVector().subtract(attacker.getLocation().toVector()).normalize().multiply(knockbackMultiplier);
	        //            // cap Y knockback
	        //            if (Math.abs(newVelocity.getY()) > 0.01)
	        //                newVelocity.setY(0.01 * Math.signum(newVelocity.getY()));
	        //            // cap X knockback
	        //            if (Math.abs(newVelocity.getX()) > 1)
	        //                newVelocity.setX(1 * Math.signum(newVelocity.getX()));
	        //            // cap Z knockback
	        //            if (Math.abs(newVelocity.getZ()) > 1)
	        //                newVelocity.setZ(1 * Math.signum(newVelocity.getZ()));
	        //            if (p != null && p.isValid())
	        //                p.setVelocity(newVelocity);
	        //        }
	    }
	  
	public  Player getPlayer(){
		if(Bukkit.getPlayer(uuid)!=null){
			if(Bukkit.getPlayer(uuid).isOnline())
				return Bukkit.getPlayer(uuid);
			return null;
		}
		//if(Bukkit.getPlayer(uuid).isOnline() && Bukkit.getPlayer(uuid)!=null)
		//return Bukkit.getPlayer(uuid);	
		return null;
	}	
	  public String serializeOptions() {
	        if (this.optionsList == null)
	            return "";
	        return this.optionsList.toString();
	    }
	 public OptionsList deserializeOptions(String temp) {
	        return new OptionsList(temp);
	    }
	 public boolean getOption(Option so) {
	        if (this.optionsList == null) {
	            return so.getDefault();
	        }
	        return this.optionsList.get(so);
	    }
	 public boolean toggleOption(Option so) {
	        if (this.optionsList == null)
	            return so.getDefault();
	        boolean ret = this.optionsList.toggle(so);
	        sendMessage(ChatColor.GRAY + "> " + ChatColor.WHITE + "Toggled " + ChatColor.YELLOW + so.getDisplay() + ChatColor.WHITE + ". " + ChatColor.AQUA + so.getDesc(ret));
	        return ret;
	    }
	  public Spell getSpellForName(String name) {
	        Spellbook sb = getSpellbook();
	        Spell spell = sb.getSpell(name);
	        if (spell != null)
	            return spell;
	        spell = VillagerSpellbook.INSTANCE.getSpell(name);
	        if (spell != null)
	            return spell;
	        return null;
	    }
	  public Spellbook getSpellbook() {
	        switch (this.classType) {
	            case CRUSADER:
	                return SpellbookCrusader.INSTANCE;
	            case PALADIN:
	                return SpellbookPaladin.INSTANCE;
	            case ASSASSIN:
	                return SpellbookAssassin.INSTANCE;
	            case BARBARIAN:
	            	return SpellbookBarbarian.INSTANCE;
	            case PRIEST:
	                return SpellBookPriest.INSTANCE;
	            case REAPER:
	                return SpellbookReaper.INSTANCE;
	            case ARCHER:
	                return SpellbookArcher.INSTANCE;
	            case WIZARD:
	                return SpellbookWizard.INSTANCE;
	            case VILLAGER:
	            default:
	                return VillagerSpellbook.INSTANCE;
	        }
	    }
	  public Spell[] getSpellList() {
	        return getSpellbook().getSpellList();
	    }
	  public boolean usingSpell(Spell s) {
	        return spell_RLL == s || spell_RLR == s || spell_RRL == s || spell_RRR == s;
	    }
	  public boolean levelSpell(Spell s) {
	        if (spellLevels.containsKey(s) && spellLevels.get(s) >= s.maxLevel)
	            return false;
	        if (spellLevels.containsKey(s)) {
	            spellLevels.put(s, spellLevels.get(s) + 1);
	        } else {
	            spellLevels.put(s, 1);
	        }
	        return true;
	    }
	  private void checkSpellStatEffects() {
	        if (getSpellLevel(SpellbookCrusader.HEALTHY_DIET) > 0) {
	            switch (getSpellLevel(SpellbookCrusader.HEALTHY_DIET)) {
	                case 1:
	                    maxHP += 50;
	                    break;
	                case 2:
	                    maxHP += 75;
	                    break;
	                case 3:
	                    maxHP += 125;
	                    break;
	                case 4:
	                    maxHP += 200;
	                    break;
	                case 5:
	                    maxHP += 300;
	                    break;
	            }
	        }
	        if (getSpellLevel(SpellbookArcher.KEEN_EYES) > 0) {
	            switch (getSpellLevel(SpellbookArcher.KEEN_EYES)) {
	                case 1:
	                    critChance += 2;
	                    break;
	                case 2:
	                    critChance += 4;
	                    break;
	                case 3:
	                    critChance += 6;
	                    break;
	                case 4:
	                    critChance += 8;
	                    break;
	                case 5:
	                    critChance += 10;
	                    break;
	            }
	        }
	        if (getSpellLevel(SpellbookPaladin.DIVINITY) > 0) {
	            switch (getSpellLevel(SpellbookPaladin.DIVINITY)) {
	                case 1:
	                    maxHPMultiplier += 2;
	                    break;
	                case 2:
	                    maxHPMultiplier += 4;
	                    break;
	                case 3:
	                    maxHPMultiplier += 6;
	                    break;
	                case 4:
	                    maxHPMultiplier += 8;
	                    break;
	                case 5:
	                    maxHPMultiplier += 10;
	                    break;
	            }
	        }
	    }
	public int getProfessionLevel(Profession pr){
		if(professions.containsKey(pr)){
			return professions.get(pr)[0];
		}
		return -1;
	}
	public int getProfessionExp(Profession pr){
		if(professions.containsKey(pr)){
			return professions.get(pr)[1];
		}
		return -1;
	}
	public void setProfessionLevel(Profession pr,int in){
		if(professions.containsKey(pr)){
			professions.put(pr, new Integer[]{in,getProfessionExp(pr)});
		}
		
	}
	public void setProfessionExp(Profession pr,int in){
		if(professions.containsKey(pr)){
			professions.put(pr, new Integer[]{getProfessionLevel(pr),in});
		}
		
	}
	public void openBank() {
        if (!this.loadedSQL) {
            sendMessage(ChatColor.RED + "You can't open your bank right now.");
            return;
        }
        if (this.region != null && this.region.dangerLevel > 1) {
            sendMessage(ChatColor.RED + "Banks can only be used in Danger Level 1 regions.");
            return;
        }
        Player p = getPlayer();
        if (p != null && p.isValid()) {
            p.openInventory(bank);
        }
    }
	
	public String serializeTrinketExp() {
        if (trinketExp == null)
            return "";
        StringBuilder sb = new StringBuilder();
        for (Entry<Trinket, Long> e : trinketExp.entrySet()) {
            sb.append(e.getKey().name);
            sb.append("::");
            sb.append(e.getValue());
            sb.append("@");
        }
        String s = sb.toString().trim();
        if (s.endsWith("@"))
            s = s.substring(0, s.length() - 1);
        return s;
    }

    public void deserializeTrinketExp(String s) {
        if (trinketExp == null)
            trinketExp = new HashMap<Trinket, Long>();
        trinketExp.clear();
        String[] data = s.split("@");
        if (data.length == 0 || (data.length == 1 && data[0].equals("")))
            return;
        for (String temp : data) {
            try {
                String[] data2 = temp.split("::");
                Trinket trinket = Trinket.getTrinket(data2[0]);
                if (trinket != null)
                    trinketExp.put(trinket, Long.parseLong(data2[1]));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    /*
     * Trinkets
     */

    public long getTrinketExp(Trinket trinket) {
        if (trinket == null || !trinketExp.containsKey(trinket))
            return 0;
        return trinketExp.get(trinket);
    }
    
	public  void loadBank(String s,Inventory inv){
		HashMap<Integer, ItemStack> map = new HashMap<Integer, ItemStack>();
		if (s == null){
			this.bank=inv;
        return;
		}
		if(s.equals("null")){
			this.bank=inv;
				return;
		}
	    String[] data = s.split("@");
	    if (data.length == 0 || (data.length == 1 && data[0].equals(""))){
	    	this.bank=inv;
	        return;
	    }
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
	    this.bank=inv;
	   // bank.clear();
	   for (Entry<Integer, ItemStack> e : map.entrySet()) {
	      inv.setItem(e.getKey(), e.getValue());
	    }
	   this.bank=inv;
	}
	 public boolean isStealthed() {
		 return stealthed;
	    }
	 
}
