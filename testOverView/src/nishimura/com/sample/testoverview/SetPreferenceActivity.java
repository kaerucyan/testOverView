package nishimura.com.sample.testoverview;

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
	/** 陦ｨ遉ｺ繧堤ｮ｡逅�＠縺ｦ縺�ｋService縲�@link ServiceConnection}縺ｫ繧医▲縺ｦ繧ｵ繝ｼ繝薙せ蜀��public繝｡繧ｽ繝�ラ繧偵＃繧翫＃繧雁他縺ｳ蜃ｺ縺�*/
	private OverViewService overViewService;
	
	/** 縺�∪{@link Service}縺ｯbind縺輔ｌ縺ｦ繧具ｼ溘▲縺ｦ縺�≧繝輔Λ繧ｰ縲Ｕrue縺ｪ繧峨＆繧後※繧九� */
	private boolean mBound = false;
	
	  @Override  
	    public void onCreate(Bundle savedInstanceState) {  
	        super.onCreate(savedInstanceState);  
	        setContentView(R.layout.activity_screen_lock_enabled_ui);  
	  
	        // 繝√ぉ繝�け繝懊ャ繧ｯ繧ｹ縺ｮ蛟､繧剃ｿ晏ｭ倥☆繧九◆繧√↓菴ｿ逕ｨ  
	        final SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);  
	        final Intent intent = new Intent(SetPreferenceActivity.this, OverViewService.class);  
	  
	        CheckBox chbEnable = (CheckBox) findViewById(R.id.checkBox1);  
	        chbEnable.setChecked(pref.getBoolean("is_lockEnable", false));  
	        chbEnable.setOnCheckedChangeListener(new OnCheckedChangeListener() {  
	  
	            @Override  
	            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {  
	                // 繝√ぉ繝�け縺輔ｌ縺溘ｉ繧ｵ繝ｼ繝薙せ繧定ｵｷ蜍� 
	            	Log.i(TAG,"onCheckedChaged Start");
	                if (isChecked) {  
	                	Log.i(TAG,"onCheckedChaged isChecked True");
	                    pref.edit().putBoolean("is_lockEnable", isChecked).commit(); 
	                    try{
	                    	startService(intent);
	                    	
	                    }catch(Exception e){
	                    	
	                    	Log.e(TAG,e.getMessage());
	                    	
	                    }
	                // 繝√ぉ繝�け縺悟､悶＆繧後◆繧峨し繝ｼ繝薙せ繧貞●豁｢  
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
			 * @param className 謗･邯壹＆繧後◆Service縺ｮ蜷榊燕縺ｨ縺九◎縺薙ｉ縲�
			 * @param service 謗･邯壹＆繧後◆Service縺ｮ{@link Binder}縲ゅさ繝ｬ繧剃ｽｿ縺｣縺ｦ繧ｵ繝ｼ繝薙せ縺ｮ繧､繝ｳ繧ｹ繧ｿ繝ｳ繧ｹ繧偵�縺｣縺ｱ縺｣縺ｦ縺阪※縺�§縺上ｋ
			 
			@Override
			public void onServiceConnected(ComponentName className, IBinder service) {
				// We've bound to LocalService, cast the IBinder and get LocalService instance
				OverViewServiceBinder binder = (OverViewServiceBinder)service;
				overViewService = binder.getService();
				mBound = true;
			}

			/**
			 * Service縺九ｉ蛻�ｊ髮｢縺輔ｌ縺滓凾縺ｫ蜻ｼ縺ｰ繧後ｋ縲ゅ◆縺�＠縲∵ｭ｣蟶ｸ縺ｫ{@link Setting#unbindService(ServiceConnection)}縺輔ｌ縺溷�蜷医�蜻ｼ縺ｰ繧後↑縺��<br>
			 * 縺薙ｌ縺悟他縺ｰ繧後ｋ縺ｮ縺ｯ繧ｯ繝ｩ繝�す繝･縺励◆繧雁ｼｷ蛻ｶ邨ゆｺ�＆繧後◆蝣ｴ蜷医�
			
			@Override
			public void onServiceDisconnected(ComponentName className) {
				mBound = false;
			}


		};
	*/
		
}
