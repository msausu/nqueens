package usu.msa.pos.nqueens.nocolinear;

import org.testng.annotations.DataProvider;

public final class SolutionGenerator extends SampleGenerator {

    @DataProvider(name = "solutions", parallel = true)
    public static Object[][] unaligned() {
        return new Object[][]{
            new Object[]{ 10, 1,  1_000_000 },
            new Object[]{ 15, 1, 50_000_000 }
        };
    }

}
