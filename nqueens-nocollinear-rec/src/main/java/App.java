
import usu.msa.pos.nqueens.nocollinear.Board;
import usu.msa.pos.nqueens.nocollinear.NQueensNoCollinear;
import static usu.msa.pos.nqueens.nocollinear.NQueensNoCollinear.help;

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
        System.out.print(args.length > 1 && args[1].matches("^[pP]") ? nq.print() : nq.toString());
    }
}
