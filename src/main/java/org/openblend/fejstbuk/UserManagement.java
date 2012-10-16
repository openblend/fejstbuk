package org.openblend.fejstbuk;

import javax.enterprise.context.ApplicationScoped;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author <a href="mailto:marko.strukelj@gmail.com">Marko Strukelj</a>
 */
@ApplicationScoped
public class UserManagement
{
   private Map<String, String> usersMap = new ConcurrentHashMap<String, String>();

   public UserManagement()
   {
      usersMap.put("janez", "blender");
   }

   public boolean login(String username, String password)
   {
      String pass = usersMap.get(username);
      return password.equals(pass);
   }
}
