package com.siziksu.kotlinTicTacToe;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.siziksu.kotlinTicTacToe.common.utils.StringUtilsKt;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.Assert.assertEquals;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    @Test
    public void applicationPackage_isCorrect() throws Exception {
        // Context of the app under test.
        Context context = InstrumentationRegistry.getTargetContext();
        assertEquals("com.siziksu.kotlinTicTacToe", context.getPackageName());
    }

    @Test
    public void getUserFromEmailForFirebase_isCorrect() throws Exception {
        Assert.assertEquals("testsuccessful", StringUtilsKt.getUserFromEmailForFirebase("t]es#t.su$$cce[ssful@gmail.com"));
    }
}
