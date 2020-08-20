package com.dong.freemarkerweb.utils;

import com.dong.freemarkerweb.model.Attribute;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author LD
 */
public class CodeGenerate {

    private static Connection conn;
    private static DatabaseMetaData meta;

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("数据库连接失败！");
        }
    }

    public static void openConnection() {
        try {
            if (conn == null || conn.isClosed()) {
                conn = DriverManager.getConnection("jdbc:mysql://192.168.154.127:3306/my_data", "root", "123456");
                meta = conn.getMetaData();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /*//获取注解
    public static String getCommentByTableName(String tableName) throws Exception {
        openConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SHOW CREATE TABLE " + tableName);
        String comment = null;
        if (rs != null && rs.next()) {
            comment = rs.getString(2);
        }
        rs.close();
        stmt.close();
        conn.close();
        return comment;
    }

    //获取所有表名称
    public static List<String> getTableNames() {
        openConnection();
        ResultSet rs = null;
        List<String> nameList = new ArrayList<>();
        try {
            rs = meta.getTables("my_data", null, null, new String[]{"TABLE"});
            while (rs.next()) {
                String tName = rs.getString("TABLE_NAME");
                nameList.add(tName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return nameList;
    }*/

    /**
     * 列信息数组的集合。List中每个元素是一个数组，代表一个列的信息；
     * 每个数组的元素1是列名，元素2是注释，元素3是类型
     *
     * @param tableName 表名
     * @return 返回字段信息
     */
    public static List<String[]> getTableColumnsInfo(String tableName) throws Exception {
        openConnection();
        ResultSet rs = meta.getColumns("my_data", "%", tableName, "%");
        List<String[]> columnInfoList = new ArrayList<>();
        while (rs.next()) {
            String[] colInfo = new String[3];
            colInfo[0] = rs.getString("COLUMN_NAME");
            colInfo[1] = rs.getString("TYPE_NAME");
            colInfo[2] = rs.getString("REMARKS");
            columnInfoList.add(colInfo);
        }
        return columnInfoList;
    }

    /**
     * 获取数据
     *
     * @param tableName   表名
     * @return 返回元数据
     * @throws Exception 异常
     */
    private static Map<String, Object> getData(String tableName, String classAnnotation) throws Exception {
        Map<String, Object> result = new HashMap<>();
        result.put("className", tableName);
        result.put("classAnnotation", classAnnotation);
        result.put("author", System.getenv().get("USERNAME"));//获取电脑名称为创建人
        List<String[]> tableColumnsInfo = getTableColumnsInfo(tableName);
        List<Attribute> attributeList = new ArrayList<>();
        for (String[] strings : tableColumnsInfo) {
            attributeList.add(new Attribute(toCamel(strings[0]), convertDataType(strings[1]), strings[2]));
        }
        result.put("propertyList", attributeList);
        return result;
    }

    /**
     * 转换数据类型
     *
     * @param dataType 数据类型
     * @return 返回转换后的数据类型
     */
    public static String convertDataType(String dataType) {
        String result;
        switch (dataType) {
            case "VARCHAR":
                result = "String";
                break;
            case "BIGINT":
                result = "Long";
                break;
            case "INT":
                result = "Integer";
                break;
            case "DATETIME":
                result = "Date";
                break;
            default:
                result = "Object";
                break;
        }
        return result;
    }

    /**
     * 转为驼峰
     *
     * @param columnName 字段名
     * @return 返回转换后的字段名
     */
    public static String toCamel(String columnName) {
        StringBuilder result = new StringBuilder();
        columnName = columnName.toLowerCase();
        if (!columnName.contains("_")) {
            // 不含下划线，仅将首字母小写
            return columnName;
        } else {
            String[] string = columnName.split("_");
            for (int i = 0; i < string.length; i++) {
                if (i > 0) {
                    string[i] = string[i].substring(0, 1).toUpperCase() + string[i].substring(1);
                }
                result.append(string[i]);
            }
        }
        return result.toString();
    }

    /**
     * 生成代码
     *
     * @param map          元数据
     * @param packageName  包命
     * @param fileName     文件名
     * @param templateName 模板名
     * @throws IOException
     * @throws TemplateException
     */
    public void generate(Map<String, Object> map, String packageName, String fileName, String templateName) throws IOException, TemplateException {
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_22);
        configuration.setDirectoryForTemplateLoading(new File("src\\main\\resources\\templates"));
        configuration.setDefaultEncoding("UTF-8");
        configuration.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        Template template = configuration.getTemplate(templateName);
        File dir = new File("E:/MyProject/mySpringBoot/src/main/java/" + packageName.replace(".", "/"));
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File file = new File(dir, fileName);
        if (file.exists()){
            file.delete();
        }
        OutputStream outputStream = new FileOutputStream(file); //java文件的生成目录
        Writer out = new OutputStreamWriter(outputStream);
        map.put("packageName",packageName);
        template.process(map, out);
        outputStream.flush();
        outputStream.close();
        System.out.println("操作成功！");
        System.out.println("路径：" + dir.getAbsolutePath());

    }

    public static void batchGenerate(String tableName,String classAnnotation) throws Exception {
        String templateName = "model.ftl";
        String packageName = "com.dong.web."+templateName.substring(0,templateName.lastIndexOf("."));
        String fileName = tableName.substring(0, 1).toUpperCase() + tableName.substring(1) + "InfoBean.java";

        //获取元数据
        Map<String, Object> map = getData(tableName, classAnnotation);
        conn.close();
        //生成model
        new CodeGenerate().generate(map, packageName, fileName, templateName);
        //生成controller
        templateName = "controller.ftl";
        packageName = "com.dong.web."+templateName.substring(0,templateName.lastIndexOf("."));
        fileName = tableName.substring(0, 1).toUpperCase() + tableName.substring(1) + "InfoController.java";
        new CodeGenerate().generate(map, packageName, fileName, templateName);
        //生成service
        templateName = "service.ftl";
        packageName = "com.dong.web."+templateName.substring(0,templateName.lastIndexOf("."));
        fileName = tableName.substring(0, 1).toUpperCase() + tableName.substring(1) + "InfoService.java";
        new CodeGenerate().generate(map, packageName, fileName, templateName);
        templateName = "service.impl.ftl";
        packageName = "com.dong.web."+templateName.substring(0,templateName.lastIndexOf("."));
        fileName = tableName.substring(0, 1).toUpperCase() + tableName.substring(1) + "InfoServiceImpl.java";
        new CodeGenerate().generate(map, packageName, fileName, templateName);
    }

    public static void main(String[] args) {
        try {
            batchGenerate("permission","权限");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
