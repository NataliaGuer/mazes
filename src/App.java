
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import ds.graph.grid.Grid;
import ds.graph.grid.GridCell;
import ds.graph.tree.Node;
import ds.graph.tree.heap.BinaryMinHeap;
import mazes.generator.AldousBroder;
import mazes.generator.Eller;
import mazes.generator.Kruskal;
import mazes.generator.MazeGenerator;
import mazes.generator.Prim;
import mazes.generator.SideWinder;
import mazes.generator.Wilson;
import mazes.generator.WilsonTest;
import mazes.solver.AStarSolver;
import mazes.solver.DijkstraSolver;
import mazes.solver.MazeSolver;

public class App {
    public static void main(String[] args) throws Exception {
        mazesExamples();
    }

    public static void testDeadEnds() {
        // ogni algoritmo di generazione viene usato per creare 20 grafi, poi si conta
        // la media del numero di vicoli ciechi per ognuno
        // int sidewinder = 0;
        // int aldousBroder = 0;
        // int wilson = 0;
        // int huntAndKill = 0;

        // Grid g = null;
        // MazeGenerator mg = new aaa();

        // for (int i = 0; i < 20; i++) {
        //     g = mg.sidewinder(20, 20);
        //     g.braid(0.5);
        //     sidewinder += g.countDeadEnds();

        //     g = mg.aldousBroder(20, 20);
        //     g.braid(0.5);
        //     aldousBroder += g.countDeadEnds();

        //     g = mg.wilson(20, 20);
        //     g.braid(0.5);
        //     wilson += g.countDeadEnds();

        //     g = mg.huntAndKill(20, 20);
        //     g.braid(0.5);
        //     huntAndKill += g.countDeadEnds();
        // }

        // double sidewinderAvg = sidewinder / 20;
        // double aldousBroderAvg = aldousBroder / 20;
        // double wilsonAvg = wilson / 20;
        // double huntAndKillAvg = huntAndKill / 20;

        // String a = "sidewinder: " + sidewinderAvg + "/400\n" +
        //         "Aldous Broder: " + aldousBroderAvg + "/400\n" +
        //         "Wilson: " + wilsonAvg + "/400\n" +
        //         "Hunt and Kill: " + huntAndKillAvg + "/400\n";
        // System.out.println("\nMedia vicoli ciechi:\n" + a);
    }

    public static void gen() {
    }

    public static void solve(int d) {
        String common = "C:\\Users\\Natal\\Documents\\universit\u00E0\\algoritmi e strutture dati\\esame\\tesina\\mazes\\src\\test\\";
        MazeGenerator mg = new Kruskal();
        Grid g = mg.generate( d, d);
        String graphFilename = "graph"+d+".jpg";
        //g.toImage(common + "\\eller\\"+graphFilename);

        MazeSolver ms = new AStarSolver();
        GridCell start = g.getCellByCoordinates(0, 0);
        GridCell end = g.getCellByCoordinates(d-1, d-1);

        ms.solve(g, start, end);
        System.out.println("ciclo negativo");
        g.printPath(common + "\\kruskal\\solved\\bellmanford\\"+graphFilename, start, end);
    }

    public static void mazesExamples() {
        //creazione di labirinti di dimensione 25 per visualizzazione
        String fileName = "src\\data\\mazes\\prim.jpg";
        MazeGenerator mg = new Prim();
        int d = 25;
        Grid g = mg.generate(d, d);
        g.toImage(fileName);
    }

    public static void test() {

        MazeGenerator mg = new SideWinder();
        MazeSolver ms = new AStarSolver();
        
        File file = new File("src\\data\\mazesolving\\astar.txt");
        FileWriter fw = null;
        BufferedWriter bw = null;
        try {
            fw = new FileWriter(file, true);
            bw = new BufferedWriter(fw);
            for (int d = 25; d <= 150; d += 25) {
                
                Grid g = mg.generate(d, d);            
                GridCell start = g.getCellByCoordinates(0, 0);
                GridCell end = g.getCellByCoordinates(d-1, d-1);
        
                long total = 0;
        
                for (int i = 0; i < 100; i ++) {
                    g.clean();
                    long tStart = System.currentTimeMillis();
                    ms.solve(g, start, end);
                    long tEnd = System.currentTimeMillis();
        
                    long tDelta = tEnd - tStart;
                    total += tDelta;
                }
        
                long tMedium = total/100;
                
                bw.write("\ndimensione: "+ d + "\n");
                bw.write("tempo totale: "+ total + "\n");
                bw.write("tempo medio: "+ tMedium + "\n");
            }

            bw.close();
            fw.close();
        } catch (Exception e) {
            // TODO: handle exception
        }
        
    }

    public static void testGeneration() {
        //per ogni algoritmo di generazione vanno testati i tempi di generazione di labirinti di dimensioni:

        MazeGenerator mg = new Eller();
    
        File file = new File("src\\data\\mazegeneration\\eller.txt");
        FileWriter fw = null;
        BufferedWriter bw = null;
        try {
            fw = new FileWriter(file, true);
            bw = new BufferedWriter(fw);

            for (int d = 25; d <= 150; d += 25) {
                long total = 0;
                for (int i = 0; i < 100; i++) {
                    long tStart = System.currentTimeMillis();
                    mg.generate(d, d);
                    long tEnd = System.currentTimeMillis();
        
                    long delta = tEnd - tStart;
                    total += delta;
                }
        
                long tMedium = total/100;
        
                bw.write("\ndimensione: "+ d + "\n");
                bw.write("tempo totale: "+ total + "\n");
                bw.write("tempo medio: "+ tMedium + "\n");
            }
            
            bw.close();
            fw.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void testBinaryHeap() {
        BinaryMinHeap bmh = new BinaryMinHeap();
        Node a = new Node("a");
        a.setPriority(1);
        Node b = new Node("b");
        b.setPriority(3);
        Node c = new Node("c");
        c.setPriority(4);
        Node d = new Node("d");
        d.setPriority(5);
        Node e = new Node("e");
        e.setPriority(6);

        bmh.insert(a);
        bmh.insert(b);
        bmh.insert(c);
        bmh.insert(d);
        bmh.insert(e);

        System.out.println(bmh);

        //bmh.swapNodes(a, b);

        bmh.extract();

        System.out.println(bmh);
    }
}
