package net.slqmy.first_plugin.types;

import net.slqmy.first_plugin.Main;
import net.slqmy.first_plugin.utility.Utility;
import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public final class PlayerWrapper {
	private final Connection connection;
	private final UUID uuid;
	private String rank;
	private int coins;

	public PlayerWrapper(@NotNull final Main plugin, @NotNull final UUID uuid) throws SQLException {
		this.connection = plugin.getDatabase().getConnection();

		this.uuid = uuid;


		final PreparedStatement statement = connection.prepareStatement(
						"SELECT RANK, COINS FROM player_information WHERE UUID = ?;"
		);

		statement.setString(1, uuid.toString());

		final ResultSet set = statement.executeQuery();

		if (set.next()) {
			rank = set.getString("RANK");
			coins = set.getInt("COINS");
		} else {
			rank = "GUEST";
			coins = 0;

			final PreparedStatement insertStatement = connection.prepareStatement(
							"INSERT INTO player_information (ID, UUID, RANK, COINS) VALUES (" +
											"default"
											+ ", '" + uuid + "', " +
											"'" + rank + "', " +
											coins +
											");"
			);

			insertStatement.executeUpdate();
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

		try {
			final PreparedStatement statement = connection.prepareStatement("UPDATE player_information SET RANK = '" + rank + "' WHERE UUID = '" + uuid + "';");

			statement.executeUpdate();
		} catch (final SQLException exception) {
			Utility.log("There was an error setting the rank of player with UUID " + uuid + " to rank " + rank + "!");

			throw new RuntimeException(exception);
		}
	}

	public int getCoins() {
		return coins;
	}

	public void setCoins(final int coins) {
		this.coins = coins;

		try {
			final PreparedStatement statement = connection.prepareStatement("UPDATE player_information SET COINS = " + coins + " WHERE UUID = '" + uuid + "';");

			statement.executeUpdate();
		} catch (final SQLException exception) {
			Utility.log("There was an error setting the coins of player with UUID " + uuid + " to " + coins + "!");

			throw new RuntimeException(exception);
		}
	}
}
