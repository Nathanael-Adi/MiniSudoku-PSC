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
    for (int i = 0; i < popSize; i += 2) {
        if (Math.random() < probCO) {
            // Pilih dua kromosom induk secara acak
            int parent1Index = (int) (Math.random() * popSize);
            int parent2Index;
            do {
                parent2Index = (int) (Math.random() * popSize);
            } while (parent2Index == parent1Index);

            Chromosome parent1 = population[parent1Index];
            Chromosome parent2 = population[parent2Index];

            // Pilih titik pemotongan (crossover point) secara acak
            int crossoverPoint = (int) (Math.random() * (size - 1)) + 1;

            // Buat dua kromosom anak
            Chromosome child1 = new Chromosome(size, puzzle);
            Chromosome child2 = new Chromosome(size, puzzle);

            // Lakukan crossover pada titik pemotongan
            for (int j = 0; j < size; j++) {
                if (j < crossoverPoint) {
                    child1.setGene(j, parent1.getGene(j));
                    child2.setGene(j, parent2.getGene(j));
                } else {
                    child1.setGene(j, parent2.getGene(j));
                    child2.setGene(j, parent1.getGene(j));
                }
            }

            // Gantikan kromosom induk dengan anak
            population[i] = child1;
            population[i + 1] = child2;
        }
    }
   }
   
   // operasi mutasi, pilih teknik yang digunakan
   // pilih satu kromosom untuk dikenai mutasi
   // lakukan perubahan pada kromosom tersebut
   void mutation(){ 
    for (int i = 0; i < popSize; i++) {
        if (Math.random() < probM) {
            // Pilih satu kromosom secara acak
            Chromosome chromosome = population[i];

            // Pilih satu gen dalam kromosom untuk dimutasi secara acak
            int mutationPoint = (int) (Math.random() * size);

            // Lakukan mutasi pada gen yang dipilih
            chromosome.mutateGene(mutationPoint);
        }
    }      
   }
   
   // menghitung fitness dari setiap kromosom
   // mencatat kromosom terbaik
   void checkFitness(){ 
    float currentBestFitness = Integer.MAX_VALUE;
    int currentBestIndex = -1;

    for (int i = 0; i < popSize; i++) {
        Chromosome chromosome = population[i];
        chromosome.calculateFitness();

        // Periksa apakah kromosom saat ini memiliki fitness yang lebih baik dari yang terbaik
        if (chromosome.getFitness() < currentBestFitness) {
            currentBestFitness = chromosome.getFitness();
            currentBestIndex = i;
        }
    }

    // Catat kromosom terbaik
    best = currentBestIndex;      
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
