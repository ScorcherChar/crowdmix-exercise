package com.squiressoftware.crowdmix.time;


import org.joda.time.DateTime;

public class RealClock implements Clock{
    @Override
    public DateTime getNow() {
        return new DateTime();
    }
}
