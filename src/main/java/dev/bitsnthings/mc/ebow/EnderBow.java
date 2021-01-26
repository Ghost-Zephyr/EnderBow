package dev.bitsnthings.mc.ebow;

import java.util.logging.Logger;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.Bukkit;

public class EnderBow extends JavaPlugin {
  private final Logger logger = Logger.getLogger("EnderBow");
  private static EnderBow plugin;
  @Override
  public void onEnable() {
    plugin = this;
    Bukkit.getPluginManager().registerEvents(new EbowEvents(), this);
    this.getCommand("ebow").setExecutor(new EbowCommand());
		EbowUtil.registerEbowRecipes();
		EbowUtil.registerPermissions();
  }
  @Override
  public void onDisable() {
		EbowUtil.unregisterEbowRecipes();
		EbowUtil.unregisterPermissions();
  }
	public Logger getLogger(){
		return logger;
	}
	public static EnderBow getInstance(){
		return plugin;
	}
}
