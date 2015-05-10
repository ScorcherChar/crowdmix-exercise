package com.squiressoftware.crowdmix.time;

import org.joda.time.DateTime;

public interface Clock {
    DateTime getNow();
}
