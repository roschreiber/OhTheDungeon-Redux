/**
 * 
 */
package forge_sandbox.com.someguyssoftware.dungeons2;

import java.util.function.Supplier;
//import java.util.logging.Level;
//import org.bukkit.Bukkit;
//import shadow_lib.ExceptionRepoter;

public class Dungeons2 {
	public static class Dungeon2Logger {
		public boolean debug = false;
		public boolean warn = false;

		public boolean isDebugEnabled() {
			return debug;
		}

		public boolean isWarnEnabled() {
			return warn;
		}

		public void error(Supplier<String> splr) {
//            Bukkit.getLogger().log(Level.SEVERE, splr);
		}

		public void error(String str) {
//            Bukkit.getLogger().log(Level.SEVERE, str);
		}

		public void error(String str, Exception ex) {
//            Bukkit.getLogger().log(Level.SEVERE, ExceptionRepoter.exceptionToString(ex));
		}

		public void error(String str, Object[] os) {
//            str = str.replace("{}", "%s");
//            for(int i = 0; i < os.length; i++) {
//                os[i] = os[i].toString();
//            }
//            str = String.format(str, os );
//            Bukkit.getLogger().log(Level.INFO, str);
		}

		public void debug(Supplier<String> splr) {
//            Bukkit.getLogger().log(Level.INFO, splr);
		}

		public void debug(String str) {
//            Bukkit.getLogger().log(Level.INFO, str);
		}

		public void debug(String str, Object[] os) {
//            str = str.replace("{}", "%s");
//            for(int i = 0; i < os.length; i++) {
//                os[i] = os[i].toString();
//            }
//            str = String.format(str, os );
//
//            str = String.format(str, os );
//            Bukkit.getLogger().log(Level.INFO, str);
		}

		public void debug(String str, Exception ex) {
//            Bukkit.getLogger().log(Level.INFO, ExceptionRepoter.exceptionToString(ex));
		}

		public void warn(Supplier<String> splr) {
//            Bukkit.getLogger().log(Level.WARNING, splr);
		}

		public void warn(String str) {
//            Bukkit.getLogger().log(Level.WARNING, str);
		}

		public void warn(String str, Object[] os) {
//            str = str.replace("{}", "%s");
//            for(int i = 0; i < os.length; i++) {
//                os[i] = os[i].toString();
//            }
//            str = String.format(str, os );
//
//            str = String.format(str, os );
//            Bukkit.getLogger().log(Level.WARNING, str);
		}

		public void trace(String str) {
//            Bukkit.getLogger().log(Level.INFO, str);
		}

		public void trace(String str, Object[] os) {
//            str = str.replace("{}", "%s");
//            for(int i = 0; i < os.length; i++) {
//                os[i] = os[i].toString();
//            }
//            str = String.format(str, os );
//
//            str = String.format(str, os );
//            Bukkit.getLogger().log(Level.INFO, str);
		}

		public void info(String str) {
//            Bukkit.getLogger().log(Level.INFO, str);
		}
	}

	public static Dungeon2Logger log = new Dungeon2Logger();
}
