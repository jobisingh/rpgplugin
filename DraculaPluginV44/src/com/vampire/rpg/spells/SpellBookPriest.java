package com.vampire.rpg.spells;

import com.vampire.rpg.spells.paladin.HolyGuardian;
import com.vampire.rpg.spells.priest.DivineBeam;
import com.vampire.rpg.spells.priest.GodsHand;
import com.vampire.rpg.spells.priest.Sanctus;

public class SpellBookPriest extends Spellbook {
	public static final Spell Prayer= new Spell("Prayer",0,7,1,8,new String[]{
			"Pray to your gods for knowledge",
			"Pray Stronger to your gods for knowledge",
			"Pray even Stronger to your gods for knowledge",
			"You can feel the gods almost blessing you",
			"They.Hear.You",
			"You are near perfect for the gods to tell you the secret of life",
			"You now know how to extract the essence of life better",			
	},null,new PassiveSpellEffect());
	public static final Spell OP_STUFF = new Spell("Knowledge",0,4,2,8,new String[] {
			 "Gain +5% EXP from all sources.",
	         "Gain +10% EXP from all sources.",
	         "Gain +15% EXP from all sources.",
	         "Gain +17% EXP from all sources.",
	         "Gain +20% EXP from all sources.",
	},new Object[]{
			Prayer,
			7
	}, new PassiveSpellEffect());
	public static final Spell GODS_HAND = new Spell("Gods Hand", 7, 14, 1, 3, new String[]{
			"Protect your nearby allies from 25% of the damage taken for 2 seconds",
			"Protect your nearby allies from 25% of the damage taken for 3 seconds",
			"Protect your nearby allies from 25% of the damage taken for 4 seconds",
			"Protect your nearby allies from 25% of the damage taken for 5 seconds",
			"Protect your nearby allies from 25% of the damage taken for 6 seconds",
			"Protect your nearby allies from 25% of the damage taken for 7 seconds",
			"Protect your nearby allies from 25% of the damage taken for 8 seconds",
			"Protect your nearby allies from 25% of the damage taken for 9 seconds",
			"Protect your nearby allies from 25% of the damage taken for 10 seconds",
			"Protect your nearby allies from 25% of the damage taken for 11 seconds",
			"Protect your nearby allies from 25% of the damage taken for 12 seconds",
			"Protect your nearby allies from 25% of the damage taken for 13 seconds",
			"Protect your nearby allies from 25% of the damage taken for 14 seconds",
			"Protect your nearby allies from 25% of the damage taken for 15 seconds",
	}, null, new GodsHand());
	public static final Spell SANCTUS= new Spell("Sanctus",3,10,1,0,new String[]{
			"Heal yourself for 120% damage",
			"Heal yourself for 140% damage",
			"Heal yourself for 160% damage",
			"Heal yourself for 180% damage",
			"Heal yourself for 200% damage",
			"Heal yourself for 220% damage",
			"Heal yourself for 240% damage",
			"Heal yourself for 260% damage",
			"Heal yourself for 280% damage",
			"Heal yourself for 300% damage",
	},null,new Sanctus());
	public static final Spell Beam= new Spell("Divine Beam",4,9,1,1,new String[]{
			"Shoot a blessed beam that deals 140% damage.",
			"Shoot a blessed beam that deals 160% damage.",
			"Shoot a blessed beam that deals 180% damage.",
			"Shoot a blessed beam that deals 200% damage.",
			"Shoot a blessed beam that deals 220% damage.",
			"Shoot a blessed beam that deals 240% damage.",
			"Shoot a blessed beam that deals 260% damage.",
			"Shoot a blessed beam that deals 280% damage.",
			"Shoot a blessed beam that deals 300% damage.",
	},null,new DivineBeam());
	public static final Spell Holy= new Spell("Pure Light",4,7,1,2,new String[]{
			"Heal your nearby allies for 120% damage",
			"Heal your nearby allies for 130% damage",
			"Heal your nearby allies for 140% damage",
			"Heal your nearby allies for 150% damage",
			"Heal your nearby allies for 160% damage",
			"Heal your nearby allies for 170% damage",
			"Heal your nearby allies for 180% damage",
	},null, new com.vampire.rpg.spells.priest.Holy());
	public static final Spell HEAL_ENHANCE = new Spell("Heal Enhance", 0, 6, 1, 7, new String[] {
	        "Gain +10% healing from all sources except HP regeneration.",
	        "Gain +12% healing from all sources except HP regeneration.",
	        "Gain +14% healing from all sources except HP regeneration.",
	        "Gain +16% healing from all sources except HP regeneration.",
	        "Gain +18% healing from all sources except HP regeneration.",
	        "Gain +20% healing from all sources except HP regeneration.",
	 }, null, new PassiveSpellEffect());
	@Override
	public Spell[] getSpellList() {
		return SPELL_LIST;
	}
    public static final Spellbook INSTANCE = new SpellBookPriest();
    private static final Spell[] SPELL_LIST = {
    		Prayer,
    		OP_STUFF,
    		Beam,
    		Holy,
    		SANCTUS,
    		HEAL_ENHANCE,
    		GODS_HAND,
    };

}
