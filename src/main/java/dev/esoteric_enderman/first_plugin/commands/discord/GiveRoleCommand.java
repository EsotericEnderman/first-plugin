package dev.esoteric_enderman.first_plugin.commands.discord;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import dev.esoteric_enderman.first_plugin.FirstPlugin;
import dev.esoteric_enderman.first_plugin.types.AbstractCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class GiveRoleCommand extends AbstractCommand {
	private final JDA jda;

	public GiveRoleCommand(@NotNull final FirstPlugin plugin) {
		super(
						"give-role",
						"Give a user on Discord a role. From Minecraft...",
						"/give-role <guild ID> <user ID> <role ID>",
						new Integer[]{3},
						new String[]{},
						"first_plugin.give_role",
						true);

		jda = plugin.getJDA();
	}

	@Override
	public boolean execute(@NotNull final CommandSender sender, @NotNull final String @NotNull [] args) {
		final String guildID = args[0];
		final Guild guild = jda.getGuildById(guildID);

		final Player player = (Player) sender;

		if (guild == null) {
			player.sendMessage(ChatColor.RED + "Invalid guild ID!");

			return false;
		}

		final String userID = args[1];
		final Member member = guild.getMemberById(userID);

		if (member == null) {
			player.sendMessage(ChatColor.RED + "Invalid member ID!");

			return false;
		}

		final String roleID = args[2];
		final Role role = guild.getRoleById(roleID);

		if (role == null) {
			player.sendMessage(ChatColor.RED + "Invalid role ID!");

			return false;
		}

		guild.addRoleToMember(member, role).queue();

		player.sendMessage(ChatColor.GREEN + "Role added successfully!");

		return true;
	}

	@Override
	public @Nullable List<String> onTabComplete(@NotNull final CommandSender sender, @NotNull final String[] args) {
		return null;
	}
}
