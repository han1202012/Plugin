package kim.hsl.standard;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;

import androidx.annotation.NonNull;

public interface InterfaceActivity {
    /**
     * 传递上下文
     * 该方法被  " 宿主 " 模块 调用
     * @param proxyActivity
     */
    public void attach(Activity proxyActivity);

    /*
        Activity 的生命周期
        定义的 Activity 生命周期接口方法作用 :
            " 宿主 " 模块中的 Activity 声明周期 ,
            经过 " 标准 " 模块中的生命周期接口传递 ,
            传递到 " 插件 " 模块中 Activity 对应的生命周期方法 ;
     */

    public void onCreate(Bundle savedInstanceState);
    public void onStart();
    public void onResume();
    public void onPause();
    public void onStop();
    public void onDestroy();
    public void onSaveInstanceState(@NonNull Bundle outState);
    public boolean onTouchEvent(MotionEvent event);
    public void onBackPressed();
}
