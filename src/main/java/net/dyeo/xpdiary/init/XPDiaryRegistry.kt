package net.dyeo.xpdiary.init

import net.dyeo.xpdiary.block.BlockDiary
import net.dyeo.xpdiary.modid
import net.dyeo.xpdiary.tileentity.TileEntityDiary
import net.minecraft.block.Block
import net.minecraft.init.Blocks
import net.minecraft.init.Items
import net.minecraft.item.Item
import net.minecraft.item.ItemBlock
import net.minecraft.item.ItemStack
import net.minecraft.util.ResourceLocation
import net.minecraftforge.event.RegistryEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.registry.GameRegistry
import net.minecraftforge.common.ForgeHooks.getRegistryName
import net.minecraft.client.renderer.block.model.ModelResourceLocation
import net.minecraftforge.client.model.ModelLoader
import net.minecraftforge.client.event.ModelRegistryEvent
import net.minecraftforge.fml.common.thread.SidedThreadGroups.CLIENT
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly

@Mod.EventBusSubscriber(modid="xpdiary")
object XPDiaryRegistry
{
    private val items by lazy {
        arrayListOf(
                ItemBlock(BlockDiary).setRegistryName(BlockDiary.registryName!!)
        )
    }

    private val blocks by lazy {
        arrayListOf(
                BlockDiary
        )
    }

    private val tileEntities by lazy {
        arrayListOf(
                BlockDiary.registryName to TileEntityDiary::class.java
        )
    }

    @JvmStatic
    @SubscribeEvent
    fun registerItems(event: RegistryEvent.Register<Item>)
    {
        for (item in items)
        {
            event.registry.register(item)
        }
    }

    @JvmStatic
    @SubscribeEvent
    fun registerBlocks(event: RegistryEvent.Register<Block>)
    {
        for (block in blocks)
        {
            event.registry.register(block)
        }

        for ((registryName, tileEntity) in tileEntities)
        {
            GameRegistry.registerTileEntity(tileEntity, registryName)
        }
    }

    @JvmStatic
    @SubscribeEvent
    fun registerRenders(event: ModelRegistryEvent) {
        for (item in items) {
            val resourceLocation = item.registryName as ResourceLocation
            registerModel(item, 0, resourceLocation)
        }
    }

    private fun registerModel(item: Item, metadata: Int, resourceLocation: ResourceLocation) {
        val modelResourceLocation = ModelResourceLocation(resourceLocation, "inventory")

        ModelLoader.setCustomModelResourceLocation(item, metadata, modelResourceLocation)
    }
}