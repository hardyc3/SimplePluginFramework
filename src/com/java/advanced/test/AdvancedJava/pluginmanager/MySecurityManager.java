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

import java.security.Permission;

public class MySecurityManager extends SecurityManager
{
	private boolean allowAll = false;
	
	/**
	 * @param value true to allow all permissions
	 */
	public void setAllowAll(boolean value) {
		allowAll = value;
	}
	
	@Override
	public void checkPermission(Permission perm)
	{
		if(!allowAll) {
		
			super.checkPermission(perm);
		}
	}
}
