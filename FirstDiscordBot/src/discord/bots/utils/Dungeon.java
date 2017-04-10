package discord.bots.utils;

import discord.bots.BotListener;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Conor on 3/28/2017.
 */
public class Dungeon {
    private static final String POST_PATH = "C:\\Users\\Conor\\IdeaProjects\\FirstDiscordBot\\src\\discord\\bots\\dditems\\wpostfix.txt";
    private static final String WEAP_PATH = "C:\\Users\\Conor\\IdeaProjects\\FirstDiscordBot\\src\\discord\\bots\\dditems\\weaponNames.txt";
    private static final String PREF_PATH = "C:\\Users\\Conor\\IdeaProjects\\FirstDiscordBot\\src\\discord\\bots\\dditems\\wprefix.txt";
    private static final String ARMO_PATH = "C:\\Users\\Conor\\IdeaProjects\\FirstDiscordBot\\src\\discord\\bots\\dditems\\armor.txt";
    private static final String CODE_PATH = "C:\\Users\\Conor\\IdeaProjects\\FirstDiscordBot\\src\\discord\\bots\\DiceCode.txt";

    private static File commandFile = new File(CODE_PATH);
    private static File weaponFile = new File(WEAP_PATH);
    private static File prefixFile = new File(PREF_PATH);
    private static File suffixFile = new File(POST_PATH);
    private static File armorFile = new File(ARMO_PATH);
    private static Scanner sc;

    public static ArrayList<DungeonWeapon> weaponList = new ArrayList<DungeonWeapon>();
    public static ArrayList<DungeonArmor> armorList = new ArrayList<DungeonArmor>();
    public static ArrayList<Adventurer> advList = new ArrayList<Adventurer>();
    public static ArrayList<String> advweapons = new ArrayList<String>();
    public static ArrayList<String> weapons = new ArrayList<String>();
    public static ArrayList<String> traits = new ArrayList<String>();
    public static ArrayList<String> armors = new ArrayList<String>();
    public static ArrayList<String> prefix = new ArrayList<String>();
    public static ArrayList<String> suffix = new ArrayList<String>();
    public static ArrayList<String> rand = new ArrayList<String>();

    private static void main(String[] args) {
        loadItems();
    }

    public static void loadItems() {
        try {
            sc = new Scanner(commandFile);
        } catch (FileNotFoundException fnfe) {
            fnfe.printStackTrace();
        }
        while(sc.hasNext()) {
            String code = sc.nextLine();
            //items.add(code+"\n");
        }
    }

    public static void loadWeapons() {
        try {
            sc = new Scanner(weaponFile);
        } catch (FileNotFoundException fnfe) {
            fnfe.printStackTrace();
        }
        while(sc.hasNext()) {
            String weapon = sc.nextLine();
            weapons.add(weapon);
        }
        System.out.println(weapons.size());
    }

    public static void loadArmor() {
        try {
            sc = new Scanner(armorFile);
        } catch (FileNotFoundException fnfe) {
            fnfe.printStackTrace();
        }
        while(sc.hasNext()) {
            String armor = sc.nextLine();
            armors.add(armor+"\n");
        }
    }

    public static void loadPrefix() {
        try {
            sc = new Scanner(prefixFile);
        } catch (FileNotFoundException fnfe) {
            fnfe.printStackTrace();
        }
        while(sc.hasNext()) {
            String pre = sc.nextLine();
            prefix.add(pre);
        }
    }

    public static void loadSuffix() {
        try {
            sc = new Scanner(suffixFile);
        } catch (FileNotFoundException fnfe) {
            fnfe.printStackTrace();
        }
        while(sc.hasNext()) {
            String suff = sc.nextLine();
            suffix.add(suff);
        }
    }

    public static void genWeps() {
        System.out.println("PREFIX SIZE: "+prefix.size());
        System.out.println("SUFFIX SIZE: "+suffix.size());
        System.out.println("WEP SIZE: "+weapons.size());
        for(int i=0; i<weapons.size(); i++) {
            DungeonWeapon dw = new DungeonWeapon(prefix.get(BotListener.genRand(0, prefix.size()-1)), weapons.get(BotListener.genRand(0, weapons.size()-1)),
                    suffix.get(BotListener.genRand(0, suffix.size()-1)), 0, 0, 0);
            weaponList.add(dw);
        }
        System.out.println("WEAPONLIST SIZE: "+weaponList.size());
        for(int i=0; i<9; i++) {
            System.out.println(weaponList.get(i));
        }
        //System.out.println("ADVA WEP: "+advweapons.get(0));
    }

    public static void genArmor() {
        ArrayList<String> resistances = new ArrayList<String>();
        for(int i=0; i<armors.size(); i++) {
            DungeonArmor da = new DungeonArmor(armors.get(BotListener.genRand(0, armors.size()-1)), 0, resistances);
            armorList.add(da);
        }
        System.out.println("ARMORLIST SIZE: "+armorList.size());
        for(int i=0; i<armorList.size(); i++) {
            System.out.println(armorList.get(i));
        }
    }


    public static void loadTraits() {
        String str ="STR";
        String dex ="DEX";
        String con ="CON";
        String inte ="INT";
        String wis ="WIS";
        String cha ="CHA";

        traits.add(str);
        traits.add(dex);
        traits.add(con);
        traits.add(inte);
        traits.add(wis);
        traits.add(cha);
    }
}
