package io.github.skyshayde.io.github.skyshayde.commands;

import com.darichey.discord.Command;
import com.darichey.discord.CommandListener;
import com.darichey.discord.CommandRegistry;
import sx.blah.discord.api.events.EventDispatcher;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IRole;
import sx.blah.discord.handle.obj.IUser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Role {
    HashMap<String, String> roles = new HashMap<String, String>();

    public Role(EventDispatcher dispatcher, CommandRegistry registry) {
        roles.put("overwatch", "Overwatchers");
        roles.put("destiny", "Destineers");
        roles.put("terraria", "Terrarians");
        roles.put("minecraft", "Minecrafters");
        roles.put("pokemon", "Pokemans");
        roles.put("neptunespride", "Neptunians");

        registry.register(role, "role");
        dispatcher.registerListener(new CommandListener(registry));
    }

    Command role = Command.builder()
            .onCalled(ctx -> {
                List<String> cmdArgs = ctx.getArgs();
                if (cmdArgs.size() > 0) {
                    IRole role = ctx.getGuild().getRolesByName(roles.get(cmdArgs.get(0))).get(0);
                    roleToggle(role, ctx.getAuthor(), ctx.getGuild());
                    ctx.getChannel().sendMessage("Toggling role " + role.getName() + " for " + ctx.getAuthor().getName());
                }
            })
            .build();

    public void roleToggle(IRole role, IUser user, IGuild guild) {
        if (user.hasRole(role)) {
            user.removeRole(role);
        } else {
            user.addRole(role);
        }
    }
}
