package com.example.plugin_core;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import dalvik.system.DexClassLoader;

/**
 * 插件化框架核心类
 */
public class PluginManager {

    /**
     * 类加载器
     * 用于加载插件包 apk 中的 classes.dex 文件中的字节码对象
     */
    private DexClassLoader mDexClassLoader;

    /**
     * 从插件包 apk 中加载的资源
     */
    private Resources mResources;

    /**
     * 插件包信息类
     */
    private PackageInfo mPackageInfo;

    /**
     * 加载插件的上下文对象
     */
    private Context mContext;

    /**
     * PluginManager 单例
     */
    private static PluginManager instance;

    private PluginManager(){

    }

    /**
     * 获取单例类
     * @return
     */
    public static PluginManager getInstance(){
        if (instance == null) {
            instance = new PluginManager();
        }
        return instance;
    }

    /**
     * 加载插件
     * @param context 加载插件的应用的上下文
     * @param loadPath 加载的插件包地址
     */
    public void loadPlugin(Context context, String loadPath) {
        this.mContext = context;

        // DexClassLoader 的 optimizedDirectory 操作目录必须是私有的
        // ( 模式必须是 Context.MODE_PRIVATE )
        File optimizedDirectory = context.getDir("cache_plugin", Context.MODE_PRIVATE);

        // 创建 DexClassLoader
        mDexClassLoader = new DexClassLoader(
                loadPath, // 加载路径
                optimizedDirectory.getAbsolutePath(), // apk 解压缓存目录
                null,
                context.getClassLoader() // DexClassLoader 加载器的父类加载器
        );

        // 加载资源
        try {
            // 通过反射创建 AssetManager
            AssetManager assetManager = AssetManager.class.newInstance();

            // 通过反射获取 AssetManager 中的 addAssetPath 隐藏方法
            Method addAssetPathMethod = assetManager.
                    getClass().
                    getDeclaredMethod("addAssetPath", String.class);

            // 调用反射方法
            addAssetPathMethod.invoke(assetManager, loadPath);

            // 获取资源
            mResources = new Resources(
                    assetManager,
                    context.getResources().getDisplayMetrics(),
                    context.getResources().getConfiguration()
            );

            // 获取插件包中的 Activity 类信息
            mPackageInfo = context.getPackageManager().getPackageArchiveInfo(
                    loadPath, // 加载的插件包路径
                    PackageManager.GET_ACTIVITIES); // 获取的包信息类名

        } catch (IllegalAccessException e) {
            // 调用 AssetManager.class.newInstance() 反射构造方法异常
            e.printStackTrace();
        } catch (InstantiationException e) {
            // 调用 AssetManager.class.newInstance() 反射构造方法异常
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            // getDeclaredMethod 反射方法异常
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            // invoke 执行反射方法异常
            e.printStackTrace();
        }
    }

    /**
     * 获取类加载器
     * @return
     */
    public DexClassLoader getmDexClassLoader() {
        return mDexClassLoader;
    }

    /**
     * 获取插件包中的 Package 信息
     * @return
     */
    public PackageInfo getmPackageInfo() {
        return mPackageInfo;
    }

    /**
     * 获取插件包中的资源
     * @return
     */
    public Resources getmResources() {
        return mResources;
    }
}
