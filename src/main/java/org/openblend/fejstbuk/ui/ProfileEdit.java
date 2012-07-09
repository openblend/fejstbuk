package org.openblend.fejstbuk.ui;

import javax.enterprise.context.ConversationScoped;
import javax.faces.model.SelectItem;
import javax.inject.Named;

import org.openblend.fejstbuk.domain.Gender;
import org.openblend.fejstbuk.domain.User;

/**
 * @author <a href="mailto:ales.justin@jboss.org">Ales Justin</a>
 */
@ConversationScoped
@Named("profileEdit")
public class ProfileEdit extends AbstractUI {

    public User getCurrent() {
        return current;
    }

    public String save() {
        dao.merge(current);
        return "";
    }

    public SelectItem[] getGenderItems() {
        SelectItem[] items = new SelectItem[Gender.values().length];
        int i = 0;
        for(Gender g: Gender.values()) {
          items[i++] = new SelectItem(g, g.getLabel());
        }
        return items;
    }
}
