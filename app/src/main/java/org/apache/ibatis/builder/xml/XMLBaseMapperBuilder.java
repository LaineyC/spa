package org.apache.ibatis.builder.xml;

import org.apache.ibatis.parsing.XPathParser;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import java.lang.reflect.Field;

public class XMLBaseMapperBuilder {

    private XPathParser parser;

    public XMLBaseMapperBuilder(XPathParser parser){
        this.parser = parser;
    }

    private XPath xpath;
    private void initXPath() throws Exception{
        Field xpathFiled = XPathParser.class.getDeclaredField("xpath");
        xpathFiled.setAccessible(true);
        this.xpath = (XPath)xpathFiled.get(parser);
    }

    private Document document;
    private void initDocument() throws Exception{
        Field documentFiled = XPathParser.class.getDeclaredField("document");
        documentFiled.setAccessible(true);
        this.document = (Document)documentFiled.get(parser);
    }

    private Node poNode;
    private void initPoNode() throws Exception{
        this.poNode = (Node)xpath.evaluate("/mapper/*[@id='po']", document, XPathConstants.NODE);
    }

    private NodeList columnsNode;
    private void initColumnsNode() throws Exception{
        this.columnsNode = (NodeList)xpath.evaluate("child::*[@column]", poNode,XPathConstants.NODESET);
    }

    private Node idNode;
    private String idColumn;
    private String idProperty;
    private void initIdNode() throws Exception{
        this.idNode = (Node)xpath.evaluate("child::id", poNode,XPathConstants.NODE);
        this.idColumn = idNode.getAttributes().getNamedItem("column").getNodeValue();
        this.idProperty = idNode.getAttributes().getNamedItem("property").getNodeValue();
    }

    private Node insertColumnsNode;
    private String insertColumnsId;
    private void initInsertColumnsNode() throws Exception{
        this.insertColumnsNode = (Node)xpath.evaluate("/mapper/*[@id='insertColumns']", document,XPathConstants.NODE);
        this.insertColumnsId = insertColumnsNode.getAttributes().getNamedItem("id").getNodeValue();
        if(!insertColumnsNode.hasChildNodes()) {
            String insertColumns = "";
            for(int i = 0 ; i < columnsNode.getLength() ; i++){
                Node columnNode = columnsNode.item(i);
                if(columnNode.isEqualNode(idNode))
                    continue;
                insertColumns += columnNode.getAttributes().getNamedItem("column").getNodeValue();
                if(i != columnsNode.getLength() - 1){
                    insertColumns += ",";
                }
            }
            insertColumnsNode.setTextContent(insertColumns);
        }
    }

    private Node updateColumnsNode;
    private String updateColumnsId;
    private void initUpdateColumnsNode()throws Exception{
        this.updateColumnsNode = (Node)xpath.evaluate("/mapper/*[@id='updateColumns']", document,XPathConstants.NODE);
        this.updateColumnsId = updateColumnsNode.getAttributes().getNamedItem("id").getNodeValue();
        if(!updateColumnsNode.hasChildNodes()) {
            String updateColumns = "";
            for(int i = 0 ; i < columnsNode.getLength() ; i++){
                Node columnNode = columnsNode.item(i);
                if(columnNode.isEqualNode(idNode))
                    continue;
                updateColumns += columnNode.getAttributes().getNamedItem("column").getNodeValue();
                if(i != columnsNode.getLength() - 1){
                    updateColumns += ",";
                }
            }
            updateColumnsNode.setTextContent(updateColumns);
        }
    }

    private Node selectColumnsNode;
    private String selectColumnsId;
    private void initSelectColumnsNode()throws Exception{
        this.selectColumnsNode = (Node)xpath.evaluate("/mapper/*[@id='selectColumns']", document,XPathConstants.NODE);
        this.selectColumnsId = selectColumnsNode.getAttributes().getNamedItem("id").getNodeValue();
        if(!selectColumnsNode.hasChildNodes()){
            String selectColumns = "";
            for(int i = 0 ; i < columnsNode.getLength() ; i++){
                Node columnNode = columnsNode.item(i);
                selectColumns += columnNode.getAttributes().getNamedItem("column").getNodeValue();
                if(i != columnsNode.getLength() - 1){
                    selectColumns += ",";
                }
            }
            selectColumnsNode.setTextContent(selectColumns);
        }
    }

    private String table;
    private void initTableNameNode()throws Exception{
        Node tableNode = (Node)xpath.evaluate("/mapper/*[@id='table']", document,XPathConstants.NODE);
        this.table = tableNode.getTextContent();
    }

    private void insert()throws Exception{
        Node insertNode = (Node)xpath.evaluate("/mapper/*[@id='insert']", document,XPathConstants.NODE);
        if(!insertNode.hasChildNodes()){
            insertNode.appendChild(document.createTextNode("INSERT INTO " + table + "("));
            Element insertColumnsInclude = document.createElement("include");
            insertColumnsInclude.setAttribute("refid",insertColumnsId);
            insertNode.appendChild(insertColumnsInclude);
            insertNode.appendChild(document.createTextNode(") VALUES"));
            String inserts = "(";
            boolean hasId = false;
            String[] insertColumns = insertColumnsNode.getTextContent().split(",");
            for(int i = 0 ; i < insertColumns.length ; i++){
                Node columnNode = (Node)xpath.evaluate("child::*[@column='" + insertColumns[i] + "']", poNode, XPathConstants.NODE);
                inserts += "#{" + columnNode.getAttributes().getNamedItem("property").getNodeValue() + "}";
                if(i != insertColumns.length - 1){
                    inserts += ",";
                }
                if(columnNode.equals(idNode)){
                    hasId = true;
                }
            }
            insertNode.appendChild(document.createTextNode(inserts + ")"));
            if(!hasId){
                Element selectKeyInclude = document.createElement("selectKey");
                selectKeyInclude.setAttribute("resultType","java.lang.Integer");
                selectKeyInclude.setAttribute("order","AFTER");
                selectKeyInclude.setAttribute("keyProperty",idProperty);
                selectKeyInclude.setTextContent("SELECT LAST_INSERT_ID()");
                insertNode.appendChild(selectKeyInclude);
            }
        }
    }

    private void insertBatch()throws Exception{
        Node insertBatchNode = (Node)xpath.evaluate("/mapper/*[@id='insertBatch']", document,XPathConstants.NODE);
        if(!insertBatchNode.hasChildNodes()){
            insertBatchNode.appendChild(document.createTextNode("INSERT INTO " + table + "("));
            Element insertColumnsInclude = document.createElement("include");
            insertColumnsInclude.setAttribute("refid",insertColumnsId);
            insertBatchNode.appendChild(insertColumnsInclude);
            insertBatchNode.appendChild(document.createTextNode(") VALUES"));
            Element foreach = document.createElement("foreach");
            foreach.setAttribute("collection", "list");
            foreach.setAttribute("item","po");
            foreach.setAttribute("separator",",");
            String inserts = "(";
            String[] insertColumns = insertColumnsNode.getTextContent().split(",");
            for(int i = 0 ; i < insertColumns.length ; i++){
                Node columnNode = (Node)xpath.evaluate("child::*[@column='" + insertColumns[i] + "']", poNode, XPathConstants.NODE);
                inserts += "#{" + "po." + columnNode.getAttributes().getNamedItem("property").getNodeValue() + "}";
                if(i != insertColumns.length - 1){
                    inserts += ",";
                }
            }
            foreach.appendChild(document.createTextNode(inserts + ")"));
            insertBatchNode.appendChild(foreach);
        }
    }
    private void delete()throws Exception{
        Node deleteNode = (Node)xpath.evaluate("/mapper/*[@id='delete']", document,XPathConstants.NODE);
        if(!deleteNode.hasChildNodes()){
            deleteNode.appendChild(document.createTextNode("UPDATE " + table + " SET isDelete = 1 WHERE isDelete = 0 AND " + idColumn + "=#{" + idProperty + "}"));
        }
    }

    private void deleteBatch()throws Exception{
        Node deleteBatchNode = (Node)xpath.evaluate("/mapper/*[@id='deleteBatch']", document,XPathConstants.NODE);
        if(!deleteBatchNode.hasChildNodes()){
            deleteBatchNode.appendChild(document.createTextNode("UPDATE " + table + " SET isDelete = 1 WHERE isDelete = 0 AND " + idColumn + " IN"));
            Element foreach = document.createElement("foreach");
            foreach.setAttribute("collection","list");
            foreach.setAttribute("open","(");
            foreach.setAttribute("item","id");
            foreach.setAttribute("close",")");
            foreach.setAttribute("separator",",");
            foreach.setTextContent("#{id}");
            deleteBatchNode.appendChild(foreach);
        }
    }

    private void update()throws Exception{
        Node updateNode = (Node)xpath.evaluate("/mapper/*[@id='update']", document,XPathConstants.NODE);
        if(!updateNode.hasChildNodes()) {
            updateNode.appendChild(document.createTextNode("UPDATE " + table + " SET"));
            String[] updateColumns = updateColumnsNode.getTextContent().split(",");
            for(int i = 0 ; i < updateColumns.length ; i++){
                String updateColumn = updateColumns[i];
                Node columnNode = (Node)xpath.evaluate("child::*[@column='" + updateColumn + "']", poNode, XPathConstants.NODE);
                String updateProperty = columnNode.getAttributes().getNamedItem("property").getNodeValue();
                Element ifElement = document.createElement("if");
                ifElement.setAttribute("test",updateProperty +"!=null");
                String content = updateColumn + "=#{" + updateProperty + "}" + (i != updateColumns.length - 1 ? "," : "");
                ifElement.setTextContent(content);
                updateNode.appendChild(ifElement);
            }
            updateNode.appendChild(document.createTextNode("WHERE isDelete = 0 AND " + idColumn + "=#{" + idProperty + "}"));
        }
    }

    private void updateBatch()throws Exception{
        Node updateBatchNode = (Node)xpath.evaluate("/mapper/*[@id='updateBatch']", document,XPathConstants.NODE);
        if(!updateBatchNode.hasChildNodes()) {
            Element foreach = document.createElement("foreach");
            foreach.setAttribute("collection","list");
            foreach.setAttribute("item","po");
            foreach.setAttribute("separator",";");

            foreach.appendChild(document.createTextNode("UPDATE " + table + " SET"));
            String[] updateColumns = updateColumnsNode.getTextContent().split(",");
            for(int i = 0 ; i < updateColumns.length ; i++){
                String updateColumn = updateColumns[i];
                Node columnNode = (Node)xpath.evaluate("child::*[@column='" + updateColumn + "']", poNode, XPathConstants.NODE);
                String updateProperty = columnNode.getAttributes().getNamedItem("property").getNodeValue();
                Element ifElement = document.createElement("if");
                ifElement.setAttribute("test","po." + updateProperty +"!=null");
                String content = updateColumn + "=#{" + "po." + updateProperty + "}" + (i != updateColumns.length - 1 ? "," : "");
                ifElement.setTextContent(content);
                foreach.appendChild(ifElement);
            }
            foreach.appendChild(document.createTextNode("WHERE isDelete = 0 AND " + idColumn + "=#{" + "po." + idProperty + "}"));

            updateBatchNode.appendChild(foreach);
        }
    }

    private void selectById()throws Exception{
        Node selectByIdNode = (Node)xpath.evaluate("/mapper/*[@id='selectById']", document,XPathConstants.NODE);
        if(!selectByIdNode.hasChildNodes()){
            selectByIdNode.appendChild(document.createTextNode("SELECT"));
            Element selectColumnsInclude = document.createElement("include");
            selectColumnsInclude.setAttribute("refid",selectColumnsId);
            selectByIdNode.appendChild(selectColumnsInclude);
            selectByIdNode.appendChild(document.createTextNode("FROM " + table));
            selectByIdNode.appendChild(document.createTextNode("WHERE " + idColumn + "=#{" + idProperty + "}"));
        }
    }

    private void selectByIds()throws Exception{
        Node selectByIdsNode = (Node)xpath.evaluate("/mapper/*[@id='selectByIds']", document,XPathConstants.NODE);
        if(!selectByIdsNode.hasChildNodes()){
            selectByIdsNode.appendChild(document.createTextNode("SELECT"));
            Element selectColumnsInclude = document.createElement("include");
            selectColumnsInclude.setAttribute("refid",selectColumnsId);
            selectByIdsNode.appendChild(selectColumnsInclude);
            selectByIdsNode.appendChild(document.createTextNode("FROM " + table));
            selectByIdsNode.appendChild(document.createTextNode("WHERE " + idColumn + " IN"));
            Element foreach = document.createElement("foreach");
            foreach.setAttribute("collection","list");
            foreach.setAttribute("open","(");
            foreach.setAttribute("item","id");
            foreach.setAttribute("close",")");
            foreach.setAttribute("separator",",");
            foreach.setTextContent("#{id}");
            selectByIdsNode.appendChild(foreach);
        }
    }

    private void selectAll()throws Exception{
        Node selectAllNode = (Node)xpath.evaluate("/mapper/*[@id='selectAll']", document,XPathConstants.NODE);
        if(!selectAllNode.hasChildNodes()){
            selectAllNode.appendChild(document.createTextNode("SELECT"));
            Element selectColumnsInclude = document.createElement("include");
            selectColumnsInclude.setAttribute("refid",selectColumnsId);
            selectAllNode.appendChild(selectColumnsInclude);
            selectAllNode.appendChild(document.createTextNode("FROM " + table));
            selectAllNode.appendChild(document.createTextNode("WHERE isDelete = 0"));
        }
    }

    private void exists()throws Exception{
        Node existsNode = (Node)xpath.evaluate("/mapper/*[@id='exists']", document,XPathConstants.NODE);
        if(!existsNode.hasChildNodes()) {
            existsNode.appendChild(document.createTextNode("SELECT EXISTS ( SELECT " + idColumn + " FROM " + table));
            Element whereElement = document.createElement("where");
            for(int i = 0 ; i < columnsNode.getLength() ; i++){
                String existsColumn = columnsNode.item(i).getAttributes().getNamedItem("column").getNodeValue();
                String existsProperty = columnsNode.item(i).getAttributes().getNamedItem("property").getNodeValue();
                Element ifElement = document.createElement("if");
                ifElement.setAttribute("test", "param1." + existsProperty +"!=null");
                ifElement.setTextContent(" AND " + existsColumn + "=#{param1." + existsProperty + "}");
                whereElement.appendChild(ifElement);
            }
            Element ifElement = document.createElement("if");
            ifElement.setAttribute("test", "param2!=null");
            ifElement.setTextContent(" AND " + idColumn + "!=#{param2}");
            whereElement.appendChild(ifElement);
            existsNode.appendChild(whereElement);
            existsNode.appendChild(document.createTextNode(")"));
        }
    }

    public void createBaseMapperNodes(){
        try {
            initXPath();
            initDocument();
            initPoNode();
            initColumnsNode();
            initIdNode();
            initInsertColumnsNode();
            initUpdateColumnsNode();
            initSelectColumnsNode();
            initTableNameNode();

            insert();
            insertBatch();
            delete();
            deleteBatch();
            update();
            updateBatch();
            selectById();
            selectByIds();
            selectAll();
            exists();

            gc();
        }
        catch(Exception ex){
            throw new RuntimeException("createBaseMapperNodes异常", ex);
        }
    }

    private void gc(){
        this.document = null;
        this.parser = null;
        this.xpath = null;
        this.poNode = null;
        this.columnsNode = null;
        this.idNode = null;
        this.idColumn = null;
        this.idProperty = null;
        this.insertColumnsNode = null;
        this.insertColumnsId = null;
        this.updateColumnsNode = null;
        this.updateColumnsId = null;
        this.selectColumnsNode = null;
        this.selectColumnsId = null;
        this.table = null;
    }

}
