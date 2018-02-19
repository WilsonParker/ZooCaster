package com.graction.developer.zoocaster.DataBase;

import android.content.ContentValues;
import android.database.Cursor;

import com.graction.developer.zoocaster.Util.Parser.ObjectParserManager;
import com.graction.developer.zoocaster.Util.StringUtil;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by graction03 on 2017-09-28.
 */

public class DataBaseParserManager extends ObjectParserManager {
    private static final DataBaseParserManager instance = new DataBaseParserManager();
    private ContentValues values;

    public static DataBaseParserManager getInstance() {
        return instance;
    }

    public <T> T parseObject(Cursor cursor, Class cls) throws IllegalAccessException, InstantiationException, InvocationTargetException {
        T obj = (T) cls.newInstance();
        Map<String, Method> map = getMethods(cls, new ParserCompareActionMap() {
            @Override
            public void add(Map<String, Method> map, Method method) {
                map.put(method.getName().replace("set", "").toLowerCase(), method);
            }

            @Override
            public boolean compare(String s) {
                return s.toLowerCase().contains("set");
            }
        });
        for (int i = 0; i < cursor.getColumnCount(); i++) {
            Method method = map.get(cursor.getColumnName(i).toLowerCase());
            method.invoke(obj, getData(cursor, i, method));
        }
        return obj;
    }

    public Object getData(Cursor cursor, int index, Method method) {
        String typeC = method.getParameterTypes()[0].getName();
        String type = typeC;
        if (typeC.contains(".")) {
            String s[] = typeC.split("\\.");
            type = s[s.length - 1].toLowerCase();
        }
        switch (type) {
            case "int":
            case "integer":
                return cursor.getInt(index);
            case "string":
                return cursor.getString(index);
            case "float":
                return cursor.getFloat(index);
            case "long":
                return cursor.getLong(index);
            case "double":
                return cursor.getDouble(index);
            case "short":
                return cursor.getShort(index);
            default:
                return null;

        }
    }

    /**
     * boolean containField : contain fields in String[]
     */
    public String[] fieldValueToString(Object obj, boolean containField) throws InvocationTargetException, IllegalAccessException {
        Field[] fields = obj.getClass().getDeclaredFields();
        LinkedList<Field> fieldList = new LinkedList<>();
        for (Field field : fields)
            fieldList.add(field);

        List<MethodModel> methods = getMethods(obj.getClass(), new ParserCompareActionList() {
            @Override
            public void add(List<MethodModel> list, Method method) {
                Field field = null;
                for (int i = 0; i < fieldList.size(); i++) {
                    field = fieldList.get(i);
                    if (method.getName().toLowerCase().contains(field.getName().toLowerCase()))
                        break;
                }
                list.add(new MethodModel(StringUtil.convertFirstUpperOrLower(method.getName().replace("get", ""), false), field, method));
            }

            @Override
            public boolean compare(String s) {
                return s.toUpperCase().contains("GET");
            }
        });
        MethodModel model = methods.get(0);
        String r1 = model.getName(), r2 = containField ? attachData(model.getMethod().invoke(obj, null)).toString() : "";
        Loop1:
        for (int i = 1; i < methods.size(); i++) {
            model = methods.get(i);
            for (Annotation annotation : model.getField().getDeclaredAnnotations())
                if (annotation instanceof SqlIgnore)
                    continue Loop1;
            r1 += "," + model.getName();
            if (containField)
                r2 += "," + attachData(model.getMethod().invoke(obj, null));
        }
        String[] result = new String[2];
        result[0] = r1;
        result[1] = r2;
        return result;
    }

    public ContentValues bindContentValues(Object obj) throws Exception {
        values = new ContentValues();
        sqlBinding(parserDefaultGetMethodModelList(obj), (model -> {
            Method method = model.getMethod();
            switch (method.getReturnType().getSimpleName().toLowerCase()) {
                case "int":
                    values.put(model.getName(), (int) method.invoke(obj, null));
                    break;
                case "boolean":
                    values.put(model.getName(), (boolean) method.invoke(obj, null));
                    break;
                case "int[]":
                    values.put(model.getName(), StringUtil.arrayToString((int[]) method.invoke(obj, null)));
                    break;
                default:
                case "string":
                    System.out.println("name : "+model.getName());
                    System.out.println("type : "+method.getReturnType().getSimpleName().toLowerCase());
                    System.out.println("value : "+attachData(method.invoke(obj, null)));
                    values.put(model.getName(), (String) method.invoke(obj, null));
                    break;
            }
        }));
        return values;
    }


    /**
     * Need to optimization
     * this body was overlap
     */
    public void sqlBinding(List<MethodModel> methods, SqlBindingListener listener) throws Exception {
        MethodModel model = methods.get(0);
        listener.bindSql(model);
        Loop1:
        for (int i = 1; i < methods.size(); i++) {
            model = methods.get(i);
            for (Annotation annotation : model.getField().getDeclaredAnnotations())
                if (annotation instanceof SqlIgnore)
                    continue Loop1;
            listener.bindSql(model);
        }
    }


    public interface SqlBindingListener {
        void bindSql(MethodModel model) throws Exception;
    }
}
