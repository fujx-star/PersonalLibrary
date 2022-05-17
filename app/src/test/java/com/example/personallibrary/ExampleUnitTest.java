package com.example.personallibrary;

import org.junit.Test;

import static org.junit.Assert.*;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.personallibrary.room.Book;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }
    @Test
    public void ISBNConnectionTest() {
        String ISBNNumber = "9787020024760";
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;
        String tmp;
        StringBuilder stringBuilder = new StringBuilder();
        try {
            URL url = new URL("https://api.jike.xyz/situ/book/isbn/"+ ISBNNumber +"?apikey="+"12665.9188ff54accb26e3b0bc8efe55703cea.3a26bdeba8ac2b6977fb6b348b837402");
            URLConnection connection = url.openConnection();
            connection.connect();
            inputStream = connection.getInputStream();
            inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            bufferedReader = new BufferedReader(inputStreamReader);
            while ((tmp = bufferedReader.readLine()) != null) {
                stringBuilder.append(tmp);
            }
            System.out.println(stringBuilder);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (inputStreamReader != null) {
                try {
                    inputStreamReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}