package org.springframework.samples.petclinic.common;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.google.cloud.Date;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;

public class GoogleDateCombinedSerializer {
	public static class GoogleDateJsonSerializer extends JsonSerializer<Date> {
		@Override public void serialize(Date date, JsonGenerator jsonGenerator,
				SerializerProvider serializerProvider) throws IOException {

			jsonGenerator.writeString(date.toString());
		}
	}

	public static class GoogleDateJsonDeserializer extends JsonDeserializer<Date> {
		@Override public Date deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
				throws IOException, JsonProcessingException {
			return Date.parseDate(jsonParser.getValueAsString());
		}
	}
}
