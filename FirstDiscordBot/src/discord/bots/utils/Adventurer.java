package discord.bots.utils;

import discord.bots.BotListener;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import sun.plugin2.message.Message;

import java.util.ArrayList;

import static discord.bots.BotListener.format;

/**
 * Created by Conor on 3/28/2017.
 */
public class Adventurer {

    private String name;
    private int str;
    private int dex;
    private int con;
    private int inte;
    private int wis;
    private int cha;
    private DungeonWeapon wep;
    private DungeonArmor arm;
    private ArrayList<DungeonWeapon> wepInv;
    private ArrayList<DungeonArmor> armInv;

    public Adventurer(String name, int STR, int DEX, int CON, int INT, int WIS, int CHA, DungeonWeapon wep, DungeonArmor arm, ArrayList<DungeonWeapon> wepInv, ArrayList<DungeonArmor> armInv) {
        this.name = name;
        this.str = STR;
        this.dex = DEX;
        this.con = CON;
        this.inte = INT;
        this.wis = WIS;
        this.cha = CHA;
        this.wep = null;
        this.arm = null;
        this.wepInv = wepInv;
        this.armInv = armInv;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String s) {
        this.name = s;
    }

    public int getStr() {
        return this.str;
    }

    public void setStr(int n) {
        this.str = n;
    }

    public int getDex() {
        return this.dex;
    }

    public void setDex(int n) {
        this.dex = n;
    }

    public int getCon() {
        return this.con;
    }

    public void setCon(int n) {
        this.con = n;
    }

    public int getInt() {
        return this.inte;
    }

    public void setInt(int n) {
        this.inte = n;
    }

    public int getWis() {
        return this.wis;
    }

    public void setWis(int n) {
        this.wis = n;
    }

    public int getCha() {
        return this.cha;
    }

    public void setCha(int n) {
        this.cha = n;
    }

    public String getWeapon() {
        return this.wep.toString();
    }

    public void setWeapon(DungeonWeapon dw) {
        this.wep = dw;
    }

    public String getArmor() {
        return this.arm.toString();
    }

    public void setArmor(DungeonArmor da) {
        this.arm = da;
    }

    public String getInventory(MessageReceivedEvent event) {
        StringBuilder sb = new StringBuilder();
        if(wepInv.size() == 0)
            return format("You are not holding anything.", false);
        else {
            sb.append(event.getAuthor().getName() + " is holding:\nWEAPONS:\n");
            for (int i = 0; i < wepInv.size(); i++) {
                sb.append(i + 1 + ". " + wepInv.get(i).toString());
            }
            sb.append("\nARMOR:\n");
            for(int i=0; i<armInv.size();i++) {
                sb.append(i + 1 + ". " + armInv.get(i).toString());
            }
            return format(sb.toString(), false);
        }
    }

    public void addWeapon(DungeonWeapon wep) {
        if(wepInv.size() == 0 && this.wep == null)
            this.wep = wep;
        else
            wepInv.add(wep);
    }

    public void addArmor(DungeonArmor arm) {
        if(armInv.size() == 0 && this.arm == null)
            this.arm = arm;
        else
            armInv.add(arm);
    }

    public ArrayList<DungeonWeapon> getWepInv() {
        return this.wepInv;
    }

    public void setWepInv(ArrayList<DungeonWeapon> wepInv) {
        this.wepInv = wepInv;
    }

    public ArrayList<DungeonArmor> getArmInv() {
        return this.armInv;
    }

    public void setArmInv(ArrayList<DungeonArmor> armInv) {
        this.armInv = armInv;
    }

    public DungeonWeapon getWepItem() {
        return this.wep;
    }

    public DungeonArmor getArmItem() {
        return this.arm;
    }

    public String equipWeapon(int n) {
        if(wepInv.size() > 0 && this.wep != null) {
            DungeonWeapon holder = this.wep;
            this.wep = wepInv.get(n);
            wepInv.remove(n);
            wepInv.add(holder);
            return this.name + " has equipped:\n" + this.wep.toString();
        } else {
            return "You cannot perform this action.";
        }
    }

    public String equipArmor(int n) {
        if(armInv.size() > 0 && this.arm != null) {
            DungeonArmor holder = this.arm;
            this.arm = armInv.get(n);
            armInv.remove(n);
            armInv.add(holder);
            return this.name + " has equipped:\n" + this.arm.toString();
        } else {
            return "You cannot perform this action.";
        }
    }

    public String dropWeapon() {
        DungeonWeapon wep = this.wep;
        if(wep == null) {
            return "You are not holding a weapon.";
        } else {
            this.wep = null;
            return "You have dropped:\n"+wep.toString();
        }
    }

    public String dropArmor() {
        DungeonArmor currwep = this.arm;
        if(currwep == null) {
            return "You are not wearing armor.";
        } else {
            this.arm = null;
            return "You have dropped:\n"+currwep.toString();
        }
    }
}
