package cn.xiongdi.ocdm.common.utils;

import java.io.*;

public class FileUtil {

    public static String readFile(String fileName) {
        String fileContent = "";

        try {
            File f = new File(fileName);
            if (f.isFile() && f.exists()) {
                InputStreamReader read = new InputStreamReader(new FileInputStream(f), "gbk");

                String line;
                for(BufferedReader reader = new BufferedReader(read); (line = reader.readLine()) != null; fileContent = fileContent + line) {
                    ;
                }


                read.close();
            }
        } catch (Exception var6) {
            var6.printStackTrace();
        }

        return fileContent;
    }

    public static String readFileHasDivision(String fileName,String character) {
        String fileContent = "";

        try {
            File f = new File(fileName);
            if (f.isFile() && f.exists()) {
                InputStreamReader read = new InputStreamReader(new FileInputStream(f), "gbk");

                String line;
                for(BufferedReader reader = new BufferedReader(read); (line = reader.readLine()) != null; fileContent = fileContent + character + line) {
                    ;
                }


                read.close();
            }
        } catch (Exception var6) {
            var6.printStackTrace();
        }

        return fileContent;
    }

    public static String readFileToutf8(String fileName) {
        String fileContent = "";

        try {
            File f = new File(fileName);
            if (f.isFile() && f.exists()) {
                InputStreamReader read = new InputStreamReader(new FileInputStream(f), "utf-8");

                String line;
                for(BufferedReader reader = new BufferedReader(read); (line = reader.readLine()) != null; fileContent = fileContent + line) {
                    ;
                }

                read.close();
            }
        } catch (Exception var6) {
            var6.printStackTrace();
        }

        return fileContent;
    }

    public static boolean deleteServerFile(String fileName) {
        boolean delete_flag = false;
        File file = new File(fileName);
        if (file.exists() && file.isFile() && file.delete()) {
            delete_flag = true;
        } else {
            delete_flag = false;
        }

        return delete_flag;
    }

    public static void string2Txt(File file, String value) {
        try {
            FileOutputStream outSTr = null;
            BufferedOutputStream Buff = null;
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }

            if (!file.exists()) {
                file.createNewFile();
            }

            outSTr = new FileOutputStream(file);
            Buff = new BufferedOutputStream(outSTr);
            Buff.write(value.getBytes());
            Buff.flush();
            Buff.close();
        } catch (IOException var4) {
            var4.printStackTrace();
        }

    }

    public static String[] selectFile(String filePath, final String startFile) {
        File f = new File(filePath);
        String[] files = f.list(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.startsWith(startFile);
            }
        });
        return files;
    }
}
