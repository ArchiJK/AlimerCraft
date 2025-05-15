package ua.hero.archiheroes.items.block.custom;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import ua.hero.archiheroes.AlimerCraft;

public class MutagenBlock extends Block {
    public MutagenBlock(String name) {
        super(Material.ROCK);
        this.setRegistryName(name);
        this.setTranslationKey(name);
        this.setCreativeTab(AlimerCraft.AHTAB);
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }
}