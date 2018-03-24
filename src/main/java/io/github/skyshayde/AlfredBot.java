package io.github.skyshayde;

import com.darichey.discord.CommandListener;
import com.darichey.discord.CommandRegistry;
import io.github.skyshayde.io.github.skyshayde.commands.Role;
import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.events.EventDispatcher;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.ReadyEvent;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.util.DiscordException;

import java.io.*;

public class AlfredBot {
    static String DISCORD_TOKEN = "";
    static IDiscordClient client;

    public static void main(String[] args) {
        DISCORD_TOKEN = getToken();

        client = createClient(DISCORD_TOKEN, true);
        EventDispatcher dispatcher = client.getDispatcher();
        dispatcher.registerListener(new AlfredBot());
        CommandRegistry registry = new CommandRegistry("!");

        Role role = new Role(dispatcher, registry);
    }

    private static String getToken() {
        InputStream tokenStream = new AlfredBot().getClass().getClassLoader().getResourceAsStream("tokens.txt");
        try {
            return new BufferedReader(new InputStreamReader(tokenStream)).readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }

    public static IDiscordClient createClient(String token, boolean login) { // Returns a new instance of the Discord client
        ClientBuilder clientBuilder = new ClientBuilder(); // Creates the ClientBuilder instance
        clientBuilder.withToken(token); // Adds the login info to the builder
        try {
            if (login) {
                return clientBuilder.login(); // Creates the client instance and logs the client in
            } else {
                return clientBuilder.build(); // Creates the client instance but it doesn't log the client in yet, you would have to call client.login() yourself
            }
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
