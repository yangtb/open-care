package com.sm.open.care.core.utils;

import jxl.CellView;
import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.UnderlineStyle;
import jxl.format.VerticalAlignment;
import jxl.write.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: ExcelUtils
 * @Description: Excel工具类
 * @Author yangtongbin
 * @Date 2018/3/20 16:54
 */
public class ExcelUtils {
    /**
     * 设置让浏览器弹出下载对话框的Header.
     *
     * @param fileName 下载后的文件名.
     */
    public static void setFileDownloadHeader(HttpServletResponse response,HttpServletRequest request, String fileName) {
        try {
            //中文文件名支持
            final String userAgent = request.getHeader("USER-AGENT");
            String finalFileName = null;
            if(StringUtils.contains(userAgent, "MSIE")||StringUtils.contains(userAgent, "Trident")){//IE浏览器
                finalFileName = URLEncoder.encode(fileName,"UTF-8");
            }else if(StringUtils.contains(userAgent, "Chrome")||StringUtils.contains(userAgent, "Mozilla")||StringUtils.contains(userAgent, "Firefox")||StringUtils.contains(userAgent, "Safari")){//google,火狐浏览器
                finalFileName = new String(fileName.getBytes(), "ISO8859-1");
            }else{
                finalFileName = URLEncoder.encode(fileName,"UTF-8");//其他浏览器
            }
            response.setHeader("Content-Disposition", "attachment; filename=\"" + finalFileName + "\"");
            response.setHeader("Content-Type", "application/force-download");
            response.setContentType("application/vnd.ms-excel;charset=utf-8");
        } catch (UnsupportedEncodingException e) {
        }
    }
    /**
     * 通用excel生成方法
     *
     * @param response
     * @param titles
     * @param datas
     */
    public static void createXlsFile(HttpServletResponse response, String[] titles, List<List<String>> datas) {

        try {
            //新建文件
            WritableWorkbook book = Workbook.createWorkbook(response.getOutputStream());

            //生成名为"第一页"的工作表，参数0表示这是第一页
            WritableSheet sheet = book.createSheet("第一页", 0);

            CellView navCellView = new CellView();
            navCellView.setAutosize(true); //设置自动大小
            /** 写入内容 */
            //写入表头
            for (int i = 0; i < titles.length; i++) {
                sheet.setColumnView(i, navCellView);
                sheet.addCell(new Label(i, 0, titles[i]));
            }

            //写入数据内容
            for (int i = 0; i < datas.size(); i++) {
                List<String> row = datas.get(i);

                for (int j = 0; j < row.size(); j++) {
                    sheet.setColumnView(j, navCellView);
                    sheet.addCell(new Label(j, i + 1, row.get(j)));
                }
            }
            //写入数据
            book.write();
            //关闭文件
            book.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 通用excel生成方法
     *
     * @param file
     * @param titles
     * @param datas
     */
    public static void createXlsFile(File file, String[] titles, List<List<String>> datas) {

        try {
            //新建文件
            WritableWorkbook book = Workbook.createWorkbook(file);

            //生成名为"第一页"的工作表，参数0表示这是第一页
            WritableSheet sheet = book.createSheet("第一页", 0);

            CellView navCellView = new CellView();
            navCellView.setAutosize(true); //设置自动大小
            /** 写入内容 */
            //写入表头
            for (int i = 0; i < titles.length; i++) {
                sheet.setColumnView(i, navCellView);
                sheet.addCell(new Label(i, 0, titles[i]));
            }

            //写入数据内容
            for (int i = 0; i < datas.size(); i++) {
                List<String> row = datas.get(i);

                for (int j = 0; j < row.size(); j++) {
                    sheet.setColumnView(j, navCellView);
                    sheet.addCell(new Label(j, i + 1, row.get(j)));
                }
            }
            //写入数据
            book.write();
            //关闭文件
            book.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 通用excel生成方法
     *
     * @param response
     * @param titles
     * @param datas
     */
    public static void createXlsFile(HttpServletResponse response, String[] titles, List<List<String>> datas, int[] size) {

        try {
            //新建文件
            WritableWorkbook book = Workbook.createWorkbook(response.getOutputStream());

            //生成名为"第一页"的工作表，参数0表示这是第一页
            WritableSheet sheet = book.createSheet("第一页", 0);

            CellView navCellView = new CellView();
//			navCellView.setAutosize(true); //设置自动大小
            /** 写入内容 */
            //写入表头
            for (int i = 0; i < titles.length; i++) {
                navCellView.setSize(size[i]);
                sheet.setColumnView(i, navCellView);
                sheet.addCell(new Label(i, 0, titles[i]));
            }

            //写入数据内容
            for (int i = 0; i < datas.size(); i++) {
                List<String> row = datas.get(i);

                for (int j = 0; j < row.size(); j++) {
                    navCellView.setSize(size[j]);
                    sheet.setColumnView(j, navCellView);
                    sheet.addCell(new Label(j, i + 1, row.get(j)));
                }
            }
            //写入数据
            book.write();
            //关闭文件
            book.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * @Description:通用excel生成方法有标题
     * @Method:
     * @Params:
     * @ReturnType:
     * @Author:xuty@hsyuntai.com
     * @Date:2017/8/14
     * @Copyright: 版权归 hsyuntai 所有
     */
    public static void createXlsFileHeader(HttpServletResponse response, String[] titles, List<List<String>> datas, int[] size,String header) {

        try {
            //新建文件
            WritableWorkbook book = Workbook.createWorkbook(response.getOutputStream());
            //生成名为"第一页"的工作表，参数0表示这是第一页
            WritableSheet sheet = book.createSheet("第一页", 0);

            CellView navCellView = new CellView();
			/*合并单元格 （0，0）到（3，0）的点*/
			if(null!=titles&&titles.length>1){
                sheet.mergeCells(0, 0, titles.length-1, 0);
            }
            sheet.setColumnView(0, navCellView);
			/*标题的字体*/
            WritableFont wf_header = new WritableFont(WritableFont.ARIAL, 12,
                    WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE,
                    jxl.format.Colour.BLACK);// 定义格式 字体 下划线 斜体 粗体 颜色
            WritableCellFormat wcf_header = new WritableCellFormat(wf_header); // 单元格定义
            wcf_header.setBackground(jxl.format.Colour.GRAY_50); // 设置单元格的背景颜色
            wcf_header.setAlignment(jxl.format.Alignment.CENTRE); // 设置对齐方式
            wcf_header.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE); //设置垂直对齐
            wcf_header.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN, jxl.format.Colour.BLACK);
            sheet.addCell(new Label(0, 0, header, wcf_header));

            //表头字体
            WritableFont wf_title = new WritableFont(WritableFont.ARIAL, 11,
                    WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE,
                    jxl.format.Colour.BLACK);
            WritableCellFormat wcf_title = new WritableCellFormat(wf_title); // 单元格定义
            wcf_title.setBackground(jxl.format.Colour.WHITE); // 设置单元格的背景颜色
            wcf_title.setAlignment(jxl.format.Alignment.CENTRE); // 设置对齐方式
            wcf_title.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE); //设置垂直对齐
            wcf_title.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN, jxl.format.Colour.BLACK);
            wcf_title.setWrap(true);
            /** 写入内容 */
            //写入表头
            for (int i = 0; i < titles.length; i++) {
                navCellView.setSize(size[i]);
                sheet.setColumnView(i, navCellView);
                sheet.addCell(new Label(i, 1, titles[i], wcf_title));
            }
            //写入数据内容
            for (int i = 0; i < datas.size(); i++) {
                List<String> row = datas.get(i);
                for (int j = 0; j < row.size(); j++) {
                    navCellView.setSize(size[j]);
                    sheet.setColumnView(j, navCellView);
                    sheet.addCell(new Label(j, i + 2, row.get(j)));
                }
            }
            //写入数据
            book.write();
            //关闭文件
            book.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * @param
     * @return
     * @Description: 订制两个sheet, 由于只有两个sheet，就不通用了。自己定制。
     * @Author: zhuhm@hsyuntai.com
     * @Date: 2016/11/30 14:54
     */
    public static void createXlsFile(HttpServletResponse response, String[] titles, String[] titles2, List<List<String>> datas, List<List<String>> datas2, int[] size, int[] size2) {

        try {
            //新建文件
            WritableWorkbook book = Workbook.createWorkbook(response.getOutputStream());

            //生成名为"第一页"的工作表，参数0表示这是第一页
            WritableSheet sheet = book.createSheet("第一页", 0);
            WritableSheet sheet2 = book.createSheet("第二页", 1);

            /*if (CollectionUtils.isNotEmpty(datas2)) {
                sheet2.mergeCells(0, datas2.size(), 2, datas2.size());
            }*/

            CellView navCellView = new CellView();
//			navCellView.setAutosize(true); //设置自动大小
            /** 写入内容 */
            //写入表头
            for (int i = 0; i < titles.length; i++) {
                navCellView.setSize(size[i]);
                sheet.setColumnView(i, navCellView);
                sheet.addCell(new Label(i, 0, titles[i]));
            }
            for (int i = 0; i < titles2.length; i++) {
                navCellView.setSize(size2[i]);
                sheet2.setColumnView(i, navCellView);
                sheet2.addCell(new Label(i, 0, titles2[i]));
            }

            //写入数据内容
            for (int i = 0; i < datas.size(); i++) {
                List<String> row = datas.get(i);

                for (int j = 0; j < row.size(); j++) {
                    navCellView.setSize(size[j]);
                    sheet.setColumnView(j, navCellView);
                    sheet.addCell(new Label(j, i + 1, row.get(j)));
                }
            }
            for (int i = 0; i < datas2.size(); i++) {
                List<String> row = datas2.get(i);

                for (int j = 0; j < row.size(); j++) {
                    navCellView.setSize(size2[j]);
                    sheet2.setColumnView(j, navCellView);
                    sheet2.addCell(new Label(j, i + 1, row.get(j)));
                }
            }
            //写入数据
            book.write();
            //关闭文件
            book.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 毛泽
     * 功能1 *两个标题 第一个可以合并单格 第二个为正常标题
     * 功能2 *异常记录变红
     * 支付总账单
     *
     * @param response
     * @param titles
     * @param datas
     * @param size
     * @param titlesHeader
     */
    public static void createXlsFile(HttpServletResponse response, List<String> titles, List<List<String>> datas, int[] size, List<Map<String, String>> titlesHeader,String sheetName) {

        try {
            //新建文件
            WritableWorkbook book = Workbook.createWorkbook(response.getOutputStream());

            //生成名为"第一页"的工作表，参数0表示这是第一页
            WritableSheet sheet = book.createSheet(sheetName, 0);

            CellView navCellView = new CellView();
            /*设置高度*/
//			navCellView.setSize(10000);
            WritableFont wf_title = new WritableFont(WritableFont.ARIAL, 11,
                    WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE,
                    jxl.format.Colour.BLACK);
            WritableCellFormat wcf_title = new WritableCellFormat(wf_title); // 单元格定义
            wcf_title.setBackground(jxl.format.Colour.WHITE); // 设置单元格的背景颜色
            wcf_title.setAlignment(jxl.format.Alignment.CENTRE); // 设置对齐方式
            wcf_title.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE); //设置垂直对齐
            wcf_title.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN, jxl.format.Colour.BLACK);
            wcf_title.setWrap(true);
//            字体
            WritableFont rd_title = new WritableFont(WritableFont.ARIAL, 11,
                    WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE,
                    jxl.format.Colour.RED);
//            单元格
            WritableCellFormat red_title = new WritableCellFormat(rd_title); // 单元格定义
            red_title.setBackground(jxl.format.Colour.WHITE); // 设置单元格的背景颜色
            red_title.setAlignment(jxl.format.Alignment.CENTRE); // 设置对齐方式
            red_title.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE); //设置垂直对齐
            red_title.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN, jxl.format.Colour.BLACK);
            red_title.setWrap(true);

            if (titlesHeader.size()!=0) {
                for (Map header : titlesHeader) {
                    sheet.mergeCells(Integer.parseInt(header.get("header").toString()), 0, Integer.parseInt(header.get("feet").toString()), 0);
                    sheet.setColumnView(Integer.parseInt(header.get("header").toString()), navCellView);
                    sheet.addCell(new Label(Integer.parseInt(header.get("header").toString()), 0, header.get("title").toString(), wcf_title));
                }
                //写入表头
                for (int i = 0; i < titles.size(); i++) {
                    navCellView.setSize(size[i]);
                    sheet.setColumnView(i, navCellView);
                    sheet.addCell(new Label(i, 1, titles.get(i), wcf_title));
                }
                //写入数据内容
                for (int i = 0; i < datas.size(); i++) {
                    List<String> row = datas.get(i);
                    for (int j = 0; j < row.size(); j++) {
                        navCellView.setSize(size[j]);
                        sheet.setColumnView(j, navCellView);
                        sheet.addCell(new Label(j, i + 2, row.get(j), wcf_title));

                    }
                }
            } else {
                //写入表头
                for (int i = 0; i < titles.size(); i++) {
                    navCellView.setSize(size[i]);
                    sheet.setColumnView(i, navCellView);
                    sheet.addCell(new Label(i,0, titles.get(i), wcf_title));
                }

                for (int i = 0; i < datas.size(); i++) {
                    List<String> row = datas.get(i);
                    for (int j = 0; j < row.size(); j++) {
                        navCellView.setSize(size[j]);
                        sheet.setColumnView(j, navCellView);
                            if(row.size() > 8 && row.get(j)!=null &&  row.get(9).equals("异常（未处理）")) {
                                sheet.addCell(new Label(j, i+1, row.get(j), red_title));
                            }else{
                                sheet.addCell(new Label(j, i+1, row.get(j), wcf_title));
                            }
                    }
                }
            }
            //写入数据
            book.write();
            //关闭文件
            book.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 为udb开放平台设计
     * maoze30070
     * 通用excel生成方法
     *
     * @param response
     * @param titles
     * @param datas
     */
    public static void createXlsFile(HttpServletResponse response, String[] titles, List<List<String>> datas, int[] size, String header) {

        try {
            //新建文件
            WritableWorkbook book = Workbook.createWorkbook(response.getOutputStream());

            //生成名为"第一页"的工作表，参数0表示这是第一页
            WritableSheet sheet = book.createSheet("第一页", 0);

            CellView navCellView = new CellView();
            navCellView.setSize(10000);
			/*合并单元格 （0，0）到（3，0）的点*/
            sheet.mergeCells(0, 0, 3, 0);
			/*合并单元格 （4，2）到（4，3）的点*/
            sheet.mergeCells(4, 2, 4, 3);
            sheet.setColumnView(0, navCellView);
            WritableFont wf_title = new WritableFont(WritableFont.ARIAL, 11,
                    WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE,
                    jxl.format.Colour.BLACK);
            WritableCellFormat wcf_title = new WritableCellFormat(wf_title); // 单元格定义
            wcf_title.setBackground(jxl.format.Colour.WHITE); // 设置单元格的背景颜色
            wcf_title.setAlignment(jxl.format.Alignment.CENTRE); // 设置对齐方式
            wcf_title.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE); //设置垂直对齐
            wcf_title.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN, jxl.format.Colour.BLACK);
            wcf_title.setWrap(true);
            sheet.setRowView(0, 650, false); //设置行高
			/*标题的字体*/
            WritableFont wf_header = new WritableFont(WritableFont.ARIAL, 23,
                    WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE,
                    jxl.format.Colour.BLACK);// 定义格式 字体 下划线 斜体 粗体 颜色
            WritableCellFormat wcf_header = new WritableCellFormat(wf_header); // 单元格定义
            wcf_header.setBackground(jxl.format.Colour.GRAY_50); // 设置单元格的背景颜色
            wcf_header.setAlignment(jxl.format.Alignment.CENTRE); // 设置对齐方式
            wcf_header.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE); //设置垂直对齐
            wcf_header.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN, jxl.format.Colour.BLACK);
            sheet.addCell(new Label(0, 0, header, wcf_header));


            WritableCellFormat head_title = new WritableCellFormat(wf_title); // 单元格定义
            head_title.setBackground(jxl.format.Colour.GRAY_25); // 设置单元格的背景颜色
            head_title.setAlignment(jxl.format.Alignment.CENTRE); // 设置对齐方式
            head_title.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE); //设置垂直对齐
            head_title.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN, jxl.format.Colour.BLACK);
            head_title.setWrap(true);
            sheet.setRowView(0, 800, false); //设置行高
            //写入表头
            for (int i = 0; i < titles.length; i++) {
                navCellView.setSize(size[i]);
                sheet.setColumnView(i, navCellView);
                sheet.addCell(new Label(i, 1, titles[i], head_title));
            }
            //写入数据内容

            for (int i = 0; i < datas.size(); i++) {
                List<String> row = datas.get(i);
                for (int j = 0; j < row.size(); j++) {
                    navCellView.setSize(size[j]);
                    sheet.setColumnView(j, navCellView);
                    if (i != 6 || i != 7) {
                        sheet.setRowView(i, 650, false);
                    }
                    sheet.addCell(new Label(j, i + 2, row.get(j), wcf_title));

                }
            }
            //写入数据
            book.write();
            //关闭文件
            book.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @Description: 满意度调查改造-excel生成
     * @Author: qintao@hsyuntai.com
     * @Date: 2017/4/21 16:05
     * @Copyright: 版权归 hsyuntai 所有
     */
    public static void createXlsFile(HttpServletResponse response,
                                     String[] titles, String[] titles2,
                                     List<List<String>> datas, List<List<String>> datas2,
                                     int[] size, int[] size2,
                                     Integer pageType) {
        try {
            //新建文件
            WritableWorkbook book = Workbook.createWorkbook(response.getOutputStream());
            CellView navCellView = new CellView();

            /** 第一页 */
            //生成名为"第一页"的工作表
            WritableSheet sheet = book.createSheet("第一页", 0);
            //写入表头
            if (titles != null && titles.length > 0) {
                for (int i = 0; i < titles.length; i++) {
                    navCellView.setSize(size[i]);
                    sheet.setColumnView(i, navCellView);
                    sheet.addCell(new Label(i, 0, titles[i]));
                }
            }
            //写入数据
            if (CollectionUtils.isNotEmpty(datas)) {
                for (int i = 0; i < datas.size(); i++) {
                    List<String> row = datas.get(i);

                    for (int j = 0; j < row.size(); j++) {
                        navCellView.setSize(size[j]);
                        sheet.setColumnView(j, navCellView);
                        sheet.addCell(new Label(j, i + 1, row.get(j)));
                    }
                }
            }

            /** 第二页 */
            WritableSheet sheet2 = book.createSheet("第二页", 1);
            //居中显示
            WritableCellFormat wFormat = new WritableCellFormat();
            wFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
            wFormat.setAlignment(Alignment.CENTRE);

            if (pageType == 1) {
                sheet2.mergeCells(0, datas2.size(), 4, datas2.size());
            }
            if (pageType == 3) {
                sheet2.mergeCells(0, datas2.size(), 3, datas2.size());
            }
            //写入表头
            if (titles2 != null && titles2.length > 0) {
                for (int i = 0; i < titles2.length; i++) {
                    navCellView.setSize(size2[i]);
                    sheet2.setColumnView(i, navCellView);
                    sheet2.addCell(new Label(i, 0, titles2[i], wFormat));
                }
            }
            //写入数据
            if (CollectionUtils.isNotEmpty(datas2)) {
                for (int i = 0; i < datas2.size(); i++) {
                    List<String> row = datas2.get(i);

                    for (int j = 0; j < row.size(); j++) {
                        navCellView.setSize(size2[j]);
                        sheet2.setColumnView(j, navCellView);
                        sheet2.addCell(new Label(j, i + 1, row.get(j), wFormat));
                    }
                }
            }
            //写入数据
            book.write();
            //关闭文件
            book.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        File file = new File("D:/aa.xls");
        String[] titles = {"角色", "编号", "功能名称", "功能描述"};

        List<List<String>> data = new ArrayList<List<String>>();
        for (int i = 0; i < 3; i++) {
            List<String> row1 = new ArrayList<String>();
            row1.add("uc11" + i);
            row1.add("设置课" + i);
            row1.add("adfin" + i);
            row1.add("wbvdf" + i);
            data.add(row1);
        }
        ExcelUtils.createXlsFile(file, titles, data);
        System.out.println("success");
    }

  /**
   * @Description: 做批量导出的execl文件(财务的定时任务)
   * @Return:
   * @Param:
   * @Author: maoze@hsyuntai.com
   * @Date: 2017/11/27 10:22
   */
    public static byte[] BatchDownLoad(List<Map<String,Object>>  listmap) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            //新建文件
            WritableWorkbook book = Workbook.createWorkbook(out);
             if(listmap.size()!=0)  {
               int sheetCount = 0;
               for (Map<String, Object> list : listmap) {
                   Object sheetName = list.get("sheetName");
                   WritableSheet sheet = book.createSheet(sheetName.toString(), sheetCount);
                   CellView navCellView = new CellView();
                   navCellView.setAutosize(true); //设置自动大小
                   String[] titles = (String[]) list.get("titles");
                   List<List<String>> datas = (List<List<String>>) list.get("datas");
                   int[] size = (int[]) list.get("rows");
                   /** 写入内容 */
                   //写入表头
                   for (int i = 0; i < titles.length; i++) {
                       navCellView.setSize(size[i]);
                       sheet.setColumnView(i, navCellView);
                       sheet.addCell(new Label(i, 0, titles[i]));
                   }
                   if(datas != null){
                   for (int i = 0; i < datas.size(); i++) {
                       List<String> row = datas.get(i);
                       for (int j = 0; j < row.size(); j++) {
                           sheet.setColumnView(j, navCellView);
                           sheet.addCell(new Label(j, i + 1, row.get(j)));
                       }
                   }}
                   sheetCount++;
               }
           }
            //写入数据
            book.write();
            //关闭文件
            book.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        out.close();
        return out.toByteArray();
    }
}
