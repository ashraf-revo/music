package org.revo.Configration;

import org.hibernate.search.MassIndexer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.hibernate.search.jpa.Search.getFullTextEntityManager;

/**
 * Created by revo on 26/09/15.
 */
@SuppressWarnings("unused")
@Component
public class BuildSearchIndex implements ApplicationListener<ContextRefreshedEvent> {
    @PersistenceContext
    private EntityManager em;
    @Value("${org.revo.app_env.reindex}")
    private boolean reindex;

    @Override
    public void onApplicationEvent(final ContextRefreshedEvent event) {
        try {
            MassIndexer indexer = getFullTextEntityManager(em).createIndexer();
            if (reindex)
                indexer.startAndWait();

        } catch (InterruptedException e) {
        }

    }

}