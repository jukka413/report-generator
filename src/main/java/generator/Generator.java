package generator;

import org.apache.commons.lang3.StringUtils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Generator {
    public static void main(String[] args) throws IOException {
        ReadXml.setFileName(args[0]);
        TsvReader.tsvRead(args[1]);
        printHeaders(args[2]);
        printSeparated(args[2]);
        printData(args[2],args[1]);
    }

    public static void printData(String filename, String data) throws IOException {
        int countLines = 2;
        Column column = new Column();
        Page page = new Page();
        OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(filename, true),"UTF-16");
        List<TsvReader> fromTsv1 = TsvReader.tsvRead(data);
        for (TsvReader tr : fromTsv1) {
            List<String> indexes = correctSplit(Integer.parseInt(column.width.get(0)), tr.getNumber());
            List<String> date = correctSplit(Integer.parseInt(column.width.get(1)), tr.getDate());
            List<String> names = correctSplit(Integer.parseInt(column.width.get(2)), tr.getName());
            for (int i=0;i<names.size();i++){
                try {
                    writer.write("| "+indexes.get(i)
                            +prinTablespace(indexes.get(i).length(),Integer.parseInt(column.width.get(0))+2,filename));
                    writer.flush();
                } catch (IndexOutOfBoundsException e){
                    writer.write("| "+StringUtils.repeat(" ",Integer.parseInt(column.width.get(0))+1));
                    writer.flush();
                }
                try {
                    writer.write("| "+date.get(i)
                            +prinTablespace(date.get(i).length(),Integer.parseInt(column.width.get(1))+2,filename));
                } catch (IndexOutOfBoundsException e){
                    writer.write("| "+StringUtils.repeat(" ",Integer.parseInt(column.width.get(1))+1));
                    writer.flush();
                }try {
                    writer.write("| "+names.get(i)
                            +prinTablespace(names.get(i).length(),Integer.parseInt(column.width.get(2))+1,filename)
                            +" |");
                    writer.flush();
                }catch (IndexOutOfBoundsException e) {
                }
                writer.append('\n');
                countLines++;
                if(countLines==page.height){
                    printPage(filename);
                }
            }
            writer.write("");
            writer.flush();
            printSeparated(filename);
            countLines++;
            if(countLines==page.height){
                printPage(filename);
            }
        }
    }

    public static String  prinTablespace(int l, int w, String filename) throws IOException {
        Column column = new Column();
        OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(filename, true),"UTF-16");
        String tblsp = StringUtils.repeat(" ",w-l-1);
        return tblsp;
    }

    public static void printHeaders(String filename) throws IOException {
        Column column = new Column();
        OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(filename, true),"UTF-16");
        for (int i=0; i<column.title.size(); i++) {
            writer.write("| "+column.title.get(i)+StringUtils.repeat(" ",Integer.parseInt(column.width.get(i))-column.title.get(i).length()+1));
            writer.flush();
        }
        writer.write("|");
        writer.append('\n');
        writer.flush();
    }
    public static void printSeparated(String filename) throws IOException {
        Page page = new Page();
        OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(filename, true),"UTF-16");
        writer.write(StringUtils.repeat("-",page.width));
        writer.append('\n');
        writer.flush();
    }
    public static void printPage(String filename) throws IOException{
        Page page = new Page();
        OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(filename, true),"UTF-16");
        writer.append('\n');
        writer.write("~");
        writer.append('\n');
        writer.flush();
        printHeaders(filename);
        writer.write(StringUtils.repeat("-",page.width));
        writer.flush();
    }

    private static ArrayList<String> correctSplit(int width, String s) {
        if (s.length() <= width) {
            return new ArrayList(Collections.singleton(s));
        }

        String[] sArray = s.split(" ");
        ArrayList<String> finalList = new ArrayList();



        for (String aSArray : sArray) {
            if (aSArray.length() <= width) {
                finalList.add(aSArray);
            } else {
                String[] newArray = aSArray.split("(?=[^A-Za-zа-яА-Я0-9'])");
                for (int j = 0; j < newArray.length; j++) {
                    if (newArray[j].length() <= width) {
                        finalList.add(newArray[j]);
                    } else {
                        while (newArray[j].length() > width) {
                            finalList.add(newArray[j].substring(0, newArray[j].length()/2));
                            newArray[j] = newArray[j].substring(newArray[j].length()/2, newArray[j].length());
                        }
                        finalList.add(newArray[j]);
                    }
                }
            }
        }

        for (int i = 0; i < finalList.size() - 1; i++) {
            if (finalList.get(i).length() + finalList.get(i + 1).length() <= width) {
                finalList.set(i, finalList.get(i) +" "+ finalList.get(i + 1));
                finalList.remove(i + 1);
            }
        }
        return finalList;
    }
}

