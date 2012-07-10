package org.openblend.fejstbuk.clock;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

/**
 *
 */
@ApplicationScoped
@Named
public class ClockController {

    private ClockModel model = new ClockModel();

    public ClockModel getModel() {
        return model;
    }

    public void increaseMinutes() {
        int minutes = model.getMinutes() + 1;
        if (minutes == 60) {
            increaseHours();
            model.setMinutes(0);
        } else {
            model.setMinutes(minutes);
        }
    }

    private void increaseHours() {
        model.setHours(model.getHours() + 1);
    }
}
