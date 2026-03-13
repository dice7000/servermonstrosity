package net.dice7000.servermonstrosity.common.entity;

import net.minecraft.server.level.ServerBossEvent;

public interface IHasBossBar {
    ServerBossEvent getBossEvent();
}

