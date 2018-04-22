package io.github.skyshayde.commands;

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

public class RoleCommand {
    Map<String, String> roles;
    public RoleCommand(EventDispatcher dispatcher, CommandRegistry registry) {
        registry.register(role, "role");
        roles =  new HashMap<String, String>();

        roles.put("overwatch","Overwatchers");
        roles.put("overwatchers","Overwatchers");
        roles.put("destiny","Destineers");
        roles.put("destineers","Destineers");
        roles.put("terraria","Terrarians");
        roles.put("terrarians","Terrarians");
        roles.put("minecraft","Minecrafters");
        roles.put("minecrafters","Minecrafters");
        roles.put("pokemon","Pokemans");
        roles.put("pokemans","Pokemans");
        roles.put("neptunespride","Neptunians");
        roles.put("neptunians","Neptunians");
        roles.put("fortnite","Fortniters");
        roles.put("fortniters","Fortniters");
        roles.put("heroesofthestorm","HOTS");
        roles.put("hots","HOTS");


    }

    Command role = Command.builder()
            .onCalled(ctx -> {
                List<String> cmdArgs = ctx.getArgs();
                if (cmdArgs.size() > 0) {
                    IRole role = ctx.getGuild().getRolesByName(roles.get(cmdArgs.get(0).toLowerCase())).get(0);
                    Boolean onoff = roleToggle(role, ctx.getAuthor(), ctx.getGuild());
                    ctx.getChannel().sendMessage("Toggling role " + role.getName() + (onoff ? " on" : " off") +" for " + ctx.getAuthor().getName());
                }
            })
            .build();

    public boolean roleToggle(IRole role, IUser user, IGuild guild) {
        if (user.hasRole(role)) {
            user.removeRole(role);
            return false;
        } else {
            user.addRole(role);
            return true;
        }
    }
}
