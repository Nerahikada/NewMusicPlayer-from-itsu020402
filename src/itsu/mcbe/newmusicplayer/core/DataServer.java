package itsu.mcbe.newmusicplayer.core;

import java.util.List;
import java.util.Map;

import cn.nukkit.plugin.Plugin;

public class DataServer {

    private static List<String> musics;
    private static Map<String, String> keys;
    private static String form;
    private static Plugin pluginInstance;

    public static void setMusics(List<String> list) {
        musics = list;
    }

    public static List<String> getMusics() {
        return musics;
    }
    
    public static void setMusicKeys(Map<String, String> map) {
    	keys = map;
    }
    
    public static Map<String, String> getMusicKeys() {
    	return keys;
    }

    public static void setForm(String json) {
        form = json;
    }

    public static String getFormJson() {
        return form;
    }
    
    public static void setPluginInstance(Plugin plugin) {
    	pluginInstance = plugin;
    }
    
    public static Plugin getPluginInstance() {
    	return pluginInstance;
    }

}
