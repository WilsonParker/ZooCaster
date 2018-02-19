package com.graction.developer.zoocaster.Util.Parser;

import android.content.Context;

import com.graction.developer.zoocaster.Util.Log.HLogger;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Map;

public class XmlPullParserManager {
    private static final XmlPullParserManager instance = new XmlPullParserManager();
    private final ObjectParserManager objectParserManager = ObjectParserManager.getInstance();
    private HLogger logger;
    private XmlPullParserFactory factory;
    private XmlPullParser parser;

    private Context context;

    public static XmlPullParserManager getInstance() {
        return instance;
    }

    public XmlPullParserManager() {
        try {
            factory = XmlPullParserFactory.newInstance();
            parser = factory.newPullParser();
            logger = new HLogger(getClass());
        } catch (XmlPullParserException e) {
            logger.log(HLogger.LogType.ERROR, "XmlPullParserManager()", "init error", e);
        }
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public <T> ArrayList<T> xmlParser(Class cls, int resourceId) throws Exception {
        ArrayList<T> arrayList = new ArrayList<>();
        ArrayList<String> attributes = new ArrayList<>();
//        InputStream is = context.getResources().openRawResource(resourceId);
        InputStream is = context.getAssets().open("xml/weathers.xml");
        parser.setInput(new InputStreamReader(is, "UTF-8"));

        T t = null;
        final String clsName = cls.getCanonicalName(), clsLowName = clsName.substring(clsName.lastIndexOf(".") + 1).toLowerCase(), condition = "set";
        String startTag;
        int eventType;

        Map<String, Method> methods = objectParserManager.getMethods(cls, new ObjectParserManager.ParserCompareActionMap() {
            @Override
            public boolean compare(String s) {
                return s.contains(condition);
            }

            @Override
            public void add(Map<String, Method> map, Method method) {
                map.put(method.getName().replace(condition, "").toLowerCase(), method);
            }
        });
        eventType = parser.getEventType();

        while (eventType != XmlPullParser.END_DOCUMENT) {
            switch (eventType) {
                case XmlPullParser.START_TAG:
                    startTag = parser.getName().toLowerCase();
                    if (startTag.toLowerCase().equals(clsLowName)) {
                        t = (T) cls.newInstance();
                        for (int i = 0; i < parser.getAttributeCount(); i++) {
                            attributes.add(parser.getAttributeName(i));
                        }
                    }
                    for (String attribute : attributes) {
//                        logger.log(HLogger.LogType.INFO, "", "attribute : " + attribute);
//                        logger.log(HLogger.LogType.INFO, "", "attribute : " + parser.getAttributeValue(null, attribute));
//                        logger.log(HLogger.LogType.INFO, "", "run methodName : " + methods.get(attribute).getName());
//                        t = (T) methods.get(attribute).invoke(t, parser.getAttributeValue(null, attribute));
                        objectParserManager.invoke(methods.get(attribute), t, parser.getAttributeValue(null, attribute));
                    }
                    break;
                case XmlPullParser.END_TAG:
                    String endTag = parser.getName();
                    if (endTag.equals(clsLowName)) {
                        arrayList.add(t);
//                        logger.log(HLogger.LogType.INFO, t.toString());
                    }
                    break;
            }
            eventType = parser.next();
        }
        return arrayList;
    }

}
