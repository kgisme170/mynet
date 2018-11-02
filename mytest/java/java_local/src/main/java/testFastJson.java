import com.alibaba.fastjson.*;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.util.Date;

public class testFastJson {
    private static final String JSON_OBJ_STR = "{\"studentName\":\"lily\",\"studentAge\":12}";
    private static final String JSON_ARRAY_STR = "[{\"studentName\":\"lily\",\"studentAge\":12},{\"studentName\":\"lucy\",\"studentAge\":15}]";
    private static final String COMPLEX_JSON_STR = "{\"teacherName\":\"crystall\",\"teacherAge\":27,\"course\":{\"courseName\":\"english\",\"code\":1270},\"students\":[{\"studentName\":\"lily\",\"studentAge\":12},{\"studentName\":\"lucy\",\"studentAge\":15}]}";

    public void testJSONStrToJSONObject() {
        JSONObject jsonObject = JSONObject.parseObject(JSON_OBJ_STR);
        System.out.println("studentName:  " + jsonObject.getString("studentName") + ":" + "  studentAge:  "
                + jsonObject.getInteger("studentAge"));
    }

    public void testJSONObjectToJSONStr() {
        JSONObject jsonObject = JSONObject.parseObject(JSON_OBJ_STR);
        String jsonString = JSONObject.toJSONString(jsonObject);
        System.out.println(jsonString);

        String jsonString2 = jsonObject.toJSONString();
        System.out.println(jsonString2);
    }

    public void testJSONStrToJSONArray() {
        JSONArray jsonArray = JSONArray.parseArray(JSON_ARRAY_STR);
        int size = jsonArray.size();
        for (int i = 0; i < size; i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            System.out.println("studentName:  " + jsonObject.getString("studentName") + ":" + "  studentAge:  "
                    + jsonObject.getInteger("studentAge"));
        }

        for (Object obj : jsonArray) {
            JSONObject jsonObject = (JSONObject) obj;
            System.out.println("studentName:  " + jsonObject.getString("studentName") + ":" + "  studentAge:  "
                    + jsonObject.getInteger("studentAge"));
        }
    }

    public void testJSONArrayToJSONStr() {
        JSONArray jsonArray = JSONArray.parseArray(JSON_ARRAY_STR);
        String jsonString = JSONArray.toJSONString(jsonArray);
        System.out.println(jsonString);
    }

    public void testComplexJSONStrToJSONObject() {
        JSONObject jsonObject = JSONObject.parseObject(COMPLEX_JSON_STR);
        String teacherName = jsonObject.getString("teacherName");
        Integer teacherAge = jsonObject.getInteger("teacherAge");
        System.out.println("teacherName:  " + teacherName + "   teacherAge:  " + teacherAge);
        JSONObject jsonObjectcourse = jsonObject.getJSONObject("course");
        String courseName = jsonObjectcourse.getString("courseName");
        Integer code = jsonObjectcourse.getInteger("code");
        System.out.println("courseName:  " + courseName + "   code:  " + code);
        JSONArray jsonArraystudents = jsonObject.getJSONArray("students");
        for (Object object : jsonArraystudents) {
            JSONObject jsonObjectone = (JSONObject) object;
            String studentName = jsonObjectone.getString("studentName");
            Integer studentAge = jsonObjectone.getInteger("studentAge");
            System.out.println("studentName:  " + studentName + "   studentAge:  " + studentAge);
        }
    }

    public void testJSONObjectToComplexJSONStr() {
        JSONObject jsonObject = JSONObject.parseObject(COMPLEX_JSON_STR);
        String jsonString = jsonObject.toJSONString();
        System.out.println(jsonString);
    }

    public class Student {
        private String studentName;
        private int studentAge;

        public Student(String n, int a) {
            studentName = n;
            studentAge = a;
        }
    }

    public void testJSONStrToJavaBeanObj() {
        JSONObject jsonObject = JSONObject.parseObject(JSON_OBJ_STR);
        String studentName = jsonObject.getString("studentName");
        Integer studentAge = jsonObject.getInteger("studentAge");
        //第二种方式,使用TypeReference<T>类,由于其构造方法使用protected进行修饰,故创建其子类
        Student student = JSONObject.parseObject(JSON_OBJ_STR, new TypeReference<Student>() {});
        //第三种方式,使用Gson的思想
        Student student2 = JSONObject.parseObject(JSON_OBJ_STR, Student.class);
        System.out.println(student);
        System.out.println(student2);
    }

    public void testJavaBeanObjToJSONStr() {
        Student student = new Student("lily", 12);
        String jsonString = JSONObject.toJSONString(student);
        System.out.println(jsonString);
    }

    void f() {
        testJSONStrToJSONObject();
        testJSONObjectToJSONStr();
        testJSONStrToJSONArray();
        testJSONArrayToJSONStr();
        testComplexJSONStrToJSONObject();
        testJSONObjectToComplexJSONStr();
        testJSONStrToJavaBeanObj();
        testJavaBeanObjToJSONStr();
    }

    public static void main(String[] args) {
        class Person{
            String name = "abc";
            @JSONField(name="NAME")
            public String getname() {return name;}
            @JSONField(name="ID")
            public void setname(String value) {this.name = name;}

            int id=3;
            @JSONField(name="ID")
            public int getid() {return id;}
            @JSONField(name="ID")
            public void setid(int value) {this.id = id;}

        }
        Person p = new Person();
        System.out.println(p);
        String s = JSONObject.toJSONString(p, SerializerFeature.WriteClassName);
        String s1 = JSONObject.toJSONString(p);
        long millis = 1324138987429L;
        Date date = new Date(millis);
        System.out.println(JSONObject.toJSONString(date));
        System.out.println(s);
        System.out.println(s1);
        //new testFastJson().f();
    }
}