package org.openblend.fejstbuk.model;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author <a href="mailto:marko.strukelj@gmail.com">Marko Strukelj</a>
 */
@Entity
public class User
{
   @Id
   private String username;

   private String password;

   public User() {}

   public User(String username) {
      this.username = username;
   }

   public String getUsername() {
      return username;
   }

   public void setUsername(String username) {

   }

   public String getPassword()
   {
      return password;
   }

   public void setPassword(String password)
   {
      this.password = password;
   }
}
