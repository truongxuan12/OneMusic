package teamthat.com.onemusic.model;

/**
 * Created by thietit on 11/1/2016.
 */

public class ArtistMusic {

    String nameMusic;
    String musicPath;

    public ArtistMusic() {
    }

    public ArtistMusic(String nameMusic, String musicPath) {
        this.nameMusic = nameMusic;
        this.musicPath = musicPath;
    }

    public String getNameMusic() {
        return nameMusic;
    }

    public void setNameMusic(String nameMusic) {
        this.nameMusic = nameMusic;
    }

    public String getMusicPath() {
        return musicPath;
    }

    public void setMusicPath(String musicPath) {
        this.musicPath = musicPath;
    }

    @Override
    public String toString() {
        return this.nameMusic;
    }
}
