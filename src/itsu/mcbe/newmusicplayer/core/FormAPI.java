package itsu.mcbe.newmusicplayer.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;

import cn.nukkit.Player;
import cn.nukkit.network.protocol.ModalFormRequestPacket;

public class FormAPI {
	
	public static final int FORMID = 5246546;

    public static void makeForm() {
        Map<String, Object> data = new HashMap<>();
        data.put("type", "form");
        data.put("title", "NewMusicPlayer");
        data.put("content", "聞きたい音楽を選んでください。");

        List<Map<String, Object>> buttons = new ArrayList<>();

        for(String str : DataServer.getMusics()) {
            Map<String, Object> button = new HashMap<>();
            
            if(DataServer.getMusicKeys().containsKey(str)) {
            	button.put("text", DataServer.getMusicKeys().get(str));
            } else {
            	button.put("text", str);
            }

            Map<String, String> image = new HashMap<>();
            image.put("type", "url");
            image.put("data", "");

            button.put("image", image);
            buttons.add(button);
        }

        data.put("buttons", buttons);

        DataServer.setForm(new Gson().toJson(data));
    }

    public static void sendForm(Player player) {
        ModalFormRequestPacket packet = new ModalFormRequestPacket();
        packet.data = DataServer.getFormJson();
        packet.formId = FORMID;
        player.dataPacket(packet);
    }

}
