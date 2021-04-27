
import usu.msa.pos.nqueens.nocolinear.Board;
import usu.msa.pos.nqueens.nocolinear.NQueensNoCollinear;
import static usu.msa.pos.nqueens.nocolinear.NQueensNoCollinear.help;

/**
 *
 * @author msa
 */
public class App {

    public static void main(String[] args) {
        if (args.length == 0) {
            help();
        }
        NQueensNoCollinear nq = new NQueensNoCollinear(Board.boardSize(args[0]));
        System.out.print(nq.toString());
    }
}
