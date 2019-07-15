package net.dyeo.xpdiary.proxy

import net.dyeo.xpdiary.XPDiary
import net.dyeo.xpdiary.block.BlockDiary
import net.dyeo.xpdiary.gui.GuiHandler
import net.dyeo.xpdiary.gui.GuiHandlerRegistry
import net.dyeo.xpdiary.modid
import net.dyeo.xpdiary.tileentity.TileEntityDiary
import net.minecraft.block.Block
import net.minecraft.init.Blocks
import net.minecraft.init.Items
import net.minecraft.item.ItemBlock
import net.minecraft.item.ItemStack
import net.minecraft.util.ResourceLocation
import net.minecraftforge.fml.common.event.FMLInitializationEvent
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent
import net.minecraftforge.fml.common.network.NetworkRegistry
import net.minecraftforge.fml.common.registry.ForgeRegistries
import net.minecraftforge.fml.common.registry.GameRegistry

open class CommonProxy
{
    val blockDiary = BlockDiary().setRegistryName(BlockDiary.name) as Block
    var itemBlockDiary = ItemBlock(blockDiary)

    open fun preInit(event: FMLPreInitializationEvent)
    {
        registerBlocks()
        registerItems()
        registerTileEntities()
    }

    open fun init(event: FMLInitializationEvent)
    {
        registerCraftingRecipes()
        registerGuiHandler()
    }

    open fun postInit(event: FMLPostInitializationEvent)
    {
    }

    private fun registerBlocks()
    {
        blockDiary.unlocalizedName = BlockDiary.name
        ForgeRegistries.BLOCKS.register(blockDiary)
    }

    private fun registerItems()
    {
        itemBlockDiary.registryName = blockDiary.registryName!!
        ForgeRegistries.ITEMS.register(itemBlockDiary)
    }

    private fun registerCraftingRecipes()
    {
        GameRegistry.addShapedRecipe(ResourceLocation("$modid:${BlockDiary.name}"),
                ResourceLocation(""),
                ItemStack(itemBlockDiary),
                " A ",
                "BCB",
                "CDC",
                'A', Items.WRITABLE_BOOK,
                'B', Items.DIAMOND,
                'C', Blocks.IRON_BLOCK,
                'D', Blocks.LAPIS_BLOCK)
    }

    private fun registerGuiHandler()
    {
        NetworkRegistry.INSTANCE.registerGuiHandler(XPDiary, GuiHandlerRegistry)
        GuiHandlerRegistry.registerGuiHandler(GuiHandler, GuiHandler.id)
    }

    private fun registerTileEntities()
    {
        GameRegistry.registerTileEntity(TileEntityDiary::class.java, "$modid:${BlockDiary.name}")
    }
}