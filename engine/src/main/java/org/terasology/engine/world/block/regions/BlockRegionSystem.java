// Copyright 2020 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0
package org.terasology.engine.world.block.regions;

import org.terasology.engine.entitySystem.entity.EntityRef;
import org.terasology.engine.entitySystem.event.EventPriority;
import org.terasology.engine.entitySystem.event.ReceiveEvent;
import org.terasology.engine.entitySystem.systems.BaseComponentSystem;
import org.terasology.engine.entitySystem.systems.RegisterMode;
import org.terasology.engine.entitySystem.systems.RegisterSystem;
import org.terasology.engine.logic.destruction.DoDestroyEvent;
import org.terasology.engine.registry.In;
import org.terasology.engine.world.WorldProvider;
import org.terasology.engine.world.block.BlockManager;
import org.terasology.math.geom.Vector3i;

/**
 *
 */
@RegisterSystem(RegisterMode.AUTHORITY)
public class BlockRegionSystem extends BaseComponentSystem {

    @In
    private WorldProvider worldProvider;

    @In
    private BlockManager blockManager;

    // trivial priority so that all other logic can happen to the region before erasing the blocks in the region
    @ReceiveEvent(priority = EventPriority.PRIORITY_TRIVIAL)
    public void onDestroyed(DoDestroyEvent event, EntityRef entity, BlockRegionComponent blockRegion) {
        for (Vector3i blockPosition : blockRegion.region) {
            worldProvider.setBlock(blockPosition, blockManager.getBlock(BlockManager.AIR_ID));
        }
    }
}