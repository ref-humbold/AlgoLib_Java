// ALGORYTM BFS DLA TABLICY
package ref_humbold.algolib;

import java.lang.Comparable;
import java.util.Deque;
import java.util.ArrayDeque;

class Table
{
    /** Oznaczenie pustej komórki. */
    private static Integer EMPTY_CELL = null;

    /** Liczba wierszy. */
    private int numRows;

    /** Liczba kolumn. */
    private int numColumns;

    /** Tablica. */
    private Integer[][] table;

    class Cell
        implements Comparable <Cell>
    {
        public int x;
        public int y;

        public Cell(int x, int y)
        {
            this.x = x;
            this.y = y;
        }

        public int compareTo(Cell c)
        {
            if(this.x == c.x)
            {
                if(this.y == c.y)
                    return 0;
                else
                    return this.y < c.y ? -1 : 1;
            }
            else
                return this.x < c.x ? -1 : 1;
        }

        public boolean equals(Cell c)
        {
            return this.x == c.x && this.y == c.y;
        }
    }

    public Table(int n, int m)
    {
        numRows = n;
        numColumns = m;
        table = new Integer[n][m];

        for(int i = 0; i < n; ++i)
            for(int j = 0; j < m; ++j)
                table[i][j] = EMPTY_CELL;
    }

    /**
    Algorytm BFS.
    @param sourceX współrzędna X początkowej komórki
    @param sourceY współrzędna Y początkowej komórki
    */
    public void bfs(int sourceX, int sourceY)
    {
        Deque<Cell> cellQueue = new ArrayDeque<Cell>();

        table[sourceX][sourceY] = 0;
        cellQueue.addLast( new Cell(sourceX, sourceY) );

        while(!cellQueue.isEmpty())
        {
            Cell cell = cellQueue.removeFirst();

            if(cell.x > 0 && table[cell.x-1][cell.y] == EMPTY_CELL)
            {
                table[cell.x-1][cell.y] = table[cell.x][cell.y]+1;
                cellQueue.addLast( new Cell(cell.x-1, cell.y) );
            }

            if(cell.x < numRows-1 && table[cell.x+1][cell.y] == EMPTY_CELL)
            {
                table[cell.x+1][cell.y] = table[cell.x][cell.y]+1;
                cellQueue.addLast( new Cell(cell.x+1, cell.y) );
            }

            if(cell.y > 0 && table[cell.x][cell.y-1] == EMPTY_CELL)
            {
                table[cell.x][cell.y-1] = table[cell.x][cell.y]+1;
                cellQueue.addLast( new Cell(cell.x, cell.y-1) );
            }

            if(cell.y < numColumns-1 && table[cell.x][cell.y+1] == EMPTY_CELL)
            {
                table[cell.x][cell.y+1] = table[cell.x][cell.y]+1;
                cellQueue.addLast( new Cell(cell.x, cell.y+1) );
            }
        }
    }
}

