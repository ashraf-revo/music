package org.revo.Domain;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.revo.Util.ViewDetails;

/**
 * Created by ashraf on 31/01/17.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SearchCriteria {
    @JsonView(ViewDetails.Search.class)
    private String search;
    @JsonView(ViewDetails.Search.class)
    private Page page;
}
