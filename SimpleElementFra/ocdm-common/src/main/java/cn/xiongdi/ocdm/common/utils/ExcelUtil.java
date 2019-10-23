package cn.xiongdi.ocdm.common.utils;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * 描述：Excel导出工具类
 *
 * @Author NEC  2018/5/28
 */
public class ExcelUtil {

    public static String ALIGN_FEFT = "left";

    public static String ALIGN_CENTER = "center";

    public static String ALIGN_RIGHT = "right";


    /**
     * 输出Excel文件
     *
     * @param response
     * @param workbook
     * @param fileName
     * @throws IOException
     */
    public static void outPutExcel(HttpServletResponse response, HSSFWorkbook workbook, String fileName) {
        if (fileName == "") {
            fileName = DateUtil.now().getTime() + ".xls";
        }
        //转化为字节流：
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            workbook.write(os);
            byte[] bytes = os.toByteArray();
            response.setContentType("application/msexcel;charset=utf-8");
            response.setHeader("Content-disposition", "attachment;filename= " + new String(fileName.getBytes("GBK"), "ISO-8859-1"));
            ServletOutputStream sos = response.getOutputStream();
            sos.write(bytes);
            sos.flush();
            sos.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 获取单元格样式
     *
     * @param workbook
     * @param alignment 居中 居左 居右
     * @param isWrap    是否自动换行
     * @param font      字体
     * @param color     背景色
     * @return
     */
    public static HSSFCellStyle getCellStyle(HSSFWorkbook workbook, String alignment, boolean isWrap, Font font, Short color) {
        HSSFCellStyle style = workbook.createCellStyle();
        //细线边框
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        //垂直居中
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        //水平居中、居左或居右
        if (alignment != "") {
            if (ALIGN_FEFT.equals(alignment)) {
                style.setAlignment(HorizontalAlignment.LEFT);
            } else if (ALIGN_RIGHT.equals(alignment)) {
                style.setAlignment(HorizontalAlignment.RIGHT);
            } else if (ALIGN_CENTER.equals(alignment)) {
                style.setAlignment(HorizontalAlignment.CENTER);
            }
        }

        //是否自动换行
        style.setWrapText(isWrap);
        //设置字体
        style.setFont(font);
        //添加背景色
        if (color != null) {
            style.setFillForegroundColor(color);
            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        }
        return style;
    }


    /**
     * 设置字体
     *
     * @param workbook
     * @param fontName 字体名称
     * @param fontSize 字体大小
     * @param bold     是否加粗
     * @param color    字体颜色
     * @return
     */
    public static HSSFFont setFont(HSSFWorkbook workbook, String fontName, short fontSize, boolean bold, Short color) {
        HSSFFont font = workbook.createFont();
        font.setFontName(fontName);
        font.setFontHeightInPoints(fontSize);
        //是否加粗
        font.setBold(bold);
        if (color != null) {
            font.setColor(color);
        }
        return font;
    }

    /**
     * 设置单元格为文本
     *
     * @param workbook
     * @param xSSFCellStyle
     * @return
     */
    public static HSSFCellStyle setTxtStyle(HSSFWorkbook workbook, HSSFCellStyle xSSFCellStyle) {
        HSSFCellStyle txtStyle = workbook.createCellStyle();
        txtStyle.cloneStyleFrom(xSSFCellStyle);
        HSSFDataFormat format = workbook.createDataFormat();
        txtStyle.setDataFormat(format.getFormat("@"));
        return txtStyle;
    }

}
