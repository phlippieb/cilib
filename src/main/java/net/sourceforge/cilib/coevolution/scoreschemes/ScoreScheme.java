/*
 * SchoreScheme.java
 * 
 * Created on 2005/08/17
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
 * 
 */
package net.sourceforge.cilib.coevolution.scoreschemes;

import net.sourceforge.cilib.games.*;
import net.sourceforge.cilib.games.agents.*;

/**
 * @author Vangos
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public abstract class ScoreScheme {
	protected void Reset(ParticleAgent[] players)
	{
		for (int i=0; i<players.length; i++)
			players[i].GetParticleAdapter().SetScore(0);
	}
	
	public double GetFitness(ParticleAgent myPlayer, ParticleAgent[] allPlayers)
	{
		double score = 0;
		for (int i=0; i<allPlayers.length; i++)
		{
			if (myPlayer.GetParticleAdapter().GetScore() > 
					allPlayers[i].GetParticleAdapter().GetScore())
				score += 1.0;
		}
		return score;
	}
	
	public abstract void ScoreAgents(ParticleAgent[] players,Game theGame);
}