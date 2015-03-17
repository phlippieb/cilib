/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package simgen.function;

/**
 *
 * @author phlippieb
 */
public class GoldsteinPrice extends FunctionWithFixedDimensions {

    @Override
    void setName() {
        this.name = "goldsteinprice";
    }

    @Override
    void setDomains() {
        this.domainMin = -2;
        this.domainMax = 2;
    }
    
    @Override
    void setDimensions() {
        this.dimensions = 2;
    }

    @Override
    void setDefinition() {
        this.definition = "<function class=\"functions.continuous.unconstrained.GoldsteinPrice\" />";
    }

    
    
    
    
    
}
