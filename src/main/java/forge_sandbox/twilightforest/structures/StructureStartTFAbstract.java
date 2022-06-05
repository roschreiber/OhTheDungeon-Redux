/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package forge_sandbox.twilightforest.structures;

import com.google.common.collect.Lists;
import forge_sandbox.StructureBoundingBox;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import otd.lib.async.AsyncWorldEditor;
import forge_sandbox.twilightforest.TFFeature;

public abstract class StructureStartTFAbstract {
	protected List<StructureTFComponent> components = Lists.<StructureTFComponent>newLinkedList();
	protected StructureBoundingBox boundingBox;

	public StructureStartTFAbstract() {
		super();
	}

	protected void updateBoundingBox() {
		this.boundingBox = StructureBoundingBox.getNewBoundingBox();

		for (StructureTFComponent structurecomponent : this.components) {
			this.boundingBox.expandTo(structurecomponent.getBoundingBox());
		}
	}

	public void generateStructure(AsyncWorldEditor worldIn, Random rand, StructureBoundingBox structurebb) {
		Iterator<StructureTFComponent> iterator = this.components.iterator();

		while (iterator.hasNext()) {
			StructureTFComponent structurecomponent = iterator.next();
//            Bukkit.getLogger().log(Level.INFO, structurecomponent.getClass().getCanonicalName() + " : " + structurecomponent.boundingBox.toString());

			if (structurecomponent.getBoundingBox().intersectsWith(structurebb)
					&& !structurecomponent.addComponentParts(worldIn, rand, structurebb)) {
				iterator.remove();
			}
		}
	}

	public StructureStartTFAbstract(AsyncWorldEditor world, TFFeature feature, Random rand, int chunkX, int chunkZ) {
		int x = (chunkX << 4) + 8;
		int z = (chunkZ << 4) + 8;
		int y = 64 + 1; // TODO: maybe a biome-specific altitude for some of them?

		StructureTFComponent firstComponent = makeFirstComponent(world, feature, rand, x, y, z);
		components.add(firstComponent);
		firstComponent.buildComponent(firstComponent, components, rand);

		updateBoundingBox();
	}

	protected abstract StructureTFComponent makeFirstComponent(AsyncWorldEditor world, TFFeature feature, Random rand,
			int x, int y, int z);
}
