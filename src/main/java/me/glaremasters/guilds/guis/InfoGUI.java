package me.glaremasters.guilds.guis;

import ch.jalu.configme.SettingsManager;
import co.aikar.commands.ACFBukkitUtil;
import co.aikar.commands.CommandManager;
import com.github.stefvanschie.inventoryframework.Gui;
import com.github.stefvanschie.inventoryframework.GuiItem;
import com.github.stefvanschie.inventoryframework.pane.OutlinePane;
import com.github.stefvanschie.inventoryframework.pane.Pane;
import lombok.AllArgsConstructor;
import me.glaremasters.guilds.Guilds;
import me.glaremasters.guilds.configuration.sections.GuildInfoSettings;
import me.glaremasters.guilds.guild.Guild;
import me.glaremasters.guilds.guild.GuildHandler;
import me.glaremasters.guilds.messages.Messages;
import me.glaremasters.guilds.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Glare
 * Date: 5/11/2019
 * Time: 11:17 PM
 */
@AllArgsConstructor
public class InfoGUI {

    private Guilds guilds;
    private SettingsManager settingsManager;
    private GuildHandler guildHandler;

    public Gui getInfoGUI(Guild guild, Player player, CommandManager commandManager) {

        // Create the GUI with the desired name from the config
        Gui gui = new Gui(guilds, 1, ACFBukkitUtil.color(settingsManager.getProperty(GuildInfoSettings.GUI_NAME).replace("{name}",
                guild.getName()).replace("{prefix}", guild.getPrefix())));

        // Create the background pane which will just be stained glass
        OutlinePane backgroundPane = new OutlinePane(0, 0, 9, 3, Pane.Priority.LOW);

        // Create the pane for the main items
        OutlinePane foregroundPane = new OutlinePane(2, 1, 5, 1, Pane.Priority.NORMAL);

        // Add the items to the background pane
        createBackgroundItems(backgroundPane);

        // Add the items to the forground pane
        createForegroundItems(foregroundPane, guild, player, commandManager);

        // Add the glass panes to the main GUI background pane
        gui.addPane(backgroundPane);

        // Add the foreground pane to the GUI
        gui.addPane(foregroundPane);

        // Return the new info GUI object
        return gui;
    }

    /**
     * Create the background panes
     * @param pane the pane to add to
     */
    private void createBackgroundItems(OutlinePane pane) {
        // Start the itembuilder with stained glass
        ItemBuilder builder = new ItemBuilder(new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 7));
        // Loop through 9 (one row)
        for (int i = 0; i < 9; i++) {
            // Add the pane item to the GUI and cancel the click event on it
            pane.addItem(new GuiItem(builder.build(), event -> event.setCancelled(true)));
        }
    }

    /**
     * Create the regular items that will be on the GUI
     * @param pane the pane to be added to
     * @param guild the guild of the player
     */
    private void createForegroundItems(OutlinePane pane, Guild guild, Player player, CommandManager commandManager) {
        // Add the members button to the GUI
        pane.addItem(new GuiItem(easyItem(settingsManager.getProperty(GuildInfoSettings.MEMBERS_MATERIAL),
                settingsManager.getProperty(GuildInfoSettings.MEMBERS_NAME),
                settingsManager.getProperty(GuildInfoSettings.MEMBERS_LORE).stream().map(l ->
                        l.replace("{members}", String.valueOf(guild.getSize()))
                                .replace("{max}", String.valueOf(guildHandler.getGuildTier(guild.getTier().getLevel()).getMaxMembers()))).collect(Collectors.toList()))));
        // Add the home button to the GUI
        pane.addItem(new GuiItem(easyItem(settingsManager.getProperty(GuildInfoSettings.HOME_MATERIAL),
                settingsManager.getProperty(GuildInfoSettings.HOME_NAME),
                settingsManager.getProperty(GuildInfoSettings.HOME_LORE)), event -> {
            // Cancel the event
            event.setCancelled(true);
            // Close the GUI
            event.getWhoClicked().closeInventory();
            // Check if guild home is null
            if (guild.getHome() != null) {
                // Teleport player to guild home
                player.teleport(guild.getHome().getAsLocation());
                // Tell them they teleported successfully
                commandManager.getCommandIssuer(player).sendInfo(Messages.HOME__TELEPORTED);
            } else {
                // Tell them that they have no home set
                commandManager.getCommandIssuer(player).sendInfo(Messages.HOME__NO_HOME_SET);
            }
        }));
        // Add the status button to the GUI
        pane.addItem(new GuiItem(easyItem(settingsManager.getProperty(GuildInfoSettings.STATUS_MATERIAL),
                settingsManager.getProperty(GuildInfoSettings.STATUS_NAME),
                settingsManager.getProperty(GuildInfoSettings.STATUS_LORE).stream().map(l -> l.replace("{status}",
                        guild.getStatus().name())).collect(Collectors.toList())), event -> event.setCancelled(true)));
    }

    /**
     * Easily create an item for the GUI
     * @param material the material of the item
     * @param name the name of the item
     * @param lore the lore of the item
     * @return created itemstack
     */
    private ItemStack easyItem(String material, String name, List<String> lore) {
        // Start the itembuilder
        ItemBuilder builder = new ItemBuilder(Material.valueOf(material));
        // Sets the name of the item
        builder.setName(ACFBukkitUtil.color(name));
        // Sets the lore of the item
        builder.setLore(lore.stream().map(ACFBukkitUtil::color).collect(Collectors.toList()));
        // Return the created item
        return builder.build();
    }

}
