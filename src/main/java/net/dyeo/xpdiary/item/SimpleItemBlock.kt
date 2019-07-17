package net.dyeo.xpdiary.item

import net.minecraft.block.Block
import net.minecraft.item.ItemBlock

class SimpleItemBlock(block: Block): ItemBlock(block)
{
    init
    {
        registryName = block.registryName
    }
}