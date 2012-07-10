package org.openblend.fejstbuk.ui;

import java.io.Serializable;

import javax.ejb.Stateful;
import javax.enterprise.context.ConversationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.openblend.fejstbuk.dao.CustomDAO;
import org.openblend.fejstbuk.domain.User;
import org.openblend.fejstbuk.util.SecurityUtils;

/**
 * @author <a href="mailto:tomaz.cerar@redhat.com">Tomaz Cerar</a>
 */
@ConversationScoped
@Named
@Stateful
public class RegisterAction implements Serializable {

    private CustomDAO dao;

    private String name;
    private String lastName;
    private Integer age;
    private String gender;
    private String country;
    private String username;
    private String password;

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

    public void setAge(Integer age) {
        this.age = age;
    }

    @Min(value = 13,message = "Premlad si")
    @Max(value = 50,message = "Dedi zate ni fejstbuk")
    public Integer getAge() {
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

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String register(){
        User u = new User();
        u.setName(name);
        u.setSurname(lastName);
        u.setUsername(username);
        String hashed = SecurityUtils.hash(username, password);
        u.setPassword(hashed);
        boolean created = dao.createUser(u);
        if (!created)
        {
           FacesContext.getCurrentInstance().addMessage(null,
              new FacesMessage("Username is taken already. Try choose another :)"));
           return "registration-faild";
        }
        return "registered";
    }

    @Inject
    public void setDao(CustomDAO dao) {
        this.dao = dao;
    }
}
