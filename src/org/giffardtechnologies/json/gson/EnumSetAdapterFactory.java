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
package org.giffardtechnologies.json.gson;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.EnumSet;
import java.util.Set;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

public final class EnumSetAdapterFactory implements TypeAdapterFactory {
	@Override
	public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
		// return null, if the type is not an EnumSet
		@SuppressWarnings("unchecked")
		final Class<T> rawType = (Class<T>) type.getRawType();
		Type fullType = type.getType();
		if (!EnumSet.class.isAssignableFrom(rawType)) {
			return null;
		}
		@SuppressWarnings("rawtypes")
		final Class enumClass = lookupEnumClass(fullType);
		if (enumClass == null) {
			// unable to lookup the enum class
			throw new IllegalStateException("Unable to lookup the enum class for EnumSet: " + type.toString());
		}
		
		final TypeAdapter<T> delegateAdapter = gson.getDelegateAdapter(this, type);
		
		return new TypeAdapter<T>() {
			
			@Override
			public void write(JsonWriter out, T value) throws IOException {
				delegateAdapter.write(out, value);
			}
			
			@Override
			public T read(JsonReader in) throws IOException {
				// We know that T is an EnumSet, at the very least Gson will treat this like a set 
				// Seems to use LinkedHashSet as of writing, we'll let it create one of those and then 
				// copy it to an EnumSet.
				@SuppressWarnings({ "rawtypes", "unchecked" })
				Set<Enum> set = (Set<Enum>) delegateAdapter.read(in);
				if (set.isEmpty()) {
					@SuppressWarnings("unchecked")
					T copyOf = (T) EnumSet.noneOf(enumClass);
					return copyOf;
				} else {
					@SuppressWarnings("unchecked")
					T copyOf = (T) EnumSet.copyOf(set);
					return copyOf;
				}
			}
		};
	}
	
	@SuppressWarnings("rawtypes")
	protected Class lookupEnumClass(Type fullType) {
		if (fullType instanceof ParameterizedType) {
			ParameterizedType parameterizedType = (ParameterizedType) fullType;
			return (Class) parameterizedType.getActualTypeArguments()[0];
		} else {
			return null;
		}
	}
}