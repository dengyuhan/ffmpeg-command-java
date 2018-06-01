# For more information about using CMake with Android Studio, read the
# documentation: https://d.android.com/studio/projects/add-native-code.html

# Sets the minimum version of CMake required to build the native library.

cmake_minimum_required(VERSION 3.4.1)

# Creates and names a library, sets it as either STATIC
# or SHARED, and provides the relative paths to its source code.
# You can define multiple libraries, and CMake builds them for you.
# Gradle automatically packages shared libraries with your APK.

add_library( # Sets the name of the library.
             ffmpeg-jni

             # Sets the library as a shared library.
             SHARED

             # Provides a relative path to your source file(s).
             src/main/cpp/com_dyhdyh_ffmpeg_FFmpegJNI.c
             src/main/cpp/cmdutils.c
             src/main/cpp/ffmpeg.c
             src/main/cpp/ffmpeg_filter.c
             src/main/cpp/ffmpeg_opt.c
             )
add_library(
            ffmpeg
            SHARED
            IMPORTED
            )

#if(${ANDROID_ABI} STREQUAL "armeabi-v7a")
set_target_properties(
                ffmpeg
                PROPERTIES IMPORTED_LOCATION
                ${CMAKE_SOURCE_DIR}/src/main/jniLibs/armeabi-v7a/libffmpeg.so
                )

include_directories(
    #${PROJECT_SOURCE_DIR}/ffmpeg-3.3.7
    /Users/dengyuhan/Downloads/ffmpeg-3.3.7

)

add_definitions(-Wall)
add_definitions(-Wextra)
# Searches for a specified prebuilt library and stores the path as a
# variable. Because CMake includes system libraries in the search path by
# default, you only need to specify the name of the public NDK library
# you want to add. CMake verifies that the library exists before
# completing its build.


# Specifies libraries CMake should link to your target library. You
# can link multiple libraries, such as libraries you define in this
# build script, prebuilt third-party libraries, or system libraries.

target_link_libraries( # Specifies the target library.
                       ffmpeg-jni
                       ffmpeg
)
add_definitions(-Wall)