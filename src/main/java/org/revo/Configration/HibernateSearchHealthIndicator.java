package org.revo.Configration;

import org.hibernate.search.SearchFactory;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.stat.Statistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;

/**
 * Created by ashraf on 27/02/17.
 */
@SuppressWarnings("unused")
@Component
public class HibernateSearchHealthIndicator extends AbstractHealthIndicator {
    @Autowired
    private EntityManager entityManager;

    @Override
    protected void doHealthCheck(Health.Builder builder) throws Exception {
        try {
            FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);
            Statistics statistics = fullTextEntityManager.getSearchFactory().getStatistics();
            builder.up().withDetail("search version", statistics.getSearchVersion());
            builder.withDetail("count", statistics.indexedEntitiesCount());
        }
        catch (java.lang.UnsupportedOperationException e){

        }
        catch (Exception e) {
            builder.down().withDetail("error", e.getMessage());
        }
    }
}
