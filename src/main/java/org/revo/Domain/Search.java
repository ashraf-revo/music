package org.revo.Domain;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.revo.Util.ViewDetails;

import java.util.List;

/**
 * Created by ashraf on 01/03/17.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Search {
    @JsonView(ViewDetails.Search.class)
    private SearchCriteria searchCriteria;
    @JsonView(ViewDetails.Search.class)
    private List<Song> songs;
}
