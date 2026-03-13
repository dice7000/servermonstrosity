package net.dice7000.servermonstrosity.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.dice7000.servermonstrosity.ServerMonstrosity;
import net.dice7000.servermonstrosity.client.model.ServerMonolithModel;
import net.dice7000.servermonstrosity.client.model.ServerMonstrosityModel;
import net.dice7000.servermonstrosity.common.entity.ServerMonolithEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class SMonoRenderer extends MobRenderer<ServerMonolithEntity, ServerMonolithModel<ServerMonolithEntity>> {
    public SMonoRenderer(EntityRendererProvider.Context p_174304_) {
        super(p_174304_, new ServerMonolithModel<>(p_174304_.bakeLayer(ServerMonolithModel.LAYER_LOCATION)), 1.0F);
    }
    @Override public @NotNull ResourceLocation getTextureLocation(ServerMonolithEntity p_114482_) {
        return Objects.requireNonNull(ResourceLocation.tryBuild(ServerMonstrosity.MOD_ID, "textures/entity/server_monolith.png"));
    }
    @Override public void render(ServerMonolithEntity p_115455_, float p_115456_, float p_115457_, PoseStack p_115458_, MultiBufferSource p_115459_, int p_115460_) {
        super.render(p_115455_, p_115456_, p_115457_, p_115458_, p_115459_, p_115460_);
    }
}
