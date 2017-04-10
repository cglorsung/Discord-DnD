package discord.bots.utils;

import java.util.ArrayList;

import static discord.bots.BotListener.format;

/**
 * Created by Conor on 3/28/2017.
 */
public class DungeonArmor {

    private String name;
    private int rating;
    private ArrayList<String> resist;

    public DungeonArmor(String name, int rating, ArrayList<String> resist) {
        this.name = name;
        this.rating = rating;
        this.resist = resist;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRating() {
        return this.rating;
    }

    public void setRating(int n) {
        this.rating = n;
    }

    public ArrayList<String> getResist() {
        return this.resist;
    }

    public void setResist(ArrayList<String> resist) {
        this.resist = resist;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(name+"(def:"+this.getRating()+"|res:");
        if(resist.size() == 0)
            sb.append("none)");
        for(int i=0; i<resist.size(); i++) {
            if(i==resist.size()-1) {
                sb.append(resist.get(i)+")");
            }
            else {
                sb.append(resist.get(i)+", ");
            }
        }
        return sb.toString();
    }
}
