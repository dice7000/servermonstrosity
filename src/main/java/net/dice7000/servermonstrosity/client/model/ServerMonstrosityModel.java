package net.dice7000.servermonstrosity.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.dice7000.servermonstrosity.ServerMonstrosity;
import net.dice7000.servermonstrosity.client.animation.ServerMonstrosityModelAnimation;
import net.dice7000.servermonstrosity.common.entity.ServerMonstrosityEntity;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.Entity;

public class ServerMonstrosityModel<T extends Entity> extends HierarchicalModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ServerMonstrosity.SMLocation("servermonstrosity_layer"), "main");
	private final ModelPart all;
	private final ModelPart headbody;
	private final ModelPart head;
	private final ModelPart body;
	private final ModelPart arm;
	private final ModelPart leftarm;
	private final ModelPart leftshoulder;
	private final ModelPart leftupperarm;
	private final ModelPart lefthand;
	private final ModelPart rightarm;
	private final ModelPart rightshoulder;
	private final ModelPart rightupperarm;
	private final ModelPart righthand;
	private final ModelPart leg;
	private final ModelPart leftleg;
	private final ModelPart rightleg;

	public ServerMonstrosityModel(ModelPart root) {
		this.all = root.getChild("all");
		this.headbody = this.all.getChild("headbody");
		this.head = this.headbody.getChild("head");
		this.body = this.headbody.getChild("body");
		this.arm = this.all.getChild("arm");
		this.leftarm = this.arm.getChild("leftarm");
		this.leftshoulder = this.leftarm.getChild("leftshoulder");
		this.leftupperarm = this.leftarm.getChild("leftupperarm");
		this.lefthand = this.leftarm.getChild("lefthand");
		this.rightarm = this.arm.getChild("rightarm");
		this.rightshoulder = this.rightarm.getChild("rightshoulder");
		this.rightupperarm = this.rightarm.getChild("rightupperarm");
		this.righthand = this.rightarm.getChild("righthand");
		this.leg = this.all.getChild("leg");
		this.leftleg = this.leg.getChild("leftleg");
		this.rightleg = this.leg.getChild("rightleg");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition all = partdefinition.addOrReplaceChild("all", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition headbody = all.addOrReplaceChild("headbody", CubeListBuilder.create(), PartPose.offset(0.0F, -36.0F, 0.0F));

		PartDefinition head = headbody.addOrReplaceChild("head", CubeListBuilder.create().texOffs(200, 192).addBox(-14.0F, -20.0F, -19.0F, 28.0F, 40.0F, 20.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -31.0F, -18.0F));

		PartDefinition body = headbody.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-36.0F, -56.0F, -16.0F, 72.0F, 56.0F, 32.0F, new CubeDeformation(0.0F))
		.texOffs(224, 144).addBox(-14.0F, 0.0F, -10.0F, 28.0F, 12.0F, 20.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition arm = all.addOrReplaceChild("arm", CubeListBuilder.create(), PartPose.offset(0.0F, -74.0F, 0.0F));

		PartDefinition leftarm = arm.addOrReplaceChild("leftarm", CubeListBuilder.create(), PartPose.offset(54.0F, 0.0F, 0.0F));

		PartDefinition leftshoulder = leftarm.addOrReplaceChild("leftshoulder", CubeListBuilder.create().texOffs(0, 192).addBox(-18.0F, -36.0F, -16.0F, 18.0F, 24.0F, 32.0F, new CubeDeformation(0.0F))
		.texOffs(0, 88).addBox(-18.0F, -12.0F, -16.0F, 36.0F, 24.0F, 32.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition leftupperarm = leftarm.addOrReplaceChild("leftupperarm", CubeListBuilder.create().texOffs(0, 248).addBox(-8.0F, -10.0F, -8.0F, 16.0F, 20.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 20.0F, 0.0F));

		PartDefinition lefthand = leftarm.addOrReplaceChild("lefthand", CubeListBuilder.create().texOffs(0, 144).addBox(-14.0F, -10.0F, -14.0F, 28.0F, 20.0F, 28.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 38.0F, 0.0F));

		PartDefinition rightarm = arm.addOrReplaceChild("rightarm", CubeListBuilder.create(), PartPose.offset(-54.0F, 0.0F, 0.0F));

		PartDefinition rightshoulder = rightarm.addOrReplaceChild("rightshoulder", CubeListBuilder.create().texOffs(100, 192).addBox(0.0F, -36.0F, -16.0F, 18.0F, 24.0F, 32.0F, new CubeDeformation(0.0F))
		.texOffs(136, 88).addBox(-18.0F, -12.0F, -16.0F, 36.0F, 24.0F, 32.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition rightupperarm = rightarm.addOrReplaceChild("rightupperarm", CubeListBuilder.create().texOffs(64, 248).addBox(-9.0F, -10.0F, -8.0F, 16.0F, 20.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 20.0F, 0.0F));

		PartDefinition righthand = rightarm.addOrReplaceChild("righthand", CubeListBuilder.create().texOffs(112, 144).addBox(-14.0F, -10.0F, -14.0F, 28.0F, 20.0F, 28.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 38.0F, 0.0F));

		PartDefinition leg = all.addOrReplaceChild("leg", CubeListBuilder.create(), PartPose.offset(0.0F, -24.0F, 0.0F));

		PartDefinition leftleg = leg.addOrReplaceChild("leftleg", CubeListBuilder.create().texOffs(208, 44).addBox(-2.0F, 0.0F, -10.0F, 24.0F, 24.0F, 20.0F, new CubeDeformation(0.0F)), PartPose.offset(10.0F, 0.0F, 0.0F));

		PartDefinition rightleg = leg.addOrReplaceChild("rightleg", CubeListBuilder.create().texOffs(208, 0).addBox(-22.0F, 0.0F, -10.0F, 24.0F, 24.0F, 20.0F, new CubeDeformation(0.0F)), PartPose.offset(-10.0F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 512, 512);
	}

	@Override public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.all.getAllParts().forEach(ModelPart::resetPose);
	}

	@Override public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		all.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	@Override public ModelPart root() {
		return all;
	}
}