package entity;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

import java.util.Calendar;

@Entity
public class GameInfo {
    @Id
    private long id;
    @Index
    private String name;
    private String description;
    private String genre;
    private String publisher;
    private String image;
    private long release;
    private int status;

    public GameInfo() {
        this.id = Calendar.getInstance().getTimeInMillis();
        this.status = Status.active.getValue();
    }

    public GameInfo(String name, String description, String genre, String publisher, String image, long release) {
        this.name = name;
        this.description = description;
        this.genre = genre;
        this.publisher = publisher;
        this.image = image;
        this.release = release;
    }
        public enum Status{
        active(1),deactive(0),delete(-1);
        int value;
        Status(int value){
            this.value =value;
        }
        public int getValue(){
            return value;
        }
        }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public long getRelease() {
        return release;
    }

    public void setRelease(long release) {
        this.release = release;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status.getValue();
    }
}
