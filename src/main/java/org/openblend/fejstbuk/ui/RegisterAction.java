package org.openblend.fejstbuk.ui;

import java.io.Serializable;
import javax.ejb.Stateful;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.openblend.fejstbuk.domain.User;

/**
 * @author <a href="mailto:tomaz.cerar@redhat.com">Tomaz Cerar</a>
 */
@ConversationScoped
@Named
@Stateful
public class RegisterAction implements Serializable {
    @PersistenceContext
    private EntityManager em;

    private String name;
    private String lastName;
    private int age;
    private String gender;
    private String country;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NotNull(message = "Prosim vnesi priimek")
    @Size(min = 2,message = "priimek mora biti vsaj dva znaka")
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Min(value = 13,message = "Premlad si")
    @Max(value = 50,message = "Dedi zate ni fejstbuk")
    public int getAge() {
        return age;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getGender() {
        return gender;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCountry() {
        return country;
    }

    public void register(){
        User u = new User();
        u.setName(name);
        u.setSurname(lastName);
        em.persist(u);

    }
}
