package org.giffardtechnologies.json.gson;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

public class BooleanDeserializer implements JsonDeserializer<Boolean> {
	
	@Override
	public Boolean deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
		try {
			return json.getAsBoolean();
		} catch (ClassCastException e) {
		}
		if ("yes".equals(json.getAsString())) {
			return true;
		} else if ("no".equals(json.getAsString())) {
			return false;
		}
		return json.getAsInt() == 1;
	}
}