package net.slqmy.first_plugin;

import com.zaxxer.hikari.HikariDataSource;
import net.slqmy.first_plugin.utility.Utility;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.SQLException;

public final class Database {
	private final String HOST;
	private final int PORT;
	private final String DATABASE;
	private final String USERNAME;
	private final String PASSWORD;
	private HikariDataSource hikari;

	public Database(@NotNull final FirstPlugin plugin) {
		final YamlConfiguration config = (YamlConfiguration) plugin.getConfig();

		this.HOST = config.getString("Host");
		this.PORT = config.getInt("Port");
		this.DATABASE = config.getString("Database");
		this.USERNAME = config.getString("Username");
		this.PASSWORD = config.getString("Password");
	}

	public void connect() throws SQLException {
		hikari = new HikariDataSource();
		hikari.setDataSourceClassName("com.mysql.cj.jdbc.MysqlDataSource");

		hikari.addDataSourceProperty("serverName", HOST);
		hikari.addDataSourceProperty("port", PORT);
		hikari.addDataSourceProperty("databaseName", DATABASE);
		hikari.addDataSourceProperty("user", USERNAME);
		hikari.addDataSourceProperty("password", PASSWORD);
	}

	public void disconnect() {
		if (isConnected()) {
			hikari.close();
		} else {
			Utility.log("Database is not connected!");
		}
	}

	public boolean isConnected() {
		return hikari != null;
	}

	@Nullable
	public HikariDataSource getConnection() {
		return hikari;
	}
}
