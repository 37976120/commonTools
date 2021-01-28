package tools.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

public class CopyFiles {

    public static void copyFiles(File file) throws Exception {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (int i = 0; i < files.length; i++) {
                File one = files[i];
                copyFiles(one);
            }
        }
        if (file.isDirectory()) {
            return;
        }
        File newFile = newFileName(file);

        FileInputStream fileInputStream = new FileInputStream(file);
        FileChannel srcChannel = fileInputStream.getChannel();
        FileOutputStream fileOutputStream = new FileOutputStream(newFile);
        FileChannel tarChannel = fileOutputStream.getChannel();
        tarChannel.transferFrom(srcChannel, 0, srcChannel.size());
        fileInputStream.close();
        fileOutputStream.close();
    }

    private static File newFileName(File file) {
        int j = 1;
        String absolutePath = file.getAbsolutePath();
        int i = absolutePath.lastIndexOf(".");
        String dotSub = absolutePath.substring(i);
        File newFile = null;
        while (true) {
            String replace = absolutePath.replace(dotSub, "(" + j + ")" + dotSub);
            newFile = new File(replace);
            if (!newFile.exists()) {
                break;
            }
            j++;
        }
        return newFile;
    }
}
