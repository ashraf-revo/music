package org.revo.Domain;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.revo.Util.ViewDetails;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
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
@EntityListeners(AuditingEntityListener.class)
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

    public Like(Long id, Long user, Long song, Date createdDate) {
        super.setId(id);
        this.user = new User(user);
        this.song = new Song(song);
        super.setCreatedDate(createdDate);
    }
}