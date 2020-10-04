package de.relluem94.vulcan.particles;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.lwjgl.util.vector.Matrix4f;

import de.relluem94.vulcan.renderEngine.MasterRenderer;

public class ParticleMaster {
	private static Map<ParticleTexture, List<Particle>> particles = new HashMap<ParticleTexture, List<Particle>>();
	private static ParticleRenderer renderer;
	
	public static void init(Matrix4f projectionMatrix){
		renderer = new ParticleRenderer(projectionMatrix);
	}
	
	/**
	 * Update after MasterRenderer
	 * 
	 */
	public static void updateProjectionMatrix(){
		renderer.updateProjectionMatrix(MasterRenderer.getProjectionMatrix());
	}
	
	public static void update(){
		Iterator<Entry<ParticleTexture, List<Particle>>> mapIterator = particles.entrySet().iterator();
		while(mapIterator.hasNext()){
			Entry<ParticleTexture, List<Particle>> entry = mapIterator.next();
			List<Particle> list = entry.getValue();
			Iterator<Particle> iterator = list.iterator();
			while(iterator.hasNext()){
				Particle p = iterator.next();
				boolean stillAlive = p.update();
				if(!stillAlive){
					iterator.remove();
					if(list.isEmpty()){
						mapIterator.remove();
					}	
				}
			}
			if(!entry.getKey().usesAdditiveBlending()){
				InsertionSort.sortHighToLow(list);
			}
		}
	}
	
	public static void renderParticles(){
		renderer.render(particles);
	}
	
	public static void cleanUp(){
		renderer.cleanUp();
	}
	
	public static void addParticle(Particle particle){
		List<Particle> list = particles.get(particle.getTexture());
		if(list == null){
			list = new ArrayList<Particle>();
			particles.put(particle.getTexture(), list);
		}
		list.add(particle);
	}
	
}
