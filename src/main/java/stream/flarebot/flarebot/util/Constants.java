package stream.flarebot.flarebot.util;

import java.time.Clock;
import java.time.LocalDateTime;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;
import stream.flarebot.flarebot.FlareBot;
import stream.flarebot.flarebot.Getters;
import stream.flarebot.flarebot.commands.Command;

public class Constants {

    public static final long OFFICIAL_GUILD = 242671971597418498L;
    private static final String FLAREBOT_API = "https://api.flarebot.stream";
    private static final String FLAREBOT_API_DEV = "http://localhost:8880";
    public static final String INVITE_URL = "https://discord.gg/WAhW492";
    public static final String INVITE_MARKDOWN = "[Support Server](" + INVITE_URL + ")";

    public static final long DEVELOPER_ID = 364016133654970368L;
    public static final long ADMINS_ID = 242673257440870402L;
    public static final long CONTRIBUTOR_ID = 242672917186478091L;

    private static final String FLARE_TEST_BOT_CHANNEL = "448149602898673675";
    public static final char COMMAND_CHAR = '_';
    public static final String COMMAND_CHAR_STRING = String.valueOf(COMMAND_CHAR);

    @Deprecated
    public static Guild getOfficialGuild() {
        return Getters.getGuildById(OFFICIAL_GUILD);
    }

    public static TextChannel getErrorLogChannel() {
        return (FlareBot.instance().isTestBot() ?
                Getters.getChannelById(Constants.FLARE_TEST_BOT_CHANNEL) :
                Getters.getChannelById("448148669062053888"));
    }

    public static TextChannel getGuildLogChannel() {
        return (FlareBot.instance().isTestBot() ?
                Getters.getChannelById(Constants.FLARE_TEST_BOT_CHANNEL) :
                Getters.getChannelById("448148782841069600"));
    }

    private static TextChannel getEGLogChannel() {
        return (FlareBot.instance().isTestBot() ?
                Getters.getChannelById(Constants.FLARE_TEST_BOT_CHANNEL) :
                Getters.getChannelById("448148908183650305"));
    }

    public static void logEG(String eg, Command command, Guild guild, User user) {
        EmbedBuilder builder = new EmbedBuilder().setTitle("Found `" + eg + "`")
                .addField("Guild", guild.getId() + " (`" + guild.getName() + "`) ", true)
                .addField("User", user.getAsMention() + " (`" + user.getName() + "#" + user.getDiscriminator() + "`)", true)
                .setTimestamp(LocalDateTime.now(Clock.systemUTC()));
        if (command != null) builder.addField("Command", command.getCommand(), true);
        Constants.getEGLogChannel().sendMessage(builder.build()).queue();
    }

    public static TextChannel getImportantLogChannel() {
        return (FlareBot.instance().isTestBot() ?
                Getters.getChannelById(Constants.FLARE_TEST_BOT_CHANNEL) :
                Getters.getChannelById("448149027746480128"));
    }

    public static String getAPI() {
        return FlareBot.instance().isTestBot() ? FLAREBOT_API_DEV : FLAREBOT_API;
    }
}
