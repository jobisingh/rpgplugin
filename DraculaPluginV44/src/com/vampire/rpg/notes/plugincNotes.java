package com.vampire.rpg.notes;

import java.lang.reflect.Field;

public class plugincNotes {
	//	e.setCancelled(true);
    //         try {
			//		protocolManager.sendServerPacket(e.getPlayer(), packeta.getHandle());
	//			} catch (InvocationTargetException e1) {
					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}
        //     ((CraftPlayer)e.getPlayer()).getHandle().playerConnection.sendPacket((Packet<?>) packeta.getHandle().getHandle());
          //   WrappedWatchableObject oname = meta.get(2);
          //   EntityItem ea=(EntityItem) packeta.getEntity(e.getPlayer().getWorld());
             Field f;
          //   WrappedDataWatcher da= new WrappedDataWatcher();
        //     da.getob
           /*  try{
             	f = ItemEntity.class.getDeclaredField("c");
					f.setAccessible(true);
					
             }catch(Exception e){
             	
             }
             ItemStackea.getDataWatcher().get(datawatcherobject)*/
        

			// packet.getenti
             
             
             /* protocolManager.addPacketListener(
				new PacketAdapter(this, ListenerPriority.NORMAL, 
								   PacketType.Play.Server.ENTITY_METADATA) {
			@Override
			public void onPacketSending(PacketEvent event) {
				PacketContainer packet = event.getPacket();
				
				// You may also want to check event.getPacketID()
				final Entity entity = packet.getEntityModifier(event.getPlayer().getWorld()).read(0);
				final String name = entityName.get(entity.getUniqueId(), event.getPlayer().getName());
				 if (event.getPacketType() == PacketType.Play.Server.ENTITY_METADATA) {
	                   // Main.log.info("Packet of metadata");
	                    WrapperPlayServerEntityMetadata packet = new WrapperPlayServerEntityMetadata(event.getPacket().deepClone());
	                    try {
	                        if (packet.getEntity(event.getPlayer().getWorld()).getType() != EntityType.ARMOR_STAND) return;
	                    } catch (NullPointerException npe) { return; }
	                    List<WrappedWatchableObject> meta = packet.getMetadata();
	                    if (meta.get(2) == null) {
	                        log.info("No custom name");
	                        return;
	                    }
	                    WrappedWatchableObject oname = meta.get(2);
	                    String serializedName = (String) oname.getValue();
	                    Main.log.info("Serialized name is "+serializedName);
	                    if (!serializedName.contains("@")) {
	                        try {
	                            Integer.valueOf(serializedName);
	                        } catch (NumberFormatException ee) { return; }
	                    }
	                    if (serializedName.contains("@")) {
	                        String[] splittedName = serializedName.split("@");
	                        String name = TranslatedString.getString(Integer.valueOf(splittedName[0]), evt.getPlayer());
	                        int level = Integer.valueOf(splittedName[1]);
	                        name += " §7[§6Lv. "+(level < 10 ? "0"+level : level)+"§7]";
	                        oname.setValue(name);
	                    } else {
	                        String name = TranslatedString.getString(Integer.valueOf(serializedName), evt.getPlayer());
	                        oname.setValue(name);
	                    }
	                    meta.set(2, oname);
	                    packet.setMetadata(meta);
	                    evt.setCancelled(true);
	                    ((CraftPlayer)evt.getPlayer()).getHandle().playerConnection.sendPacket((Packet<?>) packet.getHandle().getHandle());
	                }
	            }				
				if (name != null) {
					// Clone the packet!
					event.setPacket(packet = packet.deepClone());
				
					// This comes down to a difference in what the packets store in memory
					if (event.getPacketID() == Packets.Server.ENTITY_METADATA) {
						WrappedDataWatcher watcher = new WrappedDataWatcher(packet.getWatchableCollectionModifier().read(0));
						
						System.out.println("Set entity name " + name + " for " + event.getPlayer());
						processDataWatcher(watcher, name);
						packet.getWatchableCollectionModifier().write(0, watcher.getWatchableObjects());
					} else {
						System.out.println("Spawn entity with name " + name + " for " + event.getPlayer());
						processDataWatcher(packet.getDataWatcherModifier().read(0), name);
					}
				}
			}
		});*/
             
             
             
             
             
             
             
             
             
             
             /*public synchronized static void OpenConnetion(){
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
     			PlayerData pd= getPD(p);
     			PreparedStatement sql= connection.prepareStatement("UPDATE `main` SET Name=?, Level=?,"
     					+ "Bank=?,KnownRecipes=?,ClassType=?,Rank=?,"
     					+ "SpellRLL=?,SpellRLR=?,SpellRRL=?,SpellRRR=?,SpLocation=?,Options=?,Mailbox=?,Professions=?,questProgress=? WHERE UUID=?;");
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
     			sql.setString(16, p.getUniqueId().toString());
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
     	}*/
}
