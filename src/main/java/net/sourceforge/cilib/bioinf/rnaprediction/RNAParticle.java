/*
 * RNAParticle.java
 *
 * Created on 2005/05/19
 *
 * Copyright (C) 2003, 2005 - CIRG@UP
 * Computational Intelligence Research Group (CIRG@UP)
 * Department of Computer Science
 * University of Pretoria
 * South Africa
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */
package net.sourceforge.cilib.bioinf.rnaprediction;

import net.sourceforge.cilib.entity.Particle;
import net.sourceforge.cilib.problem.Fitness;
import net.sourceforge.cilib.problem.InferiorFitness;
import net.sourceforge.cilib.problem.OptimisationProblem;
import net.sourceforge.cilib.pso.PSO;
import net.sourceforge.cilib.pso.particle.AbstractParticle;
import net.sourceforge.cilib.type.types.Type;
import net.sourceforge.cilib.type.types.container.Vector;

/**
 * @author marais
 *
 */
public class RNAParticle extends AbstractParticle {
	private static final long serialVersionUID = -8232081489398782692L;
	private RNAFolder folder;
	
	private RNAConformation position;
    private RNAConformation bestPosition;
    private Vector velocity;

    private Fitness bestFitness;

    private Particle neighbourhoodBest;
    
    private RNAFitness fitnessCalc;

	/**
	 * Create a new {@link RNAParticle} instance.
	 */
	public RNAParticle() {
		position = new RNAConformation();
		bestPosition = new RNAConformation();
		velocity = new Vector();
	}
	
	/**
	 * Copy constructor. Create a copy of the provided instance.
	 * @param copy The instance to copy.
	 */
	public RNAParticle(RNAParticle copy) {
		this.position = new RNAConformation();
		this.velocity = copy.velocity.getClone();
		this.bestPosition = new RNAConformation();
		
		for (RNAStem stem : this.position)
			this.position.add(stem.getClone());
		
		for (RNAStem stem : this.bestPosition)
			this.bestPosition.add(stem.getClone());
	}
	
	/**
	 * {@inheritDoc}
	 */
	public RNAParticle getClone() {
		/*RNAParticle clone = new RNAParticle();// super.clone();
       
		clone.position = new RNAConformation();
		clone.velocity = (MixedVector) this.velocity.clone();
		clone.bestPosition = new RNAConformation();
		
		for (Iterator<RNAStem> it = this.position.iterator(); it.hasNext(); )
			clone.position.add((RNAStem) it.next().clone());
		
		for (Iterator<RNAStem> it = this.bestPosition.iterator(); it.hasNext(); )
			clone.bestPosition.add((RNAStem) it.next().clone());
		
		return clone;*/
		
		return new RNAParticle(this);
	}
	
	/**
	 * Explicitly set the current fitness value.
	 * @param fitness The {@linkplain Fitness} to set.
	 */
	public void setFitness(Fitness fitness) {
		this.getProperties().put("fitness", fitness);
        if (fitness.compareTo(bestFitness) > 0) {
            bestFitness = fitness;
	
           bestPosition.clear();
           bestPosition.addAll(position);
        }
	}

	/**
	 * {@inheritDoc}
	 */
	public Fitness getBestFitness() {
		return bestFitness;
	}

	/**
	 * {@inheritDoc}
	 */
	public int getDimension() {
		return position.getDimension();
	}

	/**
	 * {@inheritDoc}
	 */
	public void initialise(OptimisationProblem problem) {
		RNAInitialiser i = new RNAInitialiser();
		this.setId(String.valueOf(PSO.getNextParticleId()));
		
		position = (RNAConformation) i.getInitialPosition(problem);
		bestPosition.clear();
		bestPosition.addAll(position);
		velocity = (Vector) i.getInitialVelocity(problem);
        bestFitness = InferiorFitness.instance();
        neighbourhoodBest = this;
        //fitnessCalc = new SimpleRNAFitness();
        folder = new GreedyRNAFolder(fitnessCalc);
	}

	/**
	 * {@inheritDoc}
	 */
	public Type getPosition() {	
		return position;
	}

	/**
	 * {@inheritDoc}
	 */
	public Type getBestPosition() {
		return bestPosition;
	}
	
	/**
	 * Set the best position for the current {@linkplain RNAParticle}. 
	 * @param bestPosition The best position to be set.
	 */
	public void setBestPosition(Type bestPosition) {
		this.bestPosition = (RNAConformation) bestPosition;
	}

	/**
	 * {@inheritDoc}
	 */
	public Type getVelocity() {
		return velocity;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setNeighbourhoodBest(Particle particle) {
		neighbourhoodBest = particle;
	}

	/**
	 * {@inheritDoc}
	 */
	public Particle getNeighbourhoodBest() {
		return neighbourhoodBest;
	}

	/**
	 * {@inheritDoc}
	 */
	public void updatePosition() {
		//velocity contains 2 RNAConformation objects. set 0 is open stems. set 1 is close stems.
		
		RNAConformation openStems = (RNAConformation) velocity.get(0);
		RNAConformation closeStems = (RNAConformation) velocity.get(1);
		
		//passed by reference!
		folder.refold(position, openStems, closeStems);
		//System.out.println("Current Fitness "+this.getFitness());
		//System.out.println("Fitnesscalc " + fitnessCalc.getRNAFitness(position));

		/*
		position.removeAll(openStems);
		//do greedy addition
		boolean canAdd = true;
		boolean conflicts = false;
		RNAStem bestStem = null;
		double bestFitness;
		while (canAdd) {
			canAdd = false; 
			bestFitness = Double.MAX_VALUE;	
			bestStem = null;
			for (RNAStem tempStem : closeStems) {
				//check if this stem conflicts with any of the current stems
				conflicts = false;
				for (RNAStem currentStem : position) {		 			
					if (((RNAStem)tempStem).conflictsWith((RNAStem)currentStem)) {
						conflicts = true;
						break;
					}
				}
				if (!conflicts) {
					//add this stem to currentFolding
					position.add(tempStem);
					//check if this stem gives better fitness				
					//test if currentFolding is more fit
					if (fitnessCalc.getRNAFitness(position).doubleValue() < bestFitness) {
						bestFitness = fitnessCalc.getRNAFitness(position).doubleValue();
						bestStem = (RNAStem)tempStem;
					}
					position.remove(tempStem);
				}
			}
			//transfer the best stem from closeStems to currentFolding
			if (bestStem != null) {
				position.add(bestStem);
				closeStems.remove(bestStem);
				canAdd = true;
			}
		}
		*/
	}

	/**
	 * {@inheritDoc}
	 */
	public void updateVelocity() {
		this.velocityUpdateStrategy.updateVelocity(this);
	}

	/**
	 * {@inheritDoc}
	 */
	public void updateControlParameters() {
		this.velocityUpdateStrategy.updateControlParameters(this);
	}

	/**
	 * Set the {@linkplain RNAFolder} to use.
	 * @param folder The {@linkplain RNAFolder} to be used by the current {@linkplain RNAParticle}.
	 */
	public void setRNAFolder(RNAFolder folder) {
		this.folder = folder;
	}
	
    /**
	 * @param fitnessCalc The fitnessCalc to set.
	 */
	public void setFitnessCalc(RNAFitness fitnessCalc) {
		this.fitnessCalc = fitnessCalc;
	}

	/**
	 * @return Returns the fitnessCalc.
	 */
	public RNAFitness getFitnessCalc() {
		return fitnessCalc;
	}


	/**
	 * {@inheritDoc}
	 */
	public void reinitialise() {
		// TODO Auto-generated method stub
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void calculateFitness(boolean count) {
		// TODO Auto-generated method stub
	}

}
