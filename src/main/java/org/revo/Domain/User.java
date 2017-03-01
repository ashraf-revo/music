package org.revo.Domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.lucene.analysis.core.LowerCaseFilterFactory;
import org.apache.lucene.analysis.core.StopFilterFactory;
import org.apache.lucene.analysis.ngram.NGramFilterFactory;
import org.apache.lucene.analysis.standard.StandardTokenizerFactory;
import org.hibernate.search.annotations.*;
import org.hibernate.search.annotations.Index;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.revo.Util.ViewDetails;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.READ_ONLY;
import static com.fasterxml.jackson.annotation.JsonProperty.Access.WRITE_ONLY;
import static org.hibernate.search.annotations.Index.YES;

/**
 * Created by ashraf on 18/01/17.
 */
@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Indexed
@AnalyzerDef(name = "customanalyzer",
        tokenizer = @TokenizerDef(factory = StandardTokenizerFactory.class),
        filters = {
                @TokenFilterDef(factory = LowerCaseFilterFactory.class),
                @TokenFilterDef(factory = NGramFilterFactory.class, params = {
                        @org.hibernate.search.annotations.Parameter(name = "minGramSize", value = "3"),
                        @org.hibernate.search.annotations.Parameter(name = "maxGramSize", value = "3")
                }),
                @TokenFilterDef(factory = StopFilterFactory.class, params = {
                        @org.hibernate.search.annotations.Parameter(name = "ignoreCase", value = "true")
                })
        })
@EntityListeners(AuditingEntityListener.class)
@Table(name = "music_user")
public class User extends BaseUser {
    @NotBlank
    @Column(length = 40)
    @Field(index = YES, store = Store.YES, analyzer = @Analyzer(definition = "customanalyzer"))
    @JsonView(ViewDetails.user.class)
    private String name;
    //        @URL
    @Column(length = 100)
    @Field(index = Index.NO, store = Store.YES)
    @JsonView(ViewDetails.user.class)
    private String imageUrl = "/assets/images/a0.png";
    @NotBlank
    @Column(length = 15)
    @JsonView(ViewDetails.user.class)
    private String phone;
    @Field(index = YES, store = Store.NO, analyzer = @Analyzer(definition = "customanalyzer"))
    @JsonView(ViewDetails.user.class)
    private String info;
    @Email
    @NotBlank
    @Column(length = 40, unique = true)
    @Field(index = YES, store = Store.YES, analyzer = @Analyzer(definition = "customanalyzer"))
    @JsonView(ViewDetails.user.class)
    private String email;
    @JsonProperty(access = WRITE_ONLY)
    @NotBlank
    @Column(length = 60)
    @JsonView(ViewDetails.user.class)
    private String password;
    @JsonProperty(access = WRITE_ONLY)
    @Transient
    @JsonView(ViewDetails.user.class)
    private String currentPassword;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
    @JsonView(ViewDetails.userSongs.class)
    private List<Song> songs = new ArrayList<>();
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
    @JsonView(ViewDetails.userLikes.class)
    private List<Like> likes = new ArrayList<>();
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
    @JsonView(ViewDetails.userViews.class)
    private List<View> views = new ArrayList<>();
    @Transient
    @JsonProperty(access = WRITE_ONLY)
    private MultipartFile image;


    @Override
    @JsonProperty(access = READ_ONLY)
    public String getUsername() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public User(Long id) {
        super.setId(id);
    }

    public User(Long id, String name, String imageUrl, String phone, String info, String email, Date createdDate) {
        super.setId(id);
        this.name = name;
        this.imageUrl = imageUrl;
        this.phone = phone;
        this.info = info;
        this.email = email;
        super.setCreatedDate(createdDate);
    }

    public User(Long id, String name, String imageUrl, String phone, String info, String email, Date createdDate, String password, boolean enable, boolean locked,String type) {
        super.setId(id);
        this.name = name;
        this.imageUrl = imageUrl;
        this.phone = phone;
        this.info = info;
        this.email = email;
        super.setCreatedDate(createdDate);
        this.password = password;
        super.setEnable(enable);
        super.setLocked(locked);
        super.setType(type);
    }

}