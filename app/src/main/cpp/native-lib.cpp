#include <jni.h>
#include <string>
#include "pthread.h"
#include "AndroidLog.h"

/*
pthread_t thread;

void *normalCallBack(void *data){
    LOGD("creat thead");
    pthread_exit(&thread);
}

extern "C" JNIEXPORT jstring JNICALL
Java_com_content_demo_MainActivity_stringFromJNI(
        JNIEnv *env,
        jobject */
/* this *//*
) {
    LOGD("print log");

    pthread_create(&thread,NULL,normalCallBack,NULL);
    std::string hello = "wechatwebserver/";
    return env->NewStringUTF(hello.c_str());
}
*/

#include <SLES/OpenSLES.h>
#include <SLES/OpenSLES_Android.h>

SLObjectItf  engineObject = NULL;
SLEngineItf  engineEngine = NULL;

SLObjectItf  outputMixObject = NULL;
SLEnvironmentalReverbItf  outputMixEnvironmenttalReverb = NULL;
SLEnvironmentalReverbSettings reverbSettings = SL_I3DL2_ENVIRONMENT_PRESET_STONECORRIDOR;

SLObjectItf pcmPlayerObject = NULL;
SLPlayItf  playItf = NULL;
SLAndroidSimpleBufferQueueItf  pcmBufferQueue = NULL;

FILE *pcmFile;
void *buffer;
u_int8_t *out_buffer;


int getPcmData (void **pcm){

    int size = 0;
    while (!feof(pcmFile)){
        LOGD("path3");

        size =  fread(out_buffer,1,44100*2*2,pcmFile);
        if(out_buffer==NULL){

            LOGD("%s","read end");
            break;
        }else{
            LOGD("%s","reading");
        }
        *pcm = out_buffer;
        break;
    }
    return size;
}

void pcmBufferCallBack(SLAndroidSimpleBufferQueueItf bf, void *context){

    int size = getPcmData(&buffer);
    if(buffer!=NULL){
        (*pcmBufferQueue)->Enqueue(pcmBufferQueue,buffer,size);
    }
}

const char *filename = "1.pcm";
extern "C"
JNIEXPORT void JNICALL
Java_com_content_demo_MainActivity_playPcm(JNIEnv *env, jobject instance, jstring path_) {
    const char *path = env->GetStringUTFChars(path_, 0);

    LOGD("path %s",path);
    pcmFile = fopen(path,"rb");
    if(pcmFile==NULL){
        LOGD("path1");
        return;
    }
    LOGD("path2");
    out_buffer = (uint8_t*)malloc(44100*2*2);


    //创建接口
    slCreateEngine(&engineObject,0,0,0,0,0);
    (*engineObject)->Realize(engineObject,SL_BOOLEAN_FALSE);
    //得到引擎对象
    (*engineObject)->GetInterface(engineObject,SL_IID_ENGINE,&engineEngine);

    const SLInterfaceID mids[1] = {SL_IID_ENVIRONMENTALREVERB};
    const SLboolean mreq[1] = {SL_BOOLEAN_FALSE};

    (*engineEngine)->CreateOutputMix(engineEngine,&outputMixObject,1,mids,mreq);
    //初始化
    (*outputMixObject)->Realize(outputMixObject,SL_BOOLEAN_FALSE);
    (*outputMixObject)->GetInterface(outputMixObject,SL_IID_ENVIRONMENTALREVERB,&outputMixEnvironmenttalReverb);

    //设置属性
    (*outputMixEnvironmenttalReverb)->SetEnvironmentalReverbProperties(outputMixEnvironmenttalReverb,&reverbSettings);


    //-------播放器
    SLDataLocator_AndroidBufferQueue androidBufferQueue = {SL_DATALOCATOR_ANDROIDSIMPLEBUFFERQUEUE,2};
    SLDataFormat_PCM pcm = {
            SL_DATAFORMAT_PCM,
            2,
            SL_SAMPLINGRATE_44_1,
            SL_PCMSAMPLEFORMAT_FIXED_16,
            SL_PCMSAMPLEFORMAT_FIXED_16,
            SL_SPEAKER_FRONT_LEFT|SL_SPEAKER_FRONT_RIGHT,
            SL_BYTEORDER_LITTLEENDIAN
    };
    SLDataSource slDataSource = {&androidBufferQueue,&pcm};

    //混音器
    SLDataLocator_OutputMix outputMix = {SL_DATALOCATOR_OUTPUTMIX,outputMixObject};
    SLDataSink dataSink = {&outputMix,NULL};


    const  SLInterfaceID  ids[1] = {SL_IID_BUFFERQUEUE};
    const  SLboolean  req[1] = {SL_BOOLEAN_TRUE};
    (*engineEngine)->CreateAudioPlayer(engineEngine,&pcmPlayerObject,&slDataSource,&dataSink,1,ids,req);



    (*pcmPlayerObject)->Realize(pcmPlayerObject,SL_BOOLEAN_FALSE);
    (*pcmPlayerObject)->GetInterface(pcmPlayerObject,SL_IID_PLAY,&playItf);


    //缓冲
    (*pcmPlayerObject)->GetInterface(pcmPlayerObject,SL_IID_BUFFERQUEUE,&pcmBufferQueue);
    (*pcmBufferQueue)->RegisterCallback(pcmBufferQueue,pcmBufferCallBack,NULL);

    //播放状态
    (*playItf)->SetPlayState(playItf,SL_PLAYSTATE_PLAYING);
    pcmBufferCallBack(pcmBufferQueue,NULL);

    env->ReleaseStringUTFChars(path_, path);
}