package com.domsplace;

import com.domsplace.*;
import com.domsplace.Objects.*;
import com.domsplace.Commands.*;
import com.domsplace.Listeners.*;
import com.domsplace.DataManagers.*;
import com.domsplace.Utils.*;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class VillagesPlugin extends JavaPlugin {
    
    public static PluginManager pluginManager;
    public static boolean LoadedPlugin = false;
    
    //Commands
    public static VillagesVillageAdminCommand VillageAdminCommand;
    public static VillagesVillagesCommand VillagesCommand;
    public static VillagesVillageCommand VillageCommand;
    public static VillagesCreateVillageCommand CreateVillageCommand;
    public static VillagesVillageInviteCommand VillageInviteCommand;
    public static VillagesVillageTopCommand VillageTopCommand;
    
    //Listeners
    public static VillageConfigListener ConfigListener;
    public static VillageVillagesListener VillagesListener;
    
    @Override
    public void onEnable() {
        //Reference this plugin globally.
        VillageUtils.plugin = getVillagesPlugin();
        
        VillagePluginManager.LoadPluginYML();
        
        pluginManager = Bukkit.getPluginManager();
        
        if(!VillageConfigManager.LoadConfig()) {
            Disable();
            return;
        }
        
        if(!VillageLanguageManager.LoadLanguage()) {
            Disable();
            return;
        }
        
        //Load Commands
        VillageAdminCommand = new VillagesVillageAdminCommand(this);
        VillagesCommand = new VillagesVillagesCommand(this);
        VillageCommand = new VillagesVillageCommand(this);
        CreateVillageCommand = new VillagesCreateVillageCommand(this);
        VillageInviteCommand = new VillagesVillageInviteCommand(this);
        VillageTopCommand = new VillagesVillageTopCommand(this);
        
        //Load Listeners
        ConfigListener = new VillageConfigListener(this);
        VillagesListener = new VillageVillagesListener(this);
        
        //Register Commands
        getCommand("villageadmin").setExecutor(VillageAdminCommand);
        getCommand("villages").setExecutor(VillagesCommand);
        getCommand("village").setExecutor(VillageCommand);
        getCommand("createvillage").setExecutor(CreateVillageCommand);
        getCommand("villageinvite").setExecutor(VillageInviteCommand);
        getCommand("villageaccept").setExecutor(VillageInviteCommand);
        getCommand("villagedeny").setExecutor(VillageInviteCommand);
        getCommand("villagetop").setExecutor(VillageTopCommand);
        
        //Register Events
        pluginManager.registerEvents(ConfigListener, this);
        pluginManager.registerEvents(VillagesListener, this);
        
        //Load in Villages
        VillageVillagesUtils.LoadAllVillages();
        
        //Managed to load the plugin successfully!
        LoadedPlugin = true;
        VillageUtils.broadcast(
            "Villages.villageadmin", 
            "Loaded " + VillagePluginManager.getName() + 
            " version " + VillagePluginManager.getVersion() + 
            " successfully."
        );
    }
    
    @Override
    public void onDisable() {
        if(!LoadedPlugin) {
            VillageUtils.Error("Failed to load plugin.", "Check console for cause.");
            Disable();
            return;
        }
        
        //Stop Threads
        ConfigListener.AutoSaveConfig.cancel();
        VillagesListener.AutoSaveVillages.cancel();
        
        //Save Data
        VillageVillagesUtils.SaveAllVillages();
    }
    
    public void Disable() {
        pluginManager.disablePlugin(this);
    }
    
    public static VillagesPlugin getVillagesPlugin() {
        try {
            Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("Villages");

            if (plugin == null || !(plugin instanceof VillagesPlugin)) {
                return null;
            }

            return (VillagesPlugin) plugin;
        } catch(NoClassDefFoundError e) {
            return null;
        }
    }
}
