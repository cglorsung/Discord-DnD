package discord.bots.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import discord.bots.BotListener;
import discord.bots.Main;
import discord.bots.Main;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import static discord.bots.BotListener.format;
import static discord.bots.BotListener.genRand;

/**
 * Created by Conor on 4/1/2017.
 */
public class GameStateIO {

    private static final String SAVE_PATH = "C:\\Users\\Conor\\IdeaProjects\\FirstDiscordBot\\Save.txt";
    private static File file;
    private static PrintWriter pw;
    private static Scanner sc;

    public static void SaveGame(MessageReceivedEvent event) {
        BotListener.log(event, "**ddSaveGame");
        if (Dungeon.advList.size() == 0) {
            event.getChannel().sendMessage(format("There are no adventurers in the dungeon. Saving is pointless.", false)).queue();
        } else {
            try {
                File file = new File("Save.txt");
                PrintWriter pw = new PrintWriter(file);
                boolean complete = false;
                ArrayList<Adventurer> advList = Dungeon.advList;
                ArrayList<DungeonWeapon> wepList = Dungeon.weaponList;
                ArrayList<DungeonArmor> armList = Dungeon.armorList;
                pw.println("ADVLIST: "+advList.size());
                pw.println("WEPLIST: "+wepList.size());
                pw.println("ARMLIST: "+armList.size());
                while (!complete) {
                    for (int i = 0; i < advList.size(); i++) {
                        Adventurer adv = advList.get(i);
                        ArrayList<DungeonWeapon> dw = adv.getWepInv();
                        ArrayList<DungeonArmor> da = adv.getArmInv();
                        pw.println(adv.getName());
                        pw.println(adv.getStr());
                        pw.println(adv.getDex());
                        pw.println(adv.getCon());
                        pw.println(adv.getInt());
                        pw.println(adv.getWis());
                        pw.println(adv.getCha());
                        if(adv.getWepItem() != null) {
                            DungeonWeapon currWep = adv.getWepItem();
                            pw.println("CURRWEP: 1");
                            pw.println(currWep.getPrefix());
                            pw.println(currWep.getName());
                            pw.println(currWep.getPostFix());
                            pw.println(currWep.getLevel());
                            pw.println(currWep.getDam());
                            pw.println(currWep.getEledam());
                        } else {
                            pw.println("CURRWEP: 0");
                        }
                        if(adv.getArmItem() != null) {
                            DungeonArmor currArm = adv.getArmItem();
                            pw.println("CURRARM: 1");
                            pw.println(currArm.getName());
                            pw.println(currArm.getRating());
                            if(currArm.getResist().size() == 0) {
                                pw.println("NORESIST");
                            } else {
                                for (int m = 0; m < currArm.getResist().size(); m++) {
                                    pw.println(currArm.getResist().get(m));
                                }
                            }
                        } else {
                            pw.println("CURRARM: 0");
                        }
                        pw.println("WEPINVI: "+dw.size());
                        for (int j = 0; j < dw.size(); j++) {
                            DungeonWeapon wep = dw.get(i);
                            pw.println(wep.getPrefix());
                            pw.println(wep.getName());
                            pw.println(wep.getPostFix());
                            pw.println(wep.getLevel());
                            pw.println(wep.getDam());
                            pw.println(wep.getEledam());
                        }
                        pw.println("ARMINVI: "+da.size());
                        for (int k = 0; k < da.size(); k++) {
                            DungeonArmor arm = da.get(k);
                            pw.println(arm.getName());
                            pw.println(arm.getRating());
                            pw.println("RESLIST: "+arm.getResist().size());
                            for (int l = 0; l < arm.getResist().size(); l++) {
                                pw.println(arm.getResist().get(l));
                            }
                        }
                    }
                    pw.println("ALLWEPS");
                    ArrayList<DungeonWeapon> fullWepList = Dungeon.weaponList;
                    for (int n = 0; n < fullWepList.size(); n++) {
                        pw.println(fullWepList.get(n).getPrefix());
                        pw.println(fullWepList.get(n).getName());
                        pw.println(fullWepList.get(n).getPostFix());
                        pw.println(fullWepList.get(n).getLevel());
                        pw.println(fullWepList.get(n).getDam());
                        pw.println(fullWepList.get(n).getEledam());
                    }
                    pw.println("ALLARMS");
                    ArrayList<DungeonArmor> fullArmList = Dungeon.armorList;
                    for (int o = 0; o < fullArmList.size(); o++) {
                        pw.println(fullArmList.get(o).getName());
                        pw.println(fullArmList.get(o).getRating());
                        if(fullArmList.get(o).getResist().size() == 0) {
                            pw.println("RESLIST: "+fullArmList.get(o).getResist().size());
                        } else {
                            pw.println("RESLIST: "+fullArmList.get(o).getResist().size());
                            for (int p = 0; p < fullArmList.get(o).getResist().size(); p++) {
                                pw.println(fullArmList.get(o).getResist().get(p));
                            }
                        }
                    }
                    pw.println("END SAVE FILE");
                    pw.close();
                    complete = true;
                }
            } catch (FileNotFoundException fnfe) {
                fnfe.printStackTrace();
            }

            event.getChannel().sendMessage(format("Game has been saved.", false)).queue();
        }
    }

    public static void LoadGame(MessageReceivedEvent event) {
        BotListener.log(event, "**ddLoadGame");
        Dungeon.advList = new ArrayList<Adventurer>();
        Dungeon.weaponList = new ArrayList<DungeonWeapon>();
        Dungeon.armorList = new ArrayList<DungeonArmor>();

        try {
            file = new File(SAVE_PATH);
            sc = new Scanner(file);
            String advLine = sc.nextLine();
            String wepLine = sc.nextLine();
            String armLine = sc.nextLine();
            int advSize = Integer.parseInt(advLine.substring(9, advLine.length()));
            int wepSize = Integer.parseInt(wepLine.substring(9, wepLine.length()));
            int armSize = Integer.parseInt(armLine.substring(9, armLine.length()));
            System.out.println("advSize: "+advSize+"\nwepSize: "+wepSize+"\narmSize: "+armSize);
            while(true) {
                for (int i = 0; i < advSize; i++) {
                    ArrayList<DungeonWeapon> dwList = new ArrayList<DungeonWeapon>();
                    ArrayList<DungeonArmor> daList = new ArrayList<DungeonArmor>();
                    DungeonWeapon dw = null;
                    DungeonArmor da = null;
                    String name = sc.nextLine();
                    int str = Integer.parseInt(sc.nextLine());
                    int dex = Integer.parseInt(sc.nextLine());
                    int con = Integer.parseInt(sc.nextLine());
                    int inte = Integer.parseInt(sc.nextLine());
                    int wis = Integer.parseInt(sc.nextLine());
                    int cha = Integer.parseInt(sc.nextLine());
                    int n = Integer.parseInt(String.valueOf(sc.nextLine().charAt(9)));
                    System.out.println(n);
                    if (n == 1) {
                        String prefix = sc.nextLine();
                        String wepName = sc.nextLine();
                        String suffix = sc.nextLine();
                        int level = Integer.parseInt(sc.nextLine());
                        int dam = Integer.parseInt(sc.nextLine());
                        int eledam = Integer.parseInt(sc.nextLine());
                        dw = new DungeonWeapon(prefix, wepName, suffix, level, dam, eledam);
                    } else {
                        if (String.valueOf(sc.nextLine().charAt(9)).equals(1)) {
                            String armName = sc.nextLine();
                            int rating = Integer.parseInt(sc.nextLine());
                            ArrayList<String> resist = new ArrayList<String>();
                            int size = Integer.parseInt(String.valueOf(sc.nextLine().charAt(9)));
                            for (int j = 0; j < size; j++) {
                                resist.add(sc.nextLine());
                            }
                            da = new DungeonArmor(armName, rating, resist);
                        }
                    }
                    String wepinvsize = sc.nextLine();
                    if (Integer.parseInt(wepinvsize.substring(9, wepinvsize.length())) != 0) {
                        for(int q=0; q<Integer.parseInt(wepinvsize.substring(9, wepinvsize.length())); q++) {
                            DungeonWeapon newWep;
                            String prefix = sc.nextLine();
                            String wepName = sc.nextLine();
                            String suffix = sc.nextLine();
                            int level = Integer.parseInt(sc.nextLine());
                            int dam = Integer.parseInt(sc.nextLine());
                            int eledam = Integer.parseInt(sc.nextLine());
                            dw = new DungeonWeapon(prefix, wepName, suffix, level, dam, eledam);
                            dwList.add(dw);
                        }
                    }
                    String arminvsize = sc.nextLine();
                    if (Integer.parseInt(arminvsize.substring(9, arminvsize.length())) != 0) {
                        for(int r=0; r<Integer.parseInt(arminvsize.substring(9, arminvsize.length())); r++) {
                            DungeonArmor newArm;
                            String armName = sc.nextLine();
                            int rating = Integer.parseInt(sc.next());
                            ArrayList<String> resist = new ArrayList<String>();
                            int size = Integer.parseInt(String.valueOf(sc.nextLine().charAt(9)));
                            for (int j = 0; j < size; j++) {
                                resist.add(sc.nextLine());
                            }
                            da = new DungeonArmor(armName, rating, resist);
                            daList.add(da);
                        }
                    }
                    Adventurer adv = new Adventurer(name, str, dex, con, inte, wis, cha, dw, da, dwList, daList);
                    Dungeon.advList.add(adv);
                }
                if (sc.nextLine().equals("ALLWEPS")) {
                    for (int k = 0; k < wepSize; k++) {
                        DungeonWeapon newWep;
                        String prefix = sc.nextLine();
                        String wepName = sc.nextLine();
                        String suffix = sc.nextLine();
                        int wepLev = Integer.parseInt(sc.nextLine());
                        int wepDam = Integer.parseInt(sc.nextLine());
                        int eleDam = Integer.parseInt(sc.nextLine());
                        newWep = new DungeonWeapon(prefix, wepName, suffix, wepLev, wepDam, eleDam);
                        Dungeon.weaponList.add(newWep);
                    }
                }
                System.out.println(Dungeon.weaponList.get(genRand(0, Dungeon.weaponList.size()-1)));
                sc.nextLine();
                    for (int l = 0; l < armSize; l++) {
                        DungeonArmor newArm;
                        String armName = sc.nextLine();
                        System.out.println("ARMNAME: "+armName);
                        int armRating = sc.nextInt();
                        System.out.println("ARMRATE: "+armRating);
                        ArrayList<String> resistance = new ArrayList<String>();
                        String resList = sc.next() + " " + sc.nextInt();
                        int size = Integer.parseInt(resList.substring(9, resList.length()));
                        System.out.println("RESLIST: "+resList);
                        if(size != 0) {
                            for (int m = 0; m < size; m++) {
                                resistance.add(sc.nextLine());
                            }
                        }
                        newArm = new DungeonArmor(armName, armRating, resistance);
                        System.out.println(newArm);
                        Dungeon.armorList.add(newArm);
                        sc.nextLine();
                    }
                //} else {System.out.println()}
                if (sc.nextLine().equals("END SAVE FILE")) {
                    event.getChannel().sendMessage(format("Game loaded successfully.", false)).queue();
                } else {
                    event.getChannel().sendMessage(format("Something went wrong and you should not be seeing this.", false)).queue();
                }
                break;
            }
            sc.close();
        } catch(FileNotFoundException fnfe) {
            fnfe.printStackTrace();
        }
        //event.getChannel().sendMessage(format("This function is not yet available.", false)).queue();
    }
}
