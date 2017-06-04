package baidumapsdk.demo;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import baidumapsdk.demo.LocationOverlayDemo.MyLocationListenner;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.map.LocationData;
import com.baidu.mapapi.map.MapController;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationOverlay;
import com.baidu.mapapi.map.MyLocationOverlay.LocationMode;
import com.baidu.mapapi.map.PopupClickListener;
import com.baidu.mapapi.map.PopupOverlay;
import com.baidu.platform.comapi.basestruct.GeoPoint;
/**
 * 此demo用来展示如何结合定位SDK实现定位，并使用MyLocationOverlay绘制定位位置
 * 同时展示如何使用自定义图标绘制并点击时弹出泡泡
 *
 */
public class LocationActivity extends Activity {
	private enum E_BUTTON_TYPE {
		LOC,
		COMPASS,
		FOLLOW
	}
	
	private E_BUTTON_TYPE mCurBtnType;

	// 定位相关
	LocationClient mLocClient;
	LocationData locData = null;
	//定位图层
	//弹出泡泡图层
	private PopupOverlay   pop  = null;//弹出泡泡图层，浏览节点时使用
	private TextView  popupText = null;//泡泡view
	private View viewCache = null;
	
	//地图相关，使用继承MapView的MyLocationMapView目的是重写touch事件实现泡泡处理
	//如果不处理touch事件，则无需继承，直接使用MapView即可
	MyLocationMapView mMapView = null;	// 地图View
	private MapController mMapController = null;
	public MyLocationListenner myListener = new MyLocationListenner();
	//UI相关
	OnCheckedChangeListener radioButtonListener = null;
	Button requestLocButton = null;
	boolean isRequest = false;//是否手动触发请求定位
	boolean isFirstLoc = true;//是否首次定位
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_location);
       initLocation();
	}
    
    private void initLocation() {
        LocationClientOption option = new LocationClientOption();
        mLocClient = new LocationClient( this );
        mLocClient.registerLocationListener( myListener );
        //就是这个方法设置为true，才能获取当前的位置信息
        //option.setIsNeedAddress(true);
        option.setOpenGps(true);
//        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy
//        );//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        //option.setCoorType("gcj02");//可选，默认gcj02，设置返回的定位结果坐标系
        option.setCoorType("bd09ll");
        //int span = 1000;
        //option.setScanSpan(span);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        mLocClient.setLocOption(option);
        mLocClient.start();
    }
    
    public class MyLocationListenner implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
			// TODO Auto-generated method stub
			if (location == null)
                return ;
			
			double lati = location.getLatitude();
            double longa = location.getLongitude();
            //打印出当前位置
            Log.i("mingguo", "location.getAddrStr()=" + location.getAddrStr()+" lati   "+lati+"  longa  "+longa);
            //打印出当前城市
            Log.i("mingguo", "location.getCity()=" + location.getCity());
		}

		@Override
		public void onReceivePoi(BDLocation arg0) {
			// TODO Auto-generated method stub
			
		}
    	
    };
}

