package org.openblend.fejstbuk.ui;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 * @author <a href="mailto:marko.strukelj@gmail.com">Marko Strukelj</a>
 */
@SessionScoped
@Named("login")
public class Login implements Serializable {

    public void login() {
       System.out.println("Login.login() invoked!");
    }
}
