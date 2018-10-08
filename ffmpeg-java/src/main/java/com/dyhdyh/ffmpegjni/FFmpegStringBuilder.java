package com.dyhdyh.ffmpegjni;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author dengyuhan
 *         created 2018/10/8 10:23
 */
public class FFmpegStringBuilder {
    private final String BEGINNING = "ffmpeg";
    private final String REPLACE_FILE = "-y";

    private List<String> builds;

    public FFmpegStringBuilder() {
        builds = new ArrayList<>();
        builds.add(BEGINNING);
        builds.add(REPLACE_FILE);
    }

    public FFmpegStringBuilder append(CharSequence s) {
        builds.add(s.toString());
        return this;
    }

    public FFmpegStringBuilder append(File file) {
        return append(file.getAbsolutePath());
    }

    public FFmpegStringBuilder clear() {
        builds.clear();
        return this;
    }

    public List<String> toList() {
        return builds;
    }

    public String[] toArray() {
        return builds.toArray(new String[0]);
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        for (CharSequence s : builds) {
            sb.append(s);
            sb.append(" ");
        }
        return sb.toString();
    }
}
