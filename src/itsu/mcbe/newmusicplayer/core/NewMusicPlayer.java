package itsu.mcbe.newmusicplayer.core;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.Config;
import cn.nukkit.utils.TextFormat;
import cn.nukkit.utils.Utils;

public class NewMusicPlayer extends PluginBase {

    private EventListener listener;

    @Override
    public void onEnable() {
        getLogger().info(TextFormat.GREEN + "NewMusicPlayer for OGG Vorbis" + this.getDescription().getVersion());
        getLogger().info(TextFormat.GREEN + "Developed by Itsu(itsu020402)");
        getLogger().info(TextFormat.RED + "このプラグインにはGPL v3.0ライセンスが適用されます。（Nukkit/Jupiter準拠）");
        getLogger().info(TextFormat.YELLOW + "このプラグインの使用にはサウンドのリソースパックが必要です。");
        getLogger().info("");
        getLogger().info(TextFormat.GREEN + "起動しました");
        getLogger().info(TextFormat.AQUA + "音楽を読み込み中...");

        loadMusicsFromResourcepack();
        loadMusicProperties();
        FormAPI.makeForm();
        DataServer.setPluginInstance(this);

        listener = new EventListener();
        getServer().getPluginManager().registerEvents(new EventListener(), this);

        getLogger().info(TextFormat.GREEN + "音楽の読み込みが完了しました。");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String args[]) {
        return listener.onCommand(sender, command, label, args);
    }
    
    private void loadMusicProperties() {
		try {
			Map<String, String> info = new HashMap<>();
			String str = Utils.readFile(new File("plugins/NewMusicPlayer/MusicKeys.properties"));
			
			for(String data : str.split("\n")) {
				if(data.startsWith("#")) continue;
				
				try {
					info.put(data.split("=")[0], data.split("=")[1]);
				} catch(Exception e) {
					continue;
				}
			}
			
			DataServer.setMusicKeys(info);
			
		} catch(IOException e) {
			e.printStackTrace();
		}
    }

    @SuppressWarnings({ "unchecked", "deprecation" })
    private void loadMusicsFromResourcepack() {
        try {
            List<String> musics = new ArrayList<>();

            /*plugins/MusicPlayer/ がなければ作る*/
            File root = new File("plugins/NewMusicPlayer/");
            if(!root.exists()) {
                root.mkdirs();
            }

            LinkedHashMap<String, Object> map = new LinkedHashMap<>();
            map.put("ResourceName", "NAME.mcpack");

            /*コンフィグをロード*/
            Config conf = new Config(new File("plugins/NewMusicPlayer/Config.yml"), Config.YAML, map);

            /*コンフィグがなければ作成*/
            if(!conf.load("plugins/NewMusicPlayer/Config.yml")) {
                conf.setAll(map);
                conf.save();
                getLogger().alert("コンフィグを作成しました。適宜設定して再起動してください。");
                getServer().getPluginManager().disablePlugin(this);/*安全のためプラグインを無効化*/
                return;
            }

            /*コンフィグで指定されたリソースパックを取得*/
            File resource = new File("resource_packs/" + conf.getString("ResourceName"));
            if(!resource.exists()) {
                getLogger().alert("コンフィグで指定されたリソースパックが見つかりません。");
                getLogger().alert("適宜設定して再起動してください。");
                getServer().getPluginManager().disablePlugin(this);/*プラグインを無効化*/
                return;
            }

            /*リソースパック（*.zip/*.mcpack）解析*/
            ZipFile zip = new ZipFile(resource);
            List<ZipEntry> entries = new ArrayList<>();
            List<String> pathes = new ArrayList<>();

            /*リソパ内のファイル/フォルダをすべて取得*/
            entries = (List<ZipEntry>) Collections.list(zip.entries());

            /*取得したファイルの名前だけ取得して登録*/
            entries.forEach(entry -> pathes.add(entry.getName()));

            /*sounds/music/ フォルダの中身だけ取得して音楽ファイルだけを抽出する*/
            pathes.stream()
                    .filter(str -> ((str.startsWith("sounds/music/") || str.startsWith("sounds\\music\\")) && str.endsWith(".ogg")))/*条件(パスが"sounds/music/"から始まっていて、かつ".ogg"で終わっているものだけを取得する*/
                    .forEach(str -> {
                        musics.add("music." + str.replaceAll("sounds/music/", "").replaceAll("sounds\\\\music\\\\", "").replaceAll(".ogg", ""));
                    });

            zip.close();
            
            String information = "# 保存はBOM無しのUTF-8で！\r\n";
            for(String str : musics) {
            	information += str + "=\r\n";
            }
            
            if(!new File("plugins/NewMusicPlayer/MusicKeys.properties").exists()) {
            	Utils.writeFile(new File("plugins/NewMusicPlayer/MusicKeys.properties"), information);
            	getLogger().notice("plugins/NewMusicPlayer/MusicKeys.propertiesを作成しました。表示する曲の日本語化はそこで設定してください。");
            }

            DataServer.setMusics(musics);

        } catch(IOException e) {
            getLogger().alert("エラーが発生しました。");
            return;
        }
    }
}
