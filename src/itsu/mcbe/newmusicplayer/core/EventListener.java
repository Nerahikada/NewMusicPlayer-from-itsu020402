package itsu.mcbe.newmusicplayer.core;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.server.DataPacketReceiveEvent;
import cn.nukkit.network.protocol.ModalFormResponsePacket;
import cn.nukkit.utils.TextFormat;

public class EventListener implements Listener {

    @EventHandler
    public void onPacket(DataPacketReceiveEvent event) {
        if(event.getPacket() instanceof ModalFormResponsePacket) {
            ModalFormResponsePacket packet = (ModalFormResponsePacket) event.getPacket();
            if(packet.formId == FormAPI.FORMID) {
                String musicName = DataServer.getMusics().get(Integer.parseInt(packet.data.replaceAll("[^0-9]", "")));
                MusicPlayerAPI.playMusic(event.getPlayer(), musicName);
                
                if(DataServer.getMusicKeys().containsKey(musicName)) {
                	event.getPlayer().sendMessage(TextFormat.GREEN + DataServer.getMusicKeys().get(musicName) + TextFormat.WHITE + "を再生しました。");
                } else {
                	event.getPlayer().sendMessage(TextFormat.GREEN + musicName + TextFormat.WHITE + "を再生しました。");
                }
            }
        }
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String args[]) {
    	if(command.getName().equals("playmusic")) {
    		if(sender instanceof Player) {
    			FormAPI.sendForm((Player) sender);
    		} else {
    			sender.sendMessage(TextFormat.RED + "ゲーム内から実行してください！");
    		}
    	}
        return true;
    }

}
