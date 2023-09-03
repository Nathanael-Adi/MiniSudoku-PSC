/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minisudokuga;

/**
 *
 * @author CEN
 */
import java.util.*;
public class Chromosome {
    float fitness;
    Gene [] genes;
    
    Chromosome (int s, Triple [] puzzle){       
        genes = new Gene [s*s];
        // inisialisasi kromosom
        for (int i=0; i<genes.length;i++){
            genes[i] = new Gene(-1);
        }
        
        // untuk menampung angka-angka 
        Vector<Integer> vn = new Vector();
        
        // isi angka-angka sejumlah size
        for (int i=0; i<s; i++){
            for (int j=0; j<s; j++){
                vn.add(j+1);
            }        
        }

        // masukkan soal dari puzzle        
        for (int i=0; i<puzzle.length; i++){
            Triple t = puzzle[i];
            genes[t.row*s+t.column].setNumber(t.number);
            vn.remove((Integer) t.number);
        }

        // sisa angka yang tidak ada di puzzle diacak untuk dimasukkan ke kromosom
        List li = Arrays.asList(vn.toArray());
        Collections.shuffle(li);
        System.out.println("The list is: " + li); 
        int idx = 0;
        for (int i=0; i<genes.length;i++){
            if (genes[i].getNumber() == -1) {
                genes[i].setNumber((Integer) li.get(idx));
                idx++;
            }
        }
        System.out.println("The fitness is: " + fitness());
    }
    
    // mengembalikan berapa banyak duplikasi di baris
    int rowCheck() {
        int size = (int) Math.sqrt(genes.length); // Ukuran papan Sudoku
        int[][] board = toBoard(size); // Mendapatkan representasi papan Sudoku dari gen kromosom
        int rowDuplicates = 0;
    
        for (int i = 0; i < size; i++) {
            Set<Integer> rowSet = new HashSet<>();
            for (int j = 0; j < size; j++) {
                int cellValue = board[i][j];
                if (cellValue != -1) {
                    if (!rowSet.add(cellValue)) {
                        rowDuplicates++;
                    }
                }
            }
        }
        return rowDuplicates;
    }
    
    // mengembalikan berapa banyak duplikasi di kolom
    int colCheck() {
        int size = (int) Math.sqrt(genes.length); // Ukuran papan Sudoku
        int[][] board = toBoard(size); // Mendapatkan representasi papan Sudoku dari gen kromosom
        int colDuplicates = 0;
    
        for (int j = 0; j < size; j++) {
            Set<Integer> colSet = new HashSet<>();
            for (int i = 0; i < size; i++) {
                int cellValue = board[i][j];
                if (cellValue != -1) {
                    if (!colSet.add(cellValue)) {
                        colDuplicates++;
                    }
                }
            }
        }
        return colDuplicates;
    }
    
    // mengembalikan berapa banyak duplikasi di subblok
    int subCheck() {
        int size = (int) Math.sqrt(genes.length); // Ukuran papan Sudoku
        int subBlockSize = (int) Math.sqrt(size); // Ukuran subblok
        int[][] board = toBoard(size); // Mendapatkan representasi papan Sudoku dari gen kromosom
        int subBlockDuplicates = 0;
    
        for (int blockRow = 0; blockRow < subBlockSize; blockRow++) {
            for (int blockCol = 0; blockCol < subBlockSize; blockCol++) {
                Set<Integer> subBlockSet = new HashSet<>();
                for (int i = blockRow * subBlockSize; i < (blockRow + 1) * subBlockSize; i++) {
                    for (int j = blockCol * subBlockSize; j < (blockCol + 1) * subBlockSize; j++) {
                        int cellValue = board[i][j];
                        if (cellValue != -1) {
                            if (!subBlockSet.add(cellValue)) {
                                subBlockDuplicates++;
                            }
                        }
                    }
                }
            }
        }
        return subBlockDuplicates;
    }
    
    int fitness(){
        return rowCheck()+colCheck()+subCheck();
    }
    
    // mengembalikan representasi kromosom ke bentuk papan permainan
    int [][] toBoard(int size){
        int [][] b = new int [size][size];
        int idx = 0;
        for (int i=0; i<size;i++){
            for (int j=0; j<size;j++){
                b[i][j] = this.genes[idx].getNumber();
                idx++;
            }            
        }
        return b;
    }
 
    // menampilkan kromosom dalam bentuk papan permainan
    void printChromosome(int size){
        Sudoku s = new Sudoku(size, this.toBoard(size));
        s.printSudoku();
    }

    public Object getGene(int j) {
        return genes[j].getNumber();
    }
    
    public void setGene(int j, Object gene) {
        genes[j].setNumber((Integer) gene);
    }
    
    public void mutateGene(int mutationPoint) {
        // Implementasi mutasi sesuai dengan kebutuhan Anda, misalnya mengganti gen dengan nilai acak
        int newValue = (int) (Math.random() * genes.length) + 1;
        genes[mutationPoint].setNumber(newValue);
    }
}
