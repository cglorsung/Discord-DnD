package discord.bots.functions;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

/**
 * Created by Conor on 3/27/2017.
 */
public interface Functions {

    public boolean called(String[] args, MessageReceivedEvent event);
    public void action(String[] args, MessageReceivedEvent event);
    public String help();
    public void executed(boolean success, MessageReceivedEvent event);

}
