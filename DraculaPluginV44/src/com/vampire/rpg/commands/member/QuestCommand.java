package com.vampire.rpg.commands.member;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.vampire.rpg.PlayerData;
import com.vampire.rpg.commands.AbstractCommand;
import com.vampire.rpg.quests.QuestManager;

public class QuestCommand extends AbstractCommand {

    public QuestCommand(String... commandNames) {
        super(commandNames);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
    }

    @Override
    public void executePlayer(final Player p, PlayerData pd, String[] args) {
        QuestManager.showQuestMenu(pd);
    }

    @Override
    public void executeConsole(CommandSender sender, String[] args) {
    }

}
