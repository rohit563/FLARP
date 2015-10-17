import java.util.ArrayList;

public class FlarperManager {
   private static FlarperManager instance = null;
   
   public ArrayList<Flarper> flarpers = new ArrayList<Flarper>();
   public ArrayList<Sword> swords = new ArrayList<Sword>();
   
   protected FlarperManager() {
      // Exists only to defeat instantiation.
   }
   public static FlarperManager getInstance() {
      if(instance == null) {
         instance = new FlarperManager();
      }
      return instance;
   }
}
