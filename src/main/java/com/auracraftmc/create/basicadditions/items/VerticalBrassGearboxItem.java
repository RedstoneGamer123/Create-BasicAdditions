package com.auracraftmc.create.basicadditions.items;

import java.util.Map;

import com.auracraftmc.create.basicadditions.registries.Blocks;
import com.simibubi.create.content.contraptions.base.IRotate;
import com.simibubi.create.foundation.utility.Iterate;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.core.NonNullList;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

public class VerticalBrassGearboxItem extends BlockItem {

	public VerticalBrassGearboxItem(Properties properties) {
		super(Blocks.BRASS_GEARBOX.get(), properties);
	}

	@Override
	public void fillItemCategory(CreativeModeTab tab, NonNullList<ItemStack> stacks) {
	}
	
	@Override
	public String getDescriptionId() {
		return "item.create_basic_additions.vertical_brass_gearbox";
	}

	@Override
	public void registerBlocks(Map<Block, Item> p_195946_1_, Item p_195946_2_) {
	}

	@Override
	protected boolean updateCustomBlockEntityTag(BlockPos pos, Level world, Player player, ItemStack stack, BlockState state) {
		Axis prefferedAxis = null;
		
		for(Direction side : Iterate.horizontalDirections) {
			BlockState blockState = world.getBlockState(pos.relative(side));
			
			if(blockState.getBlock() instanceof IRotate) {
				if(((IRotate) blockState.getBlock()).hasShaftTowards(world, pos.relative(side), blockState, side.getOpposite())) {
					if(prefferedAxis != null && prefferedAxis != side.getAxis()) {
						prefferedAxis = null;
						break;
					} else prefferedAxis = side.getAxis();
				}
			}
		}

		Axis axis = prefferedAxis == null ? player.getDirection().getClockWise().getAxis() : prefferedAxis == Axis.X ? Axis.Z : Axis.X;
		
		world.setBlockAndUpdate(pos, state.setValue(BlockStateProperties.AXIS, axis));
		
		return super.updateCustomBlockEntityTag(pos, world, player, stack, state);
	}

}
