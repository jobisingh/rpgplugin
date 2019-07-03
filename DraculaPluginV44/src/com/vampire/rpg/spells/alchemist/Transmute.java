package com.vampire.rpg.spells.alchemist;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.vampire.rpg.PlayerData;
import com.vampire.rpg.spells.Spell;
import com.vampire.rpg.spells.SpellEffect;
import com.vampire.rpg.spells.SpellbookAlchemist;

public class Transmute extends SpellEffect {

    @Override
    public boolean cast(final Player p, final PlayerData pd, int level) {
        double selfMult = functions[0].applyAsDouble(level) / 100.0;
        int mana = (int) functions[1].applyAsDouble(level);
        int selfDmg = (int) (selfMult * (pd.baseMaxHP + pd.maxHP));
        if (pd.hp <= selfDmg) {
            p.sendMessage(ChatColor.RED + "You don't have enough HP to cast Transmute!");
            return false;
        }
        if (selfDmg >= pd.hp)
            selfDmg = pd.hp - 1;
        pd.damageSelfTrue(selfDmg);
      //  pd.recoverMana(mana + SpellbookAlchemist.TRANSMUTE.manaCost);
        Spell.notify(p, "You convert some HP into mana.");
        return true;
    }
}
