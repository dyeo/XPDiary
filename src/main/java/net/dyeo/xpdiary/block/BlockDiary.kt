package net.dyeo.xpdiary.block

import net.dyeo.xpdiary.XPDiary
import net.dyeo.xpdiary.gui.GuiHandler
import net.dyeo.xpdiary.tileentity.TileEntityDiary
import net.minecraft.block.BlockContainer
import net.minecraft.block.material.Material
import net.minecraft.block.state.IBlockState
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.BlockRenderLayer
import net.minecraft.util.EnumBlockRenderType
import net.minecraft.util.EnumFacing
import net.minecraft.util.EnumHand
import net.minecraft.util.math.AxisAlignedBB
import net.minecraft.util.math.BlockPos
import net.minecraft.world.IBlockAccess
import net.minecraft.world.World
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly

class BlockDiary : BlockContainer(Material.ROCK)
{
    companion object
    {
        const val name = "diary"
    }

    init
    {
        setCreativeTab(CreativeTabs.MISC)
    }

    override fun createNewTileEntity(worldIn: World, meta: Int): TileEntity? = TileEntityDiary()

    override fun hasTileEntity(): Boolean = true

    override fun onBlockActivated(worldIn: World, pos: BlockPos, state: IBlockState, playerIn: EntityPlayer, hand: EnumHand, facing: EnumFacing, hitX: Float, hitY: Float, hitZ: Float): Boolean
    {
        if(!worldIn.isRemote)
        {
            playerIn.openGui(XPDiary, GuiHandler.id, worldIn, pos.x, pos.y, pos.z)
            (worldIn.getTileEntity(pos) as? TileEntityDiary).let {
                it?.updateBalance()
            }
        }
        return true
    }

    override fun getBoundingBox(state: IBlockState?, source: IBlockAccess?, pos: BlockPos?): AxisAlignedBB
            = AxisAlignedBB(0.0, 0.0, 0.0, (16 / 16.0f).toDouble(), (12 / 16.0f).toDouble(), (16 / 16.0f).toDouble())

    @SideOnly(Side.CLIENT)
    override fun getBlockLayer(): BlockRenderLayer = BlockRenderLayer.SOLID

    override fun isOpaqueCube(state: IBlockState?): Boolean = false

    override fun isFullCube(state: IBlockState?): Boolean = false

    override fun getRenderType(iBlockState: IBlockState?): EnumBlockRenderType = EnumBlockRenderType.MODEL
}