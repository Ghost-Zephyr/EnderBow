package dev.bitsnthings.mc.ebow;

import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.EventPriority;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.Player;
import org.bukkit.entity.Entity;
import org.bukkit.GameMode;
import org.bukkit.Material;

public class EbowEvents implements Listener {
	@EventHandler(priority=EventPriority.HIGH)
	public void onEntityShootBow(EntityShootBowEvent event){
    if (event.isCancelled()) return;
    if (event.getEntity() instanceof Player) {
      Player player = (Player) event.getEntity();
      if (EbowUtil.isEbow(event.getBow())) {
        event.setConsumeItem(false);
        if (player.getGameMode() != GameMode.CREATIVE) {
          if (!player.getInventory().contains(Material.ENDER_PEARL)) {
            event.setCancelled(true);
            return;
          } else {
            ItemStack peepearl = new ItemStack(Material.ENDER_PEARL, 1);
            player.getInventory().removeItem(peepearl);
          }
        }
        Entity arrow = event.getProjectile();
        Entity pearl = player.launchProjectile(EnderPearl.class, arrow.getVelocity());
        event.setProjectile(pearl);
      }
    }
  }
}

