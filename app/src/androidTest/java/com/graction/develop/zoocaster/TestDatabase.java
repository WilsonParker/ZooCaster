package com.graction.develop.zoocaster;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.graction.developer.zoocaster.DataBase.DataBaseHelper;
import com.graction.developer.zoocaster.DataBase.DataBaseStorage;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class TestDatabase {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
//        Context appContext = InstrumentationRegistry.getTargetContext();
//        assertEquals("com.graction.develop.zoocaster", appContext.getPackageName());
    }

    @Test
    public void test1(){
        String[] args = {"경기도"};
        System.out.println(DataBaseStorage.dataBaseHelper.selectIsNull(DataBaseStorage.Table.TABLE_FAVORITE, DataBaseStorage.Column.COLUMN_FAVORITE_ORIGIN_ADDRESS+"=?", args));
    }
}
