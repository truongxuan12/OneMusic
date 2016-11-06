package teamthat.com.onemusic.model;

/**
 * Created by ASUS on 11/1/2016.
 */
public class Artist {
    String Id;
    String name;
    String image;
    String love;
    String des;
static int a =0;
    public Artist() {
    }

    public Artist(String id, String name, String image, String love, String des) {
        Id = id;
        this.name = name;
        this.image = image;
        this.love = love;
        this.des = des;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLove() {
        return love;
    }

    public void setLove(String love) {
        this.love = love;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
