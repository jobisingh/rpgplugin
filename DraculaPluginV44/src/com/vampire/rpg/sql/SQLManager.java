package com.vampire.rpg.sql;

import java.io.File;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.vampire.rpg.AbstractManager;
import com.vampire.rpg.PlayerData;
import com.vampire.rpg.Pluginc;
import com.vampire.rpg.Rank;
import com.vampire.rpg.classes.ClassType;
import com.vampire.rpg.crafting.CraftedAPI;
import com.vampire.rpg.professions.Profession;
import com.vampire.rpg.trinkets.Trinket;
import com.vampire.rpg.utils.VamMessages;
import com.vampire.rpg.utils.VamSerializer;

public class SQLManager extends AbstractManager {
	 public static Connection connection;
	 private static String host, database, username, password;
	 private static int port;
	public SQLManager(Pluginc plugin) {
		super(plugin);
		plugin=Pluginc.getInstance();
		}

	@Override
	public void initialize() {
		host="localhost";
	     port = 1111;
	     database ="data_base_name";
	     username ="root";
	     password = "";
	     File dir = new File(Pluginc.getInstance().getDataFolder(), "database");
	        if (!dir.exists())
	            dir.mkdirs();
	        for (File f : dir.listFiles()) {
	            if (f.getName().endsWith(".txt")) {
	                readFile(f);
	            }
	        }
	}
	public void readFile(File f) {
		 Scanner scan = null;
	        try {
	            scan = new Scanner(f);
	            // name
	             host = scan.nextLine().trim();
	            port = Integer.parseInt(scan.nextLine().trim());
	            database = scan.nextLine().trim();
	             username = scan.nextLine().trim();
	             if(scan.hasNext())
	             password = scan.nextLine().trim();
	            //type
	        }catch (Exception e) {
	            System.out.println("Error reading file " + f.getName());
	            e.printStackTrace();
	        } finally {
	            if (scan != null)
	                scan.close();
	        }
	}

	public synchronized static void OpenConnetion(){
		try{
			connection= DriverManager.getConnection("jdbc:mysql://" + host+ ":" + port + "/" + database, username, password);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public synchronized static void closeConnetion(){
		try{
			connection.close();
		}catch(Exception  e){
			e.printStackTrace();
		}
	}
	public synchronized static boolean tableExists(){
		OpenConnetion();
		try{
			DatabaseMetaData dbm = connection.getMetaData();
			ResultSet rs = dbm.getTables(null, null, "main", null);
			if (rs.next()) {
				return true;
			} else {
	    	return false;
			}
		}catch(Exception e){
			e.printStackTrace();
			System.out.println(ChatColor.RED+"ERROR IN CHECKING IF TABLE EXISTS");
		}
		return true;
	  }
	public synchronized static boolean hasData(Player p){
		try{
			PreparedStatement sql= connection.prepareStatement("SELECT * FROM `main` WHERE UUID=?;");
			sql.setString(1, p.getUniqueId().toString());
			ResultSet result= sql.executeQuery();
			boolean containsplayer = result.next();
			sql.close();
			result.close();
			return containsplayer;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	public static void saveData(Player p){
		OpenConnetion();
		try{
			PlayerData pd= Pluginc.getPD(p);
			PreparedStatement sql= connection.prepareStatement("UPDATE `main` SET Name=?, Level=?,"
					+ "Bank=?,KnownRecipes=?,ClassType=?,Rank=?,"
					+ "SpellRLL=?,SpellRLR=?,SpellRRL=?,SpellRRR=?,SpLocation=?,Options=?,Mailbox=?,Professions=?,questProgress=?,trinket=?,trinketExp=?,Exp=? WHERE UUID=?;");
			sql.setString(1, p.getName());
			sql.setInt(2, pd.level);
			sql.setString(3, desirialise(pd.bank));
			sql.setString(4, CraftedAPI.serializeRecipes(pd.knownRecipes));
			sql.setString(5, pd.classType.toString());
			sql.setString(6, pd.rank.getName());
			sql.setString(7, pd.spell_RLL==null?"":pd.spell_RLL.name);
			sql.setString(8, pd.spell_RLR==null?"":pd.spell_RLR.name);
			sql.setString(9, pd.spell_RRL==null?"":pd.spell_RRL.name);
			sql.setString(10, pd.spell_RRR==null?"":pd.spell_RRR.name);
			sql.setString(11, pd.serializeSPAllocation());
			sql.setString(12, pd.serializeOptions());
			sql.setString(13, pd.serializeMailbox());
		//	System.out.println(pd.serializeOptions());
			StringBuilder sb=new StringBuilder();
			for(Entry<Profession,Integer[]> e:pd.professions.entrySet()){
				sb.append(e.getKey().toString()+"-"+e.getValue()[0]+":"+e.getValue()[1]);
				sb.append(",");
			}
			String s=sb.toString().trim();
			if (s.endsWith(","))
		        s = s.substring(0, s.length() - 1);
			sql.setString(14, s);
			sql.setString(15, pd.serializeQuestProgress());
			sql.setString(16, pd.trinket == null ? "" : pd.trinket.toString());
			sql.setString(17, pd.serializeTrinketExp());
			sql.setLong(18, pd.exp);
			sql.setString(19, p.getUniqueId().toString());
			//sql.setString(5, pd.classType.toString());
		//	System.out.println("NOTICE ME SENPAI");
			System.out.println("Saved SQL for "+p.getName());
			sql.executeUpdate();
			sql.close();
			//pd.unload();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			closeConnetion();
			//pd.unload();
		}
	}
	public static void loadData(Player p){
		OpenConnetion();
		try{
			PlayerData pd= Pluginc.getPD(p);
			if(tableExists()){
				if(hasData(p)){
					PreparedStatement sql=connection.prepareStatement("SELECT Level,Bank,KnownRecipes,ID,FirstPlayed,"
							+ "ClassType,Rank,SpellRLL,SpellRLR,SpellRRL,SpellRRR,SpLocation,Options,Mailbox,Professions,questProgress,trinket,trinketExp,Exp FROM main WHERE UUID=?;");
					sql.setString(1, p.getUniqueId().toString());
					ResultSet result = sql.executeQuery();
					result.next();
					pd.level= result.getInt("Level");
					pd.exp=result.getLong("Exp");
					Inventory inv= Bukkit.createInventory(p, 54, "Bank");
					pd.loadBank(result.getString("Bank"),inv);				
					pd.knownRecipes=CraftedAPI.deserializeStringToRecipes(result.getString("KnownRecipes"));
					p.sendMessage(org.bukkit.ChatColor.GREEN+"ID IS:"+ String.valueOf(result.getInt("ID")));
					pd.firstplayed= result.getTimestamp("FirstPlayed");
					pd.classType= ClassType.getClassType(result.getString("ClassType"));
					pd.rank=Rank.valueOf(result.getString("Rank").toUpperCase());
					pd.optionsList = pd.deserializeOptions(result.getString("Options"));
					pd.spell_RLL=pd.getSpellForName(result.getString("SpellRLL"));
					pd.spell_RLR=pd.getSpellForName(result.getString("SpellRLR"));
					pd.spell_RRL=pd.getSpellForName(result.getString("SpellRRL"));
					pd.spell_RRR=pd.getSpellForName(result.getString("SpellRRR"));
					pd.trinket=Trinket.getTrinket(result.getString("trinket"));
					pd.deserializeTrinketExp(result.getString("trinketExp"));
					pd.deserializeMailbox(result.getString("Mailbox"));
					if(result.getString("Professions")!=null && result.getString("Professions").length()>0){
						pd.professions=deserializeProfessions(result.getString("Professions"));
					//String s=result.getString("Mining");
				//	pd.miningLvl=Integer.valueOf(s.split(":")[0]);;
				//	pd.miningExp=Integer.valueOf(s.split(":")[1]);
					}else{
						StringBuilder sb=new StringBuilder();
						for(Profession pr:Profession.values()){
							sb.append(pr.toString()+"-1:0");
							sb.append(",");
						}
						String s=sb.toString().trim();
						if (s.endsWith(","))
					        s = s.substring(0, s.length() - 1);
						pd.professions=deserializeProfessions(s);
					//	pd.miningExp=0;
					//	pd.miningLvl=1;
					}
					
				//	pd.spellLevels=pd.deserializeSPAllocation(result.);
					pd.deserializeQuestProgress(result.getString("questProgress"));
					pd.deserializeSPAllocation(result.getString("SpLocation"));
					System.out.println("Loaded SQL for "+p.getName());
				//	pd.PostLoad();
					sql.close();
					result.close();
				}
				else{																																					//11	//12 		//13	//14		//15  //16    //17        //18			//19	//20	  //21
					PreparedStatement sql= connection.prepareStatement("INSERT INTO `main`(UUID,Name,Level,Bank,Inventory,Location,ClassType,Rank,KnownRecipes,Options,SpellRLL,SpellRLR,SpellRRL,SpellRRR,SpLocation,Mailbox,Professions,questProgress,trinket,trinketExp,Exp) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);");
					sql.setString(1, p.getUniqueId().toString());
					sql.setString(2, p.getName());
					sql.setInt(3, 1);
					sql.setString(4, "null");
					sql.setString(5, "null");
					sql.setString(6, "null");
					sql.setString(7, "VILLAGER");
					sql.setString(8, "MEMBER");
					sql.setString(9, "null");
					sql.setString(10, "");
					sql.setString(11, "");
					sql.setString(12, "");
					sql.setString(13, "");				
					sql.setString(14, "");
					sql.setString(15, "");
					sql.setString(16, "");
					StringBuilder sb=new StringBuilder();
					for(Profession pr:Profession.values()){
						sb.append(pr.toString()+"-1:0");
						sb.append(",");
					}
					String s=sb.toString().trim();
					if (s.endsWith(","))
				        s = s.substring(0, s.length() - 1);
					sql.setString(17, s);
					sql.setString(18, "");
					sql.setString(19, "");
					sql.setString(20, "");
					sql.setLong(21, 0);
					sql.execute();
					sql.close();
					PreparedStatement sql1=connection.prepareStatement("SELECT Level,Bank,KnownRecipes,ID,FirstPlayed,"
							+ "ClassType,Rank,SpellRLL,SpellRLR,SpellRRL,SpellRRR,SpLocation,Options,Mailbox,Professions,questProgress,trinket,trinketExp,Exp FROM main WHERE UUID=?;");
					sql1.setString(1, p.getUniqueId().toString());
					ResultSet result = sql1.executeQuery();
					result.next();
					pd.level=1;
					Inventory inv= Bukkit.createInventory(p, 54, "Bank");
					//loadBank(result.getString("Bank"),inv);
					pd.loadBank(result.getString("Bank"),inv);	
					pd.exp=result.getLong("Exp");
					pd.knownRecipes=CraftedAPI.deserializeStringToRecipes(result.getString("KnownRecipes"));
					//p.sendMessage(org.bukkit.ChatColor.GREEN+"ID IS:"+ String.valueOf(result.getInt("ID")));
					p.sendMessage(org.bukkit.ChatColor.GREEN+"ID IS:"+ String.valueOf(result.getInt("ID")));
					pd.firstplayed= result.getTimestamp("FirstPlayed");
					pd.classType= ClassType.getClassType(result.getString("ClassType"));
					pd.rank=Rank.valueOf(result.getString("Rank").toUpperCase());
					pd.optionsList = pd.deserializeOptions(result.getString("Options"));
					pd.spell_RLL=pd.getSpellForName(result.getString("SpellRLL"));
					pd.spell_RLR=pd.getSpellForName(result.getString("SpellRLR"));
					pd.spell_RRL=pd.getSpellForName(result.getString("SpellRRL"));
					pd.spell_RRR=pd.getSpellForName(result.getString("SpellRRR"));
					pd.trinket=Trinket.getTrinket(result.getString("trinket"));
					pd.deserializeTrinketExp(result.getString("trinketExp"));
					pd.deserializeMailbox(result.getString("Mailbox"));
				//	String s=result.getString("Mining");
					pd.professions=deserializeProfessions(result.getString("Professions"));
			//		pd.miningLvl=Integer.valueOf(s.split(":")[0]);;
			//		pd.miningExp=Integer.valueOf(s.split(":")[1]);
				//	pd.spellLevels=pd.deserializeSPAllocation(result.);
					pd.deserializeQuestProgress(result.getString("questProgress"));
					pd.deserializeSPAllocation(result.getString("SpLocation"));
					System.out.println("CREATED and Loaded SQL for "+p.getName());
					sql1.execute();
					sql1.close();
					
				}
				pd.loadedSQL=true;
				pd.PostLoad();
				p.sendMessage(org.bukkit.ChatColor.GREEN+"Loaded succesfully your data");
			}else{
				createTable();
				System.out.println("DETECTED NO TABLE AND CREATED ONE");
				loadData(p);
			}
		}catch(Exception e){
			e.printStackTrace();
			p.sendMessage(ChatColor.BLUE+"there was an error loading your data");
			p.sendMessage(ChatColor.BLUE+"Dont worry, Most likely it's still saved.");
			p.sendMessage(ChatColor.RED+"Please report this to TheBloodyVampire/Undvik");

		}finally{
			closeConnetion();
		}
		
	}
	public static void createTable(){
		StringBuilder sb=new StringBuilder();
		sb.append("CREATE TABLE main ( ");
		for(SqlItem s: SqlItem.values()){
			sb.append(s.getID()+" ");
			sb.append(s.sqlType+" ");
			sb.append("NOT NULL ");
			if(s.getID().equals("FirstPlayed"))
			sb.append("DEFAULT CURRENT_TIMESTAMP ");
			if(s.getID().equals("ID"))
				sb.append("AUTO_INCREMENT");
			sb.append(",");
		}
		String s=sb.toString().trim();
		s+="PRIMARY KEY (ID)";
		if (s.endsWith(","))
	        s = s.substring(0, s.length() - 1);
		s+=");";
	//	VamMessages.announce(s);
		OpenConnetion();
		try {
			PreparedStatement sql=connection.prepareStatement(s);
			sql.execute();
			sql.close();
		} catch (SQLException e) {
			System.out.println(ChatColor.RED+"CREATING TABLE-ERROR");
			e.printStackTrace();
		}finally{
			//VamMessages.announce(ChatColor.RED+"finally in db code");
			closeConnetion();
		}
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

	public static void deleteTable() {
		OpenConnetion();
		try {
			PreparedStatement sql=connection.prepareStatement("DROP TABLE `main`");
			sql.execute();
			sql.close();
			VamMessages.announce(ChatColor.RED+"DELETED DB");
		} catch (SQLException e) {
			System.out.println(ChatColor.RED+"DELETING TABLE-ERROR");
			e.printStackTrace();
		}finally{
			//VamMessages.announce(ChatColor.RED+"finally in db code");
			closeConnetion();
		}
	}

}
