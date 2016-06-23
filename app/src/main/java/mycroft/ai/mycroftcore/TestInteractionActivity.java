package mycroft.ai.mycroftcore;

/**
 * Created by paul on 2016/06/22.
 */

import android.app.Activity;
import android.app.VoiceInteractor;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class TestInteractionActivity extends Activity implements View.OnClickListener {

    static final String TAG = "TestInteractionActivity";
    VoiceInteractor mInteractor;
    Button mAbortButton;
    Button mCompleteButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!isVoiceInteraction()) {
            Log.w(TAG, "Not running as a voice interaction!");
            finish();
            return;
        }
        setContentView(R.layout.test_interaction);
        mAbortButton = (Button) findViewById(R.id.abort);
        mAbortButton.setOnClickListener(this);
        mCompleteButton = (Button) findViewById(R.id.complete);
        mCompleteButton.setOnClickListener(this);
        // Framework should take care of these.
        getWindow().setGravity(Gravity.TOP);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        mInteractor = getVoiceInteractor();
        Bundle status = new Bundle();
        VoiceInteractor.Prompt prompt = new VoiceInteractor.Prompt("this is a confirmation");
        VoiceInteractor.ConfirmationRequest req = new VoiceInteractor.ConfirmationRequest(prompt, null) {
            @Override
            public void onCancel() {
                Log.i(TAG, "Canceled!");
                getActivity().finish();
            }

            @Override
            public void onConfirmationResult(boolean confirmed, Bundle result) {
                Log.i(TAG, "Confirmation result: confirmed=" + confirmed + " result=" + result);
                getActivity().finish();
            }
        };
        mInteractor.submitRequest(req);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View v) {
        if (v == mAbortButton) {
            VoiceInteractor.Prompt prompt2 = new VoiceInteractor.Prompt("Dammit, we suck :(");
            VoiceInteractor.AbortVoiceRequest req = new VoiceInteractor.AbortVoiceRequest(
                    prompt2, null) {
                @Override
                public void onCancel() {
                    Log.i(TAG, "Canceled!");
                }

                @Override
                public void onAbortResult(Bundle result) {
                    Log.i(TAG, "Abort result: result=" + result);
                    getActivity().finish();
                }
            };
            mInteractor.submitRequest(req);
        } else if (v == mCompleteButton) {
            VoiceInteractor.Prompt prompt3 = new VoiceInteractor.Prompt("Woohoo, completed!");
            VoiceInteractor.CompleteVoiceRequest req = new VoiceInteractor.CompleteVoiceRequest(
                    prompt3, null) {
                @Override
                public void onCancel() {
                    Log.i(TAG, "Canceled!");
                }

                @Override
                public void onCompleteResult(Bundle result) {
                    Log.i(TAG, "Complete result: result=" + result);
                    getActivity().finish();
                }
            };
            mInteractor.submitRequest(req);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}