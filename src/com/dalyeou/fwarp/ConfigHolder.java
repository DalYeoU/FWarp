package com.dalyeou.fwarp;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

//Use it in main class onEnable() new ConfigHolder(this);

public class ConfigHolder {

	private JavaPlugin plugin;
	private static ConfigHolder instance;

	@SuppressWarnings("static-access")
	public ConfigHolder(JavaPlugin plugin) {
		this.plugin = plugin;
		this.instance = this;
		createConfigs();
	}

	private void createConfigs() {
		for (Configs config : Configs.values()) {
			config.init(this);
		}
	}

	public FileConfiguration createConfig(String name) {
		File conf = new File(plugin.getDataFolder(), name);

		if (!conf.exists()) {
			conf.getParentFile().mkdirs();
			plugin.saveResource(name, false);
		}

		FileConfiguration configRet = new YamlConfiguration();

		try {
			configRet.load(conf);
			return configRet;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InvalidConfigurationException e) {
			e.printStackTrace();
		}

		return null;
	}

	public void saveConfig(FileConfiguration config, String name) {
		try {
			config.save(new File(plugin.getDataFolder(), name));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void reloadConfig(FileConfiguration config, String name) {
		try {
			config.load(new File(plugin.getDataFolder(), name));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public enum Configs {
		CONFIG("config.yml"),
		PORTAL("portal.yml"),
		WARP("warp.yml"),
		PER("permission.yml");

		private String name;
		private FileConfiguration config;

		Configs(String name) {
			this.name = name;
		}

		public void init(ConfigHolder holder) {
			this.config = holder.createConfig(name);
		}

		public FileConfiguration getConfig() {
			return config;
		}

		public void saveConfig() {
			instance.saveConfig(config, name);
		}
		
		public void reloadConfig() {
			instance.reloadConfig(config, name);
		}
	}

}