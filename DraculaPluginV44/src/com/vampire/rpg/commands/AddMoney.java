package com.vampire.rpg.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.block.banner.Pattern;
import org.bukkit.block.banner.PatternType;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.BannerMeta;
import org.bukkit.inventory.meta.ItemMeta;

import com.vampire.rpg.ItemAPI;
import com.vampire.rpg.PlayerData;
import com.vampire.rpg.Pluginc;
import com.vampire.rpg.items.ItemManager;
import com.vampire.rpg.mailboxes.MailboxItem;
import com.vampire.rpg.professions.Profession;
import com.vampire.rpg.utils.BannerBuilder;
import com.vampire.rpg.utils.VamMessages;

public class AddMoney implements CommandExecutor{
	//public static ArrayList<CraftedItem> as = Pluginc.getAsd();
	public ArrayList<ItemStack> armor = new  ArrayList<ItemStack>();
	public int getMoney(Player p,PlayerInventory inv){
		int money=0;
		//PlayerInventory inv=p.getInventory();
		for(ItemStack is:inv.getContents()){
			if(is!=null && is.getType()!=Material.AIR){
			if(is.hasItemMeta()&&is.getItemMeta().hasDisplayName()&&is.getItemMeta().getDisplayName().equals(ChatColor.GREEN+"Aurum Fragment")){
				//p.sendMessage("test");
				money+=is.getAmount();
				}
			if(is.hasItemMeta()&&is.getItemMeta().hasDisplayName()&&is.getItemMeta().getDisplayName().equals(ChatColor.GREEN+"Aurum Ingot")){
				//p.sendMessage("test");
				money+=is.getAmount()*64;
				}
			}
		}
		return money;
	}
	public static void equipBanner(Player p,ItemStack banner){
		PlayerData pd=Pluginc.getPD(p);
		if(pd.banner!=null){
			p.sendMessage(ChatColor.RED+"You have  a banner equiped already!");
			p.sendMessage(ChatColor.RED+"Unequip your current one to use this one");
			return;
		}			
		ItemStack helmet=p.getEquipment().getHelmet();
		pd.replaceBanner=helmet;
		BannerMeta meta= (BannerMeta) banner.getItemMeta();
		List<String> lore= new ArrayList<String>();	
		if(ItemAPI.hasLore(helmet)){
			for(String s:helmet.getItemMeta().getLore()){
				lore.add(s);
		 	}
		}
		meta.setLore(lore);
		banner.setItemMeta(meta);
		p.getEquipment().setHelmet(banner);
		pd.banner=banner;
		pd.updateEquipment();
	}
	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] arg3) {
		if(!(sender instanceof Player)){
			return true;
		}
		Player p=(Player)sender;
		if(!p.getName().equals(Pluginc.Owner_Name) && !p.getName().equals(Pluginc.Host_Name)){
			
			return true;
		}
		//CraftedItemManager a= new CraftedItemManager(ItemAPI.LoreItem(new ItemStack(Material.DIAMOND_SWORD), ChatColor.RED+"Demon Slayer", ChatColor.BLACK+"A mighty sword", true, true), new ItemStack[]{new ItemStack(Material.ACACIA_DOOR),new ItemStack(Material.BEETROOT)});
		//p.sendMessage(a.getCraftedItem().toString());
		//p.sendMessage(a.getRecipe()[0].toString());
		
	//	for(CraftedItem c:Pluginc.getAllCraftedItems()){
		//	p.sendMessage(c.getCraftedItemName());
	//	}
		//for(Pluginc.craf a: Pluginc.geta()){
		//	p.sendMessage(a.toString());
		//}
		p.sendMessage("your bal is"+String.valueOf(getMoney(p,p.getInventory())));
		PlayerInventory pinv= p.getInventory();
		/*for(ItemStack is:pinv.getContents()){
			if(is!=null){
				p.sendMessage(is.getType().toString());
			}
		}*/
		//ItemStack a= ItemAPI.ColoredItem(new ItemStack(Material.ACACIA_DOOR), "kys", Arrays.asList(4, 53, 62));
		ItemStack asd= ItemAPI.LoreItem(new ItemStack(Material.ANVIL), "\u2694"+"test", null);
		/*ItemStack goldfrag= new ItemStack(Material.GOLD_NUGGET);
		ItemMeta meta=goldfrag.getItemMeta();
		meta.setDisplayName(ChatColor.GREEN+"Aurum Fragment");
		goldfrag.setItemMeta(meta);
		goldfrag.setAmount(64);
		pinv.addItem(goldfrag);
		pinv.addItem(asd);
		ItemStack item=p.getEquipment().getItemInMainHand();*/
	//	StatAccumulator.clearStats(p);
		/*armor.clear();
		
		armor.add(p.getEquipment().getHelmet());
        armor.add(p.getEquipment().getChestplate());
        armor.add(p.getEquipment().getLeggings());
        armor.add(p.getEquipment().getBoots());*/
      //  for (ItemStack i : armor) {
        //    PlayerStats.addStats(p,i, false);
            //addSet(i);
     //   }
	/*	ItemStack goldingot= new ItemStack(Material.GOLD_INGOT);
		ItemMeta meta1=goldingot.getItemMeta();
		meta1.setDisplayName(ChatColor.GREEN+"Aurum Ingot");
		goldingot.setItemMeta(meta1);
		goldingot.setAmount(64);*/
		//p.sendMessage(String.valueOf(PlayerStats.getDef(p)));
	//	p.sendMessage(String.valueOf(PlayerStats.getLevel(p)));
	//	ItemStack i=ItemManager.itemIdentifierToRPGItemMap.get("compass").generate();
	//	ItemStack i2= ItemManager.itemIdentifierToRPGItemMap.get("test_me").generate();
	//   p.getInventory().addItem(ItemManager.itemIdentifierToRPGItemMap.get("me").generate());
	//	pinv.addItem(i);
//		pinv.addItem(i2);
	//	p.sendMessage(String.valueOf(PlayerStats.getHP(p)));
	//	pinv.addItem(goldingot);
		 //IChatBaseComponent barmsg = ChatSerializer.a("{text:"§e§lWELCOME TO",extra:[{text:" §6§lREAL MYTH GAMING"}]});"
		//PacketPlayOutChat bar = new PacketPlayOutChat(barmsg, 2);
		//((CraftPlayer) p).getHandle().playerConnection.sendPacket(bar);
		p.sendMessage("your bal is"+String.valueOf(getMoney(p,p.getInventory())));
		VamMessages.sendActionBar(p,"KYSSSS");
		PlayerData pd=Pluginc.getPD(p);
		MailboxItem m= new MailboxItem(ItemAPI.LoreItem(new ItemStack(Material.IRON_AXE), ChatColor.BLACK+"best reward",ChatColor.GREEN+ "TROLOLOL", true), 6, pd,true);
		pd.setProfessionLevel(Profession.MINING, pd.getProfessionLevel(Profession.MINING)+23);
		//pd.miningLvl+=23;
		ItemStack i3=new BannerBuilder(DyeColor.LIGHT_BLUE).addPattern(DyeColor.GREEN, PatternType.STRIPE_BOTTOM).
				addPattern(DyeColor.WHITE, PatternType.CIRCLE_MIDDLE).addPattern(DyeColor.BLACK, PatternType.CREEPER).
				addPattern(DyeColor.PINK, PatternType.FLOWER).addPattern(DyeColor.PINK, PatternType.MOJANG).generateItem();
	/*	ItemStack i2=new ItemStack(Material.BANNER);
		BannerMeta meta2=(BannerMeta) i2.getItemMeta();
		meta2.setBaseColor(DyeColor.LIGHT_BLUE);
		meta2.addPattern(new Pattern(DyeColor.GREEN, PatternType.STRIPE_BOTTOM));
		meta2.addPattern(new Pattern(DyeColor.WHITE, PatternType.CIRCLE_MIDDLE));
		meta2.addPattern(new Pattern(DyeColor.BLACK, PatternType.CREEPER));
		meta2.addPattern(new Pattern(DyeColor.PINK, PatternType.FLOWER));
		meta2.addPattern(new Pattern(DyeColor.PINK, PatternType.MOJANG));
		meta2.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		meta2.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
		i2.setItemMeta(meta2);*/
		p.getInventory().addItem(i3);
		equipBanner(p, i3);
	/*	PacketContainer cont= new PacketContainer(PacketType.Play.Server.ENTITY_EQUIPMENT);
		WrapperPlayServerEntityEquipment wrapper= new WrapperPlayServerEntityEquipment(cont);
		wrapper.setSlot(ItemSlot.HEAD);
		wrapper.setItem(i2);
		wrapper.setEntityID(p.getEntityId());
	//	for(Player p1:Pluginc.getInstance().getServer().getOnlinePlayers())
		//wrapper.sendPacket(p1);*/
		
		//for(MailboxItem ma:pd.Mailbox){
	//		p.sendMessage(ma.Reward.getItemMeta().getDisplayName());
	//	}
		//Inventory inv= Bukkit.createInventory(null, 9, "test");
		//inv.addItem(new ItemStack(Material.CARROT_ITEM));
		//p.openInventory(inv);
		return true;
	}

}
