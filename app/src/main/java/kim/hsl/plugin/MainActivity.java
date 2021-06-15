package kim.hsl.plugin;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.plugin_core.PluginManager;
import com.example.plugin_core.ProxyActivity;

import pub.devrel.easypermissions.EasyPermissions;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.i(TAG, "getClassLoader() : " +
                getClassLoader());
        Log.i(TAG, "getClassLoader().getParent() : " +
                getClassLoader().getParent());
        Log.i(TAG, "getClassLoader().getParent().getParent() : " +
                getClassLoader().getParent().getParent());

        EasyPermissions.requestPermissions(
                this,
                "权限申请原理对话框 : 描述申请权限的原理",
                100,

                // 下面是要申请的权限 可变参数列表
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        );


        /*
            加载 " 插件 " 模块的 apk 文件
            先将该插件包拷贝到
         */
        String path = getExternalFilesDir(null).getAbsolutePath() + "/plugin-debug.apk";
        Log.i(TAG, "path : " + path);

        PluginManager.getInstance().loadPlugin(this, path);

    }

    public void onClick(View view) {
        // 跳转到 plugin_core 模块的代理 Activity
        // 首先要获取 " 插件 " 模块中的入口 Activity 类
        Intent intent = new Intent(this, ProxyActivity.class);

        // 获取  " 插件 " 模块中的 Activity 数组信息
        ActivityInfo[] activityInfos = PluginManager.getInstance().getmPackageInfo().activities;

        // 获取的插件包中的 Activity 不为空 , 才进行界面跳转
        if (activityInfos.length > 0) {
            Log.i(TAG, "CLASS_NAME : " + activityInfos[0].toString());

            // 这里取插件包中的第 0 个 Activity
            // 次序就是在 AndroidManifest.xml 清单文件中定义 Activity 组件的次序
            // 必须将 Launcher Activity 定义在第一个位置
            // 不能在 Launcher Activity 之前定义 Activity 组件
            // 传入的是代理的目标组件的全类名
            intent.putExtra("CLASS_NAME", activityInfos[0].name);
            startActivity(intent);
        }
    }

}