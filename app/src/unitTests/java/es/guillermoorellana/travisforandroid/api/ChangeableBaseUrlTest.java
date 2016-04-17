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

import okhttp3.HttpUrl;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ChangeableBaseUrlTest {

    @Test
    public void constructor_shouldUsePassedBaseUrl() {
        ChangeableBaseUrl changeableBaseUrl = new ChangeableBaseUrl("https://wiyarmir.es");
        assertThat(changeableBaseUrl.url()).isEqualTo(HttpUrl.parse("https://wiyarmir.es"));
    }

    @Test
    public void setBaseUrl() {
        ChangeableBaseUrl changeableBaseUrl = new ChangeableBaseUrl("https://1");
        changeableBaseUrl.setBaseUrl("https://2");
        assertThat(changeableBaseUrl.url()).isEqualTo(HttpUrl.parse("https://2"));
    }
}