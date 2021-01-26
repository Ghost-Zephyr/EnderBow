package dev.bitsnthings.mc.ebow;

import java.util.ArrayList;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.permissions.Permission;
import org.bukkit.NamespacedKey;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Bukkit;

public final class EbowUtil {
  public static final NamespacedKey EBOW_KEY = new NamespacedKey(EnderBow.getInstance(), "enderbow");
  public static final NamespacedKey EBOW_UPGRADE_KEY = new NamespacedKey(EnderBow.getInstance(), "enderbowupgrade");
  public static final String ENDERBOW_USE_PERM = "enderbow.use";
  public static final String ENDERBOW_GIVE_SELF_PERM = "enderbow.give.self";
  public static final String ENDERBOW_GIVE_OTHERS_PERM = "enderbow.give.others";
  private static final ArrayList<Permission> perms = new ArrayList<>();

  private EbowUtil() {}

  public static ItemStack createEbow() {
    ItemStack ebow = new ItemStack(Material.BOW);
    ItemMeta meta = ebow.getItemMeta();
    meta.setDisplayName(ChatColor.DARK_PURPLE + "Ender Bow");
    meta.addEnchant(Enchantment.ARROW_INFINITE, 1, false);
    ebow.setItemMeta(meta);
    return ebow;
  }

  public static boolean isEbow(ItemStack stack) {
    if (stack == null || stack.getType() != Material.BOW || !stack.hasItemMeta() || !stack.getItemMeta().hasDisplayName()) return false;
    else if (stack.getItemMeta().getDisplayName().equals(ChatColor.DARK_PURPLE+"Ender Bow")) return true;
    else return false;
  }

  public static boolean registerEbowRecipes() {
    ItemStack ebow = EbowUtil.createEbow();
    ShapedRecipe ebowRecipe = new ShapedRecipe(EBOW_KEY, ebow);
    ebowRecipe.shape(
      "GKE",
      "GEK",
      "GKE"
    );
    ebowRecipe.setIngredient('G', Material.STRING);
    ebowRecipe.setIngredient('K', Material.STICK);
    ebowRecipe.setIngredient('E', Material.ENDER_PEARL);

    ShapedRecipe bowUpgradeRecipe = new ShapedRecipe(EBOW_UPGRADE_KEY, ebow);
    bowUpgradeRecipe.shape(
      "-E-",
      "EBE",
      "-E-"
    );
    bowUpgradeRecipe.setIngredient('B', Material.BOW);
    bowUpgradeRecipe.setIngredient('E', Material.ENDER_PEARL);

    boolean recipeSucc = Bukkit.addRecipe(ebowRecipe);
    boolean upgradeSucc = Bukkit.addRecipe(bowUpgradeRecipe);

    if (recipeSucc && upgradeSucc) {
      EnderBow.getInstance().getLogger().info("Registered recipes.");
      return true;
    } else {
      EnderBow.getInstance().getLogger().warning("Failed to register recipes!");
      return false;
    }
  }
  public static boolean unregisterEbowRecipes() {
    boolean recipeSucc = Bukkit.removeRecipe(EBOW_KEY);
    boolean upgradeSucc = Bukkit.removeRecipe(EBOW_UPGRADE_KEY);
    if (recipeSucc && upgradeSucc) {
      EnderBow.getInstance().getLogger().info("Unregistered recipes.");
      return true;
    } else {
      EnderBow.getInstance().getLogger().warning("Failed to unregister recipes!");
      return false;
    }
  }

  public static void registerPermissions() {
    perms.add(new Permission(ENDERBOW_USE_PERM, "Allows player to use enderbow", PermissionDefault.TRUE));
    perms.add(new Permission(ENDERBOW_GIVE_SELF_PERM, "Allows player give themselves an enderbow", PermissionDefault.OP));
    perms.add(new Permission(ENDERBOW_GIVE_OTHERS_PERM, "Allows player give others an enderbow", PermissionDefault.OP));
		for(Permission perm: perms){
			Bukkit.getPluginManager().addPermission(perm);
			EnderBow.getInstance().getLogger().fine("Registered Permission: " + perm.getName());
		}
  }
  public static void unregisterPermissions() {
    for(Permission perm: perms) {
      Bukkit.getPluginManager().removePermission(perm);
      EnderBow.getInstance().getLogger().fine("Unregistered Permission: " + perm.getName());
    }
    perms.clear();
  }
}
