/**
 * Copyright 2014 Hardy Cherry
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 **/
package com.java.advanced.test.AdvancedJava.pluginmanager;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class PluginManager
{
	private String pluginDir = ".";
	private List<Plugin> plugins;
	
	/**
	 * @param newPluginDir the new plugin directory to look in for plugins
	 */
	public void setPluginDir(String newPluginDir) {
		
		pluginDir = newPluginDir;
	}
	
	/**
	 * Loads plugins from the pluginDir and returns a list of instances of the plugin classes.
	 * @return the plugin object instances
	 */
	public List<Plugin> loadPlugins() {
		
		plugins = new ArrayList<Plugin>();
		
		List<File> filesList = getJarFiles();
		
		for(int i = 0; i < filesList.size(); i++) {
			
			File file = filesList.get(i);
				
			try {
				List<String> classNames = loadClassFilesFromJar(file);
					
				for(String className : classNames) {
					plugins.add(getPluginInstance(className, file));
				}					
			} catch(FileNotFoundException e) {
				e.printStackTrace();
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
		
		return plugins;
	}
	
	/**
	 * @return list of jar files in the pluginDir
	 */
	private List<File> getJarFiles() {

		FileFilter jarFilter = new FileFilter() {	
			@Override
			public boolean accept(File pathname)
			{
				return pathname.getName().toUpperCase().endsWith("JAR");
			}
		};
		
		File filePath = new File(pluginDir);
		File[] files = filePath.listFiles(jarFilter);
		List<File> filesList = Arrays.stream(files).collect(Collectors.toList());
		
		return filesList;
	}
	
	/**
	 * Extracts from a jar file all the classes and returns a list of the class names with out the .class extension.
	 * @param jarFile the jar file to extract classes from
	 * @return the list of class names
	 * @throws IOException if there are problems accessing the jar file.
	 */
	private List<String> loadClassFilesFromJar(File jarFile) throws IOException {
		
		List<String> classNames = new ArrayList<String>();
		ZipInputStream zip = null;
		try {
			zip = new ZipInputStream(new FileInputStream(jarFile.getAbsolutePath()));
		
			for (ZipEntry entry = zip.getNextEntry(); entry != null; entry = zip.getNextEntry())
			{
				if (entry.getName().endsWith(".class") && !entry.isDirectory())
				{
					classNames.add(entry.getName().substring(0, entry.getName().length()-".class".length()));
				}
			}
		} finally {
			zip.close();
		}
		
		return classNames;
	}

	/**
	 * Loads a class from the jar file and gets a new instance of it. 
	 * @param className the class to load
	 * @param jarFile the jar file to load from
	 * @return the new instance of the loaded class
	 * @throws IOException if there are problems accessing the file
	 */
	private Plugin getPluginInstance(String className, File jarFile) throws IOException {
		
		Plugin result = null;
		
		URL[] urls = {new URL("jar:file:" + jarFile.getAbsolutePath() + "!/")};					
		
		MyLoader loader = null;
		try {
			loader = new MyLoader(urls, this.getClass().getClassLoader());		
			try {
				className = className.replace("/",".");
				Class cls = loader.loadClass(className);
				
				Object classInstance = cls.newInstance();
				if(classInstance instanceof Plugin) {
					result = (Plugin)classInstance;
				}				
			} catch(ClassNotFoundException e) {
				e.printStackTrace();
			} catch(IllegalAccessException | InstantiationException e) {
				e.printStackTrace();
			}
		} finally {
			loader.close();
		}
		
		return result;
	}
	
	private class MyLoader extends URLClassLoader {
	    
		public MyLoader(URL[] urls, ClassLoader parent)
		{
			super(urls, parent);
		}

		/**
	     * This implementation of loadClass uses a child first delegation model rather than the standard parent first.
	     * If the requested class cannot be found in this class loader, the parent class loader will be consulted
	     * via the standard ClassLoader.loadClass(String) mechanism.
	     */
		@Override
	    public Class<?> loadClass(String className) throws ClassNotFoundException {
	        			
	        try {
	            return getClass().getClassLoader().loadClass(className);
	        } catch (ClassNotFoundException e) {
	        }
	    
	        // second check whether it's already been loaded
	        Class<?> clazz = findLoadedClass(className);
	        if (clazz != null) {
	        	return clazz;
	        }
	
	        // nope, try to load locally
	        try {
	            clazz = findClass(className);
	            return clazz;
	        } catch (ClassNotFoundException e) {
	        	// try next step
	        }
	
	        // use the standard URLClassLoader (which follows normal parent delegation)
	        return super.loadClass(className);
	    }
		
		public void close() throws IOException
		{
			super.close();
		}
	}
}
