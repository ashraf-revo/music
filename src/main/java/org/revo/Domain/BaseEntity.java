package org.revo.Domain;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.revo.Util.ViewDetails.like;
import org.revo.Util.ViewDetails.song;
import org.revo.Util.ViewDetails.user;
import org.revo.Util.ViewDetails.view;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by ashraf on 18/02/17.
 */
@MappedSuperclass
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
abstract class BaseEntity {
    @Id
    @GenericGenerator(name = "wikiSequenceGenerator", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator")
    @GeneratedValue(generator = "wikiSequenceGenerator")
    @JsonView({song.class, user.class, like.class, view.class})
    private Long id;
    @CreatedDate
    @JsonView({song.class, user.class, like.class, view.class})
    private Date createdDate = new Date();
}