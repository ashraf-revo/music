package org.revo.Configration;

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

    @Override
    public void onApplicationEvent(final ContextRefreshedEvent event) {
        try {
            getFullTextEntityManager(em).createIndexer().startAndWait();
        } catch (InterruptedException e) {
        }

    }

}