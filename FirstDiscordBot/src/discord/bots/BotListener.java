package discord.bots;

import discord.bots.functions.Functions;
import discord.bots.utils.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import net.dv8tion.jda.audio.player.FilePlayer;
import net.dv8tion.jda.audio.player.Player;
import net.dv8tion.jda.audio.player.URLPlayer;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.VoiceChannel;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import net.dv8tion.jda.core.managers.AudioManager;

import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static discord.bots.Main.loadDDCommands;

/**
 * Created by Conor on 3/27/2017.
 */
public class BotListener extends ListenerAdapter {

    private static final String diceRoll = "C:\\Users\\Conor\\IdeaProjects\\FirstDiscordBot\\src\\discord\\bots\\diceroll.mp3";
    private static StringBuilder sb;
    private static SimpleDateFormat sdf = Main.sdf;
    private static String ddStatMessage = "";
    private static String ddhelpMessage = "";
    private static String helpMessage = "";
    private static String ddWeapons = "";
    private static String ddArmors = "";
    private static String message;

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        sb = new StringBuilder();
        if(event.getMessage().getRawContent().equalsIgnoreCase("**help")) {
            sb = new StringBuilder();
                log(event, "**help");
                if (helpMessage.length() == 0) {
                    for (String i : Main.comString) {
                        sb.append(i);
                        sb.append("\n");
                    }
                    helpMessage = format(sb.toString(), false);
                    event.getChannel().sendMessage(helpMessage).queue();
                } else
                    event.getChannel().sendMessage(helpMessage).queue();
        }
        else if(event.getMessage().getRawContent().equalsIgnoreCase("**time")) {
            log(event, "**time");

            message = format("Current time: "+sdf.format(Main.date), false);
            event.getChannel().sendMessage(message).queue();
        }
        else if(event.getMessage().getRawContent().equalsIgnoreCase("**joke")) {
            getJoke(event);
        }
        else if(event.getMessage().getRawContent().equalsIgnoreCase("**tellmethatjoke")) {
            getSpeechJoke(event);
        }
        else if(event.getMessage().getRawContent().toLowerCase().contains("**rolldice:")) {
            MessageBuilder mb = new MessageBuilder();
            String channel = event.getChannel().getId();
            //connectTo(Main.newBot.getVoiceChannelById(channel));
            String input = event.getMessage().getRawContent().toLowerCase();
            String output = rollDice(event, input);
            log(event, input);
            //Player player = getMyFilePlayer();
            //player.play();
            event.getChannel().sendMessage(format(output,false)).queue();
        }
        else if(event.getMessage().getRawContent().equalsIgnoreCase("**diceVerify")) {
            sb = new StringBuilder();
            log(event, "**diceVerify");
            for(String i : Main.codeString) {
                sb.append(i);
            }
            message = sb.toString();
            event.getChannel().sendMessage("```java\nHere is my code: \n"+message+"```").queue();
        }
        else if(event.getMessage().getRawContent().equalsIgnoreCase("**closeWriter") && event.getAuthor().getName().equals("laxoholic26")) {
            log(event, "**closeWriter");
            Main.writer.close();
            event.getChannel().sendMessage(format("Log has stopped.", false)).queue();
        }
        else if(event.getMessage().getRawContent().equalsIgnoreCase("**music")) {
            log(event, "**music");
            event.getChannel().sendMessage(format("You will get music when I get funding.", false)).queue();
        }
        else if(event.getMessage().getRawContent().equalsIgnoreCase("fuck you bot")) {
            MessageBuilder mb = new MessageBuilder();
            String message = "```Fuck you, "+event.getAuthor().getName()+".```";
            mb.append(message).setTTS(true);
            log(event, "meanie");
            event.getChannel().sendMessage(mb.build()).queue();
            //event.getChannel().sendMessage(format("Fuck you, @"+event.getAuthor().getName(), false)).queue();
        }
        else if(event.getMessage().getRawContent().equalsIgnoreCase("**ddRollTraits")) {
            ddRollTraits(event);
        }
        else if(event.getMessage().getRawContent().equalsIgnoreCase("**ddhelp")) {
            printDDCommands(event);
        }
        else if(event.getMessage().getRawContent().equalsIgnoreCase("**ddweapons")) {
            printDDWeapons(event);
        }
        else if(event.getMessage().getRawContent().equalsIgnoreCase("**ddGetNWeapon")) {
            getRandWeapon(event);
        }
        else if(event.getMessage().getRawContent().equalsIgnoreCase("**ddGetAdvWeapon")) {
            getRandAdvWeapon(event);
        }
        else if(event.getMessage().getRawContent().equalsIgnoreCase("**ddarmors")) {
            printDDArmors(event);
        }
        else if(event.getMessage().getRawContent().equalsIgnoreCase("**ddMyStats")) {
            printDDStats(event);
        }
        else if(event.getMessage().getRawContent().equalsIgnoreCase("**join")) {
            List<VoiceChannel> channels = Main.newBot.getVoiceChannels();
            for(int i=0; i<channels.size(); i++) {
                System.out.println(channels.get(i).getName());
                if(channels.get(i).getName().equals("Hi-fi")) {
                    connectTo(Main.newBot.getVoiceChannelById(channels.get(i).getId()));
                    break;
                }
            }

        }
        else if(event.getMessage().getRawContent().equalsIgnoreCase("**ddNewGame")) {
            StringBuilder sb = new StringBuilder();
            sb.append("Game has been reset.\n");
            Dungeon.advList = new ArrayList<Adventurer>();
            Dungeon.weapons = new ArrayList<String>();
            Dungeon.traits = new ArrayList<String>();
            Dungeon.armors = new ArrayList<String>();
            Main.ddcomString = new ArrayList<String>();
            Dungeon.prefix = new ArrayList<String>();
            Dungeon.suffix = new ArrayList<String>();
            Dungeon.advweapons = new ArrayList<String>();
            Dungeon.weaponList = new ArrayList<DungeonWeapon>();
            Dungeon.loadWeapons();
            Dungeon.loadTraits();
            Dungeon.loadArmor();
            loadDDCommands();
            Dungeon.loadPrefix();
            Dungeon.loadSuffix();
            Dungeon.genWeps();
            log(event, "**ddNewGame");
            sb.append("Adventurers: "+Dungeon.advList.size()+"\n");
            sb.append("Weapons: "+Dungeon.weaponList.size()+"\n");
            sb.append("Traits: "+Dungeon.traits.size()+"\n");
            sb.append("Armors: "+Dungeon.armors.size()+"\n");
            sb.append("Prefixes: "+Dungeon.prefix.size()+"\n");
            sb.append("Suffixes: "+Dungeon.suffix.size()+"\n");
            event.getChannel().sendMessage(format(sb.toString(), false)).queue();
        }
        else if(event.getMessage().getRawContent().equalsIgnoreCase("**ddMyInv")) {
            if(Dungeon.advList.size() == 0) {
                event.getChannel().sendMessage(format("There are no adventurers.", false)).queue();
            } else {
                Adventurer adv = getAdv(event);
                StringBuilder sb = new StringBuilder();
                sb.append(adv.getInventory(event));
                event.getChannel().sendMessage(sb.toString()).queue();
            }
        }
        else if(event.getMessage().getRawContent().equalsIgnoreCase("**ddGetPlayers")) {
            ddGetPlayers(event);
        }
        else if(event.getMessage().getRawContent().equalsIgnoreCase("**ddprocess")) {
            sb = new StringBuilder();
            sb.append(format("To enter, each player must use **ddRollTraits to create an Adventurer.\n" +
                    "From there, you may check your stats, check your inventory, and see who else is in the dungeon.", false));
            event.getChannel().sendMessage(sb.toString()).queue();
        }
        else if(event.getMessage().getRawContent().equalsIgnoreCase("**ddGetArmor")) {
            ddGetArmor(event);
        }
        else if(event.getMessage().getRawContent().equalsIgnoreCase("**ddSaveGame")) {
            GameStateIO.SaveGame(event);
        }
        else if(event.getMessage().getRawContent().equalsIgnoreCase("**ddLoadGame")) {
            GameStateIO.LoadGame(event);
        }
        else if(event.getMessage().getRawContent().equalsIgnoreCase("**ddTestGame")) {
            ddRollTraits(event);
            GameStateIO.SaveGame(event);
            GameStateIO.LoadGame(event);
        }
        else if(event.getMessage().getRawContent().contains("**ddEquipWeapon:")) {
            ddEquipWeapon(event);
        }
        else if(event.getMessage().getRawContent().contains("**ddEquipArmor:")) {
            ddEquipArmor(event);
        }
        else if(event.getMessage().getRawContent().equalsIgnoreCase("**ddLeave")) {
            ddLeave(event);
        }
        else if(event.getMessage().getRawContent().equalsIgnoreCase("**ddDropWep")) {
            Adventurer adv = getAdv(event);
            String s = adv.dropWeapon();
            event.getChannel().sendMessage(format(s, false)).queue();
        }
    }

    @Override
    public void onReady(ReadyEvent event) {
        //System.out.println("status: Logged in as "+event.getJDA().getSelfUser().getName());
        Main.log(sdf.format(Main.date)+"||  Logged in as: " + event.getJDA().getSelfUser().getName());
    }

    //Format the message strings before they are sent to discord client
    public static String format(String s, boolean b) {
        if(!b) {
            return "```"+s+"```";
        }
        else
            return s;
    }

    //Roll a die based on the input user has given
    private static String rollDice(MessageReceivedEvent event, String s) {
        String base = "The results are in: ";
        int x = Integer.parseInt(s.substring(11, s.length()));
        String roll = String.valueOf(genRand(1, x));
        return base+roll;
    }

    //Generate random numbers between int min & int max
    public static int genRand(int min, int max) {
        return (min+(int)(Math.random()*((max - min) + 1)));
    }

    public static void log(MessageReceivedEvent event, String s) {
        Main.log(sdf.format(Main.date)+"|| "+event.getAuthor().getName()+" called "+s);
    }

    private static void getJoke(MessageReceivedEvent event) {
        int rand = genRand(0, Main.jokeString.size()-1);
        log(event, "**joke");
        message = format("Let me tell a joke: "+Main.jokeString.get(rand), false);
        event.getChannel().sendMessage(message).queue();
    }

    private static void getSpeechJoke(MessageReceivedEvent event) {
        MessageBuilder mb = new MessageBuilder();
        int rand = genRand(0, Main.jokeString.size()-1);
        log(event, "**joke");
        message = format("Let me tell a joke: "+Main.jokeString.get(rand), false);
        mb.append(message).setTTS(true);
        event.getChannel().sendMessage(mb.build()).queue();
    }

    private static void printDDCommands(MessageReceivedEvent event) {
        sb = new StringBuilder();
        log(event, "**ddInstruct");
        if (ddhelpMessage.length() == 0) {
            for (String i : Main.ddcomString) {
                sb.append(i);
                //Main.handleCommand(Main.parser.parse(event.getMessage().getContent().toLowerCase(), event));
            }
            ddhelpMessage = format(sb.toString(),false);
            event.getChannel().sendMessage(ddhelpMessage).queue();
        } else
            event.getChannel().sendMessage(ddhelpMessage).queue();
    }

    private static void printDDWeapons(MessageReceivedEvent event) {
        sb = new StringBuilder();
        log(event, "**ddweapons");
        if (ddWeapons.length() == 0) {
            for (String i : Dungeon.weapons) {
                sb.append(i);
            }
            ddWeapons = format(sb.toString(),false);
            event.getChannel().sendMessage(ddWeapons).queue();
        } else
            event.getChannel().sendMessage(ddWeapons).queue();
    }

    private static void getRandWeapon(MessageReceivedEvent event) {
        String name = event.getMessage().getAuthor().getName();
        //String weapon = Dungeon.weapons.get();
        DungeonWeapon wep = Dungeon.weaponList.get(genRand(0, Dungeon.weapons.size()));
        wep.setPrefix("NONE");
        wep.setPostfix("NONE");
        Adventurer adv = getAdv(event);
        wep.setLevel(adv.getDex());
        wep.setDam(adv.getStr());
        wep.setEledam(adv.getWis());
        adv.setWeapon(wep);
        log(event, "**getRandWeapon");
        event.getChannel().sendMessage(format(name+" has received a: \n"+wep.toString(), false)).queue();
    }

    private static void getRandAdvWeapon(MessageReceivedEvent event) {
        String name = event.getAuthor().getName();
        try {
            StringBuilder sb = new StringBuilder();
            Adventurer adv = getAdv(event);
            int rand = genRand(0, Dungeon.weaponList.size() - 1);
            System.out.println(name + " has rolled: " + rand);
            DungeonWeapon dw = Dungeon.weaponList.get(rand);
            dw.setLevel(adv.getDex());
            dw.setDam(adv.getStr());
            dw.setEledam(adv.getWis());
            adv.addWeapon(dw);
            String weapon = dw.toString();
            log(event, "**getRandAdvWeapon");
            sb.append(format(name + " has received a: \n" + weapon, false));
            System.out.println(sb.toString());
            event.getChannel().sendMessage(sb.toString()).queue();
        } catch(NullPointerException npe) {
            event.getChannel().sendMessage(format("Sorry, "+name+". It looks like you haven't entered the dungeon yet.", false)).queue();
        }
    }

    private static void printDDArmors(MessageReceivedEvent event) {
        sb = new StringBuilder();
        log(event, "**ddarmors");
        if (ddArmors.length() == 0) {
            for (String i : Dungeon.armors) {
                sb.append(i);
            }
            ddArmors = format(sb.toString(),false);
            event.getChannel().sendMessage(ddArmors).queue();
        } else
            event.getChannel().sendMessage(ddArmors).queue();
    }

    private static void printDDStats(MessageReceivedEvent event) {
        ddGetStats(event);
    }

    private void connectTo(VoiceChannel channel) {
        AudioManager manager = channel.getGuild().getAudioManager();
        manager.openAudioConnection(channel);
    }

    private MediaPlayer getMediaPlayer() {
        String diceRoll = "C:\\Users\\Conor\\IdeaProjects\\FirstDiscordBot\\src\\discord\\bots\\DiceRoll.mp3";
        Media hit = new Media(new File(diceRoll).toURI().toString());
        MediaPlayer mp = new MediaPlayer(hit);
        return mp;
    }

    private FilePlayer getMyFilePlayer() {
        try {
            return new FilePlayer(new File(diceRoll));
        } catch(IOException ioe) {
            ioe.printStackTrace();
        } catch(UnsupportedAudioFileException uafe) {
            uafe.printStackTrace();
        }
        return null;
    }

    public static Adventurer getAdv(MessageReceivedEvent event) {
        String name = event.getAuthor().getName();
        for(int i=0; i<Dungeon.advList.size(); i++) {
            if(name.equals(Dungeon.advList.get(i).getName())) {
                Adventurer adv = Dungeon.advList.get(i);
                return adv;
            }
        }
        return null;
    }

    private static void ddGetArmor(MessageReceivedEvent event) {
        if(Dungeon.advList.size() == 0) {
            int rand = genRand(0, Dungeon.armorList.size() - 1);
            DungeonArmor da = Dungeon.armorList.get(rand);
            da.setRating(genRand(0, 20));
            String armor = da.toString();
            log(event, "**getArmor");
            sb.append(format("TEST" + " has received a: \n" + armor, false));
            event.getChannel().sendMessage(sb.toString()).queue();
        } else {
            Adventurer adv = getAdv(event);
            int rand = genRand(0, Dungeon.armorList.size() - 1);
            System.out.println(adv.getName() + " has rolled: " + rand);
            DungeonArmor da = Dungeon.armorList.get(rand);
            da.setRating(adv.getCon());
            adv.addArmor(da);
            String armor = da.toString();
            log(event, "**getArmor");
            sb.append(format(adv.getName() + " has received a: \n" + armor, false));
            event.getChannel().sendMessage(sb.toString()).queue();
        }
    }

    public static void ddRollTraits(MessageReceivedEvent event) {
        boolean isMade = false;
        for (int i = 0; i < Dungeon.advList.size(); i++) {
            if (Dungeon.advList.get(i).getName().equals(event.getMessage().getAuthor().getName())) {
                isMade = true;
                System.out.println("found user: " + event.getAuthor().getName());
            }
        }
        if (!isMade) {
            sb = new StringBuilder();
            log(event, "**ddRollTraits");

            String name = event.getMessage().getAuthor().getName();

            Adventurer adv = new Adventurer(name, 0, 0, 0, 0, 0, 0, null, null, null, null);
            sb.append(name + " has entered the dungeon.\nThese are your traits:\n");
            for (int i = 0; i < Dungeon.traits.size(); i++) {
                int rand = genRand(1, 20);
                sb.append(Dungeon.traits.get(i) + ": " + String.valueOf(rand) + "\n");
                if (i == 0)
                    adv.setStr(rand);
                else if (i == 1)
                    adv.setDex(rand);
                else if (i == 2)
                    adv.setCon(rand);
                else if (i == 3)
                    adv.setInt(rand);
                else if (i == 4)
                    adv.setWis(rand);
                else if (i == 5)
                    adv.setCha(rand);
            }
            adv.setWepInv(new ArrayList<DungeonWeapon>());
            adv.setArmInv(new ArrayList<DungeonArmor>());
            Dungeon.advList.add(adv);
            event.getChannel().sendMessage(format(sb.toString(), false)).queue();
        } else {
            event.getChannel().sendMessage(format("You have already rolled, " + event.getAuthor().getName() + "! Your fate is set.", false)).queue();
        }
    }

    public static void ddGetStats(MessageReceivedEvent event) {
        sb = new StringBuilder();
        log(event, "**ddMyStats");
        for(int i=0; i<Dungeon.advList.size(); i++) {
            if(event.getAuthor().getName().equals(Dungeon.advList.get(i).getName())) {
                Adventurer adv = Dungeon.advList.get(i);
                String wep = "";
                String arm = "";
                if(adv.getWepItem() == null) {
                    wep = "none";
                } else {
                    wep = adv.getWepItem().toString();
                }
                if(adv.getArmItem() == null) {
                    arm = "none";
                } else {
                    arm = adv.getArmItem().toString();
                }
                sb.append("NME: "+adv.getName()+"\nSTR: "+adv.getStr()+"\nDEX: "+adv.getDex()+"\nCON: "
                        +adv.getCon()+"\nINT: "+adv.getInt()+"\nWIS: "+adv.getWis()+"\nCHA: "+adv.getCha()+
                        "\nWEP: "+wep+"\nARM: "+arm);
            }
        }
        event.getChannel().sendMessage(format(sb.toString(), false)).queue();
    }

    public static void ddEquipWeapon(MessageReceivedEvent event) {
        Adventurer adv = getAdv(event);
        int num = Integer.parseInt(event.getMessage().getRawContent().substring(17, event.getMessage().getRawContent().length()));
        System.out.println(num);
        event.getChannel().sendMessage(format(adv.equipWeapon(num-1), false)).queue();
    }

    public static void ddEquipArmor(MessageReceivedEvent event) {
        Adventurer adv = getAdv(event);
        int num = Integer.parseInt(event.getMessage().getRawContent().substring(16, event.getMessage().getRawContent().length()));
        System.out.println(num);
        event.getChannel().sendMessage(format(adv.equipArmor(num-1), false)).queue();
    }

    public static void ddLeave(MessageReceivedEvent event) {
        Adventurer adv = getAdv(event);
        for(int i=0; i<Dungeon.advList.size(); i++) {
            if(Dungeon.advList.get(i).getName().equalsIgnoreCase(adv.getName())) {
                Dungeon.advList.remove(i);
            }
        }
        event.getChannel().sendMessage(format(adv.getName()+" has left the dungeon.\n" +
                "The remaining adventurers are:\n"+ddGetPlayers(event), false));
    }

    public static String ddGetPlayers(MessageReceivedEvent event) {
        sb = new StringBuilder();
        sb.append("List of current adventurers:\n");
        if(Dungeon.advList.size() == 0)
            event.getChannel().sendMessage(format("There are no adventurers.", false)).queue();
        else {
            for (int i = 0; i < Dungeon.advList.size(); i++) {
                sb.append(i + 1 + ". " + Dungeon.advList.get(i).getName()+"\n");
            }
            event.getChannel().sendMessage(format(sb.toString(), false)).queue();
        }
        return sb.toString();
    }

    /*public URLPlayer getPlayer(JDA bot, String url) {
        try {
            return new URLPlayer(bot, new URL(url));
        } catch(MalformedURLException mue) {
            mue.printStackTrace();
        } catch(IOException ioe) {
            ioe.printStackTrace();
        } catch(UnsupportedAudioFileException uafe) {
            uafe.printStackTrace();
        }
    }*/
}
