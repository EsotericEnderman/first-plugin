package net.slqmy.first_plugin.commands;

import net.slqmy.first_plugin.FirstPlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SetConfigCommand implements CommandExecutor {
	FirstPlugin firstPlugin;

	public SetConfigCommand(FirstPlugin firstPlugin) {
		this.firstPlugin = firstPlugin;
	}

	@Override
	public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String label, String[] arguments) {
		if (commandSender instanceof Player) {
			Player player = (Player)commandSender;

			if (arguments.length != 2) {
				player.sendMessage("You must provide 2 arguments!");
			} else {
				firstPlugin.getConfig().set("Word", arguments[0]);
				List<String> stringList = firstPlugin.getConfig().getStringList("Fruits");
				stringList.add(arguments[1]);

				firstPlugin.getConfig().set("Fruits", stringList);

				firstPlugin.saveConfig();
			}
		}

		return false;
	}
}
