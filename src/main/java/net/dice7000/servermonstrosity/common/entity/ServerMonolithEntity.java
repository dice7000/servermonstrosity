package net.dice7000.servermonstrosity.common.entity;

import com.mojang.logging.LogUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.BossEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServerMonolithEntity extends Monster implements IHasBossBar {
    private final int hpCheckPoint = 10;
    private final ServerBossEvent bossEvent;

    private final Map<DamageSource, Float> dampDamageSources = new HashMap<>();

    private final float maxLastDamage = 20000000000000000.0F; // 2 kei
    private final float maxHDamage = 200.0F;
    private final float dampCrit = 2000000000000.0F;

    public ServerMonolithEntity(EntityType<? extends Monster> p_33002_, Level p_33003_) {
        super(p_33002_, p_33003_);

        this.bossEvent = new ServerBossEvent(
                this.getDisplayName(),
                BossEvent.BossBarColor.RED,
                BossEvent.BossBarOverlay.PROGRESS
        );
        this.bossEvent.setVisible(false);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.FOLLOW_RANGE, 50D)
                .add(Attributes.MAX_HEALTH, 1000D)
                .add(Attributes.MOVEMENT_SPEED, 0D)
                .add(Attributes.ATTACK_DAMAGE, 50D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1.0D)
                ;
    }

    int tickCounter = 0;
    @Override public void tick() {
        super.tick();

        invulnerableTime = 0;
        setNoGravity(true);

        tickCounter++;

        if (tickCounter > 30) {
            if (tickCounter % (20 * 20) == 300) level().playSound(null, getX(), getY(), getZ(),
                    SoundEvents.ENDERMAN_STARE, SoundSource.HOSTILE, 1.0F, 1.0F);
            if (tickCounter % (20 * 20) == 390) {
                List<Entity> entityList = level().getEntities(this, this.getBoundingBox().inflate(10));
                for (Entity entity : entityList) {
                    entity.setDeltaMovement(
                            (entity.getX() - this.getX()) >= 0 ? 1 : -1,
                            1,
                            (entity.getZ() - this.getZ()) >= 0 ? 1 : -1
                    );
                }
                entityList.clear();
            }
            if (tickCounter % (20 * 20) == 0) {
                level().playSound(null, BlockPos.containing(position()),
                        SoundEvents.TOTEM_USE, SoundSource.HOSTILE, 1.0F, 1.0F);
            }
        }

        LogUtils.getLogger().info("ServerMonolithEntity health: {}, dampCrit: {}, heal value: {}", getHealth(), dampCrit, getHealth() + (dampCrit / 20));
        float healValue = getHealth() + (dampCrit / 20);
        setHealth(healValue);
    }

    @Override protected SoundEvent getHurtSound(DamageSource p_33034_) {
        return SoundEvents.BLAZE_HURT;
    }
    @Override public boolean ignoreExplosion() {
        return true;
    }
    @Override public boolean fireImmune() {
        return true;
    }
    @Override public boolean isPushable() {
        return false;
    }
    @Override public boolean canCollideWith(Entity entity) {
        return true;
    }
    @Override public boolean canBeCollidedWith() {
        return true;
    }
    @Override public float getMaxHealth() {
        return 9223372036854775807.0F;
    }

    @Override public void setHealth(float p_21154_) {
        float originalDamage = getHealth() - p_21154_;
        if (Float.isNaN(p_21154_)) originalDamage = 0;
        if (p_21154_ == Float.POSITIVE_INFINITY) originalDamage = Float.MAX_VALUE;
        if (p_21154_ == Float.NEGATIVE_INFINITY) originalDamage = Float.MIN_VALUE;
        float adjustDamage;
        if (originalDamage <= 0.0F) {
            adjustDamage = originalDamage;
        } else if (originalDamage <= maxHDamage) {
            adjustDamage = (((originalDamage * 1 / maxHDamage) * maxLastDamage));
        } else {
            adjustDamage = maxLastDamage;
        }

        anotherSetHealth(getHealth() - adjustDamage);
        LogUtils.getLogger().info("ServerMonolithEntity setHealth to {}", adjustDamage);
    }

    private void anotherSetHealth(float health) {
        this.entityData.set(DATA_HEALTH_ID, health);
    }

    @Override public ServerBossEvent getBossEvent() {
        return bossEvent;
    }

    List<Projectile> alreadyPassedProjectile = new ArrayList<>();
    @Override public void aiStep() {
        super.aiStep();

        float barWidth = this.getMaxHealth() / hpCheckPoint;
        int barNumber = (int) Mth.clamp
                (Math.floor((this.getMaxHealth() - this.getHealth()) / barWidth) + 1, 1, hpCheckPoint);

        if (!this.level().isClientSide) {
            float progress = (this.getHealth() - (barWidth * (hpCheckPoint - barNumber))) / barWidth;
            bossEvent.setProgress(progress);
            String name = switch (barNumber) {
                case 1 -> "1st"; case 2 -> "2nd"; case 3 -> "3rd"; case 4 -> "4th"; case 5  -> "5th";
                case 6 -> "6th"; case 7 -> "7th"; case 8 -> "8th"; case 9 -> "9th"; case 10 -> "final";
                default -> throw new IllegalStateException("Unexpected value: " + barNumber);
            };
            this.bossEvent.setName(Component.literal("Server Monolith - " + name + " bar"));

            List<Entity> entityList = level().getEntities(this, this.getBoundingBox().inflate(10));
            for (Entity entity : entityList) {
                if (alreadyPassedProjectile.contains(entity)) continue;
                if (entity instanceof Projectile projectile) {
                    boolean random = Math.random() <= (barNumber * 0.08);
                    if (random) {
                        projectile.discard();
                        level().playSound(null, BlockPos.containing(position()),
                                SoundEvents.STONE_BREAK, SoundSource.HOSTILE, 1.0F, 1.0F);
                    } else {
                        alreadyPassedProjectile.add(projectile);
                    }
                }
            }
            alreadyPassedProjectile.removeIf(Entity::isRemoved);
        }
    }

    @Override public void die(DamageSource source) {
        super.die(source);
        this.bossEvent.setVisible(false);
    }

}
