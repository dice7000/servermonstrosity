package net.dice7000.servermonstrosity.common.entity;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.BossEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;

public class ServerMonolithEntity extends Monster implements IHasBossBar {
    private int hpCheckPoint = 10;
    private final ServerBossEvent bossEvent;
    private float barWidth;
    private int barNumber = 1;

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

    @Override public void tick() {
        super.tick();

        invulnerableTime = 0;
        setNoGravity(true);
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

    float maxLastDamage = 20000000000000000.0F;
    float hDamageBrunchPoint = 200.0F;
    float maxHDamage = 10000.0F;
    float dampCrit = 2000000000000.0F;
    @Override public void setHealth(float p_21154_) {


        float originalDamage = getHealth() - p_21154_;
        float adjustDamage;
        if (originalDamage <= 0.0F) {
            adjustDamage = originalDamage;
        } else if (originalDamage <= hDamageBrunchPoint) {
            adjustDamage = ((originalDamage * (1 / hDamageBrunchPoint)) * dampCrit);
        } else if (originalDamage <= maxHDamage) {
            adjustDamage = (dampCrit + ((p_21154_ * 1 / maxHDamage) * (maxLastDamage - dampCrit)));
        } else {
            adjustDamage = maxLastDamage;
        }

        super.setHealth(getHealth() - adjustDamage);
    }

    @Override public ServerBossEvent getBossEvent() {
        return bossEvent;
    }

    @Override public void aiStep() {
        super.aiStep();

        barWidth = this.getMaxHealth() / hpCheckPoint;
        barNumber = (int) Mth.clamp
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
        }
    }

    @Override public void die(DamageSource source) {
        super.die(source);
        this.bossEvent.setVisible(false);
    }

}
