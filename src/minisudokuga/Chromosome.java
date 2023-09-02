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
    }
    
    // mengembalikan berapa banyak duplikasi di baris
    int rowCheck(){
        return 0;
    }
    
    // mengembalikan berapa banyak duplikasi di kolom
    int colCheck(){
        return 0;
    }
    
    // mengembalikan berapa banyak duplikasi di subblok
    int subCheck(){
        return 0;
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
    
    public void calculateFitness() {
        // Implementasikan logika perhitungan fitness sesuai dengan aturan Sudoku
        // Anda dapat menggunakan logika yang telah Anda terapkan dalam metode fitness() sebelumnya.
        // Perhitungan fitness dapat menjadi perbandingan antara jumlah duplikasi di baris, kolom, dan subblok.
        int rowDuplicates = rowCheck();
        int colDuplicates = colCheck();
        int subBlockDuplicates = subCheck();
        
        // Misalnya, kita dapat menghitung total fitness sebagai jumlah duplikasi di semua area Sudoku.
        fitness = rowDuplicates + colDuplicates + subBlockDuplicates;
    }
    
    public float getFitness() {
        return fitness;
    }
}
