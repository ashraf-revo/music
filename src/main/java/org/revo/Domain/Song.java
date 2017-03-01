package org.revo.Domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.hibernate.search.annotations.*;
import org.hibernate.validator.constraints.NotBlank;
import org.revo.Util.ViewDetails;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.WRITE_ONLY;
import static org.hibernate.search.annotations.Index.NO;
import static org.hibernate.search.annotations.Index.YES;

/**
 * Created by ashraf on 18/01/17.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Indexed
@EntityListeners(AuditingEntityListener.class)
@Table(name = "music_song")
public class Song extends BaseEntity {
    @NotBlank
    @Column(length = 70)
    @Field(index = YES, store = Store.YES, analyzer = @Analyzer(definition = "customanalyzer"))
    @Boost(5)
    @JsonView(ViewDetails.song.class)
    private String title;
    //    @URL
    @Column(length = 100)
    @Field(index = NO, store = Store.YES)
    @JsonView(ViewDetails.song.class)
    private String imageUrl = "/assets/images/p1.jpg";
    //    @URL
    @Column(length = 100)
    @JsonView(ViewDetails.song.class)
    private String fileUrl = "/assets/audio/a0.mp3";
    @NotBlank
    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @JsonView(ViewDetails.song.class)
    private String description;
    @ManyToOne
    @JoinColumn
    @NotNull
    @CreatedBy
    @JsonView(ViewDetails.songUser.class)
    private User user;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "song")
    @JsonView(ViewDetails.songLikes.class)
    private List<Like> likes = new ArrayList<>();
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "song")
    @JsonView(ViewDetails.songViews.class)
    private List<View> views = new ArrayList<>();
    @Transient
    @JsonProperty(access = WRITE_ONLY)
    private MultipartFile file;
    @Transient
    @JsonProperty(access = WRITE_ONLY)
    private MultipartFile image;
    @Transient
    @JsonView(ViewDetails.song.class)
    private Like liked = null;

    public Song(Long id) {
        super.setId(id);
    }

    public Song(Long id, String title, String imageUrl, String fileUrl, String description, Long uid, String uname, String uimageUrl, String uphone, String uinfo, String uemail, Date ucreatedDate, Date createdDate) {
        super.setId(id);
        this.title = title;
        this.imageUrl = imageUrl;
        this.fileUrl = fileUrl;
        this.description = description;
        this.user = new User(uid, uname, uimageUrl, uphone, uinfo, uemail, ucreatedDate);
        super.setCreatedDate(createdDate);
    }

    public static String[] ProjectionField() {
        return new String[]{"id", "title","imageUrl"};
    }
}