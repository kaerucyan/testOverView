package nishimura.com.sample.testoverview;



import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.PixelFormat;

public class OverViewService extends Service {
	
	  
	private WindowManager wm;  
	 
	private View view;  
	  
	private WindowManager.LayoutParams params;
	
	private LayoutInflater layoutInflater; 
	private final static String TAG ="OverViewService";
	private boolean flag;
	
	private BroadcastReceiver mScreenOnListener = new BroadcastReceiver() {
    
		
		
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();  
			
			if (action.equals(Intent.ACTION_SCREEN_ON)) {  
				Log.i(TAG,"CATCH:ACTION_SCREEN_ON");
				wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);  
				 
				view = layoutInflater.inflate(R.layout.overlay, null);      
				params = new WindowManager.LayoutParams(  
				 WindowManager.LayoutParams.WRAP_CONTENT,  
				 WindowManager.LayoutParams.WRAP_CONTENT,  
				 WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY,    
				 WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH |  
				 WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL |  
				 WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,    
				 PixelFormat.TRANSLUCENT);      
				
				try{
					
					wm.addView(view, params);  
					flag=true;
				}catch(Exception e){
					
					Log.i(TAG,"ERROR"+e.getMessage());
					
				}

				
            } else if (action.equals(Intent.ACTION_USER_PRESENT)) {
            	//Lock逕ｻ髱｢繧偵せ繝ｫ繝ｼ縺吶ｋ繧医≧縺ｪ繧｢繝励Μ縺ｮ襍ｷ蜍輔�迥ｶ豕√ｂ諠ｳ螳壹☆繧九∋縺阪�
            	if(flag){
            		wm.removeView(view);  
            		flag=false;
            	}
            }
			
			
		}
	};
	
	public void onCreate(){
		layoutInflater = LayoutInflater.from(this); 
		// WindowManager繧貞叙蠕励＠縺ｦ縲』ml縺九ｉ邁｡邏�↑View繧堤函謌舌＠縺ｦ縲〃iew縺ｮ螻樊�繧よｺ門ｙ縺励※縺翫￥  
	
		
		
	}
	
	@Override
	public int onStartCommand(Intent intent,int flags,int startId){
		Log.i(TAG,"onStartCommand");
		super.onStartCommand(intent,flags, startId);
		Log.i(TAG,"onStartCommand.super");
        // ACTION_SCREEN_ON繧貞女縺大叙繧毅roadcastReceiver繧堤匳骭ｲ  
        IntentFilter filter = new IntentFilter();  
        
        filter.addAction(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_USER_PRESENT);
        
        registerReceiver(mScreenOnListener, filter);
        Log.i(TAG,"ReceiverRegist");
        return START_STICKY; 
		
	}
	
    public void onDestroy() {  
        // BroadcastReceiver繧堤匳骭ｲ隗｣髯､  
    	// 豸医☆譎ゅ�縺薙ｌ  
    	if(flag){
    		wm.removeView(view);  
    		flag=false;
    	}
    	unregisterReceiver(mScreenOnListener);  
  
        super.onDestroy();  
    }  
	
	@Override
	public IBinder onBind(Intent intent) {
		// TODO 閾ｪ蜍慕函謌舌＆繧後◆繝｡繧ｽ繝�ラ繝ｻ繧ｹ繧ｿ繝�
		return null;
	}
	
	public class OverViewServiceBinder extends Binder {
		OverViewService getService() {
			// 繧ｯ繝ｩ繧､繧｢繝ｳ繝医′public繝｡繧ｽ繝�ラ繧貞他縺ｳ蜃ｺ縺帙ｋ繧医≧縺ｫ縲∬�蛻��霄ｫ縺ｮ繧､繝ｳ繧ｹ繧ｿ繝ｳ繧ｹ繧定ｿ斐☆縲�
			return OverViewService.this;
		}
	}



}
