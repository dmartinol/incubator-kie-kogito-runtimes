/*
 * Copyright 2016 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.kie.dmn.feel.lang.runtime.functions;

import java.time.Duration;
import java.time.LocalTime;
import java.time.OffsetTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalAmount;

public class DurationFunction
        extends BaseFEELFunction {

    public TemporalAmount apply(String val) {
        if ( val != null ) {
            try {
                // try to parse as days/hours/minute/seconds
                return Duration.parse( val );
            } catch( DateTimeParseException e ) {
                // if it failed, try to parse as years/months
                try {
                    return Period.parse( val );
                } catch( DateTimeParseException e2 ) {
                    // failed to parse, so return null according to the spec
                    return null;
                }
            }
        }
        return null;
    }

    @Override
    public String getName() {
        return "duration";
    }
}
