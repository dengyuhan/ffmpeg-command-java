package com.dyhdyh.ffmpegjni.listener;

/**
 * @author dengyuhan
 *         created 2018/6/4 18:20
 */
public interface OnFFmpegLoggerListener {

    /**
     * Print no output.
     */
    int AV_LOG_QUIET = -8;

    /**
     * Something went really wrong and we will crash now.
     */
    int AV_LOG_PANIC = 0;

    /**
     * Something went wrong and recovery is not possible.
     * For example, no header was found for a format which depends
     * on headers or an illegal combination of parameters is used.
     */
    int AV_LOG_FATAL = 8;

    /**
     * Something went wrong and cannot losslessly be recovered.
     * However, not all future data is affected.
     */
    int AV_LOG_ERROR = 16;

    /**
     * Something somehow does not look correct. This may or may not
     * lead to problems. An example would be the use of '-vstrict -2'.
     */
    int AV_LOG_WARNING = 24;

    /**
     * Standard information.
     */
    int AV_LOG_INFO = 32;

    /**
     * Detailed information.
     */
    int AV_LOG_VERBOSE = 40;

    /**
     * Stuff which is only useful for libav* developers.
     */
    int AV_LOG_DEBUG = 48;

    /**
     * Extremely verbose debugging, useful for libav* development.
     */
    int AV_LOG_TRACE = 56;

    void onPrint(int level, byte[] messageByteArray);
}
