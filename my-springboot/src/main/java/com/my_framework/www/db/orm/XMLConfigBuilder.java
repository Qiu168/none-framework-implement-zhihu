package com.my_framework.www.db.orm;

import com.my_framework.www.core.context.ResourceHolder;
import com.my_framework.www.core.context.ResourceLoader;
import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author _qiu
 */
public class XMLConfigBuilder {
    private String mapperLocation;
    public XMLConfigBuilder(){
        ResourceLoader resourceLoader = ResourceHolder.getResourceLoader();
        if(resourceLoader==null){
            mapperLocation="mapper/*.xml";
            return;
        }
        mapperLocation = resourceLoader.getValue("mybatis-plus.mapper-locations");
        if(mapperLocation==null){
            mapperLocation="mapper/*.xml";
        }
    }
    @SneakyThrows
    public List<InputStream> getMapperXml() {
        String baseDir = "src/main/resources";  // 你的资源目录，根据实际情况修改
        Collection<File> files = FileUtils.listFiles(new File(baseDir), new String[]{"xml"}, true);
        ArrayList<InputStream> xmlInputStream=new ArrayList<>();
        for (File file : files) {
            if (file.getPath().matches(".*\\\\"+ mapperLocation.replace("/","\\\\").replace("*", ".*")+"$")) {
                // 处理匹配到的资源
                FileInputStream fileInputStream = new FileInputStream(file);
                xmlInputStream.add(fileInputStream);
            }
        }
        return xmlInputStream;
    }

    // 配置文件解析
    public MapperConfiguration parse(){
        // Mapper映射文件配置信息
        Map<String , MapperStatement> mapperStatementMap = new ConcurrentHashMap<>();
//        Node mappersNode = parser.xNode("/configuration/mappers");
//        NodeList mapperNodeList = mappersNode.getChildNodes();
        List<InputStream> mapperXml = getMapperXml();
        for (InputStream inputStream : mapperXml) {
            XPathParser parser = new XPathParser(inputStream);
            Element element = parser.getDocument().getDocumentElement();
            String namespace = element.getAttribute("namespace");
            NodeList sqlNodeList = element.getChildNodes();
            for (int j = 0; j < sqlNodeList.getLength(); j++) {
                Node sqlNode = sqlNodeList.item(j);
                if (sqlNode.getNodeType() == Node.ELEMENT_NODE) {
                    String id = "";
                    String resultType = "";
                    String parameterType = "";
                    Node idNode = sqlNode.getAttributes().getNamedItem("id");
                    if (null == idNode) {
                        throw new RuntimeException("sql is null");
                    } else {
                        id = sqlNode.getAttributes().getNamedItem("id").getNodeValue();
                    }
                    Node resultTypeNode = sqlNode.getAttributes().getNamedItem("resultType");
                    if (null != resultTypeNode) {
                        resultType = sqlNode.getAttributes().getNamedItem("resultType").getNodeValue();
                    }
                    Node parameterTypeNode = sqlNode.getAttributes().getNamedItem("parameterType");
                    if (null != parameterTypeNode) {
                        parameterType = sqlNode.getAttributes().getNamedItem("parameterType").getNodeValue();
                    }
                    String sql = sqlNode.getTextContent();
                    MapperStatement mapperStatement = new MapperStatement();
                    mapperStatement.setId(id);
                    mapperStatement.setNamespace(namespace);
                    mapperStatement.setParameterType(parameterType);
                    mapperStatement.setResultType(resultType);
                    mapperStatement.setSql(sql);
                    mapperStatementMap.put(namespace + "." + id, mapperStatement);
                }
            }
        }
        return new MapperConfiguration(mapperStatementMap);
    }
}
//MapperConfiguration
// (mapperStatement=
// {
// com.my_framework.www.mybatis.UUserInfoMapper.selectByPrimaryKey=MapperStatement
// (namespace=com.my_framework.www.mybatis.UUserInfoMapper, id=selectByPrimaryKey, parameterType=java.lang.Integer, resultType=com.my_framework.www.mybatis.UUserInfo, sql=
//        select * from account where id = #{id}
//    )})