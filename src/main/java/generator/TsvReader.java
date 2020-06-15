package generator;

import com.univocity.parsers.tsv.TsvParser;
import com.univocity.parsers.tsv.TsvParserSettings;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class TsvReader {

    private String number;
    private String date;
    private String name;

    private TsvReader(String number, String date, String name) {
        this.number = number;
        this.date = date;
        this.name = name;
    }

    public static List<TsvReader> tsvRead(String filename) {
        List<TsvReader> fromTsv = new ArrayList<>();

        TsvParserSettings settings = new TsvParserSettings();
        settings.getFormat().setLineSeparator("\n");

        // creates a TSV parser
        TsvParser parser = new TsvParser(settings);

        // parses all rows in one go.
        List<String[]> allRows = parser.parseAll(new File(filename));
        for (String[] allRow : allRows) {
            fromTsv.add(new TsvReader(allRow[0], allRow[1], allRow[2]));
            //System.out.println(fromTsv);
        }
        return fromTsv;

    }
    public String getNumber() {
        return number;
    }

    public String getDate() {
        return date;
    }

    public String getName() {
        return name;
    }
}