package com.vampire.rpg.sql;

public enum SqlItem {
	ID("ID","int"), //auto increase
	UUID("UUID","char(36)"),
	NAME("Name","varchar(18)"),
	LEVEL("Level","bigint"),
	EXP("Exp","int"),
	CLASS("ClassType","TINYTEXT"),
	RANK("Rank","TINYTEXT"),
	FIRST_LOGIN("FirstPlayed","TIMESTAMP"), //current timestamp
	LOCATION("Location","TINYTEXT"),
	RECIPES("KnownRecipes","TEXT"),
	OPTIONS("Options","TEXT"),
	SPELL_RLL("SpellRLL","TINYTEXT"),
	SPELL_RLR("SpellRLR","TINYTEXT"),
	SPELL_RRL("SpellRRL","TINYTEXT"),
	SPELL_RRR("SpellRRR","TINYTEXT"),
	SP_LOCATION("SpLocation","TEXT"),
	PROFESSIONS("Professions","TINYTEXT"),
	QUEST_PROGRESS("questProgress","TEXT"),
	INV("Inventory","MEDIUMTEXT"),
	BANK("Bank","MEDIUMTEXT"),
	MAILBOX("Mailbox","MEDIUMTEXT"),
	TRINKET("trinket","MEDIUMTEXT"),
	TRINKET_EXP("trinketExp","MEDIUMTEXT"),
	;
	private String identifier;
	String sqlType;
	String defaultValue;
	SqlItem(String identifier,String type){
		this.identifier=identifier;
		this.sqlType=type;
	}
	public String getID(){
		return identifier;
	}
}
