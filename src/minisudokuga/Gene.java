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
public class Gene {
    private int number;

    public Gene(int n) {
        number = n;
    }

    public void setNumber(int i) {
        number = i;
    }

    public int getNumber() {
        return number;
    }
}
