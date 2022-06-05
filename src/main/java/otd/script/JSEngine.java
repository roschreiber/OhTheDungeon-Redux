package otd.script;

import javax.script.ScriptEngine;

import org.openjdk.nashorn.api.scripting.NashornScriptEngineFactory;

import otd.Main;

public class JSEngine {

	public static ScriptEngine getEngine() {
		return (new NashornScriptEngineFactory()).getScriptEngine(Main.instance.getClass().getClassLoader());
//		return getScriptEngineManager().getEngineByName(ENGINE_NAMEs.get(0));
	}
}
