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
package com.java.advanced.test.AdvancedJava.pluginmanager.plugin;

import com.java.advanced.test.AdvancedJava.pluginmanager.Plugin;

public class PluginTest implements Plugin
{

	@Override
	public void setup()
	{
		try {
			SecurityManager m = System.getSecurityManager();
			if(m != null)
				m.checkPermission(new RuntimePermission("createClassLoader"));
			else
				System.out.println("no security manager");
			System.out.println("I can create class loaders");
		} catch (Exception e) {
			
			System.out.println("Unable to create class loaders in unsecured code");
		}
		
		try {
			SecurityManager m = System.getSecurityManager();
			if(m != null)
				m.checkPermission(new RuntimePermission("exitVM.{3}"));
			else
				System.out.println("no security manager");
			
			System.out.println("I can shut down the system");
		} catch (Exception e) {
			
			System.out.println("Unable to exit in unsecured code");
		}
		
		System.out.println("Setup Plugin 1");
		
	}

	@Override
	public void tearDown()
	{
		try {
			SecurityManager m = System.getSecurityManager();
			if(m != null)
				m.checkPermission(new RuntimePermission("exitVM.{3}"));
			else
				System.out.println("no security manager");
			
			System.out.println("system shut down started");
			System.exit(3);
		} catch (Exception e) {
			
			System.out.println("Unable to exit in unsecured code");
		}
		System.out.println("Tear Down Plugin 1");
		
	}

	@Override
	public void performAction()
	{
		System.out.println("Plugin ACTION!!!!");		
	}

}