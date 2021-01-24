package com.pp.utils.cache;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.annotation.Nullable;

import com.jakewharton.disklrucache.DiskLruCache;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DiskCacheUtils {
    private static int DEFAULT_CACHE_SIZE = 20;
    private static SizeUnit DEFAULT_SIZE_UNIT = SizeUnit.MB;
    private DiskLruCache mCache;
    private String mPath;
    private long mSize;

    public DiskCacheUtils(Context context, String parent) {
        this(context, parent, null);
    }

    public DiskCacheUtils(Context context, String parent, String child) {
        this(context, parent, child, DEFAULT_CACHE_SIZE);
    }

    public DiskCacheUtils(Context context, String parent, String child, int size) {
        this(context, parent, child, DEFAULT_CACHE_SIZE, DEFAULT_SIZE_UNIT);
    }

    public DiskCacheUtils(Context context, String parent, @Nullable String child, int size, SizeUnit unit) {
        mPath = child == null ? parent : parent + "/" + child;
        mSize = (long) (size * Math.pow(1024, unit.ordinal()));
        int appVersion = 0;
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                appVersion = (int) context.getPackageManager().getPackageInfo(context.getPackageName(), 0).getLongVersionCode();
            } else {
                appVersion = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        File file = new File(mPath);
        if (!file.isDirectory()) try {
            throw new Exception("path must be a directory");
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            mCache = DiskLruCache.open(file, appVersion, 1, mSize);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean saveCache(String key, InputStream is) {
        return saveCache(key, is, 0);
    }

    public boolean saveCache(String key, InputStream is, int index) {
        boolean finish = false;
        try {
            DiskLruCache.Editor editor = mCache.edit(key);
            OutputStream os = editor.newOutputStream(index);
            byte[] buf = new byte[1024];
            int read;
            while ((read = is.read(buf)) != -1) {
                os.write(buf, 0, read);
            }
            os.flush();
            os.close();
            is.close();
            finish = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return finish;
    }

    public InputStream loadCache(String key) {
        InputStream ins = null;
        try {
            DiskLruCache.Snapshot snapshot = mCache.get(key);
            if (snapshot == null) {
                throw new Exception("snapshot is null");
            } else {
                ins = snapshot.getInputStream(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ins;
    }

    public enum SizeUnit {
        BYTE,
        KB,
        MB,
        GB,
        TB
    }
}
