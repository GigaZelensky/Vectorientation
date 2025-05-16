
package vectorientation.mixin;

import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.MathHelper;
import net.minecraft.client.render.entity.model.EntityModel;
import org.joml.Vector3f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.joml.AxisAngle4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import net.minecraft.client.render.entity.EntityRenderer;

@SuppressWarnings("rawtypes")
@Mixin(LivingEntityRenderer.class)
public abstract class LivingEntityRendererMixin<T extends LivingEntity> {

    private static final float EPS = 0.01F;

    @Inject(method = "render", at = @At("HEAD"))
    private void vectorientation$preRender(T entity, float entityYaw, float tickDelta, MatrixStack matrices,
                                           VertexConsumerProvider vertexConsumers, int light, CallbackInfo ci) {
        Vec3d vel = entity.getVelocity();
        double speed = vel.length();

        if (speed <= EPS) {
            return;
        }

        matrices.push();

        Vec3d n = vel.normalize();
        float yaw = (float) (MathHelper.atan2(n.z, n.x) * (180.0F / Math.PI)) - 90.0F;
        float pitch = (float) (MathHelper.asin(n.y) * (180.0F / Math.PI));

        matrices.multiply(net.minecraft.util.math.RotationAxis.POSITIVE_Y.rotationDegrees(-yaw));
        matrices.multiply(net.minecraft.util.math.RotationAxis.POSITIVE_Z.rotationDegrees(pitch));

        double stretch = Math.min(speed * 4.0, 0.8);
        float sx = 1.0f - (float) stretch * 0.5f;
        float sy = 1.0f + (float) stretch;
        matrices.scale(sx, sy, sx);
    }

    @Inject(method = "render", at = @At("RETURN"))
    private void vectorientation$postRender(T entity, float entityYaw, float tickDelta, MatrixStack matrices,
                                            VertexConsumerProvider vertexConsumers, int light, CallbackInfo ci) {
        Vec3d vel = entity.getVelocity();
        if (vel.length() > EPS) {
            matrices.pop();
        }
    }
}
