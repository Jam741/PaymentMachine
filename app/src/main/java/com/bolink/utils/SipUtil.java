package com.bolink.utils;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.sip.SipAudioCall;
import android.net.sip.SipException;
import android.net.sip.SipManager;
import android.net.sip.SipProfile;
import android.net.sip.SipRegistrationListener;
import android.util.Log;

import com.bolink.R;
import com.bolink.bean.Messages;
import com.bolink.receiver.IncomingCallReceiver;
import com.bolink.rx.RxBus;

import java.text.ParseException;

/**
 * Created by xulu on 2017/9/13.
 * 该方法是用于安卓自带的sip
 * 优点是集成简单
 * 缺点是有杂音，通话效果不理想
 * 实际使用linesip
 */

public class SipUtil {
    Context context;

    public SipUtil(Context context) {
        this.context = context;
        initializeManager();
    }

    public String sipAddress = null;
    public SipManager manager = null;
    public SipProfile me = null;
    public SipAudioCall call = null;
    public IncomingCallReceiver callReceiver;

    public void initializeManager() {
        if(manager == null) {
            manager = SipManager.newInstance(context);
        }

        initializeLocalProfile();
    }

    /**
     * Logs you into your SIP provider, registering this device as the location to
     * send SIP calls to for your SIP address.
     */
    private static final String TAG = "WalkieTalkieActivity";
    public void initializeLocalProfile() {
        if (manager == null) {
            return;
        }

        if (me != null) {
            closeLocalProfile();
        }

        String username = context.getResources().getString(R.string.sip_account);
        String domain = context.getResources().getString(R.string.sip_domain);
        String password = context.getResources().getString(R.string.sip_password);

        try {
            SipProfile.Builder builder = new SipProfile.Builder(username, domain);
            builder.setPassword(password);
            builder.setProtocol("UDP");
            me = builder.build();

            Intent i = new Intent();
            i.setAction("android.SipDemo.INCOMING_CALL");
            PendingIntent pi = PendingIntent.getBroadcast(context, 0, i, Intent.FILL_IN_DATA);
            manager.open(me, pi, null);


            // This listener must be added AFTER manager.open is called,
            // Otherwise the methods aren't guaranteed to fire.

            manager.setRegistrationListener(me.getUriString(), new SipRegistrationListener() {
                public void onRegistering(String localProfileUri) {
                    updateStatus("Registering with SIP Server...");
                }

                public void onRegistrationDone(String localProfileUri, long expiryTime) {
                    updateStatus("Ready");
                }

                public void onRegistrationFailed(String localProfileUri, int errorCode,
                                                 String errorMessage) {
                    updateStatus("Registration failed.  Please check settings.");
                }
            });

        } catch (ParseException pe) {
            updateStatus("Connection Error.");
        } catch (SipException se) {
            updateStatus("Connection error.");
        }
    }

    /**
     * Closes out your local profile, freeing associated objects into memory
     * and unregistering your device from the server.
     */
    public void closeLocalProfile() {
        if (manager == null) {
            return;
        }
        try {
            if (me != null) {
                manager.close(me.getUriString());
            }
        } catch (Exception ee) {
            Log.d("WalkieTalkieActivity", "Failed to close local profile.", ee);
        }
    }

    /**
     * Make an outgoing call.
     */
    public void initiateCall() {

        updateStatus(sipAddress);

        try {
            SipAudioCall.Listener listener = new SipAudioCall.Listener() {
                // Much of the client's interaction with the SIP Stack will
                // happen via listeners.  Even making an outgoing call, don't
                // forget to set up a listener to set things up once the call is established.
                @Override
                public void onCallEstablished(SipAudioCall call) {
                    //播出电话对方接通时调用
                    call.startAudio();
                    call.setSpeakerMode(true);
                    if(call.isMuted()){
                        call.toggleMute();
                    }
                    updateStatus(call);
                    RxBus.get().post(new Messages(Messages.CALL_ESTABLISHED,null));
                }

                @Override
                public void onCallEnded(SipAudioCall call) {
                    //对方挂断时调用。主动挂断不调用
                    updateStatus("Ready.");
                    call.close();
                    RxBus.get().post(new Messages(Messages.CALL_ENDED,null));
                }

                @Override
                public void onError(SipAudioCall call, int errorCode, String errorMessage) {
                    //无人应答接听时调用
                    super.onError(call, errorCode, errorMessage);
                    if(call != null) {
                        try {
                            call.endCall();
                        } catch (SipException se) {
                            Log.d("WalkieTalkieActivity",
                                    "Error ending call.", se);
                        }
                        call.close();
                    }
                }

            };
            call = manager.makeAudioCall(me.getUriString(), sipAddress, listener, 30);
        } catch (Exception e) {
            Log.i("WalkieTalkieActivity", "Error when trying to close manager.", e);
            if (me != null) {
                try {
                    manager.close(me.getUriString());
                } catch (Exception ee) {
                    Log.i("WalkieTalkieActivity",
                            "Error when trying to close manager.", ee);
                    ee.printStackTrace();
                }
            }
            if (call != null) {
                call.close();
            }
        }
    }

    /**
     * Updates the status box at the top of the UI with a messege of your choice.
     * @param status The String to display in the status box.
     */
    public void updateStatus(final String status) {
        // Be a good citizen.  Make sure UI changes fire on the UI thread.
//        this.runOnUiThread(new Runnable() {
//            public void run() {
//                TextView labelView = (TextView) findViewById(R.id.sipLabel);
//                labelView.setText(status);
//            }
//        });
//        Toast.makeText(context,status,Toast.LENGTH_LONG).show();
    }

    /**
     * Updates the status box with the SIP address of the current call.
     * @param call The current, active call.
     */
    public void updateStatus(SipAudioCall call) {
        String useName = call.getPeerProfile().getDisplayName();
//        call.getPeerProfile().get
        if(useName == null) {
            useName = call.getPeerProfile().getUserName();
        }
        updateStatus(useName + "@" + call.getPeerProfile().getSipDomain());
    }

    public void destroy(){
        if(call != null) {
            try {
                call.endCall();
            } catch (SipException se) {
                Log.d("WalkieTalkieActivity",
                        "Error ending call.", se);
            }
            call.close();
        }
        closeLocalProfile();
        if (callReceiver != null) {
            context.unregisterReceiver(callReceiver);
        }
    }
}
