package ru.rsreu.gorobchenko.project.shared.music;

import com.almasb.fxgl.audio.Music;
import com.almasb.fxgl.dsl.FXGL;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public class MusicPlayer {
    private static final MusicPlayer instance = new MusicPlayer();

    public static MusicPlayer getInstance() {
        return instance;
    }

    private final Queue<Music> musicQueue = new LinkedBlockingQueue<>();
    private Music currentMusic;
    private MusicPlaylist currentPlaylist;

    public void setPlaylist(MusicPlaylist playlist) {
        if (currentPlaylist == playlist) {
            return;
        }

        musicQueue.clear();
        this.currentPlaylist = playlist;
        musicQueue.addAll(this.currentPlaylist.getMusicList());
        playNext();
    }

    private void playNext() {
        if (currentMusic != null) {
            FXGL.getAudioPlayer().stopMusic(currentMusic);
        }

        if (musicQueue.isEmpty()) {
            throw new IllegalStateException("Music queue is empty. Set the music queue before playing.");
        }

        currentMusic = musicQueue.poll(); // Берем следующий трек
        musicQueue.offer(currentMusic);   // Добавляем его обратно в конец очереди

        FXGL.getAudioPlayer().playMusic(currentMusic);

        currentMusic.getAudio().setOnFinished(this::playNext);
    }

    public void nextInPlaylist() {
        if (musicQueue.isEmpty()) {
            setPlaylist(MusicPlaylist.GAME);
        } else {
            playNext();
        }
    }

    public void playOnce(Music music, MusicPlaylist nextPlaylist) {
        _playOnce(music);
        currentMusic.getAudio().setOnFinished(() -> setPlaylist(nextPlaylist));
    }

    public void playOnce(Music music) {
        _playOnce(music);
    }

    private void _playOnce(Music music) {
        FXGL.getAudioPlayer().stopAllMusic();
        currentPlaylist = null;
        currentMusic = music;
        FXGL.getAudioPlayer().playMusic(music);
    }
}
