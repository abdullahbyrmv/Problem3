import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Main {
    public static void main(String[] args) throws IOException {
        File dest = new File("dest");
        dest.mkdir();
        for (String argument : args) {
            File resource = new File(argument);
            File destination = new File("dest/" + resource.getName());

            if (!resource.exists()) {
                System.out.println("The Mentioned Directory Does Not Exist: " + resource);
            }
            else if (resource.isDirectory()) {
                file_checker(resource, destination);
            }
            else if (resource.isFile()) {
                fileCopier(resource, destination);
            }
        }
    }

    public static void file_checker(File source, File destination) throws IOException {

        if (source.isDirectory()) {
            if (!destination.exists()) {
                destination.mkdir();
            }
            String[] collection_of_files = source.list();

            if (collection_of_files != null) {
                for (int i = 0; i < collection_of_files.length; i++) {
                    String childfile = collection_of_files[i];
                    File srcFile = new File(source, childfile);
                    File destFile = new File(destination, childfile);
                    file_checker(srcFile, destFile);
                }
            }
        }
        else if (!source.isDirectory()) {
            fileCopier(source, destination);
        }
    }

    public static void fileCopier(File source, File destination) throws IOException {
        try {
            InputStream input = new FileInputStream(source);
            OutputStream output = new FileOutputStream(destination);
            byte[] buffer = new byte[4096];

            int length;
            while ((length = input.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        System.out.println(">Started:" + source);
        System.out.println(">Finished FILE:" + source);
        if(source.length() < 1024){
            long size = (long)(Math.ceil(source.length()));
            System.out.println(">Total "+ size + " B were copied!");
        }
        else if (source.length() >= 1_048_576 && source.length() < 1_073_741_824) {
            long size = (long)(Math.ceil(source.length()/1024.00/1024.00));
            System.out.println(">Total " + size + " MB were copied.");
        }
        else {
            long size = (long)(Math.ceil(source.length() / 1024.00));
            System.out.println(">Total " + size + " KB were copied!");
        }
    }
}
