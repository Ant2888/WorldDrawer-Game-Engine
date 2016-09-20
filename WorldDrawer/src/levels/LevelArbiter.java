package levels;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

import displays.WorldManager;
import displays.WorldTemplate;

public class LevelArbiter {
	WorldManager manager;
	HashMap<Class<? extends WorldTemplate>, WorldTemplate> gameWorlds;
	
	public LevelArbiter(WorldManager manager) {
		gameWorlds = new HashMap<>();
		this.manager = manager;
	}
	
	/**
	 * Adds a class to the map. 
	 * @param level
	 */
	public void addLevel(Class<? extends WorldTemplate> level){
		gameWorlds.put(level, null);
	}
	
	public void addLevel(Class<? extends WorldTemplate> level, WorldTemplate runTime){
		gameWorlds.put(level, runTime);
	}
	
	/**
	 * Attempts to initialize a class of type WorldTemplate.
	 * @param level
	 * @param wm
	 * @return
	 */
	public WorldTemplate initializeLevel(Class<? extends WorldTemplate> level, WorldManager wm){
		try {
			Constructor<?> init = level.getConstructor(WorldManager.class);
			WorldTemplate toRet = (WorldTemplate) init.newInstance(wm);
			return toRet;
		} catch (NoSuchMethodException | SecurityException | InstantiationException 
				| IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			System.out.println("FAILED TO INITIALIZE CLASS: "+level);
			e.printStackTrace();
			System.exit(1);
		}
		return null;
	}
	
	public WorldTemplate initializeLevel(Class<? extends WorldTemplate> level){
		return initializeLevel(level, manager);
	}

	/**
	 * Attempts to initialize the class THEN adds the class to the map (if successful)
	 * @param level
	 * @param wm
	 * @return
	 */
	public WorldTemplate initAndAdd(Class<? extends WorldTemplate> level, WorldManager wm){
		WorldTemplate toRet = initializeLevel(level, wm);
		gameWorlds.put(level, toRet);
		return toRet;
	}
	
	public WorldTemplate initAndAdd(Class<? extends WorldTemplate> level){
		WorldTemplate toRet = initializeLevel(level);
		gameWorlds.put(level, toRet);
		return toRet;
	}
	
	/**
	 * Gets the level currently loaded into runtime. Null if it is not initialized or not found.
	 * 
	 * @param level
	 * @return
	 */
	public WorldTemplate getLiveLevel(Class<? extends WorldTemplate> level){
		for(Class<? extends WorldTemplate> classes: gameWorlds.keySet()){
			if(classes == level)
				return gameWorlds.get(classes);
		}
		return null;
	}
	
	/**
	 * Determines if the arbiter contains the class for the level. 
	 * @param level
	 * @return
	 */
	public boolean getStaticLevel(Class<? extends WorldTemplate> level){
		for(Class<? extends WorldTemplate> classes: gameWorlds.keySet()){
			if(classes == level)
				return true;
		}
		return false;
	}
}
