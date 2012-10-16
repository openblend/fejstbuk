package org.openblend.fejstbuk.ui;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * @author <a href="mailto:marko.strukelj@gmail.com">Marko Strukelj</a>
 */
@SessionScoped
@Named("login")
public class Login implements Serializable {

    @Inject
    private Credentials credentials;

    public void login() {
       System.out.println("Login.login() invoked!");
       System.out.println(" Username: " + credentials.getUsername());
       System.out.println(" Password: " + credentials.getPassword());
    }
}
