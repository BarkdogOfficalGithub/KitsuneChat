package com.cyberkitsune.prefixchat;

import java.util.List;
import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;

public class ChatListener implements Listener {

	private KitsuneChat plugin;
	private KitsuneChatUtils util;

	public ChatListener(KitsuneChat plugin) {
		this.plugin = plugin;
		util = new KitsuneChatUtils(plugin);
	}

	/**
	 * playerChat handles capturing the event and doing silly things with it.
	 * 
	 * @param evt
	 *            The event! :o
	 */
	@EventHandler
	public void playerChat(PlayerChatEvent evt) {
		evt.setCancelled(true);
		String message = KitsuneChatUtils.colorizeString(evt.getMessage());
		String prefix = "<" + evt.getPlayer().getDisplayName() + ">";
		/*
		 * So, first split the first char to see what channel it goes to. Then,
		 * colorize, Finally, send to just the group
		 */
		if (evt.getMessage().startsWith(plugin.getConfig().getString("global.prefix"))) {
			Set<Player> everybody = evt.getRecipients();
			for (Player plr : everybody) {
				plr.sendMessage(ChatColor.DARK_AQUA
						+ "["+evt.getPlayer().getWorld().getName()+"] " + ChatColor.RESET
						+ prefix
						+ " "
						+ message.replaceFirst(
								"\\"+plugin.getConfig().getString("global.prefix"), ""));
			}
		} else if (evt.getMessage().startsWith(
				plugin.getConfig().getString("world.prefix"))) {
			List<Player> worldPlayers = evt.getPlayer().getWorld().getPlayers();
			for (Player plr : worldPlayers) {
				plr.sendMessage(ChatColor.GOLD + "["+plugin.getConfig().getString("world.prefix")+"] " + ChatColor.RESET
						+ prefix
						+ " " 
						+ message.replaceFirst(
								"\\"+plugin.getConfig().getString("world.prefix"), ""));
			}
		} else if (evt.getMessage().startsWith(
				plugin.getConfig().getString("admin.prefix"))) {
			if (evt.getPlayer().hasPermission("kitsunechat.adminchat")) {
				for (Player plr : plugin.getServer().getOnlinePlayers()) {
					if (plr.hasPermission("kitsunechat.adminchat")) {
						plr.sendMessage(ChatColor.GREEN
								+ "[@] " + ChatColor.RESET
								+ prefix
								+ " "
								+ message.replaceFirst("\\"+plugin.getConfig()
										.getString("admin.prefix"), ""));
					}
				}
			} else {
				evt.getPlayer()
						.sendMessage(
								ChatColor.RED
										+ "You do not have permissions to use admin chat.");
			}
		} else if (evt.getMessage().startsWith(
				plugin.getConfig().getString("party.prefix"))) {
			if (plugin.chans.isInAChannel(evt.getPlayer())) {
				Set<Player> channelPlayers = plugin.chans
						.getChannelMembers(plugin.chans.getChannelName(evt
								.getPlayer()));
				for (Player plr : channelPlayers) {
					plr.sendMessage(ChatColor.YELLOW
							+ "["
							+ plugin.chans.getChannelName(evt.getPlayer())
							+ "] " + ChatColor.RESET
							+ prefix
							+ " "
							+ message.replaceFirst("\\"+plugin.getConfig()
									.getString("party.prefix"), ""));
				}
			} else {
				evt.getPlayer()
						.sendMessage(
								ChatColor.YELLOW
										+ "[KitsuneChat] You are not currently in a channel.");
			}

		} else {
			Set<Player> local = KitsuneChatUtils.getNearbyPlayers(plugin
					.getConfig().getInt("local.radius"), evt.getPlayer());
			for (Player plr : local) {
				plr.sendMessage(prefix + " " + message);
			}
		}

	}

}
