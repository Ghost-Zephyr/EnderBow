package dev.bitsnthings.mc.ebow;

import org.bukkit.command.*;

import org.bukkit.entity.Player;
import org.bukkit.ChatColor;
import org.bukkit.Bukkit;

public class EbowCommand implements CommandExecutor {
  @Override
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    Player receiver;
    switch (args.length) {
      case 0:
        if (!(sender instanceof Player)) {
          sender.sendMessage(ChatColor.RED + "Can't give an ender bow to the console!");
          return false;
        }
        if (!sender.hasPermission(EbowUtil.ENDERBOW_GIVE_SELF_PERM)) return false;
        receiver = (Player) sender;
      break;
      case 1:
        if (!sender.hasPermission(EbowUtil.ENDERBOW_GIVE_OTHERS_PERM)) return false;
        receiver = (Player) Bukkit.getPlayer(args[0]);
      break;
      default: return false;
    }
    receiver.getInventory().addItem(EbowUtil.createEbow());
    sender.sendMessage(String.format("Gave %s an ender bow.", receiver.getDisplayName()));
    if (sender.getName() != "CONSOLE") {
      String recv = receiver.getDisplayName();
      if (sender.getName() == receiver.getDisplayName()) recv = "himself";
      EnderBow.getInstance().getLogger().info(String.format("%s gave %s an ender bow.", sender.getName(), recv));
    }
    return true;
  }
}
