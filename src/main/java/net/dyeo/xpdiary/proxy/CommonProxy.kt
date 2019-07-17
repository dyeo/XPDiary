package net.dyeo.xpdiary.proxy

import net.dyeo.xpdiary.XPDiary
import net.dyeo.xpdiary.block.BlockDiary
import net.dyeo.xpdiary.gui.GuiHandler
import net.dyeo.xpdiary.gui.GuiHandlerRegistry
import net.dyeo.xpdiary.init.ModBlocks
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
    open fun preInit(event: FMLPreInitializationEvent)
    {
        registerTileEntities()
    }

    open fun init(event: FMLInitializationEvent)
    {
        ModBlocks.registerCraftingRecipes()
        registerGuiHandler()
    }

    open fun postInit(event: FMLPostInitializationEvent)
    {
    }

    private fun registerGuiHandler()
    {
        NetworkRegistry.INSTANCE.registerGuiHandler(XPDiary, GuiHandlerRegistry)
        GuiHandlerRegistry.registerGuiHandler(GuiHandler, GuiHandler.id)
    }

    private fun registerTileEntities()
    {
        GameRegistry.registerTileEntity(TileEntityDiary::class.java, "xpdiary:diary")
    }
}