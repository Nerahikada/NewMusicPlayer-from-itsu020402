package itsu.mcbe.newmusicplayer.core;

import cn.nukkit.Player;
import cn.nukkit.network.protocol.PlaySoundPacket;
import cn.nukkit.network.protocol.StopSoundPacket;

public class MusicPlayerAPI {

    public static void playMusic(Player player, String musicName) {
        stopMusic(player);

        PlaySoundPacket packet = new PlaySoundPacket();
        packet.name = musicName;
        packet.volume = 400f;
        packet.pitch = 1;
        packet.x = (int) player.x;
        packet.y = (int) player.y;
        packet.z = (int) player.z;
        player.dataPacket(packet);
    }

    public static void stopMusic(Player player) {
        StopSoundPacket packet = new StopSoundPacket();
        packet.name = "";
        packet.stopAll = true;
        player.dataPacket(packet);
    }

    public static void playBroadcastMusic(String musicName) {
    	stopBroadcastMusic();
    	
        for(Player player : DataServer.getPluginInstance().getServer().getOnlinePlayers().values()) {
	        PlaySoundPacket packet = new PlaySoundPacket();
	        packet.name = musicName;
	        packet.volume = 400f;
	        packet.x = (int) player.x;
	        packet.y = (int) player.y;
	        packet.z = (int) player.z;
	        player.dataPacket(packet);
        }
    }
    
    public static void stopBroadcastMusic() {
    	for(Player player : DataServer.getPluginInstance().getServer().getOnlinePlayers().values()) {
            StopSoundPacket packet = new StopSoundPacket();
            packet.name = "";
            packet.stopAll = true;
            player.dataPacket(packet);
        }
    }

}
