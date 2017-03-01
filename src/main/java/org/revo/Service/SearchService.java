package org.revo.Service;

import org.hibernate.search.jpa.FullTextQuery;
import org.revo.Domain.SearchCriteria;
import org.revo.Domain.Song;

import java.util.List;

/**
 * Created by ashraf on 26/02/17.
 */
public interface SearchService {
    List<Song> search(SearchCriteria searchCriteria, String... fields);

    List<Song> searchAndGet(SearchCriteria searchCriteria, String... fields);

    FullTextQuery getFullTextQuery(SearchCriteria searchCriteria, String... fields);
}