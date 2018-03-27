package io.github.skyshayde;

import com.darichey.discord.CommandRegistry;
import io.github.skyshayde.commands.Role;
import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.events.EventDispatcher;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.ReadyEvent;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.impl.obj.ReactionEmoji;
import sx.blah.discord.util.DiscordException;


public class AlfredBot {
    private static String DISCORD_TOKEN = Token.getDiscordToken();
    public static IDiscordClient client;
    public static EventDispatcher dispatcher;
    public static CommandRegistry registry;

    public static void main(String[] args) {
        client = createClient(DISCORD_TOKEN);
        dispatcher = client.getDispatcher();
        registry = new CommandRegistry("!");
        dispatcher.registerListener(new AlfredBot());
        Role role = new Role(dispatcher, registry);
    }


    private static IDiscordClient createClient(String token) { // Returns a new instance of the Discord client
        ClientBuilder clientBuilder = new ClientBuilder(); // Creates the ClientBuilder instance
        clientBuilder.withToken(token); // Adds the login info to the builder
        try {
            return clientBuilder.login(); // Creates the client instance and logs the client in
        } catch (DiscordException e) { // This is thrown if there was a problem building the client
            e.printStackTrace();
            return null;
        }
    }

    @EventSubscriber
    public void onReadyEvent(ReadyEvent event) { // This method is called when the ReadyEvent is dispatched
        System.out.println("AlfredBot has started - " + event.getClient().getApplicationClientID());
    }
}
