package me.michaelkrauty.RecordCoord;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class RecordCoord extends JavaPlugin implements Listener{
	
	public void onEnable(){
		getServer().getPluginManager().registerEvents(this, this);
		Server server = Bukkit.getServer();
		ConsoleCommandSender console = server.getConsoleSender();
		console.sendMessage(ChatColor.LIGHT_PURPLE + "test, lol");
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){
		Server server = Bukkit.getServer();
		ConsoleCommandSender console = server.getConsoleSender();
		if(sender instanceof Player){
			Player player = (Player) sender;
			String playerName = player.getName();
			Location playerLocation = player.getLocation();
				if(sender.hasPermission("recordcoord.use")){
					if(commandLabel.equalsIgnoreCase("recordcoords")){
						if(args.length == 1){
							getConfig().set(playerName + "." + args[0] + ".x", playerLocation.getBlockX());
							getConfig().set(playerName + "." + args[0] + ".y", playerLocation.getBlockY());
							getConfig().set(playerName + "." + args[0] + ".z", playerLocation.getBlockZ());
							saveConfig();
							reloadConfig();
							sender.sendMessage(ChatColor.GOLD + "Saved coordinates. Use /recallcoords [name] to view them.");
							return true;
						}else{
							sender.sendMessage("Usage: /recordcoords <name>");
							return true;
						}
					}
					if(commandLabel.equalsIgnoreCase("recallcoords")){
						if(args.length == 1){
							
							int x = getConfig().getInt(playerName + "." + args[0] + ".x");
							int y = getConfig().getInt(playerName + "." + args[0] + ".y");
							int z = getConfig().getInt(playerName + "." + args[0] + ".z");
							
							sender.sendMessage(ChatColor.GOLD + args[0] + ":");
							sender.sendMessage(ChatColor.GOLD + "x: " + x);
							sender.sendMessage(ChatColor.GOLD + "y: " + y);
							sender.sendMessage(ChatColor.GOLD + "z: " + z);
							return true;
						}else{
							sender.sendMessage("Usage: /recallcoords <name>");
							return true;
						}
					}
					if(commandLabel.equalsIgnoreCase("listcoords")){
						if(args.length == 0){
							sender.sendMessage(ChatColor.GOLD + "" + getConfig().getConfigurationSection(playerName).getKeys(false));
							console.sendMessage(ChatColor.GOLD + "" + getConfig().getConfigurationSection(playerName).getKeys(false));
						}
					}
					if(commandLabel.equalsIgnoreCase("removecoords")){
						if(args.length == 1){
							getConfig().set(playerName + args[0], null);
							saveConfig();
							reloadConfig();
							sender.sendMessage(ChatColor.GOLD + "Removed ''" + args[0] + "''");
							return true;
						}else{
							return true;
						}
					}
				}else{
					sender.sendMessage("You don't have permission to do that!");
					return true;
				}
		}else{
			return true;
		}
		return true;
	}
}
