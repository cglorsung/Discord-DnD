package discord.bots;

import discord.bots.functions.Functions;
import discord.bots.functions.PingCommand;
import discord.bots.utils.Dungeon;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import discord.bots.utils.CommandParser;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Scanner;

public class Main {

    /*
    These variables will be the control variables for the various file loading needs. Please
    enter the entire filepath so that the program may read the files correctly. Thank you!
     */
    private static final String DDCOMM_PATH = "C:\\Users\\Conor\\IdeaProjects\\FirstDiscordBot\\src\\discord\\bots\\ddcommands.txt";
    private static final String CODE_PATH = "C:\\Users\\Conor\\IdeaProjects\\FirstDiscordBot\\src\\discord\\bots\\DiceCode.txt";
    private static final String COMM_PATH = "C:\\Users\\Conor\\IdeaProjects\\FirstDiscordBot\\src\\discord\\bots\\commands.txt";
    private static final String TOKEN_PATH = "C:\\Users\\Conor\\IdeaProjects\\FirstDiscordBot\\src\\discord\\bots\\token.txt";
    private static final String JOKE_PATH = "C:\\Users\\Conor\\IdeaProjects\\FirstDiscordBot\\src\\discord\\bots\\jokes.txt";

    private static File ddcommandFile = new File(DDCOMM_PATH);
    private static File commandFile = new File(COMM_PATH);
    private static File tokenFile = new File(TOKEN_PATH);
    private static File jokeFile = new File(JOKE_PATH);
    private static File codeFile = new File(CODE_PATH);

    private static ArrayList<String> alString = new ArrayList<String>();
    private static Path logFile = Paths.get("log.txt");
    private static Scanner sc;

    public static final SimpleDateFormat sdf = new SimpleDateFormat("'('E')' dd/MM/yyyy hh:mm:ss a zzz");
    public static HashMap<String, Functions> commands = new HashMap<String, Functions>();
    public static ArrayList<String> ddcomString = new ArrayList<String>();
    public static ArrayList<String> jokeString = new ArrayList<String>();
    public static ArrayList<String> codeString = new ArrayList<String>();
    public static ArrayList<String> comString = new ArrayList<String>();
    public static final CommandParser parser = new CommandParser();
    public static Date date = new Date();
    public static PrintWriter writer;
    public static JDA newBot;

    public static void main(String[] args) {
	// write your code here
        long time = System.currentTimeMillis();
        try{
             sc = new Scanner(tokenFile);
             writer = new PrintWriter(new BufferedWriter(new FileWriter("log1.txt")));
        } catch(FileNotFoundException fnfe){
             fnfe.printStackTrace();
        } catch(IOException ioe) {
            ioe.printStackTrace();
        }

        System.out.println("Hello console");

        log(sdf.format(date)+"|| WRITER INITIALIZED SUCCESSFULLY");
        log(sdf.format(date)+"|| TESTING SECOND LINE");

        String token = sc.nextLine();
        sc.close();

        try {
            newBot = new JDABuilder(AccountType.BOT).addListener(new BotListener()).setToken(token).buildBlocking();
            newBot.setAutoReconnect(true);
        } catch(Exception e) {
            e.printStackTrace();
        }

        commands.put("ping", new PingCommand());
        Dungeon.loadWeapons();
        if(Dungeon.weapons.size() > 0)
            System.out.println("weapons loaded successfully "+Dungeon.weapons.get(0));
        Dungeon.loadTraits();
        if(Dungeon.traits.size() > 0)
            System.out.println("traits loaded successfully "+Dungeon.traits.get(0));
        Dungeon.loadArmor();
        if(Dungeon.armors.size() > 0)
            System.out.println("armors loaded successfully "+Dungeon.armors.get(0));
        loadDDCommands();
        if(ddcomString.size() > 0)
            System.out.println("ddcommands loaded successfully "+ddcomString.get(0));
        loadCommands();
        if(comString.size() > 0)
            System.out.println("commands loaded successfully "+comString.get(0));
        loadJokes();
        if(jokeString.size() > 0)
            System.out.println("jokes loaded successfully "+jokeString.get(0));
        loadCode();
        if(codeString.size() > 0)
            System.out.println("dice verification code loaded successfully "+codeString.get(0));
        Dungeon.loadPrefix();
        if(Dungeon.prefix.size() > 0)
            System.out.println("weapon prefixes loaded successfully");
        Dungeon.loadSuffix();
        if(Dungeon.suffix.size() > 0)
            System.out.println("weapon suffixes loaded successfully");
        Dungeon.genWeps();
        if(Dungeon.advweapons.size() > 0)
            System.out.println("special weapons loaded successfully");
        Dungeon.genArmor();
        if(Dungeon.armorList.size() > 0)
            System.out.println("armors loaded successfully");
        //confirmLoad();
    }

    public static void log(String s) {
            writer.println(s);
    }

    public static void loadCommands() {
        try {
            sc = new Scanner(commandFile);
        } catch (FileNotFoundException fnfe) {
            fnfe.printStackTrace();
        }
        while(sc.hasNext()) {
            String comm = sc.nextLine();
            comString.add(comm);
        }
    }

    public static void loadJokes() {
        try {
            sc = new Scanner(jokeFile);
        } catch (FileNotFoundException fnfe) {
            fnfe.printStackTrace();
        }
        while(sc.hasNext()) {
            String joke = sc.nextLine();
            jokeString.add(joke);
        }
    }

    public static void loadCode() {
        try {
            sc = new Scanner(codeFile);
        } catch (FileNotFoundException fnfe) {
            fnfe.printStackTrace();
        }
        while(sc.hasNext()) {
            String code = sc.nextLine();
            codeString.add(code+"\n");
        }
    }

    public static void loadDDCommands() {
        try {
            sc = new Scanner(ddcommandFile);
        } catch (FileNotFoundException fnfe) {
            fnfe.printStackTrace();
        }
        while(sc.hasNext()) {
            String command = sc.nextLine();
            ddcomString.add(command+"\n");
        }
    }

    /*private static void confirmLoad() {
        newBot.getTextChannels().get(0).sendMessage("```Really Friendly Bot is online.```").queue();
    }*/
}
