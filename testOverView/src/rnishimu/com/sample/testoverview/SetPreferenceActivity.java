package rnishimu.com.sample.testoverview;

import rnishimu.com.sample.testoverview.OverViewService.OverViewServiceBinder;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class SetPreferenceActivity extends Activity{
	
	private final static String TAG = "SetPreferenceActivity";
	/** 表示を管理しているService。{@link ServiceConnection}によってサービス内のpublicメソッドをごりごり呼び出す */
	private OverViewService overViewService;
	
	/** いま{@link Service}はbindされてる？っていうフラグ。trueならされてる。 */
	private boolean mBound = false;
	
	  @Override  
	    public void onCreate(Bundle savedInstanceState) {  
	        super.onCreate(savedInstanceState);  
	        setContentView(R.layout.activity_screen_lock_enabled_ui);  
	  
	        // チェックボックスの値を保存するために使用  
	        final SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);  
	        final Intent intent = new Intent(SetPreferenceActivity.this, OverViewService.class);  
	  
	        CheckBox chbEnable = (CheckBox) findViewById(R.id.checkBox1);  
	        chbEnable.setChecked(pref.getBoolean("is_lockEnable", false));  
	        chbEnable.setOnCheckedChangeListener(new OnCheckedChangeListener() {  
	  
	            @Override  
	            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {  
	                // チェックされたらサービスを起動  
	            	Log.i(TAG,"onCheckedChaged Start");
	                if (isChecked) {  
	                	Log.i(TAG,"onCheckedChaged isChecked True");
	                    pref.edit().putBoolean("is_lockEnable", isChecked).commit(); 
	                    try{
	                    	startService(intent);
	                    	
	                    }catch(Exception e){
	                    	
	                    	Log.e(TAG,e.getMessage());
	                    	
	                    }
	                // チェックが外されたらサービスを停止  
	                } else {  
	                	Log.i(TAG,"onCheckedChaged isChecked false");
	                    pref.edit().putBoolean("is_lockEnable", isChecked).commit();  
	                    try{
	                    	stopService(intent);  
	                	}catch(Exception e){
                    	
	                		Log.e(TAG,e.getMessage());
                    	
	                	}
	                }  
	            }  
	        });  
	  
	    }  
	  /*
		private ServiceConnection mConnection = new ServiceConnection() {
			/**
			 * @param className 接続されたServiceの名前とかそこら。
			 * @param service 接続されたServiceの{@link Binder}。コレを使ってサービスのインスタンスをひっぱってきていじくる
			 
			@Override
			public void onServiceConnected(ComponentName className, IBinder service) {
				// We've bound to LocalService, cast the IBinder and get LocalService instance
				OverViewServiceBinder binder = (OverViewServiceBinder)service;
				overViewService = binder.getService();
				mBound = true;
			}

			/**
			 * Serviceから切り離された時に呼ばれる。ただし、正常に{@link Setting#unbindService(ServiceConnection)}された場合は呼ばれない。<br>
			 * これが呼ばれるのはクラッシュしたり強制終了された場合。
			
			@Override
			public void onServiceDisconnected(ComponentName className) {
				mBound = false;
			}


		};
	*/
		
}
