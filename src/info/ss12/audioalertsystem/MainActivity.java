package info.ss12.audioalertsystem;

import info.ss12.audioalertsystem.notification.CameraLightNotification;
import info.ss12.audioalertsystem.notification.FlashNotification;
import info.ss12.audioalertsystem.notification.NotificationBarNotification;
import info.ss12.audioalertsystem.notification.VibrateNotification;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.widget.Button;
import android.widget.Switch;

public class MainActivity extends Activity
{
	private final String TAG = "Main Activity";
	private boolean alarmActivated = false;

	
	private Switch micSwitch;
	private Button testAlert;
	
	private ButtonController buttonControl;
	
	private VibrateNotification vibrate;
	private FlashNotification flash;
	private NotificationBarNotification bar;
	private CameraLightNotification cameraLight;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		buttonControl = new ButtonController(this);
		micSwitch = (Switch )findViewById(R.id.mic_switch);
		micSwitch.setOnClickListener(buttonControl);
		micSwitch.setOnTouchListener(buttonControl);

		testAlert = (Button)findViewById(R.id.test_alert);
		testAlert.setOnClickListener(buttonControl);
		
		vibrate = new VibrateNotification(this);
		flash = new FlashNotification(this);
		bar = new NotificationBarNotification();
		cameraLight = new CameraLightNotification(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	
	private Handler handler = new Handler()
	{

		@Override
		public void handleMessage(Message msg) 
		{
			
			if(msg.arg1 == 1 && !alarmActivated) // Turn On
			{
				bar.startNotify();
				flash.startNotify();
				vibrate.startNotify();
				cameraLight.startNotify();
				alarmActivated = true;
			}
			else if(msg.arg1 == 0 && alarmActivated)
			{
				bar.stopNotify();
				flash.stopNotify();
				vibrate.stopNotify();
				cameraLight.stopNotify();
				alarmActivated = false;
			}
			Log.d(TAG, "FIRE ALARM DETECTED");	
		}
		
	};

	public Handler getHandler() 
	{
		return handler;
	}
	
	public void setHandler(Handler handler) 
	{
		this.handler = handler;
	}

}
