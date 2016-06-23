package mycroft.ai.mycroftcore;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class VoiceInteractionMain extends Activity {

    View.OnClickListener mStartListener = new View.OnClickListener() {
        public void onClick(View v) {
            startService(new Intent(VoiceInteractionMain.this, MainInteractionService.class));
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        findViewById(R.id.start).setOnClickListener(mStartListener);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
