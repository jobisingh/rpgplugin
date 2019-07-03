package com.vampire.rpg.badges;

import org.bukkit.ChatColor;

public enum Badge {

    BETA("Beta Tester", "This player played Zentrela extensively before its release on June 16, 2016.", "\u03B2", ChatColor.RED), // β
    GAMMA("Gamme","balha","a",ChatColor.DARK_PURPLE),
    QUESTWRITER("Questwriter", "This player has written 10 or more quests that have been added to Drakona.", "\u270E", ChatColor.AQUA), // 
    SUPPORTER("Super Supporter", "Purchased from the the store!", "\u2764", ChatColor.LIGHT_PURPLE), // ❤
    LEVEL_100("Power Leveler", "This player was the first to reach level 100 in [].", "\u2B06", ChatColor.GOLD), // 
    WIKI_TEAM("Wiki Team", "This player has made significant contributions to the Drakona wiki.", "\u270D", ChatColor.GREEN), // 
    YT_1("Tier 1 Youtuber", "This player has made Youtube videos of Drakona receiving over 5000 views in total.", "\u278A", ChatColor.RED), // 
    YT_2("Tier 2 Youtuber", "This player has made Youtube videos of Drakona receiving over 20000 views in total.", "\u278B", ChatColor.RED), // 
    YT_3("Tier 3 Youtuber", "This player has made Youtube videos of Drakona receiving over 50000 views in total.", "\u278C", ChatColor.RED), // 
    YT_4("Tier 4 Youtuber", "This player has made Youtube videos of Drakona receiving over 150000 views in total.", "\u278D", ChatColor.RED), // 
    YT_5("Tier 5 Youtuber", "This player has made Youtube videos of Drakona receiving over 1000000 views in total.", "\u278E", ChatColor.RED), // 
    BUILDER("Builder", "This player is on the [] build team.", "\u2692", ChatColor.AQUA), //
    ACHIEVER("Achiever","This player has completed all of []'s achievements.","\u2605",ChatColor.GOLD),
    PORTAL_QUEST("Portal Quest","This player was the first to complete the portal mission.","\u058E",ChatColor.DARK_BLUE),
    ;//�?
    private String displayName;
    private String description;
    private String display;
    private ChatColor color;

    private String cacheDisplayName;
    private String cacheDescription;
    private String cacheDisplay;
    private String cacheTooltip;

    public String getDisplayName() {
        if (cacheDisplayName != null)
            return cacheDisplayName;
        return cacheDisplayName = color + displayName;
    }

    public String getDescription() {
        if (cacheDescription != null)
            return cacheDescription;
        return cacheDescription = ChatColor.GRAY + description;
    }

    public String getDisplay() {
        if (cacheDisplay != null)
            return cacheDisplay;
        return cacheDisplay = color + display;
    }

    public String getTooltip() {
        if (cacheTooltip != null)
            return cacheTooltip;
        return cacheTooltip = getDisplayName() + "\n" + getDescription();
    }

    Badge(String displayName, String description, String display, ChatColor color) {
        this.displayName = displayName;
        this.description = description;
        this.display = display;
        this.color = color;
    }
    //TODO save badges to SQL

}
