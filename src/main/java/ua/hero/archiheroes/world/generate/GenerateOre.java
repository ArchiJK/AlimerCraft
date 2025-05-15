package ua.hero.archiheroes.world.generate;

import lucraft.mods.lucraftcore.materials.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.state.pattern.BlockMatcher;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraftforge.fml.common.IWorldGenerator;

import java.util.Random;
import java.util.function.Predicate;

public class GenerateOre implements IWorldGenerator {

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {

        switch(world.provider.getDimension()) { //Получение ID измерения

            case -1: //Нэзер
                break;
            case 0: //Обычний мир
            //    runGenerator(Blocks.ACACIA_DOOR.getDefaultState(), 1, 8, 0, 16, BlockMatcher.forBlock(Blocks.STONE), world, random, chunkX, chunkZ); //Вызов генератора с заданными параметрами



                break;
            case 1: //Край

            //    IBlockState tinOre = Material.TIN.getBlock(Material.MaterialComponent.ORE);
            //    if (tinOre != null) {
            //    runGenerator(tinOre, Material.TIN.getMinVeinSize(), 8, 0, 150, BlockMatcher.forBlock(Blocks.CHORUS_PLANT), world, random, chunkX, chunkZ);
            //    }

            //    IBlockState tinOreState = Material.TIN.getBlock(Material.MaterialComponent.ORE);

                // Параметры генерации руды TIN


                // Запускаем генерацию руды TIN
            //    runGenerator(tinOreState, 4, 12, 40, 100, BlockMatcher.forBlock(Blocks.CHORUS_FLOWER), world, random, chunkX, chunkZ);

                break;
            case 125: //Мир из другого мода (х заменить на нужный ID измерения)
                break;

        /*
            BlocksInit.TEST_ORE.getDefaultState() - генерируемый блок
            1 - максимальное количество блоков в месторождении, можно задать вариацию записью 1 + random.nextInt(4) для генерации от 1-го до 5-ти блоков в месторождении
            8 - количество месторождений на чанк
            0 - минимальная высота генерации
            16 - максимальная высота генерации
            BlockMatcher.forBlock(Blocks.STONE) - блок, который можно заменить на руду
        */

        }

    }

    private void runGenerator(IBlockState blockToGen, int blockAmount, int chancesToSpawn, int minHeight, int maxHeight, Predicate<IBlockState> blockToReplace, World world, Random rand, int chunk_X, int chunk_Z){ //Объявление генератора

        if (minHeight < 0 || maxHeight > 256 || minHeight > maxHeight) //Проверка на правильность координаты Y

            throw new IllegalArgumentException("Illegal Height Arguments for WorldGenerator"); //Если неправильно то будет ошибка в консоли

        WorldGenMinable generator = new WorldGenMinable(blockToGen, blockAmount, (com.google.common.base.Predicate<IBlockState>) blockToReplace); //Новый экземпляр генератора

        int heightdiff = maxHeight - minHeight + 1;

        for (int i = 0; i < chancesToSpawn; i++){ //Запуск генератора в каждом чанке с заданным параметрами

            int x = chunk_X * 16 +rand.nextInt(16);
            int y = minHeight + rand.nextInt(heightdiff);
            int z = chunk_Z * 16 + rand.nextInt(16);

            generator.generate(world, rand, new BlockPos(x, y, z));

        }

    }

}