/*
 * Copyright (C) 2010 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.bolink.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.sip.SipAudioCall;
import android.net.sip.SipProfile;
import android.net.sip.SipSession;

import com.bolink.MainActivity;
import com.bolink.bean.Messages;
import com.bolink.rx.RxBus;

/**
 * Listens for incoming SIP calls, intercepts and hands them off to MainActivity.
 * xulu
 * 监听sip来电
 */
public class IncomingCallReceiver extends BroadcastReceiver {
    public SipSession session = null;
    /**
     * Processes the incoming call, answers it, and hands it over to the
     * MainActivity.
     * @param context The context under which the receiver is running.
     * @param intent The intent being received.
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        SipAudioCall incomingCall = null;
        try {

            SipAudioCall.Listener listener = new SipAudioCall.Listener() {
                @Override
                public void onRinging(SipAudioCall call, SipProfile caller) {
                    try {
                        call.answerCall(30);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };

            MainActivity wtActivity = (MainActivity) context;

            incomingCall = wtActivity.sipUtil.manager.takeAudioCall(intent, listener);

            incomingCall.answerCall(30);
            incomingCall.startAudio();
            incomingCall.setSpeakerMode(true);
            if(incomingCall.isMuted()) {
                incomingCall.toggleMute();
            }
            wtActivity.sipUtil.call = incomingCall;
            wtActivity.sipUtil.updateStatus(incomingCall);
            RxBus.get().post(new Messages(Messages.CALL_RECEIVED,null));
            session = wtActivity.sipUtil.manager.getSessionFor(intent);
            session.setListener(new SipSession.Listener(){
                @Override
                public void onCallEnded(SipSession session) {
                    //接到的电话，主动或被挂断后调动
                    super.onCallEnded(session);
                    RxBus.get().post(new Messages(Messages.CALL_ENDED,null));
                }
            });
        } catch (Exception e) {
            if (incomingCall != null) {
                incomingCall.close();
            }
        }
    }

}
