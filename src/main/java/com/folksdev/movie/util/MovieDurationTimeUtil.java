package com.folksdev.movie.util;

public class MovieDurationTimeUtil {

    public static String getMovieDurationString(int duration) {
        return (duration / 60) + "h " + (duration % 60) + "m";
    }
}
