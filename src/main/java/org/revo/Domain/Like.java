package org.revo.Domain;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.revo.Util.ViewDetails;
import org.springframework.data.annotation.CreatedBy;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created by ashraf on 18/01/17.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "music_like")
public class Like extends BaseEntity {
    @ManyToOne
    @JoinColumn
    @NotNull
    @CreatedBy
    @JsonView(ViewDetails.likeUser.class)
    private User user;
    @ManyToOne
    @JoinColumn
    @NotNull
    @JsonView(ViewDetails.likeSong.class)
    private Song song;

    @Override
    public String toString() {
        return "Like{" +
                "id='" + getId() + '\'' +
                ", createdDate='" + getCreatedDate() + '\'' +
                ", user='" + user.getId() + '\'' +
                ", song='" + song.getId() + '\'' +
                '}';
    }

    public Like(Long id, Long user, Long song, Date createdDate) {
        super.setId(id);
        this.user = new User(user);
        this.song = new Song(song);
        super.setCreatedDate(createdDate);
    }
}