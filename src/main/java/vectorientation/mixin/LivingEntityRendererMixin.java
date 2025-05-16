package vectorientation.mixin;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import vectorientation.main.Vectorientation;

@Mixin(LivingEntityRenderer.class)
public abstract class LivingEntityRendererMixin<T extends LivingEntity> {
    @Inject(
            method = "Lnet/minecraft/client/render/entity/LivingEntityRenderer;render(Lnet/minecraft/entity/LivingEntity;FFLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V",
            at = @At("HEAD")
    )
    private void vectorientation$applySquish(T entity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, CallbackInfo ci) {
        matrixStack.push();
        if (!entity.isOnGround() && Vectorientation.SQUETCH) {
            Vec3d vel = entity.getVelocity();
            float speed = (float) (Vectorientation.MIN_WARP + Vectorientation.WARP_FACTOR * Math.abs(vel.y));
            matrixStack.scale(1.0F / speed, speed, 1.0F / speed);
        }
    }

    @Inject(
            method = "Lnet/minecraft/client/render/entity/LivingEntityRenderer;render(Lnet/minecraft/entity/LivingEntity;FFLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V",
            at = @At("TAIL")
    )
    private void vectorientation$popMatrix(T entity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, CallbackInfo ci) {
        matrixStack.pop();
    }
}
