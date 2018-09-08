package uShop;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Cmd extends BukkitCommand {
	
	private Main plugin;
	
	protected Cmd(Main plugin, String name) {
		super(name);
		this.plugin = plugin;
		this.setPermission("ushop.use");
		this.setDescription("Main command of uShop");
		this.setUsage(name);
	}

	@Override
	public boolean execute(CommandSender cs, String arg1, String[] arg2) {
		if (!(cs instanceof Player))
			return true;
		Player p = (Player) cs;
		if (!p.hasPermission("ushop.use")) {
			cs.sendMessage("You dont have permission!");
			return true;
		}
		Inventory inv = Bukkit.createInventory(null, 9 * plugin.getConfig().getInt("gui-rows"),
				plugin.getConfig().getString("gui-name").replace("&", "§"));
		ItemStack is = new ItemStack(Material.getMaterial(plugin.getConfig().getString("gui-sellitem.material")));
		ItemMeta im = is.getItemMeta();
		im.setDisplayName(plugin.getConfig().getString("gui-sellitem.displayname").replace('&', '§').replace("%total%",
				plugin.getEconomy().format(0)));
		is.setItemMeta(im);
		inv.setItem(inv.getSize() - 5, is);

		p.openInventory(inv);
		if (plugin.getOpenShops().containsKey(p)) {
			return true;
		}
		plugin.getOpenShops().put(p, inv);
		
		return true;
	}

}
