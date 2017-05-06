package stream.flarebot.flarebot.commands.music;

import com.arsenarsen.lavaplayerbridge.PlayerManager;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;
import stream.flarebot.flarebot.FlareBot;
import stream.flarebot.flarebot.MessageUtils;
import stream.flarebot.flarebot.commands.Command;
import stream.flarebot.flarebot.commands.CommandType;
import stream.flarebot.flarebot.music.VideoThread;

public class PlayCommand implements Command {

    private PlayerManager musicManager;

    public PlayCommand(FlareBot bot) {
        this.musicManager = bot.getMusicManager();
    }

    @Override
    public void onCommand(User sender, TextChannel channel, Message message, String[] args, Member member) {
        if (args.length > 0) {
            if (args[0].startsWith("http") || args[0].startsWith("www.")) {
                VideoThread.getThread(args[0], channel, sender).start();
            } else {
                String term = FlareBot.getMessage(args, 0);
                VideoThread.getSearchThread(term, channel, sender).start();
            }
        } else {
            if (!(musicManager.getPlayer(channel.getGuild().getId()).getPlayingTrack() != null) &&
                    (musicManager.getPlayer(channel.getGuild().getId()).getPaused())) {
                MessageUtils.sendErrorMessage("There is no music playing!", channel);
            } else {
                musicManager.getPlayer(channel.getGuild().getId()).play();
                channel.sendMessage("Resuming...!").queue();
            }
        }
    }

    @Override
    public String getCommand() {
        return "play";
    }

    @Override
    public String getDescription() {
        return "Resumes your playlist or searches for songs on YouTube";
    }

    @Override
    public String getUsage() {
        return "`{%}play [searchTerm/URL]` - Resumes the playlist [or searches for a song on YouTube]";
    }

    @Override
    public CommandType getType() {
        return CommandType.MUSIC;
    }
}