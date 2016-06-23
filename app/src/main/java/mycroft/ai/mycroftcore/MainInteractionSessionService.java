package mycroft.ai.mycroftcore;

/**
 * Created by paul on 2016/06/22.
 */

import android.os.Bundle;
import android.service.voice.VoiceInteractionSession;
import android.service.voice.VoiceInteractionSessionService;

public class MainInteractionSessionService extends VoiceInteractionSessionService {
    @Override
    public VoiceInteractionSession onNewSession(Bundle args) {
        return new MainInteractionSession(this);
    }
}
