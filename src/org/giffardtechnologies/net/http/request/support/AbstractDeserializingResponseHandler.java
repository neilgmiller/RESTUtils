package org.giffardtechnologies.net.http.request.support;

import java.lang.reflect.Array;
import java.lang.reflect.ParameterizedType;

import org.giffardtechnologies.net.http.request.Deserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public abstract class AbstractDeserializingResponseHandler<T> extends AbstractResponseHandler<T> {
	private static Logger s_logger = LoggerFactory.getLogger(AbstractDeserializingResponseHandler.class);
	
	private final Class<T> responseClass;
	
	private final Gson mGsonForPrinting;
	
	@SuppressWarnings("unchecked")
	protected AbstractDeserializingResponseHandler() {
		super();
		responseClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setPrettyPrinting();
		mGsonForPrinting = gsonBuilder.create();
	}
	
	public AbstractDeserializingResponseHandler(Class<T> classOfT) {
		super();
		responseClass = classOfT;
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setPrettyPrinting();
		mGsonForPrinting = gsonBuilder.create();
	}
	
	public abstract Deserializer getDeserializer();
	
	@Override
	protected T convert(int statusCode, String entity) {
		if (statusCode == 204 && responseClass.isArray()) {
			@SuppressWarnings("unchecked")
			T emptyArray = (T) Array.newInstance(responseClass.getComponentType(), 0);
			return emptyArray;
		} else {
			if (responseClass.equals(Void.class)) {
				// no response
				return null;
			}
			// Not Void so continue
			JsonElement jsonElement = new JsonParser().parse(entity);
			if (s_logger.isDebugEnabled()) {
				String json = mGsonForPrinting.toJson(jsonElement);
				s_logger.debug(json);
			}
			return getDeserializer().fromJson(entity, responseClass);
		}
	}
	
}
