package discord.bots.functions;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

/**
 * Created by Conor on 3/27/2017.
 */
public class PingCommand implements Functions {
    private final String HELP = "To use: ~!ping";

    public boolean called(String[] args, MessageReceivedEvent event) {
        return true;
    }
    public void action(String[] args, MessageReceivedEvent event) {
        event.getTextChannel().sendMessage("PONG");
    }
    public String help() {
        return HELP;
    }
    public void executed(boolean success, MessageReceivedEvent event) {
        return;
    }

}
