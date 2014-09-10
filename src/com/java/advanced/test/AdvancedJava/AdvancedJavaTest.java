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
package com.java.advanced.test.AdvancedJava;

import java.io.IOException;
import java.util.List;

import com.java.advanced.test.AdvancedJava.pluginmanager.MySecurityManager;
import com.java.advanced.test.AdvancedJava.pluginmanager.Plugin;
import com.java.advanced.test.AdvancedJava.pluginmanager.PluginManager;

public class AdvancedJavaTest
{

	public static void main(String[] args) throws IOException
	{		
		MySecurityManager m = new MySecurityManager();
		System.setSecurityManager(m);
				
		m.setAllowAll(true);
		
		PluginManager pm = new PluginManager();
		List<Plugin> plugins = pm.loadPlugins();
		
		m.setAllowAll(false);
		for(Plugin plugin : plugins) {
			
			plugin.run();
		}
		m.setAllowAll(true);
	}

}
