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
public class Page {
    @JsonView(ViewDetails.Search.class)
    private int number = 0;
    @JsonView(ViewDetails.Search.class)
    private int size = 10;
}