//
// Created by stronguser on 2020/7/13.
//

#ifndef CONTENT_ANDROIDLOG_H
#define CONTENT_ANDROIDLOG_H

#endif //CONTENT_ANDROIDLOG_H
#include <android/log.h>
#define LOGD(FORMAT,...)__android_log_print(ANDROID_LOG_DEBUG,"wwn",FORMAT,##__VA_ARGS__)
