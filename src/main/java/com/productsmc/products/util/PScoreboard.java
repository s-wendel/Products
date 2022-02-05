package com.productsmc.products.util;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class PScoreboard {
	
	private Scoreboard board;
	private Objective obj;
	
	public PScoreboard() {
		board = Bukkit.getScoreboardManager().getNewScoreboard();
		obj = board.registerNewObjective("products", "dummy", ChatColor.YELLOW + "" + ChatColor.BOLD + "PRODUCTS " + ChatColor.WHITE + "" + ChatColor.BOLD + "S2");
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);
	}
	
	public void setRow(String name, String identifier, int score, String text) {
		Team team = null;
		if(board.getTeam(name) == null) {
			team = board.registerNewTeam(name);
		} else {
			team = board.getTeam(name);
		}
		team.addEntry(identifier);
		team.setPrefix(text);
		obj.getScore(identifier).setScore(score);
	}
	
	public void show(Player player) {
		player.setScoreboard(board);
	}
	
}
