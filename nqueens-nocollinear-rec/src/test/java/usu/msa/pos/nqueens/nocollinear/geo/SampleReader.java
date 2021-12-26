package usu.msa.pos.nqueens.nocollinear.geo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.apache.commons.compress.compressors.xz.XZCompressorInputStream;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

/**
 *
 * @author msa
 */
public class SampleReader implements AutoCloseable {

    private final CSVParser parser;
    private BufferedReader reader;

    public SampleReader(String file) {
        try {
            Path csv = Paths.get(URI.create("file://" + file));
            if (file.endsWith(".xz")) {
                InputStream is = Files.newInputStream(csv);
                reader = new BufferedReader(new InputStreamReader(new XZCompressorInputStream(is)));
            } else {
                reader = Files.newBufferedReader(csv);
            }
            parser = new CSVParser(reader, CSVFormat.DEFAULT);
        } catch (IOException ex) {
            throw new RuntimeException("cannot read sample file " + ex.getMessage());
        }
    }

    public Object[][] get() {
        try {
            List<Object[]> res = new ArrayList<>();
            for (CSVRecord record : parser.getRecords()) {
                List<Point> points = new ArrayList<>();
                for (int i = 0; i < record.size(); i += 2) {
                    points.add(new Point(Integer.parseInt(record.get(i)), Integer.parseInt(record.get(i + 1))));
                }
                res.add(new Object[]{ points });
            }
            return res.toArray(Object[][]::new); 
        } catch (IOException ex) {
            throw new RuntimeException("error reading sample file " + ex.getMessage());
        }
    }

    @Override
    public void close() throws Exception {
        if (reader != null) {
            reader.close();
        }
    }
}
