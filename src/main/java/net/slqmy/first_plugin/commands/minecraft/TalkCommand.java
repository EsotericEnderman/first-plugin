package net.slqmy.first_plugin.commands.minecraft;

import com.theokanning.openai.OpenAiService;
import com.theokanning.openai.completion.CompletionRequest;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import net.slqmy.first_plugin.Main;
import net.slqmy.first_plugin.types.AbstractCommand;
import net.slqmy.rank_system.managers.RankManager;
import net.slqmy.rank_system.types.Rank;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitScheduler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class TalkCommand extends AbstractCommand implements Listener {
	private final BukkitScheduler SCHEDULER = Bukkit.getScheduler();
	private final OpenAiService service;
	private final Main plugin;
	private final RankManager rankManager;
	private final HashMap<UUID, StringBuilder> conversations = new HashMap<>();

	public TalkCommand(@NotNull final Main plugin) {
		super(
						"talk",
						"Talk to the legendary Slime GPT AI!",
						"/talk",
						new Integer[]{0},
						new String[]{"ai", "slime-gpt"},
						"first_plugin.talk",
						true
		);

		this.plugin = plugin;
		this.rankManager = plugin.getRankSystem().getRankManager();
		this.service = new OpenAiService(plugin.getConfig().getString("OpenAI-Key"), 0);
	}

	@Override
	public boolean execute(@NotNull final CommandSender sender, @NotNull final String[] args) {
		final Player player = (Player) sender;
		final UUID uuid = player.getUniqueId();

		if (conversations.containsKey(uuid)) {
			conversations.remove(uuid);

			player.sendMessage(
							ChatColor.DARK_GRAY.toString() + ChatColor.BOLD + "\n| " + ChatColor.GRAY + "Your conversation with "
											+ ChatColor.DARK_GREEN + ChatColor.BOLD + "SlimeGPT " + ChatColor.GRAY + "has ended.\n "
			);
		} else {
			conversations.put(
							uuid,
							new StringBuilder(
											"The following is a conversation on a Minecraft server between a Minecraft player and an AI assistant. The assistant is helpful, creative, clever, and very friendly. The name of the AI assistant is SlimeGPT.\n"
															+
															"\n" +
															"Player: Hello, who are you?"
							)
			);

			player.sendMessage(ChatColor.DARK_GRAY.toString() + ChatColor.BOLD + "\n| " + ChatColor.GRAY
							+ "You have started a new conversation with " + ChatColor.DARK_GREEN + ChatColor.BOLD + "SlimeGPT"
							+ ChatColor.GRAY + ".\n ");
		}

		return true;
	}

	@Override
	public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull String[] args) {
		return null;
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onAsyncPlayerChatMessage(@NotNull final AsyncPlayerChatEvent event) {
		final Player player = event.getPlayer();
		final UUID uuid = player.getUniqueId();

		if (conversations.containsKey(uuid)) {
			event.setCancelled(true);

			final Rank playerRank = rankManager.getPlayerRank(uuid, false);
			final String displayName = playerRank.getDisplayName();

			final String rankDisplay = displayName.equals(ChatColor.RESET.toString() + ChatColor.RESET)
							? ChatColor.RESET.toString()
							: displayName + " ";

			final String message = event.getMessage();

			player.sendMessage(rankDisplay + "You" + ChatColor.AQUA + " » " + ChatColor.RED + ChatColor.BOLD + "AI "
							+ ChatColor.RESET + "SlimeGPT" + ChatColor.DARK_GRAY + ": " + ChatColor.RESET + message);

			final Player.Spigot spigotPlayer = player.spigot();

			spigotPlayer.sendMessage(ChatMessageType.ACTION_BAR,
							TextComponent
											.fromLegacyText(ChatColor.RED.toString() + ChatColor.BOLD + "AI " + ChatColor.RESET + "SlimeGPT "
															+ ChatColor.GRAY + "is thinking..."));

			SCHEDULER.runTaskAsynchronously(plugin, () -> {
				player.sendMessage(
								ChatColor.RED.toString() + ChatColor.BOLD + "AI " + ChatColor.RESET + "SlimeGPT " + ChatColor.AQUA + "» "
												+ rankDisplay + "You" + ChatColor.DARK_GRAY + ": " + ChatColor.RESET + getAIResponse(uuid, message));

				spigotPlayer.sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(""));
			});
		}
	}

	@EventHandler
	public void onPlayerQuit(@NotNull final PlayerQuitEvent event) {
		final UUID uuid = event.getPlayer().getUniqueId();

		conversations.remove(uuid);
	}

	private String getAIResponse(@NotNull final UUID uuid, @NotNull final String message) {
		final StringBuilder conversation = conversations.get(uuid);

		conversation.append("\nPlayer: ").append(message).append("\nAI: ");

		final CompletionRequest request = CompletionRequest.builder()
						.prompt(conversation.toString())
						.model("text-davinci-003")
						.temperature(0.9D)
						.maxTokens(150)
						.topP(1D)
						.frequencyPenalty(0D)
						.presencePenalty(0.6D)
						.stop(Arrays.asList("Player: ", "AI: "))
						.build();

		return service.createCompletion(request).getChoices().get(0).getText();
	}
}
