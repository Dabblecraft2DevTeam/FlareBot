package com.bwfcwalshy.flarebot.commands.administrator;

import com.bwfcwalshy.flarebot.FlareBot;
import com.bwfcwalshy.flarebot.MessageUtils;
import com.bwfcwalshy.flarebot.commands.Command;
import com.bwfcwalshy.flarebot.commands.CommandType;
import com.bwfcwalshy.flarebot.permissions.Group;
import com.bwfcwalshy.flarebot.util.Parser;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IUser;

import java.io.IOException;

public class PermissionsCommand implements Command {

    @Override
    public void onCommand(IUser sender, IChannel channel, IMessage message, String[] args) {
        if (args.length == 0) {
            MessageUtils.sendMessage(getDescription(), channel);
            return;
        }
        switch (args[0].toLowerCase()) {
            case "givegroup":
                if (args.length < 3) {
                    MessageUtils.sendMessage(getDescription(), channel);
                    return;
                }
                IUser user = Parser.mention(args[1]);
                if (user == null) {
                    MessageUtils.sendMessage("No such user!", channel);
                    return;
                }
                if (!getPermissions(channel).hasGroup(args[2])) {
                    MessageUtils.sendMessage("No such group!", channel);
                    return;
                }
                Group group = getPermissions(channel).getGroup(args[2]);
                if (getPermissions(channel).getUser(user).addGroup(group))
                    MessageUtils.sendMessage("Success", channel);
                else MessageUtils.sendMessage("User already had that group!", channel);
                break;
            case "revokegroup":
                if (args.length < 3) {
                    MessageUtils.sendMessage(getDescription(), channel);
                    return;
                }
                IUser user2 = Parser.mention(args[1]);
                if (user2 == null) {
                    MessageUtils.sendMessage("No such user!", channel);
                    return;
                }
                if (!getPermissions(channel).hasGroup(args[2])) {
                    MessageUtils.sendMessage("No such group!", channel);
                    return;
                }
                Group group2 = getPermissions(channel).getGroup(args[2]);
                if (getPermissions(channel).getUser(user2).removeGroup(group2))
                    MessageUtils.sendMessage("Success", channel);
                else MessageUtils.sendMessage("User never had that group!", channel);
                break;
            case "addpermission":
                if (args.length < 3) {
                    MessageUtils.sendMessage(getDescription(), channel);
                    return;
                }
                Group group3 = getPermissions(channel).getGroup(args[1]);
                if (getPermissions(channel).addPermission(group3.getName(), args[2]))
                    MessageUtils.sendMessage("Success", channel);
                else MessageUtils.sendMessage("Group already had that permission", channel);
                break;
            case "removepermission":
                if (args.length < 3) {
                    MessageUtils.sendMessage(getDescription(), channel);
                    return;
                }
                Group group4 = getPermissions(channel).getGroup(args[1]);
                if (getPermissions(channel).removePermission(group4.getName(), args[2]))
                    MessageUtils.sendMessage("Success", channel);
                else MessageUtils.sendMessage("Group never had that permission", channel);
                break;
            case "list":
                if (args.length < 2) {
                    MessageUtils.sendMessage(getDescription(), channel);
                    return;
                }
                if (!getPermissions(channel).hasGroup(args[1])) {
                    MessageUtils.sendMessage("No such group!", channel);
                    return;
                }
                Group group5 = getPermissions(channel).getGroup(args[1]);
                StringBuilder perms = new StringBuilder("**Permissions for group ").append(group5.getName())
                        .append("**\n```fix\n");
                group5.getPermissions().forEach(perm -> perms.append(perm).append('\n'));
                perms.append("\n```");
                MessageUtils.sendMessage(perms, channel);
                break;
            case "save":
                if (getPermissions(channel).isCreator(sender))
                    try {
                        FlareBot.getInstance().getPermissions().save();
                    } catch (IOException e) {
                        MessageUtils.sendException("Could not save permissions!", e, channel);
                    }
                break;
            default:
                MessageUtils.sendMessage(getDescription(), channel);
                break;
        }
    }

    @Override
    public String getCommand() {
        return "permissions";
    }

    @Override
    public String[] getAliases() {
        return new String[]{"perm", "perms"};
    }

    @Override
    public String getDescription() {
        return "permissions givegroup | revokegroup <user> <group> for user management or list | addpermission | removepermission <group> <permission> for group management.";
    }

    @Override
    public CommandType getType() {
        return CommandType.ADMINISTRATIVE;
    }

    @Override
    public String getPermission() {
        return "flarebot.permissions";
    }
}
