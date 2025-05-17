package vectorientation.mixin;

import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import vectorientation.main.Vectorientation;

@Mixin(LivingEntityRenderer.class)
public abstract class LivingEntityRendererMixin {
    @Inject(method = "scale(Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/client/util/math/MatrixStack;F)V", at = @At("HEAD"))
    private void vectorientation$squish(LivingEntity entity, MatrixStack matrices, float amount, CallbackInfo ci) {
        if(!Vectorientation.MOB_SQUETCH) return;
        Identifier id = Registries.ENTITY_TYPE.getId(entity.getType());
        if(Vectorientation.MOB_BLACKLIST != null && Vectorientation.MOB_BLACKLIST.contains(id)) return;
        if(entity.isOnGround()) return;
        Vec3d vel = entity.getVelocity();
        float speed = (float)(Vectorientation.MOB_MIN_WARP + Vectorientation.MOB_WARP_FACTOR * Math.abs((float)vel.y));
        matrices.scale(1/speed, speed, 1/speed);
    }
}
