package com.core;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.poi.POIXMLDocument;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.UnderlinePatterns;
import org.apache.poi.xwpf.usermodel.VerticalAlign;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlToken;
import org.openxmlformats.schemas.drawingml.x2006.main.CTNonVisualDrawingProps;
import org.openxmlformats.schemas.drawingml.x2006.main.CTPositiveSize2D;
import org.openxmlformats.schemas.drawingml.x2006.wordprocessingDrawing.CTInline;



public class WordCompileReport {

    public static void searchAndReplace(String srcPath, String destPath,
                                        Map map,Map mapImage) {
        try {
            XWPFDocument document = new XWPFDocument(
                    POIXMLDocument.openPackage(srcPath));

            //替换表格占位符
            checkTables(document,map);
            //替换段落占位符
            checkParagraphs(document,map);
            //在末尾添加文字
            addParagraphToWord(document,"这里添加文字",30,0,"#EB9074",true);
            //替换图片
            replaceTextToImage(document,mapImage,200,200);





            FileOutputStream outStream = null;
            outStream = new FileOutputStream(destPath);
            document.write(outStream);
            outStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




    public static void checkTables(XWPFDocument document,
                                   Map<String,String> map) {
        Iterator it = document.getTablesIterator();
        while (it.hasNext()) {
            XWPFTable table = (XWPFTable) it.next();
            int rcount = table.getNumberOfRows();
            for (int i = 0; i < rcount; i++) {
                XWPFTableRow row = table.getRow(i);
                List<XWPFTableCell> cells = row.getTableCells();
                for (XWPFTableCell cell : cells) {
                    List<XWPFParagraph> listCell;
                    for (Entry<String,String> e : map.entrySet()) {
                        listCell = cell.getParagraphs();
                        List<XWPFRun> cellRun;
                        Map mapAttr = new HashMap();
                        for (int j = 0; j < listCell.size(); j++) {
                            if (listCell.get(j).getText().indexOf(e.getKey()) != -1) {
                                cellRun = listCell.get(j).getRuns();
                                for (int c = 0; c < cellRun.size(); c++) {
                                    if (cellRun.get(c).getText(0).equals(e.getKey())) {
                                        mapAttr = getWordXWPFRunStyle(cellRun.get(c));
                                        listCell.get(j).removeRun(c);
                                        XWPFRun newRun = listCell.get(j).insertNewRun(c);
                                        setWordXWPFRunStyle(newRun, mapAttr,e.getValue(), false);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }



    public static void checkParagraphs(XWPFDocument document,Map<String,String> map){
        List listRun;
        Map mapAttr = new HashMap();
        List<XWPFParagraph> listParagraphs = document.getParagraphs();
        for (int sa = 0; sa < listParagraphs.size(); sa++) {
            for (Entry<String,String> e : map.entrySet()) {
                if (listParagraphs.get(sa).getText().indexOf(e.getKey()) != -1) {
                    listRun = listParagraphs.get(sa).getRuns();
                    for (int p = 0; p < listRun.size(); p++) {
                        if (listRun.get(p).toString().equals(e.getKey())) {
                            //得到占位符的文本格式
                            XWPFRun runOld = listParagraphs.get(sa).getRuns().get(p);
                            mapAttr=getWordXWPFRunStyle(runOld); //封装该占位符文本样式到map
                            listParagraphs.get(sa).removeRun(p);//移除占位符
                            //创建设置对应占位符的文本
                            XWPFRun runNew = listParagraphs.get(sa).insertNewRun(p);
                            setWordXWPFRunStyle(runNew,mapAttr,e.getValue(),true);
                        }
                    }
                }
            }
        }
    }


    public static Map getWordXWPFRunStyle(XWPFRun runOld){
        Map mapAttr = new HashMap();
        mapAttr.put("Color", runOld.getColor());
        if(-1==runOld.getFontSize()){
            mapAttr.put("FontSize", 12);
        }else{
            mapAttr.put("FontSize", runOld.getFontSize());
        }
        mapAttr.put("Subscript", runOld.getSubscript());
        mapAttr.put("Underline", runOld.getUnderline());
        mapAttr.put("FontFamily",runOld.getFontFamily());
        return mapAttr;
    }



    public static XWPFRun setWordXWPFRunStyle(XWPFRun runNew,Map mapAttr,String text,boolean flag){
        runNew.setColor((String) mapAttr.get("Color"));
        if("-1".equals(mapAttr.get("FontSize").toString())){//处理小四字号读取为-1的问题
            runNew.setFontSize(12);
        }else{
            runNew.setFontSize((Integer) mapAttr.get("FontSize"));
        }
        runNew.setBold(flag);
        runNew.setUnderline((UnderlinePatterns) mapAttr.get("Underline"));
        runNew.setText(text);
        runNew.setSubscript((VerticalAlign) mapAttr.get("Subscript"));
        runNew.setFontFamily((String) mapAttr.get("FontFamily"));
        return runNew;
    }


    public static void updatePicture(XWPFDocument document,int id, int width, int height) {
        if(id==0){
            id = document.getAllPictures().size()-1;
        }
        final int EMU = 9525;
        width *= EMU;
        height *= EMU;
        String blipId = document.getAllPictures().get(id).getPackageRelationship()
                .getId();
        CTInline inline = document.createParagraph().createRun().getCTR()
                .addNewDrawing().addNewInline();

        String picXml = ""
                + ""
                + "   "
                + "      "
                + "         "
                + id
                + "\" name=\"Generated\"/>"
                + "            "
                + "         "
                + "         "
                + blipId
                + "\" xmlns:r=\"http://schemas.openxmlformats.org/officeDocument/2006/relationships\"/>"
                + "            "
                + "               "
                + "            "
                + "         "
                + "         "
                + "            "
                + "               "
                + width
                + "\" cy=\""
                + height
                + "\"/>"
                + "            "
                + "            "
                + "               "
                + "            "
                + "         "
                + "      "
                + "   " + "";
        // CTGraphicalObjectData graphicData =
        inline.addNewGraphic().addNewGraphicData();
        XmlToken xmlToken = null;
        try {
            xmlToken = XmlToken.Factory.parse(picXml);
        } catch (XmlException xe) {
            xe.printStackTrace();
        }
        inline.set(xmlToken);
        // graphicData.set(xmlToken);
        inline.setDistT(0);
        inline.setDistB(0);
        inline.setDistL(0);
        inline.setDistR(0);
        CTPositiveSize2D extent = inline.addNewExtent();
        extent.setCx(width);
        extent.setCy(height);
        CTNonVisualDrawingProps docPr = inline.addNewDocPr();
        docPr.setId(id);
        docPr.setName("IMG_" + id);
        docPr.setDescr("IMG_" + id);
    }


    public static void addPictureToWord(XWPFDocument document,String imagePath,int imageType,int width,int height){
        if(0==imageType){
            imageType=XWPFDocument.PICTURE_TYPE_JPEG;
        }
        try {
            String ind = document.addPictureData(new FileInputStream(imagePath), imageType);
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        updatePicture(document,document.getAllPictures().size()-1,400,400);
    }


    public static void addParagraphToWord(XWPFDocument document,String text,int fontSize,int alignment,String RGBColor,boolean isBold){
        XWPFParagraph paragraph = document.createParagraph();
        if(1==alignment){
            paragraph.setAlignment(ParagraphAlignment.CENTER);
        }else if(2==alignment){
            paragraph.setAlignment(ParagraphAlignment.CENTER);
        }else if(3==alignment){
            paragraph.setAlignment(ParagraphAlignment.RIGHT);
        }else{
            paragraph.setIndentationLeft(alignment);
        }
        XWPFRun runOne = paragraph.createRun();
        runOne.setText(text);
        runOne.setBold(isBold);
        runOne.setFontSize(fontSize);
        if(RGBColor.startsWith("#")){
            runOne.setColor(RGBColor.substring(1));
        }else{
            runOne.setColor(RGBColor);
        }
    }


    public static void addRunToParagraph(XWPFParagraph paragraph,String text,int fontSize,String RGBColor,boolean isBold,boolean isWrap){
        XWPFRun runText = paragraph.createRun();
//            runText.setStrike(true);  //删除线
        runText.setBold(isBold);
        runText.setColor(RGBColor);
        runText.setFontSize(fontSize);
        runText.setText(text);
        if(isWrap)runText.addBreak();
    }


    public static void replaceTextToImage(XWPFDocument document,Map<String,String> mapImage,int width,int height){
        List listRun;
        List<XWPFParagraph> listParagraphs = document.getParagraphs();
        for (int sa = 0; sa < listParagraphs.size(); sa++) {
            for (Entry<String,String> e : mapImage.entrySet()) {
                if (listParagraphs.get(sa).getText().indexOf(e.getKey()) != -1) {
                    listRun = listParagraphs.get(sa).getRuns();
                    for (int p = 0; p < listRun.size(); p++) {
                        if (listRun.get(p).toString().equals(e.getKey())) {
                            listParagraphs.get(sa).removeRun(p);//移除占位符
                            //获得当前CTInline
                            CTInline inline = listParagraphs.get(sa).createRun().getCTR().addNewDrawing().addNewInline();
                            try {
                                insertPicture(document,e.getValue(),inline,width,height);
                            } catch (InvalidFormatException e1) {
                                e1.printStackTrace();
                            } catch (FileNotFoundException e1) {
                                e1.printStackTrace();
                            }
                        }
                    }
                }
            }
        }
    }


    public static void insertPicture(XWPFDocument document,String filePath,CTInline inline,int width, int height) throws InvalidFormatException, FileNotFoundException{
        String ind = document.addPictureData(new FileInputStream(filePath), 5);
        int id = document.getAllPictures().size()-1;
        final int EMU = 9525;
        width *= EMU;
        height *= EMU;
        String blipId = document.getAllPictures().get(id).getPackageRelationship()
                .getId();
        String picXml = ""
                + ""
                + "   "
                + "      "
                + "         "
                + id
                + "\" name=\"Generated\"/>"
                + "            "
                + "         "
                + "         "
                + blipId
                + "\" xmlns:r=\"http://schemas.openxmlformats.org/officeDocument/2006/relationships\"/>"
                + "            "
                + "               "
                + "            "
                + "         "
                + "         "
                + "            "
                + "               "
                + width
                + "\" cy=\""
                + height
                + "\"/>"
                + "            "
                + "            "
                + "               "
                + "            "
                + "         "
                + "      "
                + "   " + "";
        inline.addNewGraphic().addNewGraphicData();
        XmlToken xmlToken = null;
        try {
            xmlToken = XmlToken.Factory.parse(picXml);
        } catch (XmlException xe) {
            xe.printStackTrace();
        }
        inline.set(xmlToken);
        inline.setDistT(0);
        inline.setDistB(0);
        inline.setDistL(0);
        inline.setDistR(0);
        CTPositiveSize2D extent = inline.addNewExtent();
        extent.setCx(width);
        extent.setCy(height);
        CTNonVisualDrawingProps docPr = inline.addNewDocPr();
        docPr.setId(id);
        docPr.setName("IMG_" + id);
        docPr.setDescr("IMG_" + id);
    }

    public static void main(String[] args) {
        HashMap map = new HashMap();
        HashMap mapImage = new HashMap();
        map.put("${name}$", "02");
        map.put("${userIDs}$", "5201314");
        mapImage.put("${image1}$", "F:\\A.jpg");
        mapImage.put("${image2}$", "F:\\B.jpg");
        String srcPath = "c:\\zhenli\\cc.docx";
        String destPath = "c:\\zhenli\\输出模版.docx";
        searchAndReplace(srcPath, destPath, map,mapImage);
    }
}