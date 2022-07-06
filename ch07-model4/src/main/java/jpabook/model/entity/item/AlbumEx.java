package jpabook.model.entity.item;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "ALBUM_EX")
@DiscriminatorValue("A")    //  구분 컬럼에 입력할 값을 지정
public class AlbumEx extends ItemEx {
    
    private String artist;


    public String getArtist() {
        return this.artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

}
