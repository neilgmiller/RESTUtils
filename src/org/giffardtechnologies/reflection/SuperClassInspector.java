/*
 * Copyright 2014 Neil Miller
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
 */
package org.giffardtechnologies.reflection;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class SuperClassInspector {
	
	private final Class<?> m_theClass;
	private final ParameterizedType m_genericSuperclass;
	private final Type[] m_actualTypeArguments;
	
	public SuperClassInspector(Class<?> theClass) {
		super();
		m_theClass = theClass;
		m_genericSuperclass = (ParameterizedType) m_theClass.getGenericSuperclass();
		m_actualTypeArguments = m_genericSuperclass.getActualTypeArguments();
	}
	
	public <Z> Class<Z> findActualClassArgumentToSuper(int genericIndex) throws IndexOutOfBoundsException {
		return GenericsUtil.findActualClassArgument(m_actualTypeArguments[genericIndex]);
	}
}
