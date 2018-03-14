package com.graction.develop.zoocaster;

import com.graction.developer.zoocaster.Model.Address.AddressModel;
import com.graction.developer.zoocaster.Util.Parser.MathematicsManager;
import com.graction.developer.zoocaster.Util.Parser.ObjectParserManager;

import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class TestString {
    @Test
    public void addition_isCorrect() throws Exception {
//        assertEquals(4, 2 + 2);
        test1();
    }

    // https://maps.googleapis.com
    // maps/api/place/autocomplete/json
    // https://maps.googleapis.com/maps/api/place/autocomplete/json?input=인덕원&components=country:kr&language=ko&key=AIzaSyBVNOVufLmeLx4sWqAjJDSwR8mIrGawdbw
    // https://maps.googleapis.com/maps/api/place/autocomplete/json?input=인덕원&components=country:kr&language=ko&key=AIzaSyBVNOVufLmeLx4sWqAjJDSwR8mIrGawdbw&count=10
    private void test1() {
        Map map = new HashMap();
        map.put("a",1);
        map.put("b",2);
        map.put("c",3);
        try {
            System.out.println(ObjectParserManager.getInstance().mapToString(AddressModel.getParameter("인덕원")));
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

}