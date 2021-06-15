package com.example.plugin_core;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.MotionEvent;

/**
 * 该 Activity 只是个空壳 ;
 * 主要用于持有从 apk 加载的 Activity 类
 * 并在 ProxyActivity  声明周期方法中调用对应 PluginActivity 类的生命周期方法
 */
public class ProxyActivity extends AppCompatActivity {

    /**
     * 被代理的目标 Activity 组件的全类名
     */
    private String className = "";

    /**
     * 插件包中的 Activity 界面组件
     */
    private PluginActivityInterface pluginActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proxy);

        // 要代理的目标组件的全类名
        className = getIntent().getStringExtra("CLASS_NAME");

        // 注意此处的 ClassLoader 类加载器必须是插件管理器中的类加载器
        try {
            // 使用类加载器加载插件中的界面组件
            Class<?> clazz = getClassLoader().loadClass(className);
            // 使用反射创建插件界面组件 Activity
            Activity activity = (Activity) clazz.newInstance();

            // 判断 Activity 组件是否是 PluginActivityInterface 接口类型的
            if (activity instanceof PluginActivityInterface){
                // 如果是 PluginActivityInterface 类型 , 则强转为该类型
                this.pluginActivity = (PluginActivityInterface) activity;

                // 上下文注入
                // 将该 ProxyActivity 绑定注入到 插件包的 PluginActivity 类中
                // 该 PluginActivity 具有运行的上下文
                // 一旦绑定注入成功 , 则被代理的 PluginActivity 也具有了上下文
                pluginActivity.attach(this);

                // 调用
                pluginActivity.onCreate(savedInstanceState);
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        pluginActivity.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        pluginActivity.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        pluginActivity.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        pluginActivity.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        pluginActivity.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        pluginActivity.onSaveInstanceState(outState);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        return pluginActivity.onTouchEvent(event);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        pluginActivity.onBackPressed();
    }

    /**
     * 需要使用插件包加载的类加载器
     * @return
     */
    @Override
    public ClassLoader getClassLoader() {
        return PluginManager.getInstance().getmDexClassLoader();
    }

    /**
     * 需要使用插件包中加载的资源
     * @return
     */
    @Override
    public Resources getResources() {
        return PluginManager.getInstance().getmResources();
    }
}