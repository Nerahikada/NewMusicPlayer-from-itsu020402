# NewMusicPlayer(Japanese)
MusicPlayerプラグインの後継版 / サウンドリソースパックメーカー  
  
<img src="https://pbs.twimg.com/media/DWZiAqpUQAAwW3f.jpg">  
  
## ダウンロード
<a href="https://github.com/itsu020402/NewMusicPlayer/raw/master/NewMusicPlayer.jar">https://github.com/itsu020402/NewMusicPlayer/raw/master/NewMusicPlayer.jar</a>  
  
## 使い方
### プラグインとして使う
1. サウンドリソパをresource_packsディレクトリに入れる
2. jarをpluginsディレクトリに入れて鯖を起動  
3. 一度鯖を落とす  
4. plugins/NewMusicPlayer/Config.ymlのResourceNameの欄にリソパの名前を記入する  
5. plugins/NewMusicPlayer/MusicKeys.propertiesの「=」の次に表示させたい曲名を記入する（しなくてもいい）  
6. 鯖を再起動  
7. /playmusicコマンドでフォームが開きます。  
  
### ソフトウェア（サウンドリソースパックメーカー）として使う
このプラグインはJavaソフトウェアとしても機能します。  
サウンドリソースパックを作るソフトです。  
1. 適当なフォルダを用意する  
2. そのフォルダにDLしたjarとリソースパックに入れたい音楽ファイル（.ogg）を好きなだけ入れる  
3. jarをクリック  
4. 同フォルダ内にSoundMusicPack.mcpackが生成されます。  
いつになっても作成されない場合はコマンドプロンプトから実行してみてください。  
  
# NewMusicPlayer(English)
## Download
<a href="https://github.com/itsu020402/NewMusicPlayer/raw/master/NewMusicPlayer.jar">https://github.com/itsu020402/NewMusicPlayer/raw/master/NewMusicPlayer.jar</a>  
  
## How to use
### Use as Nukkit plugin
1. Add sound resource pack into resource_pack directory.  
2. Add this plugin and run your server.  
3, Shutdown your server.(For create plugins/NewMusicPlayer/MusicKeys.properties).  
4. Write sound resource pack name in plugins/NewMusicPlayer/Config.yml.  
5. Write each sound name in plugins/NewMusicPlayer/MusicKeys.properties next to "=" character.  
6. Restart your server.  
7. Send /playmusic command and show musiclist form.  
  
### Use as sound resource pack maker(Java software)
1. Create new folder.  
2. Add NewMusicPlayer.jar and sound files(.ogg) you want to play in the folder.  
3. Run as Java software(Click NewNusicPlayer.jar).  
4. SoundMusicPack.mcpack will be created in the folder.  
(If SoundMusicPack.mcpack won't be created, please try to run the jar from commandline.)  
  
