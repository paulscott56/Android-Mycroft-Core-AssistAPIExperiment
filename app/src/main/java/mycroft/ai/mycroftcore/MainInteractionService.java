package mycroft.ai.mycroftcore;

import android.content.Intent;
import android.os.Bundle;
import android.service.voice.AlwaysOnHotwordDetector;
import android.service.voice.AlwaysOnHotwordDetector.Callback;
import android.service.voice.AlwaysOnHotwordDetector.EventPayload;
import android.service.voice.VoiceInteractionService;
import android.util.Log;

import java.util.Arrays;
import java.util.Locale;

/**
 * Created by paul on 2016/06/22.
 */

public class MainInteractionService extends VoiceInteractionService {
    static final String TAG = "MainInteractionService";
    private AlwaysOnHotwordDetector mHotwordDetector;
    private final Callback mHotwordCallback = new Callback() {
        @Override
        public void onAvailabilityChanged(int status) {
            Log.i(TAG, "onAvailabilityChanged(" + status + ")");
            hotwordAvailabilityChangeHelper(status);
        }

        @Override
        public void onDetected(EventPayload eventPayload) {
            Log.i(TAG, "onDetected");
        }

        @Override
        public void onError() {
            Log.i(TAG, "onError");
        }

        @Override
        public void onRecognitionPaused() {
            Log.i(TAG, "onRecognitionPaused");
        }

        @Override
        public void onRecognitionResumed() {
            Log.i(TAG, "onRecognitionResumed");
        }
    };

    @Override
    public void onReady() {
        super.onReady();
        Log.i(TAG, "Creating " + this);
        Log.i(TAG, "Keyphrase enrollment error? " + getKeyphraseEnrollmentInfo().getParseError());
        Log.i(TAG, "Keyphrase enrollment meta-data: "
                + Arrays.toString(getKeyphraseEnrollmentInfo().listKeyphraseMetadata()));
        mHotwordDetector = createAlwaysOnHotwordDetector(
                "Hello There", Locale.forLanguageTag("en-US"), mHotwordCallback);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Bundle args = new Bundle();
        args.putParcelable("intent", new Intent(this, TestInteractionActivity.class));
        startSession(args);
        stopSelf(startId);
        return START_NOT_STICKY;
    }

    private void hotwordAvailabilityChangeHelper(int availability) {
        Log.i(TAG, "Hotword availability = " + availability);
        switch (availability) {
            case AlwaysOnHotwordDetector.STATE_HARDWARE_UNAVAILABLE:
                Log.i(TAG, "STATE_HARDWARE_UNAVAILABLE");
                break;
            case AlwaysOnHotwordDetector.STATE_KEYPHRASE_UNSUPPORTED:
                Log.i(TAG, "STATE_KEYPHRASE_UNSUPPORTED");
                break;
            case AlwaysOnHotwordDetector.STATE_KEYPHRASE_UNENROLLED:
                Log.i(TAG, "STATE_KEYPHRASE_UNENROLLED");
                Intent enroll = mHotwordDetector.createEnrollIntent();
                Log.i(TAG, "Need to enroll with " + enroll);
                break;
            case AlwaysOnHotwordDetector.STATE_KEYPHRASE_ENROLLED:
                Log.i(TAG, "STATE_KEYPHRASE_ENROLLED - starting recognition");
                if (mHotwordDetector.startRecognition(
                        AlwaysOnHotwordDetector.RECOGNITION_FLAG_NONE)) {
                    Log.i(TAG, "startRecognition succeeded");
                } else {
                    Log.i(TAG, "startRecognition failed");
                }
                break;
        }
    }
}