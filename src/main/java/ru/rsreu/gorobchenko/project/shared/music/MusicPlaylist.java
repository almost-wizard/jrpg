package ru.rsreu.gorobchenko.project.shared.music;

import com.almasb.fxgl.audio.Music;
import com.almasb.fxgl.dsl.FXGL;

import java.util.ArrayList;
import java.util.List;

public enum MusicPlaylist {
    GAME("menu.mp3", "amb_sw_karczma.mp3", "amb_city_dng2.mp3", "amb_catacombs.mp3", "amb_old_vizima.mp3", "amb_outdoor_dng1.mp3", "amb_sewers_03.mp3"),
    WIN("Believe.mp3"),
    DEAD("game-over.mp3"),
    TEST("open-door.wav", "close-door.wav"),
    ;

    private final List<Music> musicList = new ArrayList<>();

    MusicPlaylist(String... musicList) {
        for (String musicName : musicList) {
            this.musicList.add(FXGL.getAssetLoader().loadMusic(musicName));
        }
    }

    public List<Music> getMusicList() {

        return musicList;
    }
}
