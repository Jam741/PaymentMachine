package com.vguang.vbarlib;

import android.app.Activity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.bolink.R;
import com.bolink.utils.CommontUtils;
import com.sun.jna.ptr.IntByReference;
import com.vguang.Vbar;

public class MainActivity extends Activity {

	private TextView decodeStr;
	private Button opendev, beep1, lignton,lightoff,beep2,closedev,decodestart;



	boolean state = false;

	boolean devicestate = false;
	IntByReference device;

    Vbar b = new Vbar();



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main2);




		decodeStr = (TextView) findViewById(R.id.decodeStr);
		decodeStr.setMovementMethod(ScrollingMovementMethod.getInstance());


		beep1 = (Button) findViewById(R.id.beep1);
		lignton = (Button) findViewById(R.id.lighton);
		lignton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				b.vbarLight(true);

			}
		});

		beep2 = (Button) findViewById(R.id.beep2);
		beep2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				b.vbarBeep(2);

			}
		});

		closedev = (Button) findViewById(R.id.closedev);
		closedev.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				b.closeDev();
				System.exit(0);

			}
		});
		lightoff = (Button) findViewById(R.id.lightoff);
		lightoff.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				b.vbarLight(false);

			}
		});

		opendev = (Button) findViewById(R.id.openDev);
		opendev.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				state = b.vbarOpen();
				if (state) {
					Log.v("######################", "open success");
				} else {
					Log.v("#####################", "open fail");
				}


			}
		});

		beep1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				b.vbarBeep(1);

			}
		});

		decodestart = (Button) findViewById(R.id.begindecode);
		decodestart.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Thread t = new Thread(){
					@Override
					public void run() {
						// TODO Auto-generated method stub
						super.run();
//						CommontUtils.writeSDFile("scan start:","==============");
						while(true)
						{
							final String str = b.getResultsingle();
							if(str != null)
							{
								runOnUiThread(new Runnable() {
									public void run() {

										{
//											CommontUtils.writeSDFile("scan result:","==>"+str);
											decodeStr.setText(str + "1234567890\r\n");

										}
									}
								});

							}

							try {
								Thread.sleep(100);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

						}
					}
				};
				t.start();
				beep1.setEnabled(false);
				lignton.setEnabled(false);
				lightoff.setEnabled(false);
				beep2.setEnabled(false);

			}
		});
	}



	public void refreshAlarmView(TextView textView,String msg){
		textView.setText(msg);
		int offset=textView.getLineCount()*textView.getLineHeight();
		if(offset>(textView.getHeight()-textView.getLineHeight()-20)){
			textView.scrollTo(0,offset-textView.getHeight()+textView.getLineHeight()+20);
		}
	}


	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}


	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
//		if (id == R.id.action_settings) {
//			return true;
//		}
		return super.onOptionsItemSelected(item);
	}
}
