package cn.xiongdi.ocdm.common.utils;

import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;

public class UploadUtils {
    /**
     * 文件上传
     * @param mfile
     * @param savePath 保存路径
     * @param flag 是否生成缩略图标识(true:生成,false:不生成)
     * @return
     */
    public static List<String> uploadFile(MultipartFile mfile, String savePath, boolean flag) {
        List<String> filePathList = new ArrayList<>();
        try {
            byte[] content = mfile.getBytes();
            String fileName = getFileName(mfile);
            //原始文件名
            filePathList.add(mfile.getOriginalFilename());
            //保存的文件名
            filePathList.add(fileName);
            //判断目录不存在,若不存在则创建新的文件夹
            if(savePath != null){
                File file = new File(savePath);
                if (!file.exists()) {
                    file.mkdirs();
                }
            }
            FileOutputStream outputStream = new FileOutputStream(savePath + fileName);
            outputStream.write(content);
            outputStream.close();
            //判断上传的是否为图片
            if(isImage(savePath + fileName)){
                if(flag) {
                    String thFileName = getFileName(mfile);
                    toSmaillImg(savePath + fileName, savePath + thFileName);
                    //缩略图文件名
                    filePathList.add(thFileName);
                }
            }
            return filePathList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return filePathList;
    }

    /**
     * 判断文件是否为图片
     * @param filePath 图片路径
     * @return true 是 | false 否
     */
    public static boolean isImage(String filePath){
        boolean flag = false;
        try
        {
            File file = new java.io.File(filePath);
            BufferedImage bufreader = ImageIO.read(file);
            int width = bufreader.getWidth();
            int height = bufreader.getHeight();
            if(width==0 || height==0){
                flag = false;
            }else {
                flag = true;
            }
        }
        catch (IOException e)
        {
            flag = false;
        }catch (Exception e) {
            flag = false;
        }
        return flag;
    }

    /**
     * 生成缩略图
     * @param filePath
     * @param thumbPath
     * @throws Exception
     */
    public static void toSmaillImg(String filePath,String thumbPath) throws Exception{
        String newurl =thumbPath;
        java.awt.Image bigJpg = javax.imageio.ImageIO.read(new java.io.File(filePath));
        float tagsize = 100;
        int old_w = bigJpg.getWidth(null);
        int old_h = bigJpg.getHeight(null);
        int new_w = 200;
        int new_h = 200;
        float tempdouble;
        tempdouble = old_w > old_h ? old_w/tagsize : old_h/tagsize;
        new_w = Math.round(old_w/tempdouble);
        new_h = Math.round(old_h/tempdouble);
        java.awt.image.BufferedImage tag = new java.awt.image.BufferedImage(new_w,new_h,java.awt.image.BufferedImage.TYPE_INT_RGB);
        tag.getGraphics().drawImage(bigJpg,0,0,new_w,new_h,null);
        FileOutputStream newimage = new FileOutputStream(newurl);
        com.sun.image.codec.jpeg.JPEGImageEncoder encoder = com.sun.image.codec.jpeg.JPEGCodec.createJPEGEncoder(newimage);
        encoder.encode(tag);
        newimage.close();
    }

    /**
     * 获取文件名
     * @param file
     * @return
     */
    private static String getFileName(MultipartFile file) {
        String guid = DateUtil.dateFormat(DateUtil.now(), "yyyyMMddHHmmss") + "-" + java.util.UUID.randomUUID().toString().replaceAll("-", "");
        String fileName = file.getOriginalFilename();
        String extName = fileName.substring(fileName.lastIndexOf("."));
        String fileNewName = guid + extName;
        String filePath = fileNewName;
        return filePath;
    }
}
