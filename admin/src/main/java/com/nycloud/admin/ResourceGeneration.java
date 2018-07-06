package com.nycloud.admin;

import com.nycloud.common.constants.SysConstant;
import com.nycloud.common.utils.SnowFlake;
import com.nycloud.admin.security.ResourcesMapping;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import java.io.File;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * @description: 资源初始化所有资源数据并添加进数据库，注意先清空掉数据库资源再添加，以免重复性添加
 * @author: super.wu
 * @date: Created in 2018/5/18 0018
 * @modified By:
 * @version: 1.0
 **/
public class ResourceGeneration {

    private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/nycloud-admin-v2?useUnicode=true&characterEncoding=utf-8";
    private static final String USER = "root";
    private static final String PASS = "123456";

    private static final String packageName = "com.nycloud.admin.controller";

    public static void main(String[] args) {
        Connection conn = null;
        Statement stmt = null;
        List<String> classNames = getClassName(packageName);
        try {
            Class.forName(JDBC_DRIVER);

            //STEP 3: Open a connection
            System.out.println("Connecting to a selected database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("Connected database successfully...");

            //STEP 4: Execute a query
            System.out.println("Inserting records into the table...");
            stmt = conn.createStatement();
            for (String className : classNames) {
                Class<?> cls = Class.forName(className);
                RequestMapping requestMapping = cls.getDeclaredAnnotation(RequestMapping.class);
                Api api = cls.getDeclaredAnnotation(Api.class);
                if (requestMapping == null || api == null) {
                    continue;
                }
                String classUrl = null;
                if (requestMapping != null && requestMapping.value().length > 0) {
                    classUrl = requestMapping.value()[0];
                }
                Long parentId = SnowFlake.getInstance().nextId();
                String parentName = api.value();
                String parentDescription = api.tags()[0];
                StringBuffer parentSqlBuffer = new StringBuffer("insert into sys_resource (id, name, description, level, type) values('");
                parentSqlBuffer.append(parentId).append("','");
                parentSqlBuffer.append(parentName).append("','");
                parentSqlBuffer.append(parentDescription).append("',");
                parentSqlBuffer.append("1, 1)");
                try {
                    stmt.executeUpdate(parentSqlBuffer.toString());
                } catch (Exception e) {
                    System.out.println(parentSqlBuffer.toString());
                }
                Method[] methods = cls.getMethods();
                for (Method method: methods) {
                    ApiOperation apiOperation = method.getDeclaredAnnotation(ApiOperation.class);
                    RequestMapping methodRequestMapping = method.getDeclaredAnnotation(RequestMapping.class);
                    PostMapping postMapping = method.getDeclaredAnnotation(PostMapping.class);
                    DeleteMapping deleteMapping = method.getDeclaredAnnotation(DeleteMapping.class);
                    GetMapping getMapping = method.getDeclaredAnnotation(GetMapping.class);
                    PutMapping putMapping = method.getDeclaredAnnotation(PutMapping.class);
                    ResourcesMapping resourcesMapping = method.getDeclaredAnnotation(ResourcesMapping.class);
                    if (apiOperation != null) {
                        StringBuffer sb = new StringBuffer("url = ");
                        sb.append(classUrl);
                        String methodType;
                        String methodUrl = "";
                        if (postMapping != null) {
                            methodType = "POST";
                            if (postMapping.value().length > 0) {
                                methodUrl = postMapping.value()[0];
                            }
                        } else if (deleteMapping != null) {
                            methodType = "DELETE";
                            if (deleteMapping.value().length > 0) {
                                methodUrl = deleteMapping.value()[0];
                            }
                        } else if (getMapping != null) {
                            methodType = "GET";
                            if (getMapping.value().length > 0) {
                                methodUrl = getMapping.value()[0];
                            }
                        } else  if (putMapping != null) {
                            methodType = "PUT";
                            if (putMapping.value().length > 0) {
                                methodUrl = putMapping.value()[0];
                            }
                        } else if (methodRequestMapping != null) {
                            methodUrl = methodRequestMapping.value()[0];
                            methodType = methodRequestMapping.value()[1];
                        } else {
                            break;
                        }
                        sb.append(methodUrl);
                        sb.append(" method = ");
                        sb.append(methodType);
                        sb.append(" 功能 = ");
                        sb.append(apiOperation.value());
                        System.out.println(sb.toString());
                        String name = apiOperation.value();
                        String url = classUrl != null ? classUrl + methodUrl : methodUrl;
                        String urlRequestType = methodType;
                        String description = apiOperation.notes();
                        int type = methodUrl.indexOf(SysConstant.API_NO_PERMISSION) != -1 ? 3 : 2;
                        if (resourcesMapping != null) {
                            String code = resourcesMapping.code();
                            String pageElements = resourcesMapping.element();
                            StringBuffer sqlBuffer = new StringBuffer("insert into sys_resource (id, name, url, url_request_type, description, code, page_elements, parent_id, level, type) values('");
                            sqlBuffer.append(SnowFlake.getInstance().nextId()).append("','");
                            sqlBuffer.append(name).append("','");
                            sqlBuffer.append("/").append(url).append("','");
                            sqlBuffer.append(urlRequestType).append("','");
                            sqlBuffer.append(description).append("','");
                            sqlBuffer.append(code).append("','");
                            sqlBuffer.append(pageElements).append("','");
                            sqlBuffer.append(parentId).append("',");
                            sqlBuffer.append("2").append(",");
                            sqlBuffer.append(type).append(")");
                            stmt.executeUpdate(sqlBuffer.toString());
                        } else {
                            StringBuffer sqlBuffer = new StringBuffer("insert into sys_resource (id, name, code, page_elements, url, url_request_type, description, parent_id, level, type) values('");
                            sqlBuffer.append(SnowFlake.getInstance().nextId()).append("','");
                            sqlBuffer.append(name).append("','','','");
                            sqlBuffer.append("/").append(url).append("','");
                            sqlBuffer.append(urlRequestType).append("','");
                            sqlBuffer.append(description).append("','");
                            sqlBuffer.append(parentId).append("',");
                            sqlBuffer.append("2").append(",");
                            sqlBuffer.append(type).append(")");
                            try {
                                stmt.executeUpdate(sqlBuffer.toString());
                            } catch (Exception e) {
                                System.out.println(sqlBuffer.toString());
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) { stmt.close(); }
                if (conn != null) { conn.close(); }
            } catch (SQLException e) {

            }
        }
    }

    public static List<String> getClassName(String packageName) {
        String filePath = ClassLoader.getSystemResource("").getPath() + packageName.replace(".", "/");
        List<String> fileNames = getClassName(filePath, null);
        return fileNames;
    }

    private static List<String> getClassName(String filePath, List<String> className) {
        List<String> myClassName = new ArrayList<String>();
        File file = new File(filePath);
        File[] childFiles = file.listFiles();
        for (File childFile : childFiles) {
            if (childFile.isDirectory()) {
                myClassName.addAll(getClassName(childFile.getPath(), myClassName));
            } else {
                String childFilePath = childFile.getPath();
                childFilePath = childFilePath.substring(childFilePath.indexOf("/classes") + 9, childFilePath.lastIndexOf("."));
                childFilePath = childFilePath.replace("/", ".");
                myClassName.add(childFilePath);
            }
        }
        return myClassName;
    }

}
