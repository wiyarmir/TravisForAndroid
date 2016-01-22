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

package es.guillermoorellana.travisforandroid.api.entity;

import com.google.gson.Gson;

import org.junit.Test;
import org.junit.runner.RunWith;

import es.guillermoorellana.travisforandroid.TravisDroidRobolectricTestRunner;
import es.guillermoorellana.travisforandroid.api.Fixtures;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(TravisDroidRobolectricTestRunner.class)
public class ApiBuildHistoryTest {

    @Test
    public void fromJson() {
        Gson gson = TravisDroidRobolectricTestRunner.travisApp().applicationComponent().gson();
        ApiBuildHistory build = gson.fromJson(Fixtures.FULL_BUILD_HISTORY_JSON, ApiBuildHistory.class);
        assertThat(build.getBuilds())
                .isNotNull()
                .isNotEmpty()
                .hasSize(3);
        assertThat(build.getCommits())
                .isNotNull()
                .isNotEmpty()
                .hasSize(3);
    }
}
