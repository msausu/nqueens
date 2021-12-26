package usu.msa.pos.nqueens.nocollinear.geo;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.testng.annotations.DataProvider;

public final class SampleGenerator {

    private static final Logger LOG = Logger.getAnonymousLogger();
    public static final String OEIS_A000170 = "OEIS A000170",
            S_5X5_3P_ALIGNED = "3p samples aligned in 5x5",
            S_5X5_4P_ALIGNED = "4p samples aligned in 5x5", 
            S_5X5_3P_UNALIGNED = "3p samples unaligned in 5x5",
            S_5X5_4P_UNALIGNED = "4p samples unaligned in 5x5"
            ; 
    
    @DataProvider(name = OEIS_A000170, parallel = true)
    public static Object[][] oeisA00170() {
        return new Object[][] {
            { 2, 0 },
            { 3, 0 }, 
            { 4, 2 }, 
            { 5, 10 }, 
            { 6, 4 } , 
            { 7, 40 }, 
            { 8, 92 }, 
            { 9, 352 }, 
            { 10, 724 }, 
            { 11, 2680 }, 
            { 12, 14200 }, 
            { 13, 73712 }, 
            { 14, 365596 }
        };
    }
    
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
