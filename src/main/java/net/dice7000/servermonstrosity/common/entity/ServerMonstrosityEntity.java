package net.dice7000.servermonstrosity.common.entity;

import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class ServerMonstrosityEntity extends Monster {
    private GoalState state = GoalState.IDLE;
    private static final EntityDataAccessor<Boolean> ANIMATE_ATTACK1 =
            SynchedEntityData.defineId(ServerMonstrosityEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> ANIMATE_ATTACK2 =
            SynchedEntityData.defineId(ServerMonstrosityEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> ANIMATE_TRIPLE_ATTACK =
            SynchedEntityData.defineId(ServerMonstrosityEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> ANIMATE_JUMP =
            SynchedEntityData.defineId(ServerMonstrosityEntity.class, EntityDataSerializers.BOOLEAN);

    public ServerMonstrosityEntity(EntityType<? extends Monster> p_33002_, Level p_33003_) {
        super(p_33002_, p_33003_);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(ANIMATE_ATTACK1, false);
        this.entityData.define(ANIMATE_ATTACK2, false);
        this.entityData.define(ANIMATE_TRIPLE_ATTACK, false);
        this.entityData.define(ANIMATE_JUMP, false);
    }

    @Override protected void registerGoals() {
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    int attackCooldown = 0;
    boolean tmp = true;
    @Override public void tick() {
        super.tick();
        if (getTarget() != null) {
            state = GoalState.ATTACK;
        } else {
            state = GoalState.IDLE;
        }
        if (getState() == GoalState.ACTION_END) {
            attackCooldown++;
            if (attackCooldown >= 40) {
                attackCooldown = 0;
                if (tmp) state = GoalState.ATTACK; else state = GoalState.TRIPLE_ATTACK;
                tmp = !tmp;
            }
        }
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.FOLLOW_RANGE, 50D)
                .add(Attributes.MAX_HEALTH, 1000D)
                .add(Attributes.MOVEMENT_SPEED, 1.0D)
                .add(Attributes.ATTACK_DAMAGE, 50D)
                ;
    }

    @Override protected SoundEvent getHurtSound(DamageSource p_33034_) {
        return SoundEvents.BLAZE_HURT;
    }
    @Override protected SoundEvent getDeathSound() {
        return SoundEvents.BLAZE_DEATH;
    }

    public enum GoalState {IDLE, ATTACK, TRIPLE_ATTACK, DASH, JUMP, ACTION_END}
    public GoalState getState() {
        return state;
    }
    public void setState(GoalState state) {
        this.state = state;
    }
}
