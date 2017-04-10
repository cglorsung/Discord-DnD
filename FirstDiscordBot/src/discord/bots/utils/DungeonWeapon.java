package discord.bots.utils;

import discord.bots.BotListener;

/**
 * Created by Conor on 3/28/2017.
 */
public class DungeonWeapon {
    private String prefix;
    private String name;
    private String postfix;
    private int level;
    private int dam;
    private int eledam;

    public DungeonWeapon(String prefix, String name, String postfix, int level, int dam, int eledam) {
        this.prefix = prefix;
        this.name = name;
        this.postfix = postfix;
        this.level = level;
        this.dam = dam;
        this.eledam = eledam;
    }

    public String getPrefix() {
        return this.prefix;
    }

    public void setPrefix(String s) {
        this.prefix = s;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String s) {
        this.name = s;
    }

    public String getPostFix() {
        return this.postfix;
    }

    public void setPostfix(String s) {
        this.postfix = s;
    }

    public int getLevel() {
        return this.level;
    }

    public void setLevel(int n) {
        this.level = n;
    }

    public int getDam() {
        return this.dam;
    }

    public void setDam(int n) {
        this.dam = n;
    }

    public int getEledam() {
        return this.eledam;
    }

    public void setEledam(int n) {
        this.eledam = n;
    }

    @Override
    public String toString() {
        if(this.prefix.equals("NONE")) {
            return name+"\n(lvl:"+level+"|dam:"+dam+"|ele:"+eledam+")";
        } else {
            return prefix + " " + name + " of " + postfix + "\n(lvl:" + level + "|dam:" + dam + "|ele:" + eledam + ")";
        }
    }
}
