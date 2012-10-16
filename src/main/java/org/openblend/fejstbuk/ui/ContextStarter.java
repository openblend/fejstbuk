package org.openblend.fejstbuk.ui;

import javax.enterprise.context.ConversationScoped;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.InjectionTarget;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;


/**
 * @author <a href="mailto:tomaz.cerar@redhat.com">Tomaz Cerar</a>
 */


public class ContextStarter {
    @Inject
    BeanManager bm;

    @AroundInvoke
    public Object startContext(InvocationContext ctx) throws Exception {
        if (!bm.getContext(ConversationScoped.class).isActive()) {

            InjectionTarget<ConversationScoped> it = bm.createInjectionTarget(bm.createAnnotatedType(ConversationScoped.class));
            CreationalContext<ConversationScoped> cc = bm.createCreationalContext(null);
            it.produce(cc);


        }
       return ctx.proceed();
    }
}
