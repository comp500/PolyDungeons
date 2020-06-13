package polydungeons.structures;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import net.minecraft.block.Blocks;
import net.minecraft.structure.pool.SinglePoolElement;
import net.minecraft.structure.pool.StructurePool;
import net.minecraft.structure.pool.StructurePoolBasedGenerator;
import net.minecraft.structure.pool.StructurePoolElement;
import net.minecraft.structure.processor.RuleStructureProcessor;
import net.minecraft.structure.processor.StructureProcessor;
import net.minecraft.structure.processor.StructureProcessorRule;
import net.minecraft.structure.rule.AlwaysTrueRuleTest;
import net.minecraft.structure.rule.RandomBlockMatchRuleTest;
import net.minecraft.util.Identifier;
import polydungeons.PolyDungeons;

import java.util.Arrays;
import java.util.List;

public class DungeonData {

    public static void registerPool(String name, String terminators, List<Pair<StructurePoolElement, Integer>> elements) {
        StructurePoolBasedGenerator.REGISTRY.add(
                new StructurePool(
                        new Identifier(PolyDungeons.MODID, name),
                        new Identifier(terminators),
                        elements,
                        StructurePool.Projection.RIGID
                )
        );
    }

    public static void init() {
    }

    static {
        // it took me a while to figure out what exactly this does, but it turns out it randomly replaces blocks.
        // intuitive, right?
        ImmutableList<StructureProcessor> crackBlackstone = ImmutableList.of(
            new RuleStructureProcessor(
                    ImmutableList.of(
                            new StructureProcessorRule(
                                new RandomBlockMatchRuleTest(Blocks.POLISHED_BLACKSTONE_BRICKS, 0.1F),
                                AlwaysTrueRuleTest.INSTANCE,
                                Blocks.CRACKED_POLISHED_BLACKSTONE_BRICKS.getDefaultState()
                            ),
                            new StructureProcessorRule(
                                    new RandomBlockMatchRuleTest(Blocks.POLISHED_BLACKSTONE, 0.1F),
                                    AlwaysTrueRuleTest.INSTANCE,
                                    Blocks.BLACKSTONE.getDefaultState()
                            )
                    ))
        );
        registerPool("dungeon/caps", "empty", Arrays.asList(
                new Pair<>(new SinglePoolElement("polydungeons:dungeon/caps/cap", crackBlackstone), 1),
                new Pair<>(new SinglePoolElement("polydungeons:dungeon/caps/small_cap", crackBlackstone), 1)
        ));

        registerPool("dungeon/rooms", "empty", Arrays.asList(
                new Pair<>(new SinglePoolElement("polydungeons:dungeon/rooms/room_7x7_empty", crackBlackstone), 1),
                new Pair<>(new SinglePoolElement("polydungeons:dungeon/rooms/room_14x7_empty", crackBlackstone), 1),
                new Pair<>(new SinglePoolElement("polydungeons:dungeon/rooms/staircase", crackBlackstone), 1),
                new Pair<>(new SinglePoolElement("polydungeons:dungeon/halls/hall_7x7_empty", crackBlackstone), 8)
        ));
    }
}