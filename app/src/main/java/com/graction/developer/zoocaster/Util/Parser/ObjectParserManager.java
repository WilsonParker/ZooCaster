package com.graction.developer.zoocaster.Util.Parser;

import com.graction.developer.zoocaster.Util.StringUtil;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by graction03 on 2017-09-28.
 */

public class ObjectParserManager {
    private static final ObjectParserManager instance = new ObjectParserManager();

    public static ObjectParserManager getInstance() {
        return instance;
    }

    public List<String> getFieldList(Class cls) {
        List<String> list = new ArrayList<>();
        for (Field field : cls.getDeclaredFields())
            list.add(field.getName().toLowerCase());
        return list;
    }

    /**
     * boolean containField : contain fields in String[]
     */
    /*public String[] fieldValueToString(Object obj, boolean containField) throws InvocationTargetException, IllegalAccessException {
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
        Loop1 : for (int i = 1; i < methods.size(); i++) {
            model = methods.get(i);
            for (Annotation annotation : model.getField().getDeclaredAnnotations())
                if(annotation instanceof SqlIgnore)
                    continue Loop1;
            r1 += "," + model.getName();
            if (containField)
                r2 += "," + attachData(model.getMethod().invoke(obj, null));
        }
        String[] result = new String[2];
        result[0] = r1;
        result[1] = r2;
        return result;
    }*/


    public String[] fieldValueToString(Object obj, boolean containField) throws InvocationTargetException, IllegalAccessException {
        List<MethodModel> methods = parserDefaultGetMethodModelList(obj);
        MethodModel model = methods.get(0);
        String r1 = model.getName(), r2 = containField ? (String) model.getMethod().invoke(obj, null) : "";
        for (int i = 1; i < methods.size(); i++) {
            model = methods.get(i);
            r1 += "," + model.getName();
            if (containField)
                r2 += "," + attachData(model.getMethod().invoke(obj, null));
        }
        String[] result = new String[2];
        result[0] = r1;
        result[1] = r2;
        return result;
    }

    public List<MethodModel> parserDefaultGetMethodModelList(Object obj){
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
        return methods;
    }

    public Map<String, Method> getMethods(Class cls, ParserCompareActionMap parserCompareAction) {
        Map<String, Method> map = new HashMap<>();
        for (Method method : cls.getDeclaredMethods()) {
            if (parserCompareAction.compare(method.getName())) {
                parserCompareAction.add(map, method);
            }
        }
        return map;
    }

    public LinkedList<MethodModel> getMethods(Class cls, ParserCompareActionList parserCompareAction) {
        LinkedList<MethodModel> list = new LinkedList<>();
        for (Method method : cls.getDeclaredMethods()) {
            if (parserCompareAction.compare(method.getName())) {
                parserCompareAction.add(list, method);
            }
        }
        return list;
    }

    public Object invoke(Method method, Object obj, Object... value) throws InvocationTargetException, IllegalAccessException {
        Object result;
        Type[] types = method.getParameterTypes();
        Object[] o = new Object[types.length];
        for (int i = 0; i < types.length; i++) {
            String t = types[i].toString().toLowerCase();
            String v = String.valueOf(value[i]);
            if (t.equals(value[i].getClass().toString())) {
                o[i] = value[i];
            } else if (t.contains("string")) {
                o[i] = String.valueOf(value[i]);
            } else if (t.contains("double")) {
                o[i] = Double.parseDouble(v);
            } else if (t.contains("long")) {
                o[i] = Long.parseLong(v);
            } else if (t.contains("float")) {
                o[i] = Float.parseFloat(v);
            } else if (t.contains("int")) {
                o[i] = Integer.parseInt(v);
            } else if (t.contains("boolean")) {
                o[i] = Boolean.parseBoolean(v);
            } else if (t.contains("string")) {
                o[i] = v;
            } else if (t.contains("byte")) {
                o[i] = Byte.parseByte(v);
            } else if (t.contains("char")) {
                o[i] = v.charAt(0);
            }
        }
        result = method.invoke(obj, o);
        return result;
    }

    public Object attachData(Object obj) throws InvocationTargetException, IllegalAccessException {
        if (obj == null)
            return "\"null\"";
        String t[] = obj.getClass().getName().toLowerCase().split("\\.");
        switch (t[t.length - 1]) {
            case "string":
                return "\"" + obj + "\"";
            case "double":
            case "long":
            case "float":
            case "integer":
            case "character":
            case "byte":
                return obj;
            default:
                return obj;
        }
    }

    public interface ParserCompareAction {
        boolean compare(String s);
    }

    public interface ParserCompareActionMap extends ParserCompareAction {
        void add(Map<String, Method> map, Method method);

    }

    public interface ParserCompareActionList extends ParserCompareAction {
        void add(List<MethodModel> list, Method method);

//        String setName(Method method);
    }

    public class MethodModel {
        private String name;
        private Field field;
        private Method method;

        public MethodModel(String name, Field field, Method method) {
            this.name = name;
            this.field = field;
            this.method = method;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Method getMethod() {
            return method;
        }

        public void setMethod(Method method) {
            this.method = method;
        }

        public Field getField() {
            return field;
        }

        public void setField(Field field) {
            this.field = field;
        }
    }

}
