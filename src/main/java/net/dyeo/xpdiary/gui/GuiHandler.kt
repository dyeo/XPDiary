package net.dyeo.xpdiary.gui

import net.dyeo.xpdiary.container.ContainerDiary
import net.dyeo.xpdiary.tileentity.TileEntityDiary
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import net.minecraftforge.fml.common.network.IGuiHandler

object GuiHandler : IGuiHandler
{
    val id = 0

    override fun getServerGuiElement(ID: Int, player: EntityPlayer, world: World, x: Int, y: Int, z: Int): Any?
    {
        if (ID == id)
        {
            val xyz = BlockPos(x, y, z)
            val tileEntity = world.getTileEntity(xyz)
            if (tileEntity is TileEntityDiary)
            {
                val tileEntityDiary = tileEntity as? TileEntityDiary
                return ContainerDiary(player, tileEntityDiary!!)
            }
        }
        return null
    }

    override fun getClientGuiElement(ID: Int, player: EntityPlayer, world: World, x: Int, y: Int, z: Int): Any?
    {
        if (ID == id)
        {
            val xyz = BlockPos(x, y, z)
            val tileEntity = world.getTileEntity(xyz)
            if (tileEntity is TileEntityDiary)
            {
                val tileEntityDiary = tileEntity as? TileEntityDiary
                return GuiDiary(player, tileEntityDiary!!)
            }
        }
        return null
    }
}
