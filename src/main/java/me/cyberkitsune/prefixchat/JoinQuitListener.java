package me.cyberkitsune.prefixchat;

import org.bukkit.ChatColor;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class JoinQuitListener implements Listener{

	private KitsuneChat plugin;
	public JoinQuitListener(KitsuneChat plugin) {
		this.plugin = plugin;
	}
	
	public void onJoin(PlayerJoinEvent evt) {
		if(plugin.vaultEnabled) {
			String playername = ChatColor.translateAlternateColorCodes('&', plugin.vaultChat.getPlayerPrefix(evt.getPlayer())+evt.getPlayer().getDisplayName()+plugin.vaultChat.getPlayerSuffix(evt.getPlayer()));
			evt.setJoinMessage(ChatColor.YELLOW+playername+" has joined the game.");
		}
	}
	
	public void onLeave(PlayerQuitEvent evt) {
		if(plugin.vaultEnabled) {
			String playername = ChatColor.translateAlternateColorCodes('&', plugin.vaultChat.getPlayerPrefix(evt.getPlayer())+evt.getPlayer().getDisplayName()+plugin.vaultChat.getPlayerSuffix(evt.getPlayer()));
			evt.setQuitMessage(ChatColor.YELLOW+playername+" has quit the game.");
		}
	}
	
	
}
