package net.slqmy.first_plugin;

import net.slqmy.first_plugin.utility.Utility;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class Database {
	private final String HOST;
	private final int PORT;
	private final String DATABASE;
	private final String USERNAME;
	private final String PASSWORD;
	private Connection connection;

	public Database(@NotNull final FirstPlugin plugin) {
		final YamlConfiguration config = (YamlConfiguration) plugin.getConfig();

		this.HOST = config.getString("Host");
		this.PORT = config.getInt("Port");
		this.DATABASE = config.getString("Database");
		this.USERNAME = config.getString("Username");
		this.PASSWORD = config.getString("Password");
	}

	public void connect() throws SQLException {
		connection = DriverManager.getConnection(
						"jdbc:mysql://"
						+ HOST
						+ ":"
						+ PORT
						+ "/"
						+ DATABASE
						+ "?useSSL=false",
						USERNAME,
						PASSWORD
		);
	}

	public void disconnect() {
		if (isConnected()) {
			try {
				connection.close();
			} catch (final SQLException exception) {
				Utility.log("There was en error while closing the database connection!");
				throw new RuntimeException(exception);
			}
		}
	}

	public boolean isConnected() {
		return connection != null;
	}

	public Connection getConnection() {
		return connection;
	}
}
