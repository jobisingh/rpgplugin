package com.vampire.rpg.options;

public enum OptionType {
	CHAT("Chat",new Option[]{Option.FORCE_GLOBAL_CHAT,Option.LOCAL_CHAT_WARNING,Option.CHAT_FILTER,Option.GLOBAL_CHAT,Option.LOCAL_CHAT}),
	CONSOLE_MESSAGES("Message",new Option[]{Option.MANA_MESSAGES,Option.DAMAGE_MESSAGES,Option.EXP_MESSAGES,Option.HEAL_MESSAGES,Option.SPELL_MESSAGES,Option.LEVEL_ANNOUNCEMENTS,Option.ITEM_PROTECT,Option.SET_NOTIFICATION}),
	DISPLAY("Display",new Option[]{Option.WEATHER_REGIONS,Option.TAGS_ABOVE_ITEMS}),
	;
	String name;
	Option[] options;
	OptionType(String name,Option[] options){
		this.name=name;
		this.options=options;
	}
}
