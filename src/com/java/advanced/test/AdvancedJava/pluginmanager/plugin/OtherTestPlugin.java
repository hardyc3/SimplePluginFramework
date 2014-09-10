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

public class OtherTestPlugin implements Plugin
{
	@Override
	public void setup()
	{
		System.out.println("Setup 2");
		
	}

	@Override
	public void tearDown()
	{
		System.out.println("Tear down 2");
		
	}

	@Override
	public void performAction()
	{
		System.out.println("ACTION 2!!!");
		
	}
}
