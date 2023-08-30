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
public class GeneticAlgorithm {
   int size;
   Triple [] puzzle;
   double probCO;
   double probM;
   int popSize;
   int maxIter;
   Chromosome [] population;
   int best;
   
   // konstruktor
   GeneticAlgorithm(int s, Triple [] p, double pco, double pm, int ps, int mi){
       size = s;
       puzzle = p;
       probCO = pco;
       probM = 0;
       popSize = ps;
       maxIter = mi;
   }
   
   // memilih kromosom yang akan dikenai operasi crossover/mutasi
   int selection(){
      return -1; 
   }
   
   // operasi crossover, pilih teknik yang digunakan 
   // dua kromosom anak menggantikan kromosom induk ini
   void crossover(){       
   }
   
   // operasi mutasi, pilih teknik yang digunakan
   // pilih satu kromosom untuk dikenai mutasi
   // lakukan perubahan pada kromosom tersebut
   void mutation(){       
   }
   
   // menghitung fitness dari setiap kromosom
   // mencatat kromosom terbaik
   void checkFitness(){       
   }
   
   // pembangkitan populasi awal, generate kromosom sebanyak popSize
   void init(){
       population = new Chromosome[popSize] ;
       for (int i=0; i<popSize; i++){
           population[i] = new Chromosome(size, puzzle);
           population[i].printChromosome(size);
       }
   }
   
   // iterasi
   void iteration(){
       init();
       checkFitness();
       int iter = 0;
       while (!termination(iter)){
           crossover();
           mutation();
//           restChromosomes();
           checkFitness();
           iter++;
       }
   }
   
   // kondisi berhenti
   boolean termination(int iter){
        return ((iter == maxIter) || (population[best].fitness == 0));
   }
}
