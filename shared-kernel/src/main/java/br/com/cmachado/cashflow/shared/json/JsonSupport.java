package br.com.cmachado.cashflow.shared.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializer;
import com.google.gson.JsonDeserializer;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Shared Gson configured for the cross-service message contract. Both the
 * producer (transaction-service) and the consumer (consolidation-service) must
 * serialize {@link LocalDateTime} the same way, so it is centralised here.
 */
public final class JsonSupport {
    private static final DateTimeFormatter ISO = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    private static final Gson GSON = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class,
                    (JsonSerializer<LocalDateTime>) (src, type, ctx) ->
                            new JsonPrimitive(src.format(ISO)))
            .registerTypeAdapter(LocalDateTime.class,
                    (JsonDeserializer<LocalDateTime>) (json, type, ctx) ->
                            LocalDateTime.parse(json.getAsString(), ISO))
            .create();

    private JsonSupport() {
    }

    public static Gson gson() {
        return GSON;
    }
}
