/*
 * Copyright 2015 Guillermo Orellana Ruiz
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

package es.guillermoorellana.travisforandroid.api;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

public final class ItemTypeAdapterFactory implements TypeAdapterFactory {

    public <T> TypeAdapter<T> create(Gson gson, final TypeToken<T> type) {

        final TypeAdapter<T> delegate = gson.getDelegateAdapter(this, type);
        final TypeAdapter<JsonElement> elementAdapter = gson.getAdapter(JsonElement.class);

        return new ItemTypeAdapter<>(delegate, elementAdapter).nullSafe();
    }

    private static class ItemTypeAdapter<T> extends TypeAdapter<T> {

        private final TypeAdapter<T> delegate;
        private final TypeAdapter<JsonElement> elementAdapter;

        public ItemTypeAdapter(TypeAdapter<T> delegate, TypeAdapter<JsonElement> elementAdapter) {
            this.delegate = delegate;
            this.elementAdapter = elementAdapter;
        }

        public void write(JsonWriter out, T value) throws IOException {
            delegate.write(out, value);
        }

        public T read(JsonReader in) throws IOException {

            JsonElement jsonElement = elementAdapter.read(in);
            if (jsonElement.isJsonObject()) {
                JsonObject jsonObject = jsonElement.getAsJsonObject();
                //  repositories
                if (jsonObject.has("repos") && jsonObject.get("repos").isJsonArray()) {
                    jsonElement = jsonObject.get("repos");
                }

                // single repo
                if (jsonObject.has("repo") && jsonObject.get("repo").isJsonObject()) {
                    jsonElement = jsonObject.get("repo");
                }

                // fetch user
                if (jsonObject.has("user") && jsonObject.get("user").isJsonObject()) {
                    jsonElement = jsonObject.get("user");
                }

                // NOTE Add more objects here, if needed
            }

            return delegate.fromJsonTree(jsonElement);
        }
    }
}