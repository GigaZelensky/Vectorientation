package vectorientation.mixin;

import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModel;
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
public abstract class LivingEntityRendererMixin<T extends LivingEntity, M extends EntityModel<T>> {
    @Inject(method = "scale(Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/client/util/math/MatrixStack;F)V", at = @At("HEAD"))
    private void vectorientation$scale(T entity, MatrixStack matrices, float tickDelta, CallbackInfo ci) {
        if (!Vectorientation.MOBS) return;
        if (entity.isOnGround()) return;
        if (!Vectorientation.SQUETCH) return;
        Identifier id = Registries.ENTITY_TYPE.getId(entity.getType());
        if (Vectorientation.MOB_BLACKLIST.contains(id)) return;
        Vec3d vel = entity.getVelocity();
        float speed = (float)(Vectorientation.MIN_WARP + Vectorientation.WARP_FACTOR * Math.abs(vel.y));
        matrices.scale(1f/speed, speed, 1f/speed);
    }
}
