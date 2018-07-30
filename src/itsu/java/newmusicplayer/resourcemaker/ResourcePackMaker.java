package itsu.java.newmusicplayer.resourcemaker;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import com.google.gson.Gson;

public class ResourcePackMaker {
	
	private UUID headerUUID;
    private UUID moduleUUID;

    private Map<String, Object> manifest;
    private Map<String, Object> manifestHeader;
    private Map<String, Object> manifestModule;

    private Map<String, Object> definition;

    private File musicFolder;

    public static void main(String args[]) {
        ResourcePackMaker maker = new ResourcePackMaker();
        maker.process();
    }

    private void process() {
        System.out.println("フォルダ階層を作成しています...");
        init();
        makeManifest();
        makeSoundDefinition();

        try {
            makeRawFolders();
            createZip();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("完了しました。SoundResourcePack.mcpackを適用させてください。");

    }

    private void init() {
        headerUUID = UUID.randomUUID();
        moduleUUID = UUID.randomUUID();

        manifestModule = new HashMap<>();
        manifestHeader = new HashMap<>();
        manifest = new HashMap<>();
        definition = new HashMap<>();

        musicFolder = new File("sounds/music/");
    }

    private void makeManifest() {
        manifestModule.put("description", "SoundResourcePack made by NewMusicPlayerPlugin");
        manifestModule.put("type", "resources");
        manifestModule.put("uuid", headerUUID.toString());
        manifestModule.put("version", new int[]{1, 0, 0});

        manifestHeader.put("description", "SoundResourcePack made by NewMusicPlayerPlugin");
        manifestHeader.put("name", "SoundResourcePack");
        manifestHeader.put("uuid", moduleUUID.toString());
        manifestHeader.put("version", new int[]{1, 0, 0});

        manifest.put("format_version", 1);
        manifest.put("header", manifestHeader);
        manifest.put("modules", new Map[]{manifestModule});
    }

    private void makeSoundDefinition() {
    	File files[] = new File("./").listFiles();
    	for(File file : files) {
    		if(file.getName().endsWith(".ogg")) {
    			Map<String, Object> sounds = new HashMap<>();
    			sounds.put("volume", 1);
    			sounds.put("name", "sounds/music/" + file.getName().replaceAll(".ogg", ""));
    			
    			Map<String, Object> map = new HashMap<>();
    			map.put("sounds", sounds);
    			map.put("category", "music");
    			
    	        definition.put("music." + file.getName().replaceAll(".ogg", ""), map);
    		} else {
    			continue;
    		}
    	}
    }

    private void makeRawFolders() throws IOException {
        musicFolder.mkdirs();

        Utils.writeFile(new File("manifest.json"), new Gson().toJson(manifest));
        Utils.writeFile(new File("sounds/sound_definitions.json"), new Gson().toJson(definition));

        for(File file : new File("./").listFiles()) {
        	if(file.getName().endsWith(".ogg")) {
        	      Utils.copyFile(file, new File("sounds/music/" + file.getName()));
        	}
        }
    }

    /**
     * Copied from https://qiita.com/areph/items/8d1ab96c93aa2463ff4a
     * @author areph
     * @throws IOException 
     */
    private void createZip() throws IOException {
        System.out.println("圧縮中...");
        List<File> filesList = new ArrayList<>();
        filesList.addAll(Arrays.asList(new File("sounds/music/").listFiles()));
        filesList.add(new File("sounds/sound_definitions.json"));
        filesList.add(new File("manifest.json"));
        
        File[] files = (File[]) filesList.toArray(new File[0]);
        ZipOutputStream zos = null;
        try {
            zos = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(new File("SoundResourcePack.mcpack"))));
            createZip(zos, files);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            zos.close();
        }
    }

    /**
     * Copied from https://qiita.com/areph/items/8d1ab96c93aa2463ff4a
     * @author areph
     */
    private void createZip(ZipOutputStream zos, File[] files) throws IOException {
        byte[] buf = new byte[1024];
        InputStream is = null;
        try {
            for (File file : files) {
                ZipEntry entry = new ZipEntry(file.getPath());
                zos.putNextEntry(entry);
                is = new BufferedInputStream(new FileInputStream(file));
                int len = 0;
                while ((len = is.read(buf)) != -1) {
                    zos.write(buf, 0, len);
                }
            }
        } finally {
        	zos.close();
        }
    }

}
