package com.example.musicbot.listeners.impl;

import com.example.musicbot.LavaplayerAudioSource;
import com.example.musicbot.TrackScheduler;
import com.example.musicbot.listeners.CringeListener;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.youtube.YoutubeAudioSourceManager;
import org.javacord.api.DiscordApi;
import org.javacord.api.audio.AudioConnection;
import org.javacord.api.audio.AudioSource;
import org.javacord.api.event.message.MessageCreateEvent;
import org.springframework.stereotype.Component;

@Component
public class CringeListenerImpl implements CringeListener {

    @Override
    public void onMessageCreate(MessageCreateEvent messageCreateEvent) {
        // Create a player manager
        AudioPlayerManager playerManager = new DefaultAudioPlayerManager();
        playerManager.registerSourceManager(new YoutubeAudioSourceManager());
        AudioPlayer player = playerManager.createPlayer();

// Create an audio source and add it to the audio connection's queue
        DiscordApi api= messageCreateEvent.getApi();
        AudioSource source = new LavaplayerAudioSource(api, player);

        TrackScheduler scheduler= new TrackScheduler(player);
        if(messageCreateEvent.getMessageContent().equals("!cringe")){
            //messageCreateEvent.getChannel().sendMessage(messageCreateEvent.getMessageAuthor().getIdAsString());
           //new MessageBuilder().setContent("Poraka").send(messageCreateEvent.getMessageAuthor().asUser().get());

            messageCreateEvent.getMessageAuthor().getConnectedVoiceChannel().get().connect().thenAcceptAsync(audioConnection -> {
                audioConnection.setAudioSource(source);

                playerManager.loadItem("https://www.youtube.com/watch?v=oZaLXmkbO3E&ab_channel=Dosis",scheduler);
                messageCreateEvent.getApi().addMessageCreateListener(messageCreateEvent1 -> {
                    if(messageCreateEvent1.getMessageContent().equals("!leave")){

                        audioConnection.close();
                    }
                });
            });


        }

        }



    }

