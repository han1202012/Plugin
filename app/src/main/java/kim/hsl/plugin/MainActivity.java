package kim.hsl.plugin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.plugin_core.PluginManager;
import com.example.plugin_core.ProxyActivity;

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


        /*
            假设
         */

    }

    public void onClick(View view) {
        // 跳转到 plugin_core 模块的代理 Activity
        Intent intent = new Intent(this, ProxyActivity.class);
        startActivity(intent);
    }

}