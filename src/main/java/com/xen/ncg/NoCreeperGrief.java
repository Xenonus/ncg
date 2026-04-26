package com.xen.ncg;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.level.GameRules;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.level.ExplosionEvent;
import net.neoforged.bus.api.IEventBus;

@Mod("nocreepergrief")
public class NoCreeperGrief {

    public static final GameRules.Key<GameRules.BooleanValue> DO_CREEPERS_GRIEF =
            GameRules.register(
                    "doCreepersGrief",
                    GameRules.Category.MISC,
                    GameRules.BooleanValue.create(true)
            );

    public NoCreeperGrief(IEventBus modBus) {
        NeoForge.EVENT_BUS.register(new EventHandler());
    }
}

class EventHandler {

    @SubscribeEvent
    public void onExplosionDetonate(ExplosionEvent.Detonate event) {
        Entity source = event.getExplosion().getDirectSourceEntity();

        if (source instanceof Creeper creeper && creeper.level() instanceof ServerLevel level) {
            boolean doGrief = level.getGameRules().getBoolean(NoCreeperGrief.DO_CREEPERS_GRIEF);

            if (!doGrief) {
                event.getAffectedBlocks().clear();
            }
        }
    }
}
