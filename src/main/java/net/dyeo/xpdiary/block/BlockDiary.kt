package net.dyeo.xpdiary.block

import net.dyeo.xpdiary.XPDiary
import net.dyeo.xpdiary.common.network.GuiHandler
import net.dyeo.xpdiary.modid
import net.dyeo.xpdiary.tileentity.TileEntityDiary
import net.minecraft.block.BlockContainer
import net.minecraft.block.material.MapColor
import net.minecraft.block.material.Material
import net.minecraft.block.state.IBlockState
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.*
import net.minecraft.util.math.AxisAlignedBB
import net.minecraft.util.math.BlockPos
import net.minecraft.world.IBlockAccess
import net.minecraft.world.World
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly
import java.util.*
import net.minecraft.item.Item.getItemFromBlock
import net.minecraft.item.ItemStack
import net.minecraft.util.math.RayTraceResult

object BlockDiary : BlockContainer(Material.ROCK, MapColor.BLUE)
{
    init
    {
        unlocalizedName = "diary"
        registryName = ResourceLocation(modid,"diary")
        setCreativeTab(CreativeTabs.MISC)
        setResistance(30.0f)
        setHardness(5.0f)
    }

    override fun createNewTileEntity(worldIn: World, meta: Int): TileEntity? = TileEntityDiary()

    override fun hasTileEntity(): Boolean = true

    override fun onBlockActivated(worldIn: World, pos: BlockPos, state: IBlockState, playerIn: EntityPlayer, hand: EnumHand, facing: EnumFacing, hitX: Float, hitY: Float, hitZ: Float): Boolean
    {
        if(!worldIn.isRemote && !playerIn.isSneaking)
        {
            (worldIn.getTileEntity(pos) as? TileEntityDiary).let {
                it?.updateBalance()
            }
            
            playerIn.openGui(XPDiary, GuiHandler.GUI_ID_DIARY, worldIn, pos.x, pos.y, pos.z)
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

    override fun getPickBlock(state: IBlockState, target: RayTraceResult?, world: World, pos: BlockPos, player: EntityPlayer?): ItemStack
        = ItemStack(getItemFromBlock(this), 1, this.getMetaFromState(world.getBlockState(pos)))

    override fun quantityDropped(state: IBlockState, fortune: Int, random: Random): Int = 1

    override fun onBlockHarvested(worldIn: World, pos: BlockPos, state: IBlockState, player: EntityPlayer)
    {
        super.onBlockHarvested(worldIn, pos, state, player)
        (worldIn.getTileEntity(pos) as? TileEntityDiary)
                .let {
                    this.dropXpOnBlockBreak(worldIn, pos, it!!.balance.toInt())
                }
    }

    @SideOnly(Side.CLIENT)
    override fun randomDisplayTick(stateIn: IBlockState, worldIn: World, pos: BlockPos, rand: Random)
    {
        super.randomDisplayTick(stateIn, worldIn, pos, rand)

        for (i in -2..2)
        {
            var j = -2
            while (j <= 2)
            {
                if (i > -2 && i < 2 && j == -1)
                {
                    j = 2
                }

                if (rand.nextInt(16) == 0)
                {
                    for (k in 0..1)
                    {
                        val blockpos = pos.add(i, k, j)

                        if (net.minecraftforge.common.ForgeHooks.getEnchantPower(worldIn, blockpos) > 0)
                        {
                            if (!worldIn.isAirBlock(pos.add(i / 2, 0, j / 2)))
                            {
                                break
                            }

                            worldIn.spawnParticle(EnumParticleTypes.ENCHANTMENT_TABLE, pos.x.toDouble() + 0.5, pos.y.toDouble() + 2.0, pos.z.toDouble() + 0.5, (i.toFloat() + rand.nextFloat()).toDouble() - 0.5, (k.toFloat() - rand.nextFloat() - 1.0f).toDouble(), (j.toFloat() + rand.nextFloat()).toDouble() - 0.5)
                        }
                    }
                }
                ++j
            }
        }
    }
}