package br.com.cmachado.cashflowcontrol.utils;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.gson.*;
import org.springframework.data.util.Pair;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public abstract class Jsons {
    public static <T> T fromJson(String json, Class<T> classOfT) {
        return new Gson().fromJson(json, classOfT);
    }

    public static String toJson(Object src) {
        var gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                .registerTypeAdapter(LocalTime.class, new LocalTimeAdapter())
                .create();

        var json = gson.toJson(src);

        return json;
    }

    public static <T> Pair<String, T> fileToJson(String path, Class<T> type) throws IOException {
        var mapper = new ObjectMapper();

        mapper.registerModule(new JavaTimeModule());

        var file = new File(path);

        var object = mapper.readValue(file, type);

        var json = mapper.writeValueAsString(object);

        return Pair.of(json, object);
    }

    static class LocalDateAdapter implements JsonSerializer<LocalDate> {
        public JsonElement serialize(LocalDate date, Type typeOfSrc, JsonSerializationContext context) {
            return new JsonPrimitive(date.format(DateTimeFormatter.ISO_LOCAL_DATE)); // "yyyy-MM-dd"
        }
    }

    static class LocalDateTimeAdapter implements JsonSerializer<LocalDateTime> {
        public JsonElement serialize(LocalDateTime date, Type typeOfSrc, JsonSerializationContext context) {
            return new JsonPrimitive(date.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)); // "yyyy-MM-ddTHH:mm:ss"
        }
    }

    static class LocalTimeAdapter implements JsonSerializer<LocalTime> {
        public JsonElement serialize(LocalTime time, Type typeOfSrc, JsonSerializationContext context) {
            return new JsonPrimitive(time.format(DateTimeFormatter.ISO_LOCAL_TIME)); // "HH:mm:ss"
        }
    }
}