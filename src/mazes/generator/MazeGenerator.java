package mazes.generator;

import java.util.Random;

import ds.graph.grid.Grid;

public abstract class MazeGenerator{

    protected Random rnd;
    
    public MazeGenerator() {
        this.rnd = new Random();
    }

    public abstract Grid generate(int rows, int columns);
}
