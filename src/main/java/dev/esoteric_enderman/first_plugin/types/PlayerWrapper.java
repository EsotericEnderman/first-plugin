package dev.esoteric_enderman.first_plugin.types;

import com.zaxxer.hikari.HikariDataSource;
import dev.esoteric_enderman.first_plugin.FirstPlugin;
import dev.esoteric_enderman.first_plugin.utility.DebugUtility;
import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public final class PlayerWrapper {
	private final HikariDataSource hikari;
	private final UUID uuid;
	private String rank;
	private int coins;

	public PlayerWrapper(@NotNull final FirstPlugin plugin, @NotNull final UUID uuid) throws SQLException {
		this.hikari = plugin.getDatabase().getConnection();
		assert hikari != null;

		this.uuid = uuid;

		// Idea: DatabaseUtility class.
		try (final Connection connection = hikari.getConnection();
		     final PreparedStatement statement = connection.prepareStatement("SELECT RANK, COINS FROM player_information WHERE UUID = ?;")) {
			statement.setString(1, uuid.toString());

			final ResultSet set = statement.executeQuery();

			if (set.next()) {
				rank = set.getString("RANK");
				coins = set.getInt("COINS");
			} else {
				rank = "GUEST";
				coins = 0;

				try (final Connection insertConnection = hikari.getConnection();
				     final PreparedStatement insertStatement = insertConnection.prepareStatement(
								     "INSERT INTO player_information (ID, UUID, RANK, COINS) VALUES (" +
												     "default"
												     + ", '" + uuid + "', " +
												     "'" + rank + "', " +
												     coins +
												     ");"
				     )) {
					insertStatement.executeUpdate();
				}
			}
		} catch (final SQLException exception) {
			DebugUtility.logError(exception, "There was en error with accessing data of player with UUID " + uuid + "!");
		}
	}

	public UUID getUUID() {
		return uuid;
	}

	public String getRank() {
		return rank;
	}

	public void setRank(@NotNull final String rank) {
		this.rank = rank;

		try (final Connection connection = hikari.getConnection(); final PreparedStatement statement = connection.prepareStatement("UPDATE player_information SET RANK = '" + rank + "' WHERE UUID = '" + uuid + "';")) {
			statement.executeUpdate();
		} catch (final SQLException exception) {
			DebugUtility.logError(exception, "There was an error setting the rank of player with UUID ");
		}
	}

	public int getCoins() {
		return coins;
	}

	public void setCoins(final int coins) {
		this.coins = coins;

		try (final Connection connection = hikari.getConnection(); final PreparedStatement statement = connection.prepareStatement("UPDATE player_information SET COINS = " + coins + " WHERE UUID = '" + uuid + "';")) {
			statement.executeUpdate();
		} catch (final SQLException exception) {
			DebugUtility.logError(exception, "There was an error setting the coins of player with UUID " + uuid + " to " + coins + "!");
		}
	}
}
