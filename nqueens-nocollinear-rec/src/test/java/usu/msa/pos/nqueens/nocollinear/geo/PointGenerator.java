package usu.msa.pos.nqueens.nocollinear.geo;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.testng.annotations.DataProvider;

public final class PointGenerator {

    private static final Logger LOG = Logger.getAnonymousLogger();
    public static final String S_5X5_3P_ALIGNED = "3p samples aligned in 5x5",
            S_5X5_4P_ALIGNED = "4p samples aligned in 5x5", 
            S_5X5_3P_UNALIGNED = "3p samples unaligned in 5x5",
            S_5X5_4P_UNALIGNED = "4p samples unaligned in 5x5"
            ; 

    @DataProvider(name = S_5X5_3P_ALIGNED, parallel = true)
    public static Object[][] aligned() {
        return getSamples("5x5-3p-aligned.csv");
    }

    @DataProvider(name = S_5X5_3P_UNALIGNED, parallel = true)
    public static Object[][] unaligned3p() {
        return getSamples("5x5-3p-unaligned.csv");
    }
    
    @DataProvider(name = S_5X5_4P_UNALIGNED, parallel = true)
    public static Object[][] unaligned4p() {
        return getSamples("5x5-4p-unaligned.csv.xz");
    }

    @DataProvider(name = S_5X5_4P_ALIGNED, parallel = true)
    public static Object[][] aligned4p() {
        return getSamples("5x5-4p-aligned.csv.xz");
    }
    
    private static Object[][] getSamples(String samples) {
        String file = System.getProperty("samples_dir") + samples;
        LOG.log(Level.INFO, "sample file: {0}", file);
        return new SampleReader(file).get();
    }

}
