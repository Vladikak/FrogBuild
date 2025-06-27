//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.example.addon.modules;

import java.util.ArrayList;
import java.util.List;
import meteordevelopment.meteorclient.events.world.TickEvent;
import meteordevelopment.meteorclient.settings.BlockListSetting;
import meteordevelopment.meteorclient.settings.Setting;
import meteordevelopment.meteorclient.settings.SettingGroup;
import meteordevelopment.meteorclient.systems.modules.Categories;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.utils.player.FindItemResult;
import meteordevelopment.meteorclient.utils.player.InvUtils;
import meteordevelopment.meteorclient.utils.world.BlockUtils;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

public class ModuleExample extends Module {
    public int ticks;

    private final BlockPos.Mutable pPos = new BlockPos.Mutable();
    private final BlockPos.Mutable[] placePositions = new BlockPos.Mutable[15];

    private final SettingGroup sgGeneral;
    private final Setting<List<Block>> blocks;

    public ModuleExample(String name, String description) {
        super(Categories.Misc, name, description);
        this.sgGeneral = this.settings.getDefaultGroup();
        this.blocks = this.sgGeneral.add(((BlockListSetting.Builder)((BlockListSetting.Builder)(new BlockListSetting.Builder()).name("blocks")).description("What blocks to use for OneSquad.")).defaultValue(new Block[]{Blocks.OBSIDIAN}).build());
        for (int i = 0; i < placePositions.length; i++) {
            placePositions[i] = new BlockPos.Mutable();
        }
    }

    public void onActivate() {
        if (this.mc.world != null && this.mc.player != null) {
            FindItemResult result = this.getInvBlock();
            if (result != null) {
                this.pPos.set(this.mc.player.getBlockPos());
                int[][] symbolOffsets = {
                    {0, 0, 0},   // Блок 1 (основание)
                    {0, 1, 0},   // Блок 2
                    {0, 2, 0},   // Блок 3
                    {0, 2, 1},   // Блок 4
                    {0, 2, -1},  // Блок 5
                    {0, 3, 0},   // Блок 6
                    {0, 3, 2},   // Блок 7
                    {0, 3, -2},  // Блок 8
                    {0, 4, 0},   // Блок 9
                    {0, 4, 2},   // Блок 10
                    {0, 4, -2},  // Блок 11
                    {0, 5, 0},   // Блок 12
                    {0, 5, 2},   // Блок 13
                    {0, 5, -2},  // Блок 14
                    {0, 6, 0}    // Блок 15
                };

                Direction facing = this.mc.player.getHorizontalFacing();

// Сначала вычисляем точку, от которой будем строить символ (pPos + 2 блока в направлении взгляда)
                int baseX = this.pPos.getX();
                int baseY = this.pPos.getY();
                int baseZ = this.pPos.getZ();

// Смещаем базовую точку на 2 блока в нужном направлении
                switch (facing) {
                    case EAST -> baseX += 2;  // +X
                    case WEST -> baseX -= 2;  // -X
                    case NORTH -> baseZ -= 2; // -Z
                    case SOUTH -> baseZ += 2; // +Z
                }

// Проходим по всем блокам символа и устанавливаем их координаты
                for (int i = 0; i < symbolOffsets.length; i++) {
                    int xOffset = symbolOffsets[i][0];
                    int yOffset = symbolOffsets[i][1];
                    int zOffset = symbolOffsets[i][2];

                    // Поворачиваем смещение в зависимости от направления
                    switch (facing) {
                        case EAST, WEST:
                            // Ориентация по X (без изменений или инвертирование X)
                            if (facing == Direction.WEST) {
                                xOffset = -xOffset;
                            }
                            break;
                        case NORTH, SOUTH:
                            // Ориентация по Z (меняем X и Z местами)
                            int temp = xOffset;
                            xOffset = (facing == Direction.NORTH) ? -zOffset : zOffset;
                            zOffset = temp;
                            break;
                    }

                    // Устанавливаем итоговые координаты (базовая точка + смещение)
                    this.placePositions[i].set(
                        baseX + xOffset,
                        baseY + yOffset,
                        baseZ + zOffset
                    );
                }

                this.ticks = 0;
            }
        } else {
            this.toggle();
        }
    }

    @EventHandler
    private void onTick(TickEvent.Pre event) {
        if (this.ticks == 0) {
            if (!BlockUtils.canPlace(this.placePositions[0]) && BlockUtils.canPlace(this.placePositions[1]) && BlockUtils.canPlace(this.placePositions[2]) && BlockUtils.canPlace(this.placePositions[3]) && BlockUtils.canPlace(this.placePositions[4]) && BlockUtils.canPlace(this.placePositions[5])) {
                this.toggle();
            } else {
                BlockUtils.place(this.placePositions[0], this.getInvBlock(), false, 100, true);
                ++this.ticks;
            }
        } else if (this.ticks == 1) {
            if (!this.mc.world.getBlockState(this.placePositions[0]).isAir()) {
                BlockUtils.place(this.placePositions[1], this.getInvBlock(), false, 100, true);
                ++this.ticks;
            } else {
                --this.ticks;
            }

        } else if (this.ticks == 2) {
            if (!this.mc.world.getBlockState(this.placePositions[1]).isAir()) {
                BlockUtils.place(this.placePositions[2], this.getInvBlock(), false, 100, true);
                ++this.ticks;
            } else {
                --this.ticks;
            }

        } else if (this.ticks == 3) {
            if (!this.mc.world.getBlockState(this.placePositions[2]).isAir()) {
                BlockUtils.place(this.placePositions[3], this.getInvBlock(), false, 100, true);
                ++this.ticks;
            } else {
                --this.ticks;
            }

        } else if (this.ticks == 4) {
            if (!this.mc.world.getBlockState(this.placePositions[3]).isAir()) {
                BlockUtils.place(this.placePositions[4], this.getInvBlock(), false, 100, true);
                ++this.ticks;
            } else {
                --this.ticks;
            }

        } else if (this.ticks == 5) {
            if (!this.mc.world.getBlockState(this.placePositions[4]).isAir()) {
                BlockUtils.place(this.placePositions[5], this.getInvBlock(), false, 100, true);
                ++this.ticks;
            } else {
                --this.ticks;
            }

        } else if (this.ticks == 6) {
            if (!this.mc.world.getBlockState(this.placePositions[5]).isAir()) {
                BlockUtils.place(this.placePositions[6], this.getInvBlock(), false, 100, true);
                ++this.ticks;
            } else {
                --this.ticks;
            }

        } else if (this.ticks == 7) {
            if (!this.mc.world.getBlockState(this.placePositions[6]).isAir()) {
                BlockUtils.place(this.placePositions[7], this.getInvBlock(), false, 100, true);
                ++this.ticks;
            } else {
                --this.ticks;
            }

        } else if (this.ticks == 8) {
            if (!this.mc.world.getBlockState(this.placePositions[7]).isAir()) {
                BlockUtils.place(this.placePositions[8], this.getInvBlock(), false, 100, true);
                ++this.ticks;
            } else {
                --this.ticks;
            }

        } else if (this.ticks == 9) {
            if (!this.mc.world.getBlockState(this.placePositions[8]).isAir()) {
                BlockUtils.place(this.placePositions[9], this.getInvBlock(), false, 100, true);
                ++this.ticks;
            } else {
                --this.ticks;
            }

        } else if (this.ticks == 10) {
            if (!this.mc.world.getBlockState(this.placePositions[9]).isAir()) {
                BlockUtils.place(this.placePositions[10], this.getInvBlock(), false, 100, true);
                ++this.ticks;
            } else {
                --this.ticks;
            }

        } else if (this.ticks == 11) {
            if (!this.mc.world.getBlockState(this.placePositions[10]).isAir()) {
                BlockUtils.place(this.placePositions[11], this.getInvBlock(), false, 100, true);
                ++this.ticks;
            } else {
                --this.ticks;
            }

        } else if (this.ticks == 12) {
            if (!this.mc.world.getBlockState(this.placePositions[11]).isAir()) {
                BlockUtils.place(this.placePositions[12], this.getInvBlock(), false, 100, true);
                ++this.ticks;
            } else {
                --this.ticks;
            }

        } else if (this.ticks == 13) {
            if (!this.mc.world.getBlockState(this.placePositions[12]).isAir()) {
                BlockUtils.place(this.placePositions[13], this.getInvBlock(), false, 100, true);
                ++this.ticks;
            } else {
                --this.ticks;
            }

        } else if (this.ticks == 14) {
            if (!this.mc.world.getBlockState(this.placePositions[13]).isAir()) {
                BlockUtils.place(this.placePositions[14], this.getInvBlock(), false, 100, true);
                ++this.ticks;
            } else {
                --this.ticks;
            }

        } else if (this.ticks == 15) {
            if (!this.mc.world.getBlockState(this.placePositions[14]).isAir()) {
                this.toggle();
            } else {
                --this.ticks;
            }
        }
    }

    private FindItemResult getInvBlock() {
        return InvUtils.findInHotbar((itemStack) -> ((List)this.blocks.get()).contains(Block.getBlockFromItem(itemStack.getItem())));
    }
}
