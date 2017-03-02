package org.revo.Domain;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.Setter;
import org.apache.lucene.analysis.core.LowerCaseFilterFactory;
import org.apache.lucene.analysis.core.StopFilterFactory;
import org.apache.lucene.analysis.ngram.NGramFilterFactory;
import org.apache.lucene.analysis.standard.StandardTokenizerFactory;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.search.annotations.AnalyzerDef;
import org.hibernate.search.annotations.TokenFilterDef;
import org.hibernate.search.annotations.TokenizerDef;
import org.revo.Util.ViewDetails.like;
import org.revo.Util.ViewDetails.song;
import org.revo.Util.ViewDetails.user;
import org.revo.Util.ViewDetails.view;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.Date;

/**
 * Created by ashraf on 18/02/17.
 */
@MappedSuperclass
@Getter
@Setter
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