package io.github.skyshayde.commands;

import com.darichey.discord.Command;
import com.darichey.discord.CommandContext;
import com.darichey.discord.CommandRegistry;
import com.google.api.services.compute.Compute;
import com.google.api.services.compute.model.Instance;
import io.github.skyshayde.VMUtilities;
import sx.blah.discord.api.events.EventDispatcher;

import java.io.IOException;
import java.util.List;

public class ServerCommand {
    private static final String PROJECT_ID = "alfredbot-199021";
    private static final String ZONE_NAME = "us-east1-b";

    public ServerCommand(EventDispatcher dispatcher, CommandRegistry registry) {
        registry.register(srv, "server");
    }

    Command srv = Command.builder()
            .onCalled(ctx -> {
                if (!ctx.getAuthor().getRolesForGuild(ctx.getGuild()).contains(ctx.getGuild().getRoleByID(362703633734959134L))) {
                    return;
                }
                List<String> cmdArgs = ctx.getArgs();
                if (cmdArgs.size() > 0) {
                    switch (cmdArgs.get(0).toLowerCase()) {
                        case "start":
                            start(ctx);
                            break;
                        case "stop":
                            stop(ctx);
                            break;
                        case "status":
                            status(ctx);
                            break;
                        default:
                            defaultCase(ctx);
                            break;
                    }
                }
            })
            .build();


    void start(CommandContext ctx) {
        Compute compute = VMUtilities.getCompute();
        try {
            compute.instances().start(PROJECT_ID, ZONE_NAME, "minecraft").execute();
            ctx.getChannel().sendMessage("Server is starting");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void stop(CommandContext ctx) {
        Compute compute = VMUtilities.getCompute();
        try {
            compute.instances().stop(PROJECT_ID, ZONE_NAME, "minecraft").execute();
            ctx.getChannel().sendMessage("Server is stopping");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void status(CommandContext ctx) {
        Compute compute = VMUtilities.getCompute();
        try {
            Instance instance = compute.instances().get(PROJECT_ID, ZONE_NAME, "minecraft").execute();
            switch (instance.getStatus()) {
                case "RUNNING":
                    ctx.getChannel().sendMessage("Server: " + instance.getName() + " is currently running with IP: " + instance.getNetworkInterfaces().get(0).getAccessConfigs().get(0).getNatIP());
                    break;
                case "TERMINATED":
                    ctx.getChannel().sendMessage("Server: " + instance.getName() + " is currently not running");
                    break;
                default:
                    ctx.getChannel().sendMessage("Server: " + instance.getName() + " is not running or stopped.  It is likely starting up right now");
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void pack(CommandContext ctx) {
        new Thread(() -> {
            try {
                String server = ctx.getArgs().get(2);
                Compute compute = VMUtilities.getCompute();
                List<Instance> instances = compute.instances().list(PROJECT_ID, ZONE_NAME).execute().getItems();
                if (instances.stream().anyMatch(i -> i.getName().equals(server))) {
                    
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    void unpack(CommandContext ctx) {
        new Thread(() -> {
            String server = ctx.getArgs().get(2);
        });
    }

    void defaultCase(CommandContext ctx) {
    }

}
