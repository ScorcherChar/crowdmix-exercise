package com.squiressoftware.crowdmix.posts;

import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class Post {

    private String message;
    private DateTime postedTime;

    public Post(String message, DateTime postedTime) {
        this.message = message;
        this.postedTime = postedTime;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setPostedTime(DateTime postedTime) {
        this.postedTime = postedTime;
    }

    public String print(DateTime now) {
        Period period = new Period(getPostedTime(), now);

        PeriodFormatter formatter = new PeriodFormatterBuilder()
                .appendSeconds().appendSuffix(" seconds ago")
                .appendMinutes().appendSuffix(" minutes ago")
                .appendHours().appendSuffix(" hours ago")
                .appendDays().appendSuffix(" days ago")
                .appendWeeks().appendSuffix(" weeks ago")
                .appendMonths().appendSuffix(" months ago")
                .appendYears().appendSuffix(" years ago")
                .printZeroNever()
                .toFormatter();

        return String.format("%s (%s)", getMessage(), formatter.print(period));
    }

    public DateTime getPostedTime() {
        return postedTime;
    }
}
