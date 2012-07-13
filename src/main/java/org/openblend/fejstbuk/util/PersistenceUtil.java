package org.openblend.fejstbuk.util;

import javax.enterprise.context.ConversationScoped;
import javax.enterprise.inject.Default;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

import org.jboss.solder.core.ExtensionManaged;

/**
 * @author <a href="mailto:tomaz.cerar@redhat.com">Tomaz Cerar</a>
 */
public class PersistenceUtil {
    @ExtensionManaged
    @Produces
    @PersistenceUnit
    @ConversationScoped
    @Default
    private EntityManagerFactory producerField;

}
