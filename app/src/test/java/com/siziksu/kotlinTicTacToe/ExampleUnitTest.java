package com.siziksu.kotlinTicTacToe;

import com.siziksu.kotlinTicTacToe.common.utils.StringUtilsKt;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    @Test
    public void getUserFromEmailForFirebase_isCorrect() throws Exception {
        assertEquals("testsuccessful", StringUtilsKt.getUserFromEmailForFirebase("t]es#t.su$$cce[ssful@gmail.com"));
    }
}