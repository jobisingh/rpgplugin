package com.vampire.rpg.spells;

import com.vampire.rpg.spells.barbarian.Battlecry;
import com.vampire.rpg.spells.barbarian.Berserk;
import com.vampire.rpg.spells.barbarian.BloodPact;
import com.vampire.rpg.spells.barbarian.Chains;
import com.vampire.rpg.spells.barbarian.HulkSmash;
import com.vampire.rpg.spells.barbarian.Wrath;

public class SpellbookBarbarian extends Spellbook {
	
	public static final Spell WRATH= new Spell("Wrath ", 6, 5, 1, 1, new String[]{
			"Sacrifice 20% of your maximum HP to deal 70% bonus damage for 10 seconds.",
            "Sacrifice 25% of your maximum HP to deal 80% bonus damage for 10 seconds.",
            "Sacrifice 30% of your maximum HP to deal 90% bonus damage for 10 seconds.",
            "Sacrifice 35% of your maximum HP to deal 100% bonus damage for 10 seconds.",
            "Sacrifice 40% of your maximum HP to deal 110% bonus damage for 10 seconds.",
	}, null, new Wrath());
	public static final Spell BATTLECRY= new Spell("Battle Cry",2,10,1,0,new String[]{
		"Scream at the enemies in front of you, dealing 120% damage",
		"Scream at the enemies in front of you, dealing 140% damage",
		"Scream at the enemies in front of you, dealing 160% damage",
		"Scream at the enemies in front of you, dealing 180% damage",
		"Scream at the enemies in front of you, dealing 200% damage",
		"Scream at the enemies in front of you, dealing 220% damage",
		"Scream at the enemies in front of you, dealing 240% damage",
		"Scream at the enemies in front of you, dealing 260% damage",
		"Scream at the enemies in front of you, dealing 280% damage",
		"Scream at the enemies in front of you, dealing 300% damage",
	},null, new Battlecry());
	public static final Spell BLOOD_PACT= new Spell("Blood Pact", 5, 5, 2, 1, new String[] {
            "Sacrifice 8% of your maximum HP and deal that amount over 2 seconds to a single enemy target.",
            "Sacrifice 11% of your maximum HP and deal that amount over 2 seconds to a single enemy target.",
            "Sacrifice 14% of your maximum HP and deal that amount over 2 seconds to a single enemy target.",
            "Sacrifice 17% of your maximum HP and deal that amount over 2 seconds to a single enemy target.",
            "Sacrifice 20% of your maximum HP and deal that amount over 2 seconds to a single enemy target.",
    }, null, new BloodPact());
	public static final Spell CHAINS= new Spell("Chains",5,10,1,3,new String[]{
			"Draw in enemies towards you and create a small explosion dealing 250% damage to the closest enemies.",
            "Draw in enemies towards you and create a small explosion dealing 270% damage to the closest enemies.",
            "Draw in enemies towards you and create a small explosion dealing 290% damage to the closest enemies.",
            "Draw in enemies towards you and create a small explosion dealing 310% damage to the closest enemies.",
            "Draw in enemies towards you and create a small explosion dealing 330% damage to the closest enemies.",
            "Draw in enemies towards you and create a small explosion dealing 350% damage to the closest enemies.",
            "Draw in enemies towards you and create a small explosion dealing 370% damage to the closest enemies.",
            "Draw in enemies towards you and create a small explosion dealing 390% damage to the closest enemies.",
            "Draw in enemies towards you and create a small explosion dealing 410% damage to the closest enemies.",
            "Draw in enemies towards you and create a small explosion dealing 430% damage to the closest enemies.",		
	},null,new Chains());
	public static final Spell SMASH= new Spell("Hulk Smash",5,10,2,1,new String[]{
			"Jump a short distance forward, dealing 150% damage to enemies nearby when you land.",
            "Jump a short distance forward, dealing 175% damage to enemies nearby when you land.",
            "Jump a short distance forward, dealing 200% damage to enemies nearby when you land.",
            "Jump a short distance forward, dealing 225% damage to enemies nearby when you land.",
            "Jump a short distance forward, dealing 250% damage to enemies nearby when you land.",
            "Jump a short distance forward, dealing 275% damage to enemies nearby when you land.",
            "Jump a short distance forward, dealing 300% damage to enemies nearby when you land.",
            "Jump a short distance forward, dealing 325% damage to enemies nearby when you land.",
            "Jump a short distance forward, dealing 350% damage to enemies nearby when you land.",
            "Jump a short distance forward, dealing 375% damage to enemies nearby when you land.",
	},null,new HulkSmash());
	public static final Spell AXE_MASTERY= new Spell("Axes Mastery",0,5,1,8,new String[]{
			 "Deal 2% increased base damage.",
	         "Deal 4% increased base damage.",
	         "Deal 6% increased base damage.",
	         "Deal 8% increased base damage.",
	         "Deal 10% increased base damage.",
	},null,new PassiveSpellEffect());
	public static final Spell BERSERK= new Spell("Berserk",7,7,2,0,new String[]{
			"test",
			"test",
			"test",
			"test",
			"test",
			"test",
			"test",
	},new Object[]{
			BLOOD_PACT,
			5,
	},new Berserk());
	@Override
	public Spell[] getSpellList() {
		return SPELL_LIST;
	}
	 public static final Spellbook INSTANCE = new SpellbookBarbarian();
	 private static final Spell[] SPELL_LIST = {
			 WRATH,
			 BATTLECRY,
			 BLOOD_PACT,
			 CHAINS,
			 BERSERK,
	    };
}
