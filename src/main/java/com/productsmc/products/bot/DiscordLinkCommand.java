package com.productsmc.products.bot;

import com.productsmc.products.Products;
import com.productsmc.products.user.User;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class DiscordLinkCommand implements CommandExecutor {

    private final char[] letters = { 'A', 'B', 'G', 'H', 'K', 'R', 'S', 'T' };
    Random random = new Random();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String str, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }

        Player player = (Player) sender;
        User user = Products.getInstance().getUserManager().getUser(player.getUniqueId());

        if(cmd.getName().equalsIgnoreCase("link")) {

            if(user.getDiscordId() != null) {
                player.sendMessage(ChatColor.RED + "You have already linked your Discord Account!");
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 1f, 1f);
                return true;
            }

            Map<UUID, String> botTokens = Products.getInstance().botTokens;
            if(!botTokens.containsKey(player.getUniqueId())) {
                botTokens.put(player.getUniqueId(), "" + getRandomLetter() + getRandomLetter() + ThreadLocalRandom.current().nextInt(100, 999));
            }

            player.sendMessage("");
            player.sendMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + "Discord Linking");
            player.sendMessage("");
            player.sendMessage(ChatColor.YELLOW + "Step 1 " + ChatColor.WHITE + "Join the Discord by typing " + ChatColor.YELLOW + "'/discord'");
            player.sendMessage(ChatColor.YELLOW + "Step 2 " + ChatColor.WHITE + "Navigate to #commands");
            player.sendMessage(ChatColor.YELLOW + "Step 3 " + ChatColor.WHITE + "Type " + ChatColor.YELLOW + " $link " + botTokens.get(player.getUniqueId()) + ChatColor.WHITE + " into #commands.");
            player.sendMessage("");
            player.sendMessage(ChatColor.DARK_GRAY + "On Discord verification receive the following: 1x Amber Rank (for 1 Season), 1x Discord Trail");

            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1f, 1.25f);

        }

        return true;

    }

    public char getRandomLetter() {
        return letters[(int) random.nextInt(letters.length)];
    }

}
