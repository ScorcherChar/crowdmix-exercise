package com.squiressoftware.crowdmix.time;


import org.joda.time.DateTime;

public class ClockImpl implements Clock{
    @Override
    public DateTime getNow() {
        return new DateTime();
    }
}
