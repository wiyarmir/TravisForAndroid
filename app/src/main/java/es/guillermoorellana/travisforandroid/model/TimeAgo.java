/*
 *   Copyright 2016 Guillermo Orellana Ruiz
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */

package es.guillermoorellana.travisforandroid.model;

import android.content.res.Resources;

import java.util.Date;

import es.guillermoorellana.travisforandroid.R;

public class TimeAgo {

    private final Resources resources;

    public TimeAgo(Resources resources) {
        this.resources = resources;
    }

    /**
     * Get time ago that date occurred
     *
     * @param date
     * @return time string
     */
    public String timeAgo(final Date date) {
        return timeAgo(date.getTime());
    }

    /**
     * Get time ago that milliseconds date occurred
     *
     * @param millis
     * @return time string
     */
    public String timeAgo(final long millis) {
        return time(System.currentTimeMillis() - millis);
    }

    /**
     * Get time string for milliseconds distance
     *
     * @param distanceMillis
     * @return time string
     */

    public String time(Long distanceMillis) {

        final double seconds = distanceMillis / 1000d;
        final double minutes = seconds / 60d;
        final double hours = minutes / 60d;
        final double days = hours / 24d;
        final double years = days / 365d;

        final String time;
        if (seconds < 45) {
            time = resources.getString(R.string.time_seconds);
        } else if (seconds < 90 || minutes < 45) {
            time = resources.getQuantityString(R.plurals.time_minute, minutes < 2 ? 1 : 2, Math.round(minutes));
        } else if (minutes < 90 || hours < 24) {
            time = resources.getQuantityString(R.plurals.time_hour, hours < 2 ? 1 : 2, Math.round(hours));
        } else if (hours < 48 || days < 30) {
            time = resources.getQuantityString(R.plurals.time_day, days < 2 ? 1 : 2, Math.round(days));
        } else if (days < 60 || days < 365) {
            time = resources.getQuantityString(R.plurals.time_month, (days / 30) < 2 ? 1 : 2, Math.round(days / 30));
        } else {
            time = resources.getQuantityString(R.plurals.time_year, years < 2 ? 1 : 2, Math.round(years));
        }

        return time + " " + resources.getString(R.string.time_ago);

    }

}

